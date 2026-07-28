package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

/**
 * Dripstone feature found in caves.
 *
 * @see <a href="https://minecraft.wiki/w/Dripstone_(feature)">Dripstone (feature)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SpeleothemClusterConfiguration extends FeatureConfiguration {

    /**
     * The block that the base of the cluster is made of.
     * @return the base block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData baseBlock();

    /**
     * The block that the pointed tips of the cluster are made of.
     * @return the pointed block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData pointedBlock();

    /**
     * The blocks that the cluster is allowed to replace when generating.
     * @return the replaceable blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> replaceableBlocks();

    /**
     * The vertical distance searched between the floor and ceiling, between 1 and 512 (inclusive).
     * @return the floor to ceiling search range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int floorToCeilingSearchRange();

    /**
     * The height of the cluster, between 1 and 128 (inclusive).
     * @return the height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider height();

    /**
     * The radius of the cluster, between 1 and 128 (inclusive).
     * @return the radius provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider radius();

    /**
     * The maximum allowed height difference between a stalagmite and its opposing stalactite,
     * between 0 and 64 (inclusive).
     * @return the maximum stalagmite/stalactite height difference
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxStalagmiteStalactiteHeightDiff();

    /**
     * The height deviation of the cluster, between 1 and 64 (inclusive).
     * @return the height deviation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int heightDeviation();

    /**
     * The thickness of the speleothem block layer, between 0 and 128 (inclusive).
     * @return the speleothem block layer thickness provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider speleothemBlockLayerThickness();

    /**
     * The density of the cluster, between 0.0 and 2.0 (inclusive).
     * @return the density provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider density();

    /**
     * The wetness of the cluster, between 0.0 and 2.0 (inclusive).
     * @return the wetness provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider wetness();

    /**
     * The chance of a speleothem generating at the maximum distance from the center,
     * between 0.0 and 1.0 (inclusive).
     * @return the chance of a speleothem at the maximum distance from center
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfSpeleothemAtMaxDistanceFromCenter();

    /**
     * The maximum distance from the edge that affects the chance of a speleothem,
     * between 1 and 64 (inclusive).
     * @return the maximum distance from edge affecting speleothem chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxDistanceFromEdgeAffectingChanceOfSpeleothem();

    /**
     * The maximum distance from the center that affects the height bias, between 1 and 64 (inclusive).
     * @return the maximum distance from center affecting height bias
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxDistanceFromCenterAffectingHeightBias();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SpeleothemClusterConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData baseBlock;
        private @Nullable BlockData pointedBlock;
        private @Nullable Set<Material> replaceableBlocks;
        private int floorToCeilingSearchRange;
        private @Nullable IntProvider height;
        private @Nullable IntProvider radius;
        private int maxStalagmiteStalactiteHeightDiff;
        private int heightDeviation;
        private @Nullable IntProvider speleothemBlockLayerThickness;
        private @Nullable FloatProvider density;
        private @Nullable FloatProvider wetness;
        private float chanceOfSpeleothemAtMaxDistanceFromCenter;
        private int maxDistanceFromEdgeAffectingChanceOfSpeleothem;
        private int maxDistanceFromCenterAffectingHeightBias;

        public Builder() {}

        public Builder(SpeleothemClusterConfiguration configuration) {
            this.baseBlock = configuration.baseBlock();
            this.pointedBlock = configuration.pointedBlock();
            this.replaceableBlocks = configuration.replaceableBlocks();
            this.floorToCeilingSearchRange = configuration.floorToCeilingSearchRange();
            this.height = configuration.height();
            this.radius = configuration.radius();
            this.maxStalagmiteStalactiteHeightDiff = configuration.maxStalagmiteStalactiteHeightDiff();
            this.heightDeviation = configuration.heightDeviation();
            this.speleothemBlockLayerThickness = configuration.speleothemBlockLayerThickness();
            this.density = configuration.density();
            this.wetness = configuration.wetness();
            this.chanceOfSpeleothemAtMaxDistanceFromCenter = configuration.chanceOfSpeleothemAtMaxDistanceFromCenter();
            this.maxDistanceFromEdgeAffectingChanceOfSpeleothem = configuration.maxDistanceFromEdgeAffectingChanceOfSpeleothem();
            this.maxDistanceFromCenterAffectingHeightBias = configuration.maxDistanceFromCenterAffectingHeightBias();
        }

        /**
         * Sets the base block.
         * @param baseBlock the block that the base of the cluster is made of
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder baseBlock(BlockData baseBlock) {
            this.baseBlock = baseBlock;
            return this;
        }

        /**
         * Sets the pointed block.
         * @param pointedBlock the block that the pointed tips of the cluster are made of
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder pointedBlock(BlockData pointedBlock) {
            this.pointedBlock = pointedBlock;
            return this;
        }

        /**
         * Sets the replaceable blocks.
         * @param replaceableBlocks the blocks that the cluster is allowed to replace
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlocks(Set<Material> replaceableBlocks) {
            this.replaceableBlocks = replaceableBlocks;
            return this;
        }

        /**
         * Sets the replaceable blocks.
         * @param replaceableBlocks the blocks that the cluster is allowed to replace
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlocks(Material... replaceableBlocks) {
            this.replaceableBlocks = Set.of(replaceableBlocks);
            return this;
        }

        /**
         * Sets the floor to ceiling search range.
         * @param floorToCeilingSearchRange the vertical distance searched between floor and ceiling
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder floorToCeilingSearchRange(int floorToCeilingSearchRange) {
            this.floorToCeilingSearchRange = floorToCeilingSearchRange;
            return this;
        }

        /**
         * Sets the height provider.
         * @param height the height of the cluster
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the radius provider.
         * @param radius the radius of the cluster
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder radius(IntProvider radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Sets the maximum stalagmite/stalactite height difference.
         * @param maxStalagmiteStalactiteHeightDiff the maximum allowed height difference
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxStalagmiteStalactiteHeightDiff(int maxStalagmiteStalactiteHeightDiff) {
            this.maxStalagmiteStalactiteHeightDiff = maxStalagmiteStalactiteHeightDiff;
            return this;
        }

        /**
         * Sets the height deviation.
         * @param heightDeviation the height deviation of the cluster
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder heightDeviation(int heightDeviation) {
            this.heightDeviation = heightDeviation;
            return this;
        }

        /**
         * Sets the speleothem block layer thickness provider.
         * @param speleothemBlockLayerThickness the thickness of the speleothem block layer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder speleothemBlockLayerThickness(IntProvider speleothemBlockLayerThickness) {
            this.speleothemBlockLayerThickness = speleothemBlockLayerThickness;
            return this;
        }

        /**
         * Sets the density provider.
         * @param density the density of the cluster
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder density(FloatProvider density) {
            this.density = density;
            return this;
        }

        /**
         * Sets the wetness provider.
         * @param wetness the wetness of the cluster
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder wetness(FloatProvider wetness) {
            this.wetness = wetness;
            return this;
        }

        /**
         * Sets the chance of a speleothem at the maximum distance from center.
         * @param chanceOfSpeleothemAtMaxDistanceFromCenter the chance at the maximum distance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfSpeleothemAtMaxDistanceFromCenter(float chanceOfSpeleothemAtMaxDistanceFromCenter) {
            this.chanceOfSpeleothemAtMaxDistanceFromCenter = chanceOfSpeleothemAtMaxDistanceFromCenter;
            return this;
        }

        /**
         * Sets the maximum distance from edge affecting speleothem chance.
         * @param maxDistanceFromEdgeAffectingChanceOfSpeleothem the maximum distance from edge
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxDistanceFromEdgeAffectingChanceOfSpeleothem(int maxDistanceFromEdgeAffectingChanceOfSpeleothem) {
            this.maxDistanceFromEdgeAffectingChanceOfSpeleothem = maxDistanceFromEdgeAffectingChanceOfSpeleothem;
            return this;
        }

        /**
         * Sets the maximum distance from center affecting height bias.
         * @param maxDistanceFromCenterAffectingHeightBias the maximum distance from center
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxDistanceFromCenterAffectingHeightBias(int maxDistanceFromCenterAffectingHeightBias) {
            this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
            return this;
        }

        /**
         * Builds the speleothem cluster configuration.
         * @return the speleothem cluster configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SpeleothemClusterConfiguration build() {
            Preconditions.checkNotNull(baseBlock, "baseBlock must be set");
            Preconditions.checkNotNull(pointedBlock, "pointedBlock must be set");
            Preconditions.checkNotNull(replaceableBlocks, "replaceableBlocks must be set");
            Preconditions.checkNotNull(height, "height must be set");
            Preconditions.checkNotNull(radius, "radius must be set");
            Preconditions.checkNotNull(speleothemBlockLayerThickness, "speleothemBlockLayerThickness must be set");
            Preconditions.checkNotNull(density, "density must be set");
            Preconditions.checkNotNull(wetness, "wetness must be set");
            Preconditions.checkArgument(floorToCeilingSearchRange >= 1 && floorToCeilingSearchRange <= 512, "floorToCeilingSearchRange must be between 1 and 512");
            Preconditions.checkArgument(maxStalagmiteStalactiteHeightDiff >= 0 && maxStalagmiteStalactiteHeightDiff <= 64, "maxStalagmiteStalactiteHeightDiff must be between 0 and 64");
            Preconditions.checkArgument(heightDeviation >= 1 && heightDeviation <= 64, "heightDeviation must be between 1 and 64");
            Preconditions.checkArgument(chanceOfSpeleothemAtMaxDistanceFromCenter >= 0.0F && chanceOfSpeleothemAtMaxDistanceFromCenter <= 1.0F, "chanceOfSpeleothemAtMaxDistanceFromCenter must be between 0.0 and 1.0");
            Preconditions.checkArgument(maxDistanceFromEdgeAffectingChanceOfSpeleothem >= 1 && maxDistanceFromEdgeAffectingChanceOfSpeleothem <= 64, "maxDistanceFromEdgeAffectingChanceOfSpeleothem must be between 1 and 64");
            Preconditions.checkArgument(maxDistanceFromCenterAffectingHeightBias >= 1 && maxDistanceFromCenterAffectingHeightBias <= 64, "maxDistanceFromCenterAffectingHeightBias must be between 1 and 64");

            record Holder() {
                static final ConstructWireProvider<SpeleothemClusterConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.SpeleothemClusterConfigurationImpl");
            }
            return Holder.WIRE.construct(
                baseBlock,
                pointedBlock,
                replaceableBlocks,
                floorToCeilingSearchRange,
                height,
                radius,
                maxStalagmiteStalactiteHeightDiff,
                heightDeviation,
                speleothemBlockLayerThickness,
                density,
                wetness,
                chanceOfSpeleothemAtMaxDistanceFromCenter,
                maxDistanceFromEdgeAffectingChanceOfSpeleothem,
                maxDistanceFromCenterAffectingHeightBias
            );
        }
    }
}