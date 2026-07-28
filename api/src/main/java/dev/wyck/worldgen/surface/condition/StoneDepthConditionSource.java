package dev.wyck.worldgen.surface.condition;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the current position is within a specified distance from the surface, either upward or
 * downward, using terrain depth. Used in vanilla to place grass blocks and dirt layers on the surface
 * in the overworld, and soul sand, soul soil, gravel and basalt floors and ceilings in the nether.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface StoneDepthConditionSource extends ConditionSource {

    /**
     * The vertical offset.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int offset();

    /**
     * Whether the surface depth is added to the offset. Note: this is not {@code add_stone_depth}.
     * @return whether the surface depth is added
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean addSurfaceDepth();

    /**
     * Adds a mapped value of the secondary surface depth to the offset.
     * @return the secondary depth range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int secondaryDepthRange();

    /**
     * The face the depth is measured from.
     * @return the surface type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    CaveSurface surfaceType();

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
     * Creates a new stone depth condition source.
     * @param offset the vertical offset
     * @param addSurfaceDepth whether the surface depth is added to the offset
     * @param secondaryDepthRange the secondary depth range added to the offset
     * @param surfaceType the face the depth is measured from
     * @return the stone depth condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StoneDepthConditionSource of(int offset, boolean addSurfaceDepth, int secondaryDepthRange, CaveSurface surfaceType) {
        record Holder() {
            static final ConstructWireProvider<StoneDepthConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.StoneDepthConditionSourceImpl");
        }
        return Holder.WIRE.construct(offset, addSurfaceDepth, secondaryDepthRange, surfaceType);
    }

    /**
     * Creates a new stone depth condition source without a secondary depth range.
     * @param offset the vertical offset
     * @param addSurfaceDepth whether the surface depth is added to the offset
     * @param surfaceType the face the depth is measured from
     * @return the stone depth condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StoneDepthConditionSource of(int offset, boolean addSurfaceDepth, CaveSurface surfaceType) {
        return of(offset, addSurfaceDepth, 0, surfaceType);
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
     * Builder for {@link StoneDepthConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int offset;
        private boolean addSurfaceDepth;
        private int secondaryDepthRange;
        private @Nullable CaveSurface surfaceType;

        private Builder() {}

        private Builder(StoneDepthConditionSource source) {
            this.offset = source.offset();
            this.addSurfaceDepth = source.addSurfaceDepth();
            this.secondaryDepthRange = source.secondaryDepthRange();
            this.surfaceType = source.surfaceType();
        }

        /**
         * Sets the vertical offset.
         * @param offset the offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder offset(int offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Sets whether the surface depth is added to the offset.
         * @param addSurfaceDepth whether the surface depth is added
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder addSurfaceDepth(boolean addSurfaceDepth) {
            this.addSurfaceDepth = addSurfaceDepth;
            return this;
        }

        /**
         * Sets the secondary depth range added to the offset.
         * @param secondaryDepthRange the secondary depth range
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder secondaryDepthRange(int secondaryDepthRange) {
            this.secondaryDepthRange = secondaryDepthRange;
            return this;
        }

        /**
         * Sets the face the depth is measured from.
         * @param surfaceType the surface type
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder surfaceType(CaveSurface surfaceType) {
            this.surfaceType = surfaceType;
            return this;
        }

        /**
         * Builds the stone depth condition source.
         * @return the stone depth condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public StoneDepthConditionSource build() {
            Preconditions.checkNotNull(surfaceType, "surfaceType must be set");
            return of(offset, addSurfaceDepth, secondaryDepthRange, surfaceType);
        }
    }
}
