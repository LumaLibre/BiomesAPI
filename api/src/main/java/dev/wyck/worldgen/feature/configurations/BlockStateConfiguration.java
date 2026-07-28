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
 * Abstract block state configuration.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockStateConfiguration extends FeatureConfiguration {

    /**
     * The block state used by this configuration.
     * @return the block state
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
     * Creates a new block state configuration.
     * @param state the block state used by this configuration
     * @return a new block state configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockStateConfiguration of(BlockData state) {
        record Holder() {
            static final ConstructWireProvider<BlockStateConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.BlockStateConfigurationImpl");
        }
        return Holder.WIRE.construct(state);
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
     * Builder for {@link BlockStateConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData state;

        public Builder() {}

        public Builder(BlockStateConfiguration configuration) {
            this.state = configuration.state();
        }

        /**
         * Sets the block state used by this configuration.
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the block state used by this configuration from the given material's default block data.
         * @param material the material to derive the block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.state = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Builds the block state configuration.
         * @return the block state configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public BlockStateConfiguration build() {
            Preconditions.checkNotNull(state, "state must be set");
            return of(state);
        }
    }
}