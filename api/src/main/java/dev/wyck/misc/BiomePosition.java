package dev.wyck.misc;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * The position of one biome cell in an outgoing chunk packet.
 *
 * <p>Minecraft stores biomes at quart resolution.
 *
 * @param chunkLocation the containing chunk
 * @param quartX the world quart X
 * @param quartY the world quart Y
 * @param quartZ the world quart Z
 * @param localQuartX the chunk-relative quart X
 * @param localQuartY the column-relative quart Y
 * @param localQuartZ the chunk-relative quart Z
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public record BiomePosition(
    ChunkLocation chunkLocation,
    int quartX,
    int quartY,
    int quartZ,
    int localQuartX,
    int localQuartY,
    int localQuartZ
) {

    /**
     * Gets the minimum block X represented by this biome cell.
     * @return the minimum block X
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int blockX() {
        return this.quartX << 2;
    }

    /**
     * Gets the minimum block Y represented by this biome cell.
     * @return the minimum block Y
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int blockY() {
        return this.quartY << 2;
    }

    /**
     * Gets the minimum block Z represented by this biome cell.
     * @return the minimum block Z
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int blockZ() {
        return this.quartZ << 2;
    }

    /**
     * Gets the minimum chunk-relative block X represented by this biome cell.
     * @return the minimum chunk-relative block X
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int localBlockX() {
        return this.localQuartX << 2;
    }

    /**
     * Gets the minimum column-relative block Y represented by this biome cell.
     * @return the minimum column-relative block Y
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int localBlockY() {
        return this.localQuartY << 2;
    }

    /**
     * Gets the minimum chunk-relative block Z represented by this biome cell.
     * @return the minimum chunk-relative block Z
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public int localBlockZ() {
        return this.localQuartZ << 2;
    }

    /**
     * Creates a biome-cell position from chunk-relative quart coordinates.
     * @param chunkLocation the containing chunk
     * @param minQuartY the world's minimum build height in quart coordinates
     * @param localQuartX the chunk-relative quart X
     * @param localQuartY the column-relative quart Y
     * @param localQuartZ the chunk-relative quart Z
     * @return the biome-cell position
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static BiomePosition fromLocalQuart(ChunkLocation chunkLocation, int minQuartY, int localQuartX, int localQuartY, int localQuartZ) {
        return new BiomePosition(
            chunkLocation,
            (chunkLocation.x() << 2) + localQuartX,
            minQuartY + localQuartY,
            (chunkLocation.z() << 2) + localQuartZ,
            localQuartX,
            localQuartY,
            localQuartZ
        );
    }

    /**
     * Creates the biome-cell position containing the supplied block coordinates.
     * @param chunkLocation the containing chunk
     * @param minQuartY the world's minimum build height in quart coordinates
     * @param blockX the world block X
     * @param blockY the world block Y
     * @param blockZ the world block Z
     * @return the containing biome-cell position
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static BiomePosition fromBlock(ChunkLocation chunkLocation, int minQuartY, int blockX, int blockY, int blockZ) {
        int quartX = blockX >> 2;
        int quartY = blockY >> 2;
        int quartZ = blockZ >> 2;
        return new BiomePosition(
            chunkLocation,
            quartX,
            quartY,
            quartZ,
            quartX - (chunkLocation.x() << 2),
            quartY - minQuartY,
            quartZ - (chunkLocation.z() << 2)
        );
    }
}
