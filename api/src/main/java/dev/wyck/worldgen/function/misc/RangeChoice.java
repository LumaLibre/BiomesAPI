package dev.wyck.worldgen.function.misc;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Computes the input value and, depending on whether it falls within {@code [minInclusive, maxExclusive)},
 * returns one of two other density functions. Essentially an if-then-else statement.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#range_choice">Density function - range_choice</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RangeChoice extends DensityFunction, Registerable<RangeChoice> {

    /**
     * The value to compare against the range.
     * @return the input density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction input();

    /**
     * The lower bound of the range, inclusive.
     * @return the minimum inclusive value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double minInclusive();

    /**
     * The upper bound of the range, exclusive.
     * @return the maximum exclusive value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double maxExclusive();

    /**
     * The density function used when the input is inside the range.
     * @return the in-range density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction whenInRange();

    /**
     * The density function used when the input is outside the range.
     * @return the out-of-range density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction whenOutOfRange();

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
     * Creates a new range choice density function.
     * @param resourceKey the resource key, or null
     * @param input the value to compare against the range
     * @param minInclusive the lower bound of the range, inclusive
     * @param maxExclusive the upper bound of the range, exclusive
     * @param whenInRange the density function used when the input is inside the range
     * @param whenOutOfRange the density function used when the input is outside the range
     * @return a new range choice density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RangeChoice of(@Nullable ResourceKey resourceKey, DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        record Holder() {
            static final ConstructWireProvider<RangeChoice> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.misc.RangeChoiceImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    /**
     * Creates a new range choice density function.
     * @param input the value to compare against the range
     * @param minInclusive the lower bound of the range, inclusive
     * @param maxExclusive the upper bound of the range, exclusive
     * @param whenInRange the density function used when the input is inside the range
     * @param whenOutOfRange the density function used when the input is outside the range
     * @return a new range choice density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RangeChoice of(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return of(null, input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
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
     * Builder for {@link RangeChoice}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable DensityFunction input;
        private double minInclusive;
        private double maxExclusive;
        private @Nullable DensityFunction whenInRange;
        private @Nullable DensityFunction whenOutOfRange;

        private Builder() {}

        private Builder(RangeChoice function) {
            this.resourceKey = function.resourceKey().orElse(null);
            this.input = function.input();
            this.minInclusive = function.minInclusive();
            this.maxExclusive = function.maxExclusive();
            this.whenInRange = function.whenInRange();
            this.whenOutOfRange = function.whenOutOfRange();
        }

        /**
         * Sets the resource key.
         * @param resourceKey the resource key
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the value to compare against the range.
         * @param input the input density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder input(DensityFunction input) {
            this.input = input;
            return this;
        }

        /**
         * Sets the lower bound of the range, inclusive.
         * @param minInclusive the minimum inclusive value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minInclusive(double minInclusive) {
            this.minInclusive = minInclusive;
            return this;
        }

        /**
         * Sets the upper bound of the range, exclusive.
         * @param maxExclusive the maximum exclusive value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxExclusive(double maxExclusive) {
            this.maxExclusive = maxExclusive;
            return this;
        }

        /**
         * Sets the density function used when the input is inside the range.
         * @param whenInRange the in-range density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder whenInRange(DensityFunction whenInRange) {
            this.whenInRange = whenInRange;
            return this;
        }

        /**
         * Sets the density function used when the input is outside the range.
         * @param whenOutOfRange the out-of-range density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder whenOutOfRange(DensityFunction whenOutOfRange) {
            this.whenOutOfRange = whenOutOfRange;
            return this;
        }

        /**
         * Builds the range choice density function.
         * @return the range choice density function
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RangeChoice build() {
            Preconditions.checkNotNull(input, "input must be set");
            Preconditions.checkNotNull(whenInRange, "whenInRange must be set");
            Preconditions.checkNotNull(whenOutOfRange, "whenOutOfRange must be set");
            return of(resourceKey, input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
        }
    }
}
