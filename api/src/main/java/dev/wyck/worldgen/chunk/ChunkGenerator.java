package dev.wyck.worldgen.chunk;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.4.0")
public interface ChunkGenerator extends Wrapper {

    @ApiStatus.Internal
    Decoder<ChunkGenerator> DECODER = Decoder.create("dev.wyck.decode.worldgen.chunk.ChunkGeneratorDecoders");

    /**
     * The biome source this generator draws from.
     * @return the biome source
     * @since 2.5.0
     */
    @AsOf("2.5.0")
    BiomeSource biomeSource();

    /**
     * Creates a new noise-based chunk generator.
     * @return a new noise-based chunk generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseBasedChunkGenerator.Builder noise() {
        return NoiseBasedChunkGenerator.builder();
    }

    /**
     * Creates a new flat chunk generator.
     * @return a new flat chunk generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLevelSource.Builder flat() {
        return FlatLevelSource.builder();
    }

    /**
     * Creates a new debug chunk generator.
     * @param biome the biome
     * @return a new debug chunk generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DebugLevelSource debug(Biome biome) {
        return DebugLevelSource.of(biome);
    }

    /**
     * Creates a new builder for a {@link CraftBukkitChunkGenerator}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CraftBukkitChunkGenerator.Builder craftBukkit() {
        return CraftBukkitChunkGenerator.builder();
    }

    /**
     * Reads a Minecraft chunk generator.
     * @param minecraftGenerator the chunk generator to read
     * @return the decoded chunk generator
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ChunkGenerator decode(Object minecraftGenerator) {
        return DECODER.decode(minecraftGenerator);
    }
}
