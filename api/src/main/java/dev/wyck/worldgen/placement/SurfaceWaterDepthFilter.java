package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Returns the current position if the number of motion blocking blocks under the surface is less
 * than or equal to the maximum water depth. Otherwise returns empty.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SurfaceWaterDepthFilter extends PlacementFilter {

    /**
     * The maximum allowed water depth.
     * @return the maximum water depth
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxWaterDepth();

    /**
     * Creates a new surface water depth filter.
     * @param maxWaterDepth the maximum allowed water depth
     * @return a new surface water depth filter
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SurfaceWaterDepthFilter of(int maxWaterDepth) {
        record Holder() {
            static final ConstructWireProvider<SurfaceWaterDepthFilter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.SurfaceWaterDepthFilterImpl");
        }
        return Holder.WIRE.construct(maxWaterDepth);
    }
}