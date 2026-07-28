package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's FeatureConfiguration.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface FeatureConfiguration extends Wrapper {
    @ApiStatus.Internal
    Decoder<FeatureConfiguration> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.FeatureConfigurationDecoders");

    @AsOf("3.0.0")
    NoneFeatureConfiguration NONE = NoneFeatureConfiguration.INSTANCE; // vanilla

    /**
     * Gets the {@link NoneFeatureConfiguration} instance.
     * @return the none feature configuration
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static NoneFeatureConfiguration none() {
        return NONE;
    }

    /**
     * Creates a builder for a {@link BlockBlobConfiguration}.
     * @return a new block blob configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockBlobConfiguration.Builder blockBlob() {
        return BlockBlobConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link BlockColumnConfiguration}.
     * @return a new block column configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockColumnConfiguration.Builder blockColumn() {
        return BlockColumnConfiguration.builder();
    }

    /**
     * Creates a {@link BlockPileConfiguration}.
     * @param stateProvider the block state provider used for the blocks in the pile
     * @return a new block pile configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockPileConfiguration blockPile(BlockStateProvider stateProvider) {
        return BlockPileConfiguration.of(stateProvider);
    }

    /**
     * Creates a builder for a {@link BlockStateConfiguration}.
     * @return a new block state configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockStateConfiguration.Builder blockState() {
        return BlockStateConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link ColumnFeatureConfiguration}.
     * @return a new column feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ColumnFeatureConfiguration.Builder column() {
        return ColumnFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link CountConfiguration}.
     * @return a new count configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CountConfiguration.Builder count() {
        return CountConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link DeltaFeatureConfiguration}.
     * @return a new delta feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DeltaFeatureConfiguration.Builder delta() {
        return DeltaFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link DiskConfiguration}.
     * @return a new disk configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DiskConfiguration.Builder disk() {
        return DiskConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link PointedDripstoneConfiguration}.
     * @apiNote This is {@link SpeleothemConfiguration} in 26.2+.
     * @return a new pointed dripstone configuration builder
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static PointedDripstoneConfiguration.Builder pointedDripstone() {
        return PointedDripstoneConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link DripstoneClusterConfiguration}.
     * @return a new dripstone cluster configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DripstoneClusterConfiguration.Builder dripstoneCluster() {
        return DripstoneClusterConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SpeleothemConfiguration}.
     * @return a new pointed dripstone configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpeleothemConfiguration.Builder speleothem() {
        return SpeleothemConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SpeleothemClusterConfiguration}.
     * @return a new speleothem cluster configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpeleothemClusterConfiguration.Builder speleothemCluster() {
        return SpeleothemClusterConfiguration.builder();
    }

    /**
     * Creates a builder for an {@link EndGatewayConfiguration}.
     * @return a new end gateway configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndGatewayConfiguration.Builder endGateway() {
        return EndGatewayConfiguration.builder();
    }

    /**
     * Creates a builder for an {@link EndSpikeConfiguration}.
     * @return a new end spike configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndSpikeConfiguration.Builder endSpike() {
        return EndSpikeConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link FallenTreeConfiguration}.
     * @return a new fallen tree configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FallenTreeConfiguration.Builder fallenTree() {
        return FallenTreeConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link GeodeConfiguration}.
     * @return a new geode configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeConfiguration.Builder geode() {
        return GeodeConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link HugeMushroomFeatureConfiguration}.
     * @return a new huge mushroom feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HugeMushroomFeatureConfiguration.Builder hugeMushroom() {
        return HugeMushroomFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link LargeDripstoneConfiguration}.
     * @return a new large dripstone configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LargeDripstoneConfiguration.Builder largeDripstone() {
        return LargeDripstoneConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link LayerConfiguration}.
     * @return a new layer configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LayerConfiguration.Builder layer() {
        return LayerConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link MultifaceGrowthConfiguration}.
     * @return a new multiface growth configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultifaceGrowthConfiguration.Builder multifaceGrowth() {
        return MultifaceGrowthConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link NetherForestVegetationConfig}.
     * @return a new nether forest vegetation configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NetherForestVegetationConfig.Builder netherForestVegetation() {
        return NetherForestVegetationConfig.builder();
    }

    /**
     * Creates a builder for an {@link OreConfiguration}.
     * @return a new ore configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static OreConfiguration.Builder ore() {
        return OreConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link ProbabilityFeatureConfiguration}.
     * @return a new probability feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ProbabilityFeatureConfiguration probability(float probability) {
        return ProbabilityFeatureConfiguration.of(probability);
    }

    /**
     * Creates a builder for a {@link RandomBooleanFeatureConfiguration}.
     * @return a new random boolean feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBooleanFeatureConfiguration.Builder randomBoolean() {
        return RandomBooleanFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link RandomFeatureConfiguration}.
     * @return a new random feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomFeatureConfiguration.Builder random() {
        return RandomFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link ReplaceBlockConfiguration}.
     * @return a new replace block configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceBlockConfiguration.Builder replaceBlock() {
        return ReplaceBlockConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link ReplaceSphereConfiguration}.
     * @return a new replace sphere configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceSphereConfiguration.Builder replaceSphere() {
        return ReplaceSphereConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link RootSystemConfiguration}.
     * @return a new root system configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RootSystemConfiguration.Builder rootSystem() {
        return RootSystemConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SculkPatchConfiguration}.
     * @return a new sculk patch configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SculkPatchConfiguration.Builder sculkPatch() {
        return SculkPatchConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SimpleBlockConfiguration}.
     * @return a new simple block configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleBlockConfiguration.Builder simpleBlock() {
        return SimpleBlockConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link CompositeFeatureConfiguration}.
     * @return a new simple random feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CompositeFeatureConfiguration.Builder composite() {
        return CompositeFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SpikeConfiguration}.
     * @return a new spike configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpikeConfiguration.Builder spike() {
        return SpikeConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link SpringConfiguration}.
     * @return a new spring configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpringConfiguration.Builder spring() {
        return SpringConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link TreeConfiguration}.
     * @return a new tree configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TreeConfiguration.Builder tree() {
        return TreeConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link TwistingVinesConfig}.
     * @return a new twisting vines configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwistingVinesConfig.Builder twistingVines() {
        return TwistingVinesConfig.builder();
    }

    /**
     * Creates a builder for a {@link LakeFeatureConfiguration}.
     * @return a new lake feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LakeFeatureConfiguration.Builder lake() {
        return LakeFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link TemplateFeatureConfiguration}.
     * @return a new template feature configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static TemplateFeatureConfiguration.Builder templateFeature() {
        return TemplateFeatureConfiguration.builder();
    }

    /**
     * Creates a builder for an {@link UnderwaterMagmaConfiguration}.
     * @return a new underwater magma configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static UnderwaterMagmaConfiguration.Builder underwaterMagma() {
        return UnderwaterMagmaConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link VegetationPatchConfiguration}.
     * @return a new vegetation patch configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static VegetationPatchConfiguration.Builder vegetationPatch() {
        return VegetationPatchConfiguration.builder();
    }

    /**
     * Creates a builder for a {@link WeightedRandomFeatureConfiguration}.
     * @return a new weighted random feature configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static WeightedRandomFeatureConfiguration.Builder weightedRandom() {
        return WeightedRandomFeatureConfiguration.builder();
    }

    /**
     * Reads the configuration owned by a Minecraft configured feature.
     * @param minecraftConfiguredFeature the Minecraft configured feature carrying the configuration
     * @return the wrapper for the configuration
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FeatureConfiguration decode(Object minecraftConfiguredFeature) {
        return DECODER.decode(minecraftConfiguredFeature);
    }
}
