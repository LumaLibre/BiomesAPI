package dev.wyck.test.bootstrap;

import dev.wyck.biome.Biomes;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.level.StemPersistence;
import dev.wyck.level.dimension.Dimensions;
import dev.wyck.worldgen.biome.FixedBiomeSource;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.chunk.FlatLevelSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import org.bukkit.Material;
import org.bukkit.World;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// not live
@Disabled
@SuppressWarnings("removal")
@ExtendWith(MinecraftBootstrap.class)
class LevelCreatorTest {

    private static ChunkGenerator flatGenerator() {
        return FlatLevelSource.builder()
                .biomeSource(FixedBiomeSource.of(Biomes.PLAINS))
                .settings(FlatLevelGeneratorSettings.builder()
                        .biome(Biomes.PLAINS)
                        .layer(Material.STONE, 1)
                        .build())
                .build();
    }

    private static LevelCreator.Builder minimal() {
        return LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:my_world"))
                .dimension(Dimensions.OVERWORLD)
                .generator(flatGenerator());
    }

    @Test
    void toBuilderRoundTripsWithoutDroppingAnything() {
        LevelCreator original = LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:round_trip"))
                .dimension(Dimensions.END)
                .generator(flatGenerator())
                .seed(99L)
                .generateStructures(false)
                .bonusChest(true)
                .environment(World.Environment.THE_END)
                .persistence(StemPersistence.PERSISTENT)
                .name("explicit")
                .build();

        LevelCreator copy = original.toBuilder().build();

        assertEquals(original.resourceKey(), copy.resourceKey());
        assertEquals(original.dimension(), copy.dimension());
        assertEquals(original.generator(), copy.generator());
        assertEquals(original.seed(), copy.seed(), "seed lost on round-trip");
        assertEquals(original.generateStructures(), copy.generateStructures(), "generateStructures lost on round-trip");
        assertEquals(original.bonusChest(), copy.bonusChest(), "bonusChest lost on round-trip");
        assertEquals(original.environment(), copy.environment(), "environment lost on round-trip");
        assertEquals(original.persistence(), copy.persistence(), "persistence lost on round-trip");
        assertEquals(original.name(), copy.name(), "name lost on round-trip");
        assertEquals(original.spawners(), copy.spawners());
    }

    @Test
    void anUnsetNameFallsBackToTheKeyPath() {
        assertEquals("my_world", minimal().build().name());
    }
    @Test
    void anUnsetNameFlattensSeparatorsInTheKeyPath() {
        LevelCreator nested = LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:nested/world.v2"))
                .dimension(Dimensions.OVERWORLD)
                .generator(flatGenerator())
                .build();

        assertEquals("nested_world_v2", nested.name());
    }

    @Test
    void buildRejectsAMissingResourceKey() {
        assertThrows(IllegalArgumentException.class, () -> LevelCreator.builder()
                .dimension(Dimensions.OVERWORLD)
                .generator(flatGenerator())
                .build());
    }

    @Test
    void buildRejectsAMissingGenerator() {
        assertThrows(IllegalArgumentException.class, () -> LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:no_generator"))
                .dimension(Dimensions.OVERWORLD)
                .build());
    }

    @Test
    void buildRejectsAMissingDimension() {
        assertThrows(IllegalArgumentException.class, () -> LevelCreator.builder()
                .resourceKey(ResourceKey.of("test:no_dimension"))
                .generator(flatGenerator())
                .build());
    }
}
