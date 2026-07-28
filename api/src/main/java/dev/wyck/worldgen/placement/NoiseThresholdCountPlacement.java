package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Returns multiple copies of the current block position. The count is either the below or above
 * value, chosen by comparing <code>noise(x / 200, z / 200)</code> against the noise level: values
 * below the level use the below count, otherwise the above count is used.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseThresholdCountPlacement extends PlacementModifier {

    /**
     * The threshold within the noise for when to use the below or above count.
     * @return the noise level
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double noiseLevel();

    /**
     * The count when the noise is below the threshold. A value lower than 0 is treated as 0.
     * @return the below noise count
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int belowNoise();

    /**
     * The count when the noise is above the threshold. A value lower than 0 is treated as 0.
     * @return the above noise count
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int aboveNoise();

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
     * Creates a new noise threshold count placement.
     * @param noiseLevel the threshold within the noise for when to use the below or above count
     * @param belowNoise the count when the noise is below the threshold
     * @param aboveNoise the count when the noise is above the threshold
     * @return a new noise threshold count placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseThresholdCountPlacement of(double noiseLevel, int belowNoise, int aboveNoise) {
        record Holder() {
            static final ConstructWireProvider<NoiseThresholdCountPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.NoiseThresholdCountPlacementImpl");
        }
        return Holder.WIRE.construct(noiseLevel, belowNoise, aboveNoise);
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
     * Builder for {@link NoiseThresholdCountPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private double noiseLevel;
        private int belowNoise;
        private int aboveNoise;

        public Builder() {}

        public Builder(NoiseThresholdCountPlacement configuration) {
            this.noiseLevel = configuration.noiseLevel();
            this.belowNoise = configuration.belowNoise();
            this.aboveNoise = configuration.aboveNoise();
        }

        /**
         * Sets the noise level.
         * @param noiseLevel the threshold within the noise for when to use the below or above count
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseLevel(double noiseLevel) {
            this.noiseLevel = noiseLevel;
            return this;
        }

        /**
         * Sets the below noise count.
         * @param belowNoise the count when the noise is below the threshold
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder belowNoise(int belowNoise) {
            this.belowNoise = belowNoise;
            return this;
        }

        /**
         * Sets the above noise count.
         * @param aboveNoise the count when the noise is above the threshold
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder aboveNoise(int aboveNoise) {
            this.aboveNoise = aboveNoise;
            return this;
        }

        /**
         * Builds the noise threshold count placement.
         * @return the noise threshold count placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NoiseThresholdCountPlacement build() {
            return of(noiseLevel, belowNoise, aboveNoise);
        }
    }
}