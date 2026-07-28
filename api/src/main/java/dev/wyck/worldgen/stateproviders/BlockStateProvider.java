package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Used to provide a (potentially randomized) block and block state to be placed by configured features.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider">Block state provider</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface BlockStateProvider extends Wrapper {

    @ApiStatus.Internal
    Decoder<BlockStateProvider> DECODER = Decoder.create("dev.wyck.decode.worldgen.stateproviders.BlockStateProviderDecoders");

    /**
     * Creates a simple block state provider.
     * @param state the block state
     * @return a simple block state provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static SimpleStateProvider simple(BlockData state) {
        return SimpleStateProvider.of(state);
    }

    /**
     * Creates a simple block state provider.
     * @param material the material of the block
     * @return a simple block state provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static SimpleStateProvider simple(Material material) {
        return SimpleStateProvider.of(material);
    }

    /**
     * Creates a rotated block provider.
     * @param material the material of the block
     * @return a rotated block provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RotatedBlockProvider rotated(Material material) {
        return RotatedBlockProvider.of(material);
    }

    /**
     * Creates a builder for a weighted state provider.
     * @return a new weighted state provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static WeightedStateProvider.Builder weighted() {
        return WeightedStateProvider.builder();
    }

    /**
     * Creates a builder for a randomized int state provider.
     * @return a new randomized int state provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RandomizedIntStateProvider.Builder randomizedInt() {
        return RandomizedIntStateProvider.builder();
    }

    /**
     * Creates a builder for a noise provider.
     * @return a new noise provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static NoiseProvider.Builder noise() {
        return NoiseProvider.builder();
    }

    /**
     * Creates a builder for a rule based state provider.
     * @return a new rule based state provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RuleBasedStateProvider.Builder ruleBased() {
        return RuleBasedStateProvider.builder();
    }

    /**
     * Creates a builder for a dual-noise provider.
     * @return a new dual-noise provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static DualNoiseProvider.Builder dualNoise() {
        return DualNoiseProvider.builder();
    }

    /**
     * Creates a builder for a noise threshold provider.
     * @return a new noise threshold provider builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static NoiseThresholdProvider.Builder noiseThreshold() {
        return NoiseThresholdProvider.builder();
    }

    /**
     * Reads a Minecraft block state provider into a wrapper.
     * @param minecraftProvider the Minecraft provider to read
     * @return the wrapper for the provider
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BlockStateProvider decode(Object minecraftProvider) {
        return DECODER.decode(minecraftProvider);
    }

}
