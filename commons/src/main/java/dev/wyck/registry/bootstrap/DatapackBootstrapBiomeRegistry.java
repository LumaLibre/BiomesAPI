package dev.wyck.registry.bootstrap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.biome.Biome;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.BiomeRegistry;
import dev.wyck.util.internal.LevelBiomeEdit;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.DatapackPromotion;
import dev.wyck.util.DimensionSources;
import dev.wyck.util.ThrowingRunnable;
import dev.wyck.worldgen.climate.ClimatePoint;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.registrar.RegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.minecraft.SharedConstants;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of {@link BootstrapBiomeRegistry} that registers custom biomes through Paper's
 * supported datapack pipeline the only contract-backed way to enter the biome registry before it
 * freezes. No {@code PaperRegistries}, no {@code Unsafe}, no JVM flags, no hand-fighting the load
 * future for a registry handle (the tag/compose windows don't expose a writable biome registry).
 *
 * <p>Flow: collect biomes via {@link #queue}, then on the {@code DATAPACK_DISCOVERY} lifecycle
 * event (fires during load, before registries are built from packs) generate an in-memory datapack:
 * each queued biome is built to an NMS {@link net.minecraft.world.level.biome.Biome} via the existing programmatic builder, encoded
 * to JSON with the biome codec, and written to {@code data/<ns>/worldgen/biome/<name>.json}.
 *
 * @version 2.3.0
 * @since 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class DatapackBootstrapBiomeRegistry implements BootstrapBiomeRegistry {

    // TODO: Abstract this out to some interfaces to use elsewhere in API

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PACK_ID_FORMAT = "wyck_biome_%s";
    private static final String FEATURE_NAMESPACE_FORMAT = "wyck_%s";

    private final List<Biome> pending = new ArrayList<>();
    private final List<LevelBiomeEdit> dimensionEdits = new ArrayList<>();
    private boolean installed = false;
    private @Nullable String packId;
    private @Nullable String featureNamespace;
    private @Nullable Throwable deferred;

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry queue(Biome biome) {
        this.pending.add(biome);
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry install(BootstrapContext context) {
        Preconditions.checkState(!this.installed, "already installed");
        this.installed = true;
        String name = context.getPluginMeta().getName().toLowerCase(Locale.ROOT).replace(' ', '-');
        this.packId = String.format(PACK_ID_FORMAT, name);
        this.featureNamespace = String.format(FEATURE_NAMESPACE_FORMAT, name.replace('-', '_'));

        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(this::discover)
        );
        return this;
    }

    @AsOf("2.3.0")
    public BootstrapBiomeRegistry deferring(ThrowingRunnable action) {
        if (this.deferred != null) {
           return this;
        }
        try {
            action.run();
        } catch (Throwable t) {
            if (this.deferred == null) {
                this.deferred = t;
            } else {
                this.deferred.addSuppressed(t);
            }
        }
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry addToDimension(Dimension dimension, Biome biome, ClimatePoint placement) {
        this.dimensionEdits.add(new LevelBiomeEdit.Add(dimension.resourceKey(), biome.resourceKey(), placement));
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry replaceInDimension(Dimension dimension, Biome target, Biome replacement) {
        this.dimensionEdits.add(new LevelBiomeEdit.Replace(dimension.resourceKey(), target.resourceKey(), replacement.resourceKey()));
        return this;
    }

    /**
     * Generates the biome datapack and registers it with Paper. Rethrows on failure so datapack
     * load aborts and the server refuses to start.
     */
    private void discover(RegistrarEvent<DatapackRegistrar> event) {
        if (this.deferred != null) {
            throw new RuntimeException("Deferred custom biome failure; aborting startup", this.deferred);
        }
        if (this.pending.isEmpty() && this.dimensionEdits.isEmpty()) {
            return;
        }
        try {
            Preconditions.checkNotNull(this.packId, "install() not called");
            Path packRoot = writePack();
            event.registrar().discoverPack(packRoot.toUri(), packId);
            this.pending.clear();
            this.dimensionEdits.clear();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to register custom biomes via datapack; aborting", t);
        }
    }

    /**
     * Writes a temporary datapack containing one JSON file per queued biome, plus pack.mcmeta.
     * Returns the pack root directory.
     */
    private Path writePack() throws Exception {
        Path root = Files.createTempDirectory("wyck-biome-pack-");

        // pack.mcmeta. This MC version uses the versioned schema: alongside pack_format it requires
        // min_format and max_format as [major, minor] arrays. We declare support for exactly the
        // running format (minor 0).
        int format = serverDataPackFormat();
        com.google.gson.JsonArray formatRange = new com.google.gson.JsonArray();
        formatRange.add(format);
        formatRange.add(0);

        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", format);
        pack.add("min_format", formatRange);
        pack.add("max_format", formatRange);
        pack.addProperty("description", "Wyck generated biomes");
        JsonObject meta = new JsonObject();
        meta.add("pack", pack);
        Files.writeString(root.resolve("pack.mcmeta"), GSON.toJson(meta));

        BiomeRegistry builder = BiomeRegistry.registry();

        DatapackPromotion promotion = DatapackPromotion.beginCollect(this.featureNamespace);
        try {
            for (Biome biome : this.pending) {
                builder.buildDelegate(biome);
            }

            promotion.buildProvider();

            BootstrapSafeMinecraftRegistries.setActive(promotion.provider());
            promotion.writeFiles(root, GSON);

            RegistryOps<JsonElement> ops = promotion.provider().createSerializationContext(JsonOps.INSTANCE);

            promotion.beginReference();
            for (Biome biome : this.pending) {
                ResourceKey rk = biome.resourceKey();
                net.minecraft.world.level.biome.Biome nms = (net.minecraft.world.level.biome.Biome) builder.buildDelegate(biome);

                DataResult<JsonElement> result = net.minecraft.world.level.biome.Biome.DIRECT_CODEC.encodeStart(ops, nms);
                if (result.error().isPresent()) {
                    throw new IllegalStateException("Biome codec encode failed for "
                        + rk.namespace() + ":" + rk.path() + " -> " + result.error().get().message());
                }
                JsonElement json = result.result().orElseThrow();

                Path biomeFile = root.resolve("data")
                    .resolve(rk.namespace())
                    .resolve("worldgen")
                    .resolve("biome")
                    .resolve(rk.path() + ".json");
                Files.createDirectories(biomeFile.getParent());
                Files.writeString(biomeFile, GSON.toJson(json));

                KeyChains.BIOMES.append(biome);
            }
        } finally {
            BootstrapSafeMinecraftRegistries.clearActive();
            DatapackPromotion.end();
        }

        writeDimensionOverrides(root);
        return root;
    }

    private void writeDimensionOverrides(Path root) throws Exception {
        if (this.dimensionEdits.isEmpty()) {
            return;
        }

        Map<ResourceKey, List<LevelBiomeEdit>> byDimension = new LinkedHashMap<>();
        for (LevelBiomeEdit edit : this.dimensionEdits) {
            byDimension.computeIfAbsent(edit.dimension(), k -> new ArrayList<>()).add(edit);
        }

        for (Map.Entry<ResourceKey, List<LevelBiomeEdit>> entry : byDimension.entrySet()) {
            ResourceKey dimension = entry.getKey();
            JsonObject dimensionJson = buildDimensionJson(dimension, entry.getValue());

            Path file = root.resolve("data")
                .resolve(dimension.namespace())
                .resolve("dimension")
                .resolve(dimension.path() + ".json");
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(dimensionJson));
        }
    }

    private JsonObject buildDimensionJson(ResourceKey dimension, List<LevelBiomeEdit> edits) {
        // start from the reconstructed vanilla source, then apply this dimension's edits
        List<Pair<Climate.ParameterPoint, net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>>> base = DimensionSources.vanillaPairs(dimension);
        List<Pair<Climate.ParameterPoint, net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>>> pairs = DimensionSources.applyEdits(base, edits);
        DimensionSources.DimensionDefaults defaults = DimensionSources.defaultsFor(dimension);

        JsonArray biomes = new JsonArray();
        for (Pair<Climate.ParameterPoint, net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>> pair : pairs) {
            JsonObject biomeEntry = new JsonObject();
            biomeEntry.addProperty("biome", pair.getSecond().identifier().toString());

            DataResult<JsonElement> encoded = Climate.ParameterPoint.CODEC.encodeStart(JsonOps.INSTANCE, pair.getFirst());
            biomeEntry.add("parameters", encoded.result().orElseThrow());
            biomes.add(biomeEntry);
        }

        JsonObject biomeSource = new JsonObject();
        biomeSource.addProperty("type", "minecraft:multi_noise");
        biomeSource.add("biomes", biomes);

        JsonObject generator = new JsonObject();
        generator.addProperty("type", "minecraft:noise");
        generator.addProperty("settings", defaults.noiseSettings()); // per dimension noiseSettings settings
        generator.add("biome_source", biomeSource);

        JsonObject dimensionJson = new JsonObject();
        dimensionJson.addProperty("type", defaults.dimensionType()); // per dimension dimension type
        dimensionJson.add("generator", generator);
        return dimensionJson;
    }

    /**
     * Server-data pack_format int for the running version. On this version
     * {@code WorldVersion.datapackVersion()} returns a {@code PackFormat} object; we read its
     * (first no-arg) int accessor, which is the major/format number written into pack.mcmeta.
     */
    public static int serverDataPackFormat() {
        try {
            Object version = SharedConstants.getCurrentVersion();
            Object packFormat = version.getClass().getMethod("datapackVersion").invoke(version);

            for (var m : packFormat.getClass().getMethods()) {
                if (m.getParameterCount() == 0
                    && (m.getReturnType() == int.class || m.getReturnType() == Integer.class)
                    && !m.getName().equals("hashCode")) {
                    return ((Number) m.invoke(packFormat)).intValue();
                }
            }
            throw new IllegalStateException("PackFormat exposes no int accessor: " + packFormat.getClass().getName());
        } catch (Throwable t) {
            throw new IllegalStateException("Could not determine server data pack_format", t);
        }
    }
}