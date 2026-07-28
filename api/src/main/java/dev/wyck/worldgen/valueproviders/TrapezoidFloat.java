package dev.wyck.worldgen.valueproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A trapezoid float provider: a random value with an isosceles trapezoidal distribution.
 * The {@link #plateau()} is the length of the range in the middle that has a uniform
 * distribution; a plateau of {@code 0} yields a triangular distribution.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/float_provider">float provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrapezoidFloat extends FloatProvider {

    /**
     * The length of the range in the middle that has a uniform distribution.
     * @return the plateau
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float plateau();

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
     * Creates a new trapezoid float provider.
     * @param min the minimum value
     * @param max the maximum value
     * @param plateau the range in the middle that has a uniform distribution
     * @return the trapezoid float provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrapezoidFloat of(float min, float max, float plateau) {
        record Holder() {
            static final ConstructWireProvider<TrapezoidFloat> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.TrapezoidFloatImpl");
        }
        return Holder.WIRE.construct(min, max, plateau);
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
     * Builder for {@link TrapezoidFloat}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FloatProviderBuilder<TrapezoidFloat, Builder> {
        private float plateau;

        public Builder() {}

        public Builder(TrapezoidFloat provider) {
            super(provider);
            this.plateau = provider.plateau();
        }

        /**
         * Sets the plateau.
         * @param plateau the range in the middle that has a uniform distribution
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder plateau(float plateau) {
            this.plateau = plateau;
            return this;
        }

        @Override
        public TrapezoidFloat build() {
            Preconditions.checkArgument(plateau <= maxInclusive - minInclusive, "plateau must be <= max - min");
            return of(minInclusive, maxInclusive, plateau);
        }
    }
}
