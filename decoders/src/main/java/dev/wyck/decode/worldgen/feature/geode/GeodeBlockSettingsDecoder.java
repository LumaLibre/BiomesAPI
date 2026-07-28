package dev.wyck.decode.worldgen.feature.geode;

import dev.wyck.decode.Decoders;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.feature.configurations.geode.GeodeBlockSettings;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.decode.Decodable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class GeodeBlockSettingsDecoder implements Decodable<GeodeBlockSettings, net.minecraft.world.level.levelgen.GeodeBlockSettings> {
    @Override
    public GeodeBlockSettings decode(net.minecraft.world.level.levelgen.GeodeBlockSettings settings) {
        return GeodeBlockSettings.of(
            BlockStateProvider.decode(settings.fillingProvider()),
            BlockStateProvider.decode(settings.innerLayerProvider()),
            BlockStateProvider.decode(settings.alternateInnerLayerProvider()),
            BlockStateProvider.decode(settings.middleLayerProvider()),
            BlockStateProvider.decode(settings.outerLayerProvider()),
            settings.innerPlacements().stream().map(Decoders::blockData).toList(),
            TagSet.decodeBlocks(settings.cannotReplace()),
            TagSet.decodeBlocks(settings.invalidBlocks())
        );
    }
}
