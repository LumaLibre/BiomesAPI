package dev.wyck.worldgen.chunk;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.LevelCreator;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.noise.Noise;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps a chunk generator for use in a {@link LevelCreator}'s level stem.
 *
 * @version 2.5.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseBasedChunkGenerator extends ChunkGenerator {

    /**
     * The noise generator.
     * @return the noise generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Noise noise();

    /**
     * Converts this chunk generator to a builder.
     * @return a new builder for this chunk generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Builds a noise generator over the given biome source, referencing the given settings.
     * @param biomeSource the biome source the generator draws from
     * @param abstractNoise the noise generator settings
     * @return a wrapped chunk generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseBasedChunkGenerator of(BiomeSource biomeSource, Noise abstractNoise) {
        record Holder() {
            static final ConstructWireProvider<NoiseBasedChunkGenerator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.chunk.NoiseBasedChunkGeneratorImpl");
        }
        return Holder.WIRE.construct(biomeSource, abstractNoise);
    }

    /**
     * Creates a new builder for a chunk generator.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating chunk generators.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BiomeSource biomeSource;
        private @Nullable Noise noise;

        public Builder() {}

        public Builder(NoiseBasedChunkGenerator other) {
            this.biomeSource = other.biomeSource();
            this.noise = other.noise();
        }

        /**
         * Sets the biome source of the chunk generator.
         * @param biomeSource the biome source of the chunk generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biomeSource(BiomeSource biomeSource) {
            this.biomeSource = biomeSource;
            return this;
        }

        /**
         * Sets the noise generator of the chunk generator.
         * @param noise the noise generator of the chunk generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noise(Noise noise) {
            this.noise = noise;
            return this;
        }

        /**
         * Sets the noise generator of the chunk generator.
         * @param resourceKey the noise generator settings key
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noise(ResourceKey resourceKey) {
            this.noise = Noise.reference(resourceKey);
            return this;
        }

        /**
         * Builds the chunk generator.
         * @return the chunk generator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NoiseBasedChunkGenerator build() {
            Preconditions.checkNotNull(biomeSource, "biomeSource cannot be null");
            Preconditions.checkNotNull(noise, "noise cannot be null");
            return of(biomeSource, noise);
        }
    }
}