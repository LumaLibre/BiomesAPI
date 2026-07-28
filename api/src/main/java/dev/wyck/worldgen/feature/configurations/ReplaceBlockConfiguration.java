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
 * A technical feature that replaces a single block using a list of targets and states.
 * Unused in vanilla.
 *
 * @see <a href="https://minecraft.wiki/w/Replace_Single_Block">Replace Single Block</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReplaceBlockConfiguration extends FeatureConfiguration {

    /**
     * A list of targets. This can be empty.
     * @return the list of targets
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<OreConfiguration.TargetBlockState> targetStates();

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
     * Creates a new replacement block configuration.
     * @param targetStates the list of targets
     * @return a new replacement block configuration
     */
    @AsOf("3.0.0")
    static ReplaceBlockConfiguration of(List<OreConfiguration.TargetBlockState> targetStates) {
        record Holder() {
            static final ConstructWireProvider<ReplaceBlockConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.ReplaceBlockConfigurationImpl");
        }
        return Holder.WIRE.construct(targetStates);
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
     * Builder for {@link ReplaceBlockConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<OreConfiguration.TargetBlockState> targetStates = new ArrayList<>();

        public Builder() {}

        public Builder(ReplaceBlockConfiguration configuration) {
            this.targetStates.addAll(configuration.targetStates());
        }

        /**
         * Sets the list of targets.
         * @param targetStates the list of targets
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targetStates(List<OreConfiguration.TargetBlockState> targetStates) {
            this.targetStates = targetStates;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a target to the list.
         * @param target the target to add
         * @param state the state to replace with
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(RuleTest target, BlockData state) {
            this.targetStates.add(new OreConfiguration.TargetBlockState(target, state));
            return this;
        }

        /**
         * Adds multiple targets to the list.
         * @param targetStates the targets to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder target(OreConfiguration.TargetBlockState... targetStates) {
            Collections.addAll(this.targetStates, targetStates);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ReplaceBlockConfiguration build() {
            Preconditions.checkArgument(!targetStates.isEmpty(), "targetStates must not be empty");
            return of(targetStates);
        }
    }
}