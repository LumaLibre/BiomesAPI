package dev.wyck.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a trunk of the specified height.
 * The topmost 2 blocks have a chance to be moved by one block each.
 * On top of the trunk a straight branch of length {@link #bendLength()} is generated.
 * Foliage is attached to all block of the top branch and any trunk block that is higher than {@link #minHeightForLeaves()}.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BendingTrunkPlacer extends TrunkPlacer {

    /**
     * The minimum height for leaves.
     * Must be a positive integer.
     * @return the minimum height for leaves
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int minHeightForLeaves();

    /**
     * The length of the bend between 1 and 64.
     * @return the bend length
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider bendLength();

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
     * Creates a new bending trunk placer.
     * @param minHeight the minimum height of the trunk
     * @param maxHeight the maximum height of the trunk
     * @param bendLength the length of the bend between 1 and 64
     * @param minHeightForLeaves the minimum height for leaves
     * @param bendLengthProvider the length of the bend between 1 and 64
     * @return a new bending trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BendingTrunkPlacer of(int minHeight, int maxHeight, int bendLength, int minHeightForLeaves, IntProvider bendLengthProvider) {
        record Holder() {
            static final ConstructWireProvider<BendingTrunkPlacer> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.trunkplacers.BendingTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(minHeight, maxHeight, bendLength, minHeightForLeaves, bendLengthProvider);
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
     * Builder for {@link BendingTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, BendingTrunkPlacer> {
        // impl defaults
        private int minHeightForLeaves = 1;
        private IntProvider bendLength = IntProvider.constant(1);

        public Builder() {}

        public Builder(BendingTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.minHeightForLeaves = trunkPlacer.minHeightForLeaves();
            this.bendLength = trunkPlacer.bendLength();
        }

        @AsOf("3.0.0")
        public Builder minHeightForLeaves(int minHeightForLeaves) {
            this.minHeightForLeaves = minHeightForLeaves;
            return this;
        }

        @AsOf("3.0.0")
        public Builder bendLength(IntProvider bendLength) {
            this.bendLength = bendLength;
            return this;
        }

        @Override
        protected BendingTrunkPlacer create() {
            Preconditions.checkArgument(this.minHeightForLeaves >= 0, "minHeightForLeaves should be positive");
            Preconditions.checkArgument(this.bendLength.minInclusive() < 1, "bendLength must be greater than 0");
            Preconditions.checkArgument(this.bendLength.maxInclusive() <= 64, "bendLength must be less than or equal to 64");
            return of(baseHeight, heightRandA, heightRandB, minHeightForLeaves, bendLength);
        }
    }
}
