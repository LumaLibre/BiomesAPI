package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places vines attached to any foliage blocks.
 * Each vine is extended by 4 blocks downwards if possible.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface LeaveVineDecorator extends TreeDecorator {

    /**
     * The probability of placing a vine at any given position, between 0.0F and 1.0F (inclusive).
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
     * Creates a new leave vine decorator.
     * @param probability the probability of placing a vine at any given position, between 0.0F and 1.0F (inclusive)
     * @return a new leave vine decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LeaveVineDecorator of(float probability) {
        record Holder() {
            static final ConstructWireProvider<LeaveVineDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.LeaveVineDecoratorImpl");
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
     * Builder for {@link LeaveVineDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;

        public Builder() {}

        public Builder(LeaveVineDecorator decorator) {
            this.probability = decorator.probability();
        }

        /**
         * Sets the probability of placing a vine at any given position, between 0.0F and 1.0F (inclusive).
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
         * Builds the leave vine decorator.
         * @return a new leave vine decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public LeaveVineDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            return of(probability);
        }
    }
}
