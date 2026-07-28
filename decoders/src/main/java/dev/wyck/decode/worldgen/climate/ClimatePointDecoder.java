package dev.wyck.decode.worldgen.climate;

import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.climate.ClimatePoint;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ClimatePointDecoder implements Decodable<ClimatePoint, Climate.ParameterPoint> {
    @Override
    public ClimatePoint decode(Climate.ParameterPoint point) {
        return ClimatePoint.of(
            ClimateParameter.decode(point.temperature()),
            ClimateParameter.decode(point.humidity()),
            ClimateParameter.decode(point.continentalness()),
            ClimateParameter.decode(point.erosion()),
            ClimateParameter.decode(point.depth()),
            ClimateParameter.decode(point.weirdness()),
            Climate.unquantizeCoord(point.offset())
        );
    }
}
