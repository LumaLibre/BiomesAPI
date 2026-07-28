package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Rule tests are used to test if a block matches specific conditions.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#Rule_test">Processor list (Rule test)</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface RuleTest extends Wrapper {

    @ApiStatus.Internal
    Decoder<RuleTest> DECODER = Decoder.create("dev.wyck.decode.worldgen.ruletest.RuleTestDecoders");

    /**
     * Matches any block.
     * @return the always true test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static AlwaysTrueTest alwaysTrue() {
        return AlwaysTrueTest.INSTANCE;
    }

    /**
     * Tests the block is the specified block.
     * @param block the block to match
     * @return the block match test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BlockMatchTest blockMatch(Material block) {
        return BlockMatchTest.of(block);
    }

    /**
     * Tests is the block for the specified {@link BlockData}.
     * @param blockState the block blockState to match
     * @return the block blockState match test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BlockStateMatchTest blockStateMatch(BlockData blockState) {
        return BlockStateMatchTest.of(blockState);
    }

    /**
     * Tests is the block for the specified {@link Material}.
     * @param material the material to match
     * @return the block material match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockStateMatchTest blockStateMatch(Material material) {
        return BlockStateMatchTest.of(material);
    }

    /**
     * Tests if the block is in the specified block tag.
     * @param tag the tag to match
     * @return the tag match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TagMatchTest tagMatch(ResourceKey tag) {
        return TagMatchTest.of(tag);
    }

    /**
     * Tests if the block is in the specified block tag.
     * @param tag the tag to match
     * @return the tag match test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static TagMatchTest tagMatch(Tag<Material> tag) {
        return TagMatchTest.of(tag);
    }

    /**
     * Tests the block is the specified block. Then only matches with a given probability.
     * @param block the block to match
     * @param probability the probability of matching
     * @return the random block match test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RandomBlockMatchTest randomBlockMatch(Material block, float probability) {
        return RandomBlockMatchTest.of(block, probability);
    }

    /**
     * Tests is the block for the specified {@link BlockData}. Then only matches with a given probability.
     * @param state the block blockState to match
     * @param probability the probability of matching
     * @return the random block blockState match test
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static RandomBlockStateMatchTest randomBlockStateMatch(BlockData state, float probability) {
        return RandomBlockStateMatchTest.of(state, probability);
    }

    /**
     * Tests is the block for the specified {@link Material}. Then only matches with a given probability.
     * @param material the material to match
     * @param probability the probability of matching
     * @return the random block material match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBlockStateMatchTest randomBlockStateMatch(Material material, float probability) {
        return RandomBlockStateMatchTest.of(material, probability);
    }

    /**
     * Reads a Minecraft rule test into a Wyck rule test.
     * @param minecraftRuleTest the Minecraft rule test
     * @return the decoded rule test
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static RuleTest decode(Object minecraftRuleTest) {
        return DECODER.decode(minecraftRuleTest);
    }

}