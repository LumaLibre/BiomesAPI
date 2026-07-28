package dev.wyck.decode.tags;

import dev.wyck.decode.Decoders;
import dev.wyck.tags.TagSet;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BlockTagSetDecoder implements Decodable<TagSet<Material>, HolderSet<Block>> {

    @Override
    public TagSet<Material> decode(HolderSet<Block> minecraftObject) {
        return minecraftObject.unwrapKey()
            .map(tag -> TagSet.ofBlockTag(Decoders.key(tag.location())))
            .orElseGet(() -> TagSet.ofBlocks(Decoders.materials(minecraftObject)));
    }
}
