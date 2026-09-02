package dev.wyck.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.VirtualBiome;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.papermc.paper.network.ChannelInitializeListenerHolder;
import net.kyori.adventure.key.Key;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A standalone PacketHandler implementation that injects a Netty handler into the Minecraft server.
 * This handler intercepts outbound packets and modifies them to use custom biomes.
 * This handler does not require any external dependencies and only relies on vanilla's netty and Bukkit's listeners.
 *
 * @version 2.1.0
 * @since 2.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
@ApiStatus.Internal
public class NettyPacketHandler implements PacketHandler {

    private static final Logger LOGGER = Logger.getLogger(NettyPacketHandler.class.getName());

    private final VirtualBiomeCollector collector;
    private final String handlerName;
    private final Key listenerKey;

    @AsOf("2.1.0")
    public NettyPacketHandler(String name, VirtualBiomeCollector collector) {
        this.collector = collector;
        this.handlerName = name + "_wyck_handler";
        this.listenerKey = Key.key("wyck", name + "_channel_init");
    }

    @AsOf("2.1.0")
    public NettyPacketHandler(String name) {
        this(name, new VirtualBiomeCollector());
    }

    @Override
    public PacketHandler register() {
        ChannelInitializeListenerHolder.addListener(listenerKey, channel -> injectChannel(channel, null));

        for (Player online : Bukkit.getOnlinePlayers()) {
            Channel channel = channelOf(online);
            if (channel != null) {
                injectChannel(channel, online);
            }
        }
        return this;
    }

    @Override
    public PacketHandler unregister() {
        ChannelInitializeListenerHolder.removeListener(listenerKey);
        clearBiomes(); // todo: change?
        return this;
    }

    /**
     * Adds the Wyck handler to a channel's pipeline if not already present.
     * If {@code knownPlayer} is non-null, the handler starts with the player pre-cached.
     *
     * @param channel the channel to inject the handler into
     * @param knownPlayer an optional player whose channel this is, to prime the handler's cache and avoid a lookup on the first packet
     */
    @SuppressWarnings("resource")
    private void injectChannel(Channel channel, @Nullable Player knownPlayer) {
        channel.eventLoop().execute(() -> {
            ChannelPipeline pipeline = channel.pipeline();
            if (pipeline.get(handlerName) != null) return;
            try {
                NettyChannelHandler handler = new NettyChannelHandler(collector);
                if (knownPlayer != null) {
                    handler.player = knownPlayer;
                }
                pipeline.addBefore("packet_handler", handlerName, handler);
            } catch (NoSuchElementException | IllegalArgumentException e) {
                LOGGER.log(Level.WARNING, "Failed to inject Netty handler", e);
            }
        });
    }

    private static @Nullable Channel channelOf(Player player) {
        try {
            return ((CraftPlayer) player).getHandle().connection.connection.channel;
        } catch (Throwable t) {
            LOGGER.log(Level.WARNING, "Could not resolve Netty channel for " + player.getName(), t);
            return null;
        }
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


    @AsOf("2.1.0")
    private static class NettyChannelHandler extends ChannelDuplexHandler {

        private static final Logger LOGGER = Logger.getLogger(NettyChannelHandler.class.getName());

        private static final @Nullable Field BLOCK_UPDATE_STATE_FIELD = findFieldByType(ClientboundBlockUpdatePacket.class, BlockState.class, "blockState");
        private static final @Nullable Field SECTION_POS_FIELD = findFieldByType(ClientboundSectionBlocksUpdatePacket.class, SectionPos.class, "sectionPos");
        private static final @Nullable Field SECTION_POSITIONS_FIELD = findFieldByType(ClientboundSectionBlocksUpdatePacket.class, short[].class, "positions");
        private static final @Nullable Field SECTION_STATES_FIELD = findFieldByType(ClientboundSectionBlocksUpdatePacket.class, BlockState[].class, "states");

        private final VirtualBiomeCollector collector;

        @Nullable Player player;

        public NettyChannelHandler(VirtualBiomeCollector collector) {
            this.collector = collector;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            try {
                if (player == null) {
                    player = resolvePlayer(ctx.channel());
                }
                if (player != null) {
                    if (msg instanceof ClientboundLevelChunkWithLightPacket chunkPacket) {
                        handleChunkPacket(player, chunkPacket);
                    } else if (msg instanceof ClientboundBlockUpdatePacket blockPacket) {
                        handleBlockUpdate(player, blockPacket);
                    } else if (msg instanceof ClientboundSectionBlocksUpdatePacket sectionPacket) {
                        handleSectionUpdate(player, sectionPacket);
                    }
                }
            } catch (Throwable t) {
                LOGGER.log(Level.WARNING, "Failed to process outbound packet", t);
            }
            super.write(ctx, msg, promise);
        }

        private static @Nullable Player resolvePlayer(Channel channel) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                try {
                    Channel playerChannel = ((CraftPlayer) online).getHandle().connection.connection.channel;
                    if (playerChannel == channel) return online;
                } catch (Throwable ignored) {}
            }
            return null;
        }

