package dev.wyck.renderer.packet.data;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.biome.CustomBiome;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.BiomePosition;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * A class representing a "virtual" or fake custom biome designed for sending via packets.
 * The {@link Biome} must exist and be registered, but phony custom biomes are never
 * actually set to any chunks and only exist for packet sending purposes.
 *
 * <p>Three conditions control where this biome applies:
 * <ul>
 *   <li>{@link #conditional()} — the inexpensive, biome-independent spatial gate
 *       ({@code (player, chunkLocation)}). Evaluated <em>before</em> the chunk is decoded, so put
 *       world/permission/region checks here.</li>
 *   <li>{@link #biomeCondition()} — optional, biome-aware refinement
 *       ({@code (player, snapshotChunkData)}). Evaluated <em>after</em> decoding, only for chunks
 *       that passed the spatial gate. May be {@code null}, in which case it always passes.</li>
 *   <li>{@link #positionCondition()} — optional, quart-resolution refinement evaluated for every
 *       biome cell after the chunk-level conditions pass.</li>
 * </ul>
 *
 * @author Jsinco
 * @since 0.0.6
 * @version 3.3.0
 */
@NullMarked
@AsOf("2.2.0")
public final class VirtualBiome {

    private final ResourceKey biomeResourceKey;
    private final List<BlockReplacement> blockReplacements;
    private final BiPredicate<Player, ChunkLocation> conditional;
    private final @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition;
    private final @Nullable BiPredicate<Player, BiomePosition> positionCondition;
    private final PacketHandler.Priority priority;

    /**
     * Creates a virtual biome.
     * @param biomeResourceKey the biome to render
     * @param blockReplacements the client-side block replacements
     * @param conditional the inexpensive chunk-level condition
     * @param biomeCondition the condition evaluated against decoded chunk data
     * @param positionCondition the condition evaluated for every biome cell
     * @param priority the priority used when multiple biomes match
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public VirtualBiome(
        ResourceKey biomeResourceKey,
        List<BlockReplacement> blockReplacements,
        BiPredicate<Player, ChunkLocation> conditional,
        @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition,
        @Nullable BiPredicate<Player, BiomePosition> positionCondition,
        PacketHandler.Priority priority
    ) {
        this.biomeResourceKey = biomeResourceKey;
        this.blockReplacements = blockReplacements;
        this.conditional = conditional;
        this.biomeCondition = biomeCondition;
        this.positionCondition = positionCondition;
        this.priority = priority;
    }

    /**
     * Creates a virtual biome using chunk-level conditions only.
     * @param biomeResourceKey the biome to render
     * @param blockReplacements the client-side block replacements
     * @param conditional the inexpensive chunk-level condition
     * @param biomeCondition the condition evaluated against decoded chunk data
     * @param priority the priority used when multiple biomes match
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    public VirtualBiome(
        ResourceKey biomeResourceKey,
        List<BlockReplacement> blockReplacements,
        BiPredicate<Player, ChunkLocation> conditional,
        @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition,
        PacketHandler.Priority priority
    ) {
        this(biomeResourceKey, blockReplacements, conditional, biomeCondition, null, priority);
    }

    /**
     * Gets the resource key of the biome this virtual biome renders.
     * @return the biome resource key
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    public ResourceKey biomeResourceKey() {
        return this.biomeResourceKey;
    }

    /**
     * Gets the client-side block replacements applied alongside this biome.
     * @return the block replacements
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    public List<BlockReplacement> blockReplacements() {
        return this.blockReplacements;
    }

    /**
     * Gets the inexpensive, biome-independent spatial gate.
     * @return the chunk-level condition
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    public BiPredicate<Player, ChunkLocation> conditional() {
        return this.conditional;
    }

    /**
     * Gets the biome-aware refinement evaluated after the chunk is decoded.
     * @return the biome condition, or null if the chunk always passes
     * @since 2.2.0
     */
    @AsOf("2.2.0")
    public @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition() {
        return this.biomeCondition;
    }

    /**
     * Gets the quart-resolution refinement evaluated for every biome cell.
     * @return the position condition, or null if the whole chunk is affected
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public @Nullable BiPredicate<Player, BiomePosition> positionCondition() {
        return this.positionCondition;
    }

    /**
     * Gets the priority used when multiple virtual biomes match.
     * @return the priority
     * @since 0.0.6
     */
    @AsOf("0.0.6")
    public PacketHandler.Priority priority() {
        return this.priority;
    }

    /**
     * Gets the {@link Biome} associated with this phony biome.
     * If the custom biome does not exist, it will be created without being registered.
     *
     * @return the CustomBiome associated with this phony biome.
     * @since 0.0.10
     */
    @AsOf("0.0.10")
    public Biome biome() {
        Biome customBiome = KeyChains.BIOMES.get(biomeResourceKey);
        if (customBiome != null) {
            return customBiome;
        }
        Biome other = Biome.builder(biomeResourceKey).build();
        KeyChains.BIOMES.append(other);
        return other;
    }

    /**
     * Converts this PhonyCustomBiome to a Builder.
     * @return A new Builder instance with the same properties as this instance.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VirtualBiome that = (VirtualBiome) obj;
        return Objects.equals(biomeResourceKey, that.biomeResourceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biomeResourceKey);
    }

    @Override
    public String toString() {
        return "VirtualBiome[biomeResourceKey=" + biomeResourceKey
            + ", blockReplacements=" + blockReplacements
            + ", priority=" + priority + ']';
    }

    /**
     * Creates a new Builder instance.
     * @return a new Builder instance
     * @since 0.0.10
     */
    @AsOf("0.0.10")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating PhonyCustomBiome instances.
     * @version 2.2.0
     * @since 0.0.6
     * @author Jsinco
     */
    @AsOf("0.0.10")
    public static class Builder {
        private @Nullable ResourceKey biomeResourceKey;
        private List<BlockReplacement> blockReplacements = new ArrayList<>();
        private BiPredicate<Player, ChunkLocation> conditional = (player, chunkLocation) -> true;
        private @Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition = null;
        private @Nullable BiPredicate<Player, BiomePosition> positionCondition = null;
        private PacketHandler.Priority priority = PacketHandler.Priority.NORMAL;

        public Builder() {}

        public Builder(VirtualBiome other) {
            this.biomeResourceKey = other.biomeResourceKey();
            this.blockReplacements.addAll(other.blockReplacements());
            this.conditional = other.conditional();
            this.biomeCondition = other.biomeCondition();
            this.positionCondition = other.positionCondition();
            this.priority = other.priority();
        }

        @AsOf("0.0.6")
        public Builder biome(Biome biome) {
            this.biomeResourceKey = biome.resourceKey();
            if (biome instanceof CustomBiome customBiome) {
                this.blockReplacements.addAll(customBiome.blockReplacements());
            }
            return this;
        }

        @AsOf("0.0.10")
        public Builder biome(ResourceKey biomeResourceKey) {
            this.biomeResourceKey = biomeResourceKey;
            if (KeyChains.BIOMES.get(biomeResourceKey) instanceof CustomBiome customBiome) {
                this.blockReplacements.addAll(customBiome.blockReplacements());
            }
            return this;
        }

        @AsOf("3.0.0")
        public Builder replacement(Material original, Material to) {
            this.blockReplacements.add(new BlockReplacement(original, to));
            return this;
        }

        @AsOf("3.0.0")
        public Builder replacement(BlockReplacement... replacement) {
            Collections.addAll(this.blockReplacements, replacement);
            return this;
        }

        @AsOf("3.0.0")
        public Builder replacements(List<BlockReplacement> replacements) {
            this.blockReplacements = replacements;
            return this;
        }

        @AsOf("3.0.0")
        public Builder replacement(List<BlockReplacement> replacement) {
            this.blockReplacements.addAll(replacement);
            return this;
        }

        @AsOf("0.0.6")
        public Builder conditional(BiPredicate<Player, ChunkLocation> conditional) {
            this.conditional = conditional;
            return this;
        }

        @AsOf("2.2.0")
        public Builder biomeCondition(@Nullable BiPredicate<Player, SnapshotChunkData> biomeCondition) {
            this.biomeCondition = biomeCondition;
            return this;
        }

        /**
         * Sets the condition evaluated for every biome cell in a matching chunk.
         * @param positionCondition the position condition, or null to affect the whole chunk
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder positionCondition(@Nullable BiPredicate<Player, BiomePosition> positionCondition) {
            this.positionCondition = positionCondition;
            return this;
        }

        @AsOf("0.0.6")
        public Builder priority(PacketHandler.Priority priority) {
            this.priority = priority;
            return this;
        }

        @AsOf("0.0.6")
        public VirtualBiome build() {
            Preconditions.checkNotNull(biomeResourceKey, "biome cannot be null");
            return new VirtualBiome(biomeResourceKey, blockReplacements, conditional, biomeCondition, positionCondition, priority);
        }
    }
}
