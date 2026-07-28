package dev.wyck.decode.worldgen.chunk;

import dev.wyck.biome.Biome;
import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.chunk.flat.FlatLayerInfo;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class FlatLevelGeneratorSettingsDecoder implements Decodable<FlatLevelGeneratorSettings, net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings> {
    @Override
    public FlatLevelGeneratorSettings decode(net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings settings) {
        Biome biome = Biome.reference(Decoders.referenceKey(settings.getBiome()));
        return FlatLevelGeneratorSettings.of(
            settings.getLayersInfo().stream().map(FlatLayerInfo::decode).toList(),
            FastReflection.read(settings, "decoration"),
            FastReflection.read(settings, "addLakes"),
            biome,
            biome,
            lakes(settings),
            structures(settings.structureOverrides())
        );
    }

    private static List<PlacedFeature> lakes(Object settings) {
        List<Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>> holders =
            FastReflection.read(settings, "lakes");
        return holders.stream().map(PlacedFeature::decode).toList();
    }

    private static Set<ResourceKey> structures(Optional<HolderSet<net.minecraft.world.level.levelgen.structure.StructureSet>> holders) {
        if (holders.isEmpty()) return Set.of();
        return holders.get().stream()
            .map(holder -> Decoders.referenceKey(holder))
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }
}
