package dev.wyck.worldgen.chunk;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * CraftBukkit's implementation of a {@link ChunkGenerator}.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Obsolete
public interface CraftBukkitChunkGenerator extends ChunkGenerator {

    /**
     * The delegate Minecraft chunk generator for everything Bukkit's chunk generator can't do.
     * @return the delegate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ChunkGenerator delegate();

    /**
     * The Bukkit chunk generator.
     * @return the generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    org.bukkit.generator.ChunkGenerator generator();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new CraftBukkitChunkGenerator.
     * @param delegate the delegate
     * @param generator the generator
     * @return the CraftBukkitChunkGenerator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CraftBukkitChunkGenerator of(ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator) {
        record Holder() {
            static final ConstructWireProvider<CraftBukkitChunkGenerator> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.chunk.CraftBukkitChunkGeneratorImpl");
        }
        return Holder.WIRE.construct(delegate, generator);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CraftBukkitChunkGenerator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ChunkGenerator delegate;
        private org.bukkit.generator.@Nullable ChunkGenerator generator;

        public Builder() {}

        public Builder(CraftBukkitChunkGenerator other) {
            this.delegate = other.delegate();
            this.generator = other.generator();
        }

        /**
         * Sets the delegate.
         * @param delegate the delegate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder delegate(ChunkGenerator delegate) {
            this.delegate = delegate;
            return this;
        }

        /**
         * Sets the Bukkit generator.
         * @param generator the Bukkit generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder generator(org.bukkit.generator.ChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        // Friendly

        /**
         * Sets the Bukkit generator.
         * @param generator the Bukkit generator
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder bukkitGenerator(org.bukkit.generator.ChunkGenerator generator) {
            this.generator = generator;
            return this;
        }

        /**
         * Builds the CraftBukkitChunkGenerator.
         * @return the CraftBukkitChunkGenerator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CraftBukkitChunkGenerator build() {
            Preconditions.checkNotNull(delegate, "delegate must be set");
            Preconditions.checkNotNull(generator, "generator must be set");
            return of(delegate, generator);
        }
    }
}