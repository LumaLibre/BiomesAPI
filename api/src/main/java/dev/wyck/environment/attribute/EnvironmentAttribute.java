package dev.wyck.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * Environment attributes control various visual and gameplay features depending on the dimension,
 * biome, time, and weather and can be applied to {@link Biome}s and {@link Dimension}s.
 *
 * @param <V> the type of the environment attribute
 * @see EnvironmentAttributeMap
 * @since 1.1.0
 * @version 3.0.0
 */
@NullMarked
@AsOf("1.1.0")
public interface EnvironmentAttribute<V> extends Wrapper, Keyed {

    /**
     * Gets the key of the environment attribute.
     * @return the key of the environment attribute
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    ResourceKey key();

    /**
     * Gets the value of the environment attribute.
     * @return the value of the environment attribute, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @MonotonicNonNull V value();

    /**
     * Sets the value of the environment attribute.
     * @param value the value to set
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    void value(V value);

    /**
     * Gets the default value of the environment attribute.
     * @return the default value of the environment attribute
     * @throws UnsupportedOperationException if the default value isn't decodable yet :(
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    V defaultValue();

    /**
     * Gets the underlying Minecraft value of the environment attribute.
     * @return the underlying Minecraft value of the environment attribute
     * @param <U> the type of the underlying Minecraft value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    <U> U minecraftValue();

    /**
     * Gets the default Minecraft value of the environment attribute.
     * @return the default Minecraft value of the environment attribute
     * @param <U> the type of the default Minecraft value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    <U> U minecraftDefaultValue();

    /**
     * If the client handles processing of this attribute.
     * @return true if the client handles processing of this attribute, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean syncable();

    /**
     * If the attribute is spatially interpolated.
     * @return true if the attribute is spatially interpolated, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean spatiallyInterpolated();

    /**
     * If the attribute is positional.
     * @return true if the attribute is positional, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean positional();

    /**
     * Gets an EnvironmentAttribute instance for the given key and default value.
     * @param key the key of the environment attribute
     * @param converter the converter for the environment attribute
     * @param defaultValue the default value of the environment attribute
     * @return an EnvironmentAttribute instance for the given key and default value
     * @param <V> the type of the environment attribute
     * @param <U> the underlying type of the environment attribute
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <V, U> EnvironmentAttribute<V> of(ResourceKey key, @Nullable Converter<V, U> converter, @Nullable V defaultValue) {
        record Holder() {
            static final ConstructWireProvider<EnvironmentAttribute<?>> WIRE =
                ConstructWireProvider.create("dev.wyck.environment.attribute.EnvironmentAttributeImpl");
        }
        EnvironmentAttribute<V> attribute = (EnvironmentAttribute<V>) Holder.WIRE.construct(key, converter);
        if (defaultValue != null) {
            attribute.value(defaultValue);
        }
        return attribute;
    }

    /**
     * Gets an EnvironmentAttribute instance for the given key.
     * @param key the key of the environment attribute
     * @param converter the converter for the environment attribute
     * @return an EnvironmentAttribute instance for the given key
     * @param <V> the type of the environment attribute
     * @param <U> the underlying type of the environment attribute
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <V, U> EnvironmentAttribute<V> of(ResourceKey key, @Nullable Converter<V, U> converter) {
        return of(key, converter, null);
    }

    /**
     * Gets an EnvironmentAttribute instance for the given key.
     * @param key the key of the environment attribute
     * @return an EnvironmentAttribute instance for the given key
     * @param <V> the type of the environment attribute
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <V> EnvironmentAttribute<V> of(ResourceKey key) {
        return of(key, null);
    }

    /**
     * Gets an EnvironmentAttributeSupplier instance for the given key and default value.
     * @param key the key of the environment attribute
     * @param converter the converter for the environment attribute
     * @return an EnvironmentAttributeSupplier instance for the given key and default value
     * @param <V> the type of the environment attribute
     * @param <U> the underlying type of the environment attribute
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    static <V, U> EnvironmentAttributeSupplier<V> ofSupplier(ResourceKey key, @Nullable Converter<V, U> converter) {
        return new EnvironmentAttributeSupplier<>(of(key, converter));
    }

    /**
     * Gets an EnvironmentAttributeSupplier instance for the given key.
     * @param key the key of the environment attribute
     * @return an EnvironmentAttributeSupplier instance for the given key
     * @param <V> the type of the environment attribute
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    static <V> EnvironmentAttributeSupplier<V> ofSupplier(ResourceKey key) {
        return new EnvironmentAttributeSupplier<>(of(key));
    }

    /**
     * Gets a FriendlyColorSupplier instance for the given key.
     * @param key the key of the environment attribute
     * @return a FriendlyColorSupplier instance for the given key
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FriendlyColorSupplier ofFriendlyColorSupplier(ResourceKey key) {
        return new FriendlyColorSupplier(of(key));
    }

    /**
     * Reads a Minecraft environment attribute into a wrapper.
     *
     * <p>The value is passed separately because Minecraft keeps it in the map the attribute is an entry
     * of, not on the attribute itself.
     *
     * @param minecraftAttribute the environment attribute to read
     * @param minecraftValue the value that attribute holds
     * @return the wrapper for it, carrying the decoded value
     * @param <V> the type of the environment attribute
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @SuppressWarnings("unchecked")
    static <V> EnvironmentAttribute<V> decode(Object minecraftAttribute, Object minecraftValue) {
        record Holder() {
            static final Decoder<EnvironmentAttribute<?>> DECODER = Decoder.create("dev.wyck.decode.environment.attribute.EnvironmentAttributeDecoders");
        }
        return (EnvironmentAttribute<V>) Holder.DECODER.decode(Map.entry(minecraftAttribute, minecraftValue));
    }

    /**
     * Converts an API value to a Minecraft value.
     * @param <V> the type of the API value
     * @param <U> the type of the Minecraft value
     * @since 1.1.0
     * @version 3.0.0
     */
    @AsOf("1.1.0")
    @FunctionalInterface
    interface Converter<V, U> {
        U convert(V value);
    }
}
