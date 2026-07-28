package dev.wyck.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.UniformInt;
import org.jspecify.annotations.NullMarked;

/**
 * Places a U shape trunk of multiple branches.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CherryTrunkPlacer extends TrunkPlacer {

    /**
     * The number of branches between 1 and 3.
     * @return the number of branches
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider branchCount();

    /**
     * The horizontal length of the branches between 2 and 16.
     * @return the horizontal length of the branches
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider branchHorizontalLength();

    /**
     * The offset of the branches from the top between -16 and 16.
     * @return the offset of the branches
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    UniformInt branchStartOffsetFromTop();

    /**
     * The offset of the branches from the top between -16 and 16.
     * @return the offset of the branches
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider branchEndOffsetFromTop();

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
     * Creates a new cherry trunk placer.
     * @param baseHeight the base height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @param branchCount the number of branches
     * @param branchHorizontalLength the horizontal length of the branches
     * @param branchStartOffsetFromTop the offset of the branches from the top
     * @param branchEndOffsetFromTop the offset of the branches from the top
     * @return a new cherry trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CherryTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB, IntProvider branchCount, IntProvider branchHorizontalLength, IntProvider branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop) {
        record Holder() {
            static final ConstructWireProvider<CherryTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.CherryTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB, branchCount, branchHorizontalLength, branchStartOffsetFromTop, branchEndOffsetFromTop);
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
     * Builder for {@link CherryTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, CherryTrunkPlacer> {
        private IntProvider branchCount = IntProvider.constant(1);
        private IntProvider branchHorizontalLength = IntProvider.constant(2);
        private UniformInt branchStartOffsetFromTop = IntProvider.uniform(-16, 0);
        private IntProvider branchEndOffsetFromTop = IntProvider.constant(0);

        public Builder() {}

        public Builder(CherryTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.branchCount = trunkPlacer.branchCount();
            this.branchHorizontalLength = trunkPlacer.branchHorizontalLength();
            this.branchStartOffsetFromTop = trunkPlacer.branchStartOffsetFromTop();
            this.branchEndOffsetFromTop = trunkPlacer.branchEndOffsetFromTop();
        }

        /**
         * Sets the number of branches.
         * @param branchCount the number of branches
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder branchCount(IntProvider branchCount) {
            this.branchCount = branchCount;
            return this;
        }

        /**
         * Sets the horizontal length of the branches.
         * @param branchHorizontalLength the horizontal length of the branches
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder branchHorizontalLength(IntProvider branchHorizontalLength) {
            this.branchHorizontalLength = branchHorizontalLength;
            return this;
        }

        /**
         * Sets the offset of the branches from the top.
         * @param branchStartOffsetFromTop the offset of the branches from the top
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder branchStartOffsetFromTop(UniformInt branchStartOffsetFromTop) {
            this.branchStartOffsetFromTop = branchStartOffsetFromTop;
            return this;
        }

        /**
         * Sets the offset of the branches from the top.
         * @param branchEndOffsetFromTop the offset of the branches from the top
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder branchEndOffsetFromTop(IntProvider branchEndOffsetFromTop) {
            this.branchEndOffsetFromTop = branchEndOffsetFromTop;
            return this;
        }

        @Override
        protected CherryTrunkPlacer create() {
            Preconditions.checkArgument(branchCount.minInclusive() >= 1 && branchCount.maxInclusive() <= 3, "branchCount must be between 1 and 3");
            Preconditions.checkArgument(branchHorizontalLength.minInclusive() >= 2 && branchHorizontalLength.maxInclusive() <= 16, "branchHorizontalLength must be between 2 and 16");
            Preconditions.checkArgument(branchStartOffsetFromTop.minInclusive() >= -16 && branchStartOffsetFromTop.maxInclusive() <= 0, "branchStartOffsetFromTop must be between -16 and 0");
            Preconditions.checkArgument(branchEndOffsetFromTop.minInclusive() >= -16 && branchEndOffsetFromTop.maxInclusive() <= 16, "branchEndOffsetFromTop must be between -16 and 16");
            return of(baseHeight, heightRandA, heightRandB, branchCount, branchHorizontalLength, branchStartOffsetFromTop, branchEndOffsetFromTop);
        }
    }

}
