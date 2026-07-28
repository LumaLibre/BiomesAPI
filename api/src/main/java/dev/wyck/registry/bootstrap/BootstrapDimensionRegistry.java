package dev.wyck.registry.bootstrap;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.util.ThrowingRunnable;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Registers custom dimension types during the Paper bootstrap/registry-load phase, so they enter
 * the dimension-type registry before it freezes.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Experimental
@SuppressWarnings("UnstableApiUsage")
public interface BootstrapDimensionRegistry {

    /**
     * Composes a new datapack-backed BootstrapDimensionRegistry and installs it into the context.
     * @param context the bootstrap context
     * @return a new, installed BootstrapDimensionRegistry instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BootstrapDimensionRegistry compose(BootstrapContext context, Composer type) {
        BootstrapDimensionRegistry registry = type == Composer.DATAPACK ? datapack() : unsafe();
        registry.install(context);
        return registry;
    }

    /**
     * Creates a new DatapackBootstrapDimensionRegistry instance.
     * @return a new instance of DatapackBootstrapDimensionRegistry
     * @since 3.0.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("3.0.0")
    static BootstrapDimensionRegistry datapack() {
        record Holder() {
            static final WireProvider<BootstrapDimensionRegistry> WIRE = WireProvider.create("dev.wyck.registry.bootstrap.DatapackBootstrapDimensionRegistry");
        }
        return Holder.WIRE.getNew();
    }

    /**
     * Creates a new UnsafePaperBootstrapDimensionRegistry instance.
     * @return a new instance of UnsafePaperBootstrapDimensionRegistry
     * @since 3.0.0
     * @apiNote {@link #install(BootstrapContext)} must be called after this method.
     */
    @AsOf("3.0.0")
    static BootstrapDimensionRegistry unsafe() {
        record Holder() {
            static final WireProvider<BootstrapDimensionRegistry> WIRE = WireProvider.create("dev.wyck.registry.bootstrap.UnsafePaperBootstrapDimensionRegistry");
        }
        return Holder.WIRE.getNew();
    }

    /**
     * Installs the registry into the given context.
     * @param context the context to install the registry into
     * @return this registry instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BootstrapDimensionRegistry install(BootstrapContext context);

    /**
     * Queues a dimension type to be registered.
     * @param dimension the dimension type to register
     * @return this registry instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BootstrapDimensionRegistry queue(Dimension dimension);

    /**
     * Defers the execution of a runnable until the registry is installed.
     * @param runnable the runnable to defer
     * @return this registry instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BootstrapDimensionRegistry deferring(ThrowingRunnable runnable);
}
