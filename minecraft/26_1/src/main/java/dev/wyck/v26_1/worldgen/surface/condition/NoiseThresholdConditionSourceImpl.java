package dev.wyck.v26_1.worldgen.surface.condition;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.surface.condition.NoiseThresholdConditionSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record NoiseThresholdConditionSourceImpl(
    @Override ResourceKey noise,
    @Override double minThreshold,
    @Override double maxThreshold,
    @Override boolean is3d
) implements NoiseThresholdConditionSource {
    @Override
    public Object toMinecraft() {
        if (this.is3d) {
            throw new IllegalArgumentException("3d surface noise conditions require Minecraft 26.2 or newer");
        }
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> key =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.NOISE, this.noise.identifier());
        return net.minecraft.world.level.levelgen.SurfaceRules.noiseCondition(key, this.minThreshold, this.maxThreshold);
    }
}
