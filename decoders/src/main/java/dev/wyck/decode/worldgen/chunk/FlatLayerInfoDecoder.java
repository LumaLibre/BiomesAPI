package dev.wyck.decode.worldgen.chunk;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.chunk.flat.FlatLayerInfo;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FlatLayerInfoDecoder implements Decodable<FlatLayerInfo, net.minecraft.world.level.levelgen.flat.FlatLayerInfo> {
    @Override
    public FlatLayerInfo decode(net.minecraft.world.level.levelgen.flat.FlatLayerInfo layer) {
        return FlatLayerInfo.of(Decoders.blockData(layer.getBlockState()).getMaterial(), layer.getHeight());
    }
}
