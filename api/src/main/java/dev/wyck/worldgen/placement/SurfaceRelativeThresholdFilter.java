package dev.wyck.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.HeightmapType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Returns the current position if it lies within a range relative to the surface at that column.
 * Otherwise returns empty.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SurfaceRelativeThresholdFilter extends PlacementFilter {

    /**
     * The heightmap to measure the surface against.
     * @return the heightmap type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    HeightmapType heightmap();

    /**
     * The minimum relative height from the surface to the current position.
     * @return the minimum inclusive relative height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int minInclusive();

    /**
     * The maximum relative height from the surface to the current position.
     * @return the maximum inclusive relative height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxInclusive();

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
     * Creates a new surface relative threshold filter.
     * @param heightmap the heightmap to measure the surface against
     * @param minInclusive the minimum relative height from the surface to the current position
     * @param maxInclusive the maximum relative height from the surface to the current position
     * @return a new surface relative threshold filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SurfaceRelativeThresholdFilter of(HeightmapType heightmap, int minInclusive, int maxInclusive) {
        record Holder() {
            static final ConstructWireProvider<SurfaceRelativeThresholdFilter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.SurfaceRelativeThresholdFilterImpl");
        }
        return Holder.WIRE.construct(heightmap, minInclusive, maxInclusive);
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
     * Builder for {@link SurfaceRelativeThresholdFilter}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable HeightmapType heightmap;
        private int minInclusive = Integer.MIN_VALUE;
        private int maxInclusive = Integer.MAX_VALUE;

        public Builder() {}

        public Builder(SurfaceRelativeThresholdFilter configuration) {
            this.heightmap = configuration.heightmap();
            this.minInclusive = configuration.minInclusive();
            this.maxInclusive = configuration.maxInclusive();
        }

        /**
         * Sets the heightmap.
         * @param heightmap the heightmap to measure the surface against
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder heightmap(HeightmapType heightmap) {
            this.heightmap = heightmap;
            return this;
        }

        /**
         * Sets the minimum inclusive relative height.
         * @param minInclusive the minimum relative height from the surface to the current position
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minInclusive(int minInclusive) {
            this.minInclusive = minInclusive;
            return this;
        }

        /**
         * Sets the maximum inclusive relative height.
         * @param maxInclusive the maximum relative height from the surface to the current position
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxInclusive(int maxInclusive) {
            this.maxInclusive = maxInclusive;
            return this;
        }

        /**
         * Builds the surface relative threshold filter.
         * @return the surface relative threshold filter
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SurfaceRelativeThresholdFilter build() {
            Preconditions.checkNotNull(heightmap, "heightmap must be set");

            return of(heightmap, minInclusive, maxInclusive);
        }
    }
}