package dev.wyck.decode.worldgen.climate;

import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ClimateParameterDecoder implements Decodable<ClimateParameter, Climate.Parameter> {
    @Override
    public ClimateParameter decode(Climate.Parameter parameter) {
        return ClimateParameter.span(Climate.unquantizeCoord(parameter.min()), Climate.unquantizeCoord(parameter.max()));
    }
}
