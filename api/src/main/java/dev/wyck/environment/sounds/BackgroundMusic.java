package dev.wyck.environment.sounds;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Wraps a Minecraft BackgroundMusic.
 *
 * @version 2.4.1
 * @since 2.4.1
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.1")
public interface BackgroundMusic extends Wrapper {

    /**
     * Gets the default music.
     * @return the default music, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<Music> defaultMusic();

    /**
     * Gets the creative music.
     * @return the creative music, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<Music> creativeMusic();

    /**
     * Gets the underwater music.
     * @return the underwater music, if present
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    Optional<Music> underwaterMusic();

    /**
     * Creates a new builder with the same values as this background music.
     * @return a new builder with the same values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new background music record.
     * @param defaultMusic the default music
     * @param creativeMusic the creative music
     * @param underwaterMusic the underwater music
     * @return a new background music record
     * @since 2.4.1
     */
    @NullUnmarked
    @AsOf("2.4.1")
    static BackgroundMusic of(Music defaultMusic, Music creativeMusic, Music underwaterMusic) {
        record Holder() {
            static final ConstructWireProvider<BackgroundMusic> WIRE = ConstructWireProvider.construct("dev.wyck.environment.sounds.BackgroundMusicImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(defaultMusic), Optional.ofNullable(creativeMusic), Optional.ofNullable(underwaterMusic));
    }

    /**
     * Creates a new background music record builder.
     * @return a new background music record builder
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft background music into a wrapper.
     * @param minecraftBackgroundMusic the background music to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BackgroundMusic decode(Object minecraftBackgroundMusic) {
        record Holder() {
            static final Decoder<BackgroundMusic> DECODER = Decoder.create("dev.wyck.decode.environment.sounds.BackgroundMusicDecoder");
        }
        return Holder.DECODER.decode(minecraftBackgroundMusic);
    }

    /**
     * Builder for creating background music records.
     * @since 2.4.1
     * @version 2.4.1
     * @author Jsinco
     */
    @AsOf("2.4.1")
    final class Builder {
        private @Nullable Music defaultMusic;
        private @Nullable Music creativeMusic;
        private @Nullable Music underwaterMusic;

        public Builder() {}

        public Builder(BackgroundMusic music) {
            this.defaultMusic = music.defaultMusic().orElse(null);
            this.creativeMusic = music.creativeMusic().orElse(null);
            this.underwaterMusic = music.underwaterMusic().orElse(null);
        }

        /**
         * Sets the default music.
         * @param defaultMusic the default music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder defaultMusic(@Nullable Music defaultMusic) {
            this.defaultMusic = defaultMusic;
            return this;
        }

        /**
         * Sets the creative music.
         * @param creativeMusic the creative music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder creativeMusic(@Nullable Music creativeMusic) {
            this.creativeMusic = creativeMusic;
            return this;
        }

        /**
         * Sets the underwater music.
         * @param underwaterMusic the underwater music
         * @return this builder
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public Builder underwaterMusic(@Nullable Music underwaterMusic) {
            this.underwaterMusic = underwaterMusic;
            return this;
        }

        /**
         * Builds the background music.
         * @return a new background music record
         * @since 2.4.1
         */
        @AsOf("2.4.1")
        public BackgroundMusic build() {
            return of(defaultMusic, creativeMusic, underwaterMusic);
        }
    }
}
