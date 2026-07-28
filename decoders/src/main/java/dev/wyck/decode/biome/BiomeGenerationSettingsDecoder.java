package dev.wyck.decode.biome;

import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class BiomeGenerationSettingsDecoder implements Decodable<BiomeGenerationSettings, net.minecraft.world.level.biome.BiomeGenerationSettings> {

    @Override
    public BiomeGenerationSettings decode(net.minecraft.world.level.biome.BiomeGenerationSettings settings) {
        List<ConfiguredWorldCarver> carvers = new ArrayList<>();
        for (Holder<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> carver : settings.getCarvers()) {
            carvers.add(ConfiguredWorldCarver.decode(carver));
        }

        Decoration[] steps = Decoration.values();
        List<HolderSet<net.minecraft.world.level.levelgen.placement.PlacedFeature>> minecraftFeatures = settings.features();
        if (minecraftFeatures.size() > steps.length) {
            throw new IllegalArgumentException("Minecraft has " + minecraftFeatures.size()
                + " decoration steps, but Wyck only knows " + steps.length);
        }

        Map<Decoration, List<PlacedFeature>> features = new EnumMap<>(Decoration.class);
        for (int index = 0; index < minecraftFeatures.size(); index++) {
            List<PlacedFeature> stepFeatures = minecraftFeatures.get(index).stream()
                .map(PlacedFeature::decode)
                .toList();
            if (!stepFeatures.isEmpty()) {
                features.put(steps[index], stepFeatures);
            }
        }
        return BiomeGenerationSettings.of(carvers, features);
    }
}
