package dev.wyck.worldgen.climate;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClimateParameterImpl(@Override double min, @Override double max) implements ClimateParameter {

    @Override
    public Object toMinecraft() {
        return net.minecraft.world.level.biome.Climate.Parameter.span((float) this.min, (float) this.max);
    }
}
