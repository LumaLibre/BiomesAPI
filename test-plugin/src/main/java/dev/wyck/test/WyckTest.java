package dev.wyck.test;

import dev.wyck.biome.Biome;
import dev.wyck.biome.Biomes;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.noise.Noise;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Biome customPlains = Biomes.PLAINS
            .wrap()
            .toBuilder()
            .resourceKey(ResourceKey.of("wyck:custom_plains"))
            .attribute(EnvironmentAttributes.FOG_COLOR, "#FF0000")
            .register();

        System.out.println(customPlains);

        Noise noise = Noise.overworld()
                .wrap();

        System.out.println(noise);
    }
}