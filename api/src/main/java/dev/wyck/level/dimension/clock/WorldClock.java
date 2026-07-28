package dev.wyck.level.dimension.clock;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a registered world clock.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface WorldClock extends Wrapper, Keyed, Registerable<WorldClock> {

    WorldClock OVERWORLD = of(ResourceKey.minecraft("overworld"));
    WorldClock THE_END = of(ResourceKey.minecraft("the_end"));

    /**
     * The registry key this clock points at.
     * @return the world clock key
     * @since 2.4.0
     */
    @Override
    @AsOf("2.4.0")
    ResourceKey key();

    /**
     * Creates a reference to a registered world clock.
     * @param key the key of an entry in the world-clock registry
     * @return a reference to that world clock
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static WorldClock of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<WorldClock> WIRE = ConstructWireProvider.create("dev.wyck.level.dimension.clock.WorldClockImpl");
        }
        return Holder.WIRE.construct(key);
    }

    /**
     * Creates a reference to a registered world clock.
     * @param key the key of an entry in the world-clock registry
     * @return a reference to that world clock
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WorldClock of(String key) {
        return of(ResourceKey.of(key));
    }
}