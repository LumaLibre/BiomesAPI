package dev.wyck.v1_21_11.registry.level;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Lifecycle;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.level.LevelType;
import dev.wyck.level.entity.LevelSpawner;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.CraftBukkitChunkGenerator;
import dev.wyck.worldgen.chunk.CraftBukkitChunkGeneratorImpl;
import io.papermc.paper.FeatureHooks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.validation.ContentValidationException;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class SimpleLevelFactory implements LevelFactory {

    @Override
    @SuppressWarnings("removal")
    public World createWorld(LevelCreator world) {
        Preconditions.checkNotNull(world, "world cannot be null");

        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer minecraftServer = server.getServer();

        ResourceKey levelKey = world.resourceKey();
        Identifier levelId = (Identifier) levelKey.toMinecraft();
        String name = world.name();

        net.minecraft.resources.ResourceKey<LevelStem> stemKey = net.minecraft.resources.ResourceKey.create(Registries.LEVEL_STEM, levelId);
        net.minecraft.resources.ResourceKey<Level> dimensionKey = net.minecraft.resources.ResourceKey.create(Registries.DIMENSION, stemKey.identifier());

        LevelStem stem = world.levelStem().asHandle();

        if (world.persistence() == dev.wyck.level.StemPersistence.PERSISTENT) {
            world.levelStem().register();
        }

        NamespacedKey bukkitKey = new NamespacedKey(levelKey.namespace(), levelKey.path());
        Preconditions.checkArgument(
            Bukkit.getWorld(bukkitKey) == null && Bukkit.getWorld(name) == null,
            "world already loaded under %s", levelKey);

        net.minecraft.resources.ResourceKey<LevelStem> storageDimension = switch (world.environment()) {
            case NETHER -> LevelStem.NETHER;
            case THE_END -> LevelStem.END;
            default -> LevelStem.OVERWORLD;
        };

        LevelStorageSource.LevelStorageAccess levelStorageAccess;
        try {
            levelStorageAccess = LevelStorageSource.createDefault(server.getWorldContainer().toPath())
                .validateAndCreateAccess(name, storageDimension);
        } catch (IOException | ContentValidationException e) {
            throw new RuntimeException(e);
        }

        WorldLoader.DataLoadContext context = minecraftServer.worldLoaderContext;
        WorldOptions worldOptions = new WorldOptions(world.seed(), world.generateStructures(), world.bonusChest());

        WorldDimensions baseDimensions = presetDimensions(context, world.type());
        WorldDimensions worldDimensions = withStem(baseDimensions, stemKey, persistableStem(stem, world.generator()));

        WorldDimensions.Complete complete = worldDimensions.bake(
            context.datapackDimensions().lookupOrThrow(Registries.LEVEL_STEM));
        Lifecycle lifecycle = complete.lifecycle().add(context.datapackWorldgen().allRegistriesLifecycle());

        LevelSettings levelSettings = new LevelSettings(
            name,
            minecraftServer.getDefaultGameType(),
            false,
            Difficulty.NORMAL,
            false,
            new GameRules(context.dataConfiguration().enabledFeatures()),
            context.dataConfiguration());

        PrimaryLevelData primaryLevelData = new PrimaryLevelData(
            levelSettings, worldOptions, complete.specialWorldProperty(), lifecycle);

        RegistryAccess.Frozen dimensionsRegistryAccess = complete.dimensionsRegistryAccess();
        primaryLevelData.customDimensions = dimensionsRegistryAccess.lookupOrThrow(Registries.LEVEL_STEM);
        primaryLevelData.checkName(name);
        primaryLevelData.setModdedInfo(minecraftServer.getServerModName(), minecraftServer.getModdedStatus().shouldReportAsModified());

        long biomeZoomSeed = BiomeManager.obfuscateSeed(primaryLevelData.worldGenOptions().seed());

        List<CustomSpawner> spawners = new ArrayList<>();
        for (LevelSpawner spawner : world.spawners()) {
            spawners.add((CustomSpawner) spawner.toMinecraft(primaryLevelData));
        }

        //noinspection DataFlowIssue
        ServerLevel serverLevel = new ServerLevel(
            minecraftServer,
            minecraftServer.executor,
            levelStorageAccess,
            primaryLevelData,
            dimensionKey,
            stem,
            primaryLevelData.isDebugWorld(),
            biomeZoomSeed,
            spawners,
            true,
            null,
            world.environment(),
            null,
            null
        );

        if (world.generator() instanceof CraftBukkitChunkGeneratorImpl craftBukkitChunkGenerator) {
            craftBukkitChunkGenerator.bindLevel(serverLevel);
        }

        minecraftServer.addLevel(serverLevel);
        minecraftServer.initWorld(serverLevel, primaryLevelData, worldOptions);
        serverLevel.setSpawnSettings(true);
        FeatureHooks.tickEntityManager(serverLevel);
        minecraftServer.prepareLevel(serverLevel);

        return serverLevel.getWorld();
    }

    // TODO: just wrap WorldPreset
    private static WorldDimensions presetDimensions(WorldLoader.DataLoadContext context, LevelType type) {
        HolderLookup.RegistryLookup<WorldPreset> presets = context.datapackWorldgen().lookupOrThrow(Registries.WORLD_PRESET);
        return Optional.ofNullable(Identifier.tryParse(type.getKey()))
            .map(id -> net.minecraft.resources.ResourceKey.create(Registries.WORLD_PRESET, id))
            .flatMap(presets::get)
            .or(() -> presets.get(WorldPresets.NORMAL))
            .orElseThrow(() -> new IllegalStateException("Invalid datapack contents: can't find world preset " + type.getKey()))
            .value()
            .createWorldDimensions();
    }

    private static LevelStem persistableStem(LevelStem stem, ChunkGenerator generator) {
        if (generator instanceof CraftBukkitChunkGenerator craftBukkit) {
            return new LevelStem(stem.type(), craftBukkit.delegate().asHandle());
        }
        return stem;
    }

    private static WorldDimensions withStem(WorldDimensions base, net.minecraft.resources.ResourceKey<LevelStem> key, LevelStem stem) {
        var builder = ImmutableMap.<net.minecraft.resources.ResourceKey<LevelStem>, LevelStem>builder();
        builder.putAll(base.dimensions());
        builder.put(key, stem);
        return new WorldDimensions(builder.buildKeepingLast());
    }
}