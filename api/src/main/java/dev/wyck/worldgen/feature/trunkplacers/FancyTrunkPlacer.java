package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a straight trunk of <code>(height + 2) * 0.618</code> and branches in random directions.
 * Foliage is attached to the end of all branches that are at least 20% up the tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FancyTrunkPlacer extends TrunkPlacer {

    /**
     * Converts this object back to a builder.
     * @return the builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new fancy trunk placer.
     * @param baseHeight the height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @return a new fancy trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FancyTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        record Holder() {
            static final ConstructWireProvider<FancyTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.FancyTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB);
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
     * Builder for {@link FancyTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, FancyTrunkPlacer> {
        public Builder() {}

        public Builder(FancyTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected FancyTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
