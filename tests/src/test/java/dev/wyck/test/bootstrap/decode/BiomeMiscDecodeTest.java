package dev.wyck.test.bootstrap.decode;

import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.TemperatureModifier;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.environment.GrassColorModifier;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.WeightedList;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class BiomeMiscDecodeTest {

    @Test
    void climateSettingsDecodeEveryField() {
        net.minecraft.world.level.biome.Biome.ClimateSettings minecraft =
            new net.minecraft.world.level.biome.Biome.ClimateSettings(
                false, -0.7f, net.minecraft.world.level.biome.Biome.TemperatureModifier.FROZEN, 0.85f);

        ClimateSettings decoded = ClimateSettings.decode(minecraft);

        assertFalse(decoded.hasPrecipitation());
        assertEquals(-0.7f, decoded.temperature());
        assertEquals(TemperatureModifier.FROZEN, decoded.temperatureModifier());
        assertEquals(0.85f, decoded.downfall());
    }

    @Test
    void specialEffectsDecodeOverridesAndModifier() {
        net.minecraft.world.level.biome.BiomeSpecialEffects minecraft =
            new net.minecraft.world.level.biome.BiomeSpecialEffects(
                0x123456,
                Optional.of(0x234567),
                Optional.empty(),
                Optional.of(0x345678),
                net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier.DARK_FOREST
            );

        BiomeSpecialEffects decoded = BiomeSpecialEffects.decode(minecraft);

        assertEquals(0x123456, decoded.waterColor());
        assertEquals(0x234567, decoded.foliageColorOverride().orElseThrow());
        assertTrue(decoded.dryFoliageColorOverride().isEmpty());
        assertEquals(0x345678, decoded.grassColorOverride().orElseThrow());
        assertEquals(GrassColorModifier.DARK_FOREST, decoded.grassColorModifier());
    }

    @Test
    void spawnLeafRecordsDecodeTheirOwnMinecraftTypes() {
        NaturalSpawner spawner = NaturalSpawner.decode(new MobSpawnSettings.SpawnerData(
            net.minecraft.world.entity.EntityTypes.ZOMBIE, 2, 5));
        assertEquals(EntityType.ZOMBIE, spawner.type());
        assertEquals(2, spawner.minCount());
        assertEquals(5, spawner.maxCount());

        SpawnCost cost = SpawnCost.decode(new MobSpawnSettings.MobSpawnCost(9.5, 1.25));
        assertEquals(1.25, cost.charge());
        assertEquals(9.5, cost.energyBudget());
    }

    @Test
    void biomeSpawnerStacksLeafDecodersAndPreservesWeights() {
        BiomeSpawner original = BiomeSpawner.builder()
            .creatureGenerationProbability(0.35f)
            .spawner(MobCategory.MONSTER, 7, EntityType.ZOMBIE, 2, 5)
            .spawner(MobCategory.MONSTER, 3, EntityType.SKELETON, 1, 4)
            .spawnCost(EntityType.ZOMBIE, 1.25, 9.5)
            .build();

        BiomeSpawner decoded = BiomeSpawner.decode(original.toMinecraft());

        assertEquals(0.35f, decoded.creatureGenerationProbability());
        WeightedList<NaturalSpawner> monsters = decoded.spawners().get(MobCategory.MONSTER);
        assertEquals(2, monsters.unwrap().size());
        assertEquals(EntityType.ZOMBIE, monsters.unwrap().getFirst().value().type());
        assertEquals(7, monsters.unwrap().getFirst().weight());
        assertEquals(EntityType.SKELETON, monsters.unwrap().getLast().value().type());
        assertEquals(3, monsters.unwrap().getLast().weight());
        assertEquals(Map.of(EntityType.ZOMBIE, new SpawnCost(1.25, 9.5)), decoded.mobSpawnCosts());
    }
}
