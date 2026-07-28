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
 * A block blob.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockBlobConfiguration extends FeatureConfiguration {

    /**
     * The block state that makes up the blob.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData state();

    /**
     * The block predicate that must be passed for the blob to be placed on a block.
     * @return the placement predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canPlaceOn();

    /**
     * Creates a new builder from this configuration.
     * @return a new builder with the same values as this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new block blob configuration.
     * @param state the block state that makes up the blob
     * @param canPlaceOn the block predicate that must be passed for the blob to be placed on a block
     * @return a new block blob configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockBlobConfiguration of(BlockData state, BlockPredicate canPlaceOn) {
        record Holder() {
            static final ConstructWireProvider<BlockBlobConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.BlockBlobConfigurationImpl");
        }
        return Holder.WIRE.construct(state, canPlaceOn);
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
     * Builder for {@link BlockBlobConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData state;
        private @Nullable BlockPredicate canPlaceOn;

        public Builder() {}

        public Builder(BlockBlobConfiguration configuration) {
            this.state = configuration.state();
            this.canPlaceOn = configuration.canPlaceOn();
        }

        /**
         * Sets the block state that makes up the blob.
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
         * Sets the block predicate that must be passed for the blob to be placed on a block.
         * @param canPlaceOn the placement predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOn(BlockPredicate canPlaceOn) {
            this.canPlaceOn = canPlaceOn;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block state that makes up the blob from the given material's default block data.
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
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        public BlockBlobConfiguration build() {
            Preconditions.checkNotNull(state, "state must be set");
            Preconditions.checkNotNull(canPlaceOn, "canPlaceOn must be set");
            return of(state, canPlaceOn);
        }
    }
}
