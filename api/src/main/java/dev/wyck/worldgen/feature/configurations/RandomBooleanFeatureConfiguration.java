package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A configuration for a random boolean feature.
 *
 * @see <a href="https://minecraft.wiki/w/Random_Boolean_Selector">Random Boolean Selector</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomBooleanFeatureConfiguration extends FeatureConfiguration {

    /**
     * Gets the placed feature to use when the random boolean is true.
     * @return the placed feature to use when the random boolean is true
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    PlacedFeature featureTrue();

    /**
     * Gets the placed feature to use when the random boolean is false.
     * @return the placed feature to use when the random boolean is false
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    PlacedFeature featureFalse();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of this configuration.
     * @param featureTrue the placed feature to use when the random boolean is true
     * @param featureFalse the placed feature to use when the random boolean is false
     * @return a new instance of this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBooleanFeatureConfiguration of(PlacedFeature featureTrue, PlacedFeature featureFalse) {
        record Holder() {
            static final ConstructWireProvider<RandomBooleanFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.RandomBooleanFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(featureTrue, featureFalse);
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
     * Builder for {@link RandomBooleanFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable PlacedFeature featureTrue;
        private @Nullable PlacedFeature featureFalse;

        public Builder() {}

        public Builder(RandomBooleanFeatureConfiguration configuration) {
            this.featureTrue = configuration.featureTrue();
            this.featureFalse = configuration.featureFalse();
        }

        /**
         * Sets the placed feature to use when the random boolean is true.
         * @param featureTrue the placed feature to use when the random boolean is true
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder featureTrue(PlacedFeature featureTrue) {
            this.featureTrue = featureTrue;
            return this;
        }

        /**
         * Sets the placed feature to use when the random boolean is false.
         * @param featureFalse the placed feature to use when the random boolean is false
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder featureFalse(PlacedFeature featureFalse) {
            this.featureFalse = featureFalse;
            return this;
        }

        /**
         * Builds the configuration.
         * @return a new instance of {@link RandomBooleanFeatureConfiguration}
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RandomBooleanFeatureConfiguration build() {
            Preconditions.checkNotNull(featureTrue, "featureTrue must be set");
            Preconditions.checkNotNull(featureFalse, "featureFalse must be set");
            return of(featureTrue, featureFalse);
        }
    }
}