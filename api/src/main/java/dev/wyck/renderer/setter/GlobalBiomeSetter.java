package dev.wyck.renderer.setter;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.misc.PointRange3D;
import dev.wyck.renderer.updater.BiomeUpdater;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

/**
 * This class provides methods to set the biome of blocks, chunks, and regions in the game.
 *
 * @version 1.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("1.0.1")
public class GlobalBiomeSetter implements BiomeSetter {

    private final BiomeUpdater biomeUpdater;

    public GlobalBiomeSetter() {
        this.biomeUpdater = BiomeUpdater.of();
    }

    public GlobalBiomeSetter(Plugin plugin) {
        this.biomeUpdater = BiomeUpdater.of(plugin);
    }

    public GlobalBiomeSetter(BiomeUpdater biomeUpdater) {
        this.biomeUpdater = biomeUpdater;
    }

    @Override
    public void setBlockBiome(Block block, Biome biome) {
        setBlockBiome(block, biome, false);
    }

    @Override
    public void setBlockBiome(Block block, Biome biome, boolean updateBiome) {
        Preconditions.checkNotNull(block, "block cannot be null");
        Preconditions.checkNotNull(biome, "abstractBiome cannot be null");

        Location location = block.getLocation();
        RegionAccessor accessor = getRegionAccessor(location);

        accessor.setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome.bukkitBiome());

        if (updateBiome) {
            biomeUpdater.updateChunkAsync(location.getWorld().getChunkAtAsync(location));
        }
    }

    @Override
    public void setChunkBiome(Chunk chunk, Biome biome) {
        World world = chunk.getWorld();

        int minHeight = world.getMinHeight();
        int maxHeight = world.getMaxHeight();
        setChunkBiome(chunk, minHeight, maxHeight, biome);
    }

    @Override
    public void setChunkBiome(Chunk chunk, Biome biome, boolean updateBiome) {
        World world = chunk.getWorld();
        int minHeight = world.getMinHeight();
        int maxHeight = world.getMaxHeight();
        setChunkBiome(chunk, minHeight, maxHeight, biome, updateBiome);
    }

    @Override
    public void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, Biome biome) {
        setChunkBiome(chunk, minHeight, maxHeight, biome, false);
    }

    @Override
    public void setChunkBiome(Chunk chunk, int minHeight, int maxHeight, Biome abstractBiome, boolean updateBiome) {
        Preconditions.checkNotNull(chunk, "chunk cannot be null");
        Preconditions.checkNotNull(abstractBiome, "abstractBiome cannot be null");

        RegionAccessor accessor = chunk.getWorld();
        org.bukkit.block.Biome biome = abstractBiome.bukkitBiome();

        int minX = chunk.getX() << 4;
        int maxX = minX + 16;

        int minZ = chunk.getZ() << 4;
        int maxZ = minZ + 16;

        for (int x = minX; x < maxX; x++) {
            for (int y = minHeight; y < maxHeight; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    // Set the biome of each block to the custom biome
                    accessor.setBiome(x, y, z, biome);
                }
            }
        }

        if (updateBiome) {
            biomeUpdater.updateChunkAsync(CompletableFuture.completedFuture(chunk));
        }
    }

    @Override
    public void setBoundingBoxBiome(World world, BoundingBox boundingBox, Biome biome) {
        setRegionBiome(world, boundingBox.getMin(), boundingBox.getMax(), biome);
    }

    @Override
    public void setRegionBiome(Location from, Location to, Biome biome) {
        World world = from.getWorld();
        if (!world.equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world!");
        }

        setRegionBiome(world, from, to, biome, false);
    }

    @Override
    public void setRegionBiome(Location from, Location to, Biome biome, boolean updateBiome) {
        World world = from.getWorld();
        if (!world.equals(to.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world!");
        }

        setRegionBiome(world, from, to, biome, updateBiome);
    }

    @Override
    public void setRegionBiome(World world, Vector from, Vector to, Biome biome) {
        setRegionBiome(world, from, to, biome, false);
    }

    @Override
    public void setRegionBiome(World world, Vector from, Vector to, Biome biome, boolean updateBiome) {
        setRegionBiome(world, from.toLocation(world), to.toLocation(world), biome, updateBiome);
    }

    @Override
    public void setRegionBiome(World world, Location from, Location to, Biome abstractBiome, boolean updateBiome) {
        Preconditions.checkNotNull(world, "world cannot be null");
        Preconditions.checkNotNull(from, "from cannot be null");
        Preconditions.checkNotNull(to, "to cannot be null");
        Preconditions.checkNotNull(abstractBiome, "abstractBiome cannot be null");

        Preconditions.checkArgument(from.getWorld().equals(to.getWorld()), "Locations must be in the same world!");

        org.bukkit.block.Biome biome = abstractBiome.bukkitBiome();
        PointRange3D range = PointRange3D.of(from, to);

        for (int x = range.minX(); x <= range.maxX(); x++) {
            for (int y = range.minY(); y <= range.maxY(); y++) {
                for (int z = range.minZ(); z <= range.maxZ(); z++) {
                    world.setBiome(x, y, z, biome);
                }
            }
        }

        if (updateBiome) {
            biomeUpdater.updateChunks(from, to);
        }
    }

}
