package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places a creaking heart, replacing a trunk block in a position where all 6 surrounding blocks are in the
 * <code>#minecraft:logs</code> tag.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CreakingHeartDecorator extends TreeDecorator {

    /**
     * The probability of placing a creaking heart, between 0.0F and 1.0F (inclusive).
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
     * Creates a new creaking heart decorator.
     * @param probability the probability of placing a creaking heart, between 0.0F and 1.0F (inclusive)
     * @return a new creaking heart decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CreakingHeartDecorator of(float probability) {
        record Holder() {
            static final ConstructWireProvider<CreakingHeartDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.CreakingHeartDecoratorImpl");
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
     * Builder for {@link CreakingHeartDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;

        public Builder() {}

        public Builder(CreakingHeartDecorator decorator) {
            this.probability = decorator.probability();
        }

        /**
         * Sets the probability of placing a creaking heart, between 0.0F and 1.0F (inclusive).
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
         * Builds the creaking heart decorator.
         * @return a new creaking heart decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CreakingHeartDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            return of(probability);
        }
    }
}
