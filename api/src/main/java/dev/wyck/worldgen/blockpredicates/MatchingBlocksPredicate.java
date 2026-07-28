package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Checks if the block at the specified offset is one of the specified blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#matching_blocks">Block predicate (matching blocks)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MatchingBlocksPredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The blocks to match against.
     * @return the blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Material> blocks();

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
     * Creates a new matching blocks predicate.
     * @param offset the offset
     * @param blocks the blocks to match against
     * @return the matching blocks predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlocksPredicate of(BlockVector offset, List<Material> blocks) {
        record Holder() {
            static final ConstructWireProvider<MatchingBlocksPredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicateImpl");
        }
        return Holder.WIRE.construct(offset, blocks);
    }

    /**
     * Creates a new matching blocks predicate with a zero offset.
     * @param blocks the blocks to match against
     * @return the matching blocks predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlocksPredicate of(List<Material> blocks) {
        return of(new BlockVector(0, 0, 0), blocks);
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
     * Builder for {@link MatchingBlocksPredicate}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);
        private List<Material> blocks = new ArrayList<>();

        public Builder() {}

        public Builder(MatchingBlocksPredicate predicate) {
            this.offset = predicate.offset();
            this.blocks.addAll(predicate.blocks());
        }

        /**
         * Sets the offset.
         * @param offset the offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder offset(BlockVector offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Sets the blocks to match against.
         * @param blocks the blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder blocks(List<Material> blocks) {
            this.blocks = blocks;
            return this;
        }

        /**
         * Sets the blocks to match against.
         * @param blocks the blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder blocks(Material... blocks) {
            this.blocks = new ArrayList<>(List.of(blocks));
            return this;
        }

        /**
         * Adds a block to match against.
         * @param block the block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder block(Material block) {
            this.blocks.add(block);
            return this;
        }

        /**
         * Adds multiple blocks to match against.
         * @param blocks the blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder block(Material... blocks) {
            Collections.addAll(this.blocks, blocks);
            return this;
        }

        /**
         * Sets the offset.
         * @param x the x offset
         * @param y the y offset
         * @param z the z offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder offset(int x, int y, int z) {
            return offset(new BlockVector(x, y, z));
        }

        /**
         * Builds the matching blocks predicate.
         * @return the matching blocks predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MatchingBlocksPredicate build() {
            return of(offset, blocks);
        }
    }
}
