package dev.wyck.decode.worldgen.feature;

import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class EndSpikeDecoder implements Decodable<EndSpike, EndSpikeFeature.EndSpike> {

    @Override
    public EndSpike decode(EndSpikeFeature.EndSpike spike) {
        return EndSpike.of(spike.getCenterX(), spike.getCenterZ(), spike.getRadius(), spike.getHeight(), spike.isGuarded());
    }
}
