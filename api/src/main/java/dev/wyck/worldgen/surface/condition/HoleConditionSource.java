package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Passes for columns where the surface depth is 0.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HoleConditionSource extends ConditionSource {

    /** The hole condition source. */
    @AsOf("3.0.0")
    HoleConditionSource INSTANCE = of();

    private static HoleConditionSource of() {
        record Holder() {
            static final ConstructWireProvider<HoleConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.HoleConditionSourceImpl");
        }
        return Holder.WIRE.construct();
    }
}
