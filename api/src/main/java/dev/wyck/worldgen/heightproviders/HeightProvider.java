package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the HeightProvider family. Sampling occurs Minecraft code side,
 * so this wrapper only carries the anchors/shape parameters.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface HeightProvider extends Wrapper {

    @ApiStatus.Internal
    Decoder<HeightProvider> DECODER = Decoder.create("dev.wyck.decode.worldgen.heightproviders.HeightProviderDecoders");

    /**
     * The vertical anchor used as the minimum height.
     * @return the minimum anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor minInclusive();

    /**
     * The vertical anchor used as the maximum height.
     * @return the maximum anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor maxInclusive();

    /**
     * A single fixed anchor.
     * @param value the vertical anchor to use as the constant height
     * @return a constant height provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ConstantHeight constant(VerticalAnchor value) {
        return ConstantHeight.of(value);
    }

    /**
     * Uniformly distributed between two anchors (inclusive).
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @return a uniform height provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static UniformHeight uniform(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
        return UniformHeight.of(minInclusive, maxInclusive);
    }

    /**
     * Creates a new uniform height builder.
     * @return a new uniform height builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformHeight.Builder uniform() {
        return UniformHeight.builder();
    }

    /**
     * Trapezoidal distribution with a flat plateau of the given height.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param plateau the length of the range in the middle that has a uniform distribution
     * @return a trapezoid height provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static TrapezoidHeight trapezoid(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int plateau) {
        return TrapezoidHeight.of(minInclusive, maxInclusive, plateau);
    }

    /**
     * Creates a new trapezoid height builder.
     * @return a new trapezoid height builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidHeight.Builder trapezoid() {
        return TrapezoidHeight.builder();
    }

    /**
     * Weighted toward the bottom of the range.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param inner the bias factor; higher values push the distribution toward the bottom
     * @return a biased-to-bottom height provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiasedToBottomHeight biasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        return BiasedToBottomHeight.of(minInclusive, maxInclusive, inner);
    }

    /**
     * Creates a new biased-to-bottom height builder.
     * @return a new biased-to-bottom height builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiasedToBottomHeight.Builder biasedToBottom() {
        return BiasedToBottomHeight.builder();
    }

    /**
     * Weighted very strongly toward the bottom of the range.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param inner the bias factor; higher values push the distribution toward the bottom
     * @return a very biased-to-bottom height provider
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static VeryBiasedToBottomHeight veryBiasedToBottom(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        return VeryBiasedToBottomHeight.of(minInclusive, maxInclusive, inner);
    }

    /**
     * Creates a new very biased-to-bottom height builder.
     * @return a new very biased-to-bottom height builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static VeryBiasedToBottomHeight.Builder veryBiasedToBottom() {
        return VeryBiasedToBottomHeight.builder();
    }

    /**
     * A random value drawn from a weighted list of height providers.
     * @param distribution the weighted pool of providers, which must not be empty
     * @return a weighted list height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedListHeight weightedList(WeightedList<HeightProvider> distribution) {
        return WeightedListHeight.of(distribution);
    }

    /**
     * Creates a new weighted list height builder.
     * @return a new weighted list height builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedListHeight.Builder weightedList() {
        return WeightedListHeight.builder();
    }

    /**
     * A constant height provider that always returns the same value.
     * @return the constant height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantHeight zero() {
        return ConstantHeight.ZERO;
    }

    /**
     * Common abstract builder for height providers with inclusive anchor bounds.
     * @param <T> the provider type
     * @param <S> the builder type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class HeightProviderBuilder<T extends HeightProvider, S extends HeightProviderBuilder<T, S>> {
        protected VerticalAnchor minInclusive = VerticalAnchor.bottom();
        protected VerticalAnchor maxInclusive = VerticalAnchor.top();

        protected HeightProviderBuilder() {}

        protected HeightProviderBuilder(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
            this.minInclusive = minInclusive;
            this.maxInclusive = maxInclusive;
        }

        /**
         * Sets the minimum anchor.
         * @param minInclusive the minimum anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S minInclusive(VerticalAnchor minInclusive) {
            this.minInclusive = minInclusive;
            return (S) this;
        }

        /**
         * Sets the maximum anchor.
         * @param maxInclusive the maximum anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S maxInclusive(VerticalAnchor maxInclusive) {
            this.maxInclusive = maxInclusive;
            return (S) this;
        }

        /**
         * Sets the minimum anchor.
         * @param min the minimum anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S min(VerticalAnchor min) {
            return this.minInclusive(min);
        }

        /**
         * Sets the maximum anchor.
         * @param max the maximum anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public S max(VerticalAnchor max) {
            return this.maxInclusive(max);
        }

        /**
         * Builds the height provider.
         * @return the height provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public abstract T build();
    }
    /**
     * Reads a Minecraft height provider into a wrapper.
     * @param minecraftHeightProvider the height provider to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static HeightProvider decode(Object minecraftHeightProvider) {
        return DECODER.decode(minecraftHeightProvider);
    }
}
