package dev.wyck.biome;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.biome.internal.CustomBiomeImpl;
import dev.wyck.environment.GrassColorModifier;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.attribute.FriendlyColorSupplier;
import dev.wyck.environment.particle.ParticleCatalog;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.keys.ResourceKey;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.data.BlockReplacement;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Extends {@link Biome} with client-side block replacements and friendly color accessors.
 *
 * @since 0.0.1
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CustomBiome extends Biome {

    /**
     * The block replacements of this custom biome.
     * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
     * @return the block replacements of this custom biome.
     * @since 0.0.6
     */
    @AsOf("3.0.0")
    List<BlockReplacement> blockReplacements();

    /**
     * Sets the block replacements of this custom biome.
     * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
     * @param blockReplacements the block replacements to set for this custom biome
     * @return this custom biome
     * @since 0.0.6
     */
    @AsOf("3.0.0")
    CustomBiome blockReplacements(List<BlockReplacement> blockReplacements);

    /**
     * @return the water color of this custom biome.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default int waterColor() {
        return specialEffects().waterColor();
    }

    /**
     * @return the foliage color override of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer foliageColor() {
        return specialEffects().foliageColorOverride().orElse(null);
    }

    /**
     * @return the dry foliage color override of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer dryFoliageColor() {
        return specialEffects().dryFoliageColorOverride().orElse(null);
    }

    /**
     * @return the grass color override of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer grassColor() {
        return specialEffects().grassColorOverride().orElse(null);
    }

    /**
     * @return the grass color modifier of this custom biome.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default GrassColorModifier grassColorModifier() {
        return specialEffects().grassColorModifier();
    }

    /**
     * @return the fog color of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer fogColor() {
        return attributes().get(EnvironmentAttributes.FOG_COLOR);
    }

    /**
     * @return the water fog color of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer waterFogColor() {
        return attributes().get(EnvironmentAttributes.WATER_FOG_COLOR);
    }

    /**
     * @return the sky color of this custom biome, or null if unset.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default @Nullable Integer skyColor() {
        return attributes().get(EnvironmentAttributes.SKY_COLOR);
    }

    /**
     * Sets the water color of this custom biome.
     * @param waterColor the water color hex (e.g. {@code "#3F75C4"})
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome waterColor(String waterColor) {
        specialEffects(specialEffects().toBuilder().waterColor(waterColor).build());
        return this;
    }

    /**
     * Sets the foliage color override of this custom biome.
     * @param foliageColor the foliage color hex, or null to clear the override
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome foliageColor(@Nullable String foliageColor) {
        specialEffects(specialEffects().toBuilder().foliageColorOverride(foliageColor).build());
        return this;
    }

    /**
     * Sets the dry foliage color override of this custom biome.
     * @param dryFoliageColor the dry foliage color hex, or null to clear the override
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome dryFoliageColor(@Nullable String dryFoliageColor) {
        specialEffects(specialEffects().toBuilder().dryFoliageColorOverride(dryFoliageColor).build());
        return this;
    }

    /**
     * Sets the grass color override of this custom biome.
     * @param grassColor the grass color hex, or null to clear the override
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome grassColor(@Nullable String grassColor) {
        specialEffects(specialEffects().toBuilder().grassColorOverride(grassColor).build());
        return this;
    }

    /**
     * Sets the grass color modifier of this custom biome.
     * @param grassColorModifier the grass color modifier
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome grassColorModifier(GrassColorModifier grassColorModifier) {
        specialEffects(specialEffects().toBuilder().grassColorModifier(grassColorModifier).build());
        return this;
    }

    /**
     * Sets the fog color of this custom biome.
     * @param fogColor the fog color hex, or null to clear it
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome fogColor(@Nullable String fogColor) {
        attributes(attributes().with(EnvironmentAttributes.FOG_COLOR, fogColor));
        return this;
    }

    /**
     * Sets the water fog color of this custom biome.
     * @param waterFogColor the water fog color hex, or null to clear it
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome waterFogColor(@Nullable String waterFogColor) {
        attributes(attributes().with(EnvironmentAttributes.WATER_FOG_COLOR, waterFogColor));
        return this;
    }

    /**
     * Sets the sky color of this custom biome.
     * @param skyColor the sky color hex, or null to clear it
     * @return this custom biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default CustomBiome skyColor(@Nullable String skyColor) {
        attributes(attributes().with(EnvironmentAttributes.SKY_COLOR, skyColor));
        return this;
    }

    /**
     * Compares this custom biome to another to determine if they are similar.
     * @param otherBiome the other custom biome to compare to
     * @return true if the custom biomes are similar, false otherwise.
     * @since 0.0.17
     */
    @AsOf("0.0.17")
    boolean isSimilar(CustomBiome otherBiome);

    /**
     * Registers this custom biome in the biome registry.
     * @return the registered custom biome
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    default CustomBiome register() {
        Biome.super.register();
        return this;
    }

    /**
     * Modifies this custom biome.
     * @return the modified custom biome
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    default CustomBiome modify() {
        Biome.super.modify();
        return this;
    }

    /**
     * Converts this back into a builder.
     * @return a builder with the same properties as this custom biome.
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    default CustomBiome.Builder toBuilder() {
        return new CustomBiome.Builder(this);
    }

    /**
     * A new builder for this class.
     * @return a new, empty {@link Builder}.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CustomBiome.Builder builder() {
        return new CustomBiome.Builder();
    }

    /**
     * @param resourceKey the ResourceKey to seed the builder with
     * @return a new {@link Builder} seeded with the given resource key.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CustomBiome.Builder builder(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "resourceKey cannot be null.");
        return new CustomBiome.Builder().resourceKey(resourceKey);
    }

    /**
     * Creates a new custom biome with the given ResourceKey.
     * @param resourceKey the ResourceKey of the custom biome to create
     * @return a new custom biome with the given ResourceKey
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CustomBiome of(ResourceKey resourceKey) {
        return builder(resourceKey).build();
    }

    /**
     * Builder for {@link CustomBiome}. Configures every property the base biome exposes plus block replacements.
     *
     * @since 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends Biome.Builder {
        private @Nullable ResourceKey resourceKey = null;
        private ClimateSettings climateSettings = ClimateSettings.DEFAULT;
        private BiomeSpecialEffects specialEffects = BiomeSpecialEffects.DEFAULT;
        private EnvironmentAttributeMap attributeMap = EnvironmentAttributeMap.EMPTY;
        private @Nullable BiomeSpawner biomeSpawner = null;
        private @Nullable BiomeGenerationSettings generationSettings = null;
        private List<BlockReplacement> blockReplacements = new ArrayList<>();
        private ParticleCatalog.@Nullable Builder particleCatalog;

        @AsOf("3.0.0")
        public Builder() {}

        @AsOf("3.0.0")
        public Builder(CustomBiome other) {
            this.resourceKey = other.resourceKey();
            this.climateSettings = other.climateSettings();
            this.specialEffects = other.specialEffects();
            this.attributeMap = other.attributes();
            this.biomeSpawner = other.biomeSpawner();
            this.generationSettings = other.generationSettings();
            this.blockReplacements = other.blockReplacements();
        }

        /**
         * Sets the ResourceKey of the custom biome.
         * @param resourceKey the ResourceKey of the custom biome
         * @return this builder
         */
        @AsOf("2.3.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the climate settings of the custom biome.
         * @param climateSettings the climate settings of the custom biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder climateSettings(ClimateSettings climateSettings) {
            this.climateSettings = climateSettings;
            return this;
        }

        /**
         * Sets the special effects of the custom biome.
         * @param specialEffects the special effects of the custom biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder specialEffects(BiomeSpecialEffects specialEffects) {
            this.specialEffects = specialEffects;
            return this;
        }

        /**
         * Sets the environment attributes of the custom biome.
         * @param attributeMap the environment attributes of the custom biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder attributes(EnvironmentAttributeMap attributeMap) {
            this.attributeMap = this.attributeMap.with(attributeMap);
            return this;
        }

        /**
         * Sets the BiomeSpawner of the custom biome.
         * @param biomeSpawner the BiomeSpawner of the custom biome, or null to remove it
         * @return this builder
         * @since 2.3.0
         */
        public Builder biomeSpawner(@Nullable BiomeSpawner biomeSpawner) {
            this.biomeSpawner = biomeSpawner;
            return this;
        }

        /**
         * Sets the generation settings of the custom biome.
         * @param generationSettings the generation settings of the custom biome, or null to remove it
         * @return this builder
         * @since 2.3.0
         */
        public Builder generationSettings(@Nullable BiomeGenerationSettings generationSettings) {
            this.generationSettings = generationSettings;
            return this;
        }

        // Friendly builder methods

        @AsOf("3.0.0")
        public Builder waterColor(String waterColor) {
            this.specialEffects = this.specialEffects.toBuilder().waterColor(waterColor).build();
            return this;
        }

        @AsOf("3.0.0")
        public Builder foliageColor(@Nullable String foliageColor) {
            this.specialEffects = this.specialEffects.toBuilder().foliageColorOverride(foliageColor).build();
            return this;
        }

        @AsOf("3.0.0")
        public Builder dryFoliageColor(@Nullable String dryFoliageColor) {
            this.specialEffects = this.specialEffects.toBuilder().dryFoliageColorOverride(dryFoliageColor).build();
            return this;
        }

        @AsOf("3.0.0")
        public Builder grassColor(@Nullable String grassColor) {
            this.specialEffects = this.specialEffects.toBuilder().grassColorOverride(grassColor).build();
            return this;
        }

        @AsOf("3.0.0")
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.specialEffects = this.specialEffects.toBuilder().grassColorModifier(grassColorModifier).build();
            return this;
        }

        @AsOf("3.0.0")
        public Builder waterFogColor(@Nullable String waterFogColor) {
            this.attribute(EnvironmentAttributes.WATER_FOG_COLOR, waterFogColor);
            return this;
        }

        @AsOf("3.0.0")
        public Builder fogColor(@Nullable String fogColor) {
            this.attribute(EnvironmentAttributes.FOG_COLOR, fogColor);
            return this;
        }

        @AsOf("3.0.0")
        public Builder skyColor(@Nullable String skyColor) {
            this.attribute(EnvironmentAttributes.SKY_COLOR, skyColor);
            return this;
        }

        @AsOf("3.0.0")
        public Builder particle(ParticleTypes type, float probability, @Nullable ParticleData data) {
            if (this.particleCatalog == null) {
                this.particleCatalog = ParticleCatalog.builder();
            }
            this.particleCatalog.particle(type, probability, data);
            return this;
        }

        @AsOf("3.0.0")
        public Builder particle(ParticleTypes type, float probability) {
            return this.particle(type, probability, null);
        }

        @AsOf("3.0.0")
        public <V> Builder attribute(EnvironmentAttributeSupplier<V> attribute, @Nullable V value) {
            this.attributeMap = this.attributeMap.with(attribute, value);
            return this;
        }

        @AsOf("3.0.0")
        public Builder attribute(FriendlyColorSupplier attribute, @Nullable String hex) {
            this.attributeMap = this.attributeMap.with(attribute, hex);
            return this;
        }

        @AsOf("3.3.0")
        public Builder particles(ParticleCatalog particleCatalog) {
            this.particleCatalog = particleCatalog.toBuilder();
            return this;
        }

        // Block replacement builder methods

        /**
         * Sets the block replacements of the custom biome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param blockReplacements the block replacements of the custom biome
         * @return this builder
         * @since 0.0.6
         */
        @AsOf("3.0.0")
        public Builder blockReplacements(BlockReplacement... blockReplacements) {
            this.blockReplacements.addAll(List.of(blockReplacements));
            return this;
        }

        /**
         * Sets the block replacements of the custom biome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param blockReplacements the block replacements of the custom biome
         * @return this builder
         * @since 2.1.0
         */
        @AsOf("3.0.0")
        public Builder blockReplacements(Collection<BlockReplacement> blockReplacements) {
            this.blockReplacements.addAll(blockReplacements);
            return this;
        }

        /**
         * Adds a block replacement to the custom biome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param from the block material to be replaced
         * @param to the replacement block material
         * @return this builder
         * @since 2.1.0
         */
        @AsOf("3.0.0")
        public Builder replace(Material from, Material to) {
            return this.replace(new BlockReplacement(from, to));
        }

        /**
         * Adds a block replacement to the custom biome.
         * @apiNote Block replacements are only supported when rendering custom biomes to clients via the {@link PacketHandler}.
         * @param replacement the block replacement to add
         * @return this builder
         * @since 2.1.0
         */
        @AsOf("3.0.0")
        public Builder replace(BlockReplacement replacement) {
            this.blockReplacements.add(replacement);
            return this;
        }

        /**
         * Builds the custom biome.
         * @return a new custom biome
         * @throws IllegalArgumentException if the resource key is not set
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CustomBiome build() {
            Preconditions.checkArgument(resourceKey != null, "resourceKey must be set");
            Preconditions.checkNotNull(climateSettings, "climateSettings cannot be null");
            Preconditions.checkNotNull(specialEffects, "specialEffects cannot be null");
            Preconditions.checkNotNull(attributeMap, "attributeMap cannot be null");

            if (this.particleCatalog != null) {
                this.attribute(EnvironmentAttributes.AMBIENT_PARTICLES, this.particleCatalog.build());
            }

            return new CustomBiomeImpl(
                resourceKey,
                climateSettings,
                specialEffects,
                attributeMap,
                biomeSpawner,
                generationSettings,
                blockReplacements
            );
        }

        @AsOf("2.4.0")
        public CustomBiome register() {
            return build().register();
        }

        @AsOf("2.4.0")
        public CustomBiome modify() {
            return build().modify();
        }
    }
}