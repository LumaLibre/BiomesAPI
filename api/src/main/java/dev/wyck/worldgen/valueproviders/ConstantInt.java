package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A constant int provider which always returns the same value.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConstantInt extends IntProvider {

    /** A constant int provider which always returns 0. */
    @AsOf("3.0.0")
    ConstantInt ZERO = of(0);

    /**
     * A constant int provider.
     * @param value the value to return
     * @return the constant int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantInt of(int value) {
        record Holder() {
            static final ConstructWireProvider<ConstantInt> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.ConstantIntImpl");
        }
        return Holder.WIRE.construct(value);
    }

}
