package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A feature that places magma blocks below water, along the floor of an ocean or similar body of water.
 *
 * @see <a href="https://minecraft.wiki/w/Underwater_Magma">Underwater Magma</a>
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface UnderwaterMagmaConfiguration extends FeatureConfiguration {

    /**
     * How far down the feature searches for a floor, between 0 and 512.
     * @return the floor search range
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    int floorSearchRange();

    /**
     * The radius around the found floor in which magma may be placed, between 0 and 64.
     * @return the placement radius around the floor
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    int placementRadiusAroundFloor();

    /**
     * The chance of placing magma at each valid position, between 0.0 and 1.0.
     * @return the placement probability per valid position
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    float placementProbabilityPerValidPosition();

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
     * Creates a new underwater magma configuration.
     * @param floorSearchRange how far down the feature searches for a floor, between 0 and 512
     * @param placementRadiusAroundFloor the radius around the found floor in which magma may be placed, between 0 and 64
     * @param placementProbabilityPerValidPosition the chance of placing magma at each valid position, between 0.0 and 1.0
     * @return a new underwater magma configuration
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static UnderwaterMagmaConfiguration of(int floorSearchRange, int placementRadiusAroundFloor, float placementProbabilityPerValidPosition) {
        Preconditions.checkArgument(floorSearchRange >= 0 && floorSearchRange <= 512, "floorSearchRange must be between 0 and 512");
        Preconditions.checkArgument(placementRadiusAroundFloor >= 0 && placementRadiusAroundFloor <= 64, "placementRadiusAroundFloor must be between 0 and 64");
        Preconditions.checkArgument(placementProbabilityPerValidPosition >= 0.0F && placementProbabilityPerValidPosition <= 1.0F, "placementProbabilityPerValidPosition must be between 0.0 and 1.0");
        record Holder() {
            static final ConstructWireProvider<UnderwaterMagmaConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.UnderwaterMagmaConfigurationImpl");
        }
        return Holder.WIRE.construct(floorSearchRange, placementRadiusAroundFloor, placementProbabilityPerValidPosition);
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
     * Builder for {@link UnderwaterMagmaConfiguration}.
     * @since 3.0.1
     * @version 3.0.1
     * @author Jsinco
     */
    @AsOf("3.0.1")
    final class Builder {
        private int floorSearchRange = 0;
        private int placementRadiusAroundFloor = 0;
        private float placementProbabilityPerValidPosition = 0.0F;

        public Builder() {}

        public Builder(UnderwaterMagmaConfiguration configuration) {
            this.floorSearchRange = configuration.floorSearchRange();
            this.placementRadiusAroundFloor = configuration.placementRadiusAroundFloor();
            this.placementProbabilityPerValidPosition = configuration.placementProbabilityPerValidPosition();
        }

        /**
         * Sets how far down the feature searches for a floor.
         * @param floorSearchRange the floor search range, between 0 and 512
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder floorSearchRange(int floorSearchRange) {
            this.floorSearchRange = floorSearchRange;
            return this;
        }

        /**
         * Sets the radius around the found floor in which magma may be placed.
         * @param placementRadiusAroundFloor the placement radius, between 0 and 64
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder placementRadiusAroundFloor(int placementRadiusAroundFloor) {
            this.placementRadiusAroundFloor = placementRadiusAroundFloor;
            return this;
        }

        /**
         * Sets the chance of placing magma at each valid position.
         * @param placementProbabilityPerValidPosition the placement probability, between 0.0 and 1.0
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder placementProbabilityPerValidPosition(float placementProbabilityPerValidPosition) {
            this.placementProbabilityPerValidPosition = placementProbabilityPerValidPosition;
            return this;
        }

        /**
         * Builds the underwater magma configuration.
         * @return the underwater magma configuration
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public UnderwaterMagmaConfiguration build() {
            return of(floorSearchRange, placementRadiusAroundFloor, placementProbabilityPerValidPosition);
        }
    }
}
