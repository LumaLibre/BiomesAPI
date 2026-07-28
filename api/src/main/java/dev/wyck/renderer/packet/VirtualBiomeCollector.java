package dev.wyck.renderer.packet;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * A collector for managing PhonyCustomBiome instances.
 *
 * @version 2.2.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.6")
public class VirtualBiomeCollector {

    private final Set<VirtualBiome> backing = new HashSet<>();

    /**
     * Appends a phony custom biome to the collector.
     * @param biome the phony custom biome to append
     */
    @AsOf("0.0.6")
    public void appendBiome(VirtualBiome biome) {
        if (backing.contains(biome)) {
            throw new IllegalArgumentException("PhonyCustomBiome with key " + biome.biomeResourceKey() + " is already registered.");
        }
        backing.add(biome);
    }

    /**
     * Checks if the collector has the given phony custom biome.
     * @param biome the phony custom biome to check
     * @return true if the collector has the biome, false otherwise
     */
    @AsOf("0.0.6")
    public boolean hasBiome(VirtualBiome biome) {
        return backing.contains(biome);
    }

    /**
     * Checks if the collector has a phony custom biome with the given ResourceKey.
     * @param biomeKey the ResourceKey of the biome to check
     * @return true if the collector has the biome, false otherwise
     * @since 0.0.8
     */
    @AsOf("0.0.6")
    public boolean hasBiome(ResourceKey biomeKey) {
        return backing.stream().anyMatch((VirtualBiome biome) -> biome.biomeResourceKey().equals(biomeKey));
    }

    /**
     * Removes a phony custom biome from the collector.
     * @param biome the phony custom biome to remove
     * @return true if the biome was removed, false if it was not found
     */
    @AsOf("0.0.6")
    public boolean removeBiome(VirtualBiome biome) {
        return backing.remove(biome);
    }

    /**
     * Removes a phony custom biome with the given ResourceKey from the collector.
     * @param biomeKey the ResourceKey of the biome to remove
     * @return true if the biome was removed, false if it was not found
     * @since 0.0.8
     */
    @AsOf("0.0.6")
    public boolean removeBiome(ResourceKey biomeKey) {
        return backing.removeIf((VirtualBiome biome) -> biome.biomeResourceKey().equals(biomeKey));
    }

    /**
     * Clears all phony custom biomes from the collector.
     */
    @AsOf("0.0.6")
    public void clearBiomes() {
        backing.clear();
    }

    /**
     * Picks the 'best' phony custom biome for the given player and chunk location.
     *
     * <p>Note: this only evaluates the cheap spatial {@link VirtualBiome#conditional()}.
     * Biome-aware conditions are ignored here; use {@link #resolverFor(Player, ChunkLocation)}
     * for the full biome-aware path used by the chunk packet listener.
     *
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best phony custom biome, or null if none match
     */
    @AsOf("0.0.6")
    public @Nullable VirtualBiome bestBiomeFor(Player player, ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return null;
        }

        return backing.stream()
                .filter((VirtualBiome phony) -> phony.conditional().test(player, chunkLocation))
                .max(Comparator.comparingInt((VirtualBiome phony) -> phony.priority().getLevel()))
                .orElse(null);
    }

    /**
     * Picks the 'best' custom biome for the given player and chunk location (spatial only).
     * @param player the player
     * @param chunkLocation the chunk location
     * @return the best custom biome, or null if none match
     */
    @AsOf("0.0.8")
    public @Nullable Biome bestCustomBiomeFor(Player player, ChunkLocation chunkLocation) {
        VirtualBiome phony = bestBiomeFor(player, chunkLocation);
        return phony != null ? phony.biome() : null;
    }

    /**
     * Cheap pre-decode gate. Returns a biome-aware resolver only when at least one phony biome
     * spatially applies to this chunk for this player, otherwise {@code null}.
     *
     * <p>Returning {@code null} is the hot path: the caller skips chunk decoding entirely.
     * The returned resolver is invoked by the NMS handler <em>after</em> it has decoded the
     * chunk once, and evaluates the biome-aware conditions against the decoded data.
     *
     * @param player the player the packet is being sent to
     * @param chunkLocation the chunk being sent
     * @return a resolver, or {@code null} if nothing could apply
     */
    @AsOf("2.2.0")
    public @Nullable VirtualBiomeResolver resolverFor(Player player, ChunkLocation chunkLocation) {
        List<VirtualBiome> candidates = spatialCandidates(player, chunkLocation);
        if (candidates.isEmpty()) {
            return null;
        }
        return snapshot -> bestMatching(candidates, player, snapshot);
    }

    private List<VirtualBiome> spatialCandidates(Player player, ChunkLocation chunkLocation) {
        if (backing.isEmpty()) {
            return List.of();
        }
        List<VirtualBiome> candidates = new ArrayList<>();
        for (VirtualBiome phony : backing) {
            if (phony.conditional().test(player, chunkLocation)) {
                candidates.add(phony);
            }
        }
        return candidates;
    }

    private @Nullable VirtualBiome bestMatching(List<VirtualBiome> candidates, Player player, SnapshotChunkData snapshot) {
        VirtualBiome best = null;
        int bestLevel = Integer.MIN_VALUE;
        for (VirtualBiome phony : candidates) {
            BiPredicate<Player, SnapshotChunkData> biomeCondition = phony.biomeCondition();
            if (biomeCondition != null && !biomeCondition.test(player, snapshot)) {
                continue;
            }
            int level = phony.priority().getLevel();
            if (level > bestLevel) {
                bestLevel = level;
                best = phony;
            }
        }
        return best;
    }
}