package dev.wyck.biome.entity.data;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a natural spawner.
 * @param type the type of entity that spawns
 * @param minCount the minimum number of entities that can spawn
 * @param maxCount the maximum number of entities that can spawn
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public record NaturalSpawner(EntityType type, int minCount, int maxCount) {

    @ApiStatus.Internal
    public static final Decoder<NaturalSpawner> DECODER = Decoder.create("dev.wyck.decode.biome.entity.NaturalSpawnerDecoder");

    @AsOf("2.3.0")
    public static NaturalSpawner of(EntityType type, int minCount, int maxCount) {
        return new NaturalSpawner(type, minCount, maxCount);
    }

    /**
     * Reads Minecraft natural spawn data into a record.
     * @param minecraftNaturalSpawner the spawn data to read
     * @return the record for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static NaturalSpawner decode(Object minecraftNaturalSpawner) {
        return DECODER.decode(minecraftNaturalSpawner);
    }
}
