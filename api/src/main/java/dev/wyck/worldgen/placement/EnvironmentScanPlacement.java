package dev.wyck.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import org.bukkit.block.BlockFace;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Scans blocks either up or down, until the target condition is met.
 * Returns the block position for which the target condition matches.
 * <p>
 * If no target can be found within the maximum number of steps, returns empty.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface EnvironmentScanPlacement extends PlacementModifier {

    /**
     * One of <code>up</code> or <code>down</code>.
     * @return the direction of search
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockFace directionOfSearch();

    /**
     * The block predicate that is searched for.
     * @return the target condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate targetCondition();

    /**
     * Each step must match this block predicate to continue the scan.
     * If a block that doesn't match it is met, but no target block is found, this returns empty.
     * @return the allowed search condition
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate allowedSearchCondition();

    /**
     * Maximum number of steps to search between 1 and 32.
     * @return the maximum number of steps
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxSteps();

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
     * Creates a new environment scan placement.
     * @param directionOfSearch one of <code>up</code> or <code>down</code>
     * @param targetCondition the block predicate that is searched for
     * @param allowedSearchCondition each step must match this block predicate to continue the scan
     * @param maxSteps maximum number of steps to search between 1 and 32
     * @return a new environment scan placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EnvironmentScanPlacement of(BlockFace directionOfSearch, BlockPredicate targetCondition, BlockPredicate allowedSearchCondition, int maxSteps) {
        record Holder() {
            static final ConstructWireProvider<EnvironmentScanPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.EnvironmentScanPlacementImpl");
        }
        return Holder.WIRE.construct(directionOfSearch, targetCondition, allowedSearchCondition, maxSteps);
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
     * Builder for {@link EnvironmentScanPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private BlockFace directionOfSearch = BlockFace.UP;
        private @Nullable BlockPredicate targetCondition;
        private BlockPredicate allowedSearchCondition = BlockPredicate.alwaysTrue();
        private int maxSteps = 1;

        public Builder() {}

        public Builder(EnvironmentScanPlacement configuration) {
            this.directionOfSearch = configuration.directionOfSearch();
            this.targetCondition = configuration.targetCondition();
            this.allowedSearchCondition = configuration.allowedSearchCondition();
            this.maxSteps = configuration.maxSteps();
        }

        /**
         * Sets the direction of search.
         * @param directionOfSearch one of <code>up</code> or <code>down</code>
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder directionOfSearch(BlockFace directionOfSearch) {
            this.directionOfSearch = directionOfSearch;
            return this;
        }

        /**
         * Sets the target condition.
         * @param targetCondition the block predicate that is searched for
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targetCondition(BlockPredicate targetCondition) {
            this.targetCondition = targetCondition;
            return this;
        }

        /**
         * Sets the allowed search condition.
         * @param allowedSearchCondition each step must match this block predicate to continue the scan
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedSearchCondition(BlockPredicate allowedSearchCondition) {
            this.allowedSearchCondition = allowedSearchCondition;
            return this;
        }

        /**
         * Sets the maximum number of steps.
         * @param maxSteps maximum number of steps to search between 1 and 32
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxSteps(int maxSteps) {
            this.maxSteps = maxSteps;
            return this;
        }

        /**
         * Builds the environment scan placement.
         * @return the environment scan placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public EnvironmentScanPlacement build() {
            Preconditions.checkArgument(directionOfSearch == BlockFace.UP || directionOfSearch == BlockFace.DOWN, "directionOfSearch must be either up or down");
            Preconditions.checkNotNull(targetCondition, "targetCondition must be set");
            Preconditions.checkState(maxSteps >= 1 && maxSteps <= 32, "maxSteps must be between 1 and 32");

            return of(directionOfSearch, targetCondition, allowedSearchCondition, maxSteps);
        }
    }
}
