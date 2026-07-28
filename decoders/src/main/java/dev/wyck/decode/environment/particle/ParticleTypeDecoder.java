package dev.wyck.decode.environment.particle;

import dev.wyck.environment.particle.ParticleType;
import dev.wyck.environment.particle.ParticleTypeImpl;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ParticleTypeDecoder implements Decodable<ParticleType, net.minecraft.core.particles.ParticleType<net.minecraft.core.particles.ParticleOptions>> {

    @Override
    public ParticleType decode(net.minecraft.core.particles.ParticleType<net.minecraft.core.particles.ParticleOptions> minecraftObject) {
        return new ParticleTypeImpl<>(minecraftObject);
    }
}
