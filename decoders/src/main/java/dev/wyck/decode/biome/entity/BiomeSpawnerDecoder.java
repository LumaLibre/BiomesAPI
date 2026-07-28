package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.entity.MobCategory;
import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
@ApiStatus.Internal
public final class BiomeSpawnerDecoder implements Decodable<BiomeSpawner, MobSpawnSettings> {

    @Override
    public BiomeSpawner decode(MobSpawnSettings settings) {
        BiomeSpawner.Builder builder = BiomeSpawner.builder()
            .creatureGenerationProbability(settings.getCreatureProbability());

        for (net.minecraft.world.entity.MobCategory category : net.minecraft.world.entity.MobCategory.values()) {
            net.minecraft.util.random.WeightedList<MobSpawnSettings.SpawnerData> spawners = settings.getMobs(category);
            if (!spawners.isEmpty()) {
                builder.spawners(
                    MobCategory.TRANSLATOR.fromNms(category),
                    Decoders.weighted(spawners, NaturalSpawner::decode)
                );
            }
        }

        Map<net.minecraft.world.entity.EntityType<?>, MobSpawnSettings.MobSpawnCost> costs =
            FastReflection.read(settings, "mobSpawnCosts");
        costs.forEach((type, cost) -> builder.spawnCost(
            Decoders.bukkitEntityType(type),
            SpawnCost.decode(cost)
        ));
        return builder.build();
    }
}
