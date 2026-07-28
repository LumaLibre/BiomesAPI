package dev.wyck.decode.worldgen.carver;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.carver.CarverDebugSettings;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CarverDebugSettingsDecoder implements Decodable<CarverDebugSettings, net.minecraft.world.level.levelgen.carver.CarverDebugSettings> {
    @Override
    public CarverDebugSettings decode(net.minecraft.world.level.levelgen.carver.CarverDebugSettings settings) {
        return CarverDebugSettings.of(
            settings.isDebugMode(),
            Decoders.blockData(settings.getAirState()),
            Decoders.blockData(settings.getWaterState()),
            Decoders.blockData(settings.getLavaState()),
            Decoders.blockData(settings.getBarrierState())
        );
    }
}
