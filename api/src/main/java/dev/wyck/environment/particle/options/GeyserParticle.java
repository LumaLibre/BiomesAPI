package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for geyser particles.
 *
 * @param waterBlocks the number of water blocks
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public record GeyserParticle(int waterBlocks) implements ParticleData {
    @Override
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().geyser(particleType, waterBlocks);
    }

    /**
     * Creates a new GeyserParticle with the given amount of water blocks.
     * @param waterBlocks the amount of water blocks
     * @return a new GeyserParticle
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public static GeyserParticle of(int waterBlocks) {
        return new GeyserParticle(waterBlocks);
    }
}
