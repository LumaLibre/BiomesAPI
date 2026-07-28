package dev.wyck.worldgen.surface.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Evaluates rules in order, using the first that applies.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SequenceRuleSource extends RuleSource {

    /**
     * The rules evaluated in order.
     * @return the rules
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<RuleSource> rules();

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
     * Creates a new sequence rule source.
     * @param rules the rules evaluated in order
     * @return the sequence rule source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceRuleSource of(List<RuleSource> rules) {
        record Holder() {
            static final ConstructWireProvider<SequenceRuleSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.rule.SequenceRuleSourceImpl");
        }
        return Holder.WIRE.construct(List.copyOf(rules));
    }

    /**
     * Creates a new sequence rule source.
     * @param rules the rules evaluated in order
     * @return the sequence rule source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceRuleSource of(RuleSource... rules) {
        return of(List.of(rules));
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
     * Builder for {@link SequenceRuleSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final List<RuleSource> rules = new ArrayList<>();

        private Builder() {}

        private Builder(SequenceRuleSource source) {
            this.rules.addAll(source.rules());
        }

        /**
         * Adds a rule to the sequence.
         * @param rule the rule to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rule(RuleSource rule) {
            this.rules.add(rule);
            return this;
        }

        /**
         * Adds rules to the sequence.
         * @param rules the rules to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rules(RuleSource... rules) {
            Collections.addAll(this.rules, rules);
            return this;
        }

        /**
         * Adds rules to the sequence.
         * @param rules the rules to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rules(List<RuleSource> rules) {
            this.rules.addAll(rules);
            return this;
        }

        /**
         * Builds the sequence rule source.
         * @return the sequence rule source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SequenceRuleSource build() {
            return of(rules);
        }
    }
}
