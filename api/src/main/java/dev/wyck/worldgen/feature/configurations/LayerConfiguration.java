package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A technical feature that fills all air blocks in a 16×1×16 layer with a specified block.
 * Used in vanilla in superflat worlds.
 *
 * @see <a href="https://minecraft.wiki/w/Fill_layer">Fill Layer</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface LayerConfiguration extends FeatureConfiguration {

    /**
     * The layer to fill, starting at the bottom of the world between 0 and 4064.
     * @return the layer to fill
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int height();

    /**
     * The block to fill the layer with.
     * @return the block data to fill the layer with
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData state();

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
     * Creates a new layer configuration.
     * @param height the layer to fill, starting at the bottom of the world between 0 and 4064
     * @param state the block to fill the layer with
     * @return a new layer configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LayerConfiguration of(int height, BlockData state) {
        record Holder() {
            static final ConstructWireProvider<LayerConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.LayerConfigurationImpl");
        }
        return Holder.WIRE.construct(height, state);
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
     * Builder for {@link LayerConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int height = 0;
        private @Nullable BlockData state;

        public Builder() {}

        public Builder(LayerConfiguration configuration) {
            this.height = configuration.height();
            this.state = configuration.state();
        }

        /**
         * Sets the height of the layer.
         * @param height the height of the layer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the block to fill the layer with.
         * @param state the block to fill the layer with
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.state = state;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block to fill the layer with.
         * @param material the block to fill the layer with
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.state = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public LayerConfiguration build() {
            Preconditions.checkArgument(height >= 0, "height must not be negative");
            Preconditions.checkNotNull(state, "state must be set");
            return of(height, state);
        }
    }
}