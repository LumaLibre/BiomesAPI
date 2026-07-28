package dev.wyck.codegen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.PileFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.EasingType;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.Noises;
import org.jspecify.annotations.Nullable;

import java.util.List;

public final class Generators {

    public static final List<GeneratorSpec> ALL = List.of(
        configuredFeatures(),
        placedFeatures(),
        densityFunctions(),
        noiseParameters(),
        biomeKeys(),
        dimensionKeys(),
        decorations(),
        heightmapTypes(),
        caveSurfaces(),
        rotations(),
        tristates(),
        skyboxes(),
        cardinalLightTypes(),
        moonPhases(),
        grassColorModifiers(),
        temperatureModifiers(),
        mobCategories(),
        fluidTypes(),
        featureTypes(),
        worldCarverTypes(),
        activities(),
        structureSets(),
        carvers(),
        environmentAttributeOperationIds(),
        easingTypes()
    );

    private static GeneratorSpec configuredFeatures() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.feature",
            "ConfiguredFeatures",
            "ConfiguredFeature",
            "ConfiguredFeature.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                AquaticFeatures.class,
                CaveFeatures.class,
                EndFeatures.class,
                MiscOverworldFeatures.class,
                NetherFeatures.class,
                OreFeatures.class,
                PileFeatures.class,
                TreeFeatures.class,
                VegetationFeatures.class
            ),
            "Typed references that point to vanilla's configured features.",
            "2.3.0",
            "CONFIGURED_FEATURES"
        );
    }

    private static GeneratorSpec placedFeatures() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.placement",
            "PlacedFeatures",
            "PlacedFeature",
            "PlacedFeature.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                AquaticPlacements.class,
                CavePlacements.class,
                EndPlacements.class,
                MiscOverworldPlacements.class,
                NetherPlacements.class,
                OrePlacements.class,
                TreePlacements.class,
                VegetationPlacements.class,
                VillagePlacements.class
            ),
            "Typed references that point to vanilla's placed features.",
            "2.3.0",
            "PLACED_FEATURES"
        );
    }

    private static GeneratorSpec densityFunctions() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.function",
            "DensityFunctions",
            "DensityFunction",
            "DensityFunction.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                NoiseRouterData.class
            ),
            "Typed references that point to vanilla's density functions.",
            "2.4.0",
            "DENSITY_FUNCTIONS"
        );
    }

    private static GeneratorSpec noiseParameters() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.synth",
            "Noises",
            "NoiseParameters",
            "NoiseParameters.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                Noises.class
            ),
            "Typed references that point to vanilla's noise parameters.",
            "2.4.0",
            "NOISE"
        );
    }

    private static GeneratorSpec biomeKeys() {
        return new ReferenceSpec(
            "dev.wyck.biome",
            "Biomes",
            "Biome",
            "Biome.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                Biomes.class
            ),
            "Typed references that point to vanilla's biomes.",
            "3.0.0",
            "BIOMES"
        );
    }

    private static GeneratorSpec dimensionKeys() {
        return new ReferenceSpec(
            "dev.wyck.level.dimension",
            "Dimensions",
            "Dimension",
            "Dimension.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                BuiltinDimensionTypes.class
            ),
            "Typed references that point to vanilla's dimensions.",
            "3.0.0",
            "DIMENSIONS"
        );
    }

    private static GeneratorSpec decorations() {
        return new EnumSpec(
            "dev.wyck.worldgen",
            "Decoration",
            GenerationStep.Decoration.class,
            Generators::enumName,
            "Wraps Minecraft's {@code GenerationStep.Decoration}.",
            "2.3.0"
        );
    }

    private static GeneratorSpec heightmapTypes() {
        return new EnumSpec(
            "dev.wyck.worldgen",
            "HeightmapType",
            Heightmap.Types.class,
            Generators::enumName,
            "Wraps Minecraft's Heightmap.Types.",
            "3.0.0"
        );
    }

    private static GeneratorSpec caveSurfaces() {
        return new EnumSpec(
            "dev.wyck.worldgen.surface.condition",
            "CaveSurface",
            CaveSurface.class,
            List.of(
                "Which face of the surface a {@link StoneDepthConditionSource stone-depth} check measures from.",
                "{@link #FLOOR}, blocks are placed based on the distance to the surface above; if {@link #CEILING},",
                "the distance to the surface below is used instead.",
                "",
                "@see <a href=\"https://minecraft.wiki/w/Surface_rule#Surface_conditions\">Surface rule (surface conditions)</a>"
            ),
            "3.0.0"
        );
    }

    private static GeneratorSpec rotations() {
        return new EnumSpec(
            "dev.wyck.worldgen",
            "Rotation",
            Rotation.class,
            Generators::enumName,
            List.of(
                "A rotation applied to a structure template, in 90-degree steps about the Y axis.",
                "",
                "@see <a href=\"https://minecraft.wiki/w/Template_pool\">Template pool</a>"
            ),
            "3.0.1"
        );
    }

    private static GeneratorSpec tristates() {
        return new EnumSpec(
            "dev.wyck.environment",
            "TriState",
            TriState.class,
            "Represents a tri-state value, which can be true, false, or default.",
            "2.4.1"
        );
    }

    private static GeneratorSpec skyboxes() {
        return new EnumSpec(
            "dev.wyck.level.dimension",
            "Skybox",
            DimensionType.Skybox.class,
            Generators::serializedName,
            "Skybox type, as it appears in Minecraft.",
            "2.4.0"
        );
    }

    private static GeneratorSpec cardinalLightTypes() {
        return new EnumSpec(
            "dev.wyck.level.dimension",
            "CardinalLightType",
            CardinalLighting.Type.class,
            Generators::serializedName,
            "Cardinal light type, as it appears in Minecraft.",
            "2.4.0"
        );
    }

    private static GeneratorSpec moonPhases() {
        return new EnumSpec(
            "dev.wyck.environment",
            "MoonPhase",
            MoonPhase.class,
            Generators::serializedName,
            List.of(
                "This enum represents the phases of the moon in Minecraft.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS MoonPhase value."
            ),
            "1.1.0"
        );
    }

    private static GeneratorSpec grassColorModifiers() {
        return new EnumSpec(
            "dev.wyck.environment",
            "GrassColorModifier",
            BiomeSpecialEffects.GrassColorModifier.class,
            Generators::serializedName,
            List.of(
                "This enum represents the grass color modifier for a biome in Minecraft.",
                "It includes three values: NONE, SWAMP, and DARK_FOREST, which correspond to the GrassColorModifier values in the BiomeSpecialEffects class in the Minecraft code.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value."
            ),
            "0.0.24"
        );
    }

    private static GeneratorSpec temperatureModifiers() {
        return new EnumSpec(
            "dev.wyck.biome",
            "TemperatureModifier",
            Biome.TemperatureModifier.class,
            Generators::serializedName,
            List.of(
                "This enum represents the temperature modifier for a biome in Minecraft.",
                "It includes two values: NONE and FROZEN, which correspond to the TemperatureModifier values in the Biome class in the Minecraft code.",
                "Each enum value carries a vanilla key, retrievable via {@link #getKey()}, which the impl module translates to the underlying NMS value."
            ),
            "0.0.1"
        );
    }

    private static GeneratorSpec mobCategories() {
        return new EnumSpec(
            "dev.wyck.biome.entity",
            "MobCategory",
            MobCategory.class,
            Generators::serializedName,
            "Represents the category of a mob in Minecraft.",
            "2.3.0"
        );
    }

    private static GeneratorSpec fluidTypes() {
        return new ConstantSpec(
            "dev.wyck.worldgen.material",
            "FluidType",
            Fluid.class,
            Generators::fluidLocation,
            List.of(
                Fluids.class
            ),
            "FLUID",
            "The vanilla fluids a block predicate can match against.",
            "2.3.0"
        );
    }

    private static GeneratorSpec featureTypes() {
        return new ConstantSpec(
            "dev.wyck.worldgen.feature",
            "FeatureType",
            Feature.class,
            Generators::featureLocation,
            List.of(
                Feature.class
            ),
            "FEATURE",
            "Typed references to the built-in feature types, the algorithms in the {@code FEATURE} registry.",
            "2.3.0"
        );
    }

    private static GeneratorSpec worldCarverTypes() {
        return new ConstantSpec(
            "dev.wyck.worldgen.carver",
            "WorldCarverType",
            WorldCarver.class,
            Generators::worldCarverLocation,
            List.of(
                WorldCarver.class
            ),
            "CARVER",
            "The vanilla world-carver algorithms that a configured carver can be built on.",
            "2.3.0"
        );
    }

    private static GeneratorSpec activities() {
        return new ConstantSpec(
            "dev.wyck.environment",
            "Activity",
            Activity.class,
            Generators::activityLocation,
            List.of(
                Activity.class
            ),
            "ACTIVITY",
            List.of(
                "This enum represents the various activities that entities in Minecraft can perform.",
                "Each enum value carries a vanilla key which the impl module translates to the underlying NMS Activity value."
            ),
            "1.1.0"
        );
    }

    private static GeneratorSpec structureSets() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.structure",
            "StructureSets",
            "ResourceKey",
            "",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                BuiltinStructureSets.class
            ),
            "Typed references that point to vanilla's built-in structure sets.",
            "3.0.0"
        );
    }

    private static GeneratorSpec carvers() {
        return new ReferenceSpec(
            "dev.wyck.worldgen.carver",
            "Carvers",
            "ConfiguredWorldCarver",
            "ConfiguredWorldCarver.reference",
            ResourceKey.class,
            Generators::keyLocation,
            List.of(
                Carvers.class
            ),
            "Typed references to the built-in configured carvers registered by vanilla.",
            "2.3.0"
        );
    }

    private static GeneratorSpec easingTypes() {
        return new ReferenceSpec(
            "dev.wyck.level.timeline",
            "EasingType",
            "Easing",
            "Easing.of",
            EasingType.class,
            Generators::easingLocation,
            List.of(EasingType.class),
            "The easing functions vanilla registers for timeline keyframe tracks.\n"
                + "Inherited by {@link dev.wyck.level.timeline.Easing}, which is where these are used from.",
            "3.2.0"
        ).asConstantsInterface();
    }

    private static @Nullable Identifier easingLocation(Object entry) {
        if (!(entry instanceof EasingType easing)) {
            return null;
        }
        // The simple-easing registry keeps its id map private; the codec is the supported way out.
        return EasingType.CODEC.encodeStart(JsonOps.INSTANCE, easing)
            .result()
            .filter(json -> json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
            .map(json -> Identifier.withDefaultNamespace(json.getAsString()))
            .orElse(null);
    }

    private static GeneratorSpec environmentAttributeOperationIds() {
        return new EnumSpec(
            "dev.wyck.environment.attribute.modifier",
            "AttributeOperation",
            AttributeModifier.OperationId.class,
            Generators::serializedName,
            List.of(
                "Modifiers for environment attribute timelines."
            ),
            "3.2.0"
        );
    }

    private static @Nullable Identifier fluidLocation(Object entry) {
        if (entry instanceof Fluid fluid) {
            return BuiltInRegistries.FLUID.getKey(fluid);
        }
        return null;
    }

    private static @Nullable Identifier featureLocation(Object entry) {
        if (entry instanceof Feature<?> feature) {
            return BuiltInRegistries.FEATURE.getKey(feature);
        }
        return null;
    }

    private static @Nullable Identifier worldCarverLocation(Object entry) {
        if (entry instanceof WorldCarver<?> carver) {
            return BuiltInRegistries.CARVER.getKey(carver);
        }
        return null;
    }

    private static @Nullable Identifier activityLocation(Object entry) {
        if (entry instanceof Activity activity) {
            return BuiltInRegistries.ACTIVITY.getKey(activity);
        }
        return null;
    }

    private static @Nullable Identifier keyLocation(Object entry) {
        if (entry instanceof ResourceKey<?> resourceKey) {
            return resourceKey.identifier();
        }
        return null;
    }

    /**
     * Keys the wrapped constant by the vanilla constant's name, e.g.
     * {@code RAW_GENERATION}. Use when the translator resolves via
     * {@code Enum.valueOf}.
     */
    static String enumName(Enum<?> constant) {
        return constant.name();
    }

    /**
     * Keys the wrapped constant by {@code getSerializedName()}, e.g.
     * {@code raw_generation}. Only valid for enums implementing
     * {@link StringRepresentable}.
     */
    static String serializedName(Enum<?> constant) {
        return ((StringRepresentable) constant).getSerializedName();
    }

}