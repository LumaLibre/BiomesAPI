package dev.wyck.worldgen.noise.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.BiomeSpawner;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.entity.LevelSpawner;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.climate.ClimatePoint;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.worldgen.noise.NoiseSettings;
import dev.wyck.worldgen.surface.SurfaceRule;
import dev.wyck.wrapper.Registerable;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Noise settings are for generating the shape of the terrain and noise caves and what blocks the terrain is generated with.
 *
 * @see <a href="https://minecraft.wiki/w/Noise_settings">Noise settings</a>
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseGeneratorSettings extends Noise, Registerable<NoiseGeneratorSettings> {

    /**
     * Fields for world generation.
     * @return the noise settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    NoiseSettings noiseSettings(); // codec name: noise

    /**
     * The default block used for the terrain.
     * @return the default block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData defaultBlock();

    /**
     * The default block used for seas and lakes.
     * @return the default fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData defaultFluid();

    /**
     * The noise router routes density functions to noise parameters used for world generation.
     * @return the noise router
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    NoiseRouter noiseRouter();

    /**
     * The main surface rule to place blocks in the terrain.
     * @return the surface rule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    SurfaceRule surfaceRule();

    /**
     * A list of climate parameters to specify the points around which the player tries to spawn.
     * The player spawns near the location where this value is smallest.
     * @return the spawn target
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<ClimatePoint> spawnTarget();

    /**
     * The sea level in this dimension.
     * Note that this value only affects world generation.
     * The sea level for mob spawning is a fixed value 63.
     * @return the sea level
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int seaLevel();

    /**
     * Disables creature spawning upon chunk generation.
     * @deprecated Deprecated in Minecraft.
     * Likely replacement would be an empty {@link BiomeSpawner} and {@link LevelSpawner}.
     * @return whether mob generation is disabled
     * @since 3.0.0
     */
    @Deprecated
    @AsOf("3.0.0")
    boolean disableMobGeneration();

    /**
     * Whether aquifers generate. If set to false, almost all caves below sea level are filled with water.
     * @return whether aquifers generate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean aquifersEnabled();

    /**
     * Whether ore veins generate.
     * @return whether ore veins generate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean oreVeinsEnabled();

    /**
     * Whether to use the old random number generator from before 1.18 for world generation.
     * @return whether to use the old random number generator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean useLegacyRandomSource();

    /**
     * Converts this object back to a builder.
     * @return A new builder with these values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new noise generator settings.
     * @param resourceKey the resource key to register this noise generator settings with
     * @param noiseSettings the noise settings
     * @param defaultBlock the default block
     * @param defaultFluid the default fluid
     * @param noiseRouter the noise router
     * @param surfaceRule the surface rule
     * @param spawnTarget the spawn target
     * @param seaLevel the sea level
     * @param disableMobGeneration whether to disable mob generation
     * @param aquifersEnabled whether aquifers generate
     * @param oreVeinsEnabled whether ore veins generate
     * @param useLegacyRandomSource whether to use the old random number generator
     * @return a new noise generator settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseGeneratorSettings of(@Nullable ResourceKey resourceKey, NoiseSettings noiseSettings, BlockData defaultBlock, BlockData defaultFluid, NoiseRouter noiseRouter, SurfaceRule surfaceRule, List<ClimatePoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {
        record Holder() {
            static final ConstructWireProvider<NoiseGeneratorSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.types.NoiseGeneratorSettingsImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), noiseSettings, defaultBlock, defaultFluid, noiseRouter, surfaceRule, spawnTarget, seaLevel, disableMobGeneration, aquifersEnabled, oreVeinsEnabled, useLegacyRandomSource);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NoiseGeneratorSettings}.
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey = null;
        private NoiseSettings noiseSettings = NoiseSettings.OVERWORLD;
        private BlockData defaultBlock = BukkitBootstrapUtil.util().createBlockData(Material.STONE);
        private BlockData defaultFluid = BukkitBootstrapUtil.util().createBlockData(Material.WATER);
        private @Nullable NoiseRouter noiseRouter = null;
        private @Nullable SurfaceRule surfaceRule = null;
        private List<ClimatePoint> spawnTarget = new ArrayList<>();
        private int seaLevel = 63;
        private boolean disableMobGeneration = false;
        private boolean aquifersEnabled = true;
        private boolean oreVeinsEnabled = true;
        private boolean useLegacyRandomSource = false;

        public Builder() {}

        public Builder(NoiseGeneratorSettings settings) {
            this.resourceKey = settings.resourceKey().orElse(null);
            this.noiseSettings = settings.noiseSettings();
            this.defaultBlock = settings.defaultBlock();
            this.defaultFluid = settings.defaultFluid();
            this.noiseRouter = settings.noiseRouter();
            this.surfaceRule = settings.surfaceRule();
            this.spawnTarget = new ArrayList<>(settings.spawnTarget());
            this.seaLevel = settings.seaLevel();
            this.disableMobGeneration = settings.disableMobGeneration();
            this.aquifersEnabled = settings.aquifersEnabled();
            this.oreVeinsEnabled = settings.oreVeinsEnabled();
            this.useLegacyRandomSource = settings.useLegacyRandomSource();
        }

        /**
         * Sets the resource key for this object.
         * @param resourceKey the resource key
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder resourceKey(@Nullable ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the noise settings.
         * @param noiseSettings the noise settings
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder noiseSettings(NoiseSettings noiseSettings) {
            this.noiseSettings = noiseSettings;
            return this;
        }

        /**
         * Sets the default block.
         * @param defaultBlock the default block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder defaultBlock(BlockData defaultBlock) {
            this.defaultBlock = defaultBlock;
            return this;
        }

        /**
         * Sets the default fluid.
         * @param defaultFluid the default fluid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder defaultFluid(BlockData defaultFluid) {
            this.defaultFluid = defaultFluid;
            return this;
        }

        /**
         * Sets the noise router.
         * @param noiseRouter the noise router
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder noiseRouter(NoiseRouter noiseRouter) {
            this.noiseRouter = noiseRouter;
            return this;
        }

        /**
         * Sets the surface rule.
         * @param surfaceRule the surface rule
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder surfaceRule(SurfaceRule surfaceRule) {
            this.surfaceRule = surfaceRule;
            return this;
        }

        /**
         * Sets the spawn target.
         * @param spawnTarget the spawn target
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder spawnTarget(List<ClimatePoint> spawnTarget) {
            this.spawnTarget = List.copyOf(spawnTarget);
            return this;
        }

        /**
         * Sets the sea level.
         * @param seaLevel the sea level
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder seaLevel(int seaLevel) {
            this.seaLevel = seaLevel;
            return this;
        }

        /**
         * Sets whether to disable mob generation.
         * @deprecated Deprecated in Minecraft.
         * @param disableMobGeneration whether to disable mob generation
         * @return this builder
         * @since 2.4.0
         */
        @Deprecated
        @AsOf("2.4.0")
        public Builder disableMobGeneration(boolean disableMobGeneration) {
            this.disableMobGeneration = disableMobGeneration;
            return this;
        }

        /**
         * Sets whether aquifers generate.
         * @param aquifersEnabled whether aquifers generate
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder aquifersEnabled(boolean aquifersEnabled) {
            this.aquifersEnabled = aquifersEnabled;
            return this;
        }

        /**
         * Sets whether ore veins generate.
         * @param oreVeinsEnabled whether ore veins generate
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder oreVeinsEnabled(boolean oreVeinsEnabled) {
            this.oreVeinsEnabled = oreVeinsEnabled;
            return this;
        }

        /**
         * Sets whether to use the old random number generator from before 1.18 for world generation.
         * @param useLegacyRandomSource whether to use the old random number generator
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder useLegacyRandomSource(boolean useLegacyRandomSource) {
            this.useLegacyRandomSource = useLegacyRandomSource;
            return this;
        }

        // Friendly

        /**
         * Sets the default block.
         * @param defaultBlock the default block
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultBlock(Material defaultBlock) {
            this.defaultBlock = BukkitBootstrapUtil.util().createBlockData(defaultBlock);
            return this;
        }

        /**
         * Sets the default fluid.
         * @param defaultFluid the default fluid
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultFluid(Material defaultFluid) {
            this.defaultFluid = BukkitBootstrapUtil.util().createBlockData(defaultFluid);
            return this;
        }

        /**
         * Adds a spawn target.
         * @param spawnTarget the spawn target
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spawnTarget(ClimatePoint... spawnTarget) {
            Collections.addAll(this.spawnTarget, spawnTarget);
            return this;
        }

        /**
         * Builds the noise generator settings.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseGeneratorSettings build() {
            Preconditions.checkArgument(this.noiseRouter != null, "noiseRouter must be set");
            Preconditions.checkArgument(this.surfaceRule != null, "surfaceRule must be set");
            return of(
                resourceKey,
                noiseSettings,
                defaultBlock,
                defaultFluid,
                noiseRouter,
                surfaceRule,
                spawnTarget,
                seaLevel,
                disableMobGeneration,
                aquifersEnabled,
                oreVeinsEnabled,
                useLegacyRandomSource
            );
        }

        /**
         * Registers this noise generator settings with the registry.
         * @return this noise generator settings
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseGeneratorSettings register() {
            NoiseGeneratorSettings settings = build();
            settings.register();
            return settings;
        }
    }
}