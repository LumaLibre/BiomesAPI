package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A trapezoid int provider.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrapezoidInt extends IntProvider {

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
     * Creates a new trapezoid int provider.
     * @param min min inclusive
     * @param max max inclusive
     * @param plateau the length of the range in the middle that has a uniform distribution
     * @return the trapezoid int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidInt of(int min, int max, int plateau) {
        record Holder() {
            static final ConstructWireProvider<TrapezoidInt> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.valueproviders.TrapezoidIntImpl");
        }
        return Holder.WIRE.construct(min, max, plateau);
    }

    /**
     * A symmetric triangular distribution over [-{@link #minInclusive()}, {@link #maxInclusive()}].
     * @param range the range of the triangular distribution
     * @return the trapezoid int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidInt triangle(int range) {
        return of(-range, range, 0);
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
     * Builder for {@link TrapezoidInt}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends IntProviderBuilder<TrapezoidInt, Builder> {
        private int plateau;

        public Builder() {}

        public Builder(TrapezoidInt provider) {
            super(provider);
            this.plateau = provider.plateau();
        }

        /**
         * Sets the plateau.
         * @param plateau the plateau
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder plateau(int plateau) {
            this.plateau = plateau;
            return this;
        }

        @AsOf("3.0.0")
        public TrapezoidInt build() {
            return of(minInclusive, maxInclusive, plateau);
        }
    }
}
