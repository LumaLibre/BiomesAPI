package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Sets the Y coordinate to a value provided by a height provider and returns the new position.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HeightRangePlacement extends PlacementModifier {

    /**
     * The new Y coordinate.
     * @return the new Y coordinate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    HeightProvider height();

    /**
     * Creates a new height range placement.
     * @param height the height provider
     * @return a new height range placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HeightRangePlacement of(HeightProvider height) {
        record Holder() {
            static final ConstructWireProvider<HeightRangePlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.HeightRangePlacementImpl");
        }
        return Holder.WIRE.construct(height);
    }
}
