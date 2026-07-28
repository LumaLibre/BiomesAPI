package dev.wyck.decode.environment.sounds;

import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.environment.sounds.Music;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BackgroundMusicDecoder implements Decodable<BackgroundMusic, net.minecraft.world.attribute.BackgroundMusic> {

    @Override
    public BackgroundMusic decode(net.minecraft.world.attribute.BackgroundMusic music) {
        return BackgroundMusic.builder()
            .defaultMusic(music.defaultMusic().map(Music::decode).orElse(null))
            .creativeMusic(music.creativeMusic().map(Music::decode).orElse(null))
            .underwaterMusic(music.underwaterMusic().map(Music::decode).orElse(null))
            .build();
    }
}
