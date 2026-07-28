package dev.wyck.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Sound;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * Wraps a Minecraft SoundEvent.
 *
 * @version 3.1.0
 * @since 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface SoundEvent extends Wrapper, Keyed {

    /**
     * Gets the location of the sound event.
     * @return the location of the sound event
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    ResourceKey location();

    /**
     * Gets the range of the sound event.
     * @return the range of the sound event, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<Float> fixedRange();

    /**
     * Creates a new sound event with a variable range.
     * @param location the location of the sound event
     * @return a new sound event with a variable range
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static SoundEvent variableRange(ResourceKey location) {
        return create(location, Optional.empty());
    }

    /**
     * Creates a new sound event with a fixed range.
     * @param location the location of the sound event
     * @param range the range of the sound event
     * @return a new sound event with a fixed range
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static SoundEvent fixedRange(ResourceKey location, float range) {
        return create(location, Optional.of(range));
    }

    /**
     * Creates a new sound event with a variable range.
     * @param key the key of the sound event
     * @return a new sound event with a variable range
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static SoundEvent variableRange(String key) {
        return variableRange(ResourceKey.minecraft(key));
    }

    /**
     * Creates a new sound event with a fixed range.
     * @param key the key of the sound event
     * @param range the range of the sound event
     * @return a new sound event with a fixed range
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static SoundEvent fixedRange(String key, float range) {
        return fixedRange(ResourceKey.minecraft(key), range);
    }

    /**
     * Creates a new BukkitSoundEvent with a variable range.
     * @param sound the sound event to create a BukkitSoundEvent from
     * @return a new BukkitSoundEvent with a variable range
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static BukkitSoundEvent variableRange(Sound sound) {
        return BukkitSoundEvent.variableRange(sound);
    }

    /**
     * Creates a new BukkitSoundEvent with a fixed range.
     * @param sound the sound event to create a BukkitSoundEvent from
     * @param range the range of the sound event
     * @return a new BukkitSoundEvent with a fixed range
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static BukkitSoundEvent fixedRange(Sound sound, float range) {
        return BukkitSoundEvent.fixedRange(sound, range);
    }

    private static SoundEvent create(ResourceKey location, Optional<Float> range) {
        record Holder() {
            static final ConstructWireProvider<SoundEvent> WIRE =
                WireProvider.construct("dev.wyck.environment.sounds.SoundEventImpl");
        }
        return Holder.WIRE.construct(location, range);
    }

    /**
     * Reads a Minecraft sound event, or a holder of one, into a wrapper.
     * @param minecraftSoundEvent the sound event to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static SoundEvent decode(Object minecraftSoundEvent) {
        record Holder() {
            static final Decoder<SoundEvent> DECODER = Decoder.create("dev.wyck.decode.environment.sounds.SoundEventDecoder");
        }
        return Holder.DECODER.decode(minecraftSoundEvent);
    }
}
