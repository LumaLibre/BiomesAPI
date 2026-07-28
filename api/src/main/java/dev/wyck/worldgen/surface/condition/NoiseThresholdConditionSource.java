package dev.wyck.worldgen.surface.condition;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Computes the noise value of the current column using a specified noise and checks if it is between
 * the min and max threshold.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseThresholdConditionSource extends ConditionSource {

    @ApiStatus.Internal
    ConstructWireProvider<NoiseThresholdConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.surface.condition.NoiseThresholdConditionSourceImpl");

    /**
     * The key of the noise to sample.
     * @return the noise key
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey noise();

    /**
     * The minimum noise value where the condition passes.
     * @return the minimum threshold
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double minThreshold();

    /**
     * The maximum noise value where the condition passes.
     * @return the maximum threshold
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double maxThreshold();

    /**
     * Whether this condition samples three-dimensional noise.
     * @return whether three-dimensional noise is sampled
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    boolean is3d();

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
     * Creates a new noise threshold condition source.
     * @param noise the key of the noise to sample
     * @param minThreshold the minimum noise value where the condition passes
     * @param maxThreshold the maximum noise value where the condition passes
     * @return the noise threshold condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdConditionSource of(ResourceKey noise, double minThreshold, double maxThreshold) {
        return of(noise, minThreshold, maxThreshold, false);
    }

    /**
     * Creates a noise threshold condition with an explicit sampling dimensionality.
     * @param noise the noise parameters to sample
     * @param minThreshold the inclusive minimum threshold
     * @param maxThreshold the inclusive maximum threshold
     * @param is3d whether to sample three-dimensional noise
     * @return a new noise threshold condition
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static NoiseThresholdConditionSource of(ResourceKey noise, double minThreshold, double maxThreshold, boolean is3d) {
        return WIRE.construct(noise, minThreshold, maxThreshold, is3d);
    }

    /**
     * Creates a new noise threshold condition source with no upper bound.
     * @param noise the key of the noise to sample
     * @param minThreshold the minimum noise value where the condition passes
     * @return the noise threshold condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdConditionSource of(ResourceKey noise, double minThreshold) {
        return of(noise, minThreshold, Double.MAX_VALUE);
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
     * Builder for {@link NoiseThresholdConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey noise;
        private double minThreshold;
        private double maxThreshold = Double.MAX_VALUE;
        private boolean is3d;

        private Builder() {}

        private Builder(NoiseThresholdConditionSource source) {
            this.noise = source.noise();
            this.minThreshold = source.minThreshold();
            this.maxThreshold = source.maxThreshold();
            this.is3d = source.is3d();
        }

        /**
         * Sets the key of the noise to sample.
         * @param noise the noise key
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noise(ResourceKey noise) {
            this.noise = noise;
            return this;
        }

        /**
         * Sets the minimum noise value where the condition passes.
         * @param minThreshold the minimum threshold
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minThreshold(double minThreshold) {
            this.minThreshold = minThreshold;
            return this;
        }

        /**
         * Sets the maximum noise value where the condition passes.
         * @param maxThreshold the maximum threshold
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxThreshold(double maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        /**
         * Sets whether this condition samples three-dimensional noise.
         * @param is3d whether to sample three-dimensional noise
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder is3d(boolean is3d) {
            this.is3d = is3d;
            return this;
        }

        /**
         * Builds the noise threshold condition source.
         * @return the noise threshold condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NoiseThresholdConditionSource build() {
            Preconditions.checkNotNull(noise, "noise must be set");
            return of(noise, minThreshold, maxThreshold, is3d);
        }
    }
}
