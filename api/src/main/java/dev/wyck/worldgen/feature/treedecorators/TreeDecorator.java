package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Decorators are used to add additional blocks to various parts of the tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TreeDecorator extends Wrapper {

    @ApiStatus.Internal
    Decoder<TreeDecorator> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.tree.TreeDecoratorDecoders");

    /**
     * Replaces blocks in the <code>#minecraft:dirt</code> tag around the tree with different blocks.
     * @param provider The block state provider to use.
     * @return The alter ground decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AlterGroundDecorator alterGround(BlockStateProvider provider) {
        return AlterGroundDecorator.of(provider);
    }

    /**
     * Attaches blocks to the exposed faces of all foliage blocks in specified directions.
     * @return The attached to leaves decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AttachedToLeavesDecorator.Builder attachedToLeaves() {
        return AttachedToLeavesDecorator.builder();
    }

    /**
     * Attaches blocks to the exposed faces of all trunk blocks in specified directions.
     * @return The attached to logs decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AttachedToLogsDecorator.Builder attachedToLogs() {
        return AttachedToLogsDecorator.builder();
    }

    /**
     * Places a single beehive with the specified probability. The beehive is always located 1 block below the
     * lowest leaves block, but possibly on a different branch.
     * @param probability The probability of placing a beehive, between 0.0F and 1.0F (inclusive).
     * @return The beehive decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BeehiveDecorator beehive(float probability) {
        return BeehiveDecorator.of(probability);
    }

    /**
     * Places cocoa beans on the bottom 3 blocks of the trunk with a probability of 25% for each face.
     * @param probability The probability that this decorator places any cocoa beans, between 0.0F and 1.0F (inclusive).
     * @return The cocoa decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CocoaDecorator cocoa(float probability) {
        return CocoaDecorator.of(probability);
    }

    /**
     * Places a creaking heart, replacing a trunk block in a position where all 6 surrounding blocks are in the
     * <code>#minecraft:logs</code> tag.
     * @param probability The probability of placing a creaking heart, between 0.0F and 1.0F (inclusive).
     * @return The creaking heart decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CreakingHeartDecorator creakingHeart(float probability) {
        return CreakingHeartDecorator.of(probability);
    }

    /**
     * Places vines attached to any foliage blocks. Each vine is extended by 4 blocks downwards if possible.
     * @param probability The probability of placing a vine at any given position, between 0.0F and 1.0F (inclusive).
     * @return The leave vine decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LeaveVineDecorator leaveVine(float probability) {
        return LeaveVineDecorator.of(probability);
    }

    /**
     * Places the <code>minecraft:pale_moss_patch</code> configured feature at the position of the lowest log, and
     * adds hanging moss to the bottom of trunk and foliage blocks.
     * @return The pale moss decorator builder.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PaleMossDecorator.Builder paleMoss() {
        return PaleMossDecorator.builder();
    }

    /**
     * Tries positions within the specified radius and height range to find a valid ground position, and places the
     * specified block on the block above any matching positions.
     * @return The place on ground decorator builder.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PlaceOnGroundDecorator.Builder placeOnGround() {
        return PlaceOnGroundDecorator.builder();
    }

    /**
     * Adds vines to each side of each trunk block with a probability of 75% each.
     * @return The trunk vine decorator.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrunkVineDecorator trunkVine() {
        return TrunkVineDecorator.of();
    }

    @ApiStatus.Internal
    static TreeDecorator decode(Object minecraftDecorator) {
        return DECODER.decode(minecraftDecorator);
    }
}
