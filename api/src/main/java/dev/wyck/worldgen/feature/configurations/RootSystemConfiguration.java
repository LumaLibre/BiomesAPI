package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagKey;
import dev.wyck.tags.TagSet;
import dev.wyck.util.Either;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A feature that spawns below naturally generated azalea trees.
 *
 * @see <a href="https://minecraft.wiki/w/Root_System">Root System</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RootSystemConfiguration extends FeatureConfiguration {

    /**
     * The placed feature to place on top of the root system.
     * @return the placed feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    PlacedFeature treeFeature();

    /**
     * The amount of vertical space for the placed feature between 1 and 64.
     * @return the vertical space
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int requiredVerticalSpaceForTree();

    /**
     * The distance to test for the level of the root system between 0 and 64.
     * @return the level test distance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int levelTestDistance();

    /**
     * The maximum level deviation between 1 and 64.
     * @return the maximum level deviation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxLevelDeviation();

    /**
     * The radius of which these roots can spread between 1 and 64.
     * @return the root radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootRadius();

    /**
     * A set of materials which can be replaced by the root system.
     * @return the root replaceable materials
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    TagSet<Material> rootReplaceable();

    /**
     * The block to use for the root column.
     * @return the root state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider rootStateProvider();

    /**
     * The number of attempts to place the root column between 1 and 256.
     * @return the root placement attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootPlacementAttempts();

    /**
     * The maximum height of the root column between 1 and 4096.
     * @return the root column max height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int rootColumnMaxHeight();

    /**
     * The radius of the hanging roots between 1 and 64.
     * @return the hanging root radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootRadius();

    /**
     * The vertical span of the hanging roots between 1 and 16.
     * @return the hanging roots vertical span
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootsVerticalSpan();

    /**
     * The block to use for the hanging roots.
     * @return the hanging root state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider hangingRootStateProvider();

    /**
     * The number of attempts to place the hanging roots between 1 and 256.
     * @return the hanging root placement attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int hangingRootPlacementAttempts();

    /**
     * The amount of vertical water allowed for the placed feature between 1 and 64.
     * @return the vertical water allowed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int allowedVerticalWaterForTree();

    /**
     * The block predicate used to check if the tree position is valid.
     * @return the block predicate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockPredicate allowedTreePosition();

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
     * Creates a new root system configuration.
     * @param treeFeature the placed feature to place on top of the root system
     * @param requiredVerticalSpaceForTree the amount of vertical space for the placed feature
     * @param levelTestDistance the distance to test for the level of the root system
     * @param maxLevelDeviation the maximum level deviation
     * @param rootRadius the radius of which these roots can spread
     * @param rootReplaceable a tag of materials which can be replaced by the root system
     * @param rootStateProvider the block to use for the root column
     * @param rootPlacementAttempts the number of attempts to place the root column
     * @param rootColumnMaxHeight the maximum height of the root column
     * @param hangingRootRadius the radius of the hanging roots
     * @param hangingRootsVerticalSpan the vertical span of the hanging roots
     * @param hangingRootStateProvider the block to use for the hanging roots
     * @param hangingRootPlacementAttempts the number of attempts to place the hanging roots
     * @param allowedVerticalWaterForTree the amount of vertical water allowed for the placed feature
     * @param allowedTreePosition the block predicate used to check if the tree position is valid
     * @return a new root system configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RootSystemConfiguration of(
        PlacedFeature treeFeature,
        int requiredVerticalSpaceForTree,
        int levelTestDistance,
        int maxLevelDeviation,
        int rootRadius,
        TagSet<Material> rootReplaceable,
        BlockStateProvider rootStateProvider,
        int rootPlacementAttempts,
        int rootColumnMaxHeight,
        int hangingRootRadius,
        int hangingRootsVerticalSpan,
        BlockStateProvider hangingRootStateProvider,
        int hangingRootPlacementAttempts,
        int allowedVerticalWaterForTree,
        BlockPredicate allowedTreePosition
    ) {
        record Holder() {
            static final ConstructWireProvider<RootSystemConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.RootSystemConfigurationImpl");
        }
        return Holder.WIRE.construct(treeFeature, requiredVerticalSpaceForTree, levelTestDistance, maxLevelDeviation, rootRadius, rootReplaceable, rootStateProvider, rootPlacementAttempts, rootColumnMaxHeight, hangingRootRadius, hangingRootsVerticalSpan, hangingRootStateProvider, hangingRootPlacementAttempts, allowedVerticalWaterForTree, allowedTreePosition);
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
     * Builder for {@link RootSystemConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable PlacedFeature treeFeature;
        private int requiredVerticalSpaceForTree = 1;
        private int levelTestDistance = 0;
        private int maxLevelDeviation = 1;
        private int rootRadius = 1;
        private Either<Set<Material>, TagKey> rootReplaceable = Either.left(new HashSet<>());
        private @Nullable BlockStateProvider rootStateProvider;
        private int rootPlacementAttempts = 1;
        private int rootColumnMaxHeight = 1;
        private int hangingRootRadius = 1;
        private int hangingRootsVerticalSpan = 1;
        private @Nullable BlockStateProvider hangingRootStateProvider;
        private int hangingRootPlacementAttempts = 1;
        private int allowedVerticalWaterForTree = 1;
        private @Nullable BlockPredicate allowedTreePosition;

        public Builder() {}

        public Builder(RootSystemConfiguration configuration) {
            this.treeFeature = configuration.treeFeature();
            this.requiredVerticalSpaceForTree = configuration.requiredVerticalSpaceForTree();
            this.levelTestDistance = configuration.levelTestDistance();
            this.maxLevelDeviation = configuration.maxLevelDeviation();
            this.rootRadius = configuration.rootRadius();
            this.rootReplaceable = configuration.rootReplaceable().value();
            this.rootStateProvider = configuration.rootStateProvider();
            this.rootPlacementAttempts = configuration.rootPlacementAttempts();
            this.rootColumnMaxHeight = configuration.rootColumnMaxHeight();
            this.hangingRootRadius = configuration.hangingRootRadius();
            this.hangingRootsVerticalSpan = configuration.hangingRootsVerticalSpan();
            this.hangingRootStateProvider = configuration.hangingRootStateProvider();
            this.hangingRootPlacementAttempts = configuration.hangingRootPlacementAttempts();
            this.allowedVerticalWaterForTree = configuration.allowedVerticalWaterForTree();
            this.allowedTreePosition = configuration.allowedTreePosition();
        }

        /**
         * Sets the placed feature to place on top of the root system.
         * @param treeFeature the placed feature to place on top of the root system
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder treeFeature(PlacedFeature treeFeature) {
            this.treeFeature = treeFeature;
            return this;
        }

        /**
         * Sets the amount of vertical space for the placed feature.
         * @param requiredVerticalSpaceForTree the amount of vertical space for the placed feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder requiredVerticalSpaceForTree(int requiredVerticalSpaceForTree) {
            this.requiredVerticalSpaceForTree = requiredVerticalSpaceForTree;
            return this;
        }

        /**
         * Sets the maximum level deviation.
         * @param levelTestDistance the maximum level deviation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder levelTestDistance(int levelTestDistance) {
            this.levelTestDistance = levelTestDistance;
            return this;
        }

        /**
         * Sets the maximum level deviation.
         * @param maxLevelDeviation the maximum level deviation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxLevelDeviation(int maxLevelDeviation) {
            this.maxLevelDeviation = maxLevelDeviation;
            return this;
        }

        /**
         * Sets the radius of which these roots can spread.
         * @param rootRadius the radius of which these roots can spread
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootRadius(int rootRadius) {
            this.rootRadius = rootRadius;
            return this;
        }

        /**
         * Sets the tag of materials which can be replaced by the root system.
         * @param rootReplaceable the tag of materials which can be replaced by the root system
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(Set<Material> rootReplaceable) {
            this.rootReplaceable.leftOverride(rootReplaceable);
            return this;
        }

        /**
         * Sets the tag of materials which can be replaced by the root system.
         * @param rootReplaceable the tag of materials which can be replaced by the root system
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(TagKey rootReplaceable) {
            this.rootReplaceable = Either.right(rootReplaceable);
            return this;
        }

        /**
         * Sets the block to use for the root column.
         * @param rootStateProvider the block to use for the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootStateProvider(BlockStateProvider rootStateProvider) {
            this.rootStateProvider = rootStateProvider;
            return this;
        }

        /**
         * Sets the number of attempts to place the root column.
         * @param rootPlacementAttempts the number of attempts to place the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootPlacementAttempts(int rootPlacementAttempts) {
            this.rootPlacementAttempts = rootPlacementAttempts;
            return this;
        }

        /**
         * Sets the maximum height of the root column.
         * @param rootColumnMaxHeight the maximum height of the root column
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rootColumnMaxHeight(int rootColumnMaxHeight) {
            this.rootColumnMaxHeight = rootColumnMaxHeight;
            return this;
        }

        /**
         * Sets the radius of the hanging roots.
         * @param hangingRootRadius the radius of the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootRadius(int hangingRootRadius) {
            this.hangingRootRadius = hangingRootRadius;
            return this;
        }

        /**
         * Sets the vertical span of the hanging roots.
         * @param hangingRootsVerticalSpan the vertical span of the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootsVerticalSpan(int hangingRootsVerticalSpan) {
            this.hangingRootsVerticalSpan = hangingRootsVerticalSpan;
            return this;
        }

        /**
         * Sets the block to use for the hanging roots.
         * @param hangingRootStateProvider the block to use for the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootStateProvider(BlockStateProvider hangingRootStateProvider) {
            this.hangingRootStateProvider = hangingRootStateProvider;
            return this;
        }

        /**
         * Sets the number of attempts to place the hanging roots.
         * @param hangingRootPlacementAttempts the number of attempts to place the hanging roots
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder hangingRootPlacementAttempts(int hangingRootPlacementAttempts) {
            this.hangingRootPlacementAttempts = hangingRootPlacementAttempts;
            return this;
        }

        /**
         * Sets the amount of vertical water allowed for the placed feature.
         * @param allowedVerticalWaterForTree the amount of vertical water allowed for the placed feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedVerticalWaterForTree(int allowedVerticalWaterForTree) {
            this.allowedVerticalWaterForTree = allowedVerticalWaterForTree;
            return this;
        }

        /**
         * Sets the block predicate used to check if the tree position is valid.
         * @param allowedTreePosition the block predicate used to check if the tree position is valid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder allowedTreePosition(BlockPredicate allowedTreePosition) {
            this.allowedTreePosition = allowedTreePosition;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a material to the root-replaceable list.
         * @param rootReplaceable the material to add
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(Material... rootReplaceable) {
            this.rootReplaceable = this.rootReplaceable.leftOrElse(new HashSet<>())
                .consumeLeft(set -> set.addAll(Set.of(rootReplaceable)));
            return this;
        }

        /**
         * Adds a tag of materials to the root-replaceable list.
         * @param rootReplaceable the tag of materials to add
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(ResourceKey rootReplaceable) {
            this.rootReplaceable = Either.right(TagKey.blocks(rootReplaceable));
            return this;
        }

        /**
         * Adds a tag of materials to the root-replaceable list.
         * @param rootReplaceable the tag of materials to add
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(TagSet<Material> rootReplaceable) {
            this.rootReplaceable = rootReplaceable.value();
            return this;
        }

        /**
         * Adds a tag of materials to the root-replaceable list.
         * @param rootReplaceable the tag of materials to add
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder rootReplaceable(Tag<Material> rootReplaceable) {
            this.rootReplaceable = Either.right(TagKey.blocks(rootReplaceable));
            return this;
        }

        /**
         * Builds the root system configuration.
         * @return the root system configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RootSystemConfiguration build() {
            Preconditions.checkNotNull(treeFeature, "treeFeature must be set");
            Preconditions.checkArgument(requiredVerticalSpaceForTree >= 1 && requiredVerticalSpaceForTree <= 64, "requiredVerticalSpaceForTree must be between 1 and 64");
            Preconditions.checkArgument(levelTestDistance >= 0 && levelTestDistance <= 64, "levelTestDistance must be between 0 and 64");
            Preconditions.checkArgument(maxLevelDeviation >= 1 && maxLevelDeviation <= 64, "maxLevelDeviation must be between 1 and 64");
            Preconditions.checkArgument(rootRadius >= 1 && rootRadius <= 64, "rootRadius must be between 1 and 64");
            Preconditions.checkNotNull(rootStateProvider, "rootStateProvider must be set");
            Preconditions.checkArgument(rootPlacementAttempts >= 1 && rootPlacementAttempts <= 256, "rootPlacementAttempts must be between 1 and 256");
            Preconditions.checkArgument(rootColumnMaxHeight >= 1 && rootColumnMaxHeight <= 4096, "rootColumnMaxHeight must be between 1 and 4096");
            Preconditions.checkArgument(hangingRootRadius >= 1 && hangingRootRadius <= 64, "hangingRootRadius must be between 1 and 64");
            Preconditions.checkArgument(hangingRootsVerticalSpan >= 1 && hangingRootsVerticalSpan <= 16, "hangingRootsVerticalSpan must be between 1 and 16");
            Preconditions.checkNotNull(hangingRootStateProvider, "hangingRootStateProvider must be set");
            Preconditions.checkArgument(hangingRootPlacementAttempts >= 1 && hangingRootPlacementAttempts <= 256, "hangingRootPlacementAttempts must be between 1 and 256");
            Preconditions.checkArgument(allowedVerticalWaterForTree >= 1 && allowedVerticalWaterForTree <= 64, "allowedVerticalWaterForTree must be between 1 and 64");
            Preconditions.checkNotNull(allowedTreePosition, "allowedTreePosition must be set");

            return of(
                treeFeature,
                requiredVerticalSpaceForTree,
                levelTestDistance,
                maxLevelDeviation,
                rootRadius,
                TagSet.blocks(rootReplaceable),
                rootStateProvider,
                rootPlacementAttempts,
                rootColumnMaxHeight,
                hangingRootRadius,
                hangingRootsVerticalSpan,
                hangingRootStateProvider,
                hangingRootPlacementAttempts,
                allowedVerticalWaterForTree,
                allowedTreePosition
            );
        }
    }
}