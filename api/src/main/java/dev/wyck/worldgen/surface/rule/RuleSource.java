package dev.wyck.worldgen.surface.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.surface.SurfaceRule;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A surface rule source — the tree ({@code SurfaceRules.RuleSource}) that decides which block is placed
 * at the surface during generation.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RuleSource extends SurfaceRule {

    @ApiStatus.Internal
    Decoder<RuleSource> DECODER =
        Decoder.create("dev.wyck.decode.worldgen.surface.RuleSourceDecoders");

    /**
     * Applies this rule only where the given condition holds.
     * @param condition the gating condition
     * @return a rule gated by the condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default ConditionRuleSource when(ConditionSource condition) {
        return ConditionRuleSource.of(condition, this);
    }

    /**
     * Reads a Minecraft surface-rule source.
     * @param minecraftRule the rule to read
     * @return the decoded rule
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static RuleSource decode(Object minecraftRule) {
        return DECODER.decode(minecraftRule);
    }
}
