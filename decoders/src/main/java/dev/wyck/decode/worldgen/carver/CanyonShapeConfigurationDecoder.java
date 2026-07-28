package dev.wyck.decode.worldgen.carver;

import dev.wyck.worldgen.carver.CanyonCarverConfiguration;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CanyonShapeConfigurationDecoder implements Decodable<CanyonCarverConfiguration.CanyonShapeConfiguration, net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration> {
    @Override
    public CanyonCarverConfiguration.CanyonShapeConfiguration decode(net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration.CanyonShapeConfiguration shape) {
        return CanyonCarverConfiguration.CanyonShapeConfiguration.of(
            FloatProvider.decode(shape.distanceFactor),
            FloatProvider.decode(shape.thickness),
            shape.widthSmoothness,
            FloatProvider.decode(shape.horizontalRadiusFactor),
            shape.verticalRadiusDefaultFactor,
            shape.verticalRadiusCenterFactor
        );
    }
}
