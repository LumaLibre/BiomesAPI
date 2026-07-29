package dev.wyck.worldgen.biome.custom;

import dev.wyck.worldgen.climate.ClimateParameter;
import dev.wyck.worldgen.climate.ClimatePoint;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class BiomeSourceContextImpl implements BiomeSourceContext {

    private final int quartX;
    private final int quartY;
    private final int quartZ;
    private final Climate.Sampler sampler;
    private @Nullable ClimatePoint climateBounds;
    private @Nullable List<ClimatePoint> spawnTarget;

    BiomeSourceContextImpl(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
        this.quartX = quartX;
        this.quartY = quartY;
        this.quartZ = quartZ;
        this.sampler = sampler;
    }

    @Override
    public int quartX() {
        return this.quartX;
    }

    @Override
    public int quartY() {
        return this.quartY;
    }

    @Override
    public int quartZ() {
        return this.quartZ;
    }

    @Override
    public ClimatePoint climateBounds() {
        ClimatePoint point = this.climateBounds;
        if (point == null) {
            point = ClimatePoint.of(
                parameter(this.sampler.temperature()),
                parameter(this.sampler.humidity()),
                parameter(this.sampler.continentalness()),
                parameter(this.sampler.erosion()),
                parameter(this.sampler.depth()),
                parameter(this.sampler.weirdness()),
                0.0F
            );
            this.climateBounds = point;
        }
        return point;
    }

    @Override
    public List<ClimatePoint> spawnTarget() {
        List<ClimatePoint> points = this.spawnTarget;
        if (points == null) {
            points = this.sampler.spawnTarget().stream()
                .map(BiomeSourceContextImpl::climatePoint)
                .toList();
            this.spawnTarget = points;
        }
        return points;
    }

    private static ClimatePoint climatePoint(Climate.ParameterPoint point) {
        return ClimatePoint.of(
            parameter(point.temperature()),
            parameter(point.humidity()),
            parameter(point.continentalness()),
            parameter(point.erosion()),
            parameter(point.depth()),
            parameter(point.weirdness()),
            Climate.unquantizeCoord(point.offset())
        );
    }

    private static ClimateParameter parameter(Climate.Parameter parameter) {
        return ClimateParameter.span(
            Climate.unquantizeCoord(parameter.min()),
            Climate.unquantizeCoord(parameter.max())
        );
    }

    private static ClimateParameter parameter(DensityFunction function) {
        return ClimateParameter.span(function.minValue(), function.maxValue());
    }
}
