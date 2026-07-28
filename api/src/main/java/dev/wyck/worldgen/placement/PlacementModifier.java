package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * A placed feature determines where a configured feature should be attempted to be placed using placement modifiers.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface PlacementModifier extends Wrapper {

    /**
     * Reference to the biome filter placement modifier.
     * @return the biome filter placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiomeFilter biomeFilter() {
        return BiomeFilter.INSTANCE;
    }

    /**
     * Creates a block predicate filter.
     * @param predicate the block predicate to filter by
     * @return a block predicate filter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BlockPredicateFilter blockPredicateFilter(BlockPredicate predicate) {
        return BlockPredicateFilter.of(predicate);
    }

    /**
     * Creates a count on every layer placement modifier.
     * @deprecated Deprecated in Minecraft.
     * @param count the count provider
     * @return a count on every layer placement modifier
     * @since 3.0.0
     */
    @Deprecated
    @AsOf("3.0.0")
    static CountOnEveryLayerPlacement countOnEveryLayer(IntProvider count) {
        return CountOnEveryLayerPlacement.of(count);
    }

    /**
     * Creates a count placement modifier.
     * @param count the count provider
     * @return a count placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static CountPlacement count(IntProvider count) {
        return CountPlacement.of(count);
    }

    /**
     * A new builder for the environment scan placement modifier.
     * @return a new environment scan placement modifier builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EnvironmentScanPlacement.Builder environmentScan() {
        return EnvironmentScanPlacement.builder();
    }

    /**
     * A new builder for the fixed placement modifier.
     * @return a new fixed placement modifier builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FixedPlacement.Builder fixed() {
        return FixedPlacement.builder();
    }

    /**
     * A new instance of the heightmap placement modifier.
     * @param heightmap the heightmap type to use
     * @return a new heightmap placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static HeightmapPlacement heightmap(HeightmapType heightmap) {
        return HeightmapPlacement.of(heightmap);
    }

    /**
     * A new instance of the height range placement modifier.
     * @param height the height provider
     * @return a new height range placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static HeightRangePlacement heightRange(HeightProvider height) {
        return HeightRangePlacement.of(height);
    }

    /**
     * Reference to the in-square placement modifier.
     * @return the in-square placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static InSquarePlacement inSquare() {
        return InSquarePlacement.INSTANCE;
    }

    /**
     * A new builder for the noise-based count placement modifier.
     * @return a new noise-based count placement modifier builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseBasedCountPlacement.Builder noiseBasedCount() {
        return NoiseBasedCountPlacement.builder();
    }

    /**
     * A new builder for the noise-based placement modifier.
     * @return a new noise-based placement modifier builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdCountPlacement.Builder noiseThresholdCount() {
        return NoiseThresholdCountPlacement.builder();
    }

    /**
     * A new builder for the random offset placement modifier.
     * @return a new random offset placement modifier builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomOffsetPlacement.Builder randomOffset() {
        return RandomOffsetPlacement.builder();
    }

    /**
     * A new instance of the rarity filter placement modifier.
     * @param chance the average chance denominator, a positive integer
     * @return a new rarity filter placement modifier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RarityFilter rarityFilter(int chance) {
        return RarityFilter.of(chance);
    }

    /**
     * A new builder for the surface relative threshold filter.
     * @return a new surface relative threshold filter builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SurfaceRelativeThresholdFilter.Builder surfaceRelativeThreshold() {
        return SurfaceRelativeThresholdFilter.builder();
    }

    /**
     * A new instance of the surface water depth filter.
     * @param maxWaterDepth the maximum allowed water depth
     * @return a new surface water depth filter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static SurfaceWaterDepthFilter surfaceWaterDepthFilter(int maxWaterDepth) {
        return SurfaceWaterDepthFilter.of(maxWaterDepth);
    }

    /**
     * Reads a Minecraft placement modifier into a wrapper.
     * @param minecraftModifier the Minecraft modifier to read
     * @return the wrapper for the modifier
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static PlacementModifier decode(Object minecraftModifier) {
        record Holder() {
            static final Decoder<PlacementModifier> DECODER = Decoder.create("dev.wyck.decode.worldgen.placement.PlacementModifierDecoders");
        }
        return Holder.DECODER.decode(minecraftModifier);
    }
}