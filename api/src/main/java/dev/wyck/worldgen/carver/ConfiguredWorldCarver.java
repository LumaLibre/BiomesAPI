package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import dev.wyck.worldgen.carver.types.CustomComposedCarver;
import dev.wyck.worldgen.carver.types.ReferencedCarver;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's ConfiguredWorldCarver,
 * a carver paired with its configuration or a reference to a carver already registered.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface ConfiguredWorldCarver extends Wrapper, Keyed {

    /**
     * References a configured carver already registered under the given key.
     * @param key the registry key of the configured carver
     * @return a reference to the registered configured carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ReferencedCarver reference(ResourceKey key) {
        return ReferencedCarver.of(key);
    }

    /**
     * Resolves this carver's key in Minecraft's configured-carver registry and decodes the registered value.
     * @return the decoded configured carver
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default ConfiguredWorldCarver wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.CONFIGURED_CARVER).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Authors a configured carver on the vanilla CAVE algorithm.
     * @return a configured cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder cave() {
        return ComposedCarver.cave();
    }

    /**
     * Authors a configured carver on the vanilla NETHER_CAVE algorithm.
     * @return a configured nether-cave carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder netherCave() {
        return ComposedCarver.netherCave();
    }

    /**
     * Authors a configured carver on the vanilla CANYON algorithm.
     * @return a configured canyon carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ComposedCarver.Builder canyon() {
        return ComposedCarver.canyon();
    }

    /**
     * Composes a registered custom carver with a config instance.
     * @param customCarver the custom carver to compose
     * @param config the config instance to carve with
     * @return an authored configured carver
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedCarver<C> custom(CustomCarver<C> customCarver, C config) {
        return CustomComposedCarver.of(customCarver, config);
    }

    /**
     * Creates a new builder for a custom carver.
     * @return a new custom carver builder
     * @param <C> the config type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> CustomComposedCarver.Builder<C> custom() {
        return CustomComposedCarver.builder();
    }

    /**
     * Reads a keyed Minecraft configured-carver holder into a reference wrapper.
     * @param minecraftConfiguredCarver the configured-carver holder to read
     * @return a reference to the configured carver
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ConfiguredWorldCarver decode(Object minecraftConfiguredCarver) {
        record Holder() {
            static final Decoder<ConfiguredWorldCarver> DECODER = Decoder.create("dev.wyck.decode.worldgen.carver.ConfiguredWorldCarverDecoder");
        }
        return Holder.DECODER.decode(minecraftConfiguredCarver);
    }
}
