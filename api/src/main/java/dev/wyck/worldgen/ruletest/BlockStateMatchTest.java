package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

/**
 * Tests is the block for the specified {@link BlockData}.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#blockstate_match">Processor list (blockstate match)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockStateMatchTest extends RuleTest {

    /**
     * The block state to match.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData blockState();

    /**
     * Creates a new block state match test.
     * @param blockState the block state to match
     * @return the block state match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockStateMatchTest of(BlockData blockState) {
        record Holder() {
            static final ConstructWireProvider<BlockStateMatchTest> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.ruletest.BlockStateMatchTestImpl");
        }
        return Holder.WIRE.construct(blockState);
    }

    /**
     * Creates a new block state match test.
     * @param material the material to match
     * @return the block state match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockStateMatchTest of(Material material) {
        return of(BukkitBootstrapUtil.util().createBlockData(material));
    }
}
