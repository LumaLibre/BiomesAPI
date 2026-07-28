package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the foliage of a mega pine (giant spruce) tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MegaPineFoliagePlacer extends FoliagePlacer {

    /**
     * The height of the crown, between 0 and 24 (inclusive).
     * @return the crown height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider crownHeight();

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
     * Creates a new mega pine foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param crownHeight the height of the crown, between 0 and 24 (inclusive)
     * @return a new mega pine foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MegaPineFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        record Holder() {
            static final ConstructWireProvider<MegaPineFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.MegaPineFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, crownHeight);
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
     * Builder for {@link MegaPineFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, MegaPineFoliagePlacer> {

        private IntProvider crownHeight = IntProvider.constant(0);

        public Builder() {}

        public Builder(MegaPineFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.crownHeight = foliagePlacer.crownHeight();
        }

        /**
         * Sets the height of the crown, between 0 and 24 (inclusive).
         * @param crownHeight the crown height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder crownHeight(IntProvider crownHeight) {
            this.crownHeight = crownHeight;
            return this;
        }

        @Override
        protected MegaPineFoliagePlacer create() {
            Preconditions.checkArgument(crownHeight.minInclusive() >= 0 && crownHeight.maxInclusive() <= 24, "crownHeight must be between 0 and 24");
            return of(radius, offset, crownHeight);
        }
    }
}
