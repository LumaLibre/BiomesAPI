package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A feature consisting of a naturally generated roughly circular deposit of a soil-like block under or next to bodies of water.
 *
 * @see <a href="https://minecraft.wiki/w/Disk">Disk</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DiskConfiguration extends FeatureConfiguration {

    /**
     * The block state provider used for the blocks that make up the disk.
     * @return the state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider stateProvider();

    /**
     * The block predicate that must be passed for a block to be replaced when generating this feature.
     * @return the target predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate target();

    /**
     * The radius of this disk. Value between 0 and 8.
     * @return the radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider radius();

    /**
     * Half of the height of this disk. Value between 0 and 4.
     * @return the half height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int halfHeight();

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
     * Creates a new disk configuration.
     * @param stateProvider the block state provider used for the blocks that make up the disk
     * @param target the block predicate that must be passed for a block to be replaced
     * @param radius the radius of this disk
     * @param halfHeight half of the height of this disk
     * @return a new disk configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DiskConfiguration of(BlockStateProvider stateProvider, BlockPredicate target, IntProvider radius, int halfHeight) {
        record Holder() {
            static final ConstructWireProvider<DiskConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.DiskConfigurationImpl");
        }
        return Holder.WIRE.construct(stateProvider, target, radius, halfHeight);
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
     * Builder for {@link DiskConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider stateProvider;
        private @Nullable BlockPredicate target;
        private @Nullable IntProvider radius;
        private int halfHeight;

        public Builder() {}

        public Builder(DiskConfiguration configuration) {
            this.stateProvider = configuration.stateProvider();
            this.target = configuration.target();
            this.radius = configuration.radius();
            this.halfHeight = configuration.halfHeight();
        }

        /**
         * Sets the block state provider used for the blocks that make up the disk.
         * @param stateProvider the state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stateProvider(BlockStateProvider stateProvider) {
            this.stateProvider = stateProvider;
            return this;
        }

        /**
         * Sets the block predicate that must be passed for a block to be replaced.
         * @param target the target predicate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(BlockPredicate target) {
            this.target = target;
            return this;
        }

        /**
         * Sets the radius of this disk.
         * @param radius the radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder radius(IntProvider radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Sets half of the height of this disk.
         * @param halfHeight the half height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder halfHeight(int halfHeight) {
            this.halfHeight = halfHeight;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public DiskConfiguration build() {
            Preconditions.checkNotNull(stateProvider, "stateProvider must be set");
            Preconditions.checkNotNull(target, "target must be set");
            Preconditions.checkNotNull(radius, "radius must be set");
            Preconditions.checkArgument(radius.minInclusive() >= 0 && radius.maxInclusive() <= 8, "radius must be between 0 and 8");
            Preconditions.checkArgument(halfHeight >= 0 && halfHeight <= 4, "halfHeight must be between 0 and 4");
            return of(stateProvider, target, radius, halfHeight);
        }
    }
}