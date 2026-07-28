package dev.wyck.decode.environment.sounds;

import dev.wyck.environment.sounds.AmbientAdditionsSettings;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AmbientAdditionsSettingsDecoder implements Decodable<AmbientAdditionsSettings, net.minecraft.world.attribute.AmbientAdditionsSettings> {

    @Override
    public AmbientAdditionsSettings decode(net.minecraft.world.attribute.AmbientAdditionsSettings additions) {
        return AmbientAdditionsSettings.of(SoundEvent.decode(additions.soundEvent()), additions.tickChance());
    }
}
