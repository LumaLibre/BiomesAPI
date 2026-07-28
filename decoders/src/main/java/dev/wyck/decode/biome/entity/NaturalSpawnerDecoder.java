package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.data.NaturalSpawner;
import dev.wyck.decode.Decoders;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NaturalSpawnerDecoder implements Decodable<NaturalSpawner, net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData> {

    @Override
    public NaturalSpawner decode(net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData spawner) {
        return NaturalSpawner.of(
            Decoders.bukkitEntityType(spawner.type()),
            spawner.minCount(),
            spawner.maxCount()
        );
    }
}
