package dev.wyck.paper.configs;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class WyckPluginConfig {
    @Comment("Whether to enable FastStats metrics collection for this plugin.")
    @Setting("metrics")
    public boolean metrics = true;

    @Comment(
            "Wyck may force a specific packet handler implementation to be used by all plugins that hook into it.\n" +
            "If a plugin attempts to use another injector, Wyck will silently redirect it to the injector defined below.\n" +
            "\n" +
            "* PROTOCOLLIB: Requires ProtocolLib to be installed alongside Wyck.\n" +
            "* PACKETEVENTS: Requires PacketEvents to be installed alongside Wyck. PacketEvents does not re-sync registries,\n" +
            "* plugins that register custom biomes after the server has started will not have their biomes rendered unless you enable '-Dpacketevents.force-per-user-registries=true'\n" +
            "* NETTY: Standalone implementation that does not require any external dependencies.\n" +
            "* NONE: No forced injector. Plugins that have chosen a specific injector will have a single instance of their injector used for sending packet-based biomes."
    )
    @Setting("forced-injector")
    public ForcedInjector forcedInjector = ForcedInjector.NONE;

}
