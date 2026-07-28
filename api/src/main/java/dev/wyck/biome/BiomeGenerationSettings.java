package dev.wyck.biome;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the generation settings of a biome, including its configured carvers and placed features.
 *
 * @see <a href="https://minecraft.wiki/w/World_generation#Biomes">World generation (Biomes)</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface BiomeGenerationSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<BiomeGenerationSettings> WIRE = ConstructWireProvider.create("dev.wyck.biome.BiomeGenerationSettingsImpl");

    @ApiStatus.Internal
    Decoder<BiomeGenerationSettings> DECODER =
        Decoder.create("dev.wyck.decode.biome.BiomeGenerationSettingsDecoder");

    /**
     * The configured carvers applied to this biome's generation.
     * @return an immutable list of carvers
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    List<ConfiguredWorldCarver> carvers();

    /**
     * The placed features applied to this biome's generation, grouped by step.
     * @return an immutable map of the generation steps to placed features
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Map<Decoration, List<PlacedFeature>> features();

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
     * Creates a new biome generation settings.
     * @param carvers the carvers of this biome's generation
     * @param features the features of this biome's generation
     * @return a new biome generation settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeGenerationSettings of(List<ConfiguredWorldCarver> carvers, Map<Decoration, List<PlacedFeature>> features) {
        return WIRE.construct(carvers, features);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * An empty generation settings carrying no carvers or features.
     * @return an empty BiomeGenerationSettings
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiomeGenerationSettings empty() {
        return new Builder().build();
    }

    /**
     * Reads Minecraft biome generation settings into a wrapper.
     * @param minecraftGenerationSettings the generation settings to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BiomeGenerationSettings decode(Object minecraftGenerationSettings) {
        return DECODER.decode(minecraftGenerationSettings);
    }

    /**
     * A builder for creating a BiomeGenerationSettings.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {

        private List<ConfiguredWorldCarver> carvers = new ArrayList<>();
        private Map<Decoration, List<PlacedFeature>> features  = new EnumMap<>(Decoration.class);

        public Builder() {}

        public Builder(BiomeGenerationSettings settings) {
            this.carvers.addAll(settings.carvers());
            // undo deep copy
            for (Map.Entry<Decoration, List<PlacedFeature>> entry : settings.features().entrySet()) {
                this.features.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
        }

        /**
         * Sets the carvers of this biome's generation.
         * @param carvers the carvers of this biome's generation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder carvers(List<ConfiguredWorldCarver> carvers) {
            this.carvers = carvers;
            return this;
        }

        /**
         * Sets the carvers of this biome's generation.
         * @param carvers the carvers of this biome's generation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder carvers(ConfiguredWorldCarver... carvers) {
            this.carvers = new ArrayList<>(List.of(carvers));
            return this;
        }

        /**
         * Sets the features of this biome's generation.
         * @param features the features of this biome's generation
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder features(Map<Decoration, List<PlacedFeature>> features) {
            this.features = features;
            return this;
        }

        /**
         * Adds a configured carver to this biome's generation.
         * @param carver the configured carver to add
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder carver(ConfiguredWorldCarver carver) {
            Preconditions.checkNotNull(carver, "carver");
            this.carvers.add(carver);
            return this;
        }

        /**
         * Adds a custom carver to this biome's generation.
         * @param carver the custom carver to add
         * @param config the configuration of the carver
         * @return this builder
         * @param <C> the type of the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public <C> Builder carver(CustomCarver<C> carver, C config) {
            Preconditions.checkNotNull(carver, "carver");
            Preconditions.checkNotNull(config, "config");
            this.carvers.add(ConfiguredWorldCarver.custom(carver, config));
            return this;
        }

        /**
         * Adds a placed feature to this biome's generation under the given step.
         * @param step the generation step the feature is placed in
         * @param feature the placed feature to add
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder feature(Decoration step, PlacedFeature feature) {
            Preconditions.checkNotNull(step, "step");
            Preconditions.checkNotNull(feature, "feature");
            this.features.computeIfAbsent(step, s -> new ArrayList<>()).add(feature);
            return this;
        }

        /**
         * Builds the BiomeGenerationSettings.
         * @return the BiomeGenerationSettings
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public BiomeGenerationSettings build() {
            return of(carvers, features);
        }
    }
}
