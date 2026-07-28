package dev.wyck.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@AsOf("3.0.0")
public interface TwoArgumentSimpleFunction extends SimpleFunction, Registerable<TwoArgumentSimpleFunction> { // misleading name?: vanilla does not extend SimpleFunction

    /**
     * The operation to perform.
     * @return the operation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Operation operation();

    /**
     * The first density function.
     * @return the first density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction first();

    /**
     * The second density function.
     * @return the second density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction second();

    /**
     * Performs an {@link Operation} on two density functions.
     * @param resourceKey the resource key, or null
     * @param operation the operation to perform
     * @param first the first density function
     * @param second the second density function
     * @return the result of the operation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoArgumentSimpleFunction of(@Nullable ResourceKey resourceKey, Operation operation, DensityFunction first, DensityFunction second) {
        record Holder() {
            static final ConstructWireProvider<TwoArgumentSimpleFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), operation, first, second);
    }

    /**
     * Performs an {@link Operation} on two density functions.
     * @param operation the operation to perform
     * @param first the first density function
     * @param second the second density function
     * @return the result of the operation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoArgumentSimpleFunction of(Operation operation, DensityFunction first, DensityFunction second) {
        return of(null, operation, first, second);
    }

    /**
     * Adds two density functions together.
     * @param first the first density function
     * @param second  the second density function
     * @return the sum of the two density functions
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoArgumentSimpleFunction add(DensityFunction first, DensityFunction second) {
        return of(Operation.ADD, first, second);
    }

    /**
     * Multiplies two density functions together.
     * @param first the first density function
     * @param second the second density function
     * @return the product of the two density functions
     * @since 3.0.0
     */
    static TwoArgumentSimpleFunction mul(DensityFunction first, DensityFunction second) {
        return of(Operation.MUL, first, second);
    }

    /**
     * Returns the minimum of two density functions.
     * @param first the first density function
     * @param second the second density function
     * @return the minimum of the two density functions
     * @since 3.0.0
     */
    static TwoArgumentSimpleFunction min(DensityFunction first, DensityFunction second) {
        return of(Operation.MIN, first, second);
    }

    /**
     * Returns the maximum of two density functions.
     * @param first the first density function
     * @param second the second density function
     * @return the maximum of the two density functions
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoArgumentSimpleFunction max(DensityFunction first, DensityFunction second) {
        return of(Operation.MAX, first, second);
    }

    /**
     * Available operations.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    enum Operation {
        ADD,
        MUL,
        MIN,
        MAX
    }
}
