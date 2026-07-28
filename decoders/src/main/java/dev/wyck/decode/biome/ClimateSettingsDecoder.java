package dev.wyck.decode.biome;

import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.TemperatureModifier;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ClimateSettingsDecoder implements Decodable<ClimateSettings, net.minecraft.world.level.biome.Biome.ClimateSettings> {

    @Override
    public ClimateSettings decode(net.minecraft.world.level.biome.Biome.ClimateSettings settings) {
        return ClimateSettings.of(
            settings.hasPrecipitation(),
            settings.temperature(),
            TemperatureModifier.TRANSLATOR.fromNms(settings.temperatureModifier()),
            settings.downfall()
        );
    }
}
