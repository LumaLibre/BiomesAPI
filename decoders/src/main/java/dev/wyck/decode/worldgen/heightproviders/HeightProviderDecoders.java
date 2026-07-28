package dev.wyck.decode.worldgen.heightproviders;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.heightproviders.BiasedToBottomHeight;
import dev.wyck.worldgen.heightproviders.ConstantHeight;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.TrapezoidHeight;
import dev.wyck.worldgen.heightproviders.UniformHeight;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.heightproviders.VeryBiasedToBottomHeight;
import dev.wyck.worldgen.heightproviders.WeightedListHeight;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.WeightedList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class HeightProviderDecoders extends DecoderRegistry<HeightProvider, net.minecraft.world.level.levelgen.heightproviders.HeightProvider> {

    public HeightProviderDecoders() {
        register("constant", nms -> ConstantHeight.of(anchor(nms, "value")));
        register("uniform", nms -> UniformHeight.of(anchor(nms, "minInclusive"), anchor(nms, "maxInclusive")));
        register("biased_to_bottom", nms -> BiasedToBottomHeight.of(anchor(nms, "minInclusive"), anchor(nms, "maxInclusive"), FastReflection.<Integer>read(nms, "inner")));
        register("very_biased_to_bottom", nms -> VeryBiasedToBottomHeight.of(anchor(nms, "minInclusive"), anchor(nms, "maxInclusive"), FastReflection.<Integer>read(nms, "inner")));
        register("trapezoid", nms -> TrapezoidHeight.of(anchor(nms, "minInclusive"), anchor(nms, "maxInclusive"), FastReflection.<Integer>read(nms, "plateau")));
        register("weighted_list", nms -> {
            WeightedList<net.minecraft.world.level.levelgen.heightproviders.HeightProvider> distribution =
                FastReflection.read(nms, "distribution");
            return WeightedListHeight.of(Decoders.weighted(distribution, HeightProvider::decode));
        });
    }

    // Every height provider keeps its anchors in private fields with no accessors.
    private static VerticalAnchor anchor(Object provider, String field) {
        return VerticalAnchor.decode(FastReflection.read(provider, field));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.heightproviders.HeightProvider provider) {
        return Decoders.registryKey(BuiltInRegistries.HEIGHT_PROVIDER_TYPE, provider.getType());
    }
}
