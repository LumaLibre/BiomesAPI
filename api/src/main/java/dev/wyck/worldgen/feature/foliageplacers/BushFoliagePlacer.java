package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a bush-shaped blob of foliage.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BushFoliagePlacer extends FoliagePlacer {

    /**
     * The foliage's height, between 0 and 16 (inclusive).
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int height();

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
     * Creates a new bush foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param height the foliage's height, between 0 and 16 (inclusive)
     * @return a new bush foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BushFoliagePlacer of(IntProvider radius, IntProvider offset, int height) {
        record Holder() {
            static final ConstructWireProvider<BushFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.BushFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, height);
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
     * Builder for {@link BushFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, BushFoliagePlacer> {

        private int height;

        public Builder() {}

        public Builder(BushFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
        }

        /**
         * Sets the foliage's height, between 0 and 16 (inclusive).
         * @param height the height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        @Override
        protected BushFoliagePlacer create() {
            Preconditions.checkArgument(height >= 0 && height <= 16, "height must be between 0 and 16");
            return of(radius, offset, height);
        }
    }
}
