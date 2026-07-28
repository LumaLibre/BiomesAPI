package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a 2×2 trunk of the specified height, except the topmost block is only a single block.
 * Foliage is attached to be block above the highest trunk block.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface GiantTrunkPlacer extends TrunkPlacer {

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
     * Creates a new giant trunk placer.
     * @param baseHeight the height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @return a new giant trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GiantTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        record Holder() {
            static final ConstructWireProvider<GiantTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.GiantTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    /**
     * Creates a new builder
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link GiantTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, GiantTrunkPlacer> {
        public Builder() {}

        public Builder(GiantTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected GiantTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
