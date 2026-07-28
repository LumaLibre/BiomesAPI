package dev.wyck.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

/**
 * Configuration for the CanyonCarver.
 *
 * @see <a href="https://minecraft.wiki/w/Carver_definition#canyon">Carver definition (canyon)</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface CanyonCarverConfiguration extends CarverConfiguration {

    @ApiStatus.Internal
    ConstructWireProvider<CanyonCarverConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.CanyonCarverConfigurationImpl");

    /**
     * The vertical rotation of the canyon.
     * @return the vertical rotation of the canyon
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider verticalRotation();

    /**
     * The shape of the canyon.
     * @return the shape of the canyon
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    CanyonShapeConfiguration shape();

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
     * Creates a new CanyonCarverConfiguration.
     * @param probability the probability of the carver spawning
     * @param y the height provider
     * @param yScale the scale of the height provider
     * @param lavaLevel the y-level of the lava level of the carver
     * @param debugSettings the debug settings of the carver
     * @param replaceable the materials that can be replaced by this carver
     * @param verticalRotation the vertical rotation of the canyon
     * @param shape the shape of the canyon
     * @return a new CanyonCarverConfiguration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CanyonCarverConfiguration of(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, Set<Material> replaceable, FloatProvider verticalRotation, CanyonShapeConfiguration shape) {
        return WIRE.construct(probability, y, yScale, lavaLevel, debugSettings, replaceable, verticalRotation, shape);
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
     * Builder for {@link CanyonCarverConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends CarverConfigurationBuilder<CanyonCarverConfiguration, Builder> {
        private @Nullable FloatProvider verticalRotation;
        private @Nullable CanyonShapeConfiguration shape;

        public Builder() {}

        public Builder(CanyonCarverConfiguration configuration) {
            super(configuration);
            this.verticalRotation = configuration.verticalRotation();
            this.shape = configuration.shape();
        }

        /**
         * Sets the vertical rotation of the canyon.
         * @param verticalRotation the vertical rotation of the canyon
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder verticalRotation(FloatProvider verticalRotation) {
            this.verticalRotation = verticalRotation;
            return this;
        }

        /**
         * Sets the shape of the canyon.
         * @param shape the shape of the canyon
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder shape(CanyonShapeConfiguration shape) {
            this.shape = shape;
            return this;
        }

        @Override
        protected CanyonCarverConfiguration create() {
            Preconditions.checkNotNull(verticalRotation, "verticalRotation must be set");
            Preconditions.checkNotNull(shape, "shape must be set");
            return CanyonCarverConfiguration.of(probability, y, yScale, lavaLevel, debugSettings, replaceable, verticalRotation, shape);
        }
    }

    /**
     * Configuration for the shape of the canyon.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    interface CanyonShapeConfiguration extends Wrapper {

        @ApiStatus.Internal
        ConstructWireProvider<CanyonShapeConfiguration> INNER_WIRE = WIRE.resolve("CanyonShapeConfigurationImpl");

        @ApiStatus.Internal
        Decoder<CanyonShapeConfiguration> DECODER =
            Decoder.create("dev.wyck.decode.worldgen.carver.CanyonShapeConfigurationDecoder");

        /**
         * Gets the distance factor for the canyon shape.
         * @return the distance factor for the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        FloatProvider distanceFor();

        /**
         * Gets the thickness of the canyon shape.
         * @return the thickness of the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        FloatProvider thickness();

        /**
         * Gets the width smoothness of the canyon shape.
         * @return the width smoothness of the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        int widthSmoothness();

        /**
         * Gets the horizontal radius factor of the canyon shape.
         * @return the horizontal radius factor of the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        FloatProvider horizontalRadiusFactor();

        /**
         * Gets the vertical radius default factor of the canyon shape.
         * @return the vertical radius default factor of the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        float verticalRadiusDefaultFactor();

        /**
         * Gets the vertical radius center factor of the canyon shape.
         * @return the vertical radius center factor of the canyon shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        float verticalRadiusCenterFactor();

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
         * Creates a new CanyonShapeConfiguration.
         * @param distanceFactor the distance factor for the canyon shape
         * @param thickness the thickness of the canyon shape
         * @param widthSmoothness the width smoothness of the canyon shape
         * @param horizontalRadiusFactor the horizontal radius factor of the canyon shape
         * @param verticalRadiusDefaultFactor the vertical radius default factor of the canyon shape
         * @param verticalRadiusCenterFactor the vertical radius center factor of the canyon shape
         * @return a new CanyonShapeConfiguration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        static CanyonShapeConfiguration of(FloatProvider distanceFactor, FloatProvider thickness, int widthSmoothness, FloatProvider horizontalRadiusFactor, float verticalRadiusDefaultFactor, float verticalRadiusCenterFactor) {
            return INNER_WIRE.construct(distanceFactor, thickness, widthSmoothness, horizontalRadiusFactor, verticalRadiusDefaultFactor, verticalRadiusCenterFactor);
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
         * Reads a Minecraft canyon shape configuration.
         * @param minecraftShape the shape to read
         * @return the decoded shape
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        static CanyonShapeConfiguration decode(Object minecraftShape) {
            return DECODER.decode(minecraftShape);
        }

        /**
         * Builder for {@link CanyonShapeConfiguration}.
         * @since 3.0.0
         * @version 3.0.0
         * @author Jsinco
         */
        @AsOf("3.0.0")
        final class Builder {
            private @Nullable FloatProvider distanceFactor;
            private @Nullable FloatProvider thickness;
            private int widthSmoothness;
            private @Nullable FloatProvider horizontalRadiusFactor;
            private float verticalRadiusDefaultFactor;
            private float verticalRadiusCenterFactor;

            public Builder() {}

            public Builder(CanyonShapeConfiguration configuration) {
                this.distanceFactor = configuration.distanceFor();
                this.thickness = configuration.thickness();
                this.widthSmoothness = configuration.widthSmoothness();
                this.horizontalRadiusFactor = configuration.horizontalRadiusFactor();
                this.verticalRadiusDefaultFactor = configuration.verticalRadiusDefaultFactor();
                this.verticalRadiusCenterFactor = configuration.verticalRadiusCenterFactor();
            }

            /**
             * Sets the distance factor for the canyon shape.
             * @param distanceFactor the distance factor for the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder distanceFactor(FloatProvider distanceFactor) {
                this.distanceFactor = distanceFactor;
                return this;
            }

            /**
             * Sets the thickness of the canyon shape.
             * @param thickness the thickness of the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder thickness(FloatProvider thickness) {
                this.thickness = thickness;
                return this;
            }

            /**
             * Sets the width smoothness of the canyon shape.
             * @param widthSmoothness the width smoothness of the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder widthSmoothness(int widthSmoothness) {
                this.widthSmoothness = widthSmoothness;
                return this;
            }

            /**
             * Sets the horizontal radius factor of the canyon shape.
             * @param horizontalRadiusFactor the horizontal radius factor of the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder horizontalRadiusFactor(FloatProvider horizontalRadiusFactor) {
                this.horizontalRadiusFactor = horizontalRadiusFactor;
                return this;
            }

            /**
             * Sets the vertical radius default factor of the canyon shape.
             * @param verticalRadiusDefaultFactor the vertical radius default factor of the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder verticalRadiusDefaultFactor(float verticalRadiusDefaultFactor) {
                this.verticalRadiusDefaultFactor = verticalRadiusDefaultFactor;
                return this;
            }

            /**
             * Sets the vertical radius center factor of the canyon shape.
             * @param verticalRadiusCenterFactor the vertical radius center factor of the canyon shape
             * @return this builder
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public Builder verticalRadiusCenterFactor(float verticalRadiusCenterFactor) {
                this.verticalRadiusCenterFactor = verticalRadiusCenterFactor;
                return this;
            }

            /**
             * Builds the canyon shape configuration.
             * @return the canyon shape configuration
             * @since 3.0.0
             */
            @AsOf("3.0.0")
            public CanyonShapeConfiguration build() {
                Preconditions.checkArgument(widthSmoothness >= 0, "widthSmoothness must be positive");
                //noinspection ConstantConditions
                return CanyonShapeConfiguration.of(distanceFactor, thickness, widthSmoothness, horizontalRadiusFactor, verticalRadiusDefaultFactor, verticalRadiusCenterFactor);
            }
        }
    }
}
