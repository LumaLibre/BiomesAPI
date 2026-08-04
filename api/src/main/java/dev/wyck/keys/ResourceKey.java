package dev.wyck.keys;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;

/**
 * This interface represents a key for a biome resource in the game.
 * It uses the @AsOf annotation to indicate the version since the interface or its methods have been present or modified.
 *
 * @version 2.3.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("2.3.0")
public interface ResourceKey extends Key, Keyed, Wrapper {

    String MINECRAFT_NAMESPACE = "minecraft";
    String WYCK_NAMESPACE = "wyck";
    char NAMESPACE_SEPARATOR = ':';

    /**
     * The namespace portion of this key (e.g. "minecraft", "wyck").
     */
    @AsOf("0.0.15")
    @KeyPattern.Namespace
    String namespace();

    /**
     * The path portion of this key.
     */
    @AsOf("0.0.15")
    @KeyPattern.Value
    String path();

    /**
     * Underlying Minecraft Identifier.
     * @return the underlying Minecraft Identifier
     * @since 2.0.0
     */
    @AsOf("2.0.0")
    Object resourceLocation();

    /**
     * The underlying Minecraft Identifier.
     * @return the underlying Minecraft Identifier
     * @param <T> the type of the underlying Minecraft Identifier
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    <T> T identifier();

    /**
     * Returns the path portion of this key.
     * @return the path portion of this key
     * @since 2.2.1
     */
    @Override
    @AsOf("2.2.1")
    @KeyPattern.Value
    default String value() {
        return path();
    }

    /**
     * Returns the string representation of this key in the format "namespace:path".
     * @return the string representation of this key
     * @since 2.2.1
     */
    @Override
    @AsOf("2.2.1")
    default String asString() {
        return namespace() + NAMESPACE_SEPARATOR + path();
    }

    /**
     * Returns a {@link Key} object representing this ResourceKey.
     * @return a {@link Key} object representing this ResourceKey
     * @since 0.0.6
     */
    @Override
    @AsOf("0.0.6")
    default Key key() {
        return Key.key(namespace(), path());
    }

    /**
     * Returns the underlying Minecraft object that this ResourceKey wraps.
     * @return the underlying Minecraft object that this ResourceKey wraps
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return resourceLocation();
    }

    /**
     * Creates a new ResourceKey from the given namespace and path.
     *
     * @param namespace the namespace for the resource location
     * @param path the path for the resource location
     * @return a new ResourceKey with the given namespace and path
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    static ResourceKey of(String namespace, String path) {
        Preconditions.checkArgument(!namespace.isEmpty(), "namespace cannot be empty");
        Preconditions.checkArgument(!path.isEmpty(), "path cannot be empty");
        record Holder() {
            static final ConstructWireProvider<ResourceKey> WIRE = ConstructWireProvider.create("dev.wyck.keys.ResourceKeyImpl");
        }
        return Holder.WIRE.construct(namespace.toLowerCase(), path.toLowerCase());
    }

    /**
     * Creates a new ResourceKey from the given string.
     * @param string the string to create the ResourceKey from
     * @return a new ResourceKey with the given string
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ResourceKey of(String string) {
        return fromString(string);
    }

    /**
     * Creates a new ResourceKey from the given Key.
     * @param key  the Key to create the ResourceKey from
     * @return a new ResourceKey with the given Key
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ResourceKey of(Key key) {
        return of(key.namespace(), key.value());
    }

    /**
     * Creates a new ResourceKey in the "wyck" namespace with the given path.
     * @param path the path for the resource location
     * @return a new ResourceKey with the "wyck" namespace and the given path
     */
    @AsOf("0.0.8")
    static ResourceKey wyck(String path) {
        return of(WYCK_NAMESPACE, path);
    }

    /**
     * Creates a new ResourceKey in the "minecraft" namespace with the given path.
     * @param path the path for the resource location
     * @return a new ResourceKey with the "minecraft" namespace and the given path
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ResourceKey minecraft(String path) {
        return of(MINECRAFT_NAMESPACE, path);
    }

    /**
     * Creates a new ResourceKey from the given string representation.
     * The string should be in the format "namespace:path".
     *
     * @param keyString the string representation of the ResourceKey
     * @return a new ResourceKey with the given string representation
     * @since 0.0.15
     */
    @AsOf("0.0.15")
    static ResourceKey fromString(String keyString) {
        Preconditions.checkArgument(!keyString.isEmpty(), "keyString cannot be empty");
        String[] parts = keyString.split(String.valueOf(NAMESPACE_SEPARATOR), 2);
        Preconditions.checkArgument(parts.length == 2, "keyString must be in the format 'namespace:path'");
        return of(parts[0], parts[1]);
    }
}