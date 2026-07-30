package dev.wyck.connection;

import dev.wyck.Wyck;
import dev.wyck.annotations.AsOf;
import dev.wyck.exceptions.HorriblePlayerLoginEvent;
import dev.wyck.factory.ConstructWireProvider;
import io.papermc.paper.connection.PlayerConfigurationConnection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/**
 * An interface for resending registries to players without relogging.
 * @since 2.2.0
 * @version 2.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.2.0")
public interface RegistryReconfigurer {

    /**
     * Creates a new RegistryReconfigurer instance.
     * @return a new RegistryReconfigurer instance
     */
    @AsOf("2.2.0")
    static RegistryReconfigurer newReconfigurer() {
        return newReconfigurer(Wyck.wyck().plugin());
    }

    /**
     * Creates a new RegistryReconfigurer instance.
     * @param provider the plugin providing the RegistryReconfigurer
     * @return a new RegistryReconfigurer instance
     */
    @AsOf("2.2.0")
    static RegistryReconfigurer newReconfigurer(Plugin provider) {
        record Holder() {
            static final ConstructWireProvider<RegistryReconfigurer> WIRE = ConstructWireProvider.create("dev.wyck.connection.RegistryReconfigurerImpl");
        }
        return Holder.WIRE.construct(provider);
    }

    /**
     * Resends the registries to the player.
     * @param player the player to resend the registries to
     * @throws HorriblePlayerLoginEvent if there are plugins on the server that use {@link org.bukkit.event.player.PlayerLoginEvent}
     */
    @AsOf("2.2.0")
    default void resendRegistries(Player player) throws HorriblePlayerLoginEvent {
        resendRegistries(player, null);
    }

    /**
     * Resends the registries to the player.
     * @param player the player to resend the registries to
     * @param consumer the consumer to call after the player has been placed into the configuration phase, but before the registries have been resent.
     * @throws HorriblePlayerLoginEvent if there are plugins on the server that use {@link org.bukkit.event.player.PlayerLoginEvent}
     */
    @AsOf("2.2.0")
    void resendRegistries(Player player, @SuppressWarnings("UnstableApiUsage") @Nullable Consumer<PlayerConfigurationConnection> consumer) throws HorriblePlayerLoginEvent;

}
