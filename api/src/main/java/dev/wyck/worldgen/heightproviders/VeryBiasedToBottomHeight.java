package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider biased strongly toward {@link #minInclusive()}, with a sharper
 * exponential distribution than {@link BiasedToBottomHeight}.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface VeryBiasedToBottomHeight extends HeightProvider {

    /**
     * The bias factor. Values between 1 and {@code max - min} (inclusive) are uniformly
     * distributed and values above it follow a sharp exponential distribution.
     * @return the bias factor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int inner();

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
     * Creates a new very-biased-to-bottom height provider.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param inner the bias factor
     * @return the very-biased-to-bottom height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static VeryBiasedToBottomHeight of(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        record Holder() {
            static final ConstructWireProvider<VeryBiasedToBottomHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.VeryBiasedToBottomHeightImpl");
        }
        return Holder.WIRE.construct(minInclusive, maxInclusive, inner);
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
     * Builder for {@link VeryBiasedToBottomHeight}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends HeightProviderBuilder<VeryBiasedToBottomHeight, Builder> {
        private int inner = 1;

        public Builder() {}

        public Builder(VeryBiasedToBottomHeight provider) {
            super(provider.minInclusive(), provider.maxInclusive());
            this.inner = provider.inner();
        }

        /**
         * Sets the bias factor.
         * @param inner the bias factor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder inner(int inner) {
            this.inner = inner;
            return this;
        }

        @Override
        public VeryBiasedToBottomHeight build() {
            return of(minInclusive, maxInclusive, inner);
        }
    }
}
