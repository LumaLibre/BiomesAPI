package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places cones starting from the top of the trunk plus the offset, with a radius that increases until it hits a
 * maximum radius, where it keeps generating frustums at maximum radius until the foliage is
 * {@link #trunkHeight()} + 1 tall.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SpruceFoliagePlacer extends FoliagePlacer {

    /**
     * The trunk height, between 0 and 24 (inclusive).
     * @return the trunk height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider trunkHeight();

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
     * Creates a new spruce foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param trunkHeight the trunk height, between 0 and 24 (inclusive)
     * @return a new spruce foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpruceFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
        record Holder() {
            static final ConstructWireProvider<SpruceFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.SpruceFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, trunkHeight);
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
     * Builder for {@link SpruceFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, SpruceFoliagePlacer> {

        private IntProvider trunkHeight = IntProvider.constant(0);

        public Builder() {}

        public Builder(SpruceFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.trunkHeight = foliagePlacer.trunkHeight();
        }

        /**
         * Sets the trunk height, between 0 and 24 (inclusive).
         * @param trunkHeight the trunk height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder trunkHeight(IntProvider trunkHeight) {
            this.trunkHeight = trunkHeight;
            return this;
        }

        @Override
        protected SpruceFoliagePlacer create() {
            Preconditions.checkArgument(trunkHeight.minInclusive() >= 0 && trunkHeight.maxInclusive() <= 24, "trunkHeight must be between 0 and 24");
            return of(radius, offset, trunkHeight);
        }
    }
}
