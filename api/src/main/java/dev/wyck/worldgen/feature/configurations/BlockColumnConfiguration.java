package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * A feature that consists of glow berries, found in lush caves.
 *
 * @see <a href="https://minecraft.wiki/w/Cave_Vines_(feature)">Cave Vines (feature)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockColumnConfiguration extends FeatureConfiguration {

    @ApiStatus.Experimental // may be moved in the future
    BlockPredicate ONLY_IN_AIR_PREDICATE = BlockPredicate.matchingBlockTag(ResourceKey.minecraft("air"));

    /**
     * The layers of this column.
     * @return an immutable list of layers
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<Layer> layers();

    /**
     * The direction the column is built in.
     * @return the direction
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockFace direction();

    /**
     * The block predicate that must be passed for each position of the column.
     * @return the allowed placement predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate allowedPlacement();

    /**
     * Determines where to cut off blocks when space is restricted. If {@code true}, layers are removed from
     * the start of the column.
     * @return whether the tip is prioritized
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean prioritizeTip();

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
     * Creates a new block column configuration.
     * @param layers the layers of the column
     * @param direction the direction the column is built in
     * @param allowedPlacement the block predicate that must be passed for each position of the column
     * @param prioritizeTip whether layers are removed from the start of the column when space is restricted
     * @return a new block column configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockColumnConfiguration of(List<Layer> layers, BlockFace direction, BlockPredicate allowedPlacement, boolean prioritizeTip) {
        record Holder() {
            static final ConstructWireProvider<BlockColumnConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.BlockColumnConfigurationImpl");
        }
        return Holder.WIRE.construct(layers, direction, allowedPlacement, prioritizeTip);
    }

    /**
     * Creates a new layer for a block column.
     * @param height the height of the layer
     * @param state the block state provider used for the layer
     * @return a new layer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockColumnConfiguration.Layer layer(IntProvider height, BlockStateProvider state) {
        return new BlockColumnConfiguration.Layer(height, state);
    }

    /**
     * Creates a block column configuration consisting of a single layer.
     * @param height the height of the layer
     * @param state the block state provider used for the layer
     * @return a new block column configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockColumnConfiguration simple(IntProvider height, BlockStateProvider state) {
        return builder().layer(height, state).build();
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
     * A single layer of a block column, pairing a height with the block state used for that height.
     * @param height the height of the layer, which must be non-negative
     * @param state the block state provider used for the layer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    record Layer(IntProvider height, BlockStateProvider state) {
        public Layer {
            Preconditions.checkArgument(height.minInclusive() >= 0, "height must be non-negative");
        }
    }

    /**
     * Builder for {@link BlockColumnConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<Layer> layers = new ArrayList<>();
        private BlockFace direction = BlockFace.UP;
        private BlockPredicate allowedPlacement = ONLY_IN_AIR_PREDICATE;
        private boolean prioritizeTip = false;

        public Builder() {}

        public Builder(BlockColumnConfiguration configuration) {
            this.layers.addAll(configuration.layers());
            this.direction = configuration.direction();
            this.allowedPlacement = configuration.allowedPlacement();
            this.prioritizeTip = configuration.prioritizeTip();
        }

        /**
         * Sets the layers of the column, replacing any existing layers.
         * @param layers the layers
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layers(List<Layer> layers) {
            this.layers = layers;
            return this;
        }

        /**
         * Sets the direction the column is built in.
         * @param direction the direction
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder direction(BlockFace direction) {
            this.direction = direction;
            return this;
        }

        /**
         * Sets the block predicate that must be passed for each position of the column.
         * @param allowedPlacement the allowed placement predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedPlacement(BlockPredicate allowedPlacement) {
            this.allowedPlacement = allowedPlacement;
            return this;
        }

        /**
         * Sets whether layers are removed from the start of the column when space is restricted.
         * @param prioritizeTip whether the tip is prioritized
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder prioritizeTip(boolean prioritizeTip) {
            this.prioritizeTip = prioritizeTip;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds one or more layers to the column.
         * @param layers the layers to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layers(Layer... layers) {
            this.layers.addAll(List.of(layers));
            return this;
        }

        /**
         * Adds a single layer to the column.
         * @param height the height of the layer
         * @param state the block state provider used for the layer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layer(IntProvider height, BlockStateProvider state) {
            this.layers.add(new Layer(height, state));
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public BlockColumnConfiguration build() {
            return of(layers, direction, allowedPlacement, prioritizeTip);
        }
    }
}
