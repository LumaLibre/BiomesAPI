package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Clamped normal samples a bell curve centered at mean with spread deviation,
 * then forces the result into [{@link #minInclusive()}, {@link #maxInclusive()}].
 * Anything outside gets snapped to the nearest bound rather than rerolled.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bell-shaped_function">Bell-shaped function</a>
 * @see <a href="https://minecraft.wiki/w/Configured_feature/float_provider">float provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ClampedNormalFloat extends FloatProvider {

    /**
     * The mean of the normal distribution.
     * @return the mean of the normal distribution
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float mean();

    /**
     * The deviation of the normal distribution.
     * @return the deviation of the normal distribution
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float deviation();

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
     * Creates a new ClampedNormalFloat.
     * @param min the minimum value to clamp to
     * @param max the maximum value to clamp to
     * @param mean the mean of the normal distribution
     * @param deviation the deviation of the normal distribution
     * @return the ClampedNormalFloat
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedNormalFloat of(float min, float max, float mean, float deviation) {
        record Holder() {
            static final ConstructWireProvider<ClampedNormalFloat> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.ClampedNormalFloatImpl");
        }
        return Holder.WIRE.construct(min, max, mean, deviation);
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
     * Builder for {@link ClampedNormalFloat}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FloatProviderBuilder<ClampedNormalFloat, Builder> {
        private float mean;
        private float deviation;

        public Builder() {}

        public Builder(ClampedNormalFloat provider) {
            super(provider);
            this.mean = provider.mean();
            this.deviation = provider.deviation();
        }

        /**
         * Sets the mean of the normal distribution.
         * @param mean the mean of the normal distribution
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder mean(float mean) {
            this.mean = mean;
            return this;
        }

        /**
         * Sets the deviation of the normal distribution.
         * @param deviation the deviation of the normal distribution
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder deviation(float deviation) {
            this.deviation = deviation;
            return this;
        }

        @Override
        public ClampedNormalFloat build() {
            return of(minInclusive, maxInclusive, mean, deviation);
        }
    }
}
