package dev.wyck.registry.level;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.level.LevelType;
import dev.wyck.level.entity.LevelSpawner;
import dev.wyck.util.internal.InternalReflectUtil;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.CraftBukkitChunkGenerator;
import dev.wyck.worldgen.chunk.CraftBukkitChunkGeneratorImpl;
import io.papermc.paper.FeatureHooks;
import io.papermc.paper.world.PaperWorldLoader;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
        net.minecraft.resources.ResourceKey<LevelStem> stemKey = net.minecraft.resources.ResourceKey.create(Registries.LEVEL_STEM, levelId);
        net.minecraft.resources.ResourceKey<Level> dimensionKey = net.minecraft.resources.ResourceKey.create(Registries.DIMENSION, stemKey.identifier());


        LevelStem stem = world.levelStem().asHandle();

        if (world.persistence() == dev.wyck.level.StemPersistence.PERSISTENT) {
            world.levelStem().register();
        }

        Preconditions.checkArgument(server.getWorld(levelKey.namespace() + ":" + levelKey.path()) == null || server.getWorld(levelId.getPath()) == null, "world already loaded under %s", levelKey);

        String name = world.name();
        PaperWorldLoader.LoadedWorldData loadedWorldData = PaperWorldLoader.loadWorldData(minecraftServer, dimensionKey, name);
        PrimaryLevelData primaryLevelData = (PrimaryLevelData) minecraftServer.getWorldData();

        WorldLoader.DataLoadContext context = minecraftServer.worldLoaderContext;
        WorldOptions worldOptions = new WorldOptions(world.seed(), world.generateStructures(), world.bonusChest());

        WorldDimensions baseDimensions = presetDimensions(context, world.type());
        WorldDimensions worldDimensions = withStem(baseDimensions, stemKey, persistableStem(stem, world.levelStem().generator()));

        Registry<LevelStem> stemRegistry = context.datapackDimensions().lookupOrThrow(Registries.LEVEL_STEM);
        WorldDimensions.Complete complete = worldDimensions.bake(stemRegistry); // TODO
        WorldGenSettings genSettings = new WorldGenSettings(worldOptions, worldDimensions);

        long biomeZoomSeed = BiomeManager.obfuscateSeed(genSettings.options().seed());

        SavedDataStorage savedDataStorage = new SavedDataStorage(
            minecraftServer.storageSource.getDimensionPath(dimensionKey).resolve(LevelResource.DATA.id()),
            minecraftServer.getFixerUpper(),
            minecraftServer.registryAccess()
        );
        savedDataStorage.set(WorldGenSettings.TYPE, genSettings);

        List<CustomSpawner> spawners = new ArrayList<>();
        for (LevelSpawner spawner : world.spawners()) {
            spawners.add((CustomSpawner) spawner.toMinecraft(savedDataStorage));
        }

        //noinspection DataFlowIssue
        ServerLevel serverLevel = new ServerLevel(
            minecraftServer,
            InternalReflectUtil.getFieldValue(minecraftServer, "executor"),
            minecraftServer.storageSource,
            genSettings,
            dimensionKey,
            stem,
            primaryLevelData.isDebugWorld(),
            biomeZoomSeed,
            spawners,
            true,
            stemKey,
            world.environment(),
            null,
            null, // nullable here and up there too ^
            savedDataStorage,
            loadedWorldData
        );

        // shitty bukkit hacks
        if (world.levelStem().generator() instanceof CraftBukkitChunkGeneratorImpl craftBukkitChunkGenerator) {
            craftBukkitChunkGenerator.bindLevel(serverLevel);
        }

        minecraftServer.addLevel(serverLevel);
        minecraftServer.initWorld(serverLevel, null);
        serverLevel.setSpawnSettings(true);
        FeatureHooks.tickEntityManager(serverLevel);
        minecraftServer.prepareLevel(serverLevel);

        return serverLevel.getWorld();
    }

    private static LevelStem persistableStem(LevelStem stem, ChunkGenerator generator) {
        if (generator instanceof CraftBukkitChunkGenerator craftBukkit) {
            return new LevelStem(stem.type(), craftBukkit.delegate().asHandle());
        }
        return stem;
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

    private static WorldDimensions withStem(WorldDimensions base, net.minecraft.resources.ResourceKey<LevelStem> key, LevelStem stem) {
        var builder = ImmutableMap.<net.minecraft.resources.ResourceKey<LevelStem>, LevelStem>builder();
        builder.putAll(base.dimensions());
        builder.put(key, stem);
        return new WorldDimensions(builder.buildKeepingLast());
    }
}