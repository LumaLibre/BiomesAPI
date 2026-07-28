package dev.wyck.decode.worldgen.synth;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class NoiseParametersDecoders extends DecoderRegistry<NoiseParameters, Object> {

    public static final ResourceKey REFERENCE = ResourceKey.wyck("reference");
    public static final ResourceKey COMPOSED = ResourceKey.wyck("composed");

    public NoiseParametersDecoders() {
        register(REFERENCE, minecraftObject -> {
            Holder.Reference<?> holder = (Holder.Reference<?>) minecraftObject;
            return NoiseParameters.reference(Decoders.key(holder.key()));
        });
        register(COMPOSED, minecraftObject -> {
            NormalNoise.NoiseParameters parameters = minecraftObject instanceof Holder<?> holder
                ? (NormalNoise.NoiseParameters) holder.value()
                : (NormalNoise.NoiseParameters) minecraftObject;
            return NoiseParameters.of(parameters.firstOctave(), List.copyOf(parameters.amplitudes()));
        });
    }

    @Override
    protected Object normalize(Object minecraftObject) {
        if (minecraftObject instanceof DensityFunction.NoiseHolder holder) {
            return holder.noiseData();
        }
        return minecraftObject;
    }

    @Override
    protected ResourceKey discriminate(Object minecraftObject) {
        return minecraftObject instanceof Holder.Reference<?> ? REFERENCE : COMPOSED;
    }
}
