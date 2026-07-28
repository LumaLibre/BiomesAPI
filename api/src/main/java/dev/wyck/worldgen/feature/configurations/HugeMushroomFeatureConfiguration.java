package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Tree-like features that consist of mushroom blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Huge_Mushroom">Huge Mushroom</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface HugeMushroomFeatureConfiguration extends FeatureConfiguration {

    /**
     * The block to use for the cap.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider capProvider();

    /**
     * The block to use for the stem.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider stemProvider();

    /**
     * The size of the cap.
     * @return the radius of the cap
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int foliageRadius();

    /**
     * The predicate that defines which blocks the huge mushroom can be placed on.
     * @return the predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate canPlaceOn();

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
     * Creates a new huge mushroom feature configuration.
     * @param capProvider the block state provider for the cap
     * @param stemProvider the block state provider for the stem
     * @param foliageRadius the radius of the cap
     * @param canPlaceOn the predicate that defines which blocks the huge mushroom can be placed on
     * @return a new huge mushroom feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HugeMushroomFeatureConfiguration of(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius, BlockPredicate canPlaceOn) {
        record Holder() {
            static final ConstructWireProvider<HugeMushroomFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.HugeMushroomFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(capProvider, stemProvider, foliageRadius, canPlaceOn);
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
     * Builder for {@link HugeMushroomFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider capProvider;
        private @Nullable BlockStateProvider stemProvider;
        private int foliageRadius = 2;
        private @Nullable BlockPredicate canPlaceOn;

        public Builder() {}

        public Builder(HugeMushroomFeatureConfiguration configuration) {
            this.capProvider = configuration.capProvider();
            this.stemProvider = configuration.stemProvider();
            this.foliageRadius = configuration.foliageRadius();
            this.canPlaceOn = configuration.canPlaceOn();
        }

        /**
         * Sets the block state provider for the cap.
         * @param capProvider the block state provider for the cap
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder capProvider(BlockStateProvider capProvider) {
            this.capProvider = capProvider;
            return this;
        }

        /**
         * Sets the block state provider for the stem.
         * @param stemProvider the block state provider for the stem
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stemProvider(BlockStateProvider stemProvider) {
            this.stemProvider = stemProvider;
            return this;
        }

        /**
         * Sets the radius of the cap.
         * @param foliageRadius the radius of the cap
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageRadius(int foliageRadius) {
            this.foliageRadius = foliageRadius;
            return this;
        }

        /**
         * Sets the predicate that defines which blocks the mushroom can be placed on.
         * @param canPlaceOn the predicate that defines which blocks the mushroom can be placed on
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOn(BlockPredicate canPlaceOn) {
            this.canPlaceOn = canPlaceOn;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public HugeMushroomFeatureConfiguration build() {
            Preconditions.checkNotNull(capProvider, "capProvider must be set");
            Preconditions.checkNotNull(stemProvider, "stemProvider must be set");
            Preconditions.checkNotNull(canPlaceOn, "canPlaceOn must be set");
            return of(capProvider, stemProvider, foliageRadius, canPlaceOn);
        }
    }
}