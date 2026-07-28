package dev.wyck.worldgen.function.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Wraps shifted noise 2d density functions.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ShiftedNoise2dFunction extends NoiseParameterFunction {

    /**
     * Shift x density function.
     * @return the shift x density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction shiftX();

    /**
     * Shift z density function.
     * @return the shift z density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction shiftZ();

    /**
     * Scale factor for x and z.
     * @return the scale factor
     * @since 3.0.0
     */
    double xzScale();

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
     * Creates a new shifted noise 2d density function.
     * @param resourceKey the resource key, or null
     * @param noiseParameters the noise parameters
     * @param shiftX the shift x density function
     * @param shiftZ the shift z density function
     * @param xzScale the scale factor for x and z
     * @return a new shifted noise 2d density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedNoise2dFunction of(@Nullable ResourceKey resourceKey, NoiseParameters noiseParameters, DensityFunction shiftX, DensityFunction shiftZ, double xzScale) {
        record Holder() {
            static final ConstructWireProvider<ShiftedNoise2dFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.noise.ShiftedNoise2dFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), noiseParameters, shiftX, shiftZ, xzScale);
    }

    /**
     * Creates a new shifted noise 2d density function.
     * @param noiseParameters the noise parameters
     * @param shiftX the shift x density function
     * @param shiftZ the shift z density function
     * @param xzScale the scale factor for x and z
     * @return a new shifted noise 2d density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedNoise2dFunction of(NoiseParameters noiseParameters, DensityFunction shiftX, DensityFunction shiftZ, double xzScale) {
        return of(null, noiseParameters, shiftX, shiftZ, xzScale);
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
     * Builder for {@link ShiftedNoise2dFunction}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable NoiseParameters noiseParameters;
        private @Nullable DensityFunction shiftX;
        private @Nullable DensityFunction shiftZ;
        private double xzScale = 1.0;

        private Builder() {}

        private Builder(ShiftedNoise2dFunction function) {
            this.resourceKey = function.resourceKey().orElse(null);
            this.noiseParameters = function.noiseParameters();
            this.shiftX = function.shiftX();
            this.shiftZ = function.shiftZ();
            this.xzScale = function.xzScale();
        }

        /**
         * Sets the resource key.
         * @param resourceKey the resource key
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder resourceKey(@Nullable ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the noise parameters.
         * @param noiseParameters the noise parameters
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseParameters(NoiseParameters noiseParameters) {
            this.noiseParameters = noiseParameters;
            return this;
        }

        /**
         * Sets the shift x density function.
         * @param shiftX the shift x density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder shiftX(DensityFunction shiftX) {
            this.shiftX = shiftX;
            return this;
        }

        /**
         * Sets the shift z density function.
         * @param shiftZ the shift z density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder shiftZ(DensityFunction shiftZ) {
            this.shiftZ = shiftZ;
            return this;
        }

        /**
         * Sets the scale factor for x and z.
         * @param xzScale the scale factor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder xzScale(double xzScale) {
            this.xzScale = xzScale;
            return this;
        }

        /**
         * Builds the shifted noise 2d density function.
         * @return the shifted noise 2d density function
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ShiftedNoise2dFunction build() {
            Preconditions.checkNotNull(noiseParameters, "noiseParameters must be set");
            Preconditions.checkNotNull(shiftX, "shiftX must be set");
            Preconditions.checkNotNull(shiftZ, "shiftZ must be set");
            return of(resourceKey, noiseParameters, shiftX, shiftZ, xzScale);
        }
    }
}
