package dev.wyck.worldgen.placement;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * When the noise value at the current position is positive, returns multiple copies of the current
 * block position, whose count is based on the noise value. When the noise value is negative or zero,
 * returns empty. The count is calculated as
 * <code>ceil((noise(x / noiseFactor, z / noiseFactor) + noiseOffset) * noiseToCountRatio)</code>.
 *
 * @see <a href="https://minecraft.wiki/w/Placed_feature#Placement_modifiers">Placement modifiers</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseBasedCountPlacement extends PlacementModifier {

    /**
     * Ratio of noise value to count.
     * @return the noise to count ratio
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int noiseToCountRatio();

    /**
     * Scales the noise input horizontally. Higher values make for wider and more spaced out peaks.
     * @return the noise factor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double noiseFactor();

    /**
     * Vertical offset of the noise.
     * @return the noise offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double noiseOffset();

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
     * Creates a new noise based count placement.
     * @param noiseToCountRatio ratio of noise value to count
     * @param noiseFactor scales the noise input horizontally
     * @param noiseOffset vertical offset of the noise
     * @return a new noise based count placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseBasedCountPlacement of(int noiseToCountRatio, double noiseFactor, double noiseOffset) {
        record Holder() {
            static final ConstructWireProvider<NoiseBasedCountPlacement> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.placement.NoiseBasedCountPlacementImpl");
        }
        return Holder.WIRE.construct(noiseToCountRatio, noiseFactor, noiseOffset);
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
     * Builder for {@link NoiseBasedCountPlacement}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int noiseToCountRatio;
        private double noiseFactor;
        private double noiseOffset = 0.0;

        public Builder() {}

        public Builder(NoiseBasedCountPlacement configuration) {
            this.noiseToCountRatio = configuration.noiseToCountRatio();
            this.noiseFactor = configuration.noiseFactor();
            this.noiseOffset = configuration.noiseOffset();
        }

        /**
         * Sets the noise to count ratio.
         * @param noiseToCountRatio ratio of noise value to count
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseToCountRatio(int noiseToCountRatio) {
            this.noiseToCountRatio = noiseToCountRatio;
            return this;
        }

        /**
         * Sets the noise factor.
         * @param noiseFactor scales the noise input horizontally
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseFactor(double noiseFactor) {
            this.noiseFactor = noiseFactor;
            return this;
        }

        /**
         * Sets the noise offset.
         * @param noiseOffset vertical offset of the noise
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder noiseOffset(double noiseOffset) {
            this.noiseOffset = noiseOffset;
            return this;
        }

        /**
         * Builds the noise based count placement.
         * @return the noise based count placement
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NoiseBasedCountPlacement build() {
            return of(noiseToCountRatio, noiseFactor, noiseOffset);
        }
    }
}