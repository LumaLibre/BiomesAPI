package dev.wyck.util;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Utility interface for specific bukkit types that may not need to be wrapped,
 * but cannot safely be called during bootstrap.
 *
 * @apiNote Methods in this interface may be removed or changed without warning.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Experimental
public interface BukkitBootstrapUtil {

    /**
     * Creates a new BlockData instance for the given material.
     * @param material the material to create a BlockData for
     * @return a new BlockData instance for the given material
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData createBlockData(Material material);

    /**
     * Gets the current BukkitBootstrapUtil instance.
     * @return the current BukkitBootstrapUtil instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BukkitBootstrapUtil util() {
        record Holder() {
            static final WireProvider<BukkitBootstrapUtil> WIRE = WireProvider.create("dev.wyck.util.BukkitBootstrapUtilImpl");
        }
        return Holder.WIRE.get();
    }
}
