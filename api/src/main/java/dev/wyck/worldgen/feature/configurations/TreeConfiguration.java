package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A tree configuration, as it appears in Minecraft.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition">Tree definition</a>
 * @since 3.0.0
 * @version 3.0.0
 */
@NullMarked
@AsOf("3.0.0")
public interface TreeConfiguration extends FeatureConfiguration {

    /**
     * The block to use for the trunk.
     * @return the trunk provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider trunkProvider();

    /**
     * Defines how the trunk is generated.
     * @return the trunk placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    TrunkPlacer trunkPlacer();

    /**
     * The block to use for the foliage.
     * @return the foliage provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider foliageProvider();

    /**
     * Defines how foliage is generated.
     * @return the foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FoliagePlacer foliagePlacer();

    /**
     * Defines how roots are generated and what blocks to use.
     * @return the root placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<RootPlacer> rootPlacer();

    /**
     * Defines the width of the tree at different heights relative to the lowest trunk block.
     * @return the minimum size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FeatureSize minimumSize();

    /**
     * Decorations to add to the tree apart from the trunk and leaves.
     * This can be empty.
     * @return the decorators
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<TreeDecorator> decorators();

    /**
     * When true, allows the tree to generate even if there are vines blocking it.
     * @return whether to ignore vines
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean ignoreVines();

    /**
     * A rule-based block state provider defining how to replace the block below the trunk.
     * @return the below trunk provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider belowTrunkProvider();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new tree configuration.
     * @param trunkProvider the trunk provider
     * @param trunkPlacer the trunk placer
     * @param foliageProvider the foliage provider
     * @param foliagePlacer the foliage placer
     * @param rootPlacer the root placer
     * @param minimumSize the minimum size
     * @param decorators the decorators
     * @param ignoreVines whether to ignore vines
     * @param belowTrunkProvider the below trunk provider
     * @return a new tree configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TreeConfiguration of(BlockStateProvider trunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, @Nullable RootPlacer rootPlacer, FeatureSize minimumSize, List<TreeDecorator> decorators, boolean ignoreVines, BlockStateProvider belowTrunkProvider) {
        record Holder() {
            static final ConstructWireProvider<TreeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.TreeConfigurationImpl");
        }
        return Holder.WIRE.construct(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, Optional.ofNullable(rootPlacer), minimumSize, decorators, ignoreVines, belowTrunkProvider);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link TreeConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider trunkProvider;
        private @Nullable TrunkPlacer trunkPlacer;
        private @Nullable BlockStateProvider foliageProvider;
        private @Nullable FoliagePlacer foliagePlacer;
        private @Nullable RootPlacer rootPlacer;
        private @Nullable FeatureSize minimumSize;
        private List<TreeDecorator> decorators = new ArrayList<>();
        private boolean ignoreVines;
        private @Nullable BlockStateProvider belowTrunkProvider;

        public Builder() {}

        public Builder(TreeConfiguration configuration) {
            this.trunkProvider = configuration.trunkProvider();
            this.trunkPlacer = configuration.trunkPlacer();
            this.foliageProvider = configuration.foliageProvider();
            this.foliagePlacer = configuration.foliagePlacer();
            this.rootPlacer = configuration.rootPlacer().orElse(null);
            this.minimumSize = configuration.minimumSize();
            this.decorators.addAll(configuration.decorators());
            this.ignoreVines = configuration.ignoreVines();
            this.belowTrunkProvider = configuration.belowTrunkProvider();
        }

        /**
         * Sets the trunk provider.
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
         * Sets the trunk placer.
         * @param trunkPlacer the trunk placer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder trunkPlacer(TrunkPlacer trunkPlacer) {
            this.trunkPlacer = trunkPlacer;
            return this;
        }

        /**
         * Sets the foliage provider.
         * @param foliageProvider the foliage provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageProvider(BlockStateProvider foliageProvider) {
            this.foliageProvider = foliageProvider;
            return this;
        }

        /**
         * Sets the foliage placer.
         * @param foliagePlacer the foliage placer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliagePlacer(FoliagePlacer foliagePlacer) {
            this.foliagePlacer = foliagePlacer;
            return this;
        }

        /**
         * Sets the root placer.
         * @param rootPlacer the root placer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootPlacer(@Nullable RootPlacer rootPlacer) {
            this.rootPlacer = rootPlacer;
            return this;
        }

        /**
         * Sets the minimum size.
         * @param minimumSize the minimum size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minimumSize(FeatureSize minimumSize) {
            this.minimumSize = minimumSize;
            return this;
        }

        /**
         * Sets the decorators.
         * @param decorators the decorators
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder decorators(List<TreeDecorator> decorators) {
            this.decorators = decorators;
            return this;
        }

        /**
         * Sets whether to ignore vines.
         * @param ignoreVines whether to ignore vines
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder ignoreVines(boolean ignoreVines) {
            this.ignoreVines = ignoreVines;
            return this;
        }

        /**
         * Sets the below trunk provider.
         * @param belowTrunkProvider the below trunk provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder belowTrunkProvider(BlockStateProvider belowTrunkProvider) {
            this.belowTrunkProvider = belowTrunkProvider;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a decorator to the list of decorators.
         * @param decorators the decorators to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder decorator(TreeDecorator... decorators) {
            Collections.addAll(this.decorators, decorators);
            return this;
        }

        /**
         * Sets the tree to ignore vines.
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder ignoreVines() {
            this.ignoreVines = true;
            return this;
        }

        /**
         * Builds the tree configuration.
         * @return the tree configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public TreeConfiguration build() {
            Preconditions.checkNotNull(trunkProvider, "trunkProvider must be set");
            Preconditions.checkNotNull(trunkPlacer, "trunkPlacer must be set");
            Preconditions.checkNotNull(foliageProvider, "foliageProvider must be set");
            Preconditions.checkNotNull(foliagePlacer, "foliagePlacer must be set");
            Preconditions.checkNotNull(minimumSize, "minimumSize must be set");
            Preconditions.checkNotNull(belowTrunkProvider, "belowTrunkProvider must be set");
            return of(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, rootPlacer, minimumSize, decorators, ignoreVines, belowTrunkProvider);
        }
    }
}
