package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * A provider of an integer value.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface IntProvider extends ValueProvider {

    /**
     * The smallest value this provider can yield.
     * @return the smallest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int minInclusive();

    /**
     * The largest value this provider can yield.
     * @return the largest value this provider can yield.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int maxInclusive();

    /**
     * Clamps the value to the given range.
     * @param min the minimum value
     * @param max the maximum value
     * @return a clamped int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default ClampedInt clamp(int min, int max) {
        return IntProvider.clamped(this, min, max);
    }

    /**
     * Creates a constant int provider.
     * @param value the value to return
     * @return a constant int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConstantInt constant(int value) {
        return ConstantInt.of(value);
    }

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
     * Creates a uniform int provider.
     * @param min min inclusive
     * @param max max inclusive
     * @return a uniform int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static UniformInt uniform(int min, int max) {
        return UniformInt.of(min, max);
    }

    /**
     * Creates a new uniform int builder.
     * @return a new uniform int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformInt.Builder uniform() {
        return UniformInt.builder();
    }

    /**
     * Creates a biased-to-bottom int provider.
     * @param min min inclusive
     * @param max max inclusive
     * @return a biased-to-bottom int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiasedToBottomInt biasedToBottom(int min, int max) {
        return BiasedToBottomInt.of(min, max);
    }

    /**
     * Creates a new biased-to-bottom int builder.
     * @return a new biased-to-bottom int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiasedToBottomInt.Builder biasedToBottom() {
        return BiasedToBottomInt.builder();
    }

    /**
     * Creates a new clamped normal int.
     * @param min min inclusive
     * @param max max inclusive
     * @param mean mean of the normal distribution
     * @param deviation deviation of the normal distribution
     * @return a new clamped normal int
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClampedNormalInt clampedNormal(int min, int max, float mean, float deviation) {
        return ClampedNormalInt.of(min, max, mean, deviation);
    }

    /**
     * Creates a new clamped normal int builder.
     * @return a new clamped normal int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedNormalInt.Builder clampedNormal() {
        return ClampedNormalInt.builder();
    }

    /**
     * Creates a trapezoidal int provider.
     * @param min min inclusive
     * @param max max inclusive
     * @param plateau the length of the range in the middle that has a uniform distribution
     * @return the trapezoid int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static TrapezoidInt trapezoid(int min, int max, int plateau) {
        return TrapezoidInt.of(min, max, plateau);
    }

    /**
     * A symmetric triangular distribution over [-{@link #minInclusive()}, {@link #maxInclusive()}].
     * @param range the range of the triangular distribution
     * @return the trapezoid int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static TrapezoidInt triangle(int range) {
        return TrapezoidInt.triangle(range);
    }

    /**
     * Creates a new trapezoid int builder.
     * @return a new trapezoid int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidInt.Builder trapezoid() {
        return TrapezoidInt.builder();
    }

    /**
     * Creates a clamped int provider.
     * @param source the source provider to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return a clamped int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClampedInt clamped(IntProvider source, int min, int max) {
        return ClampedInt.of(source, min, max);
    }

    /**
     * Creates a new clamped int builder.
     * @return a new clamped int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedInt.Builder clamped() {
        return ClampedInt.builder();
    }

    /**
     * Creates a weighted list int provider.
     * @param distribution the weighted pool of providers, which must not be empty
     * @return a weighted list int provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static WeightedListInt weightedList(WeightedList<IntProvider> distribution) {
        return WeightedListInt.of(distribution);
    }

    /**
     * Creates a new weighted list int builder.
     * @return a new weighted list int builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedListInt.Builder weightedList() {
        return WeightedListInt.builder();
    }

    /**
     * A constant int provider that always returns 0.
     * @return a constant int provider that always returns 0
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantInt zero() {
        return ConstantInt.ZERO;
    }

    /**
     * Common abstract builder for most int providers.
     * @param <T> the provider type
     * @param <S> the builder type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class IntProviderBuilder<T extends IntProvider, S extends IntProviderBuilder<T, S>> {
        protected int minInclusive;
        protected int maxInclusive;

        protected IntProviderBuilder() {}

        protected IntProviderBuilder(IntProvider provider) {
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
        public S minInclusive(int minInclusive) {
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
        public S maxInclusive(int maxInclusive) {
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
        public S min(int min) {
            return this.minInclusive(min);
        }

        /**
         * Sets the maximum allowed value.
         * @param max the maximum allowed value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S max(int max) {
            return this.maxInclusive(max);
        }

        /**
         * Builds the int provider.
         * @return the int provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public abstract T build();
    }
    /**
     * Reads a Minecraft int provider into a wrapper.
     * @param minecraftIntProvider the int provider to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static IntProvider decode(Object minecraftIntProvider) {
        record Holder() {
            static final Decoder<IntProvider> DECODER = Decoder.create("dev.wyck.decode.worldgen.valueproviders.IntProviderDecoders");
        }
        return Holder.DECODER.decode(minecraftIntProvider);
    }
}