package dev.wyck.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Applies an offset to the current position. The offset is only random when the given provider is
 * not a constant. The X and Z axes share the same provider but are sampled individually, so their
 * resulting offsets may differ.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomOffsetPlacement extends PlacementModifier {

    /**
     * The offset applied to both the X and Z axes, sampled individually per axis.
     * @return the horizontal spread provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider xzSpread();

    /**
     * The offset applied to the Y axis.
     * @return the vertical spread provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider ySpread();

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
     * Creates a new random offset placement.
     * @param xzSpread the offset applied to both the X and Z axes, between -16 and 16 (inclusive)
     * @param ySpread the offset applied to the Y axis, between -16 and 16 (inclusive)
     * @return a new random offset placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomOffsetPlacement of(IntProvider xzSpread, IntProvider ySpread) {
        record Holder() {
            static final ConstructWireProvider<RandomOffsetPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.RandomOffsetPlacementImpl");
        }
        return Holder.WIRE.construct(xzSpread, ySpread);
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
     * Builder for {@link RandomOffsetPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable IntProvider xzSpread;
        private @Nullable IntProvider ySpread;

        public Builder() {}

        public Builder(RandomOffsetPlacement configuration) {
            this.xzSpread = configuration.xzSpread();
            this.ySpread = configuration.ySpread();
        }

        /**
         * Sets the horizontal spread provider.
         * @param xzSpread the offset applied to both the X and Z axes, between -16 and 16 (inclusive)
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder xzSpread(IntProvider xzSpread) {
            this.xzSpread = xzSpread;
            return this;
        }

        /**
         * Sets the vertical spread provider.
         * @param ySpread the offset applied to the Y axis, between -16 and 16 (inclusive)
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder ySpread(IntProvider ySpread) {
            this.ySpread = ySpread;
            return this;
        }

        /**
         * Builds the random offset placement.
         * @return the random offset placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RandomOffsetPlacement build() {
            Preconditions.checkNotNull(xzSpread, "xzSpread must be set");
            Preconditions.checkNotNull(ySpread, "ySpread must be set");

            return of(xzSpread, ySpread);
        }
    }
}