package dev.wyck.environment.sounds;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Sound;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wrapper for AmbientAdditionsSettings.
 *
 * @since 2.4.1
 * @version 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface AmbientAdditionsSettings extends Wrapper {

    /**
     * The sound event to play.
     * @return the sound event to play
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    SoundEvent soundEvent();

    /**
     * The chance of the sound event to play per tick.
     * @return the chance of the sound event to play per tick
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    double tickChance();

    /**
     * Creates a new builder with the same values as these ambient additions settings.
     * @return a new builder with the same values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new ambient additions settings record.
     * @param soundEvent the sound event to play
     * @param tickChance the chance of the sound event to play per tick
     * @return a new ambient additions settings record
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static AmbientAdditionsSettings of(SoundEvent soundEvent, double tickChance) {
        record Holder() {
            static final ConstructWireProvider<AmbientAdditionsSettings> WIRE = WireProvider.construct("dev.wyck.environment.sounds.AmbientAdditionsSettingsImpl");
        }
        return Holder.WIRE.construct(soundEvent, tickChance);
    }

    /**
     * Creates a new ambient additions settings record.
     * @param bukkitSound the sound event to play
     * @param tickChance the chance of the sound event to play per tick
     * @return a new ambient additions settings record
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static AmbientAdditionsSettings of(Sound bukkitSound, double tickChance) {
        return of(BukkitSoundEvent.variableRange(bukkitSound), tickChance);
    }

    /**
     * Creates a new ambient additions settings record builder.
     * @return a new ambient additions settings record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft ambient additions settings into a wrapper.
     * @param minecraftAmbientAdditionsSettings the ambient additions settings to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static AmbientAdditionsSettings decode(Object minecraftAmbientAdditionsSettings) {
        record Holder() {
            static final Decoder<AmbientAdditionsSettings> DECODER = Decoder.create("dev.wyck.decode.environment.sounds.AmbientAdditionsSettingsDecoder");
        }
        return Holder.DECODER.decode(minecraftAmbientAdditionsSettings);
    }

    /**
     * Builder for ambient additions settings records.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable SoundEvent soundEvent;
        private double tickChance;

        public Builder() {}

        public Builder(AmbientAdditionsSettings settings) {
            this.soundEvent = settings.soundEvent();
            this.tickChance = settings.tickChance();
        }

        /**
         * Sets the sound event to play.
         * @param soundEvent the sound event to play
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder soundEvent(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
            return this;
        }

        /**
         * Sets the chance of the sound event to play per tick.
         * @param tickChance the chance of the sound event to play per tick
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder tickChance(double tickChance) {
            this.tickChance = tickChance;
            return this;
        }

        // Friendly

        /**
         * Sets the sound event to play.
         * @param bukkitSound the sound event to play
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder sound(Sound bukkitSound) {
            return soundEvent(BukkitSoundEvent.variableRange(bukkitSound));
        }

        /**
         * Sets the sound event to play.
         * @param bukkitSound the sound event to play
         * @param range the range of the sound
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(Sound bukkitSound, float range) {
            return soundEvent(BukkitSoundEvent.fixedRange(bukkitSound, range));
        }

        /**
         * Sets the sound event to play.
         * @param soundKey the sound event to play
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(String soundKey) {
            return soundEvent(SoundEvent.variableRange(soundKey));
        }

        /**
         * Sets the sound event to play.
         * @param soundKey the sound event to play
         * @param range the range of the sound
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder sound(String soundKey, float range) {
            return soundEvent(SoundEvent.fixedRange(soundKey, range));
        }

        /**
         * Builds the ambient additions settings record.
         * @return the ambient additions settings record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public AmbientAdditionsSettings build() {
            Preconditions.checkNotNull(soundEvent, "soundEvent must not be null");
            return of(soundEvent, tickChance);
        }
    }
}
