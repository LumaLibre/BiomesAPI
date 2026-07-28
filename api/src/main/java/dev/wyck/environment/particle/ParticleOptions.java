package dev.wyck.environment.particle;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Opaque handle to NMS ParticleOptions.
 * Impl module wraps the real thing.
 * @version 3.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface ParticleOptions extends Wrapper {

    @ApiStatus.Internal
    Decoder<ParticleOptions> DECODER = Decoder.create("dev.wyck.decode.environment.particle.ParticleOptionsDecoder");

    /**
     * Creates a new ParticleOptions instance.
     * @param type the particle type
     * @param data the particle data, can be null for simple particle types
     * @return a new ParticleOptions instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ParticleOptions of(ParticleTypes type, @Nullable ParticleData data) {
        ParticleOptionsFactory factory = ParticleOptionsFactory.WIRE.get();
        ParticleType particleType = type.particleType();
        if (type.isSimple()) {
            return factory.simple(particleType);
        }
        Preconditions.checkNotNull(data, "Particle data must not be null for complex particle types.");
        return data.apply(particleType);
    }

    /**
     * Creates a new ParticleOptions instance.
     * @param type the particle type
     * @return a new ParticleOptions instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ParticleOptions of(ParticleTypes type) {
        return of(type, null);
    }

    /**
     * Reads Minecraft particle options into a wrapper.
     * @param minecraftParticleOptions the particle options to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ParticleOptions decode(Object minecraftParticleOptions) {
        return DECODER.decode(minecraftParticleOptions);
    }
}
