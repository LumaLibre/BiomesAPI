package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.block.BlockFace;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Attaches blocks to exposed faces of all trunk blocks in specified directions.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AttachedToLogsDecorator extends TreeDecorator {

    /**
     * The probability of attaching a block, between 0.0F and 1.0F (inclusive).
     * @return the probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * The block of the decoration.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider blockProvider();

    /**
     * The directions to generate. Cannot be empty.
     * @return the directions
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockFace> directions();

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
     * Creates a new attached to logs decorator.
     * @param probability the probability of attaching a block, between 0.0F and 1.0F (inclusive)
     * @param blockProvider the block of the decoration
     * @param directions the directions to generate, cannot be empty
     * @return a new attached to logs decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AttachedToLogsDecorator of(float probability, BlockStateProvider blockProvider, List<BlockFace> directions) {
        record Holder() {
            static final ConstructWireProvider<AttachedToLogsDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.AttachedToLogsDecoratorImpl");
        }
        return Holder.WIRE.construct(probability, blockProvider, directions);
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
     * Builder for {@link AttachedToLogsDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;
        private @Nullable BlockStateProvider blockProvider = null;
        private List<BlockFace> directions = new ArrayList<>();

        public Builder() {}

        public Builder(AttachedToLogsDecorator decorator) {
            this.probability = decorator.probability();
            this.blockProvider = decorator.blockProvider();
            this.directions = decorator.directions();
        }

        /**
         * Sets the probability of attaching a block, between 0.0F and 1.0F (inclusive).
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
         * Sets the block of the decoration.
         * @param blockProvider the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder blockProvider(BlockStateProvider blockProvider) {
            this.blockProvider = blockProvider;
            return this;
        }

        /**
         * Sets the directions to generate. Cannot be empty.
         * @param directions the directions
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder directions(List<BlockFace> directions) {
            this.directions = directions;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds one or more directions to generate.
         * @param direction the directions to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder direction(BlockFace... direction) {
            Collections.addAll(this.directions, direction);
            return this;
        }

        /**
         * Builds the attached to logs decorator.
         * @return a new attached to logs decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public AttachedToLogsDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0F and 1.0F");
            Preconditions.checkNotNull(blockProvider, "blockProvider must be set");
            Preconditions.checkArgument(!directions.isEmpty(), "directions must not be empty");
            return of(probability, blockProvider, directions);
        }
    }
}
