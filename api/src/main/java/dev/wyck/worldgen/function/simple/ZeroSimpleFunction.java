package dev.wyck.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A density function that always returns zero.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ZeroSimpleFunction extends SimpleFunction {

    /** The zero-density function. */
    @AsOf("3.0.0")
    ZeroSimpleFunction INSTANCE = of();

    private static ZeroSimpleFunction of() {
        record Holder() {
            static final ConstructWireProvider<ZeroSimpleFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.simple.ZeroSimpleFunctionImpl");
        }
        return Holder.WIRE.construct();
    }
}
