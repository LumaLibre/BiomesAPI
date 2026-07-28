package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagKey;
import dev.wyck.tags.TagSet;
import dev.wyck.util.Either;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.surface.condition.CaveSurface;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A feature that places a patch of ground blocks, then a vegetation feature on top, searching either up
 * from a floor or down from a ceiling. Used by vanilla for lush cave floors and dripleaf patches.
 *
 * @see <a href="https://minecraft.wiki/w/Vegetation_Patch">Vegetation Patch</a>
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface VegetationPatchConfiguration extends FeatureConfiguration {

    /**
     * The blocks that the patch is allowed to replace.
     * @return the replaceable materials
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    TagSet<Material> replaceable();

    /**
     * The block used to build the patch (the "ground").
     * @return the ground state provider
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    BlockStateProvider groundState();

    /**
     * The placed feature to place on top of each found position.
     * @return the vegetation feature
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    PlacedFeature vegetationFeature();

    /**
     * Which surface to search from and place against, either {@link CaveSurface#FLOOR} or {@link CaveSurface#CEILING}.
     * @return the surface
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    CaveSurface surface();

    /**
     * The number of blocks the patch replaces per column, between 1 and 128.
     * @return the depth
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    IntProvider depth();

    /**
     * The chance to add one extra block to the {@link #depth()} of a column, between 0.0 and 1.0.
     * @return the extra bottom block chance
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    float extraBottomBlockChance();

    /**
     * The vertical distance a column searches for an available position, between 1 and 256.
     * @return the vertical range
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    int verticalRange();

    /**
     * The chance of placing the {@link #vegetationFeature()} on a found position, between 0.0 and 1.0.
     * @return the vegetation chance
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    float vegetationChance();

    /**
     * The XZ radius searched for available positions. The x and z radii are sampled independently from this provider.
     * @return the xz radius
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    IntProvider xzRadius();

    /**
     * The chance to add a search position adjacent to the initial rectangle, between 0.0 and 1.0.
     * @return the extra edge column chance
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    float extraEdgeColumnChance();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new vegetation patch configuration.
     * @param replaceable the blocks that the patch is allowed to replace
     * @param groundState the block used to build the patch
     * @param vegetationFeature the placed feature to place on top of each found position
     * @param surface which surface to search from and place against
     * @param depth the number of blocks the patch replaces per column
     * @param extraBottomBlockChance the chance to add one extra block to the depth of a column
     * @param verticalRange the vertical distance a column searches for an available position
     * @param vegetationChance the chance of placing the vegetation feature on a found position
     * @param xzRadius the XZ radius searched for available positions
     * @param extraEdgeColumnChance the chance to add a search position adjacent to the initial rectangle
     * @return a new vegetation patch configuration
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static VegetationPatchConfiguration of(
        TagSet<Material> replaceable,
        BlockStateProvider groundState,
        PlacedFeature vegetationFeature,
        CaveSurface surface,
        IntProvider depth,
        float extraBottomBlockChance,
        int verticalRange,
        float vegetationChance,
        IntProvider xzRadius,
        float extraEdgeColumnChance
    ) {
        record Holder() {
            static final ConstructWireProvider<VegetationPatchConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.VegetationPatchConfigurationImpl");
        }
        return Holder.WIRE.construct(replaceable, groundState, vegetationFeature, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, xzRadius, extraEdgeColumnChance);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link VegetationPatchConfiguration}.
     * @since 3.0.1
     * @version 3.0.1
     * @author Jsinco
     */
    @AsOf("3.0.1")
    final class Builder {
        private Either<Set<Material>, TagKey> replaceable = Either.left(new HashSet<>());
        private @Nullable BlockStateProvider groundState;
        private @Nullable PlacedFeature vegetationFeature;
        private CaveSurface surface = CaveSurface.FLOOR;
        private @Nullable IntProvider depth;
        private float extraBottomBlockChance = 0.0F;
        private int verticalRange = 1;
        private float vegetationChance = 0.0F;
        private @Nullable IntProvider xzRadius;
        private float extraEdgeColumnChance = 0.0F;

        public Builder() {}

        public Builder(VegetationPatchConfiguration configuration) {
            this.replaceable = configuration.replaceable().value();
            this.groundState = configuration.groundState();
            this.vegetationFeature = configuration.vegetationFeature();
            this.surface = configuration.surface();
            this.depth = configuration.depth();
            this.extraBottomBlockChance = configuration.extraBottomBlockChance();
            this.verticalRange = configuration.verticalRange();
            this.vegetationChance = configuration.vegetationChance();
            this.xzRadius = configuration.xzRadius();
            this.extraEdgeColumnChance = configuration.extraEdgeColumnChance();
        }

        /**
         * Sets the blocks that the patch is allowed to replace.
         * @param replaceable the replaceable materials
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(Set<Material> replaceable) {
            this.replaceable = Either.left(replaceable);
            return this;
        }

        /**
         * Sets the blocks that the patch is allowed to replace.
         * @param replaceable the replaceable materials
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(TagKey replaceable) {
            this.replaceable = Either.right(replaceable);
            return this;
        }

        /**
         * Sets the block used to build the patch.
         * @param groundState the ground state provider
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder groundState(BlockStateProvider groundState) {
            this.groundState = groundState;
            return this;
        }

        /**
         * Sets the placed feature to place on top of each found position.
         * @param vegetationFeature the vegetation feature
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder vegetationFeature(PlacedFeature vegetationFeature) {
            this.vegetationFeature = vegetationFeature;
            return this;
        }

        /**
         * Sets which surface to search from and place against.
         * @param surface the surface
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder surface(CaveSurface surface) {
            this.surface = surface;
            return this;
        }

        /**
         * Sets the number of blocks the patch replaces per column.
         * @param depth the depth, between 1 and 128
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder depth(IntProvider depth) {
            this.depth = depth;
            return this;
        }

        /**
         * Sets the chance to add one extra block to the depth of a column.
         * @param extraBottomBlockChance the extra bottom block chance, between 0.0 and 1.0
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder extraBottomBlockChance(float extraBottomBlockChance) {
            this.extraBottomBlockChance = extraBottomBlockChance;
            return this;
        }

        /**
         * Sets the vertical distance a column searches for an available position.
         * @param verticalRange the vertical range, between 1 and 256
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder verticalRange(int verticalRange) {
            this.verticalRange = verticalRange;
            return this;
        }

        /**
         * Sets the chance of placing the vegetation feature on a found position.
         * @param vegetationChance the vegetation chance, between 0.0 and 1.0
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder vegetationChance(float vegetationChance) {
            this.vegetationChance = vegetationChance;
            return this;
        }

        /**
         * Sets the XZ radius searched for available positions.
         * @param xzRadius the xz radius
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder xzRadius(IntProvider xzRadius) {
            this.xzRadius = xzRadius;
            return this;
        }

        /**
         * Sets the chance to add a search position adjacent to the initial rectangle.
         * @param extraEdgeColumnChance the extra edge column chance, between 0.0 and 1.0
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder extraEdgeColumnChance(float extraEdgeColumnChance) {
            this.extraEdgeColumnChance = extraEdgeColumnChance;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds materials to the replaceable set.
         * @param replaceable the materials to add
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(Material... replaceable) {
            this.replaceable = this.replaceable.leftOrElse(new HashSet<>())
                .consumeLeft(set -> set.addAll(Set.of(replaceable)));
            return this;
        }

        /**
         * Sets a block tag of blocks that the patch is allowed to replace.
         * @param replaceable the replaceable tag
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(ResourceKey replaceable) {
            this.replaceable = Either.right(TagKey.blocks(replaceable));
            return this;
        }

        /**
         * Sets a block tag of blocks that the patch is allowed to replace.
         * @param replaceable the replaceable tag
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(TagSet<Material> replaceable) {
            this.replaceable = replaceable.value();
            return this;
        }

        /**
         * Sets a block tag of blocks that the patch is allowed to replace.
         * @param replaceable the replaceable tag
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder replaceable(Tag<Material> replaceable) {
            this.replaceable = Either.right(TagKey.blocks(replaceable));
            return this;
        }

        /**
         * Builds the vegetation patch configuration.
         * @return the vegetation patch configuration
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public VegetationPatchConfiguration build() {
            Preconditions.checkNotNull(groundState, "groundState must be set");
            Preconditions.checkNotNull(vegetationFeature, "vegetationFeature must be set");
            Preconditions.checkNotNull(depth, "depth must be set");
            Preconditions.checkNotNull(xzRadius, "xzRadius must be set");
            Preconditions.checkArgument(extraBottomBlockChance >= 0.0F && extraBottomBlockChance <= 1.0F, "extraBottomBlockChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(verticalRange >= 1 && verticalRange <= 256, "verticalRange must be between 1 and 256");
            Preconditions.checkArgument(vegetationChance >= 0.0F && vegetationChance <= 1.0F, "vegetationChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(extraEdgeColumnChance >= 0.0F && extraEdgeColumnChance <= 1.0F, "extraEdgeColumnChance must be between 0.0 and 1.0");
            return of(
                TagSet.blocks(replaceable),
                groundState,
                vegetationFeature,
                surface,
                depth,
                extraBottomBlockChance,
                verticalRange,
                vegetationChance,
                xzRadius,
                extraEdgeColumnChance
            );
        }
    }
}
