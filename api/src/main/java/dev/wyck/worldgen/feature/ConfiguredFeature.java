package dev.wyck.worldgen.feature;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.worldgen.feature.types.ComposedConfiguredFeature;
import dev.wyck.worldgen.feature.types.CustomComposedConfiguredFeature;
import dev.wyck.worldgen.feature.types.ReferencedConfiguredFeature;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A configured feature is a feature that has been configured with a specific configuration.
 * Configured features are used to generate features in the world and are tied to biomes.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature">Configured feature</a>
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface ConfiguredFeature extends Wrapper, Keyed {

    @ApiStatus.Internal
    Decoder<ConfiguredFeature> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.ConfiguredFeatureDecoder");

    /**
     * The resource key of the configured feature, if present.
     * @return the resource key of the configured feature, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Authors a configured feature from a vanilla feature type and configuration.
     * @param featureType the vanilla feature type
     * @param config the configuration
     * @return an authored configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    @ApiStatus.Obsolete
    static ComposedConfiguredFeature of(FeatureType featureType, FeatureConfiguration config) {
        return ComposedConfiguredFeature.of(featureType, config);
    }

    /**
     * Composes a custom feature with a config instance.
     * @param feature the custom feature to compose
     * @param config the config instance to place with
     * @return an authored configured feature
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedConfiguredFeature<C> custom(CustomFeature<C> feature, C config) {
        return CustomComposedConfiguredFeature.of(feature, config);
    }

    /**
     * Composes a registered custom feature with a config instance.
     * @param feature the custom feature to compose
     * @param config the config instance to place with
     * @return an authored configured feature
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Obsolete
    static <C> CustomComposedConfiguredFeature<C> of(CustomFeature<C> feature, C config) {
        return CustomComposedConfiguredFeature.of(feature, config);
    }

    /**
     * References a configured feature already registered under the given key.
     * @param key the registry key of the configured feature
     * @return a reference to the registered configured feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ReferencedConfiguredFeature reference(ResourceKey key) {
        return ReferencedConfiguredFeature.of(key);
    }

    /**
     * Resolves this object's key in Minecraft's configured-feature registry and decodes the registered value.
     * @return the decoded configured feature
     * @throws IllegalStateException if this object has no resource key
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default ConfiguredFeature wrap() {
        ResourceKey key = resourceKey().orElseThrow(() -> new IllegalStateException("Cannot wrap a configured feature without a resource key"));
        Object minecraft = WyckRegistry.of(RegistryId.CONFIGURED_FEATURE).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Authors a configured feature from a vanilla feature type and configuration.
     * @return an authored configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedConfiguredFeature.Builder composed() {
        return ComposedConfiguredFeature.builder();
    }

    /**
     * Composes a registered custom feature with a config instance. The feature
     * @return an authored configured feature
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedConfiguredFeature.Builder<C> custom() {
        return CustomComposedConfiguredFeature.builder();
    }

    /**
     * Reads a keyed Minecraft configured-feature holder into a wrapper.
     * @param minecraftFeature the configured-feature holder to read
     * @return the wrapper for the configured feature
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ConfiguredFeature decode(Object minecraftFeature) {
        return DECODER.decode(minecraftFeature);
    }

}
