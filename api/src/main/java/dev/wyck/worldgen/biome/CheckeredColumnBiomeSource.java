package dev.wyck.worldgen.biome;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

/**
 * The checkerboard biome source places biomes in a checkerboard pattern.
 *
 * @see <a href="https://minecraft.wiki/w/Dimension_definition#checkerboard">Dimension definition (checkerboard column)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CheckeredColumnBiomeSource extends BiomeSource {

    /**
     * Any number of biomes.
     * @return the biomes
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Biome> biomes(); // vanilla-name: allowedBiomes

    /**
     * Determines the size of the checkerboard grid.
     * A scale of 0 means each cell of the grid is one chunk wide.
     * Doubles each time the scale increases.
     * Value between 0 and 62.
     * @return the size of the checkerboard grid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int size();

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
     * Creates a new checkerboard column biome source.
     * @param biomes the biomes
     * @param size the size of the checkerboard grid
     * @return the checkerboard column biome source
     * @since 3.0.0
     */
    static CheckeredColumnBiomeSource of(Set<Biome> biomes, int size) {
        Preconditions.checkArgument(size >= 0 && size <= 62, "size must be between 0 and 62");
        record Holder() {
            static final ConstructWireProvider<CheckeredColumnBiomeSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.biome.CheckerboardColumnBiomeSourceImpl");
        }
        return Holder.WIRE.construct(biomes, size);
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
     * Builder for {@link CheckeredColumnBiomeSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private Set<Biome> biomes = new HashSet<>();
        private int size = 2;

        public Builder() {}

        public Builder(CheckeredColumnBiomeSource source) {
            this.biomes.addAll(source.biomes());
            this.size = source.size();
        }

        /**
         * Sets the size of the checkerboard grid.
         * @param biomes the biomes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biomes(Set<Biome> biomes) {
            this.biomes = biomes;
            return this;
        }

        /**
         * Sets the size of the checkerboard grid.
         * @param size the size of the checkerboard grid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        // Friendly

        /**
         * Adds a biome to the list of biomes.
         * @param biome the biome to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(Biome biome) {
            this.biomes.add(biome);
            return this;
        }

        /**
         * Adds a biome to the list of biomes.
         * @param biomeKey the biome key to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(ResourceKey biomeKey) {
            this.biomes.add(Biome.reference(biomeKey));
            return this;
        }

        /**
         * Builds the checkerboard column biome source.
         * @return the checkerboard column biome source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CheckeredColumnBiomeSource build() {
            return of(biomes, size);
        }
    }
}
