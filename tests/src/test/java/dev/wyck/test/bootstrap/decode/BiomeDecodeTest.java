package dev.wyck.test.bootstrap.decode;

import dev.wyck.biome.Biome;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.carver.types.ReferencedCarver;
import dev.wyck.worldgen.feature.types.ReferencedConfiguredFeature;
import dev.wyck.worldgen.placement.PlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biomes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class BiomeDecodeTest {

    @Test
    void vanillaBiomeHolderDecodesTheWholeStack() {
        Holder<net.minecraft.world.level.biome.Biome> holder = vanilla(Biomes.PLAINS);
        net.minecraft.world.level.biome.Biome minecraft = holder.value();

        Biome decoded = Biome.decode(holder);

        assertEquals(ResourceKey.minecraft("plains"), decoded.resourceKey());
        assertEquals(minecraft.hasPrecipitation(), decoded.climateSettings().hasPrecipitation());
        assertEquals(minecraft.getBaseTemperature(), decoded.climateSettings().temperature());
        assertEquals(minecraft.getWaterColor(), decoded.specialEffects().waterColor());
        assertFalse(decoded.attributes().empty());

        assertEquals(minecraft.getMobSettings().getCreatureProbability(),
            decoded.biomeSpawner().creatureGenerationProbability());
        assertFalse(decoded.biomeSpawner().spawners().get(MobCategory.CREATURE).unwrap().isEmpty());

        BiomeGenerationSettings generation = decoded.generationSettings();
        assertFalse(generation.carvers().isEmpty());
        assertTrue(generation.carvers().stream().allMatch(ReferencedCarver.class::isInstance));
        assertFalse(generation.features().isEmpty());
        assertTrue(generation.features().values().stream().flatMap(java.util.Collection::stream)
            .allMatch(PlacedFeature.Reference.class::isInstance));

        for (Map.Entry<Decoration, java.util.List<PlacedFeature>> entry : generation.features().entrySet()) {
            int step = entry.getKey().ordinal();
            assertEquals(minecraft.getGenerationSettings().features().get(step).size(), entry.getValue().size());
        }
    }

    @Test
    void biomeWithSpawnCostsPreservesThem() {
        Biome decoded = Biome.decode(vanilla(Biomes.SOUL_SAND_VALLEY));

        BiomeSpawner spawner = decoded.biomeSpawner();
        assertNotNull(spawner);
        assertFalse(spawner.mobSpawnCosts().isEmpty());
        assertTrue(spawner.mobSpawnCosts().values().stream()
            .allMatch(cost -> cost.charge() > 0 && cost.energyBudget() > 0));
    }

    @Test
    void vanillaBiomeWithModifiedAttributesDecodes() {
        Biome decoded = Biome.decode(vanilla(Biomes.SWAMP));

        EnvironmentAttributeMap.Modification<Float, Float> waterFogDistance =
            decoded.attributes().modification(EnvironmentAttributes.WATER_FOG_END_DISTANCE);
        assertNotNull(waterFogDistance);
        assertEquals(AttributeOperation.MULTIPLY, waterFogDistance.operation());
        assertEquals(0.85F, waterFogDistance.argument());
    }

    @Test
    void directValuesDecodeAtCompletedWorldgenBoundaries() {
        Holder<net.minecraft.world.level.biome.Biome> biome = vanilla(Biomes.PLAINS);
        assertEquals(ResourceKey.minecraft("plains"), Biome.decode(biome.value()).resourceKey());

        Holder<net.minecraft.world.level.levelgen.placement.PlacedFeature> feature =
            biome.value().getGenerationSettings().features().stream()
                .flatMap(holderSet -> holderSet.stream())
                .findFirst()
                .orElseThrow();
        PlacedFeature.Composed decoded = assertInstanceOf(PlacedFeature.Composed.class,
            PlacedFeature.decode(Holder.direct(feature.value())));
        assertInstanceOf(ReferencedConfiguredFeature.class, decoded.feature());
        assertEquals(feature.value().placement().size(), decoded.placement().size());
    }

    private static Holder.Reference<net.minecraft.world.level.biome.Biome> vanilla(net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> key) {
        return BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME).get(key).orElseThrow();
    }
}
