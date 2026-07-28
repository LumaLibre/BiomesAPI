package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider biased toward {@link #minInclusive()}. Values at or below
 * {@link #inner()} follow a uniform distribution and above it an exponential one.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BiasedToBottomHeight extends HeightProvider {

    /**
     * The bias factor. Values between 1 and {@code max - min} (inclusive) are uniformly
     * distributed and values above it follow an exponential distribution.
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
     * Creates a new biased-to-bottom height provider.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param inner the bias factor
     * @return the biased-to-bottom height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiasedToBottomHeight of(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int inner) {
        record Holder() {
            static final ConstructWireProvider<BiasedToBottomHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.BiasedToBottomHeightImpl");
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
     * Builder for {@link BiasedToBottomHeight}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends HeightProviderBuilder<BiasedToBottomHeight, Builder> {
        private int inner = 1;

        public Builder() {}

        public Builder(BiasedToBottomHeight provider) {
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
        public BiasedToBottomHeight build() {
            return of(minInclusive, maxInclusive, inner);
        }
    }
}
