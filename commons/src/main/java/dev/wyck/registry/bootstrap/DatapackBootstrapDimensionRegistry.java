package dev.wyck.registry.bootstrap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.DimensionRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.ThrowingRunnable;
import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.registrar.RegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.minecraft.resources.RegistryOps;
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


@NullMarked
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class DatapackBootstrapDimensionRegistry implements BootstrapDimensionRegistry {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String PACK_ID_FORMAT = "wyck_dimension_%s";

    private final List<Dimension> pending = new ArrayList<>();
    private boolean installed = false;
    private @Nullable String packId;
    private @Nullable Throwable deferred;

    @Override
    public BootstrapDimensionRegistry queue(Dimension dimension) {
        this.pending.add(dimension);
        return this;
    }

    @Override
    public BootstrapDimensionRegistry install(BootstrapContext context) {
        Preconditions.checkState(!this.installed, "already installed");
        this.installed = true;
        String name = context.getPluginMeta().getName().toLowerCase(Locale.ROOT).replace(' ', '-');
        this.packId = String.format(PACK_ID_FORMAT, name);

        context.getLifecycleManager().registerEventHandler(
            LifecycleEvents.DATAPACK_DISCOVERY.newHandler(this::discover)
        );
        return this;
    }

    @Override
    public BootstrapDimensionRegistry deferring(ThrowingRunnable action) {
        if (this.deferred != null) {
            return this;
        }
        try {
            action.run();
        } catch (Throwable t) {
            this.deferred = t;
        }
        return this;
    }

    private void discover(RegistrarEvent<DatapackRegistrar> event) {
        if (this.deferred != null) {
            throw new RuntimeException("Deferred custom dimension type failure; aborting startup", this.deferred);
        }
        if (this.pending.isEmpty()) {
            return;
        }
        try {
            Preconditions.checkNotNull(this.packId, "install() not called");
            Path packRoot = writePack();
            event.registrar().discoverPack(packRoot.toUri(), packId);
            this.pending.clear();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to register custom dimension types via datapack; aborting", t);
        }
    }

    private Path writePack() throws Exception {
        Path root = Files.createTempDirectory("wyck-dimension-pack-");

        int format = DatapackBootstrapBiomeRegistry.serverDataPackFormat();
        JsonArray formatRange = new JsonArray();
        formatRange.add(format);
        formatRange.add(0);

        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", format);
        pack.add("min_format", formatRange);
        pack.add("max_format", formatRange);
        pack.addProperty("description", "Wyck generated dimension types");
        JsonObject meta = new JsonObject();
        meta.add("pack", pack);
        Files.writeString(root.resolve("pack.mcmeta"), GSON.toJson(meta));

        DimensionRegistry builder = DimensionRegistry.registry();

        Map<Dimension, net.minecraft.world.level.dimension.DimensionType> built = new LinkedHashMap<>();
        for (Dimension dimension : this.pending) {
            built.put(dimension, (net.minecraft.world.level.dimension.DimensionType) builder.buildDelegate(dimension));
        }

        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization().createSerializationContext(JsonOps.INSTANCE);

        for (Map.Entry<Dimension, net.minecraft.world.level.dimension.DimensionType> entry : built.entrySet()) {
            Dimension dimension = entry.getKey();
            ResourceKey rk = dimension.resourceKey();

            DataResult<JsonElement> result =
                net.minecraft.world.level.dimension.DimensionType.DIRECT_CODEC.encodeStart(ops, entry.getValue());
            if (result.error().isPresent()) {
                throw new IllegalStateException("Dimension type codec encode failed for "
                    + rk.namespace() + ":" + rk.path() + " -> " + result.error().get().message());
            }
            JsonElement json = result.result().orElseThrow();

            Path file = root.resolve("data")
                .resolve(rk.namespace())
                .resolve("dimension_type")
                .resolve(rk.path() + ".json");
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(json));

            KeyChains.DIMENSIONS.append(dimension);
        }

        return root;
    }
}