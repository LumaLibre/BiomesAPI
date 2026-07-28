package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A lake is a small, widespread, naturally-generated feature in the Overworld that contains a volume of liquid.
 * In vanilla, these only consist of lava as aquifers replaced water lakes in 1.18.
 *
 * @see <a href="https://minecraft.wiki/w/Lava_Lake">Lava Lake</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface LakeFeatureConfiguration extends FeatureConfiguration {

    /**
     * The block to use for the fluid of the lake.
     * @return the fluid provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider fluid();

    /**
     * The block to use for the barrier of the lake.
     * If {@code air} is specified, the barrier remains unchanged instead of overwriting any existing blocks.
     * @return the barrier provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider barrier();

    /**
     * Describes the block that the feature can be placed on.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canPlaceFeature();

    /**
     * Describes the block that the feature can replace with air or the provided {@code fluid} block.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canReplaceWithAirOrFluid();

    /**
     * Describes the block that the feature can replace with the provided {@code barrier} block.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canReplaceWithBarrier();

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
     * Creates a new lake feature configuration.
     * @param fluid the fluid provider
     * @param barrier the barrier provider
     * @param canPlaceOn the predicate describing the block that the feature can be placed on
     * @param canReplaceWithAirOrFluid the predicate describing the block that the feature can replace with air or the provided {@code fluid} block
     * @param canReplaceWithBarrier the predicate describing the block that the feature can replace with the provided {@code barrier} block
     * @return a new lake feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LakeFeatureConfiguration create(BlockStateProvider fluid, BlockStateProvider barrier, BlockPredicate canPlaceOn, BlockPredicate canReplaceWithAirOrFluid, BlockPredicate canReplaceWithBarrier) {
        record Holder() {
            static final ConstructWireProvider<LakeFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.LakeFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(fluid, barrier, canPlaceOn, canReplaceWithAirOrFluid, canReplaceWithBarrier);
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
     * Builder for {@link LakeFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider fluid;
        private @Nullable BlockStateProvider barrier;
        private @Nullable BlockPredicate canPlaceOn;
        private @Nullable BlockPredicate canReplaceWithAirOrFluid;
        private @Nullable BlockPredicate canReplaceWithBarrier;

        public Builder() {}

        public Builder(LakeFeatureConfiguration configuration) {
            this.fluid = configuration.fluid();
            this.barrier = configuration.barrier();
            this.canPlaceOn = configuration.canPlaceFeature();
            this.canReplaceWithAirOrFluid = configuration.canReplaceWithAirOrFluid();
            this.canReplaceWithBarrier = configuration.canReplaceWithBarrier();
        }

        /**
         * Sets the fluid provider.
         * @param fluid the fluid provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluid(BlockStateProvider fluid) {
            this.fluid = fluid;
            return this;
        }

        /**
         * Sets the barrier provider.
         * @param barrier the barrier provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder barrier(BlockStateProvider barrier) {
            this.barrier = barrier;
            return this;
        }

        /**
         * Sets the predicate describing the block that the feature can be placed on.
         * @param canPlaceOn the placement predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOn(BlockPredicate canPlaceOn) {
            this.canPlaceOn = canPlaceOn;
            return this;
        }

        /**
         * Sets the predicate describing the block that the feature can replace with air or the provided {@code fluid} block.
         * @param canReplaceWithAirOrFluid the predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canReplaceWithAirOrFluid(BlockPredicate canReplaceWithAirOrFluid) {
            this.canReplaceWithAirOrFluid = canReplaceWithAirOrFluid;
            return this;
        }

        /**
         * Sets the predicate describing the block that the feature can replace with the provided {@code barrier} block.
         * @param canReplaceWithBarrier the predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canReplaceWithBarrier(BlockPredicate canReplaceWithBarrier) {
            this.canReplaceWithBarrier = canReplaceWithBarrier;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public LakeFeatureConfiguration build() {
            Preconditions.checkNotNull(fluid, "fluid must be set");
            Preconditions.checkNotNull(barrier, "barrier must be set");
            // these can be null on older versions
            //Preconditions.checkNotNull(canPlaceOn, "canPlaceOn must be set");
            //Preconditions.checkNotNull(canReplaceWithAirOrFluid, "canReplaceWithAirOrFluid must be set");
            //Preconditions.checkNotNull(canReplaceWithBarrier, "canReplaceWithBarrier must be set");
            //noinspection ConstantConditions
            return create(fluid, barrier, canPlaceOn, canReplaceWithAirOrFluid, canReplaceWithBarrier);
        }
    }
}
