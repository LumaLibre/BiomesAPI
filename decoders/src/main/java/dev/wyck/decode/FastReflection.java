package dev.wyck.decode;

import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: this could be moved to commons for other wrappers to use
/**
 * Reads members where a class (from Minecraft) does not expose.
 * Handles are cached for performance reasons.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Internal
public final class FastReflection {

    private static final Map<String, Field> FIELDS = new ConcurrentHashMap<>();
    private static final Map<String, Method> METHODS = new ConcurrentHashMap<>();
    private static final Map<String, Class<?>> CLASSES = new ConcurrentHashMap<>();

    private FastReflection() {
    }


    @AsOf("3.3.0")
    public static Class<?> type(String binaryName) {
        return CLASSES.computeIfAbsent(binaryName, name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("No such class: " + name, e);
            }
        });
    }

    public static <T> T read(Object instance, String fieldName) {
        return read(instance.getClass(), instance, fieldName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T read(Class<?> owner, Object instance, String fieldName) {
        try {
            return (T) field(owner, fieldName).get(instance);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot read " + owner.getName() + '.' + fieldName, e);
        }
    }

    /**
     * Reads a static field, including private singletons and constants.
     * @param owner the class declaring the field
     * @param fieldName the field name
     * @return the field value
     * @param <T> the field value type
     */
    public static <T> T readStatic(Class<?> owner, String fieldName) {
        return read(owner, null, fieldName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T call(Object instance, String methodName) {
        try {
            return (T) method(instance.getClass(), methodName).invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot call " + instance.getClass().getName() + '.' + methodName, e);
        }
    }

    private static Field field(Class<?> owner, String fieldName) {
        return FIELDS.computeIfAbsent(owner.getName() + '#' + fieldName, ignored -> {
            for (Class<?> type = owner; type != null; type = type.getSuperclass()) {
                try {
                    Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException _) {
                    // keep walking up
                }
            }
            throw new IllegalStateException("No field '" + fieldName + "' on " + owner.getName());
        });
    }

    private static Method method(Class<?> owner, String methodName) {
        return METHODS.computeIfAbsent(owner.getName() + '#' + methodName + "()", ignored -> {
            for (Class<?> type = owner; type != null; type = type.getSuperclass()) {
                try {
                    Method found = type.getDeclaredMethod(methodName);
                    found.setAccessible(true);
                    return found;
                } catch (NoSuchMethodException _) {
                }
            }
            throw new IllegalStateException("No method '" + methodName + "()' on " + owner.getName());
        });
    }
}
