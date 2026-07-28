package dev.wyck.worldgen.feature.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A configured feature composed of a user-authored {@link CustomFeature} and a matching configuration
 * instance.
 *
 * @param <C> the configuration type carried to the custom feature
 * @see <a href="https://minecraft.wiki/w/Configured_feature">Configured feature</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CustomComposedConfiguredFeature<C> extends ConfiguredFeature, Registerable<CustomComposedConfiguredFeature<C>> {

    /**
     * The custom feature this configured feature composes.
     * @return the custom feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    CustomFeature<C> feature();

    /**
     * The configuration instance carried to the {@link #feature() custom feature}.
     * @return the configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    C config();

    /**
     * Converts this composed configured feature to a builder.
     * @return a builder with the same values as this composed configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder<C> toBuilder() {
        return new Builder<>(this);
    }

    /**
     * Creates a new custom composed configured feature.
     * @param resourceKey the resource key of the configured feature, or null if not present
     * @param feature the custom feature
     * @param config the configuration instance carried to the custom feature
     * @return a new custom composed configured feature
     * @param <C> the configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <C> CustomComposedConfiguredFeature<C> of(@Nullable ResourceKey resourceKey, CustomFeature<C> feature, C config) {
        record Holder() {
            static final ConstructWireProvider<CustomComposedConfiguredFeature<?>> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.types.CustomComposedConfiguredFeatureImpl");
        }
        return (CustomComposedConfiguredFeature<C>) Holder.WIRE.construct(Optional.ofNullable(resourceKey), feature, config);
    }

    /**
     * Creates a new custom composed configured feature without a resource key.
     * @param feature the custom feature
     * @param config the configuration instance carried to the custom feature
     * @return a new custom composed configured feature
     * @param <C> the configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedConfiguredFeature<C> of(CustomFeature<C> feature, C config) {
        return of(null, feature, config);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @param <C> the configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> Builder<C> builder() {
        return new Builder<>();
    }

    /**
     * Builder for {@link CustomComposedConfiguredFeature}.
     * @param <C> the configuration type
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder<C> {
        private @Nullable ResourceKey resourceKey;
        private @Nullable CustomFeature<C> feature;
        private @Nullable C config;

        public Builder() {}

        public Builder(CustomComposedConfiguredFeature<C> feature) {
            this.resourceKey = feature.resourceKey().orElse(null);
            this.feature = feature.feature();
            this.config = feature.config();
        }

        /**
         * Sets the resource key of the configured feature.
         * @param resourceKey the resource key of the configured feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder<C> resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the custom feature.
         * @param feature the custom feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder<C> feature(CustomFeature<C> feature) {
            this.feature = feature;
            return this;
        }

        /**
         * Sets the configuration instance carried to the custom feature.
         * @param config the configuration instance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder<C> config(C config) {
            this.config = config;
            return this;
        }

        /**
         * Builds the custom composed configured feature.
         * @return the custom composed configured feature
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CustomComposedConfiguredFeature<C> build() {
            Preconditions.checkNotNull(feature, "feature must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(resourceKey, feature, config);
        }
    }
}
