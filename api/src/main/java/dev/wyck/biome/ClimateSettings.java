package dev.wyck.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Represents the climate settings of a biome.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ClimateSettings extends Wrapper {

    ClimateSettings DEFAULT = of(true, 0.5F, TemperatureModifier.NONE, 0.5F);

    /**
     * Whether the biome has precipitation.
     * @return whether the biome has precipitation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean hasPrecipitation();

    /**
     * The temperature of the biome.
     * Anything less than 0.14 will cause snow to appear instead of rain.
     * @return the temperature of the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float temperature();

    /**
     * The temperature modifier of the biome.
     * @return the temperature modifier of the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    TemperatureModifier temperatureModifier();

    /**
     * The downfall of the biome.
     * @return the downfall of the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float downfall();

    /**
     * Converts this object back to a builder.
     * @return a new ClimateSettings builder from this instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new ClimateSettings instance.
     * @param hasPrecipitation whether the biome has precipitation
     * @param temperature the temperature of the biome
     * @param temperatureModifier the temperature modifier of the biome
     * @param downfall the downfall of the biome
     * @return a new ClimateSettings instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClimateSettings of(boolean hasPrecipitation, float temperature, TemperatureModifier temperatureModifier, float downfall) {
        record Holder() {
            static final ConstructWireProvider<ClimateSettings> WIRE = ConstructWireProvider.create("dev.wyck.biome.ClimateSettingsImpl");
        }
        return Holder.WIRE.construct(hasPrecipitation, temperature, temperatureModifier, downfall);
    }

    /**
     * Creates a new ClimateSettings builder.
     * @return a new ClimateSettings builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft climate settings into a wrapper.
     * @param minecraftClimateSettings the climate settings to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ClimateSettings decode(Object minecraftClimateSettings) {
        record Holder() {
            static final Decoder<ClimateSettings> DECODER = Decoder.create("dev.wyck.decode.biome.ClimateSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftClimateSettings);
    }

    /**
     * Creates a new ClimateSettings builder.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private boolean hasPrecipitation = true;
        private float temperature = 0.5F;
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        private float downfall = 0.5F;

        public Builder() {}

        public Builder(ClimateSettings other) {
            this.hasPrecipitation = other.hasPrecipitation();
            this.temperature = other.temperature();
            this.temperatureModifier = other.temperatureModifier();
            this.downfall = other.downfall();
        }

        /**
         * Sets the hasPrecipitation property of the builder.
         * @param hasPrecipitation The hasPrecipitation property of the builder.
         * @return This builder instance.
         */
        @AsOf("3.0.0")
        public Builder hasPrecipitation(boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
            return this;
        }

        /**
         * Sets the temperature property of the builder.
         * @param temperature The temperature property of the builder.
         * @return This builder instance.
         */
        @AsOf("3.0.0")
        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the temperatureModifier property of the builder.
         * @param temperatureModifier The temperatureModifier property of the builder.
         * @return This builder instance.
         */
        @AsOf("3.0.0")
        public Builder temperatureModifier(TemperatureModifier temperatureModifier) {
            this.temperatureModifier = temperatureModifier;
            return this;
        }

        /**
         * Sets the downfall property of the builder.
         * @param downfall The downfall property of the builder.
         * @return This builder instance.
         */
        @AsOf("3.0.0")
        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        /**
         * Builds a new ClimateSettings instance.
         * @return A new ClimateSettings instance.
         */
        @AsOf("3.0.0")
        public ClimateSettings build() {
            return ClimateSettings.of(hasPrecipitation, temperature, temperatureModifier, downfall);
        }
    }
}
