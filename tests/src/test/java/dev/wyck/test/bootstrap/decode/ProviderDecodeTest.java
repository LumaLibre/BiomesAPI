package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.heightproviders.ConstantHeight;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.UniformHeight;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.valueproviders.ClampedInt;
import dev.wyck.worldgen.valueproviders.ConstantInt;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.UniformFloat;
import dev.wyck.worldgen.valueproviders.UniformInt;
import dev.wyck.worldgen.valueproviders.WeightedListInt;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class ProviderDecodeTest {

    private static void assertCoversRegistry(Registry<?> registry, DecoderRegistry<?, ?> decoders, String family) {
        List<ResourceKey> missing = registry.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !decoders.handles(key))
            .toList();
        assertTrue(missing.isEmpty(), () -> "no " + family + " decoder registered for: " + missing);
    }

    @Test
    void everyVanillaProviderTypeHasADecoder() {
        assertCoversRegistry(BuiltInRegistries.INT_PROVIDER_TYPE,
            new dev.wyck.decode.worldgen.valueproviders.IntProviderDecoders(), "int provider");
        assertCoversRegistry(BuiltInRegistries.FLOAT_PROVIDER_TYPE,
            new dev.wyck.decode.worldgen.valueproviders.FloatProviderDecoders(), "float provider");
        assertCoversRegistry(BuiltInRegistries.HEIGHT_PROVIDER_TYPE,
            new dev.wyck.decode.worldgen.heightproviders.HeightProviderDecoders(), "height provider");
    }

    @Test
    void intProvidersDecodeIncludingNestedOnes() {
        IntProvider original = ClampedInt.of(UniformInt.of(2, 9), 3, 7);
        ClampedInt decoded = assertInstanceOf(ClampedInt.class, IntProvider.decode(original.asHandle()));

        assertEquals(3, decoded.minInclusive());
        assertEquals(7, decoded.maxInclusive());
        UniformInt source = assertInstanceOf(UniformInt.class, decoded.source());
        assertEquals(2, source.minInclusive());
        assertEquals(9, source.maxInclusive());
    }

    @Test
    void weightedListsDecodeThroughTheirElements() {
        WeightedListInt original = WeightedListInt.of(WeightedList.of(List.of(
            new WeightedList.Weighted<>(ConstantInt.of(4), 3),
            new WeightedList.Weighted<>(ConstantInt.of(8), 1)
        )));

        WeightedListInt decoded = assertInstanceOf(WeightedListInt.class, IntProvider.decode(original.asHandle()));
        List<WeightedList.Weighted<IntProvider>> entries = decoded.distribution().unwrap();

        assertEquals(2, entries.size());
        assertEquals(4, assertInstanceOf(ConstantInt.class, entries.getFirst().value()).minInclusive());
        assertEquals(3, entries.getFirst().weight());
    }

    @Test
    void floatProvidersDecode() {
        UniformFloat decoded = assertInstanceOf(UniformFloat.class, FloatProvider.decode(UniformFloat.of(1.5f, 4.5f).asHandle()));
        assertEquals(1.5f, decoded.minInclusive());
        assertEquals(4.5f, decoded.maxExclusive());
    }

    @Test
    void heightProvidersDecodeThroughTheirAnchors() {
        HeightProvider original = UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.belowTop(2));
        UniformHeight decoded = assertInstanceOf(UniformHeight.class, HeightProvider.decode(original.asHandle()));

        assertEquals(8, assertInstanceOf(dev.wyck.worldgen.heightproviders.AboveBottom.class, decoded.minInclusive()).offset());
        assertEquals(2, assertInstanceOf(dev.wyck.worldgen.heightproviders.BelowTop.class, decoded.maxInclusive()).offset());
    }

    @Test
    void constantHeightDecodesItsAnchor() {
        ConstantHeight decoded = assertInstanceOf(ConstantHeight.class,
            HeightProvider.decode(ConstantHeight.of(VerticalAnchor.absolute(64)).asHandle()));
        assertEquals(64, assertInstanceOf(dev.wyck.worldgen.heightproviders.Absolute.class, decoded.value()).y());
    }
}
