package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettings;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A geode feature, such as an amethyst geode, composed of concentric layers of blocks
 * surrounding a hollow, optionally cracked, interior.
 *
 * @see <a href="https://minecraft.wiki/w/Amethyst_Geode">Amethyst Geode</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface GeodeConfiguration extends FeatureConfiguration {

    /**
     * The block state providers used for each of the geode's layers and its inner placements.
     * @return the geode block settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    GeodeBlockSettings geodeBlockSettings();

    /**
     * The thickness of each of the geode's layers.
     * @return the geode layer settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    GeodeLayerSettings geodeLayerSettings();

    /**
     * The configuration of the crack on the geode.
     * @return the geode crack settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    GeodeCrackSettings geodeCrackSettings();

    /**
     * The probability for placing an inner placement on a block of the inner layer. Value between 0.0 and 1.0.
     * @return the chance to use a potential inner placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double usePotentialPlacementsChance();

    /**
     * The chance for a given inner layer block to be replaced with an alternate inner layer block. Value between 0.0 and 1.0.
     * @return the chance to use the alternate inner layer block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double useAlternateLayer0Chance();

    /**
     * Whether the inner placement blocks can only be placed on an alternate inner layer block.
     * @return whether placements require an alternate inner layer block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean placementsRequireLayer0Alternate();

    /**
     * The offset on each coordinate of the center from the feature start. Value between 1 and 20.
     * @return the outer wall distance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider outerWallDistance();

    /**
     * The number of distribution points used to shape the geode. Value between 1 and 20.
     * @return the distribution points
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider distributionPoints();

    /**
     * The offset applied to each distribution point. Value between 0 and 10.
     * @return the point offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider pointOffset();

    /**
     * The minimum Chebyshev distance between a block and the center of the geode.
     * @return the minimum generation offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int minGenOffset();

    /**
     * The maximum Chebyshev distance between a block and the center of the geode.
     * @return the maximum generation offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxGenOffset();

    /**
     * The multiplier applied to the noise shaping the geode. Value between 0.0 and 1.0.
     * @return the noise multiplier
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double noiseMultiplier();

    /**
     * The invalid block threshold. Checked {@link #distributionPoints()} times near the center of the geode;
     * if the number of invalid blocks found exceeds this number, the feature will not be generated.
     * @return the invalid blocks threshold
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int invalidBlocksThreshold();

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
     * Creates a new geode configuration.
     * @param geodeBlockSettings the block state providers for the geode's layers and inner placements
     * @param geodeLayerSettings the thickness of each of the geode's layers
     * @param geodeCrackSettings the configuration of the crack on the geode
     * @param usePotentialPlacementsChance the probability for placing an inner placement on a block of the inner layer
     * @param useAlternateLayer0Chance the chance for a given inner layer block to be replaced with an alternate inner layer block
     * @param placementsRequireLayer0Alternate whether the inner placement blocks can only be placed on an alternate inner layer block
     * @param outerWallDistance the offset on each coordinate of the center from the feature start
     * @param distributionPoints the number of distribution points used to shape the geode
     * @param pointOffset the offset applied to each distribution point
     * @param minGenOffset the minimum Chebyshev distance between a block and the center
     * @param maxGenOffset the maximum Chebyshev distance between a block and the center
     * @param noiseMultiplier the multiplier applied to the noise shaping the geode
     * @param invalidBlocksThreshold the invalid blocks threshold
     * @return a new geode configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeConfiguration of(
        GeodeBlockSettings geodeBlockSettings,
        GeodeLayerSettings geodeLayerSettings,
        GeodeCrackSettings geodeCrackSettings,
        double usePotentialPlacementsChance,
        double useAlternateLayer0Chance,
        boolean placementsRequireLayer0Alternate,
        IntProvider outerWallDistance,
        IntProvider distributionPoints,
        IntProvider pointOffset,
        int minGenOffset,
        int maxGenOffset,
        double noiseMultiplier,
        int invalidBlocksThreshold
    ) {
        record Holder() {
            static final ConstructWireProvider<GeodeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.GeodeConfigurationImpl");
        }
        return Holder.WIRE.construct(geodeBlockSettings, geodeLayerSettings, geodeCrackSettings, usePotentialPlacementsChance, useAlternateLayer0Chance, placementsRequireLayer0Alternate, outerWallDistance, distributionPoints, pointOffset, minGenOffset, maxGenOffset, noiseMultiplier, invalidBlocksThreshold);
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
     * Builder for {@link GeodeConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable GeodeBlockSettings geodeBlockSettings;
        private @Nullable GeodeLayerSettings geodeLayerSettings;
        private @Nullable GeodeCrackSettings geodeCrackSettings;
        private double usePotentialPlacementsChance = 0.35;
        private double useAlternateLayer0Chance = 0.0;
        private boolean placementsRequireLayer0Alternate = true;
        private @Nullable IntProvider outerWallDistance;
        private @Nullable IntProvider distributionPoints;
        private @Nullable IntProvider pointOffset;
        private int minGenOffset = -16;
        private int maxGenOffset = 16;
        private double noiseMultiplier = 0.05;
        private int invalidBlocksThreshold = 0;

        public Builder() {}

        public Builder(GeodeConfiguration configuration) {
            this.geodeBlockSettings = configuration.geodeBlockSettings();
            this.geodeLayerSettings = configuration.geodeLayerSettings();
            this.geodeCrackSettings = configuration.geodeCrackSettings();
            this.usePotentialPlacementsChance = configuration.usePotentialPlacementsChance();
            this.useAlternateLayer0Chance = configuration.useAlternateLayer0Chance();
            this.placementsRequireLayer0Alternate = configuration.placementsRequireLayer0Alternate();
            this.outerWallDistance = configuration.outerWallDistance();
            this.distributionPoints = configuration.distributionPoints();
            this.pointOffset = configuration.pointOffset();
            this.minGenOffset = configuration.minGenOffset();
            this.maxGenOffset = configuration.maxGenOffset();
            this.noiseMultiplier = configuration.noiseMultiplier();
            this.invalidBlocksThreshold = configuration.invalidBlocksThreshold();
        }

        /**
         * Sets the block state providers for the geode's layers and inner placements.
         * @param geodeBlockSettings the geode block settings
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder geodeBlockSettings(GeodeBlockSettings geodeBlockSettings) {
            this.geodeBlockSettings = geodeBlockSettings;
            return this;
        }

        /**
         * Sets the thickness of each of the geode's layers.
         * @param geodeLayerSettings the geode layer settings
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder geodeLayerSettings(GeodeLayerSettings geodeLayerSettings) {
            this.geodeLayerSettings = geodeLayerSettings;
            return this;
        }

        /**
         * Sets the configuration of the crack on the geode.
         * @param geodeCrackSettings the geode crack settings
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder geodeCrackSettings(GeodeCrackSettings geodeCrackSettings) {
            this.geodeCrackSettings = geodeCrackSettings;
            return this;
        }

        /**
         * Sets the probability for placing an inner placement on a block of the inner layer.
         * @param usePotentialPlacementsChance the chance to use a potential inner placement
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder usePotentialPlacementsChance(double usePotentialPlacementsChance) {
            this.usePotentialPlacementsChance = usePotentialPlacementsChance;
            return this;
        }

        /**
         * Sets the chance for a given inner layer block to be replaced with an alternate inner layer block.
         * @param useAlternateLayer0Chance the chance to use the alternate inner layer block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder useAlternateLayer0Chance(double useAlternateLayer0Chance) {
            this.useAlternateLayer0Chance = useAlternateLayer0Chance;
            return this;
        }

        /**
         * Sets whether the inner placement blocks can only be placed on an alternate inner layer block.
         * @param placementsRequireLayer0Alternate whether placements require an alternate inner layer block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder placementsRequireLayer0Alternate(boolean placementsRequireLayer0Alternate) {
            this.placementsRequireLayer0Alternate = placementsRequireLayer0Alternate;
            return this;
        }

        /**
         * Sets the offset on each coordinate of the center from the feature start.
         * @param outerWallDistance the outer wall distance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder outerWallDistance(IntProvider outerWallDistance) {
            this.outerWallDistance = outerWallDistance;
            return this;
        }

        /**
         * Sets the number of distribution points used to shape the geode.
         * @param distributionPoints the distribution points
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder distributionPoints(IntProvider distributionPoints) {
            this.distributionPoints = distributionPoints;
            return this;
        }

        /**
         * Sets the offset applied to each distribution point.
         * @param pointOffset the point offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder pointOffset(IntProvider pointOffset) {
            this.pointOffset = pointOffset;
            return this;
        }

        /**
         * Sets the minimum Chebyshev distance between a block and the center of the geode.
         * @param minGenOffset the minimum generation offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minGenOffset(int minGenOffset) {
            this.minGenOffset = minGenOffset;
            return this;
        }

        /**
         * Sets the maximum Chebyshev distance between a block and the center of the geode.
         * @param maxGenOffset the maximum generation offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxGenOffset(int maxGenOffset) {
            this.maxGenOffset = maxGenOffset;
            return this;
        }

        /**
         * Sets the multiplier applied to the noise shaping the geode.
         * @param noiseMultiplier the noise multiplier
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseMultiplier(double noiseMultiplier) {
            this.noiseMultiplier = noiseMultiplier;
            return this;
        }

        /**
         * Sets the invalid blocks threshold above which the geode will not be generated.
         * @param invalidBlocksThreshold the invalid blocks threshold
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder invalidBlocksThreshold(int invalidBlocksThreshold) {
            this.invalidBlocksThreshold = invalidBlocksThreshold;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public GeodeConfiguration build() {
            Preconditions.checkNotNull(geodeBlockSettings, "geodeBlockSettings must be set");
            Preconditions.checkNotNull(geodeLayerSettings, "geodeLayerSettings must be set");
            Preconditions.checkNotNull(geodeCrackSettings, "geodeCrackSettings must be set");
            Preconditions.checkArgument(usePotentialPlacementsChance >= 0.0 && usePotentialPlacementsChance <= 1.0, "usePotentialPlacementsChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(useAlternateLayer0Chance >= 0.0 && useAlternateLayer0Chance <= 1.0, "useAlternateLayer0Chance must be between 0.0 and 1.0");
            Preconditions.checkNotNull(outerWallDistance, "outerWallDistance must be set");
            Preconditions.checkArgument(outerWallDistance.minInclusive() >= 1 && outerWallDistance.maxInclusive() <= 20, "outerWallDistance must be between 1 and 20");
            Preconditions.checkNotNull(distributionPoints, "distributionPoints must be set");
            Preconditions.checkArgument(distributionPoints.minInclusive() >= 1 && distributionPoints.maxInclusive() <= 20, "distributionPoints must be between 1 and 20");
            Preconditions.checkNotNull(pointOffset, "pointOffset must be set");
            Preconditions.checkArgument(pointOffset.minInclusive() >= 0 && pointOffset.maxInclusive() <= 10, "pointOffset must be between 0 and 10");
            Preconditions.checkArgument(noiseMultiplier >= 0.0 && noiseMultiplier <= 1.0, "noiseMultiplier must be between 0.0 and 1.0");
            return of(
                geodeBlockSettings,
                geodeLayerSettings,
                geodeCrackSettings,
                usePotentialPlacementsChance,
                useAlternateLayer0Chance,
                placementsRequireLayer0Alternate,
                outerWallDistance,
                distributionPoints,
                pointOffset,
                minGenOffset,
                maxGenOffset,
                noiseMultiplier,
                invalidBlocksThreshold
            );
        }
    }
}