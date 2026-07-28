package dev.wyck.registry.bootstrap;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.WireProvider;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.level.LevelStemEditor;
import dev.wyck.util.ThrowingRunnable;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.climate.ClimatePoint;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Registers custom biomes during the Paper bootstrap/registry-load phase, so they enter the
 * biome registry before it freezes (no post-freezing). A biome that fails
 * to build should fail server startup.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Experimental
@SuppressWarnings("UnstableApiUsage")
public interface BootstrapBiomeRegistry {

    /**
     * Composes a new BootstrapBiomeRegistry instance.
     * @param context the bootstrap context
     * @param type the type of registry to compose
     * @return a new BootstrapBiomeRegistry instance
     * @since 2.3.0
     * @see Composer
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry compose(BootstrapContext context, Composer type) {
        BootstrapBiomeRegistry registry = type == Composer.INJECTOR ? injector() : datapack();
        registry.install(context);
        return registry;
    }

    /**
     * Creates a new UnsafePaperBootstrapBiomeRegistry instance.
     * @return a new instance of UnsafePaperBootstrapBiomeRegistry
     * @since 2.3.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry injector() {
        record Holder() {
            static final WireProvider<BootstrapBiomeRegistry> WIRE = WireProvider.create("dev.wyck.registry.bootstrap.UnsafePaperBootstrapBiomeRegistry");
        }
        return Holder.WIRE.getNew();
    }

    /**
     * Creates a new DatapackBootstrapBiomeRegistry instance.
     * @return a new instance of DatapackBootstrapBiomeRegistry
     * @since 2.3.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("2.3.0")
    static BootstrapBiomeRegistry datapack() {
        record Holder() {
            static final WireProvider<BootstrapBiomeRegistry> WIRE = WireProvider.create("dev.wyck.registry.bootstrap.DatapackBootstrapBiomeRegistry");
        }
        return Holder.WIRE.getNew();
    }

    /**
     * Installs the registry into the given context.
     * @param context the context to install the registry into
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry install(BootstrapContext context);

    /**
     * Queues a biome to be registered.
     * @param biome the biome to register
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry queue(Biome biome);

    /**
     * Defers the execution of a runnable until the registry is installed.
     * @param runnable the runnable to defer
     * @return this registry instance
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry deferring(ThrowingRunnable runnable);

    /**
     * Adds a biome to a dimension's biome distribution.
     * The biome will be added at the specified climate point and may replace an existing biome if the point overlaps with an existing one.
     * @param dimension the dimension to add the biome to
     * @param target the biome to add
     * @param placement the climate point to place the biome at
     * @return this registry instance
     * @throws UnsupportedOperationException if the implementation does not support this operation
     * @see LevelStemEditor#chunkGenerator(ChunkGenerator)
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry addToDimension(Dimension dimension, Biome target, ClimatePoint placement) throws UnsupportedOperationException;

    /**
     * Replaces a biome in a dimension.
     * @param dimension the dimension to replace the biome in
     * @param target the biome to replace
     * @param replacement the biome to replace with
     * @return this registry instance
     * @throws UnsupportedOperationException if the implementation does not support this operation
     * @see LevelStemEditor#chunkGenerator(ChunkGenerator)
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BootstrapBiomeRegistry replaceInDimension(Dimension dimension, Biome target, Biome replacement) throws UnsupportedOperationException;
}
