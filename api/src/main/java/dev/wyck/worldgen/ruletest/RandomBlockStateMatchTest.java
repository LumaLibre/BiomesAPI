package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

/**
 * Tests is the block for the specified {@link BlockData}. Then only matches with a given probability.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#random_blockstate_match">Processor list (random blockstate match)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomBlockStateMatchTest extends RuleTest {

    /**
     * The block state to match.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData blockState();

    /**
     * The probability of matching between 0.0 and 1.0.
     * @return the probability
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * Creates a new random block state match test.
     * @param blockState the block state to match
     * @param probability the probability of matching
     * @return the random block state match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBlockStateMatchTest of(BlockData blockState, float probability) {
        record Holder() {
            static final ConstructWireProvider<RandomBlockStateMatchTest> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.ruletest.RandomBlockStateMatchTestImpl");
        }
        return Holder.WIRE.construct(blockState, probability);
    }

    /**
     * Creates a new random block state match test.
     * @param material the material to match
     * @param probability the probability of matching
     * @return the random block state match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBlockStateMatchTest of(Material material, float probability) {
        return of(BukkitBootstrapUtil.util().createBlockData(material), probability);
    }
}
