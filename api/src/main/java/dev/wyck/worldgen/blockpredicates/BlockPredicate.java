package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A block predicate is a test for the state of a block at a given position in the world.
 * They are used by placement modifiers in placed features and various configured features.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate">Block predicate</a>
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface BlockPredicate extends Wrapper {

    @ApiStatus.Internal
    Decoder<BlockPredicate> DECODER = Decoder.create("dev.wyck.decode.worldgen.blockpredicates.BlockPredicateDecoders");

    /**
     * Negates this predicate.
     * @return the negated predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default NotPredicate not() {
        return NotPredicate.of(this);
    }

    /**
     * Starts building an {@link AllOfPredicate}, which matches only when every child predicate matches.
     * @return a new {@link AllOfPredicate.Builder}
     * @see AllOfPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AllOfPredicate.Builder allOf() {
        return AllOfPredicate.builder();
    }

    /**
     * Starts building an {@link AnyOfPredicate}, which matches when at least one child predicate matches.
     * @return a new {@link AnyOfPredicate.Builder}
     * @see AnyOfPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AnyOfPredicate.Builder anyOf() {
        return AnyOfPredicate.builder();
    }

    /**
     * Starts building a {@link HasSturdyFacePredicate}, which checks that the block at an offset has a
     * full supporting surface on a given face.
     * @return a new {@link HasSturdyFacePredicate.Builder}
     * @see HasSturdyFacePredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HasSturdyFacePredicate.Builder hasSturdyFace() {
        return HasSturdyFacePredicate.builder();
    }

    /**
     * Starts building an {@link InsideWorldBoundsPredicate}, which checks that the position (with offset)
     * is within the world's height limits.
     * @return a new {@link InsideWorldBoundsPredicate.Builder}
     * @see InsideWorldBoundsPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static InsideWorldBoundsPredicate.Builder insideWorldBounds() {
        return InsideWorldBoundsPredicate.builder();
    }

    /**
     * Starts building a {@link MatchingBiomesPredicate}, which checks that the biome at the block's
     * position is one of the specified biomes.
     * @return a new {@link MatchingBiomesPredicate.Builder}
     * @see MatchingBiomesPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBiomesPredicate.Builder matchingBiomes() {
        return MatchingBiomesPredicate.builder();
    }

    /**
     * Starts building a {@link MatchingBlockTagPredicate}, which checks that the block at an offset is in
     * the given block tag.
     * @return a new {@link MatchingBlockTagPredicate.Builder}
     * @see MatchingBlockTagPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate.Builder matchingBlockTag() {
        return MatchingBlockTagPredicate.builder();
    }

    /**
     * Creates a {@link MatchingBlockTagPredicate} that matches the block at the given offset.
     * @param tag the tag to match
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate matchingBlockTag(Tag<Material> tag) {
        return MatchingBlockTagPredicate.of(tag);
    }

    /**
     * Creates a {@link MatchingBlockTagPredicate} that matches the block at the given offset.
     * @param tag the tag to match
     * @return the matching block tag predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlockTagPredicate matchingBlockTag(ResourceKey tag) {
        return MatchingBlockTagPredicate.of(tag);
    }

    /**
     * Starts building a {@link MatchingBlocksPredicate}, which checks that the block at an offset is one
     * of the specified blocks.
     * @return a new {@link MatchingBlocksPredicate.Builder}
     * @see MatchingBlocksPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBlocksPredicate.Builder matchingBlocks() {
        return MatchingBlocksPredicate.builder();
    }

    /**
     * Starts building a {@link MatchingFluidsPredicate}, which checks that the fluid at an offset is one
     * of the specified fluids.
     * @return a new {@link MatchingFluidsPredicate.Builder}
     * @see MatchingFluidsPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingFluidsPredicate.Builder matchingFluids() {
        return MatchingFluidsPredicate.builder();
    }

    /**
     * Creates a {@link NotPredicate} that inverts the given predicate.
     * @param predicate the predicate to invert
     * @return the negated predicate
     * @see NotPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NotPredicate not(BlockPredicate predicate) {
        return NotPredicate.of(predicate);
    }

    /**
     * Starts building a {@link ReplaceablePredicate}, which checks that the block at an offset can be
     * replaced by placing blocks.
     * @return a new {@link ReplaceablePredicate.Builder}
     * @see ReplaceablePredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceablePredicate.Builder replaceable() {
        return ReplaceablePredicate.builder();
    }

    /**
     * Starts building a {@link SolidPredicate}, which checks that the block at an offset is solid.
     * @return a new {@link SolidPredicate.Builder}
     * @see SolidPredicate
     * @since 3.0.0
     * @deprecated Deprecated in Minecraft in favor of {@link #hasSturdyFace()}.
     */
    @Deprecated
    @AsOf("3.0.0")
    static SolidPredicate.Builder solid() {
        return SolidPredicate.builder();
    }

    /**
     * Returns the {@link TrueBlockPredicate} singleton, which always matches regardless of the block.
     * @return the always-matching predicate
     * @see TrueBlockPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrueBlockPredicate alwaysTrue() {
        return TrueBlockPredicate.INSTANCE;
    }

    /**
     * Starts building a {@link WouldSurvivePredicate}, which checks that a block state would be a valid
     * placement at the given offset.
     * @return a new {@link WouldSurvivePredicate.Builder}
     * @see WouldSurvivePredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WouldSurvivePredicate.Builder wouldSurvive() {
        return WouldSurvivePredicate.builder();
    }

    /**
     * Reads a Minecraft block predicate into a wrapper.
     * @param minecraftPredicate the Minecraft predicate to read
     * @return the wrapper for the predicate
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BlockPredicate decode(Object minecraftPredicate) {
        return DECODER.decode(minecraftPredicate);
    }

}
