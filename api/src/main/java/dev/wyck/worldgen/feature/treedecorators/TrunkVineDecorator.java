package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Adds vines to each side of each trunk block with a probability of 75% each.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrunkVineDecorator extends TreeDecorator {

    /**
     * Creates a new trunk vine decorator.
     * @return a new trunk vine decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TrunkVineDecorator of() {
        record Holder() {
            static final ConstructWireProvider<TrunkVineDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.TrunkVineDecoratorImpl");
        }
        return Holder.WIRE.construct();
    }
}
