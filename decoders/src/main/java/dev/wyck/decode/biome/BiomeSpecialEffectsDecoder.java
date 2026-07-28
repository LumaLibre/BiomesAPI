package dev.wyck.decode.biome;

import dev.wyck.biome.BiomeSpecialEffects;
import dev.wyck.environment.GrassColorModifier;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BiomeSpecialEffectsDecoder implements Decodable<BiomeSpecialEffects, net.minecraft.world.level.biome.BiomeSpecialEffects> {

    @Override
    public BiomeSpecialEffects decode(net.minecraft.world.level.biome.BiomeSpecialEffects effects) {
        return BiomeSpecialEffects.of(
            effects.waterColor(),
            effects.foliageColorOverride().orElse(null),
            effects.dryFoliageColorOverride().orElse(null),
            effects.grassColorOverride().orElse(null),
            GrassColorModifier.TRANSLATOR.fromNms(effects.grassColorModifier())
        );
    }
}
