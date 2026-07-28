package dev.wyck.tags;

import com.google.common.base.Preconditions;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Either;
import dev.wyck.util.Lazy;
import dev.wyck.util.WorldgenConversions;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class TagSetImpl<T extends Keyed, U> implements TagSet<T> {

    private final @Nullable ResourceKey resourceKey;
    private final Either<Set<T>, TagKey> value;
    private final RegistryId registryId;
    private final Lazy<WyckRegistry> registry;

    public TagSetImpl(@Nullable ResourceKey resourceKey, RegistryId registryId, Either<Set<T>, TagKey> value) {
        this.resourceKey = resourceKey;
        this.value = value;
        this.registryId = registryId;
        this.registry = WyckRegistry.lazy(registryId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public net.minecraft.core.HolderSet<U> asHolderSet() {
        net.minecraft.core.Registry<U> nmsRegistry = registry.get().asHandle();

        return value.fold(
            elements -> {
                net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<U>> registryKey = nmsRegistry.key();

                return WorldgenConversions.toHolderSet(elements, (element) -> {
                    Key kyoriKey = element.key();
                    net.minecraft.resources.Identifier id = net.minecraft.resources.Identifier.fromNamespaceAndPath(kyoriKey.namespace(), kyoriKey.value());
                    net.minecraft.resources.ResourceKey<U> elementKey = net.minecraft.resources.ResourceKey.create(registryKey, id);
                    return nmsRegistry.getOrThrow(elementKey);
                });
            },
            tagKey -> {
                net.minecraft.tags.TagKey<U> tag = (net.minecraft.tags.TagKey<U>) tagKey.toMinecraft();
                Optional<net.minecraft.core.HolderSet.Named<U>> bound = nmsRegistry.get(tag);
                if (bound.isPresent()) {
                    return bound.get();
                }

                //noinspection ConstantConditions
                if (Bukkit.getServer() == null) { // TODO: checking bootstrap utility
                    return net.minecraft.core.HolderSet.emptyNamed(nmsRegistry, tag);
                }
                return nmsRegistry.getOrThrow(tag);
            }
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public net.minecraft.tags.TagKey<U> asTagKey() {
        return value.fold(
            elements -> {
                net.minecraft.core.Registry<U> nmsRegistry = registry.get().asHandle();

                Set<net.minecraft.core.Holder<U>> target = new HashSet<>(this.asHolderSet().stream().toList());

                return nmsRegistry.getTags()
                    .filter(named -> new HashSet<>(named.stream().toList()).equals(target))
                    .map(net.minecraft.core.HolderSet.Named::key)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("could not find a tag for " + elements));
            },
            tag -> {
                return tag.asHandle();
            }
        );
    }

    @Override
    public Object toMinecraft() {
        return value.fold(
            _ -> this.asHolderSet(),
            _ -> this.asTagKey()
        );
    }

    @Override
    public TagSet<T> register() {
        Preconditions.checkState(resourceKey != null, "cannot register a TagSet without a resourceKey");
        Preconditions.checkState(value.left().isPresent(), "cannot register a TagSet that has no contents to bind to");

        net.minecraft.core.Registry<U> nms = registry.get().asHandle();
        net.minecraft.resources.Identifier identifier = resourceKey.identifier();
        net.minecraft.tags.TagKey<U> newTag = net.minecraft.tags.TagKey.create(nms.key(), identifier);

        // snapshot every currently bound tag so we don't wipe them
        Map<net.minecraft.tags.TagKey<U>, List<net.minecraft.core.Holder<U>>> merged = new HashMap<>();
        nms.getTags().forEach(named -> merged.put(named.key(), named.stream().toList()));

        Preconditions.checkArgument(!merged.containsKey(newTag), "tag already exists: " + identifier);
        merged.put(newTag, this.asHolderSet().stream().toList());

        // rebind the union
        net.minecraft.tags.TagLoader.LoadResult<U> result = new net.minecraft.tags.TagLoader.LoadResult<>(nms.key(), merged);
        nms.prepareTagReload(result).apply();
        return this;
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return Optional.ofNullable(this.resourceKey);
    }

    @Override
    public Either<Set<T>, TagKey> value() {
        return this.value;
    }

    @Override
    public RegistryId registryId() {
        return this.registryId;
    }

    @Override
    public Key key() {
        if (this.resourceKey == null) {
            throw new NoSuchElementException("resourceKey not present");
        }
        return this.resourceKey;
    }

    @Override
    public String toString() {
        return "TagSetImpl{" +
            "resourceKey=" + resourceKey +
            ", value=" + value +
            ", registryId=" + registryId +
            '}';
    }
}
