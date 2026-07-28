package dev.wyck.paper;

import dev.faststats.bukkit.BukkitContext;
import dev.faststats.data.Metric;
import dev.wyck.Wyck;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.KeyChains;
import dev.wyck.paper.configs.WyckPluginConfig;
import dev.wyck.paper.renderer.packet.PacketHandlerFactoryPluginImpl;
import dev.wyck.renderer.packet.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

@WireFactory
@AsOf("2.1.0")
@ApiStatus.Internal
public final class WyckPlugin extends JavaPlugin implements Wyck {

    public static boolean STOPPING = false;
    private static final String CONFIG_FILE = "settings.yml";
    private static final String METRICS_TOKEN = "1fad62f4a237fd41d17d1d328ccd12bd"; // not sensitive

    private final PacketHandlerFactoryPluginImpl handlerFactory;
    private final WyckPluginConfig config;
    private BukkitContext metrics;

    public WyckPlugin() {
        WyckPluginConfig loaded;
        try {
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(getDataPath().resolve(CONFIG_FILE))
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .build();

            CommentedConfigurationNode root = loader.load();
            loaded = root.get(WyckPluginConfig.class, new WyckPluginConfig());

            root.set(WyckPluginConfig.class, loaded);
            loader.save(root);
        } catch (Throwable e) {
            getComponentLogger().error("Failed to load config", e);
            loaded = new WyckPluginConfig();
        }

        this.config = loaded;
        this.handlerFactory = new PacketHandlerFactoryPluginImpl(this);
        PacketHandler.WIRE.setProvider(WyckPlugin.class, this.handlerFactory);
        getComponentLogger().info("Wired Wyck PacketHandler to {}", this.handlerFactory.getClass().getName());
    }

    @Override
    public void onEnable() {
        this.handlerFactory.enableDeferredHandlers();

        if (this.config.metrics()) {
            this.metrics = new BukkitContext.Factory(this, METRICS_TOKEN)
                .metrics(factory -> factory
                    .addMetric(Metric.string("forced_injector", () -> config.forcedInjector().toString()))
                    .addMetric(Metric.number("registered_biomes", KeyChains.BIOMES::size))
                    .create()
                )
                .create();


            this.metrics.ready();
            getComponentLogger().info("Metrics enabled");
        }

        getComponentLogger().info("Wyck v{} running on Minecraft {}", version(), Bukkit.getMinecraftVersion());
    }

    @Override
    public void onDisable() {
        STOPPING = true;
        for (PacketHandler packetHandler : handlerFactory.getHandlers()) {
            packetHandler.unregister();
        }
        if (metrics != null) {
            this.metrics.shutdown();
            getComponentLogger().info("Metrics disabled");
        }
    }

    public WyckPluginConfig getPluginConfig() {
        return config;
    }

    @Override
    public @NonNull JavaPlugin plugin() {
        return this;
    }
}
