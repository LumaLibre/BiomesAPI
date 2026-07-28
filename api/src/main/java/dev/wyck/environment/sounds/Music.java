package dev.wyck.environment.sounds;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Sound;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps the Music environment attribute's music record.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface Music extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<Music> WIRE = ConstructWireProvider.create("dev.wyck.environment.sounds.MusicImpl");

    @ApiStatus.Internal
    Decoder<Music> DECODER = Decoder.create("dev.wyck.decode.environment.sounds.MusicDecoder");

    /**
     * Gets the sound of the music.
     * @return the sound of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    SoundEvent sound();

    /**
     * Gets the minimum delay of the music.
     * @return the minimum delay of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int minDelay();

    /**
     * Gets the maximum delay of the music.
     * @return the maximum delay of the music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    int maxDelay();

    /**
     * Gets whether the music should replace the current music.
     * @return whether the music should replace the current music
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    boolean replaceCurrentMusic();

    /**
     * Creates a new music record.
     * @param sound the sound of the music
     * @param minDelay the minimum delay of the music
     * @param maxDelay the maximum delay of the music
     * @param replaceCurrentMusic whether the music should replace the current music
     * @return a new music record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Music of(SoundEvent sound, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        return WIRE.construct(sound, minDelay, maxDelay, replaceCurrentMusic);
    }

    /**
     * Creates a new music record.
     * @param bukkitSound the sound of the music
     * @param minDelay the minimum delay of the music
     * @param maxDelay the maximum delay of the music
     * @param replaceCurrentMusic whether the music should replace the current music
     * @return a new music record
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static Music of(Sound bukkitSound, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        return of(BukkitSoundEvent.variableRange(bukkitSound), minDelay, maxDelay, replaceCurrentMusic);
    }

    /**
     * Creates a new music record.
     * @param soundKey the sound of the music
     * @param minDelay the minimum delay of the music
     * @param maxDelay the maximum delay of the music
     * @param replaceCurrentMusic whether the music should replace the current music
     * @return a new music record
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static Music of(String soundKey, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        return of(SoundEvent.variableRange(soundKey), minDelay, maxDelay, replaceCurrentMusic);
    }

    /**
     * Creates a new music record builder.
     * @return a new music record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft music into a wrapper.
     * @param minecraftMusic the music to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Music decode(Object minecraftMusic) {
        return DECODER.decode(minecraftMusic);
    }


    /**
     * Builder for creating music records.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent sound;
        private int minDelay;
        private int maxDelay;
        private boolean replaceCurrentMusic;

        /**
         * Sets the sound of the music.
         * @param sound the sound of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder sound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Sets the minimum delay of the music.
         * @param minDelay the minimum delay of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder minDelay(int minDelay) {
            this.minDelay = minDelay;
            return this;
        }

        /**
         * Sets the maximum delay of the music.
         * @param maxDelay the maximum delay of the music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder maxDelay(int maxDelay) {
            this.maxDelay = maxDelay;
            return this;
        }

        /**
         * Sets whether the music should replace the current music.
         * @param replaceCurrentMusic whether the music should replace the current music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder replaceCurrentMusic(boolean replaceCurrentMusic) {
            this.replaceCurrentMusic = replaceCurrentMusic;
            return this;
        }

        // Friendly

        /**
         * Sets the sound of the music.
         * @param bukkitSound the sound of the music
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder sound(Sound bukkitSound) {
            return sound(BukkitSoundEvent.variableRange(bukkitSound));
        }

        /**
         * Sets the sound of the music.
         * @param bukkitSound the sound of the music
         * @param range the range of the sound
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(Sound bukkitSound, float range) {
            return sound(BukkitSoundEvent.fixedRange(bukkitSound, range));
        }

        /**
         * Sets the sound of the music.
         * @param soundKey the sound of the music
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(String soundKey) {
            return sound(SoundEvent.variableRange(soundKey));
        }

        /**
         * Sets the sound of the music.
         * @param soundKey the sound of the music
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(String soundKey, float range) {
            return sound(SoundEvent.fixedRange(soundKey, range));
        }

        /**
         * Builds the music record.
         * @return the music record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Music build() {
            Preconditions.checkNotNull(sound, "sound cannot be null");
            return of(sound, minDelay, maxDelay, replaceCurrentMusic);
        }
    }
}
