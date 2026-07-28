package dev.wyck.worldgen.feature.trunkplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a straight trunk of specified height.
 * Foliage is attached to be block above the highest trunk block.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface StraightTrunkPlacer extends TrunkPlacer {

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
     * Creates a new straight trunk placer.
     * @param baseHeight the height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @return a new straight trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StraightTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB) {
        record Holder() {
            static final ConstructWireProvider<StraightTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.StraightTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB);
    }

    /**
     * Creates a new straight trunk placer builder.
     * @return a new straight trunk placer builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link StraightTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacer.TrunkPlacerBuilder<Builder, StraightTrunkPlacer> {
        public Builder() {}

        public Builder(StraightTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
        }

        @Override
        protected StraightTrunkPlacer create() {
            return of(baseHeight, heightRandA, heightRandB);
        }
    }
}
