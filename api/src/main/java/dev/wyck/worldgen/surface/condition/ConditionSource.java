package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.surface.SurfaceRule;
import dev.wyck.worldgen.surface.rule.ConditionRuleSource;
import dev.wyck.worldgen.surface.rule.RuleSource;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * A surface condition source — the predicate family ({@code SurfaceRules.ConditionSource}) that gates
 * where a {@link RuleSource} applies.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConditionSource extends SurfaceRule {

    /**
     * Inverts this condition, passing when it fails.
     * @return a condition that negates this one
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default NotConditionSource not() {
        return NotConditionSource.of(this);
    }

    /**
     * Applies a rule only where this condition holds.
     * @param then the rule to apply when this condition passes
     * @return a rule gated by this condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default ConditionRuleSource then(RuleSource then) {
        return ConditionRuleSource.of(this, then);
    }

    /**
     * Reads a Minecraft surface-rule condition source.
     * @param minecraftCondition the condition to read
     * @return the decoded condition
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ConditionSource decode(Object minecraftCondition) {
        record Holder() {
            static final Decoder<ConditionSource> DECODER = Decoder.create("dev.wyck.decode.worldgen.surface.ConditionSourceDecoders");
        }
        return Holder.DECODER.decode(minecraftCondition);
    }
}
