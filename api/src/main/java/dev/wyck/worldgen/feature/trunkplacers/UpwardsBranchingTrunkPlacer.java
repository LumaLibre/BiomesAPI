package dev.wyck.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Places a straight trunk with branches extending up and outwards from the trunk.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface UpwardsBranchingTrunkPlacer extends TrunkPlacer {

    /**
     * The number of steps to generate extra branches.
     * Must be positive.
     * @return the number of steps to generate extra branches
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider extraBranchSteps();

    /**
     * The probability of each log producing a branch between 0.0 and 1.0.
     * @return the probability of each log producing a branch
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float placeBranchPerLogProbability();

    /**
     * Generates extra branch length.
     * Must be positive.
     * @return the extra branch length
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider extraBranchLength();

    /**
     * Represents blocks that tree trunks can grow through.
     * @return the blocks that tree trunks can grow through
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> canGrowThrough();

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
     * Creates a new upwards branching trunk placer.
     * @param baseHeight the base height of the trunk
     * @param heightRandA the random height modifier
     * @param heightRandB the random height modifier
     * @param extraBranchSteps the number of steps to generate extra branches
     * @param placeBranchPerLogProbability the probability of each log producing a branch
     * @param extraBranchLength the extra branch length
     * @param canGrowThrough the blocks that tree trunks can grow through
     * @return a new upwards branching trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UpwardsBranchingTrunkPlacer of(int baseHeight, int heightRandA, int heightRandB, IntProvider extraBranchSteps, float placeBranchPerLogProbability, IntProvider extraBranchLength, Set<Material> canGrowThrough) {
        record Holder() {
            static final ConstructWireProvider<UpwardsBranchingTrunkPlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.trunkplacers.UpwardsBranchingTrunkPlacerImpl");
        }
        return Holder.WIRE.construct(baseHeight, heightRandA, heightRandB, extraBranchSteps, placeBranchPerLogProbability, extraBranchLength, canGrowThrough);
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
     * Builder for {@link UpwardsBranchingTrunkPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends TrunkPlacerBuilder<Builder, UpwardsBranchingTrunkPlacer> {
        private IntProvider extraBranchSteps = IntProvider.constant(0);
        private float placeBranchPerLogProbability = 0.0F;
        private IntProvider extraBranchLength = IntProvider.constant(0);
        private Set<Material> canGrowThrough = new HashSet<>();

        public Builder() {}

        public Builder(UpwardsBranchingTrunkPlacer trunkPlacer) {
            super(trunkPlacer);
            this.extraBranchSteps = trunkPlacer.extraBranchSteps();
            this.placeBranchPerLogProbability = trunkPlacer.placeBranchPerLogProbability();
            this.extraBranchLength = trunkPlacer.extraBranchLength();
            this.canGrowThrough = new HashSet<>(trunkPlacer.canGrowThrough());
        }

        /**
         * Sets the number of steps to generate extra branches.
         * @param extraBranchSteps the number of steps to generate extra branches
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder extraBranchSteps(IntProvider extraBranchSteps) {
            this.extraBranchSteps = extraBranchSteps;
            return this;
        }

        /**
         * Sets the probability of each log producing a branch.
         * @param placeBranchPerLogProbability the probability of each log producing a branch
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder placeBranchPerLogProbability(float placeBranchPerLogProbability) {
            this.placeBranchPerLogProbability = placeBranchPerLogProbability;
            return this;
        }

        /**
         * Sets the extra branch length.
         * @param extraBranchLength the extra branch length
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder extraBranchLength(IntProvider extraBranchLength) {
            this.extraBranchLength = extraBranchLength;
            return this;
        }

        /**
         * Sets the blocks that tree trunks can grow through.
         * @param canGrowThrough the blocks that tree trunks can grow through
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canGrowThrough(Set<Material> canGrowThrough) {
            this.canGrowThrough = canGrowThrough;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds blocks that tree trunks can grow through.
         * @param canGrowThrough the blocks that tree trunks can grow through
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canGrowThrough(Material... canGrowThrough) {
            Collections.addAll(this.canGrowThrough, canGrowThrough);
            return this;
        }

        @Override
        protected UpwardsBranchingTrunkPlacer create() {
            Preconditions.checkArgument(extraBranchSteps.minInclusive() >= 0, "extraBranchSteps must be positive");
            Preconditions.checkArgument(placeBranchPerLogProbability >= 0.0F && placeBranchPerLogProbability <= 1.0F, "placeBranchPerLogProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(extraBranchLength.minInclusive() >= 0, "extraBranchLength must be positive");
            return of(baseHeight, heightRandA, heightRandB, extraBranchSteps, placeBranchPerLogProbability, extraBranchLength, canGrowThrough);
        }
    }
}
