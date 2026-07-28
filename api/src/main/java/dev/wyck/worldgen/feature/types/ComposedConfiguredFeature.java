package dev.wyck.worldgen.feature.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A configured feature composed of a built-in {@link FeatureType} algorithm and a matching
 * {@link FeatureConfiguration}.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature">Configured feature</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ComposedConfiguredFeature extends ConfiguredFeature, Registerable<ComposedConfiguredFeature> {

    /**
     * The built-in feature type algorithm this configured feature composes.
     * @return the feature type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FeatureType type();

    /**
     * The configuration applied to the {@link #type() feature type}.
     * @return the feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FeatureConfiguration config();

    /**
     * Converts this composed configured feature to a builder.
     * @return a builder with the same values as this composed configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new composed configured feature.
     * @param key the resource key of the configured feature, or null if not present
     * @param type the built-in feature type
     * @param config the configuration for the feature type
     * @return a new composed configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedConfiguredFeature of(@Nullable ResourceKey key, FeatureType type, FeatureConfiguration config) {
        record Holder() {
            static final ConstructWireProvider<ComposedConfiguredFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.types.ComposedConfiguredFeatureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(key), type, config);
    }

    /**
     * Creates a new composed configured feature without a resource key.
     * @param type the built-in feature type
     * @param config the configuration for the feature type
     * @return a new composed configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedConfiguredFeature of(FeatureType type, FeatureConfiguration config) {
        return of(null, type, config);
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
     * Builder for {@link ComposedConfiguredFeature}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable FeatureType type;
        private @Nullable FeatureConfiguration config;

        public Builder() {}

        public Builder(FeatureType type) {
            this.type = type;
        }

        public Builder(ComposedConfiguredFeature feature) {
            this.resourceKey = feature.resourceKey().orElse(null);
            this.type = feature.type();
            this.config = feature.config();
        }

        /**
         * Sets the resource key of the configured feature.
         * @param resourceKey the resource key of the configured feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the built-in feature type.
         * @param type the feature type
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder type(FeatureType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the configuration for the feature type.
         * @param config the feature configuration
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder config(FeatureConfiguration config) {
            this.config = config;
            return this;
        }

        /**
         * Builds the composed configured feature.
         * @return the composed configured feature
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ComposedConfiguredFeature build() {
            Preconditions.checkNotNull(type, "type must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(resourceKey, type, config);
        }
    }
}
