package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the current position is above water, based on terrain depth. If there is no water above
 * this block, the condition always passes regardless of the field values.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface WaterConditionSource extends ConditionSource {

    /**
     * The value added to the water depth before the comparison. May be negative to match blocks at a
     * specific depth relative to the water surface.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int offset();

    /**
     * How much the check is affected by the surface depth, between {@code -20} and {@code 20}.
     * @return the surface depth multiplier
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int surfaceDepthMultiplier();

    /**
     * If true, adds the distance to the surface to the offset, effectively checking whether the surface
     * block above this one is above water.
     * @return whether the stone depth is added
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean addStoneDepth();

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
     * Creates a new water condition source.
     * @param offset the value added to the water depth before the comparison
     * @param surfaceDepthMultiplier how much the check is affected by the surface depth
     * @param addStoneDepth whether the distance to the surface is added to the offset
     * @return the water condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WaterConditionSource of(int offset, int surfaceDepthMultiplier, boolean addStoneDepth) {
        record Holder() {
            static final ConstructWireProvider<WaterConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.WaterConditionSourceImpl");
        }
        return Holder.WIRE.construct(offset, surfaceDepthMultiplier, addStoneDepth);
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
     * Builder for {@link WaterConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int offset;
        private int surfaceDepthMultiplier;
        private boolean addStoneDepth;

        private Builder() {}

        private Builder(WaterConditionSource source) {
            this.offset = source.offset();
            this.surfaceDepthMultiplier = source.surfaceDepthMultiplier();
            this.addStoneDepth = source.addStoneDepth();
        }

        /**
         * Sets the value added to the water depth before the comparison.
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
         * Sets how much the check is affected by the surface depth.
         * @param surfaceDepthMultiplier the surface depth multiplier
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder surfaceDepthMultiplier(int surfaceDepthMultiplier) {
            this.surfaceDepthMultiplier = surfaceDepthMultiplier;
            return this;
        }

        /**
         * Sets whether the distance to the surface is added to the offset.
         * @param addStoneDepth whether the stone depth is added
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder addStoneDepth(boolean addStoneDepth) {
            this.addStoneDepth = addStoneDepth;
            return this;
        }

        /**
         * Builds the water condition source.
         * @return the water condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public WaterConditionSource build() {
            return of(offset, surfaceDepthMultiplier, addStoneDepth);
        }
    }
}
