package dev.wyck.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The mangrove root placement parameters used by a {@link MangroveRootPlacer}.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Root_placer">Tree definition (Root placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MangroveRootPlacement extends Wrapper {

    /**
     * The blocks that roots can grow through.
     * @return the blocks roots can grow through
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> canGrowThrough();

    /**
     * The blocks in which roots turn into muddy root blocks.
     * @return the blocks that turn roots muddy
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> muddyRootsIn();

    /**
     * The blocks used as muddy roots.
     * @return the muddy roots block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider muddyRootsProvider();

    /**
     * The maximum width of the root, between 1 and 12 (inclusive).
     * @return the maximum root width
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxRootWidth();

    /**
     * The maximum length of the root, between 1 and 64 (inclusive).
     * @return the maximum root length
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxRootLength();

    /**
     * The probability of random skew, between 0.0F and 1.0F (inclusive).
     * @return the random skew chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float randomSkewChance();

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
     * Creates a new mangrove root placement.
     * @param canGrowThrough the blocks that roots can grow through
     * @param muddyRootsIn the blocks in which roots turn into muddy root blocks
     * @param muddyRootsProvider the blocks used as muddy roots
     * @param maxRootWidth the maximum width of the root, between 1 and 12 (inclusive)
     * @param maxRootLength the maximum length of the root, between 1 and 64 (inclusive)
     * @return a new mangrove root placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MangroveRootPlacement of(Set<Material> canGrowThrough, Set<Material> muddyRootsIn, BlockStateProvider muddyRootsProvider, int maxRootWidth, int maxRootLength) {
        return of(canGrowThrough, muddyRootsIn, muddyRootsProvider, maxRootWidth, maxRootLength, 0.0F);
    }

    @ApiStatus.Internal
    static MangroveRootPlacement of(Set<Material> canGrowThrough, Set<Material> muddyRootsIn, BlockStateProvider muddyRootsProvider, int maxRootWidth, int maxRootLength, float randomSkewChance) {
        record Holder() {
            static final ConstructWireProvider<MangroveRootPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacementImpl");
        }
        return Holder.WIRE.construct(canGrowThrough, muddyRootsIn, muddyRootsProvider, maxRootWidth, maxRootLength, randomSkewChance);
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
     * Builder for {@link MangroveRootPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private Set<Material> canGrowThrough = new HashSet<>();
        private Set<Material> muddyRootsIn = new HashSet<>();
        private @Nullable BlockStateProvider muddyRootsProvider;
        private int maxRootWidth = 1;
        private int maxRootLength = 1;
        private float randomSkewChance = 0.0F;

        private Builder() {}

        private Builder(MangroveRootPlacement rootPlacement) {
            this.canGrowThrough.addAll(rootPlacement.canGrowThrough());
            this.muddyRootsIn.addAll(rootPlacement.muddyRootsIn());
            this.muddyRootsProvider = rootPlacement.muddyRootsProvider();
            this.maxRootWidth = rootPlacement.maxRootWidth();
            this.maxRootLength = rootPlacement.maxRootLength();
        }

        /**
         * Sets the blocks that roots can grow through.
         * @param canGrowThrough the blocks roots can grow through
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canGrowThrough(Set<Material> canGrowThrough) {
            this.canGrowThrough = canGrowThrough;
            return this;
        }

        /**
         * Sets the blocks in which roots turn into muddy root blocks.
         * @param muddyRootsIn the blocks that turn roots muddy
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder muddyRootsIn(Set<Material> muddyRootsIn) {
            this.muddyRootsIn = muddyRootsIn;
            return this;
        }

        /**
         * Sets the blocks used as muddy roots.
         * @param muddyRootsProvider the muddy roots block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder muddyRootsProvider(BlockStateProvider muddyRootsProvider) {
            this.muddyRootsProvider = muddyRootsProvider;
            return this;
        }

        /**
         * Sets the maximum width of the root, between 1 and 12 (inclusive).
         * @param maxRootWidth the maximum root width
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxRootWidth(int maxRootWidth) {
            this.maxRootWidth = maxRootWidth;
            return this;
        }

        /**
         * Sets the maximum length of the root, between 1 and 64 (inclusive).
         * @param maxRootLength the maximum root length
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxRootLength(int maxRootLength) {
            this.maxRootLength = maxRootLength;
            return this;
        }

        /**
         * Sets the probability of random skew, between 0.0F and 1.0F (inclusive).
         * @param randomSkewChance the random skew chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder randomSkewChance(float randomSkewChance) {
            this.randomSkewChance = randomSkewChance;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds one or more blocks that roots can grow through.
         * @param canGrowThrough the blocks to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canGrowThrough(Material... canGrowThrough) {
            Collections.addAll(this.canGrowThrough, canGrowThrough);
            return this;
        }

        /**
         * Adds one or more blocks in which roots turn into muddy root blocks.
         * @param muddyRootsIn the blocks to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder muddyRootsIn(Material... muddyRootsIn) {
            Collections.addAll(this.muddyRootsIn, muddyRootsIn);
            return this;
        }

        /**
         * Builds the mangrove root placement.
         * @return a new mangrove root placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MangroveRootPlacement build() {
            Preconditions.checkNotNull(muddyRootsProvider, "muddyRootsProvider must be set");
            Preconditions.checkArgument(maxRootWidth >= 1 && maxRootWidth <= 12, "maxRootWidth must be between 1 and 12");
            Preconditions.checkArgument(maxRootLength >= 1 && maxRootLength <= 64, "maxRootLength must be between 1 and 64");
            Preconditions.checkArgument(randomSkewChance >= 0.0F && randomSkewChance <= 1.0F, "randomSkewChance must be between 0.0F and 1.0F");
            return of(canGrowThrough, muddyRootsIn, muddyRootsProvider, maxRootWidth, maxRootLength, randomSkewChance);
        }
    }
}
