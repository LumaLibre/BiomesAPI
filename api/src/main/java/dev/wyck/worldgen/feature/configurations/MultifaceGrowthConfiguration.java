package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

/**
 * A feature that consists of several sculk vein or glow lichen blocks.
 *
 * @see <a href="https://minecraft.wiki/w/Sculk_Vein_(feature)">Sculk Vein (feature)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MultifaceGrowthConfiguration extends FeatureConfiguration {

    /**
     * The block to place.
     * Must be a sculk vein or glow lichen.
     * @return the block to place
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material placeBlock();

    /**
     * The search range between 1 and 64.
     * @return the search range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int searchRange();

    /**
     * If the block can be placed on the floor.
     * @return whether the block can be placed on the floor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean canPlaceOnFloor();

    /**
     * If the block can be placed on the ceiling.
     * @return whether the block can be placed on the ceiling
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean canPlaceOnCeiling();

    /**
     * If the block can be placed on the wall.
     * @return whether the block can be placed on the wall
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean canPlaceOnWall();

    /**
     * The chance of the block spreading between 0.0 and 1.0.
     * @return the chance of spreading
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float chanceOfSpreading();

    /**
     * A set of materials that the block can be placed on.
     * @return the materials that the block can be placed on
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Set<Material> canBePlacedOn();

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
     * Creates a new multiface growth configuration.
     * @param placeBlock the block to place
     * @param searchRange the search range
     * @param canPlaceOnFloor whether the block can be placed on the floor
     * @param canPlaceOnCeiling whether the block can be placed on the ceiling
     * @param canPlaceOnWall whether the block can be placed on the wall
     * @param chanceOfSpreading the chance of the block spreading
     * @param canBePlacedOn a set of materials that the block can be placed on
     * @return a new multiface growth configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultifaceGrowthConfiguration of(Material placeBlock, int searchRange, boolean canPlaceOnFloor, boolean canPlaceOnCeiling, boolean canPlaceOnWall, float chanceOfSpreading, Set<Material> canBePlacedOn) {
        record Holder() {
            static final ConstructWireProvider<MultifaceGrowthConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.MultifaceGrowthConfigurationImpl");
        }
        return Holder.WIRE.construct(placeBlock, searchRange, canPlaceOnFloor, canPlaceOnCeiling, canPlaceOnWall, chanceOfSpreading, canBePlacedOn);
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
     * Builder for {@link MultifaceGrowthConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private Material placeBlock = Material.GLOW_LICHEN;
        private int searchRange = 10;
        private boolean canPlaceOnFloor = false;
        private boolean canPlaceOnCeiling = false;
        private boolean canPlaceOnWall = false;
        private float chanceOfSpreading = 0.5F;
        private Set<Material> canBePlacedOn = new HashSet<>();

        public Builder() {}

        public Builder(MultifaceGrowthConfiguration configuration) {
            this.placeBlock = configuration.placeBlock();
            this.searchRange = configuration.searchRange();
            this.canPlaceOnFloor = configuration.canPlaceOnFloor();
            this.canPlaceOnCeiling = configuration.canPlaceOnCeiling();
            this.canPlaceOnWall = configuration.canPlaceOnWall();
            this.chanceOfSpreading = configuration.chanceOfSpreading();
            this.canBePlacedOn.addAll(configuration.canBePlacedOn());
        }

        /**
         * Sets the block to place.
         * @param placeBlock the block to place
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder placeBlock(Material placeBlock) {
            this.placeBlock = placeBlock;
            return this;
        }

        /**
         * Sets the search range.
         * @param searchRange the search range
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder searchRange(int searchRange) {
            this.searchRange = searchRange;
            return this;
        }

        /**
         * Sets whether the block can be placed on the floor.
         * @param canPlaceOnFloor whether the block can be placed on the floor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnFloor(boolean canPlaceOnFloor) {
            this.canPlaceOnFloor = canPlaceOnFloor;
            return this;
        }

        /**
         * Sets whether the block can be placed on the ceiling.
         * @param canPlaceOnCeiling whether the block can be placed on the ceiling
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnCeiling(boolean canPlaceOnCeiling) {
            this.canPlaceOnCeiling = canPlaceOnCeiling;
            return this;
        }

        /**
         * Sets whether the block can be placed on the wall.
         * @param canPlaceOnWall whether the block can be placed on the wall
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnWall(boolean canPlaceOnWall) {
            this.canPlaceOnWall = canPlaceOnWall;
            return this;
        }

        /**
         * Sets the chance of the block spreading.
         * @param chanceOfSpreading the chance of the block spreading
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chanceOfSpreading(float chanceOfSpreading) {
            this.chanceOfSpreading = chanceOfSpreading;
            return this;
        }

        /**
         * Sets the materials that the block can be placed on.
         * @param canBePlacedOn the materials that the block can be placed on
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canBePlacedOn(Set<Material> canBePlacedOn) {
            this.canBePlacedOn = canBePlacedOn;
            return this;
        }

        // Friendly builder methods

        /**
         * Allows the block to be placed on the floor.
         * @return  this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnFloor() {
            this.canPlaceOnFloor = true;
            return this;
        }

        /**
         * Allows the block to be placed on the ceiling.
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnCeiling() {
            this.canPlaceOnCeiling = true;
            return this;
        }

        /**
         * Allows the block to be placed on the wall.
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canPlaceOnWall() {
            this.canPlaceOnWall = true;
            return this;
        }

        /**
         * Adds materials to the set of materials that the block can be placed on.
         * @param canBePlacedOn the materials to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder canBePlacedOn(Material... canBePlacedOn) {
            java.util.Collections.addAll(this.canBePlacedOn, canBePlacedOn);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public MultifaceGrowthConfiguration build() {
            Preconditions.checkNotNull(placeBlock, "placeBlock must be set");
            Preconditions.checkArgument(searchRange >= 1 && searchRange <= 64, "searchRange must be between 1 and 64");
            Preconditions.checkArgument(chanceOfSpreading >= 0.0F && chanceOfSpreading <= 1.0F, "chanceOfSpreading must be between 0.0 and 1.0");
            return of(placeBlock, searchRange, canPlaceOnFloor, canPlaceOnCeiling, canPlaceOnWall, chanceOfSpreading, canBePlacedOn);
        }
    }
}