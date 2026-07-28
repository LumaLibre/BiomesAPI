package dev.wyck.decode.worldgen.feature.geode;

import dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettings;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class GeodeLayerSettingsDecoder implements Decodable<GeodeLayerSettings, net.minecraft.world.level.levelgen.GeodeLayerSettings> {
    @Override
    public GeodeLayerSettings decode(net.minecraft.world.level.levelgen.GeodeLayerSettings settings) {
        return GeodeLayerSettings.of(settings.filling, settings.innerLayer, settings.middleLayer, settings.outerLayer);
    }
}
