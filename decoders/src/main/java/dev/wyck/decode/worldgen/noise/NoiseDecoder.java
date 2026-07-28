package dev.wyck.decode.worldgen.noise;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.climate.ClimatePoint;
import dev.wyck.worldgen.noise.Noise;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.worldgen.noise.NoiseSettings;
import dev.wyck.worldgen.noise.types.NoiseGeneratorSettings;
import dev.wyck.worldgen.surface.rule.RuleSource;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class NoiseDecoder implements Decodable<Noise, Object> {
    @Override
    @SuppressWarnings("deprecation") // disableMobGeneration
    public Noise decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return Noise.reference(Decoders.referenceKey(holder));
        }
        net.minecraft.world.level.levelgen.NoiseGeneratorSettings settings = Decoders.value(minecraftObject);
        return NoiseGeneratorSettings.of(
            null,
            NoiseSettings.decode(settings.noiseSettings()),
            Decoders.blockData(settings.defaultBlock()),
            Decoders.blockData(settings.defaultFluid()),
            NoiseRouter.decode(settings.noiseRouter()),
            RuleSource.decode(settings.surfaceRule()),
            settings.spawnTarget().stream().map(ClimatePoint::decode).toList(),
            settings.seaLevel(),
            settings.disableMobGeneration(),
            settings.isAquifersEnabled(),
            settings.oreVeinsEnabled(),
            settings.useLegacyRandomSource()
        );
    }
}
