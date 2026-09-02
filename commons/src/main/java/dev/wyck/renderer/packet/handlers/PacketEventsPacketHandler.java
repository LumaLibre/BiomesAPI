package dev.wyck.renderer.packet.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.resources.ResourceLocation;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.util.mappings.IRegistry;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerMultiBlockChange;
import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler.CHUNK_SECTIONS;
import static dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler.CHUNK_SECTION_SIZE;


// Credits to: bitmochibit/Defcon
/**
 * PacketEvents-based implementation of the PacketHandler interface.
 *
 * @version 2.1.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.19")
@ApiStatus.Internal
public class PacketEventsPacketHandler implements PacketHandler {

    // TODO: Abstract out common code

    private final VirtualBiomeCollector collector;
    private final PacketListener[] packetListeners;
    private final PacketListenerPriority packetListenerPriority;

    @AsOf("2.1.0")
    public PacketEventsPacketHandler(PacketHandler.Priority priority, VirtualBiomeCollector collector) {
        this.collector = collector;
        this.packetListeners = new PacketListener[] {
                new MapChunkPacketListener(this),
                new BlockChangePacketListener(this),
                new MultiBlockChangePacketListener(this)
        };
        this.packetListenerPriority = priority.getDelegatePriority(PacketListenerPriority.class);
    }

    @AsOf("0.0.19")
    public PacketEventsPacketHandler(PacketHandler.Priority priority) {
        this(priority, new VirtualBiomeCollector());
    }


    @Override
    public PacketHandler register() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().registerListener(listener, packetListenerPriority);
        }
        return this;
    }

    @Override
    public PacketHandler unregister() {
        for (PacketListener listener : packetListeners) {
            PacketEvents.getAPI().getEventManager().unregisterListener(listener.asAbstract(packetListenerPriority));
        }
        return this;
    }

    @Override
    public PacketHandler appendBiome(VirtualBiome biome) {
        collector.appendBiome(biome);
        return this;
    }

    @Override
    public boolean removeBiome(VirtualBiome biome) {
        return collector.removeBiome(biome);
    }

    @Override
    public boolean removeBiome(ResourceKey biomeKey) {
        return collector.removeBiome(biomeKey);
    }

    @Override
    public boolean hasBiome(VirtualBiome biome) {
        return collector.hasBiome(biome);
    }

    @Override
    public boolean hasBiome(ResourceKey biomeKey) {
        return collector.hasBiome(biomeKey);
    }

    @Override
    public PacketHandler clearBiomes() {
        collector.clearBiomes();
        return this;
    }


    // Credits to: mrmcyeet
    @AsOf("0.0.19")
    private static class PacketEventsBukkitMaterials {
        private static final Map<Material, WrappedBlockState> materialToWrappedBlockStateCache = new HashMap<>();
        private static final Map<Integer, Material> idToMaterialCache = new HashMap<>();

        public static WrappedBlockState fromCachedBukkitBlockData(Material blockType) {
            return materialToWrappedBlockStateCache.computeIfAbsent(blockType, key ->
                    SpigotConversionUtil.fromBukkitBlockData(key.createBlockData())
            );
        }

        public static Material toCachedBukkitBlockData(int blockId) {
            return idToMaterialCache.computeIfAbsent(blockId, key ->
                    SpigotConversionUtil.toBukkitBlockData(WrappedBlockState.getByGlobalId(key))
                            .createBlockState()
                            .getType()
            );
        }
    }

    @AsOf("0.0.19")
    private static class MapChunkPacketListener implements PacketListener {

        static final String REGISTRY_KEY = "worldgen/biome";

        private final PacketEventsPacketHandler context;

        public MapChunkPacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        @SuppressWarnings("PatternValidation")
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.CHUNK_DATA) {
                return;
            }
            WrapperPlayServerChunkData wrapperPlayServerChunkData = new WrapperPlayServerChunkData(event);
            Column column = wrapperPlayServerChunkData.getColumn();


            User user = event.getUser();
            Player player = event.getPlayer();
            ChunkLocation chunkLocation = ChunkLocation.of(column.getX(), column.getZ());

            VirtualBiomeResolver resolver = context.collector.resolverFor(player, chunkLocation);
            if (resolver == null) {
                return;
            }

            SnapshotChunkData snapshot = new PacketEventsSnapshotChunkData(chunkLocation, column, user);
            IRegistry<?> elementRegistry = user.getRegistry(ResourceLocation.minecraft(REGISTRY_KEY));
            Preconditions.checkNotNull(elementRegistry, "Biome registry not found for user " + user.getName());
            Map<ResourceKey, Integer> biomeIds = new HashMap<>();
            boolean modified = false;
            BaseChunk[] chunks = wrapperPlayServerChunkData.getColumn().getChunks();

            for (int sectionIndex = 0; sectionIndex < chunks.length; sectionIndex++) {
                BaseChunk chunk = chunks[sectionIndex];
                if (!(chunk instanceof Chunk_v1_18 chunkV1_18)) {
                    continue;
                }

                VirtualBiome[][][] resolved = new VirtualBiome[CHUNK_SECTIONS][CHUNK_SECTIONS][CHUNK_SECTIONS];
                for (int x = 0; x < CHUNK_SECTIONS; x++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        for (int y = 0; y < CHUNK_SECTIONS; y++) {
                            VirtualBiome phony = resolver.resolve(
                                snapshot, x, sectionIndex * CHUNK_SECTIONS + y, z
                            );
                            resolved[x][y][z] = phony;
                            if (phony == null) {
                                continue;
                            }
                            ResourceKey biomeKey = phony.biomeResourceKey();
                            Integer biomeId = biomeIds.get(biomeKey);
                            if (biomeId == null) {
                                var element = elementRegistry.getByName(
                                    new ResourceLocation(biomeKey.namespace(), biomeKey.path())
                                );
                                Preconditions.checkNotNull(
                                    element,
                                    "Biome " + biomeKey.resourceLocation() + " not found in registry for user " + user.getName()
                                );
                                biomeId = element.getId(user.getClientVersion());
                                biomeIds.put(biomeKey, biomeId);
                            }
                            chunkV1_18.getBiomeData().set(x, y, z, biomeId);
                            modified = true;
                        }
                    }
                }

                for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                    for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                        for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                            VirtualBiome phony = resolved[x >> 2][y >> 2][z >> 2];
                            if (phony == null || phony.blockReplacements().isEmpty()) {
                                continue;
                            }
                            WrappedBlockState state = chunkV1_18.get(x, y, z);

                            for (BlockReplacement replacement : phony.blockReplacements()) {
                                WrappedBlockState wrappedReplacement = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                                if (!state.equals(wrappedReplacement)) {
                                    continue;
                                }

                                WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                                chunkV1_18.set(x, y, z, newState);
                                modified = true;
                                break;
                            }
                        }
                    }
                }
            }

            if (modified) {
                event.markForReEncode(true);
            }
        }
    }

    @AsOf("0.0.19")
    private static class BlockChangePacketListener implements PacketListener {
        private final PacketEventsPacketHandler context;

        public BlockChangePacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.BLOCK_CHANGE) {
                return;
            }

            WrapperPlayServerBlockChange wrapper = new WrapperPlayServerBlockChange(event);
            Player player = event.getPlayer();
            Vector3i vector3i =  wrapper.getBlockPosition();

            VirtualBiome override = context.collector.bestBiomeFor(
                player, vector3i.getX(), vector3i.getY(), vector3i.getZ()
            );
            if (override == null) {
                return;
            }

            List<BlockReplacement> blockReplacements = override.blockReplacements();
            if (blockReplacements.isEmpty()) {
                return;
            }

            WrappedBlockState wrappedBlockData = wrapper.getBlockState();
            for (BlockReplacement replacement : blockReplacements) {
                WrappedBlockState originalState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                if (!wrappedBlockData.equals(originalState)) {
                    continue;
                }

                WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                wrapper.setBlockState(newState);
                break;
            }

            event.markForReEncode(true);
        }
    }

    @AsOf("0.0.19")
    private static class MultiBlockChangePacketListener implements PacketListener {
        private final PacketEventsPacketHandler context;

        public MultiBlockChangePacketListener(PacketEventsPacketHandler context) {
            this.context = context;
        }

        @Override
        public void onPacketSend(PacketSendEvent event) {
            if (event.getPacketType() != PacketType.Play.Server.MULTI_BLOCK_CHANGE) {
                return;
            }

            WrapperPlayServerMultiBlockChange wrapper = new WrapperPlayServerMultiBlockChange(event);
            Player player = event.getPlayer();
            ClientVersion clientVersion = event.getUser().getClientVersion();
            boolean modified = false;
            for (WrapperPlayServerMultiBlockChange.EncodedBlock encodedBlock : wrapper.getBlocks()) {
                VirtualBiome override = context.collector.bestBiomeFor(
                    player, encodedBlock.getX(), encodedBlock.getY(), encodedBlock.getZ()
                );
                if (override == null || override.blockReplacements().isEmpty()) {
                    continue;
                }
                WrappedBlockState wrappedBlockData = encodedBlock.getBlockState(clientVersion);
                for (BlockReplacement replacement : override.blockReplacements()) {
                    WrappedBlockState originalState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.originalBlock());
                    if (!wrappedBlockData.equals(originalState)) {
                        continue;
                    }

                    WrappedBlockState newState = PacketEventsBukkitMaterials.fromCachedBukkitBlockData(replacement.replacementBlock());
                    encodedBlock.setBlockState(newState);
                    modified = true;
                    break;
                }
            }

            if (modified) {
                event.markForReEncode(true);
            }
        }
    }


    public static final class PacketEventsSnapshotChunkData implements SnapshotChunkData {

        private static final String BIOME_REGISTRY_KEY = "worldgen/biome";

        private final ChunkLocation location;
        private final Column column;
        private final User user;
        private final ClientVersion clientVersion;

        private final Map<Integer, Biome> idCache = new HashMap<>();
        private @Nullable IRegistry<?> biomeRegistry;

        private @Nullable Biome center;

        public PacketEventsSnapshotChunkData(ChunkLocation location, Column column, User user) {
            this.location = location;
            this.column = column;
            this.user = user;
            this.clientVersion = user.getClientVersion();
        }

        @Override
        public ChunkLocation location() {
            return location;
        }

        @Override
        public Biome centerBiome() {
            Biome c = this.center;
            if (c == null) {
                c = resolveBiome(biomeIdAt(0, CENTER_NOISE_XZ, 0, CENTER_NOISE_XZ));
                this.center = c;
            }
            return c;
        }

        @Override
        public Biome biomeAt(int x, int y, int z) {
            int sectionIdx = y >> 4;
            return resolveBiome(biomeIdAt(sectionIdx, (x & 15) >> 2, (y & 15) >> 2, (z & 15) >> 2));
        }

        @Override
        public Optional<ChunkSnapshot> bukkitSnapshot() {
            return Optional.empty();
        }

        private int biomeIdAt(int sectionIdx, int nx, int ny, int nz) {
            BaseChunk[] chunks = column.getChunks();
            if (sectionIdx < 0 || sectionIdx >= chunks.length || !(chunks[sectionIdx] instanceof Chunk_v1_18 chunk)) {
                throw new IllegalStateException("No biome section at index " + sectionIdx + " for chunk " + location);
            }
            return chunk.getBiomeData().get(nx, ny, nz);
        }

        private Biome resolveBiome(int id) {
            Biome cached = idCache.get(id);
            if (cached != null) {
                return cached;
            }

            MappedEntity entry = biomeRegistry().getById(clientVersion, id);
            if (entry == null) {
                throw new IllegalStateException("No biome registry entry for id " + id + " (client " + clientVersion + ")");
            }

            ResourceLocation name = entry.getName();
            NamespacedKey key = new NamespacedKey(name.getNamespace(), name.getKey());
            Biome biome = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(key);
            if (biome == null) {
                throw new IllegalStateException("No Bukkit biome for " + key);
            }

            idCache.put(id, biome);
            return biome;
        }

        private IRegistry<?> biomeRegistry() {
            IRegistry<?> registry = this.biomeRegistry;
            if (registry == null) {
                registry = user.getRegistry(ResourceLocation.minecraft(BIOME_REGISTRY_KEY));
                if (registry == null) {
                    throw new IllegalStateException("Biome registry not found for user " + user.getName());
                }
                this.biomeRegistry = registry;
            }
            return registry;
        }
    }
}
