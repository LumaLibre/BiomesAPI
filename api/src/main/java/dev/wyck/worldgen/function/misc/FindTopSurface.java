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
 * Scans through a column of an input density and returns the topmost Y-level whose density is above 0.
 * If no such position exists within the bounds, {@link #lowerBound()} is returned.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#find_top_surface">Density function - find_top_surface</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FindTopSurface extends DensityFunction, Registerable<FindTopSurface> {

    /**
     * The density function to scan.
     * @return the density function to scan
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction density();

    /**
     * The Y-level to start the scan at. Usually a 2D density function.
     * @return the upper bound density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction upperBound();

    /**
     * The Y-level to stop the scan at, and the value returned when no surface is found.
     * @return the lower bound
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int lowerBound();

    /**
     * The resolution of the scan; e.g. if set to 4, only every 4th block is checked.
     * @return the cell height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int cellHeight();

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
     * Creates a new find top surface density function.
     * @param resourceKey the resource key, or null
     * @param density the density function to scan
     * @param upperBound the Y-level to start the scan at
     * @param lowerBound the Y-level to stop the scan at
     * @param cellHeight the resolution of the scan
     * @return a new find top surface density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FindTopSurface of(@Nullable ResourceKey resourceKey, DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        record Holder() {
            static final ConstructWireProvider<FindTopSurface> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.misc.FindTopSurfaceImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), density, upperBound, lowerBound, cellHeight);
    }

    /**
     * Creates a new find top surface density function.
     * @param density the density function to scan
     * @param upperBound the Y-level to start the scan at
     * @param lowerBound the Y-level to stop the scan at
     * @param cellHeight the resolution of the scan
     * @return a new find top surface density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FindTopSurface of(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        return of(null, density, upperBound, lowerBound, cellHeight);
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
     * Builder for {@link FindTopSurface}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable DensityFunction density;
        private @Nullable DensityFunction upperBound;
        private int lowerBound;
        private int cellHeight;

        private Builder() {}

        private Builder(FindTopSurface function) {
            this.resourceKey = function.resourceKey().orElse(null);
            this.density = function.density();
            this.upperBound = function.upperBound();
            this.lowerBound = function.lowerBound();
            this.cellHeight = function.cellHeight();
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
         * Sets the density function to scan.
         * @param density the density function to scan
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder density(DensityFunction density) {
            this.density = density;
            return this;
        }

        /**
         * Sets the Y-level to start the scan at.
         * @param upperBound the upper bound density function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder upperBound(DensityFunction upperBound) {
            this.upperBound = upperBound;
            return this;
        }

        /**
         * Sets the Y-level to stop the scan at.
         * @param lowerBound the lower bound
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lowerBound(int lowerBound) {
            this.lowerBound = lowerBound;
            return this;
        }

        /**
         * Sets the resolution of the scan.
         * @param cellHeight the cell height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder cellHeight(int cellHeight) {
            this.cellHeight = cellHeight;
            return this;
        }

        /**
         * Builds the find top surface density function.
         * @return the find top surface density function
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FindTopSurface build() {
            Preconditions.checkNotNull(density, "density must be set");
            Preconditions.checkNotNull(upperBound, "upperBound must be set");
            return of(resourceKey, density, upperBound, lowerBound, cellHeight);
        }
    }
}
