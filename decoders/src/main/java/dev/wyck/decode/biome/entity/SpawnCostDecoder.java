package dev.wyck.decode.biome.entity;

import dev.wyck.biome.entity.data.SpawnCost;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class SpawnCostDecoder implements Decodable<SpawnCost, net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost> {

    @Override
    public SpawnCost decode(net.minecraft.world.level.biome.MobSpawnSettings.MobSpawnCost cost) {
        return SpawnCost.of(cost.charge(), cost.energyBudget());
    }
}
