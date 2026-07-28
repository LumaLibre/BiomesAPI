package dev.wyck.environment.particle.options;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleType;
import org.bukkit.Vibration;
import org.jspecify.annotations.NullMarked;

/**
 * Particle data for vibration particles.
 * 
 * @param destination The destination of the vibration.
 * @param arrivalInTicks The time in ticks until the vibration arrives.
 * @since 2.0.0
 * @version 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public record VibrationParticle(Vibration.Destination destination, int arrivalInTicks) implements ParticleData {

    @Override
    @AsOf("2.0.0")
    public ParticleOptions apply(ParticleType particleType) {
        return ParticleOptionsFactory.instance().vibration(destination, arrivalInTicks);
    }

    @AsOf("2.0.0")
    public static VibrationParticle of(Vibration.Destination destination, int arrivalInTicks) {
        return new VibrationParticle(destination, arrivalInTicks);
    }
}
