package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.UniformHeight;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
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
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
@SuppressWarnings("deprecation")
class PlacementModifierDecodeTest {

    @Test
    void everyVanillaPlacementModifierTypeHasADecoder() {
        var decoders = new dev.wyck.decode.worldgen.placement.PlacementModifierDecoders();
        List<ResourceKey> missing = BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !decoders.handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no placement modifier decoder is registered for: " + missing);
    }

    @Test
    void everyPlacementModifierRoundTripsToTheSameDispatchType() {
        var decoders = new dev.wyck.decode.worldgen.placement.PlacementModifierDecoders();
        HeightProvider height = UniformHeight.of(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80));
        List<PlacementModifier> modifiers = List.of(
            BlockPredicateFilter.of(BlockPredicate.matchingBlocks().block(Material.STONE).build()),
            RarityFilter.of(5),
            SurfaceRelativeThresholdFilter.of(HeightmapType.OCEAN_FLOOR_WG, -4, 9),
            SurfaceWaterDepthFilter.of(3),
            BiomeFilter.INSTANCE,
            CountPlacement.of(IntProvider.uniform(1, 4)),
            NoiseBasedCountPlacement.of(7, 80.0, 0.25),
            NoiseThresholdCountPlacement.of(-0.3, 2, 8),
            CountOnEveryLayerPlacement.of(IntProvider.constant(3)),
            EnvironmentScanPlacement.of(
                BlockFace.DOWN,
                BlockPredicate.matchingBlocks().block(Material.STONE).build(),
                BlockPredicate.not(BlockPredicate.matchingBlocks().block(Material.LAVA).build()),
                12
            ),
            HeightmapPlacement.of(HeightmapType.MOTION_BLOCKING_NO_LEAVES),
            HeightRangePlacement.of(height),
            InSquarePlacement.INSTANCE,
            RandomOffsetPlacement.of(IntProvider.uniform(-3, 3), IntProvider.constant(2)),
            FixedPlacement.of(List.of(new BlockVector(1, 20, 2), new BlockVector(7, 30, 9)))
        );

        for (PlacementModifier original : modifiers) {
            Object minecraft = original.asHandle();
            PlacementModifier decoded = PlacementModifier.decode(minecraft);
            assertEquals(decoders.typeOf((net.minecraft.world.level.levelgen.placement.PlacementModifier) minecraft),
                decoders.typeOf((net.minecraft.world.level.levelgen.placement.PlacementModifier) decoded.asHandle()),
                () -> original + " decoded to a different placement type");
        }
    }

    @Test
    void nestedAndPositionalPlacementFieldsDecode() {
        EnvironmentScanPlacement scan = assertInstanceOf(EnvironmentScanPlacement.class,
            PlacementModifier.decode(EnvironmentScanPlacement.of(
                BlockFace.UP,
                BlockPredicate.replaceable().offset(0, 2, 0).build(),
                BlockPredicate.alwaysTrue(),
                17
            ).asHandle()));
        assertEquals(BlockFace.UP, scan.directionOfSearch());
        assertEquals(17, scan.maxSteps());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.ReplaceablePredicate.class, scan.targetCondition());

        RandomOffsetPlacement offset = assertInstanceOf(RandomOffsetPlacement.class,
            PlacementModifier.decode(RandomOffsetPlacement.of(
                IntProvider.uniform(-5, 5), IntProvider.constant(3)
            ).asHandle()));
        assertEquals(-5, offset.xzSpread().minInclusive());
        assertEquals(5, offset.xzSpread().maxInclusive());
        assertEquals(3, offset.ySpread().minInclusive());

        FixedPlacement fixed = assertInstanceOf(FixedPlacement.class,
            PlacementModifier.decode(FixedPlacement.of(
                new BlockVector(2, 40, 3), new BlockVector(4, 60, 5)
            ).asHandle()));
        assertEquals(List.of(new BlockVector(2, 40, 3), new BlockVector(4, 60, 5)), fixed.positions());
    }
}
