package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the <code>minecraft:pale_moss_patch</code> configured feature at the position of the lowest log with
 * probability {@link #groundProbability()}.
 * Adds hanging moss to the bottom of trunk and foliage blocks with probability {@link #trunkProbability()} and
 * {@link #leavesProbability()} respectively.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface PaleMossDecorator extends TreeDecorator {

    /**
     * The probability of adding hanging moss to the bottom of foliage blocks, between 0.0F and 1.0F (inclusive).
     * @return the leaves probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float leavesProbability();

    /**
     * The probability of adding hanging moss to the bottom of trunk blocks, between 0.0F and 1.0F (inclusive).
     * @return the trunk probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float trunkProbability();

    /**
     * The probability of placing the <code>minecraft:pale_moss_patch</code> configured feature at the position of the
     * lowest log, between 0.0F and 1.0F (inclusive).
     * @return the ground probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float groundProbability();

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
     * Creates a new pale moss decorator.
     * @param leavesProbability the probability of adding hanging moss to the bottom of foliage blocks, between 0.0F and 1.0F (inclusive)
     * @param trunkProbability the probability of adding hanging moss to the bottom of trunk blocks, between 0.0F and 1.0F (inclusive)
     * @param groundProbability the probability of placing the pale moss patch at the lowest log, between 0.0F and 1.0F (inclusive)
     * @return a new pale moss decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PaleMossDecorator of(float leavesProbability, float trunkProbability, float groundProbability) {
        record Holder() {
            static final ConstructWireProvider<PaleMossDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.PaleMossDecoratorImpl");
        }
        return Holder.WIRE.construct(leavesProbability, trunkProbability, groundProbability);
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
     * Builder for {@link PaleMossDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float leavesProbability = 0.0F;
        private float trunkProbability = 0.0F;
        private float groundProbability = 0.0F;

        public Builder() {}

        public Builder(PaleMossDecorator decorator) {
            this.leavesProbability = decorator.leavesProbability();
            this.trunkProbability = decorator.trunkProbability();
            this.groundProbability = decorator.groundProbability();
        }

        /**
         * Sets the probability of adding hanging moss to the bottom of foliage blocks, between 0.0F and 1.0F (inclusive).
         * @param leavesProbability the leaves probability
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder leavesProbability(float leavesProbability) {
            this.leavesProbability = leavesProbability;
            return this;
        }

        /**
         * Sets the probability of adding hanging moss to the bottom of trunk blocks, between 0.0F and 1.0F (inclusive).
         * @param trunkProbability the trunk probability
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder trunkProbability(float trunkProbability) {
            this.trunkProbability = trunkProbability;
            return this;
        }

        /**
         * Sets the probability of placing the <code>minecraft:pale_moss_patch</code> configured feature at the position
         * of the lowest log, between 0.0F and 1.0F (inclusive).
         * @param groundProbability the ground probability
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder groundProbability(float groundProbability) {
            this.groundProbability = groundProbability;
            return this;
        }

        /**
         * Builds the pale moss decorator.
         * @return a new pale moss decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public PaleMossDecorator build() {
            Preconditions.checkArgument(leavesProbability >= 0.0F && leavesProbability <= 1.0F, "leavesProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(trunkProbability >= 0.0F && trunkProbability <= 1.0F, "trunkProbability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(groundProbability >= 0.0F && groundProbability <= 1.0F, "groundProbability must be between 0.0F and 1.0F");
            return of(leavesProbability, trunkProbability, groundProbability);
        }
    }
}
