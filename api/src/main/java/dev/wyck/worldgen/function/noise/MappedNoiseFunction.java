package dev.wyck.worldgen.function.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A noise function that maps the output of another noise function to a range, probably.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MappedNoiseFunction extends NoiseFunction {

    /**
     * The minimum target.
     * @return the minimum target
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double minTarget();

    /**
     * The maximum target.
     * @return the maximum target
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double maxTarget();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new mapped noise function.
     * @param resourceKey the resource key, or null
     * @param noiseParameters the noise parameters
     * @param xzScale the xz scale
     * @param yScale the y scale
     * @param minTarget the minimum target value
     * @param maxTarget the maximum target value
     * @return a new mapped noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedNoiseFunction of(@Nullable ResourceKey resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        record Holder() {
            static final ConstructWireProvider<MappedNoiseFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.noise.MappedNoiseFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), noiseParameters, xzScale, yScale, minTarget, maxTarget);
    }

    /**
     * Creates a new mapped noise function.
     * @param noiseParameters the noise parameters
     * @param xzScale the xz scale
     * @param yScale the y scale
     * @param minTarget the minimum target value
     * @param maxTarget the maximum target value
     * @return a new mapped noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedNoiseFunction of(NoiseParameters noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        return of(null, noiseParameters, xzScale, yScale, minTarget, maxTarget);
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
     * A builder for {@link MappedNoiseFunction}. Extends {@link NoiseFunction.AbstractBuilder} so it
     * inherits the shared noise parameters and scale setters while adding the target range.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends NoiseFunction.AbstractBuilder<Builder> {
        private double minTarget;
        private double maxTarget;

        private Builder() {}

        private Builder(MappedNoiseFunction function) {
            super(function);
            this.minTarget = function.minTarget();
            this.maxTarget = function.maxTarget();
        }

        /**
         * Sets the minimum target value for the mapped noise function.
         * @param minTarget the minimum target value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minTarget(double minTarget) {
            this.minTarget = minTarget;
            return this;
        }

        /**
         * Sets the maximum target value for the mapped noise function.
         * @param maxTarget the maximum target value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxTarget(double maxTarget) {
            this.maxTarget = maxTarget;
            return this;
        }

        /**
         * Builds the mapped noise function.
         * @return a new instance of MappedNoiseFunction
         * @since 3.0.0
         */
        @Override
        @AsOf("3.0.0")
        public MappedNoiseFunction build() {
            Preconditions.checkNotNull(noiseParameters, "noiseParameters must be set");
            return of(resourceKey, noiseParameters, xzScale, yScale, minTarget, maxTarget);
        }
    }
}
