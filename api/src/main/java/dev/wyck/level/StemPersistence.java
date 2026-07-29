package dev.wyck.level;

import dev.wyck.annotations.AsOf;
import dev.wyck.level.dimension.LevelStem;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents the persistence mode of a level stem.
 *
 * @deprecated Use {@link LevelStem#register()} instead.
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
@Deprecated(forRemoval = true, since = "3.3.0")
@ApiStatus.ScheduledForRemoval(inVersion = "3.4.0")
public enum StemPersistence {
    /** The level stem lives only for the session and is injected straight into the world's dimensions. */
    TRANSIENT,
    /** The level stem is also registered into the dimension registry, so other registry callers can see it. */
    PERSISTENT
}
