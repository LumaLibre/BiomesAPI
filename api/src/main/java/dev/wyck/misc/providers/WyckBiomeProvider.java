package dev.wyck.misc.providers;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A biome provider that uses a map of custom biomes to provide biomes.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.1")
public abstract class WyckBiomeProvider extends BiomeProvider {

    protected final Map<ResourceKey, Biome> biomeMap;

    @AsOf("2.3.1")
    public WyckBiomeProvider(Collection<Biome> biomes) {
        this.biomeMap = new HashMap<>();
        for (Biome biome : biomes) {
            biomeMap.put(biome.resourceKey(), biome);
        }
    }

    @Override
    @AsOf("2.3.0")
    public List<org.bukkit.block.Biome> getBiomes(WorldInfo worldInfo) {
        return biomeMap.values().stream().map(Biome::bukkitBiome).toList();
    }

    @AsOf("2.3.1")
    public Map<ResourceKey, Biome> biomeMap() {
        return biomeMap;
    }
}