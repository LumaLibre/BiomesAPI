package dev.wyck.decode.worldgen.noise;

import dev.wyck.worldgen.noise.NoiseSettings;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NoiseSettingsDecoder implements Decodable<NoiseSettings, net.minecraft.world.level.levelgen.NoiseSettings> {
    @Override
    public NoiseSettings decode(net.minecraft.world.level.levelgen.NoiseSettings settings) {
        return NoiseSettings.of(settings.minY(), settings.height(), settings.noiseSizeHorizontal(), settings.noiseSizeVertical());
    }
}
