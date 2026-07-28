package dev.wyck.decode.worldgen.feature.tree;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.featuresize.ThreeLayersFeatureSize;
import dev.wyck.worldgen.feature.featuresize.TwoLayersFeatureSize;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

@NullMarked
@ApiStatus.Internal
public final class FeatureSizeDecoders extends DecoderRegistry<FeatureSize, net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize> {

    public FeatureSizeDecoders() {
        register("two_layers_feature_size", size -> TwoLayersFeatureSize.of(
            minimum(size),
            field(size, "limit"),
            field(size, "lowerSize"),
            field(size, "upperSize")
        ));
        register("three_layers_feature_size", size -> ThreeLayersFeatureSize.of(
            minimum(size),
            field(size, "limit"),
            field(size, "upperLimit"),
            field(size, "lowerSize"),
            field(size, "middleSize"),
            field(size, "upperSize")
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize minecraftObject) {
        FeatureSizeType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.FEATURE_SIZE_TYPE, type);
    }

    private static @Nullable Integer minimum(Object size) {
        OptionalInt minimum = ((net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize) size)
            .minClippedHeight();
        return minimum.isPresent() ? minimum.getAsInt() : null;
    }

    private static int field(Object size, String name) {
        return FastReflection.read(size, name);
    }
}
