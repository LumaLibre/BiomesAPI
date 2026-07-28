package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Checks if the biome at the block's position is one of the specified biomes.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate">Block predicate</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MatchingBiomesPredicate extends BlockPredicate {

    /**
     * The biomes to match against.
     * @return the biome keys
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Biome> biomes();

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
     * Creates a new matching biomes predicate.
     * @param biomes the biomes to match against
     * @return the matching biomes predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingBiomesPredicate of(List<Biome> biomes) {
        record Holder() {
            static final ConstructWireProvider<MatchingBiomesPredicate> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.blockpredicates.MatchingBiomesPredicateImpl");
        }
        return Holder.WIRE.construct(biomes);
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

    final class Builder {
        private List<Biome> biomes = new ArrayList<>();

        public Builder() {}

        public Builder(MatchingBiomesPredicate predicate) {
            this.biomes.addAll(predicate.biomes());
        }

        /**
         * Sets the biomes to match against.
         * @param biomes the biomes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biomes(List<Biome> biomes) {
            this.biomes = biomes;
            return this;
        }

        /**
         * Sets the biomes to match against.
         * @param biomes the biomes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biomes(Biome... biomes) {
            this.biomes = new ArrayList<>(List.of(biomes));
            return this;
        }

        /**
         * Adds a biome to match against.
         * @param biome the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(Biome biome) {
            this.biomes.add(biome);
            return this;
        }

        /**
         * Adds multiple biomes to match against.
         * @param biomes the biomes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(Biome... biomes) {
            Collections.addAll(this.biomes, biomes);
            return this;
        }

        /**
         * Builds the matching biomes predicate.
         * @return the matching biomes predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MatchingBiomesPredicate build() {
            return of(biomes);
        }
    }
}