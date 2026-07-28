package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Dripstone feature found in caves.
 *
 * @see <a href="https://minecraft.wiki/w/Dripstone_(feature)">Dripstone (feature)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SpeleothemConfiguration extends FeatureConfiguration {

    /**
     * Describes the block forming the base of the speleothem.
     * @return the base block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData baseBlock();

    /**
     * Describes the block creating the columns of the speleothem.
     * @return the pointed block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData pointedBlock();

    /**
     * Describes which blocks the feature can generate on.
     * @return the replaceable blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> replaceableBlocks();

    /**
     * Probability for double-block dripstone between 0.0 and 1.0.
     * @return the probability for double-block dripstone
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfTallerGeneration();

    /**
     * Probability that the dripstone spreads in a horizontal direction between 0.0 and 1.0.
     * @return the probability for horizontal spread
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfDirectionalSpread();

    /**
     * Probability of horizontal spread by two blocks between 0.0 and 1.0.
     * @return the probability for horizontal spread by two blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfSpreadRadius2();

    /**
     * After the spread by two blocks, the probability of spreading the third block
     * between 0.0 and 1.0.
     * @return the probability for third block spread
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfSpreadRadius3();

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
     * Creates a new pointed dripstone configuration.
     * @param baseBlock the base block
     * @param pointedBlock the pointed block
     * @param replaceableBlocks the replaceable blocks
     * @param chanceOfTallerDripstone chance of double-block dripstone between 0.0 and 1.0
     * @param chanceOfDirectionalSpread chance of horizontal spread between 0.0 and 1.0
     * @param chanceOfSpreadRadius2 chance of horizontal spread by two blocks between 0.0 and 1.0
     * @param chanceOfSpreadRadius3 after the spread by two blocks, the probability of spreading the third block between 0.0 and 1.0
     * @return a new pointed dripstone configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpeleothemConfiguration of(BlockData baseBlock, BlockData pointedBlock, Set<Material> replaceableBlocks, float chanceOfTallerDripstone, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
        record Holder() {
            static final ConstructWireProvider<SpeleothemConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.SpeleothemConfigurationImpl");
        }
        return Holder.WIRE.construct(baseBlock, pointedBlock, replaceableBlocks, chanceOfTallerDripstone, chanceOfDirectionalSpread, chanceOfSpreadRadius2, chanceOfSpreadRadius3);
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
     * Builder for {@link SpeleothemConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData baseBlock;
        private @Nullable BlockData pointedBlock;
        private Set<Material> replaceableBlocks = new HashSet<>();
        private float chanceOfTallerGeneration = 0.2F;
        private float chanceOfDirectionalSpread = 0.7F;
        private float chanceOfSpreadRadius2 = 0.5F;
        private float chanceOfSpreadRadius3 = 0.5F;

        public Builder() {}

        public Builder(SpeleothemConfiguration configuration) {
            this.baseBlock = configuration.baseBlock();
            this.pointedBlock = configuration.pointedBlock();
            this.replaceableBlocks.addAll(configuration.replaceableBlocks());
            this.chanceOfTallerGeneration = configuration.chanceOfTallerGeneration();
            this.chanceOfDirectionalSpread = configuration.chanceOfDirectionalSpread();
            this.chanceOfSpreadRadius2 = configuration.chanceOfSpreadRadius2();
            this.chanceOfSpreadRadius3 = configuration.chanceOfSpreadRadius3();
        }

        /**
         * Sets the base block.
         * @param baseBlock the base block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder baseBlock(BlockData baseBlock) {
            this.baseBlock = baseBlock;
            return this;
        }

        /**
         * Sets the pointed block.
         * @param pointedBlock the pointed block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder pointedBlock(BlockData pointedBlock) {
            this.pointedBlock = pointedBlock;
            return this;
        }

        /**
         * Sets the replaceable blocks.
         * @param replaceableBlocks the replaceable blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlocks(Set<Material> replaceableBlocks) {
            this.replaceableBlocks = replaceableBlocks;
            return this;
        }

        /**
         * Sets the chance of double-block dripstone.
         * @param chanceOfTallerGeneration the chance of double-block dripstone
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfTallerGeneration(float chanceOfTallerGeneration) {
            this.chanceOfTallerGeneration = chanceOfTallerGeneration;
            return this;
        }

        /**
         * Sets the chance of horizontal spread.
         * @param chanceOfDirectionalSpread the chance of horizontal spread
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfDirectionalSpread(float chanceOfDirectionalSpread) {
            this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
            return this;
        }

        /**
         * Sets the chance of horizontal spread by two blocks.
         * @param chanceOfSpreadRadius2 the chance of horizontal spread by two blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfSpreadRadius2(float chanceOfSpreadRadius2) {
            this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
            return this;
        }

        /**
         * Sets the chance of spreading the third block.
         * @param chanceOfSpreadRadius3 the chance of spreading the third block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfSpreadRadius3(float chanceOfSpreadRadius3) {
            this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block state.
         * @param material the material to derive the block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder baseBlock(Material material) {
            this.baseBlock = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the block state.
         * @param material the material to derive the block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder pointedBlock(Material material) {
            this.pointedBlock = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Adds the given block states to the replaceable blocks.
         * @param materials the block states to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceableBlock(Material... materials) {
            Collections.addAll(this.replaceableBlocks, materials);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SpeleothemConfiguration build() {
            Preconditions.checkNotNull(baseBlock, "baseBlock must be set");
            Preconditions.checkNotNull(pointedBlock, "pointedBlock must be set");
            Preconditions.checkArgument(chanceOfTallerGeneration >= 0.0F && chanceOfTallerGeneration <= 1.0F, "chanceOfTallerDripstone must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfDirectionalSpread >= 0.0F && chanceOfDirectionalSpread <= 1.0F, "chanceOfDirectionalSpread must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfSpreadRadius2 >= 0.0F && chanceOfSpreadRadius2 <= 1.0F, "chanceOfSpreadRadius2 must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfSpreadRadius3 >= 0.0F && chanceOfSpreadRadius3 <= 1.0F, "chanceOfSpreadRadius3 must be between 0.0 and 1.0");
            return of(baseBlock, pointedBlock, replaceableBlocks, chanceOfTallerGeneration, chanceOfDirectionalSpread, chanceOfSpreadRadius2, chanceOfSpreadRadius3);
        }
    }
}