package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Inverts a given block predicate.
 * Matches if the specified other block predicate <i>does not</i> match.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#not">Block predicate (not)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NotPredicate extends BlockPredicate {

    /**
     * The predicate to invert.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate predicate();

    /**
     * Creates a new {@link NotPredicate} with the given predicate.
     * @param predicate the predicate to invert
     * @return the NotPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NotPredicate of(BlockPredicate predicate) {
        record Holder() {
            static final ConstructWireProvider<NotPredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.NotPredicateImpl");
        }
        return Holder.WIRE.construct(predicate);
    }
}
