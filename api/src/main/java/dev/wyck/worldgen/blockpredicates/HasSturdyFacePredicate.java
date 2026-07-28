package dev.wyck.worldgen.blockpredicates;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the block at the specified offset has a full block supporting surface in the specified direction.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#has_sturdy_face">Block predicate (has sturdy face)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HasSturdyFacePredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The direction to check.
     * @return the direction
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockFace direction();

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
     * Creates a new has sturdy face predicate.
     * @param offset the offset
     * @param direction the direction
     * @return the has sturdy face predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HasSturdyFacePredicate of(BlockVector offset, BlockFace direction) {
        record Holder() {
            static final ConstructWireProvider<HasSturdyFacePredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.HasSturdyFacePredicateImpl");
        }
        return Holder.WIRE.construct(offset, direction);
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
     * Builder for {@link HasSturdyFacePredicate}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);
        private @Nullable BlockFace direction;

        public Builder() {}

        public Builder(HasSturdyFacePredicate predicate) {
            this.offset = predicate.offset();
            this.direction = predicate.direction();
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
         * Sets the direction.
         * @param direction the direction
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder direction(BlockFace direction) {
            this.direction = direction;
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
            return offset(new BlockVector(x, y, z));
        }

        /**
         * Builds the has sturdy face predicate.
         * @return the has sturdy face predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public HasSturdyFacePredicate build() {
            // skipping preconditions checks for offsets
            Preconditions.checkNotNull(direction, "direction must be set");
            return of(offset, direction);
        }
    }
}
