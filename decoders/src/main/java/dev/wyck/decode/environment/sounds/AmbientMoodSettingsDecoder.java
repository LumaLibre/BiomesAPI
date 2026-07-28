package dev.wyck.decode.environment.sounds;

import dev.wyck.environment.sounds.AmbientMoodSettings;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class AmbientMoodSettingsDecoder implements Decodable<AmbientMoodSettings, net.minecraft.world.attribute.AmbientMoodSettings> {

    @Override
    public AmbientMoodSettings decode(net.minecraft.world.attribute.AmbientMoodSettings mood) {
        return AmbientMoodSettings.of(
            SoundEvent.decode(mood.soundEvent()),
            mood.tickDelay(),
            mood.blockSearchExtent(),
            mood.soundPositionOffset()
        );
    }
}
