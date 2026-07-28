package dev.wyck.decode.worldgen.chunk;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.biome.FixedBiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.DebugLevelSource;
import dev.wyck.worldgen.chunk.FlatLevelSource;
import dev.wyck.worldgen.chunk.NoiseBasedChunkGenerator;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

@ApiStatus.Internal
public final class ChunkGeneratorDecoders extends DecoderRegistry<ChunkGenerator, net.minecraft.world.level.chunk.ChunkGenerator> {

    public ChunkGeneratorDecoders() {
        register("noise", object -> {
            net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator generator =
                (net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator) object;
            return NoiseBasedChunkGenerator.of(
                BiomeSource.decode(generator.getBiomeSource()),
                Noise.decode(generator.generatorSettings())
            );
        });
        register("flat", object -> {
            net.minecraft.world.level.levelgen.FlatLevelSource generator =
                (net.minecraft.world.level.levelgen.FlatLevelSource) object;
            return FlatLevelSource.of(
                BiomeSource.decode(generator.getBiomeSource()),
                FlatLevelGeneratorSettings.decode(generator.settings())
            );
        });
        register("debug", object -> {
            net.minecraft.world.level.chunk.ChunkGenerator generator = (net.minecraft.world.level.chunk.ChunkGenerator) object;
            FixedBiomeSource source = (FixedBiomeSource) BiomeSource.decode(generator.getBiomeSource());
            return DebugLevelSource.of(source.biome());
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.chunk.@NonNull ChunkGenerator minecraftObject) {
        Object codec = FastReflection.call(minecraftObject, "codec");
        return Decoders.registryKey(BuiltInRegistries.CHUNK_GENERATOR, (com.mojang.serialization.MapCodec) codec);
    }
}
