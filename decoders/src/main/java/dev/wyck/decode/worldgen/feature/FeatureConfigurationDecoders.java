package dev.wyck.decode.worldgen.feature;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
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
import dev.wyck.worldgen.feature.configurations.FallenTreeConfiguration;
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
import dev.wyck.worldgen.feature.configurations.RootSystemConfiguration;
import dev.wyck.worldgen.feature.configurations.SculkPatchConfiguration;
import dev.wyck.worldgen.feature.configurations.SpringConfiguration;
import dev.wyck.worldgen.feature.configurations.SpikeConfiguration;
import dev.wyck.worldgen.feature.configurations.SpeleothemClusterConfiguration;
import dev.wyck.worldgen.feature.configurations.SpeleothemConfiguration;
import dev.wyck.worldgen.feature.configurations.TwistingVinesConfig;
import dev.wyck.worldgen.feature.configurations.UnderwaterMagmaConfiguration;
import dev.wyck.worldgen.feature.configurations.WeightedRandomFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.VegetationPatchConfiguration;
import dev.wyck.worldgen.feature.configurations.TreeConfiguration;
import dev.wyck.worldgen.feature.configurations.TemplateFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.GeodeConfiguration;
import dev.wyck.worldgen.feature.configurations.FossilFeatureConfiguration;
import dev.wyck.worldgen.feature.configurations.HugeFungusConfiguration;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettings;
import dev.wyck.worldgen.Rotation;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.material.FluidState;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.decode.DecoderRegistry;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FeatureConfigurationDecoders extends DecoderRegistry<FeatureConfiguration, net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> {

    private static final String[] NO_OP_CODECS = {
        "no_op",
        // TODO: wrap the rest of these
        "chorus_plant",
        "void_start_platform",
        "desert_well",
        "glowstone_blob",
        "freeze_top_layer",
        "vines",
        "monster_room",
        "blue_ice",
        "end_platform",
        "end_island",
        "kelp",
        "coral_tree",
        "coral_mushroom",
        "coral_claw",
        "weeping_vines",
        "bonus_chest",
        "basalt_pillar"
    };

    public FeatureConfigurationDecoders() {
        for (String type : NO_OP_CODECS) {
            register(type, _ -> NoneFeatureConfiguration.INSTANCE);
        }
        register("seagrass", configured -> ProbabilityFeatureConfiguration.of(
            ((net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration) configuration(configured)).probability
        ));
        register("bamboo", configured -> ProbabilityFeatureConfiguration.of(
            ((net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration) configuration(configured)).probability
        ));
        register("sea_pickle", configured -> CountConfiguration.of(IntProvider.decode(
            ((net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration) configuration(configured)).count()
        )));
        register("iceberg", configured -> BlockStateConfiguration.of(Decoders.blockData(
            ((net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration) configuration(configured)).state
        )));
        register("block_pile", configured -> BlockPileConfiguration.of(BlockStateProvider.decode(
            ((net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration) configuration(configured)).stateProvider
        )));
        register("simple_block", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration) configuration(configured);
            return SimpleBlockConfiguration.of(BlockStateProvider.decode(config.toPlace()), config.scheduleTick());
        });
        register("fill_layer", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration) configuration(configured);
            return LayerConfiguration.of(config.height, Decoders.blockData(config.state));
        });
        register("underwater_magma", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration) configuration(configured);
            return UnderwaterMagmaConfiguration.of(
                config.floorSearchRange,
                config.placementRadiusAroundFloor,
                config.placementProbabilityPerValidPosition
            );
        });
        register("nether_forest_vegetation", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig) configuration(configured);
            return NetherForestVegetationConfig.of(
                BlockStateProvider.decode(config.stateProvider), config.spreadWidth, config.spreadHeight
            );
        });
        register("twisting_vines", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig) configuration(configured);
            return TwistingVinesConfig.of(config.spreadWidth(), config.spreadHeight(), config.maxHeight());
        });
        register("basalt_columns", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration) configuration(configured);
            return ColumnFeatureConfiguration.of(
                IntProvider.decode(config.reach()), IntProvider.decode(config.height())
            );
        });
        register("delta_feature", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration) configuration(configured);
            return DeltaFeatureConfiguration.of(
                Decoders.blockData(config.contents()), Decoders.blockData(config.rim()),
                IntProvider.decode(config.size()), IntProvider.decode(config.rimSize())
            );
        });
        register("netherrack_replace_blobs", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration) configuration(configured);
            return ReplaceSphereConfiguration.of(
                Decoders.blockData(config.targetState), Decoders.blockData(config.replaceState),
                IntProvider.decode(config.radius())
            );
        });
        register("block_blob", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.BlockBlobConfiguration) configuration(configured);
            return BlockBlobConfiguration.of(
                Decoders.blockData(config.state()), BlockPredicate.decode(config.canPlaceOn())
            );
        });
        register("huge_red_mushroom", this::hugeMushroom);
        register("huge_brown_mushroom", this::hugeMushroom);
        register("disk", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration) configuration(configured);
            return DiskConfiguration.of(
                BlockStateProvider.decode(config.stateProvider()),
                BlockPredicate.decode(config.target()),
                IntProvider.decode(config.radius()),
                config.halfHeight()
            );
        });
        register("lake", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration) configuration(configured);
            return LakeFeatureConfiguration.create(
                BlockStateProvider.decode(config.fluid()),
                BlockStateProvider.decode(config.barrier()),
                BlockPredicate.decode(config.canPlaceFeature()),
                BlockPredicate.decode(config.canReplaceWithAirOrFluid()),
                BlockPredicate.decode(config.canReplaceWithBarrier())
            );
        });
        register("sculk_patch", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration) configuration(configured);
            return SculkPatchConfiguration.of(
                config.chargeCount(), config.amountPerCharge(), config.spreadAttempts(),
                config.growthRounds(), config.spreadRounds(),
                IntProvider.decode(config.extraRareGrowths()), config.catalystChance()
            );
        });
        register("spring_feature", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration) configuration(configured);
            return SpringConfiguration.of(
                FluidState.decode(config.state), config.requiresBlockBelow,
                config.rockCount, config.holeCount, Decoders.materials(config.validBlocks)
            );
        });
        register("multiface_growth", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration) configuration(configured);
            return MultifaceGrowthConfiguration.of(
                CraftBlockType.minecraftToBukkit(config.placeBlock), config.searchRange,
                config.canPlaceOnFloor, config.canPlaceOnCeiling, config.canPlaceOnWall,
                config.chanceOfSpreading, Decoders.materials(config.canBePlacedOn)
            );
        });
        register("random_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration) configuration(configured);
            return RandomFeatureConfiguration.of(
                config.features().stream().map(weighted -> RandomFeatureConfiguration.weighted(
                    PlacedFeature.decode(weighted.feature()), weighted.chance()
                )).toList(),
                PlacedFeature.decode(config.defaultFeature())
            );
        });
        register("weighted_random_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.WeightedRandomFeatureConfiguration) configuration(configured);
            return WeightedRandomFeatureConfiguration.of(Decoders.weighted(
                config.features(), PlacedFeature::decode
            ));
        });
        register("simple_random_selector", this::composite);
        register("sequence", this::composite);
        register("random_boolean_selector", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration) configuration(configured);
            return RandomBooleanFeatureConfiguration.of(
                PlacedFeature.decode(config.featureTrue),
                PlacedFeature.decode(config.featureFalse)
            );
        });
        register("end_gateway", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration) configuration(configured);
            return config.getExit()
                .map(exit -> EndGatewayConfiguration.knownExit(vector(exit), config.isExitExact()))
                .orElseGet(EndGatewayConfiguration::delayedExitSearch);
        });
        register("end_spike", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.EndSpikeConfiguration) configuration(configured);
            var target = config.getCrystalBeamTarget();
            return EndSpikeConfiguration.of(
                config.isCrystalInvulnerable(),
                config.getSpikes().stream().map(EndSpike::decode).toList(),
                target == null ? null : vector(target)
            );
        });
        register("speleothem", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SpeleothemConfiguration) configuration(configured);
            return SpeleothemConfiguration.of(
                Decoders.blockData(config.baseBlock()), Decoders.blockData(config.pointedBlock()),
                Decoders.materials(config.replaceableBlocks()), config.chanceOfTallerGeneration(),
                config.chanceOfDirectionalSpread(), config.chanceOfSpreadRadius2(), config.chanceOfSpreadRadius3()
            );
        });
        register("large_dripstone", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration) configuration(configured);
            return LargeDripstoneConfiguration.of(
                Decoders.materials(config.replaceableBlocks), config.floorToCeilingSearchRange,
                IntProvider.decode(config.columnRadius), FloatProvider.decode(config.heightScale),
                config.maxColumnRadiusToCaveHeightRatio,
                FloatProvider.decode(config.stalactiteBluntness),
                FloatProvider.decode(config.stalagmiteBluntness),
                FloatProvider.decode(config.windSpeed), config.minRadiusForWind, config.minBluntnessForWind
            );
        });
        register("speleothem_cluster", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SpeleothemClusterConfiguration) configuration(configured);
            return SpeleothemClusterConfiguration.builder()
                .baseBlock(Decoders.blockData(config.baseBlock()))
                .pointedBlock(Decoders.blockData(config.pointedBlock()))
                .replaceableBlocks(Decoders.materials(config.replaceableBlocks()))
                .floorToCeilingSearchRange(config.floorToCeilingSearchRange())
                .height(IntProvider.decode(config.height()))
                .radius(IntProvider.decode(config.radius()))
                .maxStalagmiteStalactiteHeightDiff(config.maxStalagmiteStalactiteHeightDiff())
                .heightDeviation(config.heightDeviation())
                .speleothemBlockLayerThickness(IntProvider.decode(config.speleothemBlockLayerThickness()))
                .density(FloatProvider.decode(config.density()))
                .wetness(FloatProvider.decode(config.wetness()))
                .chanceOfSpeleothemAtMaxDistanceFromCenter(config.chanceOfSpeleothemAtMaxDistanceFromCenter())
                .maxDistanceFromEdgeAffectingChanceOfSpeleothem(config.maxDistanceFromEdgeAffectingChanceOfSpeleothem())
                .maxDistanceFromCenterAffectingHeightBias(config.maxDistanceFromCenterAffectingHeightBias())
                .build();
        });
        register("spike", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration) configuration(configured);
            return SpikeConfiguration.of(
                Decoders.blockData(config.state()), BlockPredicate.decode(config.canPlaceOn()),
                BlockPredicate.decode(config.canReplace())
            );
        });
        register("block_column", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration) configuration(configured);
            return BlockColumnConfiguration.of(
                config.layers().stream().map(layer -> BlockColumnConfiguration.layer(
                    IntProvider.decode(layer.height()), BlockStateProvider.decode(layer.state())
                )).toList(),
                CraftBlock.notchToBlockFace(config.direction()),
                BlockPredicate.decode(config.allowedPlacement()), config.prioritizeTip()
            );
        });
        register("ore", this::ore);
        register("scattered_ore", this::ore);
        register("replace_single_block", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration)
                configuration(configured);
            return ReplaceBlockConfiguration.of(targets(config.targetStates));
        });
        register("vegetation_patch", this::vegetationPatch);
        register("waterlogged_vegetation_patch", this::vegetationPatch);
        register("tree", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration)
                configuration(configured);
            return TreeConfiguration.of(
                BlockStateProvider.decode(config.trunkProvider),
                TrunkPlacer.decode(config.trunkPlacer),
                BlockStateProvider.decode(config.foliageProvider),
                FoliagePlacer.decode(config.foliagePlacer),
                config.rootPlacer.map(RootPlacer::decode).orElse(null),
                FeatureSize.decode(config.minimumSize),
                config.decorators.stream().map(TreeDecorator::decode).toList(),
                config.ignoreVines, BlockStateProvider.decode(config.belowTrunkProvider)
            );
        });
        register("fallen_tree", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration)
                configuration(configured);
            return FallenTreeConfiguration.of(
                BlockStateProvider.decode(config.trunkProvider), IntProvider.decode(config.logLength),
                config.stumpDecorators.stream().map(TreeDecorator::decode).toList(),
                config.logDecorators.stream().map(TreeDecorator::decode).toList()
            );
        });
        register("root_system", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration)
                configuration(configured);
            return RootSystemConfiguration.of(
                PlacedFeature.decode(config.treeFeature()), config.requiredVerticalSpaceForTree(),
                config.levelTestDistance(), config.maxLevelDeviation(), config.rootRadius(),
                TagSet.blocksFromMinecraft(config.rootReplaceable()),
                BlockStateProvider.decode(config.rootStateProvider()), config.rootPlacementAttempts(),
                config.rootColumnMaxHeight(), config.hangingRootRadius(), config.hangingRootsVerticalSpan(),
                BlockStateProvider.decode(config.hangingRootStateProvider()),
                config.hangingRootPlacementAttempts(), config.allowedVerticalWaterForTree(),
                BlockPredicate.decode(config.allowedTreePosition())
            );
        });
        register("template", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration)
                configuration(configured);
            return TemplateFeatureConfiguration.of(Decoders.weighted(config.templates(), entry ->
                new TemplateFeatureConfiguration.TemplateEntry(
                    Decoders.key(entry.template()),
                    entry.rotations().stream().map(Rotation.TRANSLATOR::fromNms).toList()
                )
            ));
        });
        register("geode", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration)
                configuration(configured);
            return GeodeConfiguration.of(
                GeodeBlockSettings.decode(config.geodeBlockSettings()),
                GeodeLayerSettings.decode(config.geodeLayerSettings()),
                GeodeCrackSettings.decode(config.geodeCrackSettings()),
                config.usePotentialPlacementsChance(), config.useAlternateLayer0Chance(),
                config.placementsRequireLayer0Alternate(), IntProvider.decode(config.outerWallDistance()),
                IntProvider.decode(config.distributionPoints()), IntProvider.decode(config.pointOffset()),
                config.minGenOffset(), config.maxGenOffset(), config.noiseMultiplier(), config.invalidBlocksThreshold()
            );
        });
        register("huge_fungus", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration)
                configuration(configured);
            return HugeFungusConfiguration.of(
                Decoders.blockData(config.validBaseState), Decoders.blockData(config.stemState),
                Decoders.blockData(config.hatState), Decoders.blockData(config.decorState),
                BlockPredicate.decode(config.replaceableBlocks), config.planted
            );
        });
        register("fossil", configured -> {
            var config = (net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration)
                configuration(configured);
            return FossilFeatureConfiguration.of(
                config.fossilStructures.stream().map(Decoders::key).toList(),
                config.overlayStructures.stream().map(Decoders::key).toList(),
                Decoders.referenceKey(config.fossilProcessors),
                Decoders.referenceKey(config.overlayProcessors),
                config.maxEmptyCornersAllowed
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured) {
        return Decoders.registryKey(BuiltInRegistries.FEATURE, configured.feature());
    }

    private static net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured(
        Object value
    ) {
        return (net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>) value;
    }

    private static net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration configuration(Object value) {
        return configured(value).config();
    }

    private static BlockVector vector(net.minecraft.core.BlockPos position) {
        return new BlockVector(position.getX(), position.getY(), position.getZ());
    }

    private FeatureConfiguration hugeMushroom(Object configured) {
        var config = (net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration) configuration(configured);
        return HugeMushroomFeatureConfiguration.of(
            BlockStateProvider.decode(config.capProvider()),
            BlockStateProvider.decode(config.stemProvider()),
            config.foliageRadius(),
            BlockPredicate.decode(config.canPlaceOn())
        );
    }

    private FeatureConfiguration composite(Object configured) {
        var config = (net.minecraft.world.level.levelgen.feature.configurations.CompositeFeatureConfiguration) configuration(configured);
        return CompositeFeatureConfiguration.of(config.features().stream()
            .map(PlacedFeature::decode)
            .toList());
    }

    private FeatureConfiguration ore(Object configured) {
        var config = (net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration)
            configuration(configured);
        return OreConfiguration.of(
            targets(config.targetStates), config.size, config.discardChanceOnAirExposure
        );
    }

    private FeatureConfiguration vegetationPatch(Object configured) {
        var config = (net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration)
            configuration(configured);
        return VegetationPatchConfiguration.of(
            TagSet.decodeBlocks(config.replaceable()),
            BlockStateProvider.decode(config.groundState()),
            PlacedFeature.decode(config.vegetationFeature()),
            CaveSurface.TRANSLATOR.fromNms(config.surface()),
            IntProvider.decode(config.depth()), config.extraBottomBlockChance(),
            config.verticalRange(), config.vegetationChance(),
            IntProvider.decode(config.xzRadius()), config.extraEdgeColumnChance()
        );
    }

    private static java.util.List<OreConfiguration.TargetBlockState> targets(
        java.util.List<net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState> targets
    ) {
        return targets.stream().map(target -> OreConfiguration.target(
            RuleTest.decode(target.target), Decoders.blockData(target.state)
        )).toList();
    }
}
