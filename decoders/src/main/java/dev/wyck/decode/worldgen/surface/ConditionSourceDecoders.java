package dev.wyck.decode.worldgen.surface;

import dev.wyck.biome.Biome;
import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.surface.condition.AbovePreliminarySurfaceConditionSource;
import dev.wyck.worldgen.surface.condition.BiomeConditionSource;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.worldgen.surface.condition.HoleConditionSource;
import dev.wyck.worldgen.surface.condition.NoiseThresholdConditionSource;
import dev.wyck.worldgen.surface.condition.NotConditionSource;
import dev.wyck.worldgen.surface.condition.OptionallyFlatBedrockConditionSource;
import dev.wyck.worldgen.surface.condition.SteepConditionSource;
import dev.wyck.worldgen.surface.condition.StoneDepthConditionSource;
import dev.wyck.worldgen.surface.condition.TemperatureConditionSource;
import dev.wyck.worldgen.surface.condition.VerticalGradientConditionSource;
import dev.wyck.worldgen.surface.condition.WaterConditionSource;
import dev.wyck.worldgen.surface.condition.YAboveConditionSource;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ConditionSourceDecoders extends DecoderRegistry<ConditionSource, net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource> {

    public ConditionSourceDecoders() {
        register("biome", source -> BiomeConditionSource.of(biomes(source)));
        register("noise_threshold", source -> NoiseThresholdConditionSource.of(
            Decoders.key(FastReflection.<net.minecraft.resources.ResourceKey<?>>read(source, "noise")),
            FastReflection.read(source, "minThreshold"),
            FastReflection.read(source, "maxThreshold"),
            FastReflection.read(source, "is3d")
        ));
        register("vertical_gradient", source -> VerticalGradientConditionSource.of(
            FastReflection.<Identifier>read(source, "randomName").toString(),
            VerticalAnchor.decode(FastReflection.read(source, "trueAtAndBelow")),
            VerticalAnchor.decode(FastReflection.read(source, "falseAtAndAbove"))
        ));
        register("y_above", source -> YAboveConditionSource.of(
            VerticalAnchor.decode(FastReflection.read(source, "anchor")),
            FastReflection.read(source, "surfaceDepthMultiplier"),
            FastReflection.read(source, "addStoneDepth")
        ));
        register("water", source -> WaterConditionSource.of(
            FastReflection.read(source, "offset"),
            FastReflection.read(source, "surfaceDepthMultiplier"),
            FastReflection.read(source, "addStoneDepth")
        ));
        register("temperature", _ -> TemperatureConditionSource.INSTANCE);
        register("steep", _ -> SteepConditionSource.INSTANCE);
        register("not", source -> NotConditionSource.of(ConditionSource.decode(
            FastReflection.read(source, "target")
        )));
        register("hole", _ -> HoleConditionSource.INSTANCE);
        register("above_preliminary_surface", _ -> AbovePreliminarySurfaceConditionSource.INSTANCE);
        register("stone_depth", source -> StoneDepthConditionSource.of(
            FastReflection.read(source, "offset"),
            FastReflection.read(source, "addSurfaceDepth"),
            FastReflection.read(source, "secondaryDepthRange"),
            CaveSurface.TRANSLATOR.fromNms(FastReflection.read(source, "surfaceType"))
        ));
        register(ResourceKey.of("paper", "optionally_flat_bedrock_condition_source"), source -> {
            io.papermc.paper.world.worldgen.OptionallyFlatBedrockConditionSource condition =
                (io.papermc.paper.world.worldgen.OptionallyFlatBedrockConditionSource) source;
            return OptionallyFlatBedrockConditionSource.of(
                Decoders.key(condition.randomName()),
                VerticalAnchor.decode(condition.trueAtAndBelow()),
                VerticalAnchor.decode(condition.falseAtAndAbove()),
                condition.isRoof()
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource source) {
        return Decoders.registryKey(BuiltInRegistries.MATERIAL_CONDITION, source.codec());
    }

    private static java.util.List<Biome> biomes(Object source) {
        HolderSet<net.minecraft.world.level.biome.Biome> holders = FastReflection.read(source, "biomes");
        return holders.stream()
            .map(holder -> Biome.reference(Decoders.referenceKey(holder)))
            .toList();
    }
}
