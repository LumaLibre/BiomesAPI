package dev.wyck.environment.attribute;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import net.minecraft.world.attribute.AttributeTypes;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.logging.Logger;

@NullMarked
@ApiStatus.Internal
public class EnvironmentAttributeImpl<V, U> implements EnvironmentAttribute<V> {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.ENVIRONMENT_ATTRIBUTE);
    private static final net.minecraft.world.attribute.EnvironmentAttribute<?> UNSUPPORTED = net.minecraft.world.attribute.EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false).build();
    private static final Logger LOGGER = Logger.getLogger(EnvironmentAttributeImpl.class.getName());

    private final ResourceKey key;
    private final @Nullable Converter<V, U> converter;
    private final Lazy<net.minecraft.world.attribute.EnvironmentAttribute<U>> nms;
    private @MonotonicNonNull V value;

    public EnvironmentAttributeImpl(ResourceKey key, @Nullable Converter<V, U> converter) {
        this.key = key;
        this.converter = converter;
        this.nms = Lazy.of(this::resolveNms);
    }

    @SuppressWarnings("unchecked")
    private net.minecraft.world.attribute.EnvironmentAttribute<U> resolveNms() {
        net.minecraft.world.attribute.EnvironmentAttribute<U> nullableNms = REGISTRY.get().retrieve(key);
        if (nullableNms == null) {
            LOGGER.warning("Environment attribute with key '" + key + "' not found in registry; this attribute is not supported by this Minecraft version!");
            return (net.minecraft.world.attribute.EnvironmentAttribute<U>) UNSUPPORTED;
        }
        return nullableNms;
    }

    @Override
    public ResourceKey key() {
        return this.key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable V value() {
        if (this.value == null && this.converter == null) {
            this.value = (V) this.nms.get().defaultValue();
        }
        return this.value;
    }

    @Override
    public void value(V value) {
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V defaultValue() {
        net.minecraft.world.attribute.EnvironmentAttribute<U> nmsAttribute = nms.get();
        Object defaultValue = nmsAttribute.defaultValue();
        if (isBasic(defaultValue)) {
            return (V) defaultValue;
        }
        return EnvironmentAttribute.<V>decode(nmsAttribute, defaultValue).value();
    }

    @SuppressWarnings("unchecked")
    public U minecraftValue() {
        V current = value();
        if (this.converter == null && current != null) {
            return (U) current;
        } else if (this.converter == null) {
            throw new IllegalStateException("No converter defined");
        }
        return this.converter.convert(current);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <SHADOWED> SHADOWED minecraftDefaultValue() {
        return (SHADOWED) this.nms.get().defaultValue();
    }

    @Override
    public Object toMinecraft() {
        return this.nms.get();
    }

    @Override
    public boolean syncable() {
        return this.nms.get().isSyncable();
    }

    @Override
    public boolean spatiallyInterpolated() {
        return this.nms.get().isSpatiallyInterpolated();
    }

    @Override
    public boolean positional() {
        return this.nms.get().isPositional();
    }

    @Override
    public String toString() {
        return "EnvironmentAttributeImpl{key=" + this.key + "}";
    }

    private static boolean isBasic(Object value) {
        return value instanceof Number || value instanceof Boolean || value instanceof Character || value instanceof String;
    }
}