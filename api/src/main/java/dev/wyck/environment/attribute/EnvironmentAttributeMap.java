package dev.wyck.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.attribute.modifier.AttributeModification;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.internal.FriendlyColorUtil;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection of EnvironmentAttributes.
 *
 * @see EnvironmentAttributes
 * @since 1.1.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public record EnvironmentAttributeMap(
    Map<ResourceKey, EnvironmentAttribute<?>> attributes,
    Map<ResourceKey, Modification<?, ?>> modifications,
    List<Pending<?>> pending
) {

    public static final EnvironmentAttributeMap EMPTY = new EnvironmentAttributeMap(Map.of(), Map.of(), List.of());

    @AsOf("2.1.0")
    public EnvironmentAttributeMap {
        attributes = Map.copyOf(attributes);
        modifications = Map.copyOf(modifications);
        pending = List.copyOf(pending);
    }

    @AsOf("2.1.0")
    public EnvironmentAttributeMap(Map<ResourceKey, EnvironmentAttribute<?>> attributes) {
        this(attributes, Map.of(), List.of());
    }

    @AsOf("3.3.0")
    public EnvironmentAttributeMap(Map<ResourceKey, EnvironmentAttribute<?>> attributes, List<Pending<?>> pending) {
        this(attributes, Map.of(), pending);
    }

    /**
     * Returns the fully resolved directly set attribute map.
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
     * Returns the directly set wrapped attributes as a collection. Order matches insertion order.
     * @return the directly set wrapped attributes
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Collection<EnvironmentAttribute<?>> values() {
        return attributes().values();
    }

    /**
     * Returns the modifiers in this map, indexed by their attribute keys.
     * @return the attribute modifiers
     * @since 3.3.0
     */
    @Override
    @AsOf("3.3.0")
    public Map<ResourceKey, Modification<?, ?>> modifications() {
        return modifications;
    }

    /**
     * Returns the modifier for the given attribute, or null if that attribute is directly set or absent.
     * @param supplier the attribute supplier
     * @return the modifier associated with the attribute, or null if absent
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the modifier
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @SuppressWarnings("unchecked")
    public <V, A> @Nullable Modification<V, A> modification(EnvironmentAttributeSupplier<V> supplier) {
        return (Modification<V, A>) modifications.get(supplier.key());
    }

    /**
     * Returns the value of the given attribute, or null if it is not present.
     * Pending values take precedence over resolved ones, and the most recently
     * set pending value wins.
     *
     * @param supplier the attribute supplier
     * @param <V> the type of the attribute
     * @return the directly set value associated with the supplier, or null if modified or absent
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
        return attributes.isEmpty() && modifications.isEmpty() && pending.isEmpty();
    }

    /**
     * @return a new builder pre-populated with this map's attributes.
     * @since 2.1.0
     */
    @AsOf("2.1.0")
    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.attributes.putAll(attributes);
        builder.modifications.putAll(modifications);
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
        Map<ResourceKey, EnvironmentAttribute<?>> newAttributes = new LinkedHashMap<>(attributes);
        newAttributes.remove(supplier.key());

        Map<ResourceKey, Modification<?, ?>> newModifications = new LinkedHashMap<>(modifications);
        newModifications.remove(supplier.key());

        List<Pending<?>> newPending = new ArrayList<>(pending);
        newPending.removeIf(entry -> entry.supplier().key().equals(supplier.key()));
        if (value != null) {
            newPending.add(new Pending<>(supplier, value));
        }
        return new EnvironmentAttributeMap(newAttributes, newModifications, newPending);
    }

    /**
     * Creates a new map with a modifier for the given attribute.
     *
     * <p>The operation and argument use the same types as an attribute timeline track. For example,
     * multiplying a float attribute takes a {@link Float} argument, while
     * {@link AttributeOperation#ALPHA_BLEND} takes an
     * {@link dev.wyck.environment.attribute.modifier.AlphaValue} for float attributes.
     *
     * @param supplier the attribute supplier
     * @param operation the operation applied to the attribute
     * @param argument the operation argument
     * @return a new map containing the modifier
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the modifier
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public <V, A> EnvironmentAttributeMap withModifier(EnvironmentAttributeSupplier<V> supplier, AttributeOperation operation, A argument) {
        Map<ResourceKey, EnvironmentAttribute<?>> newAttributes = new LinkedHashMap<>(attributes);
        newAttributes.remove(supplier.key());

        List<Pending<?>> newPending = new ArrayList<>(pending);
        newPending.removeIf(entry -> entry.supplier().key().equals(supplier.key()));

        Map<ResourceKey, Modification<?, ?>> newModifications = new LinkedHashMap<>(modifications);
        newModifications.put(supplier.key(), new Modification<>(supplier.get(), operation, argument));
        return new EnvironmentAttributeMap(newAttributes, newModifications, newPending);
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
     * Creates a new map containing every entry from this map plus every entry from the given map.
     *
     * <p>Unlike {@link Builder#merge(EnvironmentAttributeMap)}, colliding keys do not throw: any
     * attribute, modifier, or pending value in {@code source} fully replaces the entry for that key
     * in this map, regardless of which form it took here.
     *
     * @param source the map whose entries take precedence
     * @return a new map with the given map's entries applied over this one
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public EnvironmentAttributeMap with(EnvironmentAttributeMap source) {
        if (source.empty()) {
            return this;
        }
        if (this.empty()) {
            return source;
        }

        Set<ResourceKey> overridden = new HashSet<>(source.attributes.keySet());
        overridden.addAll(source.modifications.keySet());
        for (Pending<?> entry : source.pending) {
            overridden.add(entry.supplier().key());
        }

        Map<ResourceKey, EnvironmentAttribute<?>> newAttributes = new LinkedHashMap<>(attributes);
        Map<ResourceKey, Modification<?, ?>> newModifications = new LinkedHashMap<>(modifications);
        List<Pending<?>> newPending = new ArrayList<>(pending);

        newAttributes.keySet().removeAll(overridden);
        newModifications.keySet().removeAll(overridden);
        newPending.removeIf(entry -> overridden.contains(entry.supplier().key()));

        newAttributes.putAll(source.attributes);
        newModifications.putAll(source.modifications);
        newPending.addAll(source.pending);

        return new EnvironmentAttributeMap(newAttributes, newModifications, newPending);
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
        return new EnvironmentAttributeMap(map, Map.of(), List.of());
    }

    /**
     * Reads a Minecraft environment attribute map into a wrapper.
     * @param minecraftAttributeMap the attribute map to read
     * @return the wrapper for it
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
     * A fixed operation and argument applied to an environment attribute.
     *
     * @param attribute the attribute being modified
     * @param operation the operation applied to the attribute
     * @param argument the argument supplied to the operation
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the operation
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public record Modification<V, A>(EnvironmentAttribute<V> attribute, AttributeOperation operation, A argument) implements AttributeModification<V> {}

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
        private final Map<ResourceKey, Modification<?, ?>> modifications = new LinkedHashMap<>();
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
            this.attributes.remove(supplier.key());
            this.modifications.remove(supplier.key());
            this.pending.removeIf(entry -> entry.supplier().key().equals(supplier.key()));
            this.pending.add(new Pending<>(supplier, value));
            return this;
        }

        /**
         * Adds an attribute modifier to the builder.
         *
         * <p>The operation and argument follow the same rules as an attribute timeline track.
         *
         * @param supplier the attribute supplier
         * @param operation the operation applied to the attribute
         * @param argument the operation argument
         * @return the builder
         * @param <V> the value type of the attribute
         * @param <A> the argument type of the modifier
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public <V, A> Builder modify(EnvironmentAttributeSupplier<V> supplier, AttributeOperation operation, A argument) {
            this.attributes.remove(supplier.key());
            this.pending.removeIf(entry -> entry.supplier().key().equals(supplier.key()));
            this.modifications.put(supplier.key(), new Modification<>(supplier.get(), operation, argument));
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
            this.modifications.clear();
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
                if (this.attributes.containsKey(entry.getKey()) || this.modifications.containsKey(entry.getKey())) {
                    throw new IllegalArgumentException("Attribute: " + entry.getKey() + " is already present.");
                }
                this.attributes.put(entry.getKey(), entry.getValue());
            }
            for (var entry : source.modifications().entrySet()) {
                if (this.attributes.containsKey(entry.getKey()) || this.modifications.containsKey(entry.getKey())) {
                    throw new IllegalArgumentException("Attribute: " + entry.getKey() + " is already present.");
                }
                this.modifications.put(entry.getKey(), entry.getValue());
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
            return new EnvironmentAttributeMap(this.attributes, this.modifications, this.pending);
        }
    }
}
