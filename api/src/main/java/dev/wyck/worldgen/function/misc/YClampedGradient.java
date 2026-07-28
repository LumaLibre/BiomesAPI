package dev.wyck.worldgen.function.misc;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Clamps the Y coordinate between {@code fromY} and {@code toY} and then linearly maps it to the range
 * {@code [fromValue, toValue]}.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#y_clamped_gradient">Density function - y_clamped_gradient</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface YClampedGradient extends DensityFunction, Registerable<YClampedGradient> {

    /**
     * The Y coordinate that maps to {@link #fromValue()}.
     * @return the lower Y coordinate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int fromY();

    /**
     * The Y coordinate that maps to {@link #toValue()}.
     * @return the upper Y coordinate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int toY();

    /**
     * The value that {@link #fromY()} maps to.
     * @return the lower value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double fromValue();

    /**
     * The value that {@link #toY()} maps to.
     * @return the upper value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double toValue();

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
     * Creates a new y clamped gradient density function.
     * @param resourceKey the resource key, or null
     * @param fromY the Y coordinate that maps to {@code fromValue}
     * @param toY the Y coordinate that maps to {@code toValue}
     * @param fromValue the value that {@code fromY} maps to
     * @param toValue the value that {@code toY} maps to
     * @return a new y clamped gradient density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YClampedGradient of(@Nullable ResourceKey resourceKey, int fromY, int toY, double fromValue, double toValue) {
        record Holder() {
            static final ConstructWireProvider<YClampedGradient> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.misc.YClampedGradientImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), fromY, toY, fromValue, toValue);
    }

    /**
     * Creates a new y clamped gradient density function.
     * @param fromY the Y coordinate that maps to {@code fromValue}
     * @param toY the Y coordinate that maps to {@code toValue}
     * @param fromValue the value that {@code fromY} maps to
     * @param toValue the value that {@code toY} maps to
     * @return a new y clamped gradient density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YClampedGradient of(int fromY, int toY, double fromValue, double toValue) {
        return of(null, fromY, toY, fromValue, toValue);
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
     * Builder for {@link YClampedGradient}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private int fromY;
        private int toY;
        private double fromValue;
        private double toValue;

        private Builder() {}

        private Builder(YClampedGradient function) {
            this.resourceKey = function.resourceKey().orElse(null);
            this.fromY = function.fromY();
            this.toY = function.toY();
            this.fromValue = function.fromValue();
            this.toValue = function.toValue();
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
         * Sets the Y coordinate that maps to {@code fromValue}.
         * @param fromY the lower Y coordinate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fromY(int fromY) {
            this.fromY = fromY;
            return this;
        }

        /**
         * Sets the Y coordinate that maps to {@code toValue}.
         * @param toY the upper Y coordinate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder toY(int toY) {
            this.toY = toY;
            return this;
        }

        /**
         * Sets the value that {@code fromY} maps to.
         * @param fromValue the lower value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fromValue(double fromValue) {
            this.fromValue = fromValue;
            return this;
        }

        /**
         * Sets the value that {@code toY} maps to.
         * @param toValue the upper value
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder toValue(double toValue) {
            this.toValue = toValue;
            return this;
        }

        /**
         * Builds the y clamped gradient density function.
         * @return the y clamped gradient density function
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public YClampedGradient build() {
            return of(resourceKey, fromY, toY, fromValue, toValue);
        }
    }
}
