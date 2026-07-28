package dev.wyck.test;

import dev.wyck.biome.Biomes;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.keys.ResourceKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WyckTest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Biomes.PLAINS
            .wrap()
            .toBuilder()
            .resourceKey(ResourceKey.of("wyck:custom_plains"))
            .attribute(EnvironmentAttributes.FOG_COLOR, "#FF0000")
            .register(); // Register my new biome that's based on vanilla's `plains`.
    }
}