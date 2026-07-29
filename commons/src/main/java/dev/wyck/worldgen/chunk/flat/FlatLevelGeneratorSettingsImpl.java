package dev.wyck.worldgen.chunk.flat;

import com.google.common.base.Suppliers;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@NullMarked
@ApiStatus.Internal
public record FlatLevelGeneratorSettingsImpl(
    @Override List<FlatLayerInfo> layers,
    @Override boolean decoration,
    @Override boolean addLakes,
    @Override Biome biome,
    @Override Biome fallbackBiome,
    @Override List<PlacedFeature> lakes,
    @Override Optional<Set<ResourceKey>> structures
) implements FlatLevelGeneratorSettings {

    @Override
    public Object toMinecraft() {
        net.minecraft.core.HolderGetter<net.minecraft.world.level.levelgen.structure.StructureSet> structureSets = BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.STRUCTURE_SET);
        net.minecraft.core.Registry<net.minecraft.world.level.biome.Biome> biomes =
            BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.BIOME);

        Optional<net.minecraft.core.HolderSet<net.minecraft.world.level.levelgen.structure.StructureSet>> structuresHolder = this.structures.map(structs ->
            net.minecraft.core.HolderSet.direct(structs.stream().map(it ->
                structureSets.getOrThrow(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.STRUCTURE_SET, it.identifier()))
            ).toList())
        );

        net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biomeHolder = biomes
            .get(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, biome.resourceKey().identifier()))
            .orElseGet(() -> biomes.getOrThrow(net.minecraft.resources.ResourceKey.create(
                net.minecraft.core.registries.Registries.BIOME,
                fallbackBiome.resourceKey().identifier()
            )));

        // Underlying impl is awkward, so we have to do it ourselves.
        net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings gen = new DecorationAwareSettings(
            structuresHolder,
            biomeHolder,
            lakes.stream().map(it -> net.minecraft.core.Holder.direct(it.<net.minecraft.world.level.levelgen.placement.PlacedFeature>asHandle())).toList(),
            decoration
        );

        if (addLakes) {
            gen.setAddLakes();
        }

        if (decoration) {
            gen.setDecoration();
        }

        gen.getLayersInfo().addAll(layers.stream().map(Wrapper::<net.minecraft.world.level.levelgen.flat.FlatLayerInfo>asHandle).toList());
        gen.updateLayers();
        return gen;
    }

    private static final class DecorationAwareSettings extends net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings {

        private final boolean decoration;
        private final Supplier<net.minecraft.world.level.biome.@Nullable BiomeGenerationSettings> undecorated = Suppliers.memoize(this::undecorated);

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private DecorationAwareSettings(
            Optional<net.minecraft.core.HolderSet<net.minecraft.world.level.levelgen.structure.StructureSet>> structureOverrides,
            net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> biome,
            List<net.minecraft.core.Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature>> lakes,
            boolean decoration
        ) {
            super(structureOverrides, biome, lakes);
            this.decoration = decoration;
        }

        @Override
        public net.minecraft.world.level.biome.BiomeGenerationSettings adjustGenerationSettings(net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome> sourceBiome) {
            if (this.decoration) {
                return super.adjustGenerationSettings(sourceBiome);
            }
            return this.undecorated.get();
        }

        private net.minecraft.world.level.biome.BiomeGenerationSettings undecorated() {
            return super.adjustGenerationSettings(this.getBiome());
        }
    }

}
