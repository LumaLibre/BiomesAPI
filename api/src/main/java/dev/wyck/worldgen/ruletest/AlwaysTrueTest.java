package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Matches any block.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#always_true">Processor list (always true)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AlwaysTrueTest extends RuleTest {

    /** The singleton instance of this test. */
    @AsOf("3.0.0")
    AlwaysTrueTest INSTANCE = of();

    private static AlwaysTrueTest of() {
        record Holder() {
            static final ConstructWireProvider<AlwaysTrueTest> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.ruletest.AlwaysTrueTestImpl");
        }
        return Holder.WIRE.construct();
    }
}
