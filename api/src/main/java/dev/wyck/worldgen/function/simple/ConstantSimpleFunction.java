package dev.wyck.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A density function that always returns the same value.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConstantSimpleFunction extends SimpleFunction, Registerable<ConstantSimpleFunction> {

    /**
     * The constant value.
     * @return the constant value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double value();

    /**
     * Creates a new constant density function.
     * @param resourceKey the resource key, or null
     * @param value the constant value
     * @return the constant density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantSimpleFunction of(@Nullable ResourceKey resourceKey, double value) {
        record Holder() {
            static final ConstructWireProvider<ConstantSimpleFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.simple.ConstantSimpleFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), value);
    }

    /**
     * Creates a new constant density function.
     * @param value the constant value
     * @return the constant density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantSimpleFunction of(double value) {
        return of(null, value);
    }
}
