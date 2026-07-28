package dev.wyck.decode.environment.sounds;

import dev.wyck.environment.sounds.AmbientAdditionsSettings;
import dev.wyck.environment.sounds.AmbientMoodSettings;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AmbientSoundsDecoder implements Decodable<AmbientSounds, net.minecraft.world.attribute.AmbientSounds> {

    @Override
    public AmbientSounds decode(net.minecraft.world.attribute.AmbientSounds sounds) {
        return AmbientSounds.of(
            sounds.loop().map(SoundEvent::decode).orElse(null),
            sounds.mood().map(AmbientMoodSettings::decode).orElse(null),
            sounds.additions().stream().map(AmbientAdditionsSettings::decode).toList()
        );
    }
}
