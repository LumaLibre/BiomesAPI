package dev.wyck.decode.environment.attribute;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.util.attribute.AttributeModifiersUtil;
import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
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
        Map<ResourceKey, EnvironmentAttributeMap.Modification<?, ?>> modifications = new LinkedHashMap<>();
        for (net.minecraft.world.attribute.EnvironmentAttribute<?> attribute : attributes) {
            read(map, attribute, decoded, modifications);
        }
        return new EnvironmentAttributeMap(decoded, modifications, List.of());
    }

    private static <V> void read(
        net.minecraft.world.attribute.EnvironmentAttributeMap map,
        net.minecraft.world.attribute.EnvironmentAttribute<V> attribute,
        Map<ResourceKey, EnvironmentAttribute<?>> decoded,
        Map<ResourceKey, EnvironmentAttributeMap.Modification<?, ?>> modifications
    ) {
        net.minecraft.world.attribute.EnvironmentAttributeMap.Entry<V, ?> entry = map.get(attribute);
        if (entry == null) {
            throw new IllegalStateException("The environment attribute " + attribute + " left its own key set");
        }

        if (entry.modifier() == AttributeModifier.<V>override()) {
            EnvironmentAttribute<?> wrapped = EnvironmentAttribute.decode(attribute, entry.argument());
            decoded.put(wrapped.key(), wrapped);
            return;
        }

        EnvironmentAttributeMap.Modification<V, ?> modification = modification(attribute, entry);
        modifications.put(modification.attribute().key(), modification);
    }

    private static <V, A> EnvironmentAttributeMap.Modification<V, ?> modification(
        net.minecraft.world.attribute.EnvironmentAttribute<V> attribute,
        net.minecraft.world.attribute.EnvironmentAttributeMap.Entry<V, A> entry
    ) {
        AttributeOperation operation = AttributeModifiersUtil.operation(attribute, entry.modifier());
        Object argument = entry.argument();

        EnvironmentAttribute<V> wrapped;
        Object decodedArgument;
        if (argument instanceof net.minecraft.world.attribute.modifier.FloatWithAlpha alpha) {
            wrapped = EnvironmentAttribute.decode(attribute, attribute.defaultValue());
            decodedArgument = AlphaValue.of(alpha.value(), alpha.alpha());
        } else if (argument instanceof net.minecraft.world.attribute.modifier.ColorModifier.BlendToGray gray) {
            wrapped = EnvironmentAttribute.decode(attribute, attribute.defaultValue());
            decodedArgument = GrayBlend.of(gray.brightness(), gray.factor());
        } else {
            wrapped = EnvironmentAttribute.decode(attribute, argument);
            decodedArgument = wrapped.value();
        }
        return new EnvironmentAttributeMap.Modification<>(wrapped, operation, decodedArgument);
    }

}
