package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.ruletest.AlwaysTrueTest;
import dev.wyck.worldgen.ruletest.BlockMatchTest;
import dev.wyck.worldgen.ruletest.BlockStateMatchTest;
import dev.wyck.worldgen.ruletest.RandomBlockMatchTest;
import dev.wyck.worldgen.ruletest.RandomBlockStateMatchTest;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.ruletest.TagMatchTest;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class RuleTestDecodeTest {

    @Test
    void everyVanillaRuleTestTypeHasADecoder() {
        var decoders = new dev.wyck.decode.worldgen.ruletest.RuleTestDecoders();
        List<ResourceKey> missing = BuiltInRegistries.RULE_TEST.keySet().stream()
            .map(key -> ResourceKey.of(key.getNamespace(), key.getPath()))
            .filter(key -> !decoders.handles(key))
            .toList();
        assertTrue(missing.isEmpty(), () -> "no rule-test decoder is registered for: " + missing);
    }

    @Test
    void allRuleTestShapesPreserveTheirFields() {
        assertInstanceOf(AlwaysTrueTest.class, decode(RuleTest.alwaysTrue()));

        BlockMatchTest block = assertInstanceOf(BlockMatchTest.class,
            decode(RuleTest.blockMatch(Material.DEEPSLATE)));
        assertEquals(Material.DEEPSLATE, block.block());

        BlockStateMatchTest state = assertInstanceOf(BlockStateMatchTest.class,
            decode(RuleTest.blockStateMatch(log(org.bukkit.Axis.X))));
        assertEquals(log(org.bukkit.Axis.X), state.blockState());

        TagMatchTest tag = assertInstanceOf(TagMatchTest.class,
            decode(RuleTest.tagMatch(ResourceKey.minecraft("stone_ore_replaceables"))));
        assertEquals(ResourceKey.minecraft("stone_ore_replaceables"), tag.tag());

        RandomBlockMatchTest randomBlock = assertInstanceOf(RandomBlockMatchTest.class,
            decode(RuleTest.randomBlockMatch(Material.NETHERRACK, 0.35f)));
        assertEquals(Material.NETHERRACK, randomBlock.block());
        assertEquals(0.35f, randomBlock.probability());

        RandomBlockStateMatchTest randomState = assertInstanceOf(RandomBlockStateMatchTest.class,
            decode(RuleTest.randomBlockStateMatch(log(org.bukkit.Axis.Z), 0.65f)));
        assertEquals(log(org.bukkit.Axis.Z), randomState.blockState());
        assertEquals(0.65f, randomState.probability());
    }

    private static RuleTest decode(RuleTest test) {
        return RuleTest.decode(test.asHandle());
    }

    private static org.bukkit.block.data.BlockData log(org.bukkit.Axis axis) {
        org.bukkit.block.data.Orientable data = (org.bukkit.block.data.Orientable)
            BukkitBootstrapUtil.util().createBlockData(Material.OAK_LOG);
        data.setAxis(axis);
        return data;
    }
}
