package dev.wyck.worldgen.feature.configurations.geode;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * The thickness of each layer of a geode. Units are non-linear; larger values generate larger geodes,
 * and values smaller than 0.01 may produce unexpectedly large geodes.
 *
 * @see <a href="https://minecraft.wiki/w/Amethyst_Geode">Amethyst Geode</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface GeodeLayerSettings extends Wrapper {

    /**
     * The thickness of the filling layer. Value between 0.01 and 50.0.
     * @return the filling layer thickness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double filling();

    /**
     * The thickness of the inner layer. Value between 0.01 and 50.0.
     * @return the inner layer thickness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double innerLayer();

    /**
     * The thickness of the middle layer. Value between 0.01 and 50.0.
     * @return the middle layer thickness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double middleLayer();

    /**
     * The thickness of the outer layer. Value between 0.01 and 50.0.
     * @return the outer layer thickness
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double outerLayer();

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
     * Creates a new geode layer settings.
     * @param filling the thickness of the filling layer
     * @param innerLayer the thickness of the inner layer
     * @param middleLayer the thickness of the middle layer
     * @param outerLayer the thickness of the outer layer
     * @return a new geode layer settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeLayerSettings of(double filling, double innerLayer, double middleLayer, double outerLayer) {
        record Holder() {
            static final ConstructWireProvider<GeodeLayerSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.geode.GeodeLayerSettingsImpl");
        }
        return Holder.WIRE.construct(filling, innerLayer, middleLayer, outerLayer);
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

    @ApiStatus.Internal
    static GeodeLayerSettings decode(Object minecraftSettings) {
        record Holder() {
            static final Decoder<GeodeLayerSettings> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.geode.GeodeLayerSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftSettings);
    }

    /**
     * Builder for {@link GeodeLayerSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private double filling = 1.7;
        private double innerLayer = 2.2;
        private double middleLayer = 3.2;
        private double outerLayer = 4.2;

        public Builder() {}

        public Builder(GeodeLayerSettings settings) {
            this.filling = settings.filling();
            this.innerLayer = settings.innerLayer();
            this.middleLayer = settings.middleLayer();
            this.outerLayer = settings.outerLayer();
        }

        /**
         * Sets the thickness of the filling layer.
         * @param filling the filling layer thickness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder filling(double filling) {
            this.filling = filling;
            return this;
        }

        /**
         * Sets the thickness of the inner layer.
         * @param innerLayer the inner layer thickness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder innerLayer(double innerLayer) {
            this.innerLayer = innerLayer;
            return this;
        }

        /**
         * Sets the thickness of the middle layer.
         * @param middleLayer the middle layer thickness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder middleLayer(double middleLayer) {
            this.middleLayer = middleLayer;
            return this;
        }

        /**
         * Sets the thickness of the outer layer.
         * @param outerLayer the outer layer thickness
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder outerLayer(double outerLayer) {
            this.outerLayer = outerLayer;
            return this;
        }

        /**
         * Builds the settings.
         * @return the settings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public GeodeLayerSettings build() {
            Preconditions.checkArgument(filling >= 0.01 && filling <= 50.0, "filling must be between 0.01 and 50.0");
            Preconditions.checkArgument(innerLayer >= 0.01 && innerLayer <= 50.0, "innerLayer must be between 0.01 and 50.0");
            Preconditions.checkArgument(middleLayer >= 0.01 && middleLayer <= 50.0, "middleLayer must be between 0.01 and 50.0");
            Preconditions.checkArgument(outerLayer >= 0.01 && outerLayer <= 50.0, "outerLayer must be between 0.01 and 50.0");
            return of(filling, innerLayer, middleLayer, outerLayer);
        }
    }
}