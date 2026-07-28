package dev.wyck.decode.worldgen.carver;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.WorldCarverType;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import net.minecraft.core.Holder;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class ConfiguredWorldCarverDecoder implements Decodable<ConfiguredWorldCarver, Object> {

    @Override
    public ConfiguredWorldCarver decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return ConfiguredWorldCarver.reference(Decoders.referenceKey(holder));
        }
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured = Decoders.value(minecraftObject);
        return ComposedCarver.of(
            WorldCarverType.TRANSLATOR.fromNms(configured.worldCarver()),
            CarverConfiguration.decode(configured)
        );
    }
}
