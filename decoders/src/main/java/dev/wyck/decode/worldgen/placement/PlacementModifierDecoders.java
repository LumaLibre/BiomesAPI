package dev.wyck.decode.worldgen.placement;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.placement.BiomeFilter;
import dev.wyck.worldgen.placement.BlockPredicateFilter;
import dev.wyck.worldgen.placement.CountOnEveryLayerPlacement;
import dev.wyck.worldgen.placement.CountPlacement;
import dev.wyck.worldgen.placement.EnvironmentScanPlacement;
import dev.wyck.worldgen.placement.FixedPlacement;
import dev.wyck.worldgen.placement.HeightRangePlacement;
import dev.wyck.worldgen.placement.HeightmapPlacement;
import dev.wyck.worldgen.placement.InSquarePlacement;
import dev.wyck.worldgen.placement.NoiseBasedCountPlacement;
import dev.wyck.worldgen.placement.NoiseThresholdCountPlacement;
import dev.wyck.worldgen.placement.PlacementModifier;
import dev.wyck.worldgen.placement.RandomOffsetPlacement;
import dev.wyck.worldgen.placement.RarityFilter;
import dev.wyck.worldgen.placement.SurfaceRelativeThresholdFilter;
import dev.wyck.worldgen.placement.SurfaceWaterDepthFilter;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("deprecation")
public final class PlacementModifierDecoders extends DecoderRegistry<PlacementModifier, net.minecraft.world.level.levelgen.placement.PlacementModifier> {

    public PlacementModifierDecoders() {
        register("block_predicate_filter", modifier -> BlockPredicateFilter.of(BlockPredicate.decode(
            FastReflection.read(modifier, "predicate")
        )));
        register("rarity_filter", modifier -> RarityFilter.of(FastReflection.read(modifier, "chance")));
        register("surface_relative_threshold_filter", modifier -> SurfaceRelativeThresholdFilter.of(
            heightmap(modifier),
            FastReflection.read(modifier, "minInclusive"),
            FastReflection.read(modifier, "maxInclusive")
        ));
        register("surface_water_depth_filter", modifier -> SurfaceWaterDepthFilter.of(
            FastReflection.read(modifier, "maxWaterDepth")
        ));
        register("biome", _ -> BiomeFilter.INSTANCE);
        register("count", modifier -> CountPlacement.of(IntProvider.decode(
            FastReflection.read(modifier, "count")
        )));
        register("noise_based_count", modifier -> NoiseBasedCountPlacement.of(
            FastReflection.read(modifier, "noiseToCountRatio"),
            FastReflection.read(modifier, "noiseFactor"),
            FastReflection.read(modifier, "noiseOffset")
        ));
        register("noise_threshold_count", modifier -> NoiseThresholdCountPlacement.of(
            FastReflection.read(modifier, "noiseLevel"),
            FastReflection.read(modifier, "belowNoise"),
            FastReflection.read(modifier, "aboveNoise")
        ));
        register("count_on_every_layer", modifier -> CountOnEveryLayerPlacement.of(IntProvider.decode(
            FastReflection.read(modifier, "count")
        )));
        register("environment_scan", modifier -> EnvironmentScanPlacement.of(
            CraftBlock.notchToBlockFace(FastReflection.<Direction>read(modifier, "directionOfSearch")),
            BlockPredicate.decode(FastReflection.read(modifier, "targetCondition")),
            BlockPredicate.decode(FastReflection.read(modifier, "allowedSearchCondition")),
            FastReflection.read(modifier, "maxSteps")
        ));
        register("heightmap", modifier -> HeightmapPlacement.of(heightmap(modifier)));
        register("height_range", modifier -> HeightRangePlacement.of(HeightProvider.decode(
            FastReflection.read(modifier, "height")
        )));
        register("in_square", _ -> InSquarePlacement.INSTANCE);
        register("random_offset", modifier -> RandomOffsetPlacement.of(
            IntProvider.decode(FastReflection.read(modifier, "xzSpread")),
            IntProvider.decode(FastReflection.read(modifier, "ySpread"))
        ));
        register("fixed_placement", modifier -> {
            List<BlockPos> positions = FastReflection.read(modifier, "positions");
            return FixedPlacement.of(positions.stream()
                .map(pos -> new BlockVector(pos.getX(), pos.getY(), pos.getZ()))
                .toList());
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.placement.PlacementModifier modifier) {
        return Decoders.registryKey(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, modifier.type());
    }

    private static HeightmapType heightmap(Object modifier) {
        Heightmap.Types heightmap = FastReflection.read(modifier, "heightmap");
        return HeightmapType.TRANSLATOR.fromNms(heightmap);
    }
}
