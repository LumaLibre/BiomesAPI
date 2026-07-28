package dev.wyck.decode.worldgen.feature;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.FeatureType;
import dev.wyck.worldgen.feature.configurations.FeatureConfiguration;
import dev.wyck.worldgen.feature.types.ComposedConfiguredFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

@NullMarked
@ApiStatus.Internal
public final class ConfiguredFeatureDecoder implements Decodable<ConfiguredFeature, Object> {

    @Override
    public ConfiguredFeature decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return ConfiguredFeature.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?> configured = Decoders.value(minecraftObject);
        return ComposedConfiguredFeature.of(
            featureType(configured.feature()),
            FeatureConfiguration.decode(configured)
        );
    }

    private static FeatureType featureType(net.minecraft.world.level.levelgen.feature.Feature<?> feature) {
        ResourceKey key = Decoders.registryKey(
            net.minecraft.core.registries.BuiltInRegistries.FEATURE, feature
        );
        return Arrays.stream(FeatureType.values())
            .filter(type -> type.resourceKey().equals(key))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No Wyck feature type maps to '" + key + "'"));
    }
}
