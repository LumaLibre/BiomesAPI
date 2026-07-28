package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

/**
 * Tests is the block is the specified block.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#block_match">Processor list (block match)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockMatchTest extends RuleTest {

    /**
     * The block to match.
     * @return the block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material block();

    /**
     * Creates a new block match test.
     * @param block the block to match
     * @return the block match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockMatchTest of(Material block) {
        record Holder() {
            static final ConstructWireProvider<BlockMatchTest> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.ruletest.BlockMatchTestImpl");
        }
        return Holder.WIRE.construct(block);
    }
}
