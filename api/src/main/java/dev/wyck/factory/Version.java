package dev.wyck.factory;

import dev.wyck.annotations.AsOf;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NullMarked;

/**
 * A record class representing a version of Minecraft.
 * @param applicable the versions that this version is applicable to
 * @param id the version ID
 * @param type the type of version
 */
@NullMarked
@AsOf("2.0.0")
public record Version(String[] applicable, String id, Type type) {

    /**
     * Returns the message associated with the version.
     * @return the message associated with the version
     */
    @AsOf("2.0.0")
    public String message() {
        return type.message();
    }

    /**
     * Checks if the version is applicable to the given version.
     * @param version the version to check
     * @return true if the version is applicable, false otherwise
     */
    @AsOf("2.0.0")
    public boolean isApplicable(String version) {
        for (String s : applicable) {
            if (version.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new Version instance.
     * @param type the type of version
     * @param id the version ID
     * @param applicable the versions that this version is applicable to
     * @return a new Version instance
     */
    @AsOf("2.0.0")
    public static Version of(Type type, String id, String... applicable) {
        return new Version(applicable, id, type);
    }

    /**
     * The type of version.
     *
     * @version 2.0.0
     * @since 2.0.0
     * @author Jsinco
     */
    @AsOf("2.0.0")
    public enum Type {
        SUPPORTED(
                "Server version (%s) running Wyck (%s)."
        ),
        UNSUPPORTED(
                "This server version (%s) is not supported by this version of Wyck (%s), but may be supported by an older release of Wyck. Update your server or use an older release: https://github.com/LumaLibre/Wyck/releases"
        ),
        UNKNOWN(
                "This server version (%s) is either too new or too old to be recognized by this version of Wyck (%s). Please check for a new release of Wyck that supports your server version: https://github.com/LumaLibre/Wyck/releases"
        );

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public String message() {
            return String.format(message, Bukkit.getMinecraftVersion(), BuildInfo.VERSION);
        }
    }
}
