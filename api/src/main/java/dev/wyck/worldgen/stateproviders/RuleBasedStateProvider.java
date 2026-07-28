package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A provider that provides a block and block state depending
 * on the block that currently exists in the world using a series of rules.
 *
 * @see <a href="https://minecraft.wiki/w/Rule-based_block_state_provider">Rule-based block state provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RuleBasedStateProvider extends BlockStateProvider {

    /**
     * The block to use when no rules' predicates match.
     * If unspecified and no rules' predicates match, then no block is placed.
     * @return the fallback block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<BlockStateProvider> fallback();

    /**
     * Rules of the block to use.
     * This can be empty.
     * @return the list of rules
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Rule> rules();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new rule-based block state provider.
     * @param fallback the fallback block state provider, or null to have no fallback
     * @param rules the rules of the block state provider
     * @return a new rule-based block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RuleBasedStateProvider of(@Nullable BlockStateProvider fallback, List<Rule> rules) {
        record Holder() {
            static final ConstructWireProvider<RuleBasedStateProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.RuleBasedStateProviderImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(fallback), rules);
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
     * Creates a new builder.
     * @param ifTrue The block predicate of this rule.
     * @param then The block to use when the predicate is passed.
     * @return a new rule
     */
    @AsOf("3.0.0")
    static Rule rule(BlockPredicate ifTrue, BlockStateProvider then) {
        return new Rule(ifTrue, then);
    }

    /**
     * A rule.
     * @param ifTrue The block predicate of this rule.
     * @param then The block to use when the predicate is passed.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    record Rule(BlockPredicate ifTrue, BlockStateProvider then) {}

    /**
     * A builder for {@link RuleBasedStateProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider fallback;
        private List<Rule> rules = new ArrayList<>();

        public Builder() {}

        public Builder(RuleBasedStateProvider provider) {
            this.fallback = provider.fallback().orElse(null);
            this.rules.addAll(provider.rules());
        }

        /**
         * Sets the fallback block state provider.
         * @param fallback the fallback block state provider, or null to have no fallback
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fallback(@Nullable BlockStateProvider fallback) {
            this.fallback = fallback;
            return this;
        }

        /**
         * Sets the rules of the block state provider.
         * @param rules the rules of the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rules(List<Rule> rules) {
            this.rules = rules;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the rules of the block state provider.
         * @param rules the rules of the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rules(Rule... rules) {
            this.rules = new ArrayList<>(List.of(rules));
            return this;
        }

        /**
         * Adds a rule to the block state provider.
         * @param ifTrue the predicate of the rule
         * @param then the block state provider to use when the predicate is passed
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rule(BlockPredicate ifTrue, BlockStateProvider then) {
            this.rules.add(new Rule(ifTrue, then));
            return this;
        }

        /**
         * Builds the block state provider.
         * @return the block state provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RuleBasedStateProvider build() {
            return of(fallback, rules);
        }
    }

}
