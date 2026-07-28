package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.BlockPileConfiguration;
import dev.wyck.worldgen.feature.configurations.BlockBlobConfiguration;
import dev.wyck.worldgen.feature.configurations.BlockColumnConfiguration;
import dev.wyck.worldgen.feature.configurations.BlockStateConfiguration;
import dev.wyck.worldgen.feature.configurations.ColumnFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.CountConfiguration;
import dev.wyck.worldgen.feature.configurations.CompositeFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.DeltaFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.DiskConfiguration;
import dev.wyck.worldgen.feature.configurations.EndGatewayConfiguration;
import dev.wyck.worldgen.feature.configurations.EndSpikeConfiguration;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.LayerConfiguration;
import dev.wyck.worldgen.feature.configurations.LakeFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.LargeDripstoneConfiguration;
import dev.wyck.worldgen.feature.configurations.MultifaceGrowthConfiguration;
import dev.wyck.worldgen.feature.configurations.NoneFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.NetherForestVegetationConfig;
import dev.wyck.worldgen.feature.configurations.HugeMushroomFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.ProbabilityFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.OreConfiguration;
import dev.wyck.worldgen.feature.configurations.RandomBooleanFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.RandomFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.SimpleBlockConfiguration;
import dev.wyck.worldgen.feature.configurations.ReplaceSphereConfiguration;
import dev.wyck.worldgen.feature.configurations.ReplaceBlockConfiguration;
import dev.wyck.worldgen.feature.configurations.SculkPatchConfiguration;
import dev.wyck.worldgen.feature.configurations.SpringConfiguration;
import dev.wyck.worldgen.feature.configurations.SpikeConfiguration;
import dev.wyck.worldgen.feature.configurations.SpeleothemClusterConfiguration;
import dev.wyck.worldgen.feature.configurations.SpeleothemConfiguration;
import dev.wyck.worldgen.feature.configurations.TwistingVinesConfig;
import dev.wyck.worldgen.feature.configurations.UnderwaterMagmaConfiguration;
import dev.wyck.worldgen.feature.configurations.WeightedRandomFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.VegetationPatchConfiguration;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.worldgen.feature.types.ComposedConfiguredFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.material.FluidState;
import dev.wyck.worldgen.material.FluidType;
import dev.wyck.worldgen.stateproviders.SimpleStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.placement.PlacedFeatures;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class ConfiguredFeatureDecodeTest {

    private static final Set<ResourceKey> UNWRAPPED = Set.of();

    @Test
    void everyVanillaFeatureTypeIsDecodedOrExplicitlyUnwrapped() {
        var decoders = new dev.wyck.decode.worldgen.feature.FeatureConfigurationDecoders();
        List<ResourceKey> missing = BuiltInRegistries.FEATURE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !UNWRAPPED.contains(key))
            .filter(key -> !decoders.handles(key))
            .toList();
        assertTrue(missing.isEmpty(), () -> "no feature configuration decoder is registered for: " + missing);

        List<ResourceKey> stale = UNWRAPPED.stream()
            .filter(decoders::handles)
            .toList();
        assertTrue(stale.isEmpty(), () -> "these feature types now have decoders: " + stale);
    }

    @Test
    void simpleConfigurationShapesDecode() {
        assertConfig(FeatureType.NO_OP, NoneFeatureConfiguration.INSTANCE, NoneFeatureConfiguration.class);

        ProbabilityFeatureConfiguration probability = assertInstanceOf(ProbabilityFeatureConfiguration.class,
            decode(FeatureType.BAMBOO, ProbabilityFeatureConfiguration.of(0.35f)).config());
        assertEquals(0.35f, probability.probability());

        CountConfiguration count = assertInstanceOf(CountConfiguration.class,
            decode(FeatureType.SEA_PICKLE, CountConfiguration.of(IntProvider.uniform(2, 7))).config());
        assertEquals(2, count.count().minInclusive());
        assertEquals(7, count.count().maxInclusive());

        BlockStateConfiguration state = assertInstanceOf(BlockStateConfiguration.class,
            decode(FeatureType.ICEBERG, BlockStateConfiguration.of(
                BukkitBootstrapUtil.util().createBlockData(Material.PACKED_ICE)
            )).config());
        assertEquals(Material.PACKED_ICE, state.state().getMaterial());

        LayerConfiguration layer = assertInstanceOf(LayerConfiguration.class,
            decode(FeatureType.FILL_LAYER, LayerConfiguration.of(
                6, BukkitBootstrapUtil.util().createBlockData(Material.STONE)
            )).config());
        assertEquals(6, layer.height());
        assertEquals(Material.STONE, layer.state().getMaterial());

        UnderwaterMagmaConfiguration magma = assertInstanceOf(UnderwaterMagmaConfiguration.class,
            decode(FeatureType.UNDERWATER_MAGMA, UnderwaterMagmaConfiguration.of(32, 4, 0.6f)).config());
        assertEquals(32, magma.floorSearchRange());
        assertEquals(4, magma.placementRadiusAroundFloor());
        assertEquals(0.6f, magma.placementProbabilityPerValidPosition());
    }

    @Test
    void configurationsStackThroughStateProviders() {
        BlockPileConfiguration pile = assertInstanceOf(BlockPileConfiguration.class,
            decode(FeatureType.BLOCK_PILE, BlockPileConfiguration.of(
                BlockStateProvider.simple(Material.MOSS_BLOCK)
            )).config());
        assertInstanceOf(SimpleStateProvider.class, pile.stateProvider());

        SimpleBlockConfiguration simple = assertInstanceOf(SimpleBlockConfiguration.class,
            decode(FeatureType.SIMPLE_BLOCK, SimpleBlockConfiguration.of(
                BlockStateProvider.simple(Material.AZALEA), true
            )).config());
        assertInstanceOf(SimpleStateProvider.class, simple.toPlace());
        assertTrue(simple.scheduleTick());
    }

    @Test
    void providerAndScalarConfigurationBatchDecodes() {
        NetherForestVegetationConfig vegetation = assertInstanceOf(NetherForestVegetationConfig.class,
            decode(FeatureType.NETHER_FOREST_VEGETATION, NetherForestVegetationConfig.of(
                BlockStateProvider.simple(Material.CRIMSON_ROOTS), 8, 4
            )).config());
        assertInstanceOf(SimpleStateProvider.class, vegetation.stateProvider());
        assertEquals(8, vegetation.spreadWidth());

        TwistingVinesConfig vines = assertInstanceOf(TwistingVinesConfig.class,
            decode(FeatureType.TWISTING_VINES, TwistingVinesConfig.of(8, 4, 12)).config());
        assertEquals(12, vines.maxHeight());

        ColumnFeatureConfiguration columns = assertInstanceOf(ColumnFeatureConfiguration.class,
            decode(FeatureType.BASALT_COLUMNS, ColumnFeatureConfiguration.of(
                IntProvider.uniform(0, 3), IntProvider.uniform(2, 7)
            )).config());
        assertEquals(7, columns.height().maxInclusive());

        DeltaFeatureConfiguration delta = assertInstanceOf(DeltaFeatureConfiguration.class,
            decode(FeatureType.DELTA_FEATURE, DeltaFeatureConfiguration.of(
                data(Material.LAVA), data(Material.MAGMA_BLOCK),
                IntProvider.uniform(1, 5), IntProvider.uniform(0, 2)
            )).config());
        assertEquals(Material.LAVA, delta.contents().getMaterial());
        assertEquals(5, delta.size().maxInclusive());

        ReplaceSphereConfiguration replace = assertInstanceOf(ReplaceSphereConfiguration.class,
            decode(FeatureType.REPLACE_BLOBS, ReplaceSphereConfiguration.of(
                data(Material.NETHERRACK), data(Material.BLACKSTONE), IntProvider.uniform(2, 6)
            )).config());
        assertEquals(Material.BLACKSTONE, replace.replaceState().getMaterial());

        BlockBlobConfiguration blob = assertInstanceOf(BlockBlobConfiguration.class,
            decode(FeatureType.BLOCK_BLOB, BlockBlobConfiguration.of(
                data(Material.MOSSY_COBBLESTONE), BlockPredicate.matchingBlocks().block(Material.STONE).build()
            )).config());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicate.class, blob.canPlaceOn());
    }

    @Test
    void nestedWorldgenConfigurationBatchDecodes() {
        HugeMushroomFeatureConfiguration mushroom = assertInstanceOf(HugeMushroomFeatureConfiguration.class,
            decode(FeatureType.HUGE_RED_MUSHROOM, HugeMushroomFeatureConfiguration.of(
                BlockStateProvider.simple(Material.RED_MUSHROOM_BLOCK),
                BlockStateProvider.simple(Material.MUSHROOM_STEM), 3,
                BlockPredicate.matchingBlocks().block(Material.DIRT).build()
            )).config());
        assertEquals(3, mushroom.foliageRadius());
        assertInstanceOf(SimpleStateProvider.class, mushroom.capProvider());

        DiskConfiguration disk = assertInstanceOf(DiskConfiguration.class,
            decode(FeatureType.DISK, DiskConfiguration.of(
                BlockStateProvider.simple(Material.SAND),
                BlockPredicate.matchingBlocks().block(Material.DIRT).build(),
                IntProvider.uniform(2, 6), 2
            )).config());
        assertEquals(6, disk.radius().maxInclusive());
        assertEquals(2, disk.halfHeight());

        LakeFeatureConfiguration lake = assertInstanceOf(LakeFeatureConfiguration.class,
            decode(FeatureType.LAKE, LakeFeatureConfiguration.create(
                BlockStateProvider.simple(Material.WATER),
                BlockStateProvider.simple(Material.STONE),
                BlockPredicate.alwaysTrue(),
                BlockPredicate.replaceable().build(),
                BlockPredicate.matchingBlocks().block(Material.DIRT).build()
            )).config());
        assertInstanceOf(SimpleStateProvider.class, lake.fluid());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.ReplaceablePredicate.class,
            lake.canReplaceWithAirOrFluid());

        SculkPatchConfiguration sculk = assertInstanceOf(SculkPatchConfiguration.class,
            decode(FeatureType.SCULK_PATCH, SculkPatchConfiguration.of(
                4, 32, 16, 2, 3, IntProvider.uniform(0, 2), 0.15f
            )).config());
        assertEquals(4, sculk.chargeCount());
        assertEquals(2, sculk.extraRareGrowths().maxInclusive());

        SpringConfiguration spring = assertInstanceOf(SpringConfiguration.class,
            decode(FeatureType.SPRING, SpringConfiguration.of(
                FluidState.flowing(FluidType.FLOWING_WATER, 5, true),
                true, 4, 1, Set.of(Material.STONE, Material.DEEPSLATE)
            )).config());
        assertEquals(FluidType.FLOWING_WATER, spring.state().fluid());
        assertEquals(5, spring.state().amount());
        assertTrue(spring.state().falling());
        assertEquals(Set.of(Material.STONE, Material.DEEPSLATE), spring.validBlocks());

        MultifaceGrowthConfiguration multiface = assertInstanceOf(MultifaceGrowthConfiguration.class,
            decode(FeatureType.MULTIFACE_GROWTH, MultifaceGrowthConfiguration.of(
                Material.GLOW_LICHEN, 12, true, false, true, 0.4f,
                Set.of(Material.STONE, Material.DIRT)
            )).config());
        assertEquals(Material.GLOW_LICHEN, multiface.placeBlock());
        assertEquals(Set.of(Material.STONE, Material.DIRT), multiface.canBePlacedOn());
    }

    @Test
    void selectorConfigurationsStackThroughPlacedFeatures() {
        RandomFeatureConfiguration random = assertInstanceOf(RandomFeatureConfiguration.class,
            decode(FeatureType.RANDOM_SELECTOR, RandomFeatureConfiguration.of(
                List.of(RandomFeatureConfiguration.weighted(PlacedFeatures.SEAGRASS_WARM, 0.4f)),
                PlacedFeatures.SEA_PICKLE
            )).config());
        assertEquals(0.4f, random.features().getFirst().chance());
        assertEquals(ResourceKey.minecraft("seagrass_warm"),
            ((dev.wyck.worldgen.placement.PlacedFeature.Reference) random.features().getFirst().feature()).key());

        WeightedRandomFeatureConfiguration weighted = assertInstanceOf(WeightedRandomFeatureConfiguration.class,
            decode(FeatureType.WEIGHTED_RANDOM_SELECTOR, WeightedRandomFeatureConfiguration.of(
                WeightedList.of(List.of(
                    new WeightedList.Weighted<>(PlacedFeatures.SEAGRASS_WARM, 3),
                    new WeightedList.Weighted<>(PlacedFeatures.KELP_WARM, 1)
                ))
            )).config());
        assertEquals(3, weighted.features().unwrap().getFirst().weight());

        CompositeFeatureConfiguration composite = assertInstanceOf(CompositeFeatureConfiguration.class,
            decode(FeatureType.SEQUENCE, CompositeFeatureConfiguration.of(
                List.of(PlacedFeatures.SEAGRASS_WARM, PlacedFeatures.KELP_WARM)
            )).config());
        assertEquals(2, composite.features().size());

        RandomBooleanFeatureConfiguration bool = assertInstanceOf(RandomBooleanFeatureConfiguration.class,
            decode(FeatureType.RANDOM_BOOLEAN_SELECTOR, RandomBooleanFeatureConfiguration.of(
                PlacedFeatures.SEAGRASS_WARM, PlacedFeatures.KELP_WARM
            )).config());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, bool.featureTrue());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, bool.featureFalse());
    }

    @Test
    void endAndSpeleothemConfigurationsDecode() {
        EndGatewayConfiguration gateway = assertInstanceOf(EndGatewayConfiguration.class,
            decode(FeatureType.END_GATEWAY, EndGatewayConfiguration.knownExit(
                new org.bukkit.util.BlockVector(12, 80, -7), true
            )).config());
        assertEquals(new org.bukkit.util.BlockVector(12, 80, -7), gateway.exit().orElseThrow());
        assertTrue(gateway.exact());

        EndSpikeConfiguration spikes = assertInstanceOf(EndSpikeConfiguration.class,
            decode(FeatureType.END_SPIKE, EndSpikeConfiguration.of(
                true, List.of(EndSpike.of(10, -20, 3, 90, true)),
                new org.bukkit.util.BlockVector(0, 128, 0)
            )).config());
        assertTrue(spikes.crystalInvulnerable());
        assertEquals(90, spikes.spikes().getFirst().height());
        assertTrue(spikes.spikes().getFirst().guarded());
        assertEquals(new org.bukkit.util.BlockVector(0, 128, 0), spikes.crystalBeamTarget());

        SpeleothemConfiguration speleothem = assertInstanceOf(SpeleothemConfiguration.class,
            decode(FeatureType.SPELEOTHEM, SpeleothemConfiguration.of(
                data(Material.DRIPSTONE_BLOCK), data(Material.POINTED_DRIPSTONE),
                Set.of(Material.STONE, Material.DEEPSLATE), 0.3f, 0.6f, 0.4f, 0.2f
            )).config());
        assertEquals(Material.POINTED_DRIPSTONE, speleothem.pointedBlock().getMaterial());
        assertEquals(0.3f, speleothem.chanceOfTallerGeneration());

        LargeDripstoneConfiguration large = assertInstanceOf(LargeDripstoneConfiguration.class,
            decode(FeatureType.LARGE_DRIPSTONE, LargeDripstoneConfiguration.of(
                Set.of(Material.STONE), 30, IntProvider.uniform(2, 5), FloatProvider.uniform(0.5f, 1.5f),
                0.5f, FloatProvider.uniform(0.5f, 2.0f), FloatProvider.constant(1.0f),
                FloatProvider.uniform(0.0f, 1.0f), 2, 0.5f
            )).config());
        assertEquals(5, large.columnRadius().maxInclusive());
        assertEquals(2, large.minRadiusForWind());

        SpeleothemClusterConfiguration cluster = assertInstanceOf(SpeleothemClusterConfiguration.class,
            decode(FeatureType.SPELEOTHEM_CLUSTER, SpeleothemClusterConfiguration.builder()
                .baseBlock(data(Material.DRIPSTONE_BLOCK))
                .pointedBlock(data(Material.POINTED_DRIPSTONE))
                .replaceableBlocks(Set.of(Material.STONE))
                .floorToCeilingSearchRange(30)
                .height(IntProvider.uniform(3, 8))
                .radius(IntProvider.uniform(1, 4))
                .maxStalagmiteStalactiteHeightDiff(4)
                .heightDeviation(2)
                .speleothemBlockLayerThickness(IntProvider.uniform(1, 3))
                .density(FloatProvider.uniform(0.5f, 1.0f))
                .wetness(FloatProvider.uniform(0.0f, 0.5f))
                .chanceOfSpeleothemAtMaxDistanceFromCenter(0.2f)
                .maxDistanceFromEdgeAffectingChanceOfSpeleothem(8)
                .maxDistanceFromCenterAffectingHeightBias(12)
                .build()).config());
        assertEquals(8, cluster.height().maxInclusive());
        assertEquals(3, cluster.speleothemBlockLayerThickness().maxInclusive());
    }

    @Test
    void spikeAndBlockColumnConfigurationsStackTheirChildren() {
        SpikeConfiguration spike = assertInstanceOf(SpikeConfiguration.class,
            decode(FeatureType.SPIKE, SpikeConfiguration.of(
                data(Material.PACKED_ICE),
                BlockPredicate.matchingBlocks().block(Material.SNOW_BLOCK).build(),
                BlockPredicate.matchingBlocks().block(Material.AIR).build()
            )).config());
        assertEquals(Material.PACKED_ICE, spike.state().getMaterial());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicate.class, spike.canReplace());

        BlockColumnConfiguration column = assertInstanceOf(BlockColumnConfiguration.class,
            decode(FeatureType.BLOCK_COLUMN, BlockColumnConfiguration.of(
                List.of(
                    BlockColumnConfiguration.layer(IntProvider.constant(3), BlockStateProvider.simple(Material.CAVE_VINES)),
                    BlockColumnConfiguration.layer(IntProvider.uniform(1, 4), BlockStateProvider.simple(Material.CAVE_VINES_PLANT))
                ), org.bukkit.block.BlockFace.DOWN, BlockPredicate.alwaysTrue(), true
            )).config());
        assertEquals(2, column.layers().size());
        assertEquals(4, column.layers().get(1).height().maxInclusive());
        assertEquals(org.bukkit.block.BlockFace.DOWN, column.direction());
        assertTrue(column.prioritizeTip());
    }

    @Test
    void oreConfigurationsStackThroughRuleTests() {
        OreConfiguration ore = assertInstanceOf(OreConfiguration.class,
            decode(FeatureType.ORE, OreConfiguration.of(
                List.of(
                    OreConfiguration.target(RuleTest.tagMatch(ResourceKey.minecraft("stone_ore_replaceables")), data(Material.IRON_ORE)),
                    OreConfiguration.target(RuleTest.blockMatch(Material.DEEPSLATE), data(Material.DEEPSLATE_IRON_ORE))
                ), 9, 0.25f
            )).config());
        assertEquals(2, ore.targetStates().size());
        assertInstanceOf(dev.wyck.worldgen.ruletest.TagMatchTest.class, ore.targetStates().getFirst().target());
        assertEquals(Material.DEEPSLATE_IRON_ORE, ore.targetStates().get(1).state().getMaterial());
        assertEquals(9, ore.size());
        assertEquals(0.25f, ore.discardChanceOnAirExposure());

        OreConfiguration scattered = assertInstanceOf(OreConfiguration.class,
            decode(FeatureType.SCATTERED_ORE, OreConfiguration.of(
                List.of(OreConfiguration.target(RuleTest.blockMatch(Material.NETHERRACK), data(Material.GOLD_ORE))),
                4, 0.0f
            )).config());
        assertInstanceOf(dev.wyck.worldgen.ruletest.BlockMatchTest.class,
            scattered.targetStates().getFirst().target());

        ReplaceBlockConfiguration replace = assertInstanceOf(ReplaceBlockConfiguration.class,
            decode(FeatureType.REPLACE_SINGLE_BLOCK, ReplaceBlockConfiguration.of(List.of(
                OreConfiguration.target(RuleTest.blockStateMatch(Material.STONE), data(Material.CALCITE))
            ))).config());
        assertInstanceOf(dev.wyck.worldgen.ruletest.BlockStateMatchTest.class,
            replace.targetStates().getFirst().target());
        assertEquals(Material.CALCITE, replace.targetStates().getFirst().state().getMaterial());
    }

    @Test
    void vegetationPatchesPreserveTagSetsAndNestedWorldgen() {
        VegetationPatchConfiguration patch = assertInstanceOf(VegetationPatchConfiguration.class,
            decode(FeatureType.VEGETATION_PATCH, VegetationPatchConfiguration.of(
                TagSet.ofBlockTag(ResourceKey.minecraft("moss_replaceable")),
                BlockStateProvider.simple(Material.MOSS_BLOCK), PlacedFeatures.PATCH_GRASS_NORMAL,
                CaveSurface.FLOOR, IntProvider.uniform(1, 3), 0.2f, 8,
                0.6f, IntProvider.uniform(2, 5), 0.1f
            )).config());
        assertTrue(patch.replaceable().isTag());
        assertEquals(ResourceKey.minecraft("moss_replaceable"),
            patch.replaceable().value().right().orElseThrow().key());
        assertInstanceOf(SimpleStateProvider.class, patch.groundState());
        assertInstanceOf(dev.wyck.worldgen.placement.PlacedFeature.Reference.class, patch.vegetationFeature());
        assertEquals(5, patch.xzRadius().maxInclusive());

        VegetationPatchConfiguration waterlogged = assertInstanceOf(VegetationPatchConfiguration.class,
            decode(FeatureType.WATERLOGGED_VEGETATION_PATCH, VegetationPatchConfiguration.of(
                TagSet.ofBlocks(Set.of(Material.DIRT, Material.CLAY)),
                BlockStateProvider.simple(Material.CLAY), PlacedFeatures.SEAGRASS_WARM,
                CaveSurface.CEILING, IntProvider.constant(2), 0.0f, 4,
                1.0f, IntProvider.constant(3), 0.0f
            )).config());
        assertEquals(Set.of(Material.DIRT, Material.CLAY),
            waterlogged.replaceable().value().left().orElseThrow());
        assertEquals(CaveSurface.CEILING, waterlogged.surface());
    }

    private static <C extends FeatureConfiguration> void assertConfig(
        FeatureType type, C configuration, Class<C> expected
    ) {
        assertInstanceOf(expected, decode(type, configuration).config());
    }

    private static ComposedConfiguredFeature decode(FeatureType type, FeatureConfiguration configuration) {
        ConfiguredFeature original = ConfiguredFeature.of(type, configuration);
        ComposedConfiguredFeature decoded = assertInstanceOf(ComposedConfiguredFeature.class,
            ConfiguredFeature.decode(original.asHandle()));
        assertEquals(type, decoded.type());
        return decoded;
    }

    private static ResourceKey key(String path) {
        return ResourceKey.minecraft(path);
    }

    private static org.bukkit.block.data.BlockData data(Material material) {
        return BukkitBootstrapUtil.util().createBlockData(material);
    }
}
