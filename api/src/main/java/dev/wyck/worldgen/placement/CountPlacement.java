package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Returns multiple copies of the current block position. A single count is limited to 4096, but
 * multiple count modifiers can be stacked to multiply that limit.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CountPlacement extends PlacementModifier {

    /**
     * The number of placements to attempt, between 0 and 4096 (inclusive).
     * @return the count provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider count();

    /**
     * Creates a new count placement.
     * @param count the number of placements to attempt, between 0 and 4096 (inclusive)
     * @return a new count placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CountPlacement of(IntProvider count) {
        record Holder() {
            static final ConstructWireProvider<CountPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.CountPlacementImpl");
        }
        return Holder.WIRE.construct(count);
    }
}