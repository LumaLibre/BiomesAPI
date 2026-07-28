package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a single beehive with the specified probability.
 * The beehive is always located 1 block below the lowest leaves block, but possibly on a different branch.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BeehiveDecorator extends TreeDecorator {

    /**
     * The probability of placing a beehive, between 0.0F and 1.0F (inclusive).
     * @return the probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

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
     * Creates a new beehive decorator.
     * @param probability the probability of placing a beehive, between 0.0F and 1.0F (inclusive)
     * @return a new beehive decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BeehiveDecorator of(float probability) {
        record Holder() {
            static final ConstructWireProvider<BeehiveDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.BeehiveDecoratorImpl");
        }
        return Holder.WIRE.construct(probability);
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
     * Builder for {@link BeehiveDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;

        public Builder() {}

        public Builder(BeehiveDecorator decorator) {
            this.probability = decorator.probability();
        }

        /**
         * Sets the probability of placing a beehive, between 0.0F and 1.0F (inclusive).
         * @param probability the probability
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Builds the beehive decorator.
         * @return a new beehive decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public BeehiveDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            return of(probability);
        }
    }
}
