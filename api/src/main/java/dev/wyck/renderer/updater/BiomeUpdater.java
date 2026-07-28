package dev.wyck.renderer.updater;

import dev.wyck.Wyck;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.misc.PointRange2D;
import dev.wyck.renderer.AbstractBiomeRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for updating biomes in Minecraft.
 * This class provides methods for updating the biomes of chunks and regions in a Minecraft world.
 * It uses the UnsafeValues instance to perform unsafe operations.
 *
 * @version 1.2.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("1.2.0")
public interface BiomeUpdater extends AbstractBiomeRenderer {

    @ApiStatus.Internal
    interface Factory {
        BiomeUpdater create(Plugin plugin);
    }

    /**
     * Returns an instance of BiomeUpdater.
     * This method returns an instance of BiomeUpdaterImpl.
     *
     * @return an instance of BiomeUpdater.
     * @since 0.0.2
     */
    @AsOf("1.2.0")
    static BiomeUpdater of() {
        return of(Wyck.wyck().plugin());
    }

    /**
     * Returns an instance of BiomeUpdater with a plugin reference.
     * @param plugin the plugin to use for scheduling chunk updates. Necessary for Folia compatibility.
     * @return an instance of BiomeUpdater with a plugin reference
     * @since 1.2.0
     */
    @AsOf("1.2.0")
    static BiomeUpdater of(Plugin plugin) {
        record Holder() {
            static final WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.renderer.updater.BiomeUpdaterFactoryImpl");
        }
        return Holder.WIRE.get().create(plugin);
    }

    /**
     * Returns a list of chunks between two locations.
     * This method calculates the chunks that are located between the 'from' and 'to' locations.
     * The locations must be in the same world.
     *
     * @param from The starting location.
     * @param to The ending location.
     * @return A list of chunks between the two locations.
     * @since 0.0.1
     */
    @AsOf("1.2.0")
    default List<CompletableFuture<Chunk>> getChunksBetweenLocations(Location from, Location to) {
        if (!from.getWorld().equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world.");
        }

        List<CompletableFuture<Chunk>> chunks = new ArrayList<>();

        PointRange2D range = PointRange2D.of(from, to);
        World world = from.getWorld();
        for (int x = range.minX(); x <= range.maxX(); x += 16) {
            for (int z = range.minZ(); z <= range.maxZ(); z += 16) {
                chunks.add(world.getChunkAtAsync(x >> 4, z >> 4));
            }
        }

        return chunks;
    }

    /**
     * Updates the biome of a chunk.
     * This method is a convenience method that calls the updateChunks method with a list containing the chunk.
     *
     * @param chunk The chunk to update.
     * @since 2.2.0
     */
    @AsOf("2.0.0")
    void updateChunkAsync(CompletableFuture<Chunk> chunk);

    /**
     * Updates the biome of a chunk.
     * This method is a convenience method that calls the updateChunks method with a list containing the chunk.
     *
     * @param chunk The chunk to update.
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    default void updateChunk(Chunk chunk) {
        updateChunkAsync(CompletableFuture.completedFuture(chunk));
    }

    /**
     * Updates the biomes of the chunks between two locations.
     * This method is a convenience method that calls the updateChunks method with the chunks between the 'from' and 'to' locations.
     *
     * @param from The starting location.
     * @param to The ending location.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    @Contract("null, _ -> fail; _, null -> fail")
    void updateChunks(Location from, Location to);

    /**
     * Updates the biomes of a list of chunks within a certain distance.
     * This method sends an update packet to all players within the specified distance of each chunk in the list.
     * The update packet contains the new biome data for the chunk.
     *
     * @param chunks The chunks to update.
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    void updateChunksAsync(Collection<CompletableFuture<Chunk>> chunks);

    /**
     * Updates the biomes of a list of chunks within a certain distance.
     * This method sends an update packet to all players within the specified distance of each chunk in the list.
     * The update packet contains the new biome data for the chunk.
     *
     * @param chunks The chunks to update.
     * @since 2.0.0
     */
    @AsOf("2.0.0")
    default void updateChunks(Collection<Chunk> chunks) {
        updateChunksAsync(chunks.stream().map(CompletableFuture::completedFuture).toList());
    }

    /**
     * Updates the biomes of all chunks within a certain radius of a given chunk.
     * This method calculates all chunks within the specified radius and sends an update packet to them.
     *
     * @param chunk The center chunk.
     * @param radius The radius around the chunk to update.
     */
    @AsOf("0.0.15")
    void updateChunkRadius(Chunk chunk, int radius);

    /**
     * Updates the biomes of all chunks within the player's view distance.
     * This method calculates all chunks within the player's view distance and sends an update packet to them.
     * @param player the player for whom to update the chunks
     */
    @AsOf("0.0.15")
    void updateChunksForPlayer(Player player);

    /**
     * Checks if a player is within the view distance of a chunk.
     * @param player the player
     * @param chunk the chunk
     * @return true if the player is within the view distance of the chunk, false otherwise
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default boolean inChunkViewDistance(Player player, Chunk chunk) {
        Location playerLocation = player.getLocation();

        int viewDistance = Bukkit.getViewDistance();
        int playerChunkX = playerLocation.getBlockX() >> 4;
        int playerChunkZ = playerLocation.getBlockZ() >> 4;

        int targetChunkX = chunk.getX();
        int targetChunkZ = chunk.getZ();

        int deltaX = Math.abs(playerChunkX - targetChunkX);
        int deltaZ = Math.abs(playerChunkZ - targetChunkZ);

        return deltaX <= viewDistance && deltaZ <= viewDistance;
    }

    /**
     * Gets a list of players who are within the view distance of a given chunk.
     *
     * @param chunk The chunk for which to get the players within its view distance.
     * @return A list of players who are within the view distance of the chunk.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default List<Player> getPlayersInDistance(Chunk chunk) {
        World world = chunk.getWorld();

        return world.getPlayers().stream()
            .filter(player -> inChunkViewDistance(player, chunk))
            .toList();
    }
}
