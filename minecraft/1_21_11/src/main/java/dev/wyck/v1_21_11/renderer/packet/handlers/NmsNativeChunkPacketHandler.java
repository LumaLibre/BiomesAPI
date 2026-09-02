package dev.wyck.v1_21_11.renderer.packet.handlers;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.misc.ChunkLocation;
import dev.wyck.renderer.packet.VirtualBiomeResolver;
import dev.wyck.renderer.packet.data.BlockReplacement;
import dev.wyck.renderer.packet.data.SnapshotChunkData;
import dev.wyck.renderer.packet.data.VirtualBiome;
import dev.wyck.renderer.packet.handlers.NativeChunkPacketHandler;
import dev.wyck.v1_21_11.renderer.packet.data.NmsSnapshotChunkData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftBiome;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@NullMarked
@WireFactory
@AsOf("0.0.6")
@ApiStatus.Internal
public final class NmsNativeChunkPacketHandler implements NativeChunkPacketHandler {

    @Override
    public void modifyChunkBiomes(Object chunkDataObj, ChunkLocation chunkLocation, VirtualBiomeResolver resolver, int sectionCount) {
        ClientboundLevelChunkPacketData chunkData = (ClientboundLevelChunkPacketData) chunkDataObj;

        LevelChunkSection[] sections = extractChunkSections(chunkData, sectionCount);

        SnapshotChunkData snapshot = new NmsSnapshotChunkData(chunkLocation, sections);
        VirtualBiome[][][][] resolved = new VirtualBiome[sections.length][CHUNK_SECTIONS][CHUNK_SECTIONS][CHUNK_SECTIONS];
        Map<dev.wyck.keys.ResourceKey, Holder<net.minecraft.world.level.biome.Biome>> biomeCache = new HashMap<>();
        boolean modified = false;

        for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
            LevelChunkSection section = sections[sectionIndex];
            for (int x = 0; x < CHUNK_SECTIONS; x++) {
                for (int y = 0; y < CHUNK_SECTIONS; y++) {
                    for (int z = 0; z < CHUNK_SECTIONS; z++) {
                        VirtualBiome phony = resolver.resolve(snapshot, x, sectionIndex * CHUNK_SECTIONS + y, z);
                        resolved[sectionIndex][x][y][z] = phony;
                        if (phony == null) {
                            continue;
                        }
                        section.setBiome(x, y, z, minecraftBiome(phony, biomeCache));
                        modified = true;
                    }
                }
            }

            for (int x = 0; x < CHUNK_SECTION_SIZE; x++) {
                for (int y = 0; y < CHUNK_SECTION_SIZE; y++) {
                    for (int z = 0; z < CHUNK_SECTION_SIZE; z++) {
                        VirtualBiome phony = resolved[sectionIndex][x >> 2][y >> 2][z >> 2];
                        if (phony == null || phony.blockReplacements().isEmpty()) {
                            continue;
                        }
                        BlockState state = section.getBlockState(x, y, z);
                        Material asBukkitMaterial = state.getBukkitMaterial();

                        for (BlockReplacement replacement : phony.blockReplacements()) {
                            if (asBukkitMaterial != replacement.originalBlock()) continue;
                            BlockState newState = CraftMagicNumbers.getBlock(replacement.replacementBlock())
                                    .defaultBlockState();
                            section.setBlockState(x, y, z, newState);
                            modified = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!modified) {
            return;
        }

        byte[] modifiedData = serializeChunkSections(sections);

        try {
            Field dataField = ClientboundLevelChunkPacketData.class.getDeclaredField("buffer");
            dataField.setAccessible(true);
            dataField.set(chunkData, modifiedData);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update chunk data", e);
        }
    }

    private Holder<net.minecraft.world.level.biome.Biome> minecraftBiome(
        VirtualBiome phony,
        Map<dev.wyck.keys.ResourceKey, Holder<net.minecraft.world.level.biome.Biome>> cache
    ) {
        Holder<net.minecraft.world.level.biome.Biome> cached = cache.get(phony.biomeResourceKey());
        if (cached != null) {
            return cached;
        }
        org.bukkit.block.Biome bukkitBiome = phony.biome().bukkitBiome();
        Holder<net.minecraft.world.level.biome.Biome> minecraftBiome = CraftBiome.bukkitToMinecraftHolder(bukkitBiome);
        if (minecraftBiome == null) {
            throw new IllegalStateException("Failed to get Minecraft biome for " + bukkitBiome);
        }
        cache.put(phony.biomeResourceKey(), minecraftBiome);
        return minecraftBiome;
    }

    private LevelChunkSection[] extractChunkSections(ClientboundLevelChunkPacketData chunkData, int sectionCount) {
        LevelChunkSection[] sections = new LevelChunkSection[sectionCount];
        DedicatedServer server = ((CraftServer) Bukkit.getServer()).getServer();
        FriendlyByteBuf serializer = chunkData.getReadBuffer();

        PalettedContainerFactory paletteFactory = PalettedContainerFactory.create(server.registryAccess());

        for (int i = 0; i < sections.length; i++) {
            sections[i] = new LevelChunkSection(paletteFactory);
        }

        for (LevelChunkSection section : sections) {
            section.read(serializer);
        }

        return sections;
    }

    private byte[] serializeChunkSections(LevelChunkSection[] sections) {
        int totalSize = 0;
        for (LevelChunkSection section : sections) {
            totalSize += section.getSerializedSize();
        }

        byte[] data = new byte[totalSize];
        ByteBuf buffer = Unpooled.wrappedBuffer(data);
        buffer.writerIndex(0);
        FriendlyByteBuf serializer = new FriendlyByteBuf(buffer);

        for (LevelChunkSection section : sections) {
            section.write(serializer);
        }

        return data;
    }
}
