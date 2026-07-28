package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the foliage of a pine tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface PineFoliagePlacer extends FoliagePlacer {

    /**
     * The foliage's height, between 0 and 24 (inclusive).
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider height();

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
     * Creates a new pine foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param height the foliage's height, between 0 and 24 (inclusive)
     * @return a new pine foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PineFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider height) {
        record Holder() {
            static final ConstructWireProvider<PineFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.PineFoliagePlacerImpl");
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
     * Builder for {@link PineFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, PineFoliagePlacer> {

        private IntProvider height = IntProvider.constant(0);

        public Builder() {}

        public Builder(PineFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.height = foliagePlacer.height();
        }

        /**
         * Sets the foliage's height, between 0 and 24 (inclusive).
         * @param height the height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        @Override
        protected PineFoliagePlacer create() {
            Preconditions.checkArgument(height.minInclusive() >= 0 && height.maxInclusive() <= 24, "height must be between 0 and 24");
            return of(radius, offset, height);
        }
    }
}
