package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Randomly spreads foliage within the given radius and height, used by trees such as the mangrove.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomSpreadFoliagePlacer extends FoliagePlacer {

    /**
     * The foliage's height, between 1 and 512 (inclusive).
     * @return the foliage height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider foliageHeight();

    /**
     * The number of leaf placement attempts, between 0 and 256 (inclusive).
     * @return the number of leaf placement attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int leafPlacementAttempts();

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
     * Creates a new random spread foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @param foliageHeight the foliage's height, between 1 and 512 (inclusive)
     * @param leafPlacementAttempts the number of leaf placement attempts, between 0 and 256 (inclusive)
     * @return a new random spread foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomSpreadFoliagePlacer of(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
        record Holder() {
            static final ConstructWireProvider<RandomSpreadFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.RandomSpreadFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset, foliageHeight, leafPlacementAttempts);
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
     * Builder for {@link RandomSpreadFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, RandomSpreadFoliagePlacer> {

        private IntProvider foliageHeight = IntProvider.constant(1);
        private int leafPlacementAttempts;

        public Builder() {}

        public Builder(RandomSpreadFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
            this.foliageHeight = foliagePlacer.foliageHeight();
            this.leafPlacementAttempts = foliagePlacer.leafPlacementAttempts();
        }

        /**
         * Sets the foliage's height, between 1 and 512 (inclusive).
         * @param foliageHeight the foliage height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageHeight(IntProvider foliageHeight) {
            this.foliageHeight = foliageHeight;
            return this;
        }

        /**
         * Sets the number of leaf placement attempts, between 0 and 256 (inclusive).
         * @param leafPlacementAttempts the number of leaf placement attempts
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder leafPlacementAttempts(int leafPlacementAttempts) {
            this.leafPlacementAttempts = leafPlacementAttempts;
            return this;
        }

        @Override
        protected RandomSpreadFoliagePlacer create() {
            Preconditions.checkArgument(foliageHeight.minInclusive() >= 1 && foliageHeight.maxInclusive() <= 512, "foliageHeight must be between 1 and 512");
            Preconditions.checkArgument(leafPlacementAttempts >= 0 && leafPlacementAttempts <= 256, "leafPlacementAttempts must be between 0 and 256");
            return of(radius, offset, foliageHeight, leafPlacementAttempts);
        }
    }
}
