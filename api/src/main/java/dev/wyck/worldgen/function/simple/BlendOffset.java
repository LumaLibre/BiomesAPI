package dev.wyck.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Used in vanilla for smooth transitions to chunks generated in old versions.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#blend_offset">Density function - blend_offset</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlendOffset extends SimpleFunction {

    /** The blend offset density function. */
    @AsOf("3.0.0")
    BlendOffset INSTANCE = of();

    private static BlendOffset of() {
        record Holder() {
            static final ConstructWireProvider<BlendOffset> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.simple.BlendOffsetImpl");
        }
        return Holder.WIRE.construct();
    }
}
