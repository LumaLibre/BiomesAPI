package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for trail particles.
 *
 * @param target The target location of the trail.
 * @param color The color of the trail.
 * @param duration The duration of the trail.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record TrailParticle(Location target, int color, int duration) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().trail(target, color, duration);
    }

    @AsOf("2.0.0")
    public static TrailParticle of(Location target, String hexColor, int duration) {
        return new TrailParticle(target, ParticleData.parseHex(hexColor), duration);
    }
}
