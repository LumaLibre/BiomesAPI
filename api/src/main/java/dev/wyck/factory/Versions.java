package dev.wyck.factory;

import dev.wyck.annotations.AsOf;
import dev.wyck.exceptions.UnsupportedVersionException;
import io.papermc.paper.ServerBuildInfo;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NullMarked;

/**
 * An enum representing supported Minecraft versions for Wyck.
 * Each enum value represents a specific Minecraft version and includes a Version object that contains information about the version.
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public enum Versions {
    V1_21_10(Version.of(Version.Type.UNSUPPORTED, "v1_21_10", "1.21.9", "1.21.10")),
    V1_21_11(Version.of(Version.Type.SUPPORTED, "v1_21_11", "1.21.11")),
    V26_1(Version.of(Version.Type.SUPPORTED, "v26_1", "26.1", "26.1.1", "26.1.2")),
    V26_2(Version.of(Version.Type.SUPPORTED, "v26_2", "26.2"));

    public static final Version ACTIVE = active();

    private final Version version;

    Versions(Version version) {
        this.version = version;
    }

    public Version version() {
        return version;
    }

    @AsOf("2.0.0")
    public static Version active() {
        String mc;
        try {
            mc = ServerBuildInfo.buildInfo().minecraftVersionId();
        } catch (Exception _) { // Listening for ClassNotFoundException; Should never happen
            mc = Bukkit.getMinecraftVersion();
        }

        for (Versions versions : values()) {
            if (versions.version().isApplicable(mc)) {
                Version del = versions.version();
                if (del.type() == Version.Type.UNSUPPORTED) {
                    throw new UnsupportedVersionException(del.message());
                }
                return del;
            }
        }

        throw new UnsupportedVersionException(Version.of(Version.Type.UNKNOWN, mc).message());
    }
}
