package dev.wyck.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.util.InclusiveRange;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Randomly chooses a block state according to two noise values.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#dual_noise_provider">Block state provider (dual noise provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DualNoiseProvider extends NoiseBasedProvider {

    /**
     * The number of block states that selected out by slow noise.
     * @return the variety of the noise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    InclusiveRange<Integer> variety();

    /**
     * The noise used for the first selection.
     * @return the parameters of the noise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    NoiseParameters slowNoise(); // vanilla-name: slowNoiseParameters

    /**
     * Horizontal scale of the slow noise.
     * Must be a positive value.
     * @return the scale of the slow noise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float slowScale();

    /**
     * The list of block states.
     * This cannot be empty.
     * @return the list of block states
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockData> states();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new dual-noise provider.
     * @param seed the seed of the noise
     * @param parameters the parameters of the noise
     * @param scale the scale of the noise
     * @param variety the variety of the noise
     * @param slowNoiseParameters the parameters of the slow noise
     * @param slowScale the scale of the slow noise
     * @param states the list of block states
     * @return a new dual-noise provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DualNoiseProvider of(long seed, NoiseParameters parameters, float scale, InclusiveRange<Integer> variety, NoiseParameters slowNoiseParameters, float slowScale, List<BlockData> states) {
        record Holder() {
            static final ConstructWireProvider<DualNoiseProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.DualNoiseProviderImpl");
        }
        return Holder.WIRE.construct(seed, parameters, scale, variety, slowNoiseParameters, slowScale, states);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DualNoiseProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends AbstractNoiseProviderBuilder<Builder, DualNoiseProvider> {
        private @Nullable InclusiveRange<Integer> variety;
        private @Nullable NoiseParameters slowNoiseParameters;
        private float slowScale;
        private List<BlockData> states = new ArrayList<>();

        public Builder() {}

        public Builder(DualNoiseProvider provider) {
            super(provider);
            this.variety = provider.variety();
            this.slowNoiseParameters = provider.slowNoise();
            this.slowScale = provider.slowScale();
            this.states.addAll(provider.states());
        }

        /**
         * Sets the variety of the noise.
         * @param variety the variety of the noise
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder variety(InclusiveRange<Integer> variety) {
            this.variety = variety;
            return this;
        }

        /**
         * Sets the parameters of the slow noise.
         * @param slowNoiseParameters the parameters of the slow noise
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder slowNoiseParameters(NoiseParameters slowNoiseParameters) {
            this.slowNoiseParameters = slowNoiseParameters;
            return this;
        }

        /**
         * Sets the scale of the slow noise.
         * @param slowScale the scale of the slow noise
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder slowScale(float slowScale) {
            this.slowScale = slowScale;
            return this;
        }

        /**
         * Sets the list of optional block states.
         * @param states the list of block states
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder states(List<BlockData> states) {
            this.states = new ArrayList<>(states);
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a block state to the list of optional block states.
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.states.add(state);
            return this;
        }

        /**
         * Adds a block state to the list of optional block states.
         * @param material the material of the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.states.add(BukkitBootstrapUtil.util().createBlockData(material));
            return this;
        }

        @Override
        protected DualNoiseProvider create() {
            Preconditions.checkNotNull(variety, "variety must not be null");
            Preconditions.checkArgument(variety.minInclusive() >= 1 && variety.maxInclusive() <= 64, "variety must be between 1 and 64");
            Preconditions.checkNotNull(slowNoiseParameters, "slowNoiseParameters must not be null");
            Preconditions.checkArgument(slowScale > 0, "slowScale must be positive");
            Preconditions.checkArgument(!states.isEmpty(), "states must not be empty");
            //noinspection ConstantConditions
            return of(seed, parameters, scale, variety, slowNoiseParameters, slowScale, states);
        }
    }
}
