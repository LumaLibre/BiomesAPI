package dev.wyck.worldgen.surface.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import org.jspecify.annotations.NullMarked;

/**
 * Applies a rule only when a condition holds.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConditionRuleSource extends RuleSource {

    /**
     * The gating condition.
     * @return the condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ConditionSource condition();

    /**
     * The rule applied when the condition holds.
     * @return the rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    RuleSource then();

    /**
     * Creates a new condition rule source.
     * @param condition the gating condition
     * @param then the rule applied when the condition holds
     * @return the condition rule source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConditionRuleSource of(ConditionSource condition, RuleSource then) {
        record Holder() {
            static final ConstructWireProvider<ConditionRuleSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.rule.ConditionRuleSourceImpl");
        }
        return Holder.WIRE.construct(condition, then);
    }
}
