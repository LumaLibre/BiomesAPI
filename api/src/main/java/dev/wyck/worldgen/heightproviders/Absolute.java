package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * An absolute height as seen on the F3 screen.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/vertical_anchor">Vertical anchor</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface Absolute extends VerticalAnchor {

    /**
     * The Y level.
     * @return an absolute y level
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int y();

    /**
     * Creates a new absolute vertical anchor.
     * @param y Y measured from the bottom of the world
     * @return a new absolute vertical anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Absolute of(int y) {
        record Holder() {
            static final ConstructWireProvider<Absolute> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.AbsoluteImpl");
        }
        return Holder.WIRE.construct(y);
    }
}
