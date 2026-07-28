package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.placement.PlacedFeature;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A technical feature that randomly chooses a feature from a list with each having an equal chance.
 *
 * @see <a href="https://minecraft.wiki/w/Simple_random_selector">Simple random selector</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CompositeFeatureConfiguration extends FeatureConfiguration {

    /**
     * Any number of placed features.
     * This cannot be empty.
     * @return the list of features
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<PlacedFeature> features();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new simple random feature configuration.
     * @param features the list of features
     * @return a new simple random feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CompositeFeatureConfiguration of(List<PlacedFeature> features) {
        record Holder() {
            static final ConstructWireProvider<CompositeFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.CompositeFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(features);
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
     * Builder for {@link CompositeFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<PlacedFeature> features = new ArrayList<>();

        public Builder() {}

        public Builder(CompositeFeatureConfiguration configuration) {
            this.features.addAll(configuration.features());
        }

        /**
         * Sets the list of features.
         * @param features the list of features
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder features(List<PlacedFeature> features) {
            this.features = features;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a feature to the list.
         * @param features the feature to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder feature(PlacedFeature... features) {
            Collections.addAll(this.features, features);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CompositeFeatureConfiguration build() {
            Preconditions.checkArgument(!features.isEmpty(), "features must not be empty");
            return of(features);
        }
    }
}