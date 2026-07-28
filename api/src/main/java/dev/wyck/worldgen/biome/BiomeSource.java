package dev.wyck.worldgen.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a biome source, which determines how biomes are distributed in a world.
 *
 * @see <a href="https://minecraft.wiki/w/Dimension_definition#Biome_sources">Dimension definition (biome sources)</a>
 * @since 2.4.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface BiomeSource extends Wrapper {

    @ApiStatus.Internal
    Decoder<BiomeSource> DECODER = Decoder.create("dev.wyck.decode.worldgen.biome.BiomeSourceDecoders");

    /**
     * Creates a biome source that always returns the specified biome.
     * @param biome the biome to return
     * @return a biome source that always returns the specified biome
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static FixedBiomeSource fixed(Biome biome) {
        return FixedBiomeSource.of(biome);
    }

    /**
     * Creates a biome source that always returns the specified biome.
     * @param biomeKey the biome to return
     * @return a biome source that always returns the specified biome
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static FixedBiomeSource fixed(ResourceKey biomeKey) {
        return FixedBiomeSource.of(biomeKey);
    }

    /**
     * Creates a multi-noise biome source.
     * @return a new multi-noise biome source builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static MultiNoiseBiomeSource.Builder multiNoise() {
        return MultiNoiseBiomeSource.builder();
    }

    /**
     * Biome source preset for the overworld.
     * @return the overworld biome source preset
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static MultiNoisePresetBiomeSource overworld() {
        return MultiNoisePresetBiomeSource.OVERWORLD;
    }

    /**
     * Biome source preset for the nether.
     * @return the nether biome source preset
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static MultiNoisePresetBiomeSource nether() {
        return MultiNoisePresetBiomeSource.NETHER;
    }

    /**
     * Biome source preset for the end.
     * @return the end biome source preset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CheckeredColumnBiomeSource.Builder checkeredColumn() {
        return CheckeredColumnBiomeSource.builder();
    }

    /**
     * The end biome source.
     * @return the end biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TheEndBiomeSource theEnd() {
        return TheEndBiomeSource.INSTANCE;
    }

    /**
     * Reads a Minecraft biome source.
     * @param minecraftSource the biome source to read
     * @return the decoded biome source
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BiomeSource decode(Object minecraftSource) {
        return DECODER.decode(minecraftSource);
    }
}
