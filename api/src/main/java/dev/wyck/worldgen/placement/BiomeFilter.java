package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.feature.FeatureType;
import org.jspecify.annotations.NullMarked;

/**
 * Returns the current position if the biome at that position includes this placed feature, otherwise returns empty.
 * In effect, this predicate restricts features from being placed outside the edges of any biome that generates the feature.
 * <p>
 * This modifier type cannot be used in placed features that are referenced from other configured features
 * (for example, from entries in a {@link FeatureType#RANDOM_SELECTOR} type feature). Minecraft does not catch this type of error automatically on trying to load the world;
 * instead, the game runs normally until it tries to generate the feature, which causes the game to crash.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BiomeFilter extends PlacementFilter {

    /** The singleton instance of {@link BiomeFilter}. */
    @AsOf("3.0.0")
    BiomeFilter INSTANCE = of();

    private static BiomeFilter of() {
        record Holder() {
            static final ConstructWireProvider<BiomeFilter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.BiomeFilterImpl");
        }
        return Holder.WIRE.construct();
    }
}
