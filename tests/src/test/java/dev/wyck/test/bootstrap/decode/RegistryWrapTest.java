package dev.wyck.test.bootstrap.decode;

import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.worldgen.noise.types.NoiseGeneratorSettings;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MinecraftBootstrap.class)
class RegistryWrapTest {

    @Test
    void everyReferenceFactoryHasAMatchingWrapMethod() throws ReflectiveOperationException {
        for (Class<?> type : List.of(
            Biome.class, Dimension.class, Timeline.class, Noise.class, ConfiguredWorldCarver.class,
            DensityFunction.class, NoiseParameters.class, ConfiguredFeature.class, PlacedFeature.class
        )) {
            assertNotNull(type.getDeclaredMethod("reference", ResourceKey.class));
            assertNotNull(type.getDeclaredMethod("wrap"));
        }
    }

    @Test
    void wrapResolvesRegistryValuesAndRunsTheirDecoders() {
        assertEquals(ResourceKey.minecraft("plains"), Biome.reference(ResourceKey.minecraft("plains")).wrap().resourceKey());
        assertEquals(ResourceKey.minecraft("overworld"), Dimension.reference(ResourceKey.minecraft("overworld")).wrap().resourceKey());
        assertEquals(ResourceKey.minecraft("day"), Timeline.reference(ResourceKey.minecraft("day")).wrap().key());

        assertInstanceOf(NoiseGeneratorSettings.class, Noise.reference(ResourceKey.minecraft("overworld")).wrap());
        assertInstanceOf(NoiseParameters.class, NoiseParameters.reference(ResourceKey.minecraft("temperature")).wrap());
        assertInstanceOf(ConfiguredFeature.class, ConfiguredFeature.reference(ResourceKey.minecraft("seagrass_short")).wrap());
        assertInstanceOf(PlacedFeature.Composed.class, PlacedFeature.reference(ResourceKey.minecraft("seagrass_warm")).wrap());
        assertInstanceOf(DensityFunction.class, DensityFunction.reference(ResourceKey.minecraft("overworld/continents")).wrap());
    }

    @Test
    void wrapRejectsMissingRegistryKeys() {
        assertThrows(IllegalStateException.class, () -> Biome.reference(ResourceKey.wyck("missing")).wrap());
    }
}
