package dev.wyck.test.bootstrap.decode;

import dev.wyck.decode.Decoders;
import dev.wyck.level.dimension.LevelStem;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MinecraftBootstrap.class)
class LevelStemDecodeTest {

    @Test
    void everyBiomeSourceAndChunkGeneratorTypeIsCovered() {
        var biomeSourceDecoders = new dev.wyck.decode.worldgen.biome.BiomeSourceDecoders();
        var chunkGeneratorDecoders = new dev.wyck.decode.worldgen.chunk.ChunkGeneratorDecoders();
        BuiltInRegistries.BIOME_SOURCE.keySet().forEach(key -> assertTrue(
            biomeSourceDecoders.handles(Decoders.key(key)),
            () -> "Missing biome source decoder for " + key
        ));
        BuiltInRegistries.CHUNK_GENERATOR.keySet().forEach(key -> assertTrue(
            chunkGeneratorDecoders.handles(Decoders.key(key)),
            () -> "Missing chunk generator decoder for " + key
        ));
    }

    @Test
    void everyVanillaDimensionTypeDecodesThroughAWholeLevelStem() {
        var dimensions = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DIMENSION_TYPE);
        var biomes = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        var noises = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS);
        var plains = biomes.get(net.minecraft.world.level.biome.Biomes.PLAINS).orElseThrow();
        var overworldNoise = noises.get(net.minecraft.world.level.levelgen.NoiseGeneratorSettings.OVERWORLD).orElseThrow();
        var generator = new net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator(
            new net.minecraft.world.level.biome.FixedBiomeSource(plains), overworldNoise);
        dimensions.entrySet().forEach(entry -> {
            var stem = new net.minecraft.world.level.dimension.LevelStem(
                dimensions.get(entry.getKey()).orElseThrow(), generator);
            LevelStem decoded = LevelStem.decode(stem);
            assertTrue(decoded.resourceKey().isEmpty());
            assertEquals(Decoders.key(entry.getKey().identifier()), decoded.dimension().resourceKey());
            assertNotNull(decoded.dimension());
            assertNotNull(decoded.chunkGenerator());
        });
    }

    @Test
    void flatAndDebugGeneratorsDecodeTheirLeafSettings() {
        var biomeGetter = BootstrapSafeMinecraftRegistries.getter(Registries.BIOME);
        var structureGetter = BootstrapSafeMinecraftRegistries.getter(Registries.STRUCTURE_SET);
        var placedGetter = BootstrapSafeMinecraftRegistries.getter(Registries.PLACED_FEATURE);

        var flatSettings = net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings.getDefault(
            biomeGetter, structureGetter, placedGetter);
        assertInstanceOf(dev.wyck.worldgen.chunk.FlatLevelSource.class,
            ChunkGenerator.decode(new net.minecraft.world.level.levelgen.FlatLevelSource(flatSettings)));

        var plains = biomeGetter.getOrThrow(net.minecraft.world.level.biome.Biomes.PLAINS);
        assertInstanceOf(dev.wyck.worldgen.chunk.DebugLevelSource.class,
            ChunkGenerator.decode(new net.minecraft.world.level.levelgen.DebugLevelSource(plains)));
    }
}
