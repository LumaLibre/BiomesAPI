package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the block at the specified offset can be replaced by placing blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#replaceable">Block predicate (replaceable)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReplaceablePredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

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
     * Creates a new replaceable predicate.
     * @param offset the offset
     * @return the replaceable predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceablePredicate of(BlockVector offset) {
        record Holder() {
            static final ConstructWireProvider<ReplaceablePredicate> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.ReplaceablePredicateImpl");
        }
        return Holder.WIRE.construct(offset);
    }

    /**
     * Creates a new replaceable predicate with a zero offset.
     * @return the replaceable predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceablePredicate of() {
        return of(new BlockVector(0, 0, 0));
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

    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);

        public Builder() {}

        public Builder(ReplaceablePredicate predicate) {
            this.offset = predicate.offset();
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

        // Friendly

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
            this.offset = new BlockVector(x, y, z);
            return this;
        }

        /**
         * Builds the replaceable predicate.
         * @return the replaceable predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ReplaceablePredicate build() {
            return of(offset);
        }
    }
}
