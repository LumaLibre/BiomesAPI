package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for particles that require a block type.
 *
 * @param type The block type.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record BlockParticle(Material type) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().block(particleType, type);
    }

    @AsOf("2.0.0")
    public static BlockParticle of(Material material) {
        return new BlockParticle(material);
    }
}
