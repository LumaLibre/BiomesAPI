package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public class ShiftedNoise2dFunctionImpl extends NoiseParameterFunctionImpl implements ShiftedNoise2dFunction {

    private final DensityFunction shiftX;
    private final DensityFunction shiftZ;
    private final double xzScale;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ShiftedNoise2dFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters, DensityFunction shiftX, DensityFunction shiftZ, double xzScale) {
        super(resourceKey, noiseParameters);
        this.shiftX = shiftX;
        this.shiftZ = shiftZ;
        this.xzScale = xzScale;
    }

    @Override
    public DensityFunction shiftX() {
        return this.shiftX;
    }

    @Override
    public DensityFunction shiftZ() {
        return this.shiftZ;
    }

    @Override
    public double xzScale() {
        return this.xzScale;
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction unwrappedX = this.shiftX.asHandle();
        net.minecraft.world.level.levelgen.DensityFunction unwrappedZ = this.shiftZ.asHandle();

        return net.minecraft.world.level.levelgen.DensityFunctions.shiftedNoise2d(
            unwrappedX,
            unwrappedZ,
            this.xzScale,
            this.noiseData()
        );
    }
}
