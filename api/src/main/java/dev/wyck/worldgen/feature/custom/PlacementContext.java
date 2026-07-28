package dev.wyck.worldgen.feature.custom;

import dev.wyck.annotations.AsOf;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Random;

/**
 * The placement surface handed to a CustomFeature during world generation.
 *
 * @param <C> the feature's configuration type
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface PlacementContext<C> {

    int BLOCK_UPDATE_LIMIT = 512;

    /**
     * The feature's configuration.
     * @return the config instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    C config();

    /**
     * A random source seeded for this placement.
     * @return a view of the world generation random
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Random random();

    /**
     * The world origin of this feature placement.
     * @return the origin as a BlockVector
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockVector origin();

    /**
     * Sets a block at the given absolute world position.
     * @param position the absolute world position
     * @param data the block data to place
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    void setBlock(BlockVector position, BlockData data);

    /**
     * Gets the block data at the given absolute world position.
     * @param position the absolute world position
     * @return the block data currently at that position
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockData getBlock(BlockVector position);

    /**
     * Removes a block at the given absolute world position.
     * @param position the absolute world position
     * @param movedByPiston whether the block was moved by a piston
     * @return whether the block was removed
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    boolean removeBlock(BlockVector position, boolean movedByPiston);

    /**
     * Removes a block at the given absolute world position.
     * @param position the absolute world position
     * @return whether the block was removed
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default boolean removeBlock(BlockVector position) {
        return removeBlock(position, false);
    }

    /**
     * Destroys a block at the given absolute world position.
     * @param position the absolute world position
     * @param dropResources whether to drop resources
     * @param breaker the entity that broke the block, if any
     * @param updateLimit the maximum number of blocks to update
     * @return whether the block was destroyed
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    boolean destroyBlock(BlockVector position, boolean dropResources, @Nullable Entity breaker, int updateLimit);

    /**
     * Destroys a block at the given absolute world position.
     * @param position the absolute world position
     * @param dropResources whether to drop resources
     * @param breaker the entity that broke the block, if any
     * @return whether the block was destroyed
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default boolean destroyBlock(BlockVector position, boolean dropResources, @Nullable Entity breaker) {
        return destroyBlock(position, dropResources, breaker, BLOCK_UPDATE_LIMIT);
    }

    /**
     * Destroys a block at the given absolute world position.
     * @param position the absolute world position
     * @param dropResources whether to drop resources
     * @return whether the block was destroyed
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default boolean destroyBlock(BlockVector position, boolean dropResources) {
        return destroyBlock(position, dropResources, null);
    }

    /**
     * Adds a new entity to the world.
     * @param entity the entity to add
     * @param reason the reason the entity was spawned
     * @return whether the entity was added successfully
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    boolean addFreshEntity(Entity entity, org.bukkit.event.entity.CreatureSpawnEvent.@Nullable SpawnReason reason);

    /**
     * Adds a new entity to the world.
     * @param entity the entity to add
     * @return whether the entity was added successfully
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default boolean addFreshEntity(Entity entity) {
        return addFreshEntity(entity, null);
    }
}