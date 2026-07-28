package dev.wyck.decode.worldgen.feature.geode;

import dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettings;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class GeodeCrackSettingsDecoder implements Decodable<GeodeCrackSettings, net.minecraft.world.level.levelgen.GeodeCrackSettings> {
    @Override
    public GeodeCrackSettings decode(net.minecraft.world.level.levelgen.GeodeCrackSettings settings) {
        return GeodeCrackSettings.of(settings.generateCrackChance, settings.baseCrackSize, settings.crackPointOffset);
    }
}
