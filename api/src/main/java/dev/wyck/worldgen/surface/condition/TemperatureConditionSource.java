package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the current block is in a biome that is cold enough for snowfall.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TemperatureConditionSource extends ConditionSource {

    /** The temperature condition source. */
    @AsOf("3.0.0")
    TemperatureConditionSource INSTANCE = of();

    private static TemperatureConditionSource of() {
        record Holder() {
            static final ConstructWireProvider<TemperatureConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.TemperatureConditionSourceImpl");
        }
        return Holder.WIRE.construct();
    }
}
