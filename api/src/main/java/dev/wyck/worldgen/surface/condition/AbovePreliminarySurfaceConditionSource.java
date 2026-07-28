package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Checks if the current position is above the preliminary surface level — a Y-level usually a few
 * blocks below the main surface, ignoring noise caves. Used to prevent grass blocks from being placed
 * in noise caves.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AbovePreliminarySurfaceConditionSource extends ConditionSource {

    /** The above-preliminary-surface condition source. */
    @AsOf("3.0.0")
    AbovePreliminarySurfaceConditionSource INSTANCE = of();

    private static AbovePreliminarySurfaceConditionSource of() {
        record Holder() {
            static final ConstructWireProvider<AbovePreliminarySurfaceConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.AbovePreliminarySurfaceConditionSourceImpl");
        }
        return Holder.WIRE.construct();
    }
}
