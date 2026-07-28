package dev.wyck.worldgen.feature.configurations;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record FossilFeatureConfigurationImpl(
    List<ResourceKey> fossilStructures,
    List<ResourceKey> overlayStructures,
    ResourceKey fossilProcessors,
    ResourceKey overlayProcessors,
    int maxEmptyCornersAllowed
) implements FossilFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration(
            fossilStructures.stream().map(key -> (net.minecraft.resources.Identifier) key.asHandle()).toList(),
            overlayStructures.stream().map(key -> (net.minecraft.resources.Identifier) key.asHandle()).toList(),
            processors(fossilProcessors),
            processors(overlayProcessors),
            maxEmptyCornersAllowed
        );
    }

    private static Holder<StructureProcessorList> processors(ResourceKey key) {
        net.minecraft.resources.ResourceKey<StructureProcessorList> nmsKey = net.minecraft.resources.ResourceKey.create(Registries.PROCESSOR_LIST, key.asHandle());
        return BootstrapSafeMinecraftRegistries.getter(Registries.PROCESSOR_LIST).getOrThrow(nmsKey);
    }
}
