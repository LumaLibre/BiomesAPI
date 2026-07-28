package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jspecify.annotations.NullMarked;

/**
 * A technical feature that chooses a single feature from a weighted list, where a higher weight makes a
 * feature more likely to be chosen.
 *
 * @see CompositeFeatureConfiguration for an equal-chance variant.
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface WeightedRandomFeatureConfiguration extends FeatureConfiguration {

    /**
     * The weighted list of features to choose from.
     * @return the weighted features
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    WeightedList<PlacedFeature> features();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new weighted random feature configuration.
     * @param features the weighted list of features to choose from
     * @return a new weighted random feature configuration
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static WeightedRandomFeatureConfiguration of(WeightedList<PlacedFeature> features) {
        record Holder() {
            static final ConstructWireProvider<WeightedRandomFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.WeightedRandomFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(features);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link WeightedRandomFeatureConfiguration}.
     * @since 3.0.1
     * @version 3.0.1
     * @author Jsinco
     */
    @AsOf("3.0.1")
    final class Builder {
        private final WeightedList.Builder<PlacedFeature> features = WeightedList.builder();

        public Builder() {}

        public Builder(WeightedRandomFeatureConfiguration configuration) {
            for (WeightedList.Weighted<PlacedFeature> entry : configuration.features().unwrap()) {
                this.features.add(entry.value(), entry.weight());
            }
        }

        /**
         * Adds a feature with a weight of 1.
         * @param feature the feature to add
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder feature(PlacedFeature feature) {
            this.features.add(feature);
            return this;
        }

        /**
         * Adds a feature with the given weight.
         * @param feature the feature to add
         * @param weight the weight of the feature
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder feature(PlacedFeature feature, int weight) {
            this.features.add(feature, weight);
            return this;
        }

        /**
         * Builds the weighted random feature configuration.
         * @return the weighted random feature configuration
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public WeightedRandomFeatureConfiguration build() {
            WeightedList<PlacedFeature> built = features.build();
            Preconditions.checkArgument(!built.isEmpty(), "features must not be empty");
            return of(built);
        }
    }
}
