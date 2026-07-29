package dev.wyck.util.attribute;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeModification;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
import dev.wyck.wrapper.Wrapper;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AttributeModifiersUtil {

    private AttributeModifiersUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Resolves the Minecraft modifier represented by an API modification.
     * @param modification the API modification
     * @return the Minecraft modifier
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the modifier
     */
    @SuppressWarnings("unchecked")
    public static <V, A> AttributeModifier<V, A> modifier(AttributeModification<V> modification) {
        if (modification.operation() == AttributeOperation.OVERRIDE) {
            return (AttributeModifier<V, A>) AttributeModifier.override();
        }

        net.minecraft.world.attribute.EnvironmentAttribute<V> attribute = modification.attribute().asHandle();
        AttributeModifier.OperationId id = modification.operation().toNms(AttributeModifier.OperationId.class);
        AttributeModifier<V, ?> modifier = attribute.type().modifierLibrary().get(id);
        if (modifier == null) {
            throw new IllegalArgumentException(
                "Operation " + modification.operation() + " is not supported by " + modification.attribute().key()
                    + "; supported operations are " + attribute.type().modifierLibrary().keySet());
        }
        return (AttributeModifier<V, A>) modifier;
    }

    /**
     * Resolves the API operation represented by a Minecraft modifier.
     * @param attribute the Minecraft attribute supporting the modifier
     * @param modifier the Minecraft modifier
     * @return the API operation
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the modifier
     */
    public static <V, A> AttributeOperation operation(
        net.minecraft.world.attribute.EnvironmentAttribute<V> attribute,
        AttributeModifier<V, A> modifier
    ) {
        if (modifier == AttributeModifier.<V>override()) {
            return AttributeOperation.OVERRIDE;
        }
        for (var candidate : attribute.type().modifierLibrary().entrySet()) {
            if (candidate.getValue() == modifier) {
                return AttributeOperation.TRANSLATOR.fromNms(candidate.getKey());
            }
        }
        throw new IllegalArgumentException("Unknown modifier for environment attribute " + attribute);
    }

    /**
     * Converts an API operation argument to its Minecraft representation.
     * @param attribute the attribute whose converter handles ordinary arguments
     * @param argument the API argument
     * @return the Minecraft argument
     * @param <V> the value type of the attribute
     * @param <A> the argument type of the modifier
     */
    @SuppressWarnings("unchecked")
    public static <V, A> A argument(EnvironmentAttribute<V> attribute, Object argument) {
        if (argument instanceof AlphaValue || argument instanceof GrayBlend) {
            return ((Wrapper) argument).asHandle();
        }
        attribute.value((V) argument);
        return attribute.minecraftValue();
    }
}
