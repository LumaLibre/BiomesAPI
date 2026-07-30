package dev.wyck;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.BuildInfo;
import dev.wyck.factory.OptionalWireProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Common interface for miscellaneous Wyck (BiomesAPI) methods and utilities.
 * Implemented by the main plugin class when running as a standalone plugin, and by a default
 * implementation when shaded into a consumer plugin.
 *
 * @since 2.1.0
 * @version 2.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
@ApiStatus.NonExtendable
public interface Wyck {

    @ApiStatus.Internal
    OptionalWireProvider<Wyck> WIRE = OptionalWireProvider.empty();

    /**
     * @return the active Wyck instance. When running as a standalone plugin, this is the
     * Bukkit-managed plugin. When shaded into a consumer plugin, returns a default instance
     * with no plugin context.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    static Wyck wyck() {
        return WIRE.optional().orElseGet(ShadedWyck::instance);
    }

    /**
     * Determines the plugin instance associated with Wyck.
     * @return the plugin instance if the library is running as a standalone plugin or {@link JavaPlugin#getProvidingPlugin(Class)} otherwise.
     * @since 2.1.0
     * @throws IllegalStateException if called from the static initializer for the given JavaPlugin
     */
    @AsOf("2.1.0")
    default JavaPlugin plugin() {
        try {
            return WIRE.optional()
                    .filter(api -> api instanceof JavaPlugin)
                    .map(api -> (JavaPlugin) api)
                    .orElse(JavaPlugin.getProvidingPlugin(Wyck.class));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Called before plugin was loaded. Do not use static initializers in your main plugin class or non-lazy classes for this component.", e);
        }
    }

    /**
     * @return the version of Wyck
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default String version() {
        return BuildInfo.VERSION;
    }

    /**
     * @return true if the library is shaded into a consumer plugin (no standalone instance available).
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default boolean isShaded() {
        return WIRE.optional().map(api -> api instanceof ShadedWyck).orElse(false);
    }

    /**
     * @return true if the library is running as a standalone plugin.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    default boolean isExternal() {
        return !isShaded();
    }

    /**
     * The default implementation of Wyck when shaded into a consumer plugin.
     * Constructed reflectively by the wire as a fallback when no standalone plugin
     * has registered itself.
     *
     * @since 2.1.0
     * @version 2.2.0
     * @author Jsinco
     */
    @AsOf("2.2.0")
    @ApiStatus.Internal
    final class ShadedWyck implements Wyck {

        private static Wyck.@Nullable ShadedWyck INSTANCE;

        private static synchronized ShadedWyck instance() {
            if (INSTANCE == null) {
                INSTANCE = new ShadedWyck();
            }
            return INSTANCE;
        }

        private ShadedWyck() {
            Preconditions.checkState(INSTANCE == null, "already initialized");
        }
    }
}
