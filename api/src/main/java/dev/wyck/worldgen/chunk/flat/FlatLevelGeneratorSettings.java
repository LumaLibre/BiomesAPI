package dev.wyck.worldgen.chunk.flat;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.biome.Biomes;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.StructureSets;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Flat source generator settings.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FlatLevelGeneratorSettings extends Wrapper {

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings CLASSIC_FLAT = builder()
        .layer(Material.GRASS_BLOCK, 1)
        .layer(Material.DIRT, 2)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.PLAINS)
        .structure(StructureSets.VILLAGES)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings TUNNELERS_DREAM = builder()
        .layer(Material.GRASS_BLOCK, 1)
        .layer(Material.DIRT, 5)
        .layer(Material.STONE, 230)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.WINDSWEPT_HILLS)
        .structure(StructureSets.MINESHAFTS, StructureSets.STRONGHOLDS)
        .decoration(true)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings WATER_WORLD = builder()
        .layer(Material.WATER, 90)
        .layer(Material.GRAVEL, 5)
        .layer(Material.DIRT, 5)
        .layer(Material.STONE, 5)
        .layer(Material.DEEPSLATE, 64)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.DEEP_OCEAN)
        .structure(StructureSets.OCEAN_RUINS, StructureSets.SHIPWRECKS, StructureSets.OCEAN_MONUMENTS)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings OVERWORLD = builder()
        .layer(Material.GRASS_BLOCK, 1)
        .layer(Material.DIRT, 3)
        .layer(Material.STONE, 59)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.PLAINS)
        .structure(StructureSets.VILLAGES, StructureSets.MINESHAFTS, StructureSets.PILLAGER_OUTPOSTS, StructureSets.RUINED_PORTALS, StructureSets.STRONGHOLDS)
        .addLakes(true)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings SNOWY_KINGDOM = builder()
        .layer(Material.SNOW, 1)
        .layer(Material.GRASS_BLOCK, 1)
        .layer(Material.DIRT, 3)
        .layer(Material.STONE, 59)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.SNOWY_PLAINS)
        .structure(StructureSets.VILLAGES, StructureSets.IGLOOS)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings BOTTOMLESS_PIT = builder()
        .layer(Material.GRASS_BLOCK, 1)
        .layer(Material.DIRT, 3)
        .layer(Material.COBBLESTONE, 2)
        .biome(Biomes.PLAINS)
        .structure(StructureSets.VILLAGES)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings DESERT = builder()
        .layer(Material.SAND, 8)
        .layer(Material.SANDSTONE, 52)
        .layer(Material.STONE, 3)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.DESERT)
        .structure(StructureSets.VILLAGES, StructureSets.DESERT_PYRAMIDS, StructureSets.MINESHAFTS, StructureSets.STRONGHOLDS)
        .decoration(true)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings REDSTONE_READY = builder()
        .layer(Material.SANDSTONE, 116)
        .layer(Material.STONE, 3)
        .layer(Material.BEDROCK, 1)
        .biome(Biomes.DESERT)
        .build();

    @AsOf("3.0.0")
    FlatLevelGeneratorSettings THE_VOID = builder()
        .layer(Material.AIR, 1)
        .biome(Biomes.THE_VOID)
        .decoration(true)
        .build();

    /**
     * Returns the list of layers.
     * @return the list of layers
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<FlatLayerInfo> layers();

    /**
     * If decorations should be generated.
     * @return whether decorations should be generated
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean decoration();

    /**
     * If lakes should be generated.
     * @return whether lakes should be generated
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean addLakes();

    /**
     * Returns the biome.
     * @apiNote Overridden by {@link ChunkGenerator#biomeSource()}
     * @return the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Obsolete
    Biome biome();

    /**
     * Returns the fallback biome.
     * @apiNote Overridden by {@link ChunkGenerator#biomeSource()}
     * @return the fallback biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Obsolete
    Biome fallbackBiome();

    /**
     * Lakes to generate.
     * @return the lakes to generate
     * @since 3.0.0
     */
    List<PlacedFeature> lakes();

    /**
     * Keys of structures to generate.
     * @apiNote This field may change at any time when the structure API is introduced.
     * @return the keys of structures to generate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @ApiStatus.Experimental
    Optional<Set<ResourceKey>> structures();

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
     * Creates a new FlatLevelGeneratorSettings.
     * @param layers the layers
     * @param decoration whether features should be generated
     * @param addLakes whether lakes should be generated
     * @param biome the biome
     * @param fallbackBiome the fallback biome
     * @param lakes the lakes to generate
     * @param structures the keys of structures to generate
     * @return a new FlatLevelGeneratorSettings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLevelGeneratorSettings of(List<FlatLayerInfo> layers, boolean decoration, boolean addLakes, Biome biome, Biome fallbackBiome, List<PlacedFeature> lakes, @Nullable Set<ResourceKey> structures) {
        record Holder() {
            static final ConstructWireProvider<FlatLevelGeneratorSettings> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettingsImpl");
        }
        return Holder.WIRE.construct(layers, decoration, addLakes, biome, fallbackBiome, lakes, Optional.ofNullable(structures));
    }

    /**
     * Reads Minecraft flat-generator settings.
     * @param minecraftSettings the flat-generator settings to read
     * @return the decoded flat-generator settings
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FlatLevelGeneratorSettings decode(Object minecraftSettings) {
        record Holder() {
            static final Decoder<FlatLevelGeneratorSettings> DECODER = Decoder.create("dev.wyck.decode.worldgen.chunk.FlatLevelGeneratorSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftSettings);
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
     * Builder for {@link FlatLevelGeneratorSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private List<FlatLayerInfo> layers = new ArrayList<>();
        private boolean decoration = false;
        private boolean addLakes = false;
        private Biome biome = Biomes.PLAINS;
        private Biome fallbackBiome = Biomes.PLAINS;
        private List<PlacedFeature> lakes = new ArrayList<>();
        private @Nullable Set<ResourceKey> structures = null;

        public Builder() {}

        public Builder(FlatLevelGeneratorSettings settings) {
            this.layers = new ArrayList<>(settings.layers());
            this.decoration = settings.decoration();
            this.biome = settings.biome();
            this.fallbackBiome = settings.fallbackBiome();
            this.lakes = new ArrayList<>(settings.lakes());
            this.structures = settings.structures().map(HashSet::new).orElse(null);
        }

        /**
         * Sets the layers.
         * @param layers the layers
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layers(List<FlatLayerInfo> layers) {
            this.layers = layers;
            return this;
        }

        /**
         * Sets whether decorations should be generated.
         * @param decoration whether decorations should be generated
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder decoration(boolean decoration) {
            this.decoration = decoration;
            return this;
        }

        /**
         * Sets whether lakes should be generated.
         * @param addLakes whether lakes should be generated
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder addLakes(boolean addLakes) {
            this.addLakes = addLakes;
            return this;
        }

        /**
         * Sets the biome.
         * @param biome the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biome(Biome biome) {
            this.biome = biome;
            return this;
        }

        /**
         * Sets the fallback biome.
         * @param fallbackBiome the fallback biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fallbackBiome(Biome fallbackBiome) {
            this.fallbackBiome = fallbackBiome;
            return this;
        }

        /**
         * Sets the lakes to generate.
         * @param lakes the lakes to generate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lakes(List<PlacedFeature> lakes) {
            this.lakes = lakes;
            return this;
        }

        /**
         * Sets the keys of structures to generate.
         * @param structures the keys of structures to generate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder structures(Set<ResourceKey> structures) {
            this.structures = structures;
            return this;
        }

        // Friendly

        /**
         * Adds a layer to the list of layers.
         * @param block the block to add
         * @param height the height of the layer
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder layer(Material block, int height) {
            this.layers.add(FlatLayerInfo.of(block, height));
            return this;
        }

        /**
         * Adds a lake to the list of lakes.
         * @param lake the lake to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lake(PlacedFeature lake) {
            this.lakes.add(lake);
            return this;
        }

        /**
         * Adds a structure to the list of structures.
         * @param structure the structure to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder structure(ResourceKey... structure) {
            if (this.structures == null) {
                this.structures = new HashSet<>();
            }
            this.structures.addAll(List.of(structure));
            return this;
        }

        /**
         * Builds the FlatLevelGeneratorSettings.
         * @return the FlatLevelGeneratorSettings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FlatLevelGeneratorSettings build() {
            return of(layers, decoration, addLakes, biome, fallbackBiome, lakes, structures);
        }
    }
}
