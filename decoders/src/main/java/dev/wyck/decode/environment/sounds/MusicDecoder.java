package dev.wyck.decode.environment.sounds;

import dev.wyck.environment.sounds.Music;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class MusicDecoder implements Decodable<Music, net.minecraft.sounds.Music> {

    @Override
    public Music decode(net.minecraft.sounds.Music music) {
        return Music.of(SoundEvent.decode(music.sound()), music.minDelay(), music.maxDelay(), music.replaceCurrentMusic());
    }
}
