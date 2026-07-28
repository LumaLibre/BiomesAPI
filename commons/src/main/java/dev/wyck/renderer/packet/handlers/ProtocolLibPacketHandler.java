package dev.wyck.renderer.packet.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.VirtualBiome;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

// Credits to: kapstock/RealisticSeasons
/**
 * ProtocolLib-based implementation of the PacketHandler interface.
 *
 * @version 2.1.0
 * @since 0.0.6
 * @author Jsinco
 */
@NullMarked
@AsOf("0.0.6")
@ApiStatus.Internal
public class ProtocolLibPacketHandler implements PacketHandler {

    private final VirtualBiomeCollector collector;
    private final PacketAdapter[] protocolListeners;


    @AsOf("2.1.0")
    public ProtocolLibPacketHandler(Plugin provider, Priority priority, VirtualBiomeCollector collector) {
        this.collector = collector;
        ListenerPriority protocolLibPrio = priority.getDelegatePriority(ListenerPriority.class);
        this.protocolListeners = new PacketAdapter[] {
                new MapChunkPacketListener(provider, protocolLibPrio, this),
                new BlockChangePacketListener(provider, protocolLibPrio, this),
                new MultiBlockChangePacketListener(provider, protocolLibPrio, this)
        };
    }

    @AsOf("0.0.6")
    public ProtocolLibPacketHandler(Plugin provider, Priority priority) {
        this(provider, priority, new VirtualBiomeCollector());
    }

    @Override
    public PacketHandler register() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        for (PacketAdapter listener : protocolListeners) {
            protocolManager.addPacketListener(listener);
        }
        return this;
    }

    @Override
    public PacketHandler unregister() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        for (PacketAdapter listener : protocolListeners) {
            protocolManager.removePacketListener(listener);
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


    @AsOf("0.0.6")
    private static class MapChunkPacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public MapChunkPacketListener(Plugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.MAP_CHUNK);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundLevelChunkWithLightPacket

            PacketContainer packet = event.getPacket();
            Player player = event.getPlayer();
            StructureModifier<Integer> ints = packet.getIntegers();
            ChunkLocation chunkLocation = ChunkLocation.of(ints.read(0), ints.read(1));

            VirtualBiomeResolver resolver = context.collector.resolverFor(player, chunkLocation);
            if (resolver == null) {
                return;
            }

            DimensionSectionCount dimensionSectionCount = DimensionSectionCount.fromBukkitEnvironment(player.getWorld().getEnvironment());
            ClientboundLevelChunkPacketData chunkData = packet.getSpecificModifier(ClientboundLevelChunkPacketData.class).read(0);

            NativeChunkPacketHandler.instance().modifyChunkBiomes(chunkData, chunkLocation, resolver, dimensionSectionCount);
        }
    }

    // TODO: extract out common code between BlockChangePacketListener and MultiBlockChangePacketListener

    @AsOf("0.0.6")
    private static class BlockChangePacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public BlockChangePacketListener(Plugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.BLOCK_CHANGE);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundBlockUpdatePacket
            PacketContainer packet = event.getPacket();
            Player player = event.getPlayer();
            BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);

            ChunkLocation chunkLocation = ChunkLocation.fromBlockCoords(blockPosition.getX(), blockPosition.getZ());
            VirtualBiome override = context.collector.bestBiomeFor(player, chunkLocation);
            if (override == null) {
                return;
            }


            List<BlockReplacement> blockReplacements = override.blockReplacements();
            if (blockReplacements.isEmpty()) {
                return;
            }


            WrappedBlockData wrappedBlockData = packet.getBlockData().read(0);
            for (BlockReplacement replacement : blockReplacements) {
                if (wrappedBlockData.getType() == replacement.originalBlock()) {
                    wrappedBlockData.setType(replacement.replacementBlock());
                    packet.getBlockData().write(0, wrappedBlockData);
                    break;
                }
            }
        }
    }


    @AsOf("0.0.6")
    private static class MultiBlockChangePacketListener extends PacketAdapter {

        private final ProtocolLibPacketHandler context;

        public MultiBlockChangePacketListener(Plugin provider, ListenerPriority priority, ProtocolLibPacketHandler context) {
            super(provider, priority, PacketType.Play.Server.MULTI_BLOCK_CHANGE);
            this.context = context;
        }

        @Override
        public void onPacketSending(PacketEvent event) {
            // ClientboundSectionBlocksUpdatePacket
            Player player = event.getPlayer();
            PacketContainer packet = event.getPacket();

            BlockPosition blockPosition = packet.getSectionPositions().read(0);
            ChunkLocation chunkLocation = ChunkLocation.fromBlockCoords(blockPosition.getX(), blockPosition.getZ());

            VirtualBiome override = context.collector.bestBiomeFor(player, chunkLocation);
            if (override == null) {
                return;
            }

            List<BlockReplacement> blockReplacements = override.blockReplacements();
            if (blockReplacements.isEmpty()) {
                return;
            }

            WrappedBlockData[] wrappedBlockDatas = packet.getBlockDataArrays().read(0);
            boolean modified = false;
            for (int i = 0; i < wrappedBlockDatas.length; i++) {
                WrappedBlockData wrappedBlockData = wrappedBlockDatas[i];
                for (BlockReplacement replacement : blockReplacements) {
                    if (wrappedBlockData.getType() == replacement.originalBlock()) {
                        wrappedBlockData.setType(replacement.replacementBlock());
                        wrappedBlockDatas[i] = wrappedBlockData;
                        modified = true;
                        break;
                    }
                }
            }

            if (modified) {
                packet.getBlockDataArrays().write(0, wrappedBlockDatas);
            }
        }
    }
}
