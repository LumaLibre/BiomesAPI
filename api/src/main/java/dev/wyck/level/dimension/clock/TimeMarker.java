package dev.wyck.level.dimension.clock;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a time marker in a world clock.
 *
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface TimeMarker extends Wrapper, Keyed, Registerable<TimeMarker> {

    ResourceKey DAY = ResourceKey.minecraft("day");
    ResourceKey NOON = ResourceKey.minecraft("noon");
    ResourceKey NIGHT = ResourceKey.minecraft("night");
    ResourceKey MIDNIGHT = ResourceKey.minecraft("midnight");
    ResourceKey WAKE_UP_FROM_SLEEP = ResourceKey.minecraft("wake_up_from_sleep");
    ResourceKey ROLL_VILLAGE_SIEGE = ResourceKey.minecraft("roll_village_siege");

    /**
     * The key of this time marker.
     * @return the world clock key
     * @since 3.2.0
     */
    @Override
    @AsOf("3.2.0")
    ResourceKey key();

    /**
     * The number of ticks this time marker represents.
     * @return the number of ticks this time marker represents
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    int ticks();

    /**
     * Whether this time marker should be shown in the /time command.
     * @return whether this time marker should be shown in the /time command
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    boolean showInCommands();

    /**
     * Creates a new builder with the same values as this time marker.
     * @return a new builder with the same values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new time marker with the given key and ticks.
     * @param key the key of the time marker
     * @param ticks the number of ticks the time marker represents
     * @return a new time marker with the given key and ticks
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static TimeMarker of(ResourceKey key, int ticks) {
        return of(key, ticks, false);
    }

    /**
     * Creates a new time marker with the given key, ticks, and showInCommands.
     * @param key the key of the time marker
     * @param ticks the number of ticks the time marker represents
     * @param showInCommands whether the time marker should be shown in the /time command
     * @return a new time marker with the given key, ticks, and showInCommands
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static TimeMarker of(ResourceKey key, int ticks, boolean showInCommands) {
        record Holder() {
            static final ConstructWireProvider<TimeMarker> WIRE = WireProvider.construct("dev.wyck.level.dimension.clock.TimeMarkerImpl");
        }
        return Holder.WIRE.construct(key, ticks, showInCommands);
    }

    /**
     * Builder for {@link TimeMarker}.
     * @since 3.2.0
     * @version 3.2.0
     * @author Jsinco
     */
    @AsOf("3.2.0")
    final class Builder {
        private @Nullable ResourceKey key;
        private int ticks;
        private boolean showInCommands;

        public Builder() {}

        public Builder(TimeMarker marker) {
            this.key = marker.key();
            this.ticks = marker.ticks();
            this.showInCommands = marker.showInCommands();
        }

        /**
         * Sets the key of the time marker.
         * @param key the key of the time marker
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder key(ResourceKey key) {
            this.key = key;
            return this;
        }

        /**
         * Sets the number of ticks the time marker represents.
         * @param ticks the number of ticks the time marker represents
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder ticks(int ticks) {
            this.ticks = ticks;
            return this;
        }

        /**
         * Sets whether the time marker should be shown in the /time command.
         * @param showInCommands whether the time marker should be shown in the /time command
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder showInCommands(boolean showInCommands) {
            this.showInCommands = showInCommands;
            return this;
        }

        /**
         * Builds the time marker.
         * @return the time marker
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public TimeMarker build() {
            Preconditions.checkNotNull(key, "key cannot be null");
            Preconditions.checkArgument(ticks >= 0, "ticks cannot be negative");
            return of(key, ticks, showInCommands);
        }
    }

}
