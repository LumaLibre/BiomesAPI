package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import dev.wyck.environment.particle.ParticleTypes;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for a {@link ParticleTypes#SHRIEK}.
 * @param delay the delay in ticks before the shriek particle is emitted
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public record ShriekParticle(int delay) implements ParticleData {

    @Override
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().shriek(delay);
    }

    /**
     * Creates a new {@link ShriekParticle} instance with the specified delay.
     * @param delay the delay in ticks before the shriek particle is emitted
     * @return a new {@link ShriekParticle} instance
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public static ShriekParticle of(int delay) {
        return new ShriekParticle(delay);
    }
}
