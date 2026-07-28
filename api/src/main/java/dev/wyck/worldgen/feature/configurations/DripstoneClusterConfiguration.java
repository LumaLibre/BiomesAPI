package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

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
public interface DripstoneClusterConfiguration extends FeatureConfiguration {

    /**
     * For how many blocks the feature searches for the floor or ceiling. Value between 1 and 512.
     * @return the floor to ceiling search range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int floorToCeilingSearchRange();

    /**
     * The height of the cluster. Value between 1 and 128.
     * @return the cluster height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider height();

    /**
     * The radius of the cluster. Value between 1 and 128.
     * @return the cluster radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider radius();

    /**
     * The maximum height difference between stalagmites and stalactites. Value between 0 and 64.
     * @return the maximum stalagmite to stalactite height difference
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxStalagmiteStalactiteHeightDiff();

    /**
     * The height deviation. Value between 1 and 64.
     * @return the height deviation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int heightDeviation();

    /**
     * The dripstone block layer's thickness. Value between 0 and 128.
     * @return the dripstone block layer thickness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider dripstoneBlockLayerThickness();

    /**
     * The density of the cluster. Value between 0.0 and 2.0.
     * @return the density
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider density();

    /**
     * The wetness of the cluster. Value between 0.0 and 2.0.
     * @return the wetness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FloatProvider wetness();

    /**
     * The chance of a dripstone column generating at the maximum distance from the center. Value between 0.0 and 1.0.
     * @return the chance of a dripstone column at the maximum distance from the center
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfDripstoneColumnAtMaxDistanceFromCenter();

    /**
     * The maximum distance from the edge that affects the chance of a dripstone column. Value between 1 and 64.
     * @return the maximum distance from the edge affecting the chance of a dripstone column
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn();

    /**
     * The maximum distance from the center that affects the height bias. Value between 1 and 64.
     * @return the maximum distance from the center affecting the height bias
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
     * Creates a new dripstone cluster configuration.
     * @param floorToCeilingSearchRange for how many blocks the feature searches for the floor or ceiling
     * @param height the height of the cluster
     * @param radius the radius of the cluster
     * @param maxStalagmiteStalactiteHeightDiff the maximum height difference between stalagmites and stalactites
     * @param heightDeviation the height deviation
     * @param dripstoneBlockLayerThickness the dripstone block layer's thickness
     * @param density the density of the cluster
     * @param wetness the wetness of the cluster
     * @param chanceOfDripstoneColumnAtMaxDistanceFromCenter the chance of a dripstone column at the maximum distance from the center
     * @param maxDistanceFromEdgeAffectingChanceOfDripstoneColumn the maximum distance from the edge affecting the chance of a dripstone column
     * @param maxDistanceFromCenterAffectingHeightBias the maximum distance from the center affecting the height bias
     * @return a new dripstone cluster configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DripstoneClusterConfiguration of(int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider dripstoneBlockLayerThickness, FloatProvider density, FloatProvider wetness, float chanceOfDripstoneColumnAtMaxDistanceFromCenter, int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, int maxDistanceFromCenterAffectingHeightBias) {
        record Holder() {
            static final ConstructWireProvider<DripstoneClusterConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.DripstoneClusterConfigurationImpl");
        }
        return Holder.WIRE.construct(floorToCeilingSearchRange, height, radius, maxStalagmiteStalactiteHeightDiff, heightDeviation, dripstoneBlockLayerThickness, density, wetness, chanceOfDripstoneColumnAtMaxDistanceFromCenter, maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, maxDistanceFromCenterAffectingHeightBias);
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
     * Builder for {@link DripstoneClusterConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int floorToCeilingSearchRange = 1;
        private @Nullable IntProvider height;
        private @Nullable IntProvider radius;
        private int maxStalagmiteStalactiteHeightDiff = 0;
        private int heightDeviation = 1;
        private @Nullable IntProvider dripstoneBlockLayerThickness;
        private @Nullable FloatProvider density;
        private @Nullable FloatProvider wetness;
        private float chanceOfDripstoneColumnAtMaxDistanceFromCenter = 0.0F;
        private int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = 1;
        private int maxDistanceFromCenterAffectingHeightBias = 1;

        public Builder() {}

        public Builder(DripstoneClusterConfiguration configuration) {
            this.floorToCeilingSearchRange = configuration.floorToCeilingSearchRange();
            this.height = configuration.height();
            this.radius = configuration.radius();
            this.maxStalagmiteStalactiteHeightDiff = configuration.maxStalagmiteStalactiteHeightDiff();
            this.heightDeviation = configuration.heightDeviation();
            this.dripstoneBlockLayerThickness = configuration.dripstoneBlockLayerThickness();
            this.density = configuration.density();
            this.wetness = configuration.wetness();
            this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = configuration.chanceOfDripstoneColumnAtMaxDistanceFromCenter();
            this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = configuration.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn();
            this.maxDistanceFromCenterAffectingHeightBias = configuration.maxDistanceFromCenterAffectingHeightBias();
        }

        /**
         * Sets how many blocks the feature searches for the floor or ceiling.
         * @param floorToCeilingSearchRange the floor to ceiling search range
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder floorToCeilingSearchRange(int floorToCeilingSearchRange) {
            this.floorToCeilingSearchRange = floorToCeilingSearchRange;
            return this;
        }

        /**
         * Sets the height of the cluster.
         * @param height the cluster height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the radius of the cluster.
         * @param radius the cluster radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder radius(IntProvider radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Sets the maximum height difference between stalagmites and stalactites.
         * @param maxStalagmiteStalactiteHeightDiff the maximum stalagmite to stalactite height difference
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
         * @param heightDeviation the height deviation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder heightDeviation(int heightDeviation) {
            this.heightDeviation = heightDeviation;
            return this;
        }

        /**
         * Sets the dripstone block layer's thickness.
         * @param dripstoneBlockLayerThickness the dripstone block layer thickness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder dripstoneBlockLayerThickness(IntProvider dripstoneBlockLayerThickness) {
            this.dripstoneBlockLayerThickness = dripstoneBlockLayerThickness;
            return this;
        }

        /**
         * Sets the density of the cluster.
         * @param density the density
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder density(FloatProvider density) {
            this.density = density;
            return this;
        }

        /**
         * Sets the wetness of the cluster.
         * @param wetness the wetness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder wetness(FloatProvider wetness) {
            this.wetness = wetness;
            return this;
        }

        /**
         * Sets the chance of a dripstone column generating at the maximum distance from the center.
         * @param chanceOfDripstoneColumnAtMaxDistanceFromCenter the chance of a dripstone column at the maximum distance from the center
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfDripstoneColumnAtMaxDistanceFromCenter(float chanceOfDripstoneColumnAtMaxDistanceFromCenter) {
            this.chanceOfDripstoneColumnAtMaxDistanceFromCenter = chanceOfDripstoneColumnAtMaxDistanceFromCenter;
            return this;
        }

        /**
         * Sets the maximum distance from the edge that affects the chance of a dripstone column.
         * @param maxDistanceFromEdgeAffectingChanceOfDripstoneColumn the maximum distance from the edge affecting the chance of a dripstone column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxDistanceFromEdgeAffectingChanceOfDripstoneColumn(int maxDistanceFromEdgeAffectingChanceOfDripstoneColumn) {
            this.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn = maxDistanceFromEdgeAffectingChanceOfDripstoneColumn;
            return this;
        }

        /**
         * Sets the maximum distance from the center that affects the height bias.
         * @param maxDistanceFromCenterAffectingHeightBias the maximum distance from the center affecting the height bias
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxDistanceFromCenterAffectingHeightBias(int maxDistanceFromCenterAffectingHeightBias) {
            this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public DripstoneClusterConfiguration build() {
            Preconditions.checkArgument(floorToCeilingSearchRange >= 1 && floorToCeilingSearchRange <= 512, "floorToCeilingSearchRange must be between 1 and 512");
            Preconditions.checkNotNull(height, "height must be set");
            Preconditions.checkArgument(height.minInclusive() >= 1 && height.maxInclusive() <= 128, "height must be between 1 and 128");
            Preconditions.checkNotNull(radius, "radius must be set");
            Preconditions.checkArgument(radius.minInclusive() >= 1 && radius.maxInclusive() <= 128, "radius must be between 1 and 128");
            Preconditions.checkArgument(maxStalagmiteStalactiteHeightDiff >= 0 && maxStalagmiteStalactiteHeightDiff <= 64, "maxStalagmiteStalactiteHeightDiff must be between 0 and 64");
            Preconditions.checkArgument(heightDeviation >= 1 && heightDeviation <= 64, "heightDeviation must be between 1 and 64");
            Preconditions.checkNotNull(dripstoneBlockLayerThickness, "dripstoneBlockLayerThickness must be set");
            Preconditions.checkArgument(dripstoneBlockLayerThickness.minInclusive() >= 0 && dripstoneBlockLayerThickness.maxInclusive() <= 128, "dripstoneBlockLayerThickness must be between 0 and 128");
            Preconditions.checkNotNull(density, "density must be set");
            Preconditions.checkArgument(density.minInclusive() >= 0.0F && density.maxInclusive() <= 2.0F, "density must be between 0.0 and 2.0");
            Preconditions.checkNotNull(wetness, "wetness must be set");
            Preconditions.checkArgument(wetness.minInclusive() >= 0.0F && wetness.maxInclusive() <= 2.0F, "wetness must be between 0.0 and 2.0");
            Preconditions.checkArgument(chanceOfDripstoneColumnAtMaxDistanceFromCenter >= 0.0F && chanceOfDripstoneColumnAtMaxDistanceFromCenter <= 1.0F, "chanceOfDripstoneColumnAtMaxDistanceFromCenter must be between 0.0 and 1.0");
            Preconditions.checkArgument(maxDistanceFromEdgeAffectingChanceOfDripstoneColumn >= 1 && maxDistanceFromEdgeAffectingChanceOfDripstoneColumn <= 64, "maxDistanceFromEdgeAffectingChanceOfDripstoneColumn must be between 1 and 64");
            Preconditions.checkArgument(maxDistanceFromCenterAffectingHeightBias >= 1 && maxDistanceFromCenterAffectingHeightBias <= 64, "maxDistanceFromCenterAffectingHeightBias must be between 1 and 64");
            return of(
                floorToCeilingSearchRange,
                height,
                radius,
                maxStalagmiteStalactiteHeightDiff,
                heightDeviation,
                dripstoneBlockLayerThickness,
                density,
                wetness,
                chanceOfDripstoneColumnAtMaxDistanceFromCenter,
                maxDistanceFromEdgeAffectingChanceOfDripstoneColumn,
                maxDistanceFromCenterAffectingHeightBias
            );
        }
    }
}