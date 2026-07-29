package dev.wyck.wrapper.decode;

import dev.wyck.annotations.AsOf;
import dev.wyck.exceptions.MissingDecoderException;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

/**
 * Lazily resolves a decoder implementation while exposing only the Wyck type it produces.
 *
 * @param <T> the type produced by the decoder
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
@ApiStatus.Internal
public final class Decoder<T> {

    private final String implementation;
    private final WireProvider<Decodable<T, ?>> provider;

    private Decoder(String className) {
        this.implementation = className;
        this.provider = WireProvider.create(className);
    }

    /**
     * Creates a lazily resolved decoder provider.
     * @param className the decoder implementation class name
     * @return a provider for the decoder
     * @param <T> the type produced by the decoder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static <T> Decoder<T> create(String className) {
        return new Decoder<>(className);
    }

    /**
     * Decodes a Minecraft object with the lazily resolved decoder.
     * @param minecraftObject the Minecraft object to read
     * @return the decoded Wyck value
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public T decode(Object minecraftObject) {
        return decode(resolved(), minecraftObject);
    }

    /**
     * Gets whether the resolved registry has a decoder for a type key.
     * @param type the decoder type key
     * @return whether the type is handled
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public boolean handles(ResourceKey type) {
        return registry().handles(type);
    }

    /**
     * Gets every type key handled by the resolved decoder registry.
     * @return the handled type keys
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public Set<ResourceKey> handled() {
        return registry().handled();
    }

    /**
     * Gets the decoder type key for a Minecraft object.
     * @param minecraftObject the Minecraft object to inspect
     * @return the object's decoder type key
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public ResourceKey typeOf(Object minecraftObject) {
        return typeOf(registry(), minecraftObject);
    }

    private DecoderRegistry<T, ?> registry() {
        Decodable<T, ?> decoder = resolved();
        if (!(decoder instanceof DecoderRegistry<?, ?> registry)) {
            throw new IllegalStateException("Decoder " + decoder.getClass().getName() + " is not registry-backed");
        }
        return castRegistry(registry);
    }

    private Decodable<T, ?> resolved() {
        try {
            return this.provider.get();
        } catch (IllegalStateException exception) {
            if (causedByMissingClass(exception)) {
                throw new MissingDecoderException(this.implementation, exception);
            }
            throw exception;
        } catch (NoClassDefFoundError error) {
            throw new MissingDecoderException(this.implementation, error);
        }
    }

    private static boolean causedByMissingClass(Throwable throwable) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            if (current instanceof ClassNotFoundException || current instanceof NoClassDefFoundError) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static <T> T decode(Decodable<T, ?> decoder, Object minecraftObject) {
        return ((Decodable<T, Object>) decoder).decode(minecraftObject);
    }

    @SuppressWarnings("unchecked")
    private static <T> ResourceKey typeOf(DecoderRegistry<T, ?> registry, Object minecraftObject) {
        return ((DecoderRegistry<T, Object>) registry).typeOf(minecraftObject);
    }

    @SuppressWarnings("unchecked")
    private static <T> DecoderRegistry<T, ?> castRegistry(DecoderRegistry<?, ?> registry) {
        return (DecoderRegistry<T, ?>) registry;
    }
}
