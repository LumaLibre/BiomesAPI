package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A feature that resembles a tree lying on the ground after falling over.
 *
 * @see <a href="https://minecraft.wiki/w/Fallen_Tree">Fallen Tree</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FallenTreeConfiguration extends FeatureConfiguration {

    /**
     * The block state provider used for the fallen tree's trunk.
     * @return the trunk provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider trunkProvider();

    /**
     * The length of the fallen log. Value between 0 and 16.
     * @return the log length
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider logLength();

    /**
     * The decorators applied to the stump of the fallen tree.
     * @return an immutable list of stump decorators
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<TreeDecorator> stumpDecorators();

    /**
     * The decorators applied to the fallen log of the tree.
     * @return an immutable list of log decorators
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<TreeDecorator> logDecorators();

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
     * Creates a new fallen tree configuration.
     * @param trunkProvider the block state provider used for the trunk
     * @param logLength the length of the fallen log
     * @param stumpDecorators the decorators applied to the stump
     * @param logDecorators the decorators applied to the fallen log
     * @return a new fallen tree configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FallenTreeConfiguration of(BlockStateProvider trunkProvider, IntProvider logLength, List<TreeDecorator> stumpDecorators, List<TreeDecorator> logDecorators) {
        record Holder() {
            static final ConstructWireProvider<FallenTreeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.FallenTreeConfigurationImpl");
        }
        return Holder.WIRE.construct(trunkProvider, logLength, stumpDecorators, logDecorators);
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
     * Builder for {@link FallenTreeConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider trunkProvider;
        private @Nullable IntProvider logLength;
        private List<TreeDecorator> stumpDecorators = new ArrayList<>();
        private List<TreeDecorator> logDecorators = new ArrayList<>();

        public Builder() {}

        public Builder(FallenTreeConfiguration configuration) {
            this.trunkProvider = configuration.trunkProvider();
            this.logLength = configuration.logLength();
            this.stumpDecorators.addAll(configuration.stumpDecorators());
            this.logDecorators.addAll(configuration.logDecorators());
        }

        /**
         * Sets the block state provider used for the trunk.
         * @param trunkProvider the trunk provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder trunkProvider(BlockStateProvider trunkProvider) {
            this.trunkProvider = trunkProvider;
            return this;
        }

        /**
         * Sets the length of the fallen log.
         * @param logLength the log length
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder logLength(IntProvider logLength) {
            this.logLength = logLength;
            return this;
        }

        /**
         * Sets the decorators applied to the stump, replacing any existing stump decorators.
         * @param stumpDecorators the stump decorators
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stumpDecorators(List<TreeDecorator> stumpDecorators) {
            this.stumpDecorators = stumpDecorators;
            return this;
        }

        /**
         * Sets the decorators applied to the fallen log, replacing any existing log decorators.
         * @param logDecorators the log decorators
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder logDecorators(List<TreeDecorator> logDecorators) {
            this.logDecorators = logDecorators;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds one or more decorators to the stump.
         * @param decorators the stump decorators to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stumpDecorator(TreeDecorator... decorators) {
            Collections.addAll(this.stumpDecorators, decorators);
            return this;
        }

        /**
         * Adds one or more decorators to the fallen log.
         * @param decorators the log decorators to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder logDecorator(TreeDecorator... decorators) {
            Collections.addAll(this.logDecorators, decorators);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FallenTreeConfiguration build() {
            Preconditions.checkNotNull(trunkProvider, "trunkProvider must be set");
            Preconditions.checkNotNull(logLength, "logLength must be set");
            Preconditions.checkArgument(logLength.minInclusive() >= 0 && logLength.maxInclusive() <= 16, "logLength must be between 0 and 16");
            return of(trunkProvider, logLength, stumpDecorators, logDecorators);
        }
    }
}