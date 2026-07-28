package dev.wyck.tags;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A wrapper over Minecraft's {@code TagKey<T>}: a named reference to a tag within a specific registry. It is
 * backed by a {@link ResourceKey} and the {@link RegistryId} of the registry it lives in.
 *
 * @since 3.1.0
 * @version 3.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.1.0")
public interface TagKey extends Wrapper {

    /**
     * The identifier of this tag.
     * @return the tag identifier
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    ResourceKey key();

    /**
     * The registry this tag belongs to.
     * @return the registry discriminator
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    RegistryId registryId();

    /**
     * Creates a block tag key from its identifier.
     * @param key the tag identifier
     * @return a new block tag key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagKey blocks(ResourceKey key) {
        return of(RegistryId.BLOCK, key);
    }

    /**
     * Creates a block tag key from a Bukkit block {@link Tag}.
     * @param tag the Bukkit block tag
     * @return a new block tag key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static TagKey blocks(Tag<Material> tag) {
        return of(RegistryId.BLOCK, tag);
    }

    /**
     * Creates a timeline tag key from its identifier.
     * @param key the tag identifier
     * @return a new timeline tag key
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static TagKey timelines(ResourceKey key) {
        return of(RegistryId.TIMELINE, key);
    }

    /**
     * Creates a tag key from a Bukkit {@link Tag}, resolving against the given registry.
     * @param registry the registry the tag belongs to
     * @param tag the Bukkit tag
     * @return a new tag key
     * @param <T> the Bukkit type of the tag's elements
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static <T extends Keyed> TagKey of(RegistryId registry, Tag<T> tag) {
        return of(registry, ResourceKey.of(tag.getKey().getNamespace(), tag.getKey().getKey()));
    }

    /**
     * Creates a tag key from its identifier, resolving against the given registry.
     * @param registry the registry the tag belongs to
     * @param key the tag identifier
     * @return a new tag key
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    @ApiStatus.Internal
    static TagKey of(RegistryId registry, ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<TagKey> WIRE = ConstructWireProvider.create("dev.wyck.tags.TagKeyImpl");
        }
        return Holder.WIRE.construct(key, registry);
    }
}
