package dev.wyck.level.entity;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.ContextWrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a level spawner.
 * These spawners are mainly used for spawning mobs in a biome-agnostic way.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface LevelSpawner extends ContextWrapper<Object> {

    @ApiStatus.Internal
    interface Factory {
        LevelSpawner vanilla(Kind kind);
        LevelSpawner custom(SpawnTick tick);
    }

    /**
     * Gets a vanilla level spawner.
     * @param kind the kind of level spawner to get
     * @return the vanilla level spawner
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LevelSpawner vanilla(Kind kind) {
        return create(kind);
    }

    /**
     * Creates a custom level spawner.
     * @param tick the tick to use for the spawner
     * @return a custom level spawner
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner custom(SpawnTick tick) {
        return create(tick);
    }

    private static LevelSpawner create(Object source) {
        record Holder() {
            static final WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.level.entity.LevelSpawnerFactoryImpl");
        }
        return switch (source) {
            case Kind kind -> Holder.WIRE.get().vanilla(kind);
            case SpawnTick tick -> Holder.WIRE.get().custom(tick);
            default -> throw new IllegalArgumentException("Unsupported level spawner source: " + source);
        };
    }

    /**
     * The level spawner for phantoms.
     * @return the level spawner for phantoms
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner phantom() {
        return vanilla(Kind.PHANTOM);
    }

    /**
     * The level spawner for patrols.
     * @return the level spawner for patrols
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner patrol() {
        return vanilla(Kind.PATROL);
    }

    /**
     * The level spawner for cats.
     * @return the level spawner for cats
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner cat() {
        return vanilla(Kind.CAT);
    }

    /**
     * The level spawner for village siege.
     * @return the level spawner for village siege
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner villageSiege() {
        return vanilla(Kind.VILLAGE_SIEGE);
    }

    /**
     * The level spawner for wandering traders.
     * @return the level spawner for wandering traders
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static LevelSpawner wanderingTrader() {
        return vanilla(Kind.WANDERING_TRADER);
    }

    /**
     * The kind of level spawner.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    enum Kind {
        PHANTOM,
        PATROL,
        CAT,
        VILLAGE_SIEGE,
        WANDERING_TRADER
    }
}
