package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for sculk charge particles.
 * 
 * @param roll The roll of the sculk charge.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record SculkChargeParticle(float roll) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().sculkCharge(roll);
    }

    @AsOf("2.0.0")
    public static SculkChargeParticle of(float roll) {
        return new SculkChargeParticle(roll);
    }
}
