package dev.wyck.decode.worldgen.valueproviders;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.valueproviders.ClampedNormalFloat;
import dev.wyck.worldgen.valueproviders.ConstantFloat;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.valueproviders.TrapezoidFloat;
import dev.wyck.worldgen.valueproviders.UniformFloat;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FloatProviderDecoders extends DecoderRegistry<FloatProvider, net.minecraft.util.valueproviders.FloatProvider> {

    public FloatProviderDecoders() {
        register("constant", nms -> ConstantFloat.of(((net.minecraft.util.valueproviders.ConstantFloat) nms).value()));
        register("uniform", nms -> {
            var uniform = (net.minecraft.util.valueproviders.UniformFloat) nms;
            return UniformFloat.of(uniform.min(), uniform.max());
        });
        register("clamped_normal", nms -> {
            var normal = (net.minecraft.util.valueproviders.ClampedNormalFloat) nms;
            return ClampedNormalFloat.of(normal.min(), normal.max(), normal.mean(), normal.deviation());
        });
        register("trapezoid", nms -> {
            var trapezoid = (net.minecraft.util.valueproviders.TrapezoidFloat) nms;
            return TrapezoidFloat.of(trapezoid.min(), trapezoid.max(), trapezoid.plateau());
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.util.valueproviders.FloatProvider provider) {
        return Decoders.registryKey(BuiltInRegistries.FLOAT_PROVIDER_TYPE, provider.codec());
    }
}
