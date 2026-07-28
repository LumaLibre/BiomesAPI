package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.ruletest.RuleTest;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A feature consisting of a natural deposit of ores or other blocks in the ground.
 *
 * @see <a href="https://minecraft.wiki/w/Ore_(feature)">Ore (feature)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface OreConfiguration extends FeatureConfiguration {

    /**
     * A list of targets.
     * @return the list of targets
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<TargetBlockState> targetStates();

    /**
     * Determines the size of the ore vein between 0 and 64.
     * @see <a href="https://minecraft.wiki/w/Ore_(feature)#Generation">Ore (feature) - Generation</a>
     * @return the size of the ore vein
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int size();

    /**
     * The chance for the entire ore vein to be discarded if any constituent block is adjacent to an air block between 0.0 and 1.0.
     * Other non-solid blocks such as water do not count.
     * @return the discard chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float discardChanceOnAirExposure();

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
     * Creates a new ore configuration.
     * @param targetStates the list of targets
     * @param size the size of the ore vein
     * @param discardChanceOnAirExposure the discard chance
     * @return a new ore configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static OreConfiguration of(List<TargetBlockState> targetStates, int size, float discardChanceOnAirExposure) {
        record Holder() {
            static final ConstructWireProvider<OreConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.OreConfigurationImpl");
        }
        return Holder.WIRE.construct(targetStates, size, discardChanceOnAirExposure);
    }

    /**
     * Creates a new target block state.
     * @param target the target rule test
     * @param state the block state
     * @return a new target block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TargetBlockState target(RuleTest target, BlockData state) {
        return new TargetBlockState(target, state);
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
     * A target block state.
     * @param target A rule test to check the block to replace.
     * @param state The block to use.
     */
    @AsOf("3.0.0")
    record TargetBlockState(RuleTest target, BlockData state) {
        public TargetBlockState {
            Preconditions.checkNotNull(target, "target");
            Preconditions.checkNotNull(state, "state");
        }
    }

    /**
     * Builder for {@link OreConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<TargetBlockState> targetStates = new ArrayList<>();
        private int size = 0;
        private float discardChanceOnAirExposure = 0.0F;

        public Builder() {}

        public Builder(OreConfiguration configuration) {
            this.targetStates.addAll(configuration.targetStates());
            this.size = configuration.size();
            this.discardChanceOnAirExposure = configuration.discardChanceOnAirExposure();
        }

        /**
         * Sets the list of targets.
         * @param targetStates the list of targets
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targetStates(List<TargetBlockState> targetStates) {
            this.targetStates = targetStates;
            return this;
        }

        /**
         * Sets the size of the ore vein.
         * @param size the size of the ore vein
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the discard chance.
         * @param discardChanceOnAirExposure the discard chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder discardChanceOnAirExposure(float discardChanceOnAirExposure) {
            this.discardChanceOnAirExposure = discardChanceOnAirExposure;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a target block state.
         * @param target the target rule test
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(RuleTest target, BlockData state) {
            this.targetStates.add(new TargetBlockState(target, state));
            return this;
        }

        /**
         * Adds multiple target block states.
         * @param targetStates the target block states to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(TargetBlockState... targetStates) {
            Collections.addAll(this.targetStates, targetStates);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public OreConfiguration build() {
            Preconditions.checkArgument(!targetStates.isEmpty(), "targetStates must not be empty");
            Preconditions.checkArgument(size >= 0 && size <= 64, "size must be between 0 and 64");
            Preconditions.checkArgument(discardChanceOnAirExposure >= 0.0F && discardChanceOnAirExposure <= 1.0F, "discardChanceOnAirExposure must be between 0.0 and 1.0");
            return of(targetStates, size, discardChanceOnAirExposure);
        }
    }
}