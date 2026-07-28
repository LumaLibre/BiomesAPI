package dev.wyck.paper;

import dev.wyck.Wyck;
import dev.wyck.annotations.AsOf;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

@AsOf("2.1.0")
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class WyckPluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {

    }

    @Override
    public @NonNull JavaPlugin createPlugin(PluginProviderContext context) {
        WyckPlugin plugin = new WyckPlugin();
        Wyck.WIRE.setProvider(WyckPluginBootstrap.class, plugin);
        context.getLogger().info("Wired Wyck WireProvider to {}", plugin.getClass().getName());
        return plugin;
    }
}
