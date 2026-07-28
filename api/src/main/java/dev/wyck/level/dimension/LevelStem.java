package dev.wyck.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a level stem, which is a {@link Dimension} with a specific {@link ChunkGenerator}.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface LevelStem extends Wrapper, Keyed, Registerable<LevelStem> {

    @ApiStatus.Internal
    ConstructWireProvider<LevelStem> WIRE = ConstructWireProvider.create("dev.wyck.level.dimension.LevelStemImpl");

    @ApiStatus.Internal
    Decoder<LevelStem> DECODER = Decoder.create("dev.wyck.decode.dimension.LevelStemDecoder");

    /**
     * Gets the resource key of this level stem, if it set.
     * @return an optional containing the resource key, or empty if not set
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Gets the dimension type of this level stem.
     * @return the dimension type
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    Dimension dimension();

    /**
     * Gets the dimension type of this level stem.
     * @return the dimension type
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Dimension dimensionType() {
        return dimension();
    }

    /**
     * Gets the chunk generator of this level stem.
     * @return the chunk generator
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ChunkGenerator chunkGenerator();

    /**
     * Gets the chunk generator of this level stem.
     * @return the chunk generator
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default ChunkGenerator generator() {
        return chunkGenerator();
    }

    /**
     * Converts this object back to a builder.
     * @return a builder with the same properties as this level stem
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new level stem with the given dimension type and chunk generator.
     * @param resourceKey the resource key
     * @param dimension the dimension type
     * @param chunkGenerator the chunk generator
     * @return a new level stem
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static LevelStem of(@Nullable ResourceKey resourceKey, Dimension dimension, ChunkGenerator chunkGenerator) {
        return WIRE.construct(Optional.ofNullable(resourceKey), dimension, chunkGenerator);
    }

    /**
     * Creates a new level stem with the given dimension type and chunk generator.
     * @param dimension the dimension type
     * @param chunkGenerator the chunk generator
     * @return a new level stem
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static LevelStem of(Dimension dimension, ChunkGenerator chunkGenerator) {
        return of(null, dimension, chunkGenerator);
    }

    /**
     * Reads a Minecraft level-stem holder or value.
     * @param minecraftStem the level-stem holder or value to read
     * @return the decoded level stem
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static LevelStem decode(Object minecraftStem) {
        return DECODER.decode(minecraftStem);
    }

    /**
     * Creates a new builder for a level stem.
     * @return a new builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating a {@link LevelStem}.
     * @since 3.3.0
     * @version 3.3.0
     * @author Jsinco
     */
    @AsOf("3.3.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable Dimension dimension;
        private @Nullable ChunkGenerator chunkGenerator;

        public Builder() {}

        public Builder(LevelStem stem) {
            this.resourceKey = stem.resourceKey().orElse(null);
            this.dimension = stem.dimension();
            this.chunkGenerator = stem.chunkGenerator();
        }

        /**
         * Sets the resource key for the level stem.
         * @param resourceKey the resource key
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the dimension type for the level stem.
         * @param dimension the dimension type
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder dimension(Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        /**
         * Sets the chunk generator for the level stem.
         * @param chunkGenerator the chunk generator
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder chunkGenerator(ChunkGenerator chunkGenerator) {
            this.chunkGenerator = chunkGenerator;
            return this;
        }

        /**
         * Builds the {@link LevelStem} with the specified properties.
         * @return the constructed level stem
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public LevelStem build() {
            Preconditions.checkNotNull(dimension, "dimension cannot be null");
            Preconditions.checkNotNull(chunkGenerator, "chunkGenerator cannot be null");
            return of(resourceKey, dimension, chunkGenerator);
        }
    }
}
