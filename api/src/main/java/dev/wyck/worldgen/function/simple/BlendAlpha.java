package dev.wyck.worldgen.function.simple;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Used in vanilla for smooth transitions to chunks generated in old versions.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#blend_alpha">Density function - blend_alpha</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlendAlpha extends SimpleFunction {

    /** The blend alpha density function. */
    @AsOf("3.0.0")
    BlendAlpha INSTANCE = of();

    private static BlendAlpha of() {
        record Holder() {
            static final ConstructWireProvider<BlendAlpha> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.simple.BlendAlphaImpl");
        }
        return Holder.WIRE.construct();
    }
}
