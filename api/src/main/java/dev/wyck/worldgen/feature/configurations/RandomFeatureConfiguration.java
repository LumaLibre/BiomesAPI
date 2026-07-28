package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A configuration for a random feature.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomFeatureConfiguration extends FeatureConfiguration {

    /**
     * Gets the list of weighted placed features.
     * @return the list of weighted placed features
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<WeightedPlacedFeature> features();

    /**
     * Gets the default placed feature.
     * @return the default placed feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    PlacedFeature defaultFeature();

    /**
     * Converts this configuration to a builder.
     * @return a new builder with the same values as this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new random feature configuration.
     * @param features the list of weighted placed features
     * @param defaultFeature the default placed feature
     * @return a new random feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomFeatureConfiguration of(List<WeightedPlacedFeature> features, PlacedFeature defaultFeature) {
        record Holder() {
            static final ConstructWireProvider<RandomFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.RandomFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(features, defaultFeature);
    }

    /**
     * Creates a new weighted placed feature.
     * @param feature the placed feature
     * @param chance the chance of the feature being placed (between 0.0 and 1.0)
     * @return a new weighted placed feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedPlacedFeature weighted(PlacedFeature feature, float chance) {
        return new WeightedPlacedFeature(feature, chance);
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
     * A weighted placed feature, which consists of a placed feature and a chance of being placed.
     * @param feature the placed feature
     * @param chance the chance of the feature being placed (between 0.0 and 1.0)
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    record WeightedPlacedFeature(PlacedFeature feature, float chance) {
        public WeightedPlacedFeature {
            Preconditions.checkNotNull(feature, "feature cannot be null");
            Preconditions.checkArgument(chance >= 0.0F && chance <= 1.0F, "chance must be between 0.0 and 1.0");
        }
    }

    /**
     * Builder for {@link RandomFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<WeightedPlacedFeature> features = new ArrayList<>();
        private @Nullable PlacedFeature defaultFeature;

        public Builder() {}

        public Builder(RandomFeatureConfiguration configuration) {
            this.features.addAll(configuration.features());
            this.defaultFeature = configuration.defaultFeature();
        }

        /**
         * Sets the list of weighted placed features.
         * @param features the list of weighted placed features
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder features(List<WeightedPlacedFeature> features) {
            this.features = features;
            return this;
        }

        /**
         * Sets the default placed feature.
         * @param defaultFeature the default placed feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder defaultFeature(PlacedFeature defaultFeature) {
            this.defaultFeature = defaultFeature;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a weighted placed feature to the list.
         * @param feature the placed feature
         * @param chance the chance of the feature being placed (between 0.0 and 1.0)
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder feature(PlacedFeature feature, float chance) {
            this.features.add(new WeightedPlacedFeature(feature, chance));
            return this;
        }

        /**
         * Adds multiple weighted placed features to the list.
         * @param features the weighted placed features to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder feature(WeightedPlacedFeature... features) {
            Collections.addAll(this.features, features);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RandomFeatureConfiguration build() {
            Preconditions.checkNotNull(defaultFeature, "defaultFeature must be set");
            return of(features, defaultFeature);
        }
    }
}