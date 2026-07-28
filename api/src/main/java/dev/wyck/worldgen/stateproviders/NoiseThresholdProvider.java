package dev.wyck.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses different block states when a noise value above or below the threshold.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#noise_threshold_provider">Block state provider (noise threshold provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseThresholdProvider extends NoiseBasedProvider {

    /**
     * The threshold of the noise value between -1.0 and 1.0.
     * If the noise value is lower than this value, the block states in {@link #lowStates()} will be selected.
     * @return the threshold of the noise value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float threshold();

    /**
     * If the noise value is higher than the threshold, the block states
     * in {@link #highStates()} will be selected with a probability of this value.
     * @return the high chance of the noise value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float highChance();

    /**
     * The default block state. This value is used when the noise value is higher than
     * the threshold but {@link #highStates()} is not selected according to {@link #highChance()}.
     * @return the default block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData defaultState();

    /**
     * List of block states to choose from when lower than threshold.
     * This cannot be empty.
     * @return the list of block states
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockData> lowStates();

    /**
     * List of block states to choose from when higher than threshold.
     * This cannot be empty.
     * @return the list of block states
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockData> highStates();

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
     * Creates a new noise threshold provider.
     * @param seed the seed of the noise
     * @param parameters the parameters of the noise
     * @param scale the scale of the noise
     * @param threshold the threshold of the noise
     * @param highChance the high chance of the noise
     * @param defaultState the default block state
     * @param lowStates the list of block states to choose from when lower than threshold
     * @param highStates the list of block states to choose from when higher than threshold
     * @return a new noise threshold provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdProvider of(long seed, NoiseParameters parameters, float scale, float threshold, float highChance, BlockData defaultState, List<BlockData> lowStates, List<BlockData> highStates) {
        record Holder() {
            static final ConstructWireProvider<NoiseThresholdProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.NoiseThresholdProviderImpl");
        }
        return Holder.WIRE.construct(seed, parameters, scale, threshold, highChance, defaultState, lowStates, highStates);
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
     * A builder for {@link NoiseThresholdProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends AbstractNoiseProviderBuilder<Builder, NoiseThresholdProvider> {
        private float threshold;
        private float highChance;
        private @Nullable BlockData defaultState;
        private List<BlockData> lowStates = new ArrayList<>();
        private List<BlockData> highStates = new ArrayList<>();

        public Builder() {}

        public Builder(NoiseThresholdProvider provider) {
            super(provider);
            this.threshold = provider.threshold();
            this.highChance = provider.highChance();
            this.defaultState = provider.defaultState();
            this.lowStates.addAll(provider.lowStates());
            this.highStates.addAll(provider.highStates());
        }

        /**
         * Sets the threshold of the noise value.
         * @param threshold the threshold of the noise value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder threshold(float threshold) {
            this.threshold = threshold;
            return this;
        }

        /**
         * Sets the high chance.
         * @param highChance the high chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder highChance(float highChance) {
            this.highChance = highChance;
            return this;
        }

        /**
         * Sets the default block state.
         * @param defaultState the default block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder defaultState(BlockData defaultState) {
            this.defaultState = defaultState;
            return this;
        }

        /**
         * Sets the low-threshold block states.
         * @param lowStates the low-threshold block states
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lowStates(List<BlockData> lowStates) {
            this.lowStates = lowStates;
            return this;
        }

        /**
         * Sets the high-threshold block states.
         * @param highStates the high-threshold block states
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder highStates(List<BlockData> highStates) {
            this.highStates = highStates;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a low-threshold block state.
         * @param lowStates the low-threshold block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lowState(BlockData... lowStates) {
            this.lowStates.addAll(List.of(lowStates));
            return this;
        }

        /**
         * Adds a high-threshold block state.
         * @param highStates the high-threshold block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder highState(BlockData... highStates) {
            this.highStates.addAll(List.of(highStates));
            return this;
        }

        @Override
        protected NoiseThresholdProvider create() {
            Preconditions.checkArgument(threshold >= -1.0F && threshold <= 1.0F, "threshold must be between -1.0 and 1.0");
            Preconditions.checkArgument(highChance >= 0.0F && highChance <= 1.0F, "highChance must be between 0.0 and 1.0");
            Preconditions.checkNotNull(defaultState, "defaultState must be set");
            Preconditions.checkArgument(!lowStates.isEmpty(), "lowStates must not be empty");
            Preconditions.checkArgument(!highStates.isEmpty(), "highStates must not be empty");
            //noinspection ConstantConditions
            return of(seed, parameters, scale, threshold, highChance, defaultState, lowStates, highStates);
        }
    }

}
