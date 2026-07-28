package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.util.InclusiveRange;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.stateproviders.DualNoiseProvider;
import dev.wyck.worldgen.stateproviders.NoiseProvider;
import dev.wyck.worldgen.stateproviders.NoiseThresholdProvider;
import dev.wyck.worldgen.stateproviders.RandomizedIntStateProvider;
import dev.wyck.worldgen.stateproviders.RotatedBlockProvider;
import dev.wyck.worldgen.stateproviders.RuleBasedStateProvider;
import dev.wyck.worldgen.stateproviders.SimpleStateProvider;
import dev.wyck.worldgen.stateproviders.WeightedStateProvider;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.worldgen.valueproviders.IntProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class BlockStateProviderDecodeTest {

    @Test
    void everyVanillaBlockStateProviderTypeHasADecoder() {
        var decoders = new dev.wyck.decode.worldgen.stateproviders.BlockStateProviderDecoders();
        List<ResourceKey> missing = BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !decoders.handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no block state provider decoder is registered for: " + missing);
    }

    @Test
    void simpleWeightedAndRotatedProvidersDecode() {
        SimpleStateProvider simple = assertInstanceOf(SimpleStateProvider.class,
            BlockStateProvider.decode(BlockStateProvider.simple(Material.STONE).asHandle()));
        assertEquals(Material.STONE, simple.state().getMaterial());

        WeightedStateProvider weighted = WeightedStateProvider.of(WeightedList.of(List.of(
            new WeightedList.Weighted<>(data(Material.DIRT), 3),
            new WeightedList.Weighted<>(data(Material.GRAVEL), 1)
        )));
        WeightedStateProvider decoded = assertInstanceOf(WeightedStateProvider.class,
            BlockStateProvider.decode(weighted.asHandle()));
        assertEquals(Material.DIRT, decoded.entries().unwrap().getFirst().value().getMaterial());
        assertEquals(3, decoded.entries().unwrap().getFirst().weight());

        RotatedBlockProvider rotated = assertInstanceOf(RotatedBlockProvider.class,
            BlockStateProvider.decode(BlockStateProvider.rotated(Material.OAK_LOG).asHandle()));
        assertEquals(Material.OAK_LOG, rotated.state());
    }

    @Test
    void noiseProvidersKeepTheirSharedAndSpecializedFields() {
        NoiseParameters noise = NoiseParameters.of(-4, List.of(1.0, 0.5));
        NoiseProvider provider = NoiseProvider.of(42L, noise, 0.25f,
            List.of(data(Material.STONE), data(Material.DEEPSLATE)));
        NoiseProvider decoded = assertInstanceOf(NoiseProvider.class,
            BlockStateProvider.decode(provider.asHandle()));
        assertEquals(42L, decoded.seed());
        assertEquals(0.25f, decoded.scale());
        assertEquals(List.of(Material.STONE, Material.DEEPSLATE), materials(decoded.states()));

        NoiseThresholdProvider threshold = NoiseThresholdProvider.of(
            7L, noise, 0.5f, -0.2f, 0.75f, data(Material.AIR),
            List.of(data(Material.DIRT)), List.of(data(Material.GRASS_BLOCK))
        );
        NoiseThresholdProvider decodedThreshold = assertInstanceOf(NoiseThresholdProvider.class,
            BlockStateProvider.decode(threshold.asHandle()));
        assertEquals(-0.2f, decodedThreshold.threshold());
        assertEquals(0.75f, decodedThreshold.highChance());
        assertEquals(Material.AIR, decodedThreshold.defaultState().getMaterial());

        DualNoiseProvider dual = DualNoiseProvider.of(
            9L, noise, 0.35f, InclusiveRange.of(2, 5),
            NoiseParameters.of(-2, List.of(1.0)), 0.1f,
            List.of(data(Material.STONE), data(Material.ANDESITE))
        );
        DualNoiseProvider decodedDual = assertInstanceOf(DualNoiseProvider.class,
            BlockStateProvider.decode(dual.asHandle()));
        assertEquals(2, decodedDual.variety().minInclusive());
        assertEquals(5, decodedDual.variety().maxInclusive());
        assertEquals(0.1f, decodedDual.slowScale());
    }

    @Test
    void randomizedAndRuleBasedProvidersStackTheirDependencies() {
        RandomizedIntStateProvider randomized = RandomizedIntStateProvider.of(
            BlockStateProvider.simple(Material.FARMLAND), "moisture", IntProvider.uniform(1, 5)
        );
        RandomizedIntStateProvider decodedRandomized = assertInstanceOf(RandomizedIntStateProvider.class,
            BlockStateProvider.decode(randomized.asHandle()));
        assertInstanceOf(SimpleStateProvider.class, decodedRandomized.source());
        assertEquals("moisture", decodedRandomized.property());
        assertEquals(1, decodedRandomized.values().minInclusive());
        assertEquals(5, decodedRandomized.values().maxInclusive());

        RuleBasedStateProvider rules = RuleBasedStateProvider.of(
            BlockStateProvider.simple(Material.STONE),
            List.of(RuleBasedStateProvider.rule(
                BlockPredicate.not(BlockPredicate.alwaysTrue()),
                BlockStateProvider.simple(Material.DIRT)
            ))
        );
        RuleBasedStateProvider decodedRules = assertInstanceOf(RuleBasedStateProvider.class,
            BlockStateProvider.decode(rules.asHandle()));
        assertInstanceOf(SimpleStateProvider.class, decodedRules.fallback().orElseThrow());
        assertEquals(1, decodedRules.rules().size());
        assertInstanceOf(dev.wyck.worldgen.blockpredicates.NotPredicate.class,
            decodedRules.rules().getFirst().ifTrue());
        assertInstanceOf(SimpleStateProvider.class, decodedRules.rules().getFirst().then());
    }

    private static BlockData data(Material material) {
        return BukkitBootstrapUtil.util().createBlockData(material);
    }

    private static List<Material> materials(List<BlockData> states) {
        return states.stream().map(BlockData::getMaterial).toList();
    }
}
