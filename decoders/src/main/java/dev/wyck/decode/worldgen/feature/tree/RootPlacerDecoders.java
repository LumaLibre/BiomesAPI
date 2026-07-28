package dev.wyck.decode.worldgen.feature.tree;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.feature.rootplacers.AboveRootPlacement;
import dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacement;
import dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class RootPlacerDecoders extends DecoderRegistry<RootPlacer, net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer> {

    public RootPlacerDecoders() {
        register("mangrove_root_placer", placer -> {
            net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement placement =
                FastReflection.read(placer, "mangroveRootPlacement");
            Optional<net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement> above =
                FastReflection.read(placer, "aboveRootPlacement");
            return MangroveRootPlacer.of(
                IntProvider.decode(FastReflection.read(placer, "trunkOffsetY")),
                BlockStateProvider.decode(FastReflection.read(placer, "rootProvider")),
                above.map(value -> AboveRootPlacement.of(
                    BlockStateProvider.decode(value.aboveRootProvider()),
                    value.aboveRootPlacementChance()
                )).orElse(null),
                MangroveRootPlacement.of(
                    Decoders.materials(placement.canGrowThrough()),
                    Decoders.materials(placement.muddyRootsIn()),
                    BlockStateProvider.decode(placement.muddyRootsProvider()),
                    placement.maxRootWidth(), placement.maxRootLength(), placement.randomSkewChance()
                )
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer minecraftObject) {
        RootPlacerType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.ROOT_PLACER_TYPE, type);
    }
}
