package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.jspecify.annotations.NullMarked;

/**
 * Returns the current position when the predicate is passed, otherwise return empty.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockPredicateFilter extends PlacementFilter {

    /**
     * The block predicate to test.
     * @return the block predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate predicate();

    /**
     * Creates a new block predicate filter.
     * @param predicate the block predicate
     * @return a new block predicate filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockPredicateFilter of(BlockPredicate predicate) {
        record Holder() {
            static final ConstructWireProvider<BlockPredicateFilter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.BlockPredicateFilterImpl");
        }
        return Holder.WIRE.construct(predicate);
    }
}
