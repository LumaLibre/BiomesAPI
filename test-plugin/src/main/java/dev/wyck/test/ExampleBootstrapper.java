package dev.wyck.test;

import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.bootstrap.BootstrapDimensionRegistry;
import dev.wyck.environment.BedRule;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.environment.particle.options.DustParticle;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.CardinalLightType;
import dev.wyck.level.dimension.Skybox;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import dev.wyck.biome.CustomBiome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.bootstrap.BootstrapBiomeRegistry;
import dev.wyck.registry.bootstrap.Composer;
import dev.wyck.biome.BiomeGenerationSettings;
import dev.wyck.worldgen.Decoration;
import dev.wyck.worldgen.placement.PlacedFeatures;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ExampleBootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {

        // This must use the 'DATAPACK' composer!
        BootstrapBiomeRegistry registry = BootstrapBiomeRegistry.compose(context, Composer.DATAPACK);
        BiomeGenerationSettings generation = BiomeGenerationSettings.builder()
            .feature(Decoration.VEGETAL_DECORATION, PlacedFeatures.TREES_PLAINS)
            .feature(Decoration.VEGETAL_DECORATION, PlacedFeatures.FALLEN_OAK_TREE)
            .build();

        ResourceKey myBiomeKey = ResourceKey.of("example", "my_biome2");
        registry.queue(
            CustomBiome.builder(myBiomeKey)
                .fogColor("#DB00FD")
                .skyColor("#2F46FF")
                .waterColor("#00FFD0")
                .grassColor("#D1D13A")
                .foliageColor("#FF6A00")
                .generationSettings(generation)
                .particle(ParticleTypes.DUST, 0.1f, DustParticle.of("#FF0000"))
                .particle(ParticleTypes.ASH, 0.01f)
                .build()
        );

        BootstrapDimensionRegistry dimRegistry = BootstrapDimensionRegistry.compose(context, Composer.DATAPACK);

        dimRegistry.queue(
            Dimension.builder()
                .resourceKey(ResourceKey.of("test:dimension"))
                .ambientLight(0.1f) // 10% ambient light
                .hasSkyLight(true)
                .defaultClock(WorldClock.OVERWORLD)
                .hasCeiling(false)
                .hasFixedTime(false)
                .hasEnderDragonFight(false)
                .skybox(Skybox.END)
                .minY(-64)
                .height(800)
                .logicalHeight(800)
                .cardinalLightType(CardinalLightType.DEFAULT)
                .attribute(EnvironmentAttributes.FOG_COLOR, "#FFAA00")
                .attribute(EnvironmentAttributes.SKY_COLOR, "#0000FF")
                .attribute(EnvironmentAttributes.BED_RULE, BedRule.builder()
                    .setErrorMessage(Component.text("No sleeping!").color(NamedTextColor.RED))
                    .canSleep(BedRule.Rule.NEVER)
                    .setCanSetSpawn(BedRule.Rule.WHEN_DARK)
                    .build()
                )
                .attribute(EnvironmentAttributes.STAR_BRIGHTNESS, 0.9f) // 90% star brightness
                .build()
        );
    }
}
