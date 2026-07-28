package dev.wyck.worldgen.blockpredicates;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Checks if the block at the specified offset is in the given block tag.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#matching_block_tag">Block predicate (matching block tag)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MatchingBlockTagPredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The block tag to check for.
     * @return the tag
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey tag();

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
     * Creates a new matching block tag predicate.
     * @param offset the offset
     * @param tag the tag
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate of(BlockVector offset, ResourceKey tag) {
        record Holder() {
            static final ConstructWireProvider<MatchingBlockTagPredicate> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.MatchingBlockTagPredicateImpl");
        }
        return Holder.WIRE.construct(offset, tag);
    }

    /**
     * Creates a new matching block tag predicate with a zero offset.
     * @param tag the tag
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate of(ResourceKey tag) {
        return of(new BlockVector(), tag);
    }

    /**
     * Creates a new matching block tag predicate from a Bukkit tag.
     * @param offset the offset
     * @param tag the tag
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate of(BlockVector offset, Tag<Material> tag) {
        return of(offset, ResourceKey.of(tag.key().asString()));
    }

    /**
     * Creates a new matching block tag predicate with a zero offset from a Bukkit tag.
     * @param tag the tag
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate of(Tag<Material> tag) {
        return of(new BlockVector(), ResourceKey.of(tag.key().asString()));
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
     * Builder for {@link MatchingBlockTagPredicate}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);
        private @Nullable ResourceKey tag;

        public Builder() {}

        public Builder(MatchingBlockTagPredicate predicate) {
            this.offset = predicate.offset();
            this.tag = predicate.tag();
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
         * Sets the tag.
         * @param tag the tag
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder tag(ResourceKey tag) {
            this.tag = tag;
            return this;
        }

        /**
         * Sets the tag from a Bukkit tag.
         * @param tag the tag
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder tag(Tag<Material> tag) {
            this.tag = ResourceKey.of(tag.key().asString());
            return this;
        }

        /**
         * Builds the matching block tag predicate.
         * @return the matching block tag predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MatchingBlockTagPredicate build() {
            Preconditions.checkNotNull(tag, "tag must be set");
            return of(offset, tag);
        }
    }
}
