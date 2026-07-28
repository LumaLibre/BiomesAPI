package dev.wyck.worldgen.blockpredicates;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.material.FluidType;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Checks if the fluid at the specified offset is one of the specified fluids.
 *
 * @see <a href="https://minecraft.wiki/w/Block_predicate#matching_fluids">Block predicate (matching fluids)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MatchingFluidsPredicate extends BlockPredicate {

    /**
     * The offset from the block to check.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockVector offset();

    /**
     * The fluids to match against.
     * @return the fluids
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<FluidType> fluids();

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
     * Creates a new matching fluids predicate.
     * @param offset the offset
     * @param fluids the fluids to match against
     * @return the matching fluids predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingFluidsPredicate of(BlockVector offset, List<FluidType> fluids) {
        record Holder() {
            static final ConstructWireProvider<MatchingFluidsPredicate> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.blockpredicates.MatchingFluidsPredicateImpl");
        }
        return Holder.WIRE.construct(offset, fluids);
    }

    /**
     * Creates a new matching fluids predicate with a zero offset.
     * @param fluids the fluids to match against
     * @return the matching fluids predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MatchingFluidsPredicate of(List<FluidType> fluids) {
        return of(new BlockVector(0, 0, 0), fluids);
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

    final class Builder {
        private BlockVector offset = new BlockVector(0, 0, 0);
        private List<FluidType> fluids = new ArrayList<>();

        public Builder() {}

        public Builder(MatchingFluidsPredicate predicate) {
            this.offset = predicate.offset();
            this.fluids.addAll(predicate.fluids());
        }

        /**
         * Sets the offset.
         * @param offset the offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder offset(BlockVector offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Sets the fluids to match against.
         * @param fluids the fluids
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluids(List<FluidType> fluids) {
            this.fluids = fluids;
            return this;
        }

        /**
         * Sets the fluids to match against.
         * @param fluids the fluids
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluids(FluidType... fluids) {
            this.fluids = new ArrayList<>(List.of(fluids));
            return this;
        }

        /**
         * Adds a fluid to match against.
         * @param fluid the fluid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluid(FluidType fluid) {
            this.fluids.add(fluid);
            return this;
        }

        /**
         * Adds multiple fluids to match against.
         * @param fluids the fluids
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluid(FluidType... fluids) {
            Collections.addAll(this.fluids, fluids);
            return this;
        }

        /**
         * Builds the matching fluids predicate.
         * @return the matching fluids predicate
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MatchingFluidsPredicate build() {
            return of(offset, fluids);
        }
    }
}