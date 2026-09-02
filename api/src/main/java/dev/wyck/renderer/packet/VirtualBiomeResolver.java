package dev.wyck.renderer.packet;

import dev.wyck.annotations.AsOf;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Bridges the API module and the NMS module across the chunk-rewrite boundary.
 *
 * <p>The packet listener decides (cheaply, with no decoding) whether any phony biome could
 * apply to a chunk and, if so, produces a resolver. The NMS handler then decodes the chunk
 * exactly once, builds a {@link SnapshotChunkData}, and calls {@link #resolve(SnapshotChunkData)}.
 * The resolver runs the biome-aware conditionals against the decoded data and returns the
 * {@link VirtualBiome} to apply, or {@code null} to leave the packet untouched (in which case
 * the NMS side skips serialization entirely).
 *
 * @version 3.3.0
 * @since 2.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.2.0")
@ApiStatus.Internal
@FunctionalInterface
public interface VirtualBiomeResolver {

    /**
     * @param chunkData the decoded chunk view, exposing the real biome/block data
     * @param localQuartX the chunk-relative quart X, from 0 through 3
     * @param localQuartY the column-relative quart Y
     * @param localQuartZ the chunk-relative quart Z, from 0 through 3
     * @return the custom biome to apply, or {@code null} to preserve the original biome cell
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @Nullable VirtualBiome resolve(SnapshotChunkData chunkData, int localQuartX, int localQuartY, int localQuartZ);
}
