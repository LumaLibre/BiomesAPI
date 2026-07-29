package dev.wyck.paper.configs;

import dev.wyck.renderer.packet.PacketHandler;
import org.jspecify.annotations.Nullable;

public enum ForcedInjector {
    PROTOCOLLIB(PacketHandler.Injector.PROTOCOLLIB),
    PACKETEVENTS(PacketHandler.Injector.PACKETEVENTS),
    NETTY(PacketHandler.Injector.NETTY),
    NONE(null);

    private final PacketHandler.@Nullable Injector injector;

    ForcedInjector(PacketHandler.@Nullable Injector injector) {
        this.injector = injector;
    }

    public PacketHandler.@Nullable Injector injector() {
        return injector;
    }

    @Override
    public String toString() {
        return injector == null ? "None" : injector.getName();
    }
}
