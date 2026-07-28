package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Uniform float providers sample a range between {@link #minInclusive()} (inclusive)
 * and {@link #maxExclusive()} (exclusive), returning a single, uniformly random value.
 * Note the upper bound is exclusive here, unlike the int provider family.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/float_provider">float provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface UniformFloat extends FloatProvider {

    /**
     * The exclusive upper bound of the range.
     * @return the exclusive upper bound
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float maxExclusive();

    @Override
    @AsOf("3.0.0")
    default float maxInclusive() {
        return maxExclusive();
    }

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
     * Creates a new uniform float provider.
     * @param min the minimum value (inclusive)
     * @param max the maximum value (exclusive)
     * @return the uniform float provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformFloat of(float min, float max) {
        record Holder() {
            static final ConstructWireProvider<UniformFloat> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.UniformFloatImpl");
        }
        return Holder.WIRE.construct(min, max);
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
     * Builder for {@link UniformFloat}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private float minInclusive;
        private float maxExclusive;

        public Builder() {}

        public Builder(UniformFloat provider) {
            this.minInclusive = provider.minInclusive();
            this.maxExclusive = provider.maxExclusive();
        }

        /**
         * Sets the minimum value (inclusive).
         * @param minInclusive the minimum value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minInclusive(float minInclusive) {
            this.minInclusive = minInclusive;
            return this;
        }

        /**
         * Sets the maximum value (exclusive).
         * @param maxExclusive the maximum value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxExclusive(float maxExclusive) {
            this.maxExclusive = maxExclusive;
            return this;
        }

        /**
         * Sets the minimum value (inclusive).
         * @param min the minimum value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder min(float min) {
            return this.minInclusive(min);
        }

        /**
         * Sets the maximum value (exclusive).
         * @param max the maximum value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder max(float max) {
            return this.maxExclusive(max);
        }

        @AsOf("3.0.0")
        public UniformFloat build() {
            return of(minInclusive, maxExclusive);
        }
    }
}
