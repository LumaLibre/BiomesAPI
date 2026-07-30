package dev.wyck.registry;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.biome.ClimateSettings;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.util.attribute.EnvironmentAttributesUtil;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * This class implements the BiomeRegistry interface and provides a method to register a custom biome to a Minecraft server.
 * It uses the BiomeLock class to unlock the biome registry before registering the custom biome, and then freezes the registry again.
 *
 * @version 2.0.0
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
@ApiStatus.Internal
public class BiomeRegistryImpl implements BiomeRegistry {

    private final Lazy<WyckRegistry> registry = WyckRegistry.lazy(RegistryId.BIOME);

    @Override
    @AsOf("2.3.0")
    public net.minecraft.world.level.biome.Biome buildDelegate(Biome biome) {
        Preconditions.checkNotNull(biome, "biome cannot be null");

        ClimateSettings settings = biome.climateSettings();
        BiomeSpecialEffects specialEffects = biome.specialEffects();
        EnvironmentAttributeMap attributes = biome.attributes();
        BiomeSpawner spawner = biome.biomeSpawner();
        BiomeGenerationSettings generationSettings = biome.generationSettings();

        net.minecraft.world.level.biome.Biome.BiomeBuilder biomeBuilder = new net.minecraft.world.level.biome.Biome.BiomeBuilder()
            .hasPrecipitation(settings.hasPrecipitation())
            .downfall(settings.downfall())
            .temperature(settings.temperature())
            .temperatureAdjustment(settings.temperatureModifier().toNms(net.minecraft.world.level.biome.Biome.TemperatureModifier.class))
            .specialEffects(specialEffects.asHandle())
            .mobSpawnSettings(spawner != null ? spawner.asHandle() : net.minecraft.world.level.biome.MobSpawnSettings.EMPTY)
            .generationSettings(generationSettings != null ? generationSettings.asHandle() : net.minecraft.world.level.biome.BiomeGenerationSettings.EMPTY);

        if (!attributes.empty()) {
            EnvironmentAttributesUtil.applyTo(biomeBuilder, attributes);
        }

        return biomeBuilder.build();
    }

    @Override
    @AsOf("2.3.1")
    @SuppressWarnings("unchecked")
    public void register(Collection<Biome> biomes) {
        Preconditions.checkNotNull(biomes, "biomes cannot be null");

        this.registry.get().whileUnfrozen(() -> {
            Registry<net.minecraft.world.level.biome.Biome> registry = (Registry<net.minecraft.world.level.biome.@NonNull Biome>) this.registry.get().toMinecraft();
            for (Biome biome : biomes) {
                Identifier resourceLocation = biome.resourceKey().identifier();

                net.minecraft.world.level.biome.Biome createdBiome = buildDelegate(biome);

                if (!registry.containsKey(resourceLocation)) {
                    Registry.register(registry, resourceLocation, createdBiome);
                }

                try {
                    KeyChains.BIOMES.append(biome);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    @AsOf("0.0.8")
    public void modify(Collection<Biome> biomes) {
        for (Biome abstractBiome : biomes) {
            ResourceKey key = abstractBiome.resourceKey();
            Preconditions.checkNotNull(key, "key cannot be null");
            Preconditions.checkNotNull(abstractBiome, "newData cannot be null");

            Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = this.registry.get().asHandle();
            net.minecraft.world.level.biome.Biome biome = biomeRegistry.getOptional((Identifier) key.resourceLocation()).orElseThrow(
                () -> new IllegalStateException("Biome " + key + " is not registered in the internal biome registry")
            );

            // Rebuild biome components
            net.minecraft.world.attribute.EnvironmentAttributeMap.Builder environmentAttributeMapBuilder = net.minecraft.world.attribute.EnvironmentAttributeMap.builder();
            EnvironmentAttributesUtil.applyTo(environmentAttributeMapBuilder, abstractBiome.attributes());

            net.minecraft.world.level.biome.Biome.ClimateSettings climateSettings = abstractBiome.climateSettings().asHandle();
            net.minecraft.world.level.biome.BiomeSpecialEffects specialEffects = abstractBiome.specialEffects().asHandle();
            net.minecraft.world.attribute.EnvironmentAttributeMap environmentAttributeMap = environmentAttributeMapBuilder.build();

            BiomeSpawner spawner = abstractBiome.biomeSpawner();
            BiomeGenerationSettings gen = abstractBiome.generationSettings();
            net.minecraft.world.level.biome.MobSpawnSettings mobSpawnSettings = spawner != null ? spawner.asHandle() : net.minecraft.world.level.biome.MobSpawnSettings.EMPTY;
            net.minecraft.world.level.biome.BiomeGenerationSettings generationSettings = gen != null ? gen.asHandle() : net.minecraft.world.level.biome.BiomeGenerationSettings.EMPTY;

            // Time to reflect
            try {
                // TODO: Clean this up with some reflection util
                Field climateSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("climateSettings");
                Field environmentAttributesField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("attributes");
                Field specialEffectsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("specialEffects");
                Field mobSpawnSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("mobSettings");
                Field generationSettingsField = net.minecraft.world.level.biome.Biome.class.getDeclaredField("generationSettings");

                climateSettingsField.setAccessible(true);
                environmentAttributesField.setAccessible(true);
                specialEffectsField.setAccessible(true);
                mobSpawnSettingsField.setAccessible(true);
                generationSettingsField.setAccessible(true);

                climateSettingsField.set(biome, climateSettings);
                environmentAttributesField.set(biome, environmentAttributeMap);
                specialEffectsField.set(biome, specialEffects);
                mobSpawnSettingsField.set(biome, mobSpawnSettings);
                generationSettingsField.set(biome, generationSettings);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to modify biome settings", e);
            }

            KeyChains.BIOMES.replace(key, abstractBiome);
        }
    }

    @AsOf("2.3.0")
    @SuppressWarnings("unchecked")
    public @Nullable Biome getBiome(ResourceKey key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        if (KeyChains.BIOMES.isRegistered(key)) {
            return KeyChains.BIOMES.getOrThrow(key);
        }

        Registry<net.minecraft.world.level.biome.Biome> biomeRegistry = this.registry.get().asHandle();
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> minecraftKey =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.BIOME, (Identifier) key.resourceLocation());
        return biomeRegistry.get(minecraftKey).map(Biome::decode).orElse(null);
    }
}
