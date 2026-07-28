package dev.wyck.worldgen.surface.condition;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the current position is above a specified height (exclusive).
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface YAboveConditionSource extends ConditionSource {

    /**
     * The vertical anchor to compare the height with.
     * @return the anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor anchor();

    /**
     * How much the check is affected by the surface depth, between {@code -20} and {@code 20}.
     * @return the surface depth multiplier
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int surfaceDepthMultiplier();

    /**
     * If true, adds the distance to the surface above to the offset.
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
     * Creates a new y above condition source.
     * @param anchor the vertical anchor to compare the height with
     * @param surfaceDepthMultiplier how much the check is affected by the surface depth
     * @param addStoneDepth whether the distance to the surface above is added to the offset
     * @return the y above condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YAboveConditionSource of(VerticalAnchor anchor, int surfaceDepthMultiplier, boolean addStoneDepth) {
        record Holder() {
            static final ConstructWireProvider<YAboveConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.YAboveConditionSourceImpl");
        }
        return Holder.WIRE.construct(anchor, surfaceDepthMultiplier, addStoneDepth);
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
     * Builder for {@link YAboveConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable VerticalAnchor anchor;
        private int surfaceDepthMultiplier;
        private boolean addStoneDepth;

        private Builder() {}

        private Builder(YAboveConditionSource source) {
            this.anchor = source.anchor();
            this.surfaceDepthMultiplier = source.surfaceDepthMultiplier();
            this.addStoneDepth = source.addStoneDepth();
        }

        /**
         * Sets the vertical anchor to compare the height with.
         * @param anchor the anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder anchor(VerticalAnchor anchor) {
            this.anchor = anchor;
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
         * Sets whether the distance to the surface above is added to the offset.
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
         * Builds the y above condition source.
         * @return the y above condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public YAboveConditionSource build() {
            Preconditions.checkNotNull(anchor, "anchor must be set");
            return of(anchor, surfaceDepthMultiplier, addStoneDepth);
        }
    }
}
