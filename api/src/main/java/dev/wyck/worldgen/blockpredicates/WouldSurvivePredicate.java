package dev.wyck.worldgen.blockpredicates;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the specified block state would be a valid placement at the given offset.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#would_survive">Block predicate (would survive)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface WouldSurvivePredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The block state to test for survival.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData state();

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
     * Creates a new would survive predicate.
     * @param offset the offset
     * @param state the block state to test
     * @return the would survive predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WouldSurvivePredicate of(BlockVector offset, BlockData state) {
        record Holder() {
            static final ConstructWireProvider<WouldSurvivePredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.WouldSurvivePredicateImpl");
        }
        return Holder.WIRE.construct(offset, state);
    }

    /**
     * Creates a new would survive predicate with a zero offset.
     * @param state the block state to test
     * @return the would survive predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WouldSurvivePredicate of(BlockData state) {
        return of(new BlockVector(0, 0, 0), state);
    }

    /**
     * Creates a new would survive predicate.
     * @param offset the offset
     * @param material the material to test
     * @return the would survive predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WouldSurvivePredicate of(BlockVector offset, Material material) {
        return of(offset, BukkitBootstrapUtil.util().createBlockData(material));
    }

    /**
     * Creates a new would survive predicate with a zero offset.
     * @param material the material to test
     * @return the would survive predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WouldSurvivePredicate of(Material material) {
        return of(new BlockVector(0, 0, 0), material);
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
        private @Nullable BlockData state;

        public Builder() {}

        public Builder(WouldSurvivePredicate predicate) {
            this.offset = predicate.offset();
            this.state = predicate.state();
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
         * Sets the block state to test.
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the block state to test.
         * @param material the material
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.state = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Builds the would survive predicate.
         * @return the would survive predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public WouldSurvivePredicate build() {
            Preconditions.checkNotNull(state, "state must be set");
            return of(offset, state);
        }
    }
}