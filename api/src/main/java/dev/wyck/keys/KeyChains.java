package dev.wyck.keys;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;

/**
 * Objects kept in memory by this implementation of Wyck.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
@ApiStatus.NonExtendable
public interface KeyChains {

    @AsOf("2.4.0")
    KeyChain<Biome> BIOMES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<Dimension> DIMENSIONS = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<ConfiguredFeature> CONFIGURED_FEATURES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<PlacedFeature> PLACED_FEATURES = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<DensityFunction> DENSITY_FUNCTIONS = KeyChain.mutable();

    @AsOf("2.4.0")
    KeyChain<NoiseParameters> NOISE = KeyChain.mutable();

    /**
     * @return {@link #BIOMES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<Biome> biomes() {
        return BIOMES;
    }

    /**
     * @return {@link #DIMENSIONS}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<Dimension> dimensions() {
        return DIMENSIONS;
    }

    /**
     * @return {@link #CONFIGURED_FEATURES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<ConfiguredFeature> configuredFeatures() {
        return CONFIGURED_FEATURES;
    }

    /**
     * @return {@link #PLACED_FEATURES}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<PlacedFeature> placedFeatures() {
        return PLACED_FEATURES;
    }

    /**
     * @return {@link #DENSITY_FUNCTIONS}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<DensityFunction> densityFunctions() {
        return DENSITY_FUNCTIONS;
    }

    /**
     * @return {@link #NOISE}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static KeyChain<NoiseParameters> noise() {
        return NOISE;
    }

}
