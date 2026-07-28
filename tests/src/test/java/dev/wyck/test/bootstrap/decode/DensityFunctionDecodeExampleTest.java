package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.noise.ShiftedNoise2dFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MinecraftBootstrap.class)
class DensityFunctionDecodeExampleTest {

    @Test
    void readVanillaOverworldTemperature() {
        NoiseGeneratorSettings overworld = BootstrapSafeMinecraftRegistries
            .mappedRegistry(Registries.NOISE_SETTINGS)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(
                Registries.NOISE_SETTINGS, ResourceKey.minecraft("overworld").identifier()))
            .value();

        DensityFunction temperature = DensityFunction.decode(overworld.noiseRouter().temperature());

        ShiftedNoise2dFunction noise = assertInstanceOf(ShiftedNoise2dFunction.class, temperature);
        assertEquals(0.25, noise.xzScale());

        assertEquals(ResourceKey.minecraft("temperature"), noise.noiseParameters().resourceKey().orElseThrow());
        assertEquals(ResourceKey.minecraft("shift_x"), noise.shiftX().resourceKey().orElseThrow());
        assertEquals(ResourceKey.minecraft("shift_z"), noise.shiftZ().resourceKey().orElseThrow());

        TwoArgumentSimpleFunction warmer = temperature.add(DensityFunction.constant(0.15));
        assertEquals(TwoArgumentSimpleFunction.Operation.ADD, warmer.operation());
    }
}
