package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A provider of a floating-point value.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/float_provider">float provider</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface FloatProvider extends ValueProvider {

    @ApiStatus.Internal
    Decoder<FloatProvider> DECODER = Decoder.create("dev.wyck.decode.worldgen.valueproviders.FloatProviderDecoders");

    /**
     * The smallest value this provider can yield.
     * @return the smallest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float minInclusive();

    /**
     * The largest value this provider can yield.
     * @return the largest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float maxInclusive();

    /** @inheritDoc */
    @Override
    default double min() {
        return minInclusive();
    }

    /** @inheritDoc */
    @Override
    default double max() {
        return maxInclusive();
    }

    /**
     * Creates a constant float provider.
     * @param value the value to return
     * @return a constant float provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConstantFloat constant(float value) {
        return ConstantFloat.of(value);
    }

    /**
     * Creates a uniform float provider.
     * @param min min inclusive
     * @param max max exclusive
     * @return a uniform float provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static UniformFloat uniform(float min, float max) {
        return UniformFloat.of(min, max);
    }

    /**
     * Creates a new uniform float builder.
     * @return a new uniform float builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformFloat.Builder uniform() {
        return UniformFloat.builder();
    }

    /**
     * Creates a clamped normal float provider.
     * @param mean the mean of the normal distribution
     * @param deviation the deviation of the normal distribution
     * @param min the minimum value to clamp to
     * @param max the maximum value to clamp to
     * @return a clamped normal float provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClampedNormalFloat clampedNormal(float mean, float deviation, float min, float max) {
        return ClampedNormalFloat.of(min, max, mean, deviation);
    }

    /**
     * Creates a new clamped normal float builder.
     * @return a new clamped normal float builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedNormalFloat.Builder clampedNormal() {
        return ClampedNormalFloat.builder();
    }

    /**
     * Creates a trapezoidal float provider.
     * @param min the minimum value
     * @param max the maximum value
     * @param plateau the range in the middle that has a uniform distribution
     * @return a trapezoid float provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static TrapezoidFloat trapezoid(float min, float max, float plateau) {
        return TrapezoidFloat.of(min, max, plateau);
    }

    /**
     * Creates a new trapezoid float builder.
     * @return a new trapezoid float builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidFloat.Builder trapezoid() {
        return TrapezoidFloat.builder();
    }

    /**
     * Common abstract builder for float providers with inclusive bounds.
     * @param <T> the provider type
     * @param <S> the builder type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class FloatProviderBuilder<T extends FloatProvider, S extends FloatProviderBuilder<T, S>> {
        protected float minInclusive;
        protected float maxInclusive;

        protected FloatProviderBuilder() {}

        protected FloatProviderBuilder(FloatProvider provider) {
            this.minInclusive = provider.minInclusive();
            this.maxInclusive = provider.maxInclusive();
        }

        /**
         * Sets the minimum allowed value.
         * @param minInclusive the minimum allowed value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S minInclusive(float minInclusive) {
            this.minInclusive = minInclusive;
            return (S) this;
        }

        /**
         * Sets the maximum allowed value.
         * @param maxInclusive the maximum allowed value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S maxInclusive(float maxInclusive) {
            this.maxInclusive = maxInclusive;
            return (S) this;
        }

        /**
         * Sets the minimum allowed value.
         * @param min the minimum allowed value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S min(float min) {
            return this.minInclusive(min);
        }

        /**
         * Sets the maximum allowed value.
         * @param max the maximum allowed value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S max(float max) {
            return this.maxInclusive(max);
        }

        /**
         * Builds the provider.
         * @return the built provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public abstract T build();
    }
    /**
     * Reads a Minecraft float provider into a wrapper.
     * @param minecraftFloatProvider the float provider to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FloatProvider decode(Object minecraftFloatProvider) {
        return DECODER.decode(minecraftFloatProvider);
    }
}
