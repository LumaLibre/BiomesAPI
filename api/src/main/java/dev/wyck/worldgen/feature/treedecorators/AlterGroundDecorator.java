package dev.wyck.worldgen.feature.treedecorators;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Replaces blocks in the <code>#minecraft:dirt</code> tag around the tree with different blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AlterGroundDecorator extends TreeDecorator {

    /**
     * A rule-based block state provider defining how to replace the ground.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider provider();

    /**
     * Creates a new alter ground decorator.
     * @param provider a rule-based block state provider defining how to replace the ground
     * @return a new alter ground decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AlterGroundDecorator of(BlockStateProvider provider) {
        record Holder() {
            static final ConstructWireProvider<AlterGroundDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.AlterGroundDecoratorImpl");
        }
        return Holder.WIRE.construct(provider);
    }
}
