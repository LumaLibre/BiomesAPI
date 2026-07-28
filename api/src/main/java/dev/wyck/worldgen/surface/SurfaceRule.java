package dev.wyck.worldgen.surface;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.surface.condition.AbovePreliminarySurfaceConditionSource;
import dev.wyck.worldgen.surface.condition.BiomeConditionSource;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.worldgen.surface.condition.HoleConditionSource;
import dev.wyck.worldgen.surface.condition.NoiseThresholdConditionSource;
import dev.wyck.worldgen.surface.condition.NotConditionSource;
import dev.wyck.worldgen.surface.condition.OptionallyFlatBedrockConditionSource;
import dev.wyck.worldgen.surface.condition.SteepConditionSource;
import dev.wyck.worldgen.surface.condition.StoneDepthConditionSource;
import dev.wyck.worldgen.surface.condition.TemperatureConditionSource;
import dev.wyck.worldgen.surface.condition.VerticalGradientConditionSource;
import dev.wyck.worldgen.surface.condition.WaterConditionSource;
import dev.wyck.worldgen.surface.condition.YAboveConditionSource;
import dev.wyck.worldgen.surface.rule.BandlandsRuleSource;
import dev.wyck.worldgen.surface.rule.BlockRuleSource;
import dev.wyck.worldgen.surface.rule.ConditionRuleSource;
import dev.wyck.worldgen.surface.rule.RuleSource;
import dev.wyck.worldgen.surface.rule.SequenceRuleSource;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * The shared supertype of the surface-rule family ({@code SurfaceRules}), merging the
 * {@link RuleSource rule sources} that decide which block is placed at the surface and the
 * {@link ConditionSource condition sources} that gate them.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @version 3.0.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface SurfaceRule extends Wrapper {

    /**
     * Matches within a given depth of stone from the floor or ceiling.
     * @param offset the vertical offset
     * @param addSurfaceDepth whether the biome surface depth is added to the offset
     * @param surfaceType the face to measure from
     * @return a stone-depth condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StoneDepthConditionSource stoneDepth(int offset, boolean addSurfaceDepth, CaveSurface surfaceType) {
        return StoneDepthConditionSource.of(offset, addSurfaceDepth, surfaceType);
    }

    /**
     * Matches within a given depth of stone from the floor or ceiling, with a secondary depth range.
     * @param offset the vertical offset
     * @param addSurfaceDepth whether the biome surface depth is added to the offset
     * @param secondaryDepthRange the secondary depth range
     * @param surfaceType the face to measure from
     * @return a stone-depth condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StoneDepthConditionSource stoneDepth(int offset, boolean addSurfaceDepth, int secondaryDepthRange, CaveSurface surfaceType) {
        return StoneDepthConditionSource.of(offset, addSurfaceDepth, secondaryDepthRange, surfaceType);
    }

    /**
     * Creates a new stone-depth condition source builder.
     * @return a new stone-depth condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StoneDepthConditionSource.Builder stoneDepth() {
        return StoneDepthConditionSource.builder();
    }

    /**
     * Inverts another condition.
     * @param target the condition to invert
     * @return a negated condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NotConditionSource not(ConditionSource target) {
        return NotConditionSource.of(target);
    }

    /**
     * Matches relative to the water level at the current block.
     * @param offset the offset from the water level
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a water condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WaterConditionSource waterBlockCheck(int offset, int surfaceDepthMultiplier) {
        return WaterConditionSource.of(offset, surfaceDepthMultiplier, false);
    }

    /**
     * Matches relative to the water level at the column start.
     * @param offset the offset from the water level
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a water condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WaterConditionSource waterStartCheck(int offset, int surfaceDepthMultiplier) {
        return WaterConditionSource.of(offset, surfaceDepthMultiplier, true);
    }

    /**
     * Creates a new water condition source builder.
     * @return a new water condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WaterConditionSource.Builder water() {
        return WaterConditionSource.builder();
    }

    /**
     * Matches when the current biome is one of the given biomes.
     * @param biomes the biomes to match
     * @return a biome condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeConditionSource isBiome(List<Biome> biomes) {
        return BiomeConditionSource.of(biomes);
    }

    /**
     * Matches when the current biome is one of the given biomes.
     * @param biomes the biomes to match
     * @return a biome condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeConditionSource isBiome(Biome... biomes) {
        return BiomeConditionSource.of(biomes);
    }

    /**
     * Creates a new biome condition source builder.
     * @return a new biome condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeConditionSource.Builder isBiome() {
        return BiomeConditionSource.builder();
    }

    /**
     * Matches when a registered noise sampled at this position is at or above the threshold.
     * @param noise the noise parameters key
     * @param minThreshold the lower threshold
     * @return a noise condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdConditionSource noiseThreshold(ResourceKey noise, double minThreshold) {
        return NoiseThresholdConditionSource.of(noise, minThreshold);
    }

    /**
     * Matches when a registered noise sampled at this position is within the threshold range.
     * @param noise the noise parameters key
     * @param minThreshold the lower threshold
     * @param maxThreshold the upper threshold
     * @return a noise condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdConditionSource noiseThreshold(ResourceKey noise, double minThreshold, double maxThreshold) {
        return NoiseThresholdConditionSource.of(noise, minThreshold, maxThreshold);
    }

    /**
     * Creates a new noise threshold condition source builder.
     * @return a new noise threshold condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdConditionSource.Builder noiseThreshold() {
        return NoiseThresholdConditionSource.builder();
    }

    /**
     * Matches at or above a vertical anchor, evaluated per block.
     * @param anchor the vertical anchor
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a y-above condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YAboveConditionSource yBlockCheck(VerticalAnchor anchor, int surfaceDepthMultiplier) {
        return YAboveConditionSource.of(anchor, surfaceDepthMultiplier, false);
    }

    /**
     * Matches at or above a vertical anchor, evaluated at the column start.
     * @param anchor the vertical anchor
     * @param surfaceDepthMultiplier the surface depth multiplier
     * @return a y-above condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YAboveConditionSource yStartCheck(VerticalAnchor anchor, int surfaceDepthMultiplier) {
        return YAboveConditionSource.of(anchor, surfaceDepthMultiplier, true);
    }

    /**
     * Creates a new y-above condition source builder.
     * @return a new y-above condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YAboveConditionSource.Builder yAbove() {
        return YAboveConditionSource.builder();
    }

    /**
     * A noisy vertical gradient that is true below one anchor and false above another, fading between.
     * @param randomName the seed name for the gradient noise
     * @param trueAtAndBelow the anchor at and below which the condition is true
     * @param falseAtAndAbove the anchor at and above which the condition is false
     * @return a vertical-gradient condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static VerticalGradientConditionSource verticalGradient(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove) {
        return VerticalGradientConditionSource.of(randomName, trueAtAndBelow, falseAtAndAbove);
    }

    /**
     * Creates a new vertical-gradient condition source builder.
     * @return a new vertical-gradient condition source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static VerticalGradientConditionSource.Builder verticalGradient() {
        return VerticalGradientConditionSource.builder();
    }

    /**
     * Creates Paper's bedrock gradient, which collapses to one layer when flat bedrock is enabled.
     * @param randomName the seed name for the gradient noise
     * @param trueAtAndBelow the anchor at and below which the condition is true
     * @param falseAtAndAbove the anchor at and above which the condition is false
     * @param roof whether the collapsed flat layer belongs to the dimension roof
     * @return an optionally-flat bedrock condition
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static OptionallyFlatBedrockConditionSource optionallyFlatBedrock(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove, boolean roof) {
        return OptionallyFlatBedrockConditionSource.of(randomName, trueAtAndBelow, falseAtAndAbove, roof);
    }

    /**
     * Creates a new optionally-flat bedrock condition source builder.
     * @return a new optionally-flat bedrock condition source builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static OptionallyFlatBedrockConditionSource.Builder optionallyFlatBedrock() {
        return OptionallyFlatBedrockConditionSource.builder();
    }

    /**
     * Matches on steep terrain.
     * @return a steep condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SteepConditionSource steep() {
        return SteepConditionSource.INSTANCE;
    }

    /**
     * Matches in surface holes.
     * @return a hole condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HoleConditionSource hole() {
        return HoleConditionSource.INSTANCE;
    }

    /**
     * Matches above the preliminary surface level.
     * @return an above-preliminary-surface condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AbovePreliminarySurfaceConditionSource abovePreliminarySurface() {
        return AbovePreliminarySurfaceConditionSource.INSTANCE;
    }

    /**
     * Matches where the biome is cold enough to snow.
     * @return a temperature condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TemperatureConditionSource temperature() {
        return TemperatureConditionSource.INSTANCE;
    }

    // Rules

    /**
     * The badlands banded-terracotta rule.
     * @return the bandlands rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BandlandsRuleSource bandlands() {
        return BandlandsRuleSource.INSTANCE;
    }

    /**
     * Places the given block's default state.
     * @param block the block to place
     * @return a block rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockRuleSource block(Material block) {
        return BlockRuleSource.of(block);
    }

    /**
     * Evaluates rules in order, using the first that applies.
     * @param rules the rules to evaluate in order
     * @return a sequence rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceRuleSource sequence(List<RuleSource> rules) {
        return SequenceRuleSource.of(rules);
    }

    /**
     * Evaluates rules in order, using the first that applies.
     * @param rules the rules to evaluate in order
     * @return a sequence rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceRuleSource sequence(RuleSource... rules) {
        return SequenceRuleSource.of(rules);
    }

    /**
     * Creates a new sequence rule source builder.
     * @return a new sequence rule source builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceRuleSource.Builder sequence() {
        return SequenceRuleSource.builder();
    }

    /**
     * Applies a rule only when a condition holds.
     * @param condition the gating condition
     * @param then the rule to apply when the condition holds
     * @return a conditional rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConditionRuleSource ifTrue(ConditionSource condition, RuleSource then) {
        return ConditionRuleSource.of(condition, then);
    }
}
