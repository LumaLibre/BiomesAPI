package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.HeightmapType;
import org.jspecify.annotations.NullMarked;

/**
 * Sets the Y coordinate to one block above the heightmap and returns the new position.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HeightmapPlacement extends PlacementModifier {

    /**
     * The heightmap to use.
     * @return the heightmap type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    HeightmapType heightmap();

    /**
     * Creates a new heightmap placement.
     * @param heightmap the heightmap type
     * @return a new heightmap placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HeightmapPlacement of(HeightmapType heightmap) {
        record Holder() {
            static final ConstructWireProvider<HeightmapPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.HeightmapPlacementImpl");
        }
        return Holder.WIRE.construct(heightmap);
    }
}
