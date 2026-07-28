package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider which samples an isosceles trapezoidal distribution between
 * {@link #minInclusive()} and {@link #maxInclusive()} with a flat {@link #plateau()}.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrapezoidHeight extends HeightProvider {

    /**
     * The length of the range in the middle that has a uniform distribution.
     * @return the plateau
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int plateau();

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
     * Creates a new trapezoid height provider.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @param plateau the length of the range in the middle that has a uniform distribution
     * @return the trapezoid height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidHeight of(VerticalAnchor minInclusive, VerticalAnchor maxInclusive, int plateau) {
        record Holder() {
            static final ConstructWireProvider<TrapezoidHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.TrapezoidHeightImpl");
        }
        return Holder.WIRE.construct(minInclusive, maxInclusive, plateau);
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
     * Builder for {@link TrapezoidHeight}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends HeightProviderBuilder<TrapezoidHeight, Builder> {
        private int plateau;

        public Builder() {}

        public Builder(TrapezoidHeight provider) {
            super(provider.minInclusive(), provider.maxInclusive());
            this.plateau = provider.plateau();
        }

        /**
         * Sets the plateau.
         * @param plateau the length of the range in the middle that has a uniform distribution
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder plateau(int plateau) {
            this.plateau = plateau;
            return this;
        }

        @Override
        public TrapezoidHeight build() {
            return of(minInclusive, maxInclusive, plateau);
        }
    }
}
