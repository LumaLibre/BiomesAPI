package dev.wyck.paper.renderer.packet;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.exceptions.MissingPacketManipulatorLibraryException;
import dev.wyck.paper.WyckPlugin;
import dev.wyck.paper.renderer.packet.handlers.MonotonicNettyHandler;
import dev.wyck.paper.renderer.packet.handlers.MonotonicPacketEventsHandler;
import dev.wyck.paper.renderer.packet.handlers.MonotonicProtocolLibHandler;
import dev.wyck.renderer.packet.PacketHandler;
import dev.wyck.renderer.packet.VirtualBiomeCollector;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NullMarked
@WireFactory
@AsOf("2.1.0")
@ApiStatus.Internal
public final class PacketHandlerFactoryPluginImpl implements PacketHandler.Factory {

    private final WyckPlugin plugin;
    private final Map<PacketHandler.Injector, PacketHandler> injectorMap = new HashMap<>();
    private final List<PacketHandler> pendingRegistration = new ArrayList<>();
    private final VirtualBiomeCollector collector = new VirtualBiomeCollector();

    private boolean enabled = false;

    public PacketHandlerFactoryPluginImpl(WyckPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PacketHandler create(Plugin provider, PacketHandler.Injector injector, PacketHandler.Priority priority) {
        PacketHandler.Injector effective = applyForcedOverride(injector);

        if (!effective.isAvailable()) {
            throw new MissingPacketManipulatorLibraryException(
                    "Could not find required classes for injector: " + effective.getName() +
                            ". Please ensure " + effective.getName() + " is installed.");
        }

        if (injectorMap.containsKey(effective)) {
            return injectorMap.get(effective);
        }

        PacketHandler handler = switch (effective) {
            case PROTOCOLLIB -> new MonotonicProtocolLibHandler(plugin, priority, collector);
            case PACKETEVENTS -> new MonotonicPacketEventsHandler(priority, collector);
            case NETTY -> new MonotonicNettyHandler("internal", collector);
        };

        if (enabled) {
            handler.register();
            plugin.getComponentLogger().info("Registered {} packet handler.", effective.getName());
        } else {
            pendingRegistration.add(handler);
            plugin.getComponentLogger().info("Deferred {} packet handler registration until onEnable.", effective.getName());
        }

        injectorMap.put(effective, handler);

        if (effective != injector) {
            plugin.getComponentLogger().info(
                    "Plugin {} requested {} but config forces {}.",
                    provider.getName(), injector.getName(), effective.getName());
        }
        return handler;
    }

    /**
     * Registers all handlers created before {@code onEnable}. Should be called from
     * {@link WyckPlugin#onEnable()}.
     */
    public void enableDeferredHandlers() {
        for (PacketHandler handler : pendingRegistration) {
            handler.register();
            plugin.getComponentLogger().info("Registered deferred {} packet handler.",
                    handler.getClass().getSimpleName());
        }
        pendingRegistration.clear();
        enabled = true;
    }

    public Collection<PacketHandler> getHandlers() {
        return injectorMap.values();
    }

    private PacketHandler.Injector applyForcedOverride(PacketHandler.Injector requested) {
        PacketHandler.Injector override = plugin.getPluginConfig().forcedInjector.injector();
        return override != null ? override : requested;
    }
}