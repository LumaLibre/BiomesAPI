package dev.wyck.decode.environment.particle;

import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsImpl;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ParticleOptionsDecoder implements Decodable<ParticleOptions, net.minecraft.core.particles.ParticleOptions> {

    @Override
    public ParticleOptions decode(net.minecraft.core.particles.ParticleOptions minecraftObject) {
        return new ParticleOptionsImpl(minecraftObject);
    }
}
