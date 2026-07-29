package dev.wyck.test.bootstrap.decode;

import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.worldgen.noise.types.NoiseGeneratorSettings;
import dev.wyck.worldgen.noise.types.ReferencedNoise;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MinecraftBootstrap.class)
class NoiseSettingsDecodeTest {

    @Test
    @Disabled("not yet fixed")
    void referencesAndEveryVanillaInlineNoiseSettingDecode() {
        var registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS);
        registry.entrySet().forEach(entry -> {
            assertInstanceOf(ReferencedNoise.class, Noise.decode(registry.get(entry.getKey()).orElseThrow()));
            NoiseGeneratorSettings decoded = assertInstanceOf(NoiseGeneratorSettings.class,
                Noise.decode(Holder.direct(entry.getValue())));
            assertEquals(entry.getValue().seaLevel(), decoded.seaLevel());
            assertEquals(entry.getValue().spawnTarget().size(), decoded.spawnTarget().size());
        });
    }
}
