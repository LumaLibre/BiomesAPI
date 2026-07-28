package dev.wyck.decode.environment.attribute;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class EnvironmentAttributeMapDecoder implements Decodable<EnvironmentAttributeMap, net.minecraft.world.attribute.EnvironmentAttributeMap> {

    @Override
    public EnvironmentAttributeMap decode(net.minecraft.world.attribute.EnvironmentAttributeMap map) {
        // sort so the same map always decodes the same way
        List<net.minecraft.world.attribute.EnvironmentAttribute<?>> attributes = map.keySet().stream()
            .sorted(Comparator.comparing(Object::toString))
            .toList();

        Map<ResourceKey, EnvironmentAttribute<?>> decoded = new LinkedHashMap<>();
        for (net.minecraft.world.attribute.EnvironmentAttribute<?> attribute : attributes) {
            EnvironmentAttribute<?> wrapped = read(map, attribute);
            decoded.put(wrapped.key(), wrapped);
        }
        return new EnvironmentAttributeMap(decoded);
    }

    // TODO: revisit this
    private static <V> EnvironmentAttribute<?> read(net.minecraft.world.attribute.EnvironmentAttributeMap map,net.minecraft.world.attribute.EnvironmentAttribute<V> attribute) {
        net.minecraft.world.attribute.EnvironmentAttributeMap.Entry<V, ?> entry = map.get(attribute);
        if (entry == null) {
            throw new IllegalStateException("The environment attribute " + attribute + " left its own key set");
        }

        if (entry.modifier() != AttributeModifier.<V>override()) {
            throw new IllegalArgumentException("The environment attribute " + attribute + " is modified rather than set, which Wyck cannot read");
        }
        return EnvironmentAttribute.decode(attribute, entry.argument());
    }
}
