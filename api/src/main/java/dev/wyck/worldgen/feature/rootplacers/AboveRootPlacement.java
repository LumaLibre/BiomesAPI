package dev.wyck.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The blocks placed above the root of a tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Root_placer">Tree definition (Root placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface AboveRootPlacement extends Wrapper {

    /**
     * The block above the root.
     * @return the above root block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider aboveRootProvider();

    /**
     * The probability of generating the block, between 0.0F and 1.0F (inclusive).
     * @return the above root placement chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float aboveRootPlacementChance();

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
     * Creates a new above root placement.
     * @param aboveRootProvider the block above the root
     * @param aboveRootPlacementChance the probability of generating the block, between 0.0F and 1.0F (inclusive)
     * @return a new above root placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AboveRootPlacement of(BlockStateProvider aboveRootProvider, float aboveRootPlacementChance) {
        record Holder() {
            static final ConstructWireProvider<AboveRootPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.rootplacers.AboveRootPlacementImpl");
        }
        return Holder.WIRE.construct(aboveRootProvider, aboveRootPlacementChance);
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
     * Builder for {@link AboveRootPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider aboveRootProvider;
        private float aboveRootPlacementChance;

        public Builder() {}

        public Builder(AboveRootPlacement aboveRootPlacement) {
            this.aboveRootProvider = aboveRootPlacement.aboveRootProvider();
            this.aboveRootPlacementChance = aboveRootPlacement.aboveRootPlacementChance();
        }

        /**
         * Sets the block above the root.
         * @param aboveRootProvider the above root block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder aboveRootProvider(BlockStateProvider aboveRootProvider) {
            this.aboveRootProvider = aboveRootProvider;
            return this;
        }

        /**
         * Sets the probability of generating the block, between 0.0F and 1.0F (inclusive).
         * @param aboveRootPlacementChance the above root placement chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder aboveRootPlacementChance(float aboveRootPlacementChance) {
            this.aboveRootPlacementChance = aboveRootPlacementChance;
            return this;
        }

        /**
         * Builds the above root placement.
         * @return a new above root placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public AboveRootPlacement build() {
            Preconditions.checkNotNull(aboveRootProvider, "aboveRootProvider must be set");
            Preconditions.checkArgument(aboveRootPlacementChance >= 0.0F && aboveRootPlacementChance <= 1.0F, "aboveRootPlacementChance must be between 0.0F and 1.0F");
            return AboveRootPlacement.of(aboveRootProvider, aboveRootPlacementChance);
        }
    }
}
