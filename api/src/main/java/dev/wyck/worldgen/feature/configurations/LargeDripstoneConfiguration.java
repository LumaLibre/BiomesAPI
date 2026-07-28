package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
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
public interface LargeDripstoneConfiguration extends FeatureConfiguration {

    /**
     * Describes which blocks the feature can generate on.
     * @return the set of replaceable blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> replaceableBlocks();

    /**
     * The search range from start point to cave floor or ceiling (rather than from floor to ceiling)
     * between 1 and 512.
     * @return the search range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int floorToCeilingSearchRange();

    /**
     * Used to provide a min and max value for radius between 1 and 16.
     * @return the column radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider columnRadius();

    /**
     * Higher value leads to higher height. Value between 0.0 and 20.0.
     * @return the height scale
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider heightScale();

    /**
     * Higher value leads to higher height between 0.0 and 20.0.
     * @return the max column radius to cave height ratio
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float maxColumnRadiusToCaveHeightRatio();

    /**
     * Truncate the tip of stalactite. Higher value leads to lower height. Value between 0.1 and 10.0
     * @return the stalactite bluntness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider stalactiteBluntness();

    /**
     * Truncate the tip of stalagmites. Higher value leads to lower height. Value between 0.1 and 10.0
     * @return the stalagmite bluntness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider stalagmiteBluntness();

    /**
     * Larger value results in larger inclination. Value between 0.0 and 2.0.
     * @return the wind speed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider windSpeed();

    /**
     * The min column radius to be used for the wind between 0 and 100.
     * @return the min column radius to be used for the wind
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int minRadiusForWind();

    /**
     * The min value of the bluntness to be used for the wind between 0.0 and 5.0.
     * @return the min bluntness to be used for the wind
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float minBluntnessForWind();

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
     * Creates a new large dripstone configuration.
     * @param replaceableBlocks the set of replaceable blocks
     * @param floorToCeilingSearchRange the search range from start point to cave floor or ceiling (rather than from floor to ceiling) between 1 and 512
     * @param columnRadius the column radius
     * @param heightScale the height scale
     * @param maxColumnRadiusToCaveHeightRatio the max column radius to cave height ratio
     * @param stalactiteBluntness the stalactite bluntness
     * @param stalagmiteBluntness the stalagmite bluntness
     * @param windSpeed the wind speed
     * @param minRadiusForWind the min column radius to be used for the wind
     * @param minBluntnessForWind the min bluntness to be used for the wind
     * @return a new large dripstone configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LargeDripstoneConfiguration of(Set<Material> replaceableBlocks, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
        record Holder() {
            static final ConstructWireProvider<LargeDripstoneConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.LargeDripstoneConfigurationImpl");
        }
        return Holder.WIRE.construct(replaceableBlocks, floorToCeilingSearchRange, columnRadius, heightScale, maxColumnRadiusToCaveHeightRatio, stalactiteBluntness, stalagmiteBluntness, windSpeed, minRadiusForWind, minBluntnessForWind);
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
     * Builder for {@link LargeDripstoneConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private Set<Material> replaceableBlocks = new HashSet<>();
        private int floorToCeilingSearchRange = 30;
        private @Nullable IntProvider columnRadius;
        private @Nullable FloatProvider heightScale;
        private float maxColumnRadiusToCaveHeightRatio = 0.1F;
        private @Nullable FloatProvider stalactiteBluntness;
        private @Nullable FloatProvider stalagmiteBluntness;
        private @Nullable FloatProvider windSpeed;
        private int minRadiusForWind = 0;
        private float minBluntnessForWind = 0.0F;

        public Builder() {}

        public Builder(LargeDripstoneConfiguration configuration) {
            this.replaceableBlocks.addAll(configuration.replaceableBlocks());
            this.floorToCeilingSearchRange = configuration.floorToCeilingSearchRange();
            this.columnRadius = configuration.columnRadius();
            this.heightScale = configuration.heightScale();
            this.maxColumnRadiusToCaveHeightRatio = configuration.maxColumnRadiusToCaveHeightRatio();
            this.stalactiteBluntness = configuration.stalactiteBluntness();
            this.stalagmiteBluntness = configuration.stalagmiteBluntness();
            this.windSpeed = configuration.windSpeed();
            this.minRadiusForWind = configuration.minRadiusForWind();
            this.minBluntnessForWind = configuration.minBluntnessForWind();
        }

        /**
         * Sets the replaceable blocks.
         * @param replaceableBlocks the replaceable blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlocks(Set<Material> replaceableBlocks) {
            this.replaceableBlocks = replaceableBlocks;
            return this;
        }

        /**
         * Sets the search range from floor to ceiling.
         * @param floorToCeilingSearchRange the search range
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder floorToCeilingSearchRange(int floorToCeilingSearchRange) {
            this.floorToCeilingSearchRange = floorToCeilingSearchRange;
            return this;
        }

        /**
         * Sets the column radius.
         * @param columnRadius the column radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder columnRadius(IntProvider columnRadius) {
            this.columnRadius = columnRadius;
            return this;
        }

        /**
         * Sets the height scale.
         * @param heightScale the height scale
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder heightScale(FloatProvider heightScale) {
            this.heightScale = heightScale;
            return this;
        }

        /**
         * Sets the max column radius to cave height ratio.
         * @param maxColumnRadiusToCaveHeightRatio the max column radius to cave height ratio
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxColumnRadiusToCaveHeightRatio(float maxColumnRadiusToCaveHeightRatio) {
            this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
            return this;
        }

        /**
         * Sets the stalactite bluntness.
         * @param stalactiteBluntness the stalactite bluntness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stalactiteBluntness(FloatProvider stalactiteBluntness) {
            this.stalactiteBluntness = stalactiteBluntness;
            return this;
        }

        /**
         * Sets the stalagmite bluntness.
         * @param stalagmiteBluntness the stalagmite bluntness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stalagmiteBluntness(FloatProvider stalagmiteBluntness) {
            this.stalagmiteBluntness = stalagmiteBluntness;
            return this;
        }

        /**
         * Sets the wind speed.
         * @param windSpeed the wind speed
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder windSpeed(FloatProvider windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        /**
         * Sets the min column radius to be used for the wind.
         * @param minRadiusForWind the min column radius to be used for the wind
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minRadiusForWind(int minRadiusForWind) {
            this.minRadiusForWind = minRadiusForWind;
            return this;
        }

        /**
         * Sets the min bluntness to be used for the wind.
         * @param minBluntnessForWind the min bluntness to be used for the wind
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minBluntnessForWind(float minBluntnessForWind) {
            this.minBluntnessForWind = minBluntnessForWind;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a replaceable block.
         * @param replaceableBlocks the replaceable blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlock(Material... replaceableBlocks) {
            Collections.addAll(this.replaceableBlocks, replaceableBlocks);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public LargeDripstoneConfiguration build() {
            Preconditions.checkArgument(floorToCeilingSearchRange >= 1 && floorToCeilingSearchRange <= 512, "floorToCeilingSearchRange must be between 1 and 512");
            Preconditions.checkNotNull(columnRadius, "columnRadius must be set");
            Preconditions.checkArgument(columnRadius.minInclusive() >= 1 && columnRadius.maxInclusive() <= 60, "columnRadius must be between 1 and 60");
            Preconditions.checkNotNull(heightScale, "heightScale must be set");
            Preconditions.checkArgument(heightScale.minInclusive() >= 0.0F && heightScale.maxInclusive() <= 20.0F, "heightScale must be between 0.0 and 20.0");
            Preconditions.checkArgument(maxColumnRadiusToCaveHeightRatio >= 0.1F && maxColumnRadiusToCaveHeightRatio <= 1.0F, "maxColumnRadiusToCaveHeightRatio must be between 0.1 and 1.0");
            Preconditions.checkNotNull(stalactiteBluntness, "stalactiteBluntness must be set");
            Preconditions.checkArgument(stalactiteBluntness.minInclusive() >= 0.1F && stalactiteBluntness.maxInclusive() <= 10.0F, "stalactiteBluntness must be between 0.1 and 10.0");
            Preconditions.checkNotNull(stalagmiteBluntness, "stalagmiteBluntness must be set");
            Preconditions.checkArgument(stalagmiteBluntness.minInclusive() >= 0.1F && stalagmiteBluntness.maxInclusive() <= 10.0F, "stalagmiteBluntness must be between 0.1 and 10.0");
            Preconditions.checkNotNull(windSpeed, "windSpeed must be set");
            Preconditions.checkArgument(windSpeed.minInclusive() >= 0.0F && windSpeed.maxInclusive() <= 2.0F, "windSpeed must be between 0.0 and 2.0");
            Preconditions.checkArgument(minRadiusForWind >= 0 && minRadiusForWind <= 100, "minRadiusForWind must be between 0 and 100");
            Preconditions.checkArgument(minBluntnessForWind >= 0.0F && minBluntnessForWind <= 5.0F, "minBluntnessForWind must be between 0.0 and 5.0");
            return of(
                replaceableBlocks,
                floorToCeilingSearchRange,
                columnRadius,
                heightScale,
                maxColumnRadiusToCaveHeightRatio,
                stalactiteBluntness,
                stalagmiteBluntness,
                windSpeed,
                minRadiusForWind,
                minBluntnessForWind
            );
        }
    }
}