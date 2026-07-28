package dev.wyck.worldgen.surface.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * The badlands banded-terracotta rule.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BandlandsRuleSource extends RuleSource {

    /** The bandlands rule source. */
    @AsOf("3.0.0")
    BandlandsRuleSource INSTANCE = of();

    private static BandlandsRuleSource of() {
        record Holder() {
            static final ConstructWireProvider<BandlandsRuleSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.rule.BandlandsRuleSourceImpl");
        }
        return Holder.WIRE.construct();
    }
}
