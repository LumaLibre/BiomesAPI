package dev.wyck.registry.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.level.LevelCreator;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

/**
 * Creates live Bukkit worlds backed by a custom dimension type and chunk generator.
 *
 * @apiNote On a region-threaded server (Folia) this must be invoked from the
 * global region thread, mirroring {@code CraftServer#createWorld}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface LevelFactory {

    @AsOf("2.4.0")
    static LevelFactory factory() {
        record Holder() {
            static final WireProvider<LevelFactory> WIRE = WireProvider.create("dev.wyck.*?.registry.level.SimpleLevelFactory");
        }
        return Holder.WIRE.get();
    }

    /**
     * Creates and loads a world from the given specification.
     *
     * @param world the world specification
     * @return the loaded Bukkit world
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    World createWorld(LevelCreator world);
}