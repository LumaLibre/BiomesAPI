package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the foliage of a cherry tree, optionally with holes and hanging leaves.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CherryFoliagePlacer extends FoliagePlacer {

    /**
     * The foliage's height, between 4 and 16 (inclusive).
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider height();

    /**
     * The probability of a hole in the wide bottom layer, between 0.0F and 1.0F (inclusive).
     * @return the wide bottom layer hole chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float wideBottomLayerHoleChance();

    /**
     * The probability of a hole in the corners, between 0.0F and 1.0F (inclusive).
     * @return the corner hole chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float cornerHoleChance();

    /**
     * The probability of generating hanging leaves, between 0.0F and 1.0F (inclusive).
     * @return the hanging leaves chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float hangingLeavesChance();

    /**
     * The probability of extending hanging leaves, between 0.0F and 1.0F (inclusive).
     * @return the hanging leaves extension chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float hangingLeavesExtensionChance();

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
     * Creates a new cherry foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param height the foliage's height, between 4 and 16 (inclusive)
     * @param wideBottomLayerHoleChance the probability of a hole in the wide bottom layer, between 0.0F and 1.0F (inclusive)
     * @param cornerHoleChance the probability of a hole in the corners, between 0.0F and 1.0F (inclusive)
     * @param hangingLeavesChance the probability of generating hanging leaves, between 0.0F and 1.0F (inclusive)
     * @param hangingLeavesExtensionChance the probability of extending hanging leaves, between 0.0F and 1.0F (inclusive)
     * @return a new cherry foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CherryFoliagePlacer of(
        IntProvider radius,
        IntProvider offset,
        IntProvider height,
        float wideBottomLayerHoleChance,
        float cornerHoleChance,
        float hangingLeavesChance,
        float hangingLeavesExtensionChance
    ) {
        record Holder() {
            static final ConstructWireProvider<CherryFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.CherryFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, height, wideBottomLayerHoleChance, cornerHoleChance, hangingLeavesChance, hangingLeavesExtensionChance);
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
     * Builder for {@link CherryFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, CherryFoliagePlacer> {

        private IntProvider height = IntProvider.constant(4);
        private float wideBottomLayerHoleChance;
        private float cornerHoleChance;
        private float hangingLeavesChance;
        private float hangingLeavesExtensionChance;

        public Builder() {}

        public Builder(CherryFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
            this.wideBottomLayerHoleChance = foliagePlacer.wideBottomLayerHoleChance();
            this.cornerHoleChance = foliagePlacer.cornerHoleChance();
            this.hangingLeavesChance = foliagePlacer.hangingLeavesChance();
            this.hangingLeavesExtensionChance = foliagePlacer.hangingLeavesExtensionChance();
        }

        /**
         * Sets the foliage's height, between 4 and 16 (inclusive).
         * @param height the height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the probability of a hole in the wide bottom layer, between 0.0F and 1.0F (inclusive).
         * @param wideBottomLayerHoleChance the wide bottom layer hole chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder wideBottomLayerHoleChance(float wideBottomLayerHoleChance) {
            this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
            return this;
        }

        /**
         * Sets the probability of a hole in the corners, between 0.0F and 1.0F (inclusive).
         * @param cornerHoleChance the corner hole chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder cornerHoleChance(float cornerHoleChance) {
            this.cornerHoleChance = cornerHoleChance;
            return this;
        }

        /**
         * Sets the probability of generating hanging leaves, between 0.0F and 1.0F (inclusive).
         * @param hangingLeavesChance the hanging leaves chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingLeavesChance(float hangingLeavesChance) {
            this.hangingLeavesChance = hangingLeavesChance;
            return this;
        }

        /**
         * Sets the probability of extending hanging leaves, between 0.0F and 1.0F (inclusive).
         * @param hangingLeavesExtensionChance the hanging leaves extension chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingLeavesExtensionChance(float hangingLeavesExtensionChance) {
            this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
            return this;
        }

        @Override
        protected CherryFoliagePlacer create() {
            Preconditions.checkArgument(height.minInclusive() >= 4 && height.maxInclusive() <= 16, "height must be between 4 and 16");
            Preconditions.checkArgument(wideBottomLayerHoleChance >= 0.0F && wideBottomLayerHoleChance <= 1.0F, "wideBottomLayerHoleChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(cornerHoleChance >= 0.0F && cornerHoleChance <= 1.0F, "cornerHoleChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(hangingLeavesChance >= 0.0F && hangingLeavesChance <= 1.0F, "hangingLeavesChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(hangingLeavesExtensionChance >= 0.0F && hangingLeavesExtensionChance <= 1.0F, "hangingLeavesExtensionChance must be between 0.0 and 1.0");
            return of(radius, offset, height, wideBottomLayerHoleChance, cornerHoleChance, hangingLeavesChance, hangingLeavesExtensionChance);
        }
    }
}
