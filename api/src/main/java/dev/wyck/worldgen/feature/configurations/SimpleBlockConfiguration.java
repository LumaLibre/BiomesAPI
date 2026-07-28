package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A feature that places a block based on a block state provider.
 *
 * @see <a href="https://minecraft.wiki/w/Simple_block">Simple block</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SimpleBlockConfiguration extends FeatureConfiguration {

    /**
     * The block to use.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider toPlace();

    /**
     * Whether to schedule a block update.
     * @return whether to schedule a block update
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean scheduleTick();

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
     * Creates a new simple block configuration.
     * @param toPlace the block state provider
     * @param scheduleTick whether to schedule a block update
     * @return a new simple block configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleBlockConfiguration of(BlockStateProvider toPlace, boolean scheduleTick) {
        record Holder() {
            static final ConstructWireProvider<SimpleBlockConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.SimpleBlockConfigurationImpl");
        }
        return Holder.WIRE.construct(toPlace, scheduleTick);
    }

    /**
     * Creates a new simple block configuration.
     * @param toPlace the block state provider
     * @return a new simple block configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleBlockConfiguration of(BlockStateProvider toPlace) {
        return of(toPlace, false);
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
     * Builder for {@link SimpleBlockConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider toPlace;
        private boolean scheduleTick = false;

        public Builder() {}

        public Builder(SimpleBlockConfiguration configuration) {
            this.toPlace = configuration.toPlace();
            this.scheduleTick = configuration.scheduleTick();
        }

        /**
         * Sets the block state provider.
         * @param toPlace the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder toPlace(BlockStateProvider toPlace) {
            this.toPlace = toPlace;
            return this;
        }

        /**
         * Sets whether to schedule a block update.
         * @param scheduleTick whether to schedule a block update
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder scheduleTick(boolean scheduleTick) {
            this.scheduleTick = scheduleTick;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SimpleBlockConfiguration build() {
            Preconditions.checkNotNull(toPlace, "toPlace must be set");
            return of(toPlace, scheduleTick);
        }
    }
}