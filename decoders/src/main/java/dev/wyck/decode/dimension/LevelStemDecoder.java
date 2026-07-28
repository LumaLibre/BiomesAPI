package dev.wyck.decode.dimension;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.LevelStem;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class LevelStemDecoder implements Decodable<LevelStem, Object> {
    @Override
    public LevelStem decode(Object minecraftObject) { // TODO: remove holder support?
        ResourceKey key = minecraftObject instanceof Holder<?> holder
            ? holder.unwrapKey().map(Decoders::key).orElse(null)
            : null;
        net.minecraft.world.level.dimension.LevelStem stem = Decoders.value(minecraftObject);
        return LevelStem.of(key, Dimension.decode(stem.type()), ChunkGenerator.decode(stem.generator()));
    }
}