        private void handleChunkPacket(Player player, ClientboundLevelChunkWithLightPacket packet) {
            ChunkLocation loc = ChunkLocation.of(packet.getX(), packet.getZ());

            VirtualBiomeResolver resolver = collector.resolverFor(player, loc);
            if (resolver == null) return;

            int sectionCount = (player.getWorld().getMaxHeight() - player.getWorld().getMinHeight()) >> 4;
            NativeChunkPacketHandler.instance().modifyChunkBiomes(packet.getChunkData(), loc, resolver, sectionCount);
        }

        private void handleBlockUpdate(Player player, ClientboundBlockUpdatePacket packet) {
            if (BLOCK_UPDATE_STATE_FIELD == null) return;

            BlockPos pos = packet.getPos();
            VirtualBiome override = collector.bestBiomeFor(player, pos.getX(), pos.getY(), pos.getZ());
            if (override == null) return;

            List<BlockReplacement> replacements = override.blockReplacements();
            if (replacements.isEmpty()) return;

            BlockState state = packet.getBlockState();
            Material currentMat = state.getBukkitMaterial();

            for (BlockReplacement r : replacements) {
                if (currentMat == r.originalBlock()) {
                    BlockState replaced = materialToState(r.replacementBlock());
                    if (replaced == null) return;
                    try {
                        BLOCK_UPDATE_STATE_FIELD.set(packet, replaced);
                    } catch (IllegalAccessException e) {
                        LOGGER.log(Level.WARNING, "Failed to rewrite block update", e);
                    }
                    return;
                }
            }
        }

        private void handleSectionUpdate(Player player, ClientboundSectionBlocksUpdatePacket packet) {
            if (SECTION_POS_FIELD == null || SECTION_POSITIONS_FIELD == null || SECTION_STATES_FIELD == null) return;

            try {
                SectionPos sectionPos = (SectionPos) SECTION_POS_FIELD.get(packet);
                short[] positions = (short[]) SECTION_POSITIONS_FIELD.get(packet);
                BlockState[] states = (BlockState[]) SECTION_STATES_FIELD.get(packet);
                if (positions == null || states == null || states.length == 0 || positions.length != states.length) return;

                boolean modified = false;
                for (int i = 0; i < states.length; i++) {
                    short packedPosition = positions[i];
                    int blockX = sectionPos.relativeToBlockX(packedPosition);
                    int blockY = sectionPos.relativeToBlockY(packedPosition);
                    int blockZ = sectionPos.relativeToBlockZ(packedPosition);
                    VirtualBiome override = collector.bestBiomeFor(player, blockX, blockY, blockZ);
                    if (override == null || override.blockReplacements().isEmpty()) {
                        continue;
                    }
                    Material mat = states[i].getBukkitMaterial();
                    for (BlockReplacement r : override.blockReplacements()) {
                        if (mat == r.originalBlock()) {
                            BlockState replaced = materialToState(r.replacementBlock());
                            if (replaced != null) {
                                states[i] = replaced;
                                modified = true;
                            }
                            break;
                        }
                    }
                }

                if (modified) {
                    SECTION_STATES_FIELD.set(packet, states);
                }
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Failed to rewrite section block update", e);
            }
        }

        private static @Nullable Field findFieldByType(Class<?> owner, Class<?> type, String mojangName) {
            try {
                Field f = owner.getDeclaredField(mojangName);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException ignored) {
                for (Field candidate : owner.getDeclaredFields()) {
                    if (candidate.getType() == type) {
                        candidate.setAccessible(true);
                        return candidate;
                    }
                }
                LOGGER.log(Level.SEVERE,
                        "Could not locate field of type " + type.getName() + " on " + owner.getName()
                                + " (looked for '" + mojangName + "'). Related packets cannot be rewritten. :(");
                return null;
            }
        }

        private static @Nullable BlockState materialToState(@Nullable Material material) {
            if (material == null || !material.isBlock()) return null;
            return ((CraftBlockData) Bukkit.createBlockData(material)).getState();
        }
    }
}
