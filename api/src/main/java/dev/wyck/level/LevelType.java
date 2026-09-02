package dev.wyck.level;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * Normally a {@code WorldPreset} in vanilla,
 * the behavior of this enumerator is generally used
 * for client-sided behavior.
 * <p>
 * This representation is a simple enum in Wyck because
 * Wyck wraps the dedicated server.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public enum LevelType {

    NORMAL("normal"),
    FLAT("flat"),
    FLAT_ALL_DIMENSIONS("flat_all_dimensions"),
    LARGE_BIOMES("large_biomes"),
    AMPLIFIED("amplified"),
    SINGLE_BIOME_SURFACE("single_biome_surface"),
    DEBUG("debug_all_block_states");

    private final String key;

    @AsOf("3.3.0")
    LevelType(String key) {
        this.key = key;
    }

    @AsOf("3.3.0")
    public String getKey() {
        return key;
    }
}
