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
 * Attaches blocks to exposed faces of all foliage blocks in specified directions.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AttachedToLeavesDecorator extends TreeDecorator {

    /**
     * The probability of attaching a block, between 0.0F and 1.0F (inclusive).
     * @return the probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * The minimum horizontal distance between two decorations, between 0 and 16 (inclusive).
     * @return the horizontal exclusion radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int exclusionRadiusXZ();

    /**
     * The minimum vertical distance between two decorations, between 0 and 16 (inclusive).
     * @return the vertical exclusion radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int exclusionRadiusY();

    /**
     * The block of the decoration.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider blockProvider();

    /**
     * The number of empty blocks required by the decoration, between 0 and 16 (inclusive).
     * @return the number of required empty blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int requiredEmptyBlocks();

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
     * Creates a new attached to leaves decorator.
     * @param probability the probability of attaching a block, between 0.0F and 1.0F (inclusive)
     * @param exclusionRadiusXZ the minimum horizontal distance between two decorations, between 0 and 16 (inclusive)
     * @param exclusionRadiusY the minimum vertical distance between two decorations, between 0 and 16 (inclusive)
     * @param blockProvider the block of the decoration
     * @param requiredEmptyBlocks the number of empty blocks required by the decoration, between 0 and 16 (inclusive)
     * @param directions the directions to generate, cannot be empty
     * @return a new attached to leaves decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AttachedToLeavesDecorator of(float probability, int exclusionRadiusXZ, int exclusionRadiusY, BlockStateProvider blockProvider, int requiredEmptyBlocks, List<BlockFace> directions) {
        record Holder() {
            static final ConstructWireProvider<AttachedToLeavesDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.AttachedToLeavesDecoratorImpl");
        }
        return Holder.WIRE.construct(probability, exclusionRadiusXZ, exclusionRadiusY, blockProvider, requiredEmptyBlocks, directions);
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
     * Builder for {@link AttachedToLeavesDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private float probability = 0.0F;
        private int exclusionRadiusXZ = 0;
        private int exclusionRadiusY = 0;
        private @Nullable BlockStateProvider blockProvider = null;
        private int requiredEmptyBlocks = 1;
        private List<BlockFace> directions = new ArrayList<>();

        public Builder() {}

        public Builder(AttachedToLeavesDecorator decorator) {
            this.probability = decorator.probability();
            this.exclusionRadiusXZ = decorator.exclusionRadiusXZ();
            this.exclusionRadiusY = decorator.exclusionRadiusY();
            this.blockProvider = decorator.blockProvider();
            this.requiredEmptyBlocks = decorator.requiredEmptyBlocks();
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
         * Sets the minimum horizontal distance between two decorations, between 0 and 16 (inclusive).
         * @param exclusionRadiusXZ the horizontal exclusion radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder exclusionRadiusXZ(int exclusionRadiusXZ) {
            this.exclusionRadiusXZ = exclusionRadiusXZ;
            return this;
        }

        /**
         * Sets the minimum vertical distance between two decorations, between 0 and 16 (inclusive).
         * @param exclusionRadiusY the vertical exclusion radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder exclusionRadiusY(int exclusionRadiusY) {
            this.exclusionRadiusY = exclusionRadiusY;
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
         * Sets the number of empty blocks required by the decoration, between 0 and 16 (inclusive).
         * @param requiredEmptyBlocks the number of required empty blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder requiredEmptyBlocks(int requiredEmptyBlocks) {
            this.requiredEmptyBlocks = requiredEmptyBlocks;
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
         * Builds the attached to leaves decorator.
         * @return a new attached to leaves decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public AttachedToLeavesDecorator build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "Probability must be between 0.0F and 1.0F");
            Preconditions.checkArgument(exclusionRadiusXZ >= 0 && exclusionRadiusXZ <= 16, "exclusionRadiusXZ must be between 0 and 16");
            Preconditions.checkArgument(exclusionRadiusY >= 0 && exclusionRadiusY <= 16, "exclusionRadiusY must be between 0 and 16");
            Preconditions.checkNotNull(blockProvider, "blockProvider must be set");
            Preconditions.checkArgument(requiredEmptyBlocks >= 1 && requiredEmptyBlocks <= 16, "requiredEmptyBlocks must be between 1 and 16");
            Preconditions.checkArgument(!directions.isEmpty(), "directions must not be empty");
            return of(probability, exclusionRadiusXZ, exclusionRadiusY, blockProvider, requiredEmptyBlocks, directions);
        }
    }
}
