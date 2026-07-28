package dev.wyck.renderer.setter;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.renderer.AbstractBiomeRenderer;
import dev.wyck.renderer.updater.BiomeUpdater;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * This utility class provides methods to set the biome of blocks, chunks, and regions in the game.
 *
 * @version 2.1.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("2.1.0")
public interface BiomeSetter extends AbstractBiomeRenderer {

    int OVERWORLD_MIN_HEIGHT = -64;
    int OVERWORLD_MAX_HEIGHT = 320;

    int NETHER_END_MIN_HEIGHT = 0;
    int NETHER_END_MAX_HEIGHT = 128;

    /**
     * Returns a new instance of the BiomeSetter interface.
     *
     * @return a new instance of the BiomeSetter interface
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    static BiomeSetter of() {
        return new GlobalBiomeSetter();
    }

    /**
     * Returns a new instance of the BiomeSetter interface with the given provider.
     * @param provider the provider
     * @return a new instance of the BiomeSetter interface with the given provider
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    @ApiStatus.Obsolete
    static BiomeSetter of(Plugin provider) {
        return new GlobalBiomeSetter(provider);
    }

    /**
     * Returns a new instance of the BiomeSetter interface with the given BiomeUpdater.
     * @param updater the BiomeUpdater
     * @return a new instance of the BiomeSetter interface with the given BiomeUpdater
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static BiomeSetter of(BiomeUpdater updater) {
        return new GlobalBiomeSetter(updater);
    }

    /**
     * Returns the RegionAccessor for the given location.
     *
     * @param location the location
     * @return the RegionAccessor for the location
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    default RegionAccessor getRegionAccessor(Location location) {
        return location.getWorld();
    }

    /**
     * Sets the biome of a block to a custom biome.
     *
     * @param block the block
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setBlockBiome(Block block, Biome biome);

    /**
     * Sets the biome of a block to a custom biome.
     * This method uses the UnsafeValues instance to perform unsafe operations.
     * It gets the location of the block and sets its biome to the custom biome.
     * If the 'updateBiome' flag is set to true, the biome of the block is updated immediately.
     *
     * @param block The block whose biome is to be set.
     * @param biome The custom biome to set for the block.
     * @param updateBiome A flag indicating whether to update the biome of the block immediately.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setBlockBiome(Block block, Biome biome, boolean updateBiome);

    /**
     * Sets the biome of a chunk to a custom biome.
     *
     * @param chunk the chunk
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setChunkBiome(Chunk chunk, Biome biome);

    /**
     * Sets the biome of a chunk to a custom biome within the default height range.
     * This method is a convenience method that calls the setChunkBiome method with the default minimum and maximum heights.
     * If the 'updateBiome' flag is set to true, the biome of the chunk is updated immediately.
     *
     * @param chunk The chunk whose biome is to be set.
     * @param biome The custom biome to set for the chunk.
     * @param updateBiome A flag indicating whether to update the biome of the chunk immediately.
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setChunkBiome(Chunk chunk, Biome biome, boolean updateBiome);

    /**
     * Sets the biome of a chunk to a custom biome within a height range.
     *
     * @param chunk the chunk
     * @param minHeight the minimum height
     * @param maxHeight the maximum height
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, Biome biome);

   /**
     * Sets the biome of a chunk to a custom biome within a specified height range.
     * This method uses the UnsafeValues instance to perform unsafe operations.
     * It iterates over the blocks in the chunk within the specified height range and sets their biome to the custom biome.
     * If the 'updateBiome' flag is set to true, the biome of the chunk is updated immediately.
     *
     * @param chunk The chunk whose biome is to be set.
     * @param minHeight The minimum height within the chunk for the biome change.
     * @param maxHeight The maximum height within the chunk for the biome change.
     * @param biome The custom biome to set for the chunk.
     * @param updateBiome A flag indicating whether to update the biome of the chunk immediately.
     * @since 0.0.1
     */
   @AsOf("0.0.1")
   void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, Biome biome, boolean updateBiome);

    /**
     * Sets the biome of a bounding box to a custom biome.
     *
     * @param world the world
     * @param boundingBox the bounding box
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setBoundingBoxBiome(World world, BoundingBox boundingBox, Biome biome);

    /**
     * Sets the biome of a region to a custom biome.
     *
     * @param from the starting location
     * @param to the ending location
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setRegionBiome(Location from, Location to, Biome biome);

    /**
     * Sets the biome of a region to a custom biome.
     * This method is a convenience method that calls the setRegionBiome method with the 'updateBiome' flag set to false.
     *
     * @param from the starting location
     * @param to the ending location
     * @param biome the custom biome
     * @param updateBiome a flag indicating whether to update the biome of the region immediately
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setRegionBiome(Location from, Location to, Biome biome, boolean updateBiome);

    /**
     * Sets the biome of a region to a custom biome.
     *
     * @param world the world
     * @param from the starting vector
     * @param to the ending vector
     * @param biome the custom biome
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    void setRegionBiome(World world, Vector from, Vector to, Biome biome);

    /**
     * Sets the biome of a region to a custom biome.
     * This method is a convenience method that calls the setRegionBiome method with the 'updateBiome' flag set to false.
     *
     * @param world the world
     * @param from the starting vector
     * @param to the ending vector
     * @param biome the custom biome
     * @param updateBiome a flag indicating whether to update the biome of the region immediately
     * @since 0.0.1
     */
    @AsOf("0.0.2")
    void setRegionBiome(World world, Vector from, Vector to, Biome biome, boolean updateBiome);

    /**
     * Sets the biome of a region to a custom biome.
     * This method uses the UnsafeValues instance to perform unsafe operations.
     * It iterates over the blocks in the region defined by the 'from' and 'to' vectors and sets their biome to the custom biome.
     * If the 'updateBiome' flag is set to true, the biome of the region is updated immediately.
     *
     * @param world The world in which the region is located.
     * @param from The starting vector of the region.
     * @param to The ending vector of the region.
     * @param biome The custom biome to set for the region.
     * @param updateBiome A flag indicating whether to update the biome of the region immediately.
     * @since 0.0.2
     */
    @AsOf("0.0.1")
    void setRegionBiome(World world, Location from, Location to, Biome biome, boolean updateBiome);

}