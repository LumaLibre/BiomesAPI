package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider which always resolves to a single fixed anchor.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConstantHeight extends HeightProvider {

    /** A constant height provider which always resolves to 0. */
    @AsOf("3.0.0")
    ConstantHeight ZERO = of(VerticalAnchor.absolute(0));

    /**
     * The vertical anchor used as the constant height.
     * @return the constant anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor value();

    /**
     * Creates a new constant height provider.
     * @param value the vertical anchor to use as the constant height
     * @return the constant height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantHeight of(VerticalAnchor value) {
        record Holder() {
            static final ConstructWireProvider<ConstantHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.ConstantHeightImpl");
        }
        return Holder.WIRE.construct(value);
    }
}
