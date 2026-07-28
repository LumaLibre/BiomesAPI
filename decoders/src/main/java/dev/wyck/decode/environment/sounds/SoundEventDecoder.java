package dev.wyck.decode.environment.sounds;

import dev.wyck.decode.Decoders;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class SoundEventDecoder implements Decodable<SoundEvent, Object> {

    @Override
    public SoundEvent decode(Object minecraftObject) {
        net.minecraft.sounds.SoundEvent event = Decoders.value(minecraftObject);
        ResourceKey location = Decoders.key(event.location());
        return event.fixedRange()
            .map(range -> SoundEvent.fixedRange(location, range))
            .orElseGet(() -> SoundEvent.variableRange(location));
    }
}
