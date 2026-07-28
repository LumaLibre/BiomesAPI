package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Extends the {@link GiantTrunkPlacer} with branches of length 5 in random directions in the top half of the trunk.
 * Foliage is additionally attached to the end of each branch.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MegaJungleTrunkPlacer extends TrunkPlacer {

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
     * Creates a new mega jungle trunk placer.
     * @param baseHeight the height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @return a new mega jungle trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MegaJungleTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        record Holder() {
            static final ConstructWireProvider<MegaJungleTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.MegaJungleTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    /**
     * Creates a new mega jungle trunk placer builder.
     * @return a new mega jungle trunk placer builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link MegaJungleTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, MegaJungleTrunkPlacer> {
        public Builder() {}

        public Builder(MegaJungleTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected MegaJungleTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
