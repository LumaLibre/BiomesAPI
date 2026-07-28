package dev.wyck.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Sound;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Wraps a Minecraft AmbientSounds.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface AmbientSounds extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<AmbientSounds> WIRE = ConstructWireProvider.construct("dev.wyck.environment.sounds.AmbientSoundsImpl");

    @ApiStatus.Internal
    Decoder<AmbientSounds> DECODER = Decoder.create("dev.wyck.decode.environment.sounds.AmbientSoundsDecoder");

    AmbientSounds EMPTY = AmbientSounds.of(null, null, Collections.emptyList());

    /**
     * Gets the loop sound event.
     * @return the loop sound event, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<SoundEvent> loop();

    /**
     * Gets the mood settings.
     * @return the mood settings, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<AmbientMoodSettings> mood();

    /**
     * Gets the ambient additions settings.
     * @return the ambient additions settings
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    List<AmbientAdditionsSettings> additions();

    /**
     * Creates a new ambient sounds record.
     * @param loop the loop sound event
     * @param mood the mood settings
     * @param additions the ambient additions settings
     * @return a new ambient sounds record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static AmbientSounds of(@Nullable SoundEvent loop, @Nullable AmbientMoodSettings mood, List<AmbientAdditionsSettings> additions) {
        return WIRE.construct(Optional.ofNullable(loop), Optional.ofNullable(mood), additions);
    }

    /**
     * Creates a new ambient sounds record builder.
     * @return a new ambient sounds record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft ambient sounds into a wrapper.
     * @param minecraftAmbientSounds the ambient sounds to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static AmbientSounds decode(Object minecraftAmbientSounds) {
        return DECODER.decode(minecraftAmbientSounds);
    }

    /**
     * A builder for ambient sounds.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent loop;
        private @Nullable AmbientMoodSettings mood;
        private final List<AmbientAdditionsSettings> additions = new ArrayList<>();

        /**
         * Sets the loop sound event.
         * @param loop the loop sound event
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder loop(@Nullable SoundEvent loop) {
            this.loop = loop;
            return this;
        }

        /**
         * Sets the mood settings.
         * @param mood the mood settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder mood(@Nullable AmbientMoodSettings mood) {
            this.mood = mood;
            return this;
        }

        /**
         * Adds an ambient additions settings.
         * @param additions the ambient additions settings
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder addition(AmbientAdditionsSettings... additions) {
            Collections.addAll(this.additions, additions);
            return this;
        }

        // Friendly

        /**
         * Sets the loop sound event.
         * @param bukkitSound the loop sound event
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder loop(Sound bukkitSound) {
            return loop(BukkitSoundEvent.variableRange(bukkitSound));
        }

        /**
         * Sets the loop sound event.
         * @param bukkitSound the loop sound event
         * @param range the range of the sound
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder loop(Sound bukkitSound, float range) {
            return loop(BukkitSoundEvent.fixedRange(bukkitSound, range));
        }

        /**
         * Sets the loop sound event.
         * @param soundKey the loop sound event
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder loop(String soundKey) {
            return loop(SoundEvent.variableRange(soundKey));
        }

        /**
         * Sets the loop sound event.
         * @param soundKey the loop sound event
         * @param range the range of the sound
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder loop(String soundKey, float range) {
            return loop(SoundEvent.fixedRange(soundKey, range));
        }

        /**
         * Builds the ambient sounds.
         * @return the ambient sounds
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public AmbientSounds build() {
            return of(loop, mood, additions);
        }
    }
}
