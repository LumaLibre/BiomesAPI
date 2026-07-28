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
 * An ice spike.
 *
 * @see <a href="https://minecraft.wiki/w/Ice_Spike">Ice Spike</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SpikeConfiguration extends FeatureConfiguration {

    /**
     * The block to use.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData state();

    /**
     * The predicate that defines which blocks the spike can be placed on.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canPlaceOn();

    /**
     * The predicate that defines which blocks the spike can replace.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canReplace();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new spike configuration.
     * @param state the block state
     * @param canPlaceOn the predicate that defines which blocks the spike can be placed on
     * @param canReplace the predicate that defines which blocks the spike can replace
     * @return a new spike configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpikeConfiguration of(BlockData state, BlockPredicate canPlaceOn, BlockPredicate canReplace) {
        record Holder() {
            static final ConstructWireProvider<SpikeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.SpikeConfigurationImpl");
        }
        return Holder.WIRE.construct(state, canPlaceOn, canReplace);
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
     * Builder for {@link SpikeConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData state;
        private @Nullable BlockPredicate canPlaceOn;
        private @Nullable BlockPredicate canReplace;

        public Builder() {}

        public Builder(SpikeConfiguration configuration) {
            this.state = configuration.state();
            this.canPlaceOn = configuration.canPlaceOn();
            this.canReplace = configuration.canReplace();
        }

        /**
         * Sets the block state.
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
         * Sets the can place on predicate.
         * @param canPlaceOn the can place on predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOn(BlockPredicate canPlaceOn) {
            this.canPlaceOn = canPlaceOn;
            return this;
        }

        /**
         * Sets the can replace predicate.
         * @param canReplace the can replace predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canReplace(BlockPredicate canReplace) {
            this.canReplace = canReplace;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block state.
         * @param material the block state
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
        public SpikeConfiguration build() {
            Preconditions.checkNotNull(state, "state must be set");
            Preconditions.checkNotNull(canPlaceOn, "canPlaceOn must be set");
            Preconditions.checkNotNull(canReplace, "canReplace must be set");
            return of(state, canPlaceOn, canReplace);
        }
    }
}