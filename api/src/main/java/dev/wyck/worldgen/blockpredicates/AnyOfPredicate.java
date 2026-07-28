package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Combined multiple block predicates. Matches if at least one of the specified blocks predicates matches.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#any_of">Block predicate (any of)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AnyOfPredicate extends CombiningPredicate {

    /**
     * Converts this object to a builder.
     * @return the builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new AnyOfPredicate with the specified predicates.
     * @param predicates the predicates to combine
     * @return the AnyOfPredicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AnyOfPredicate of(List<BlockPredicate> predicates) {
        record Holder() {
            static final ConstructWireProvider<AnyOfPredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.AnyOfPredicateImpl");
        }
        return Holder.WIRE.construct(predicates);
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
     * Builder for {@link AnyOfPredicate}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends CombiningPredicateBuilder<AnyOfPredicate, Builder> {
        public Builder() {}

        public Builder(AnyOfPredicate predicate) {
            super(predicate);
        }

        @Override
        protected AnyOfPredicate create() {
            return of(predicates);
        }
    }
}
