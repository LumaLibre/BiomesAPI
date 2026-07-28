package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.material.FluidState;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A feature consisting of a single block of liquid that flows out into spaces level with or lower than the source.
 *
 * @see <a href="https://minecraft.wiki/w/Spring">Spring</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SpringConfiguration extends FeatureConfiguration {

    /**
     * Which fluid to use.
     * @return the fluid state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FluidState state();

    /**
     * Whether the spring feature requires a block in {@link #validBlocks()} below it.
     * @return whether the feature requires a block below it
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean requiresBlockBelow();

    /**
     * The required number of blocks adjacent to the spring that belong to {@link #validBlocks()}.
     * The block above is not counted.
     * @return the number of blocks required
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rockCount();

    /**
     * The required number of air blocks adjacent to the spring.
     * The block above is not counted.
     * @return the number of air blocks required
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int holeCount();

    /**
     * A collection of blocks that may be used in other portions
     * of this configuration.
     * @return the valid blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> validBlocks();

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
     * Creates a new instance of this configuration.
     * @param state the fluid state
     * @param requiresBlockBelow whether the spring requires a block below it
     * @param rockCount the number of blocks adjacent to the spring that belong to {@link #validBlocks()}
     * @param holeCount the number of air blocks adjacent to the spring
     * @param validBlocks a collection of blocks that may be used in other portions of this configuration
     * @return a new instance of this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpringConfiguration of(FluidState state, boolean requiresBlockBelow, int rockCount, int holeCount, Set<Material> validBlocks) {
        record Holder() {
            static final ConstructWireProvider<SpringConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.SpringConfigurationImpl");
        }
        return Holder.WIRE.construct(state, requiresBlockBelow, rockCount, holeCount, validBlocks);
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
     * Builder for {@link SpringConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable FluidState state;
        private boolean requiresBlockBelow = true;
        private int rockCount = 4;
        private int holeCount = 1;
        private Set<Material> validBlocks = new HashSet<>();

        public Builder() {}

        public Builder(SpringConfiguration configuration) {
            this.state = configuration.state();
            this.requiresBlockBelow = configuration.requiresBlockBelow();
            this.rockCount = configuration.rockCount();
            this.holeCount = configuration.holeCount();
            this.validBlocks.addAll(configuration.validBlocks());
        }

        /**
         * Sets the fluid state.
         * @param state the fluid state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(FluidState state) {
            this.state = state;
            return this;
        }

        /**
         * Sets whether the spring requires a block below it.
         * @param requiresBlockBelow whether the spring requires a block below it
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder requiresBlockBelow(boolean requiresBlockBelow) {
            this.requiresBlockBelow = requiresBlockBelow;
            return this;
        }

        /**
         * Sets the rock count.
         * @param rockCount the number of blocks adjacent to the spring
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rockCount(int rockCount) {
            this.rockCount = rockCount;
            return this;
        }

        /**
         * Sets the hole count.
         * @param holeCount the number of air blocks adjacent to the spring
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder holeCount(int holeCount) {
            this.holeCount = holeCount;
            return this;
        }

        /**
         * Sets the valid blocks.
         * @param validBlocks the valid blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder validBlocks(Set<Material> validBlocks) {
            this.validBlocks = validBlocks;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a valid block.
         * @param validBlocks the valid blocks
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder validBlock(Material... validBlocks) {
            java.util.Collections.addAll(this.validBlocks, validBlocks);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SpringConfiguration build() {
            Preconditions.checkNotNull(state, "state must be set");
            return of(state, requiresBlockBelow, rockCount, holeCount, validBlocks);
        }
    }
}