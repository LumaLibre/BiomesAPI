package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A huge fungus is a large tree-like feature that generates in the Nether.
 *
 * @see <a href="https://minecraft.wiki/w/Huge_Fungus">Huge Fungus</a>
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface HugeFungusConfiguration extends FeatureConfiguration {
    /**
     * Gets the valid base state for the huge fungus configuration.
     * @return The valid base state as a BlockData object.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    BlockData validBaseState();

    /**
     * Gets the stem state for the huge fungus configuration.
     * @return The stem state as a BlockData object.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    BlockData stemState();
    /**
     * Gets the hat state for the huge fungus configuration.
     * @return The hat state as a BlockData object.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    BlockData hatState();
    /**
     * Gets the decor state for the huge fungus configuration.
     * @return The decor state as a BlockData object.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    BlockData decorState();
    /**
     * Gets the replaceable blocks for the huge fungus configuration.
     * @return The replaceable blocks as a BlockPredicate object.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    BlockPredicate replaceableBlocks();
    /**
     * Checks if the huge fungus is planted.
     * @return true if the huge fungus is planted, false otherwise.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    boolean planted();

    /**
     * Converts this object back to a builder.
     * @return A new builder with these values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of HugeFungusConfiguration with the specified parameters.
     * @param validBaseState The valid base state for the huge fungus configuration.
     * @param stemState The stem state for the huge fungus configuration.
     * @param hatState The hat state for the huge fungus configuration.
     * @param decorState The decor state for the huge fungus configuration.
     * @param replaceableBlocks The replaceable blocks for the huge fungus configuration.
     * @param planted Whether the huge fungus is planted.
     * @return A new instance of HugeFungusConfiguration with the specified parameters.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static HugeFungusConfiguration of(BlockData validBaseState, BlockData stemState, BlockData hatState, BlockData decorState, BlockPredicate replaceableBlocks, boolean planted) {
        record Holder() {
            static final ConstructWireProvider<HugeFungusConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.HugeFungusConfigurationImpl");
        }
        return Holder.WIRE.construct(validBaseState, stemState, hatState, decorState, replaceableBlocks, planted);
    }

    /**
     * Creates a new builder.
     * @return A new builder
     * @since 3.3.0
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link HugeFungusConfiguration}.
     * @since 3.3.0
     * @version 3.3.0
     * @author Jsinco
     */
    @AsOf("3.3.0")
    final class Builder {
        private @Nullable BlockData validBaseState;
        private @Nullable BlockData stemState;
        private @Nullable BlockData hatState;
        private @Nullable BlockData decorState;
        private @Nullable BlockPredicate replaceableBlocks;
        private boolean planted;

        public Builder() {}

        public Builder(HugeFungusConfiguration config) {
            this.validBaseState = config.validBaseState();
            this.stemState = config.stemState();
            this.hatState = config.hatState();
            this.decorState = config.decorState();
            this.replaceableBlocks = config.replaceableBlocks();
            this.planted = config.planted();
        }

        /**
         * Sets the valid base state for the huge fungus configuration.
         * @param validBaseState the valid base state
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder validBaseState(BlockData validBaseState) {
            this.validBaseState = validBaseState;
            return this;
        }

        /**
         * Sets the stem state for the huge fungus configuration.
         * @param stemState the stem state
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder stemState(BlockData stemState) {
            this.stemState = stemState;
            return this;
        }

        /**
         * Sets the hat state for the huge fungus configuration.
         * @param hatState the hat state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder hatState(BlockData hatState) {
            this.hatState = hatState;
            return this;
        }

        /**
         * Sets the decor state for the huge fungus configuration.
         * @param decorState the decor state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder decorState(BlockData decorState) {
            this.decorState = decorState;
            return this;
        }

        /**
         * Sets the replaceable blocks for the huge fungus configuration.
         * @param replaceableBlocks the replaceable blocks.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder replaceableBlocks(BlockPredicate replaceableBlocks) {
            this.replaceableBlocks = replaceableBlocks;
            return this;
        }

        /**
         * Sets whether the huge fungus is planted.
         * @param planted true if the huge fungus is planted, false otherwise.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder planted(boolean planted) {
            this.planted = planted;
            return this;
        }

        // Friendly methods

        /**
         * Sets the valid base state for the huge fungus configuration using a {@link Material}.
         * @param material the material to set as the valid base state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder validBaseState(Material material) {
            this.validBaseState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the stem state for the huge fungus configuration using a {@link Material}.
         * @param material the material to set as the stem state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder stemState(Material material) {
            this.stemState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the hat state for the huge fungus configuration using a {@link Material}.
         * @param material the material to set as the hat state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder hatState(Material material) {
            this.hatState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the decor state for the huge fungus configuration using a {@link Material}.
         * @param material the material to set as the decor state.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder decorState(Material material) {
            this.decorState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the huge fungus as planted.
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder planted() {
            this.planted = true;
            return this;
        }

        /**
         * Builds the {@link HugeFungusConfiguration} instance.
         * @return A new instance of {@link HugeFungusConfiguration} with the specified parameters.
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public HugeFungusConfiguration build() {
            Preconditions.checkNotNull(validBaseState, "validBaseState must be set");
            Preconditions.checkNotNull(stemState, "stemState must be set");
            Preconditions.checkNotNull(hatState, "hatState must be set");
            Preconditions.checkNotNull(decorState, "decorState must be set");
            Preconditions.checkNotNull(replaceableBlocks, "replaceableBlocks must be set");
            return of(validBaseState, stemState, hatState, decorState, replaceableBlocks, planted);
        }
    }
}
