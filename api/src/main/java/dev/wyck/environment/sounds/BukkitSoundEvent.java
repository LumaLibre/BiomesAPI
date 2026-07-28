package dev.wyck.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Sound;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A sound event that is specific to a {@link Sound}.
 *
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface BukkitSoundEvent extends SoundEvent {

    /**
     * The {@link Sound} of the sound event.
     * @return the sound of the sound event
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    Sound sound();

    /**
     * Creates a new BukkitSoundEvent from a {@link Sound}.
     * @param sound the sound to create a BukkitSoundEvent from
     * @return a new BukkitSoundEvent from the given sound
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static BukkitSoundEvent variableRange(Sound sound) {
        return create(sound, Optional.empty());
    }

    /**
     * Creates a new BukkitSoundEvent with a fixed range.
     * @param sound the sound to create a BukkitSoundEvent from
     * @param range the range of the sound event
     * @return a new BukkitSoundEvent with a fixed range
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static BukkitSoundEvent fixedRange(Sound sound, float range) {
        return create(sound, Optional.of(range));
    }

    private static BukkitSoundEvent create(Sound sound, Optional<Float> range) {
        record Holder() {
            static final ConstructWireProvider<BukkitSoundEvent> WIRE =
                ConstructWireProvider.create("dev.wyck.environment.sounds.BukkitSoundEventImpl");
        }
        return Holder.WIRE.construct(sound, range);
    }
}
