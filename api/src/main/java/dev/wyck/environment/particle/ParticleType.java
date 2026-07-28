package dev.wyck.environment.particle;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Opaque handle to a particle type.
 * Impl module wraps the real thing.
 *
 * @since 2.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public interface ParticleType extends Wrapper {

    /**
     * Reads a Minecraft particle type into a wrapper.
     * @param minecraftParticleType the particle type to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ParticleType decode(Object minecraftParticleType) {
        record Holder() {
            static final Decoder<ParticleType> DECODER = Decoder.create("dev.wyck.decode.environment.particle.ParticleTypeDecoder");
        }
        return Holder.DECODER.decode(minecraftParticleType);
    }
}
