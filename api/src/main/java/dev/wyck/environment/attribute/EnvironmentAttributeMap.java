package dev.wyck.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.internal.FriendlyColorUtil;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A collection of EnvironmentAttributes.
 *
 * @see EnvironmentAttributes
 * @since 1.1.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public record EnvironmentAttributeMap(
    Map<ResourceKey, EnvironmentAttribute<?>> attributes,
    List<Pending<?>> pending
) {

    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of(), List.of());

    @AsOf("2.1.0")
    public EnvironmentAttributeMap {
        attributes = Map.copyOf(attributes);
        pending = List.copyOf(pending);
    }

    @AsOf("2.1.0")
    public EnvironmentAttributeMap(Map<ResourceKey, EnvironmentAttribute<?>> attributes) {
        this(attributes, List.of());
    }

    /**
     * Returns the fully resolved attribute map.
     *
     * @return the resolved attributes, in an unmodifiable map
     * @since 2.1.0
     */
    @Override
    @AsOf("2.1.0")
    public Map<ResourceKey, EnvironmentAttribute<?>> attributes() {
        if (pending.isEmpty()) {
            return attributes;
        }
        Map<ResourceKey, EnvironmentAttribute<?>> resolved = new LinkedHashMap<>(attributes);
        for (Pending<?> entry : pending) {
            EnvironmentAttribute<?> attr = entry.resolve();
            resolved.put(attr.key(), attr);
        }
        return Collections.unmodifiableMap(resolved);
    }

    /**
     * Returns the wrapped attributes as a collection. Order matches insertion order.
     * @return the wrapped attributes
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Collection<EnvironmentAttribute<?>> values() {
        return attributes().values();
    }

    /**
     * Returns the value of the given attribute, or null if it is not present.
     * Pending values take precedence over resolved ones, and the most recently
     * set pending value wins.
     *
     * @param supplier the attribute supplier
     * @param <V> the type of the attribute
     * @return the value associated with the supplier, or null if absent
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    public <V> @Nullable V get(EnvironmentAttributeSupplier<V> supplier) {
        for (int i = pending.size() - 1; i >= 0; i--) {
            Pending<?> entry = pending.get(i);
            if (entry.supplier().equals(supplier)) {
                return (V) entry.value();
            }
        }
        EnvironmentAttribute<?> attribute = attributes.get(supplier.key());
        return attribute != null ? (V) attribute.value() : null;
    }

    /**
     * @return true if the map is empty, false otherwise.
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public boolean empty() {
        return attributes.isEmpty() && pending.isEmpty();
    }

    /**
     * @return a new builder pre-populated with this map's attributes.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.attributes.putAll(attributes);
        builder.pending.addAll(pending);
        return builder;
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap with the given attribute added.
     * @param supplier the attribute supplier
     * @param value the value to set
     * @return a new WrappedEnvironmentAttributeMap with the given attribute added
     * @param <V> the type of the attribute
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public <V> EnvironmentAttributeMap with(EnvironmentAttributeSupplier<V> supplier, @Nullable V value) {
        List<Pending<?>> newPending = new ArrayList<>(pending);
        if (value == null) {
            newPending.removeIf(entry -> entry.supplier().equals(supplier));
        } else {
            newPending.add(new Pending<>(supplier, value));
        }
        return new EnvironmentAttributeMap(attributes, newPending);
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap with the given color attribute added.
     * @param supplier the color attribute supplier
     * @param hex the hex value (e.g. {@code "#FF10F0"})
     * @return a new WrappedEnvironmentAttributeMap with the given color attribute added
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public EnvironmentAttributeMap with(FriendlyColorSupplier supplier, @Nullable String hex) {
        return with(supplier, FriendlyColorUtil.hexOrNull(hex));
    }

    /**
     * A deferred attribute entry, holding a supplier and its exposed value.
     *
     * @param <V> the type of the attribute
     * @since 2.1.0
     */
    @ApiStatus.Internal
    public record Pending<V>(EnvironmentAttributeSupplier<V> supplier, V value) {
        EnvironmentAttribute<V> resolve() {
            return this.supplier.unbox(value);
        }
    }

    /**
     * Creates a new Builder instance.
     * @return a new Builder instance
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new WrappedEnvironmentAttributeMap from the given attributes.
     * @param attributes the attributes to include
     * @return a new WrappedEnvironmentAttributeMap
     * @since 2.1.0
     */
    @SafeVarargs
    @AsOf("2.1.0")
    public static <V> EnvironmentAttributeMap of(EnvironmentAttribute<V>... attributes) {
        Map<ResourceKey, EnvironmentAttribute<?>> map = new LinkedHashMap<>();
        for (EnvironmentAttribute<V> attribute : attributes) {
            ResourceKey key = attribute.key();
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate attribute: " + key);
            }
            map.put(key, attribute);
        }
        return new EnvironmentAttributeMap(map, List.of());
    }

    /**
     * Reads a Minecraft environment attribute map into a wrapper.
     * @param minecraftAttributeMap the attribute map to read
     * @return the wrapper for it
     * @throws IllegalArgumentException if an attribute is modified rather than set, which this map cannot hold
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static EnvironmentAttributeMap decode(Object minecraftAttributeMap) {
        record Holder() {
            static final Decoder<EnvironmentAttributeMap> DECODER = Decoder.create("dev.wyck.decode.environment.attribute.EnvironmentAttributeMapDecoder");
        }
        return Holder.DECODER.decode(minecraftAttributeMap);
    }

    /**
     * A builder for creating WrappedEnvironmentAttributeMap instances.
     *
     * @author Jsinco
     * @version 2.1.0
     * @since 1.1.0
     */
    @AsOf("2.1.0")
    public static class Builder {

        private final Map<ResourceKey, EnvironmentAttribute<?>> attributes = new LinkedHashMap<>();
        private final List<Pending<?>> pending = new ArrayList<>();

        /**
         * Sets an attribute in the builder.
         * If an attribute with the same handle is already present,
         * this throws to flag the duplicate to the caller.
         *
         * @param supplier the attribute supplier
         * @param value the exposed value
         * @param <V> the type of the attribute
         * @return the builder
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public <V> Builder attribute(EnvironmentAttributeSupplier<V> supplier, V value) {
            this.pending.add(new Pending<>(supplier, value));
            return this;
        }

        /**
         * Sets a color attribute in the builder using a hex string.
         *
         * @param supplier the color attribute supplier
         * @param hex      the hex value (e.g. {@code "#FF10F0"})
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder attribute(FriendlyColorSupplier supplier, String hex) {
            return attribute(supplier, FriendlyColorUtil.hex(hex));
        }

        /**
         * Removes all attributes from this builder.
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder clear() {
            this.attributes.clear();
            this.pending.clear();
            return this;
        }

        /**
         * Adds all attributes from the given map. Throws if any key collides with an existing entry.
         * @param source the map to add
         * @return the builder
         * @since 2.1.0
         */
        @AsOf("2.1.0")
        public Builder merge(EnvironmentAttributeMap source) {
            for (var entry : source.attributes().entrySet()) {
                if (this.attributes.containsKey(entry.getKey())) {
                    throw new IllegalArgumentException("Attribute: " + entry.getKey() + " is already present.");
                }
                this.attributes.put(entry.getKey(), entry.getValue());
            }
            this.pending.addAll(source.pending);
            return this;
        }

        /**
         * Builds the WrappedEnvironmentAttributeMap.
         *
         * @return the WrappedEnvironmentAttributeMap
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public EnvironmentAttributeMap build() {
            return new EnvironmentAttributeMap(this.attributes, this.pending);
        }
    }
}