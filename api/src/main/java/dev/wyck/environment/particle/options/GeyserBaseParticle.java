package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for geyser base particles.
 *
 * @param waterBlocks The number of water blocks.
 * @param burstImpulseBase The base impulse for the burst effect.
 * @since 3.0.0
 */
@NullMarked
@AsOf("3.0.0")
public record GeyserBaseParticle(int waterBlocks, float burstImpulseBase) implements ParticleData {

    @Override
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().geyserBase(particleType, waterBlocks, burstImpulseBase);
    }

    /**
     * Creates a new GeyserBaseParticle with the given water blocks and burst impulse base.
     * @param waterBlocks The number of water blocks.
     * @param burstImpulseBase The base impulse for the burst effect.
     * @return A new GeyserBaseParticle.
     */
    @AsOf("3.0.0")
    public static GeyserBaseParticle of(int waterBlocks, float burstImpulseBase) {
        return new GeyserBaseParticle(waterBlocks, burstImpulseBase);
    }
}
