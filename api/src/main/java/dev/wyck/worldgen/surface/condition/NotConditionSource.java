package dev.wyck.worldgen.surface.condition;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Inverts a surface condition, passing when the nested condition fails.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NotConditionSource extends ConditionSource {

    /**
     * The condition to invert.
     * @return the inverted condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ConditionSource target(); // codec name: invert

    /**
     * Creates a new not condition source.
     * @param target the condition to invert
     * @return the not condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NotConditionSource of(ConditionSource target) {
        record Holder() {
            static final ConstructWireProvider<NotConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.NotConditionSourceImpl");
        }
        return Holder.WIRE.construct(target);
    }
}
