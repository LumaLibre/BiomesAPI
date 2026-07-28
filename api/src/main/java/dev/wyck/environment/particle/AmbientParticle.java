package dev.wyck.environment.particle;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A wrapper for ambient particles in a biome, including their type, probability, and optional data.
 *
 * @see ParticleCatalog
 * @since 1.1.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("1.1.0")
public interface AmbientParticle extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<AmbientParticle> WIRE = ConstructWireProvider.construct("dev.wyck.environment.particle.AmbientParticleImpl");

    @ApiStatus.Internal
    Decoder<AmbientParticle> DECODER = Decoder.create("dev.wyck.decode.environment.particle.AmbientParticleDecoders");

    /**
     * Gets the particle type.
     * @return the particle type
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    ParticleTypes type();

    /**
     * Gets the probability of this particle being spawned.
     * @return the probability of this particle being spawned
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    float probability();

    /**
     * Gets the particle data.
     * @return the particle data, or null if {@link #type()} is simple
     * @since 1.1.0
     */
    @AsOf("1.1.0") // TODO: Use optional
    @Nullable ParticleData particleData();

    /**
     * Delegates the wrapped ambient particle to a Minecraft ParticleOptions.
     * @return The corresponding Minecraft ParticleOptions.
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    default ParticleOptions particleOptions() {
        ParticleType particleType = this.type().particleType();
        if (this.type().isSimple()) {
            return ParticleOptionsFactory.WIRE.get().simple(particleType);
        }

        ParticleData particleData = this.particleData();
        Preconditions.checkNotNull(particleData, "Particle data must not be null for complex particle types.");
        return particleData.apply(particleType);
    }

    /**
     * Converts this AmbientParticle to a Builder instance.
     * @return a new Builder instance with the same properties as this instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new AmbientParticle instance.
     * @param ambientParticle The particle type.
     * @param probability The probability of the particle.
     * @param particleData The particle data, or null if the particle is simple.
     * @return A new AmbientParticle instance.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AmbientParticle of(ParticleTypes ambientParticle, float probability, @Nullable ParticleData particleData) {
        return WIRE.construct(ambientParticle, probability, particleData);
    }

    /**
     * Creates a new AmbientParticle instance with no particle data.
     * @param ambientParticle The particle type.
     * @param probability The probability of the particle.
     * @return A new AmbientParticle instance with no particle data.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AmbientParticle of(ParticleTypes ambientParticle, float probability) {
        return WIRE.construct(ambientParticle, probability);
    }

    /**
     * Creates a new AmbientParticle builder.
     * @return A new AmbientParticle builder.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads a Minecraft ambient particle into a wrapper.
     * @param minecraftAmbientParticle the ambient particle to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static AmbientParticle decode(Object minecraftAmbientParticle) {
        return DECODER.decode(minecraftAmbientParticle);
    }

    /**
     * Builder for creating AmbientParticle instances.
     *
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ParticleTypes type;
        private float probability;
        private @Nullable ParticleData data;

        public Builder() {}

        public Builder(AmbientParticle ambientParticle) {
            this.type = ambientParticle.type();
            this.probability = ambientParticle.probability();
            this.data = ambientParticle.particleData();
        }

        /**
         * Sets the particle type.
         * @param type The particle type.
         * @return This builder instance.
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder type(ParticleTypes type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the probability of the particle.
         * @param probability The probability of the particle.
         * @return This builder instance.
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder probability(float probability) {
            this.probability = probability;
            return this;
        }

        /**
         * Sets the particle data.
         * @param data The particle data.
         * @return This builder instance.
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder data(ParticleData data) {
            this.data = data;
            return this;
        }

        /**
         * Builds the AmbientParticle instance.
         * @return The built AmbientParticle instance.
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public AmbientParticle build() {
            Preconditions.checkNotNull(type, "type must not be null.");
            return of(type, probability, data);
        }
    }
}
