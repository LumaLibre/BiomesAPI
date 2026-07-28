package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places cocoa beans on the bottom 3 blocks of the trunk with a probability of 25% for each face.
 * The specified probability determines how often any cocoa beans are placed.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CocoaDecorator extends TreeDecorator {

    /**
     * The probability that this decorator places any cocoa beans, between 0.0F and 1.0F (inclusive).
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
     * Creates a new cocoa decorator.
     * @param probability the probability that this decorator places any cocoa beans, between 0.0F and 1.0F (inclusive)
     * @return a new cocoa decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CocoaDecorator of(float probability) {
        record Holder() {
            static final ConstructWireProvider<CocoaDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.CocoaDecoratorImpl");
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
     * Builder for {@link CocoaDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;

        public Builder() {}

        public Builder(CocoaDecorator decorator) {
            this.probability = decorator.probability();
        }

        /**
         * Sets the probability that this decorator places any cocoa beans, between 0.0F and 1.0F (inclusive).
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
         * Builds the cocoa decorator.
         * @return a new cocoa decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CocoaDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            return of(probability);
        }
    }
}
