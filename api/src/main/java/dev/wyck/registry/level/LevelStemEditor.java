package dev.wyck.registry.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.WireProvider;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.bootstrap.BootstrapBiomeRegistry;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.climate.ClimatePoint;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Modifies a loaded world's dimension type and/or chunk generator during runtime.
 * Operations with this editor are transient, but you are advised to not use this on
 * production servers.
 *
 * @see BootstrapBiomeRegistry#addToDimension(Dimension, Biome, ClimatePoint)
 * @see BootstrapBiomeRegistry#replaceInDimension(Dimension, Biome, Biome)
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
public interface LevelStemEditor {

    /**
     * Creates a new editor backed by the runtime reference-swap implementation.
     * @return a new editor instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static LevelStemEditor create() {
        record Holder() {
            static final WireProvider<LevelStemEditor> WIRE = WireProvider.create("dev.wyck.registry.level.RuntimeLevelStemEditor");
        }
        return Holder.WIRE.getNew();
    }

    /**
     * Swaps the chunk generator on a given world.
     * @param generator the chunk generator to swap in
     * @return this editor instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    LevelStemEditor chunkGenerator(ChunkGenerator generator);

    /**
     * Swaps the dimension type on a given world.
     * @param dimensionType the dimension type to swap in
     * @return this editor instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    LevelStemEditor dimension(Dimension dimensionType);

    /**
     * Applies the queued edits to a given world.
     * @param world the world to edit
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    LevelStemEditor apply(World world);

    /**
     * Reverts a world previously edited by this editor, restoring its original dimension type and/or
     * chunk generator state, and clears the corresponding snapshots.
     * @param world the world to revert
     * @return this editor instance
     * @throws IllegalStateException if this editor never edited the given world
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    LevelStemEditor restore(World world);
}
