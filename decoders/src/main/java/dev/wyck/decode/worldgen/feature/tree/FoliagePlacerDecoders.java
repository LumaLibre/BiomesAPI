package dev.wyck.decode.worldgen.feature.tree;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.feature.foliageplacers.AcaciaFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.BlobFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.BushFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.CherryFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.DarkOakFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.FancyFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.MegaPineFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.PineFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.SpruceFoliagePlacer;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FoliagePlacerDecoders extends DecoderRegistry<FoliagePlacer, net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer> {

    public FoliagePlacerDecoders() {
        register("blob_foliage_placer", placer -> BlobFoliagePlacer.of(radius(placer), offset(placer), height(placer)));
        register("spruce_foliage_placer", placer -> SpruceFoliagePlacer.of(
            radius(placer), offset(placer), provider(placer, "trunkHeight")
        ));
        register("pine_foliage_placer", placer -> PineFoliagePlacer.of(
            radius(placer), offset(placer), provider(placer, "height")
        ));
        register("acacia_foliage_placer", placer -> AcaciaFoliagePlacer.of(radius(placer), offset(placer)));
        register("bush_foliage_placer", placer -> BushFoliagePlacer.of(radius(placer), offset(placer), height(placer)));
        register("fancy_foliage_placer", placer -> FancyFoliagePlacer.of(radius(placer), offset(placer), height(placer)));
        register("jungle_foliage_placer", placer -> MegaJungleFoliagePlacer.of(radius(placer), offset(placer), height(placer)));
        register("mega_pine_foliage_placer", placer -> MegaPineFoliagePlacer.of(
            radius(placer), offset(placer), provider(placer, "crownHeight")
        ));
        register("dark_oak_foliage_placer", placer -> DarkOakFoliagePlacer.of(radius(placer), offset(placer)));
        register("random_spread_foliage_placer", placer -> RandomSpreadFoliagePlacer.of(
            radius(placer), offset(placer), provider(placer, "foliageHeight"),
            FastReflection.read(placer, "leafPlacementAttempts")
        ));
        register("cherry_foliage_placer", placer -> CherryFoliagePlacer.of(
            radius(placer), offset(placer), provider(placer, "height"),
            FastReflection.read(placer, "wideBottomLayerHoleChance"),
            FastReflection.read(placer, "cornerHoleChance"),
            FastReflection.read(placer, "hangingLeavesChance"),
            FastReflection.read(placer, "hangingLeavesExtensionChance")
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer minecraftObject) {
        FoliagePlacerType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.FOLIAGE_PLACER_TYPE, type);
    }

    private static IntProvider radius(Object placer) { return provider(placer, "radius"); }
    private static IntProvider offset(Object placer) { return provider(placer, "offset"); }
    private static int height(Object placer) { return FastReflection.read(placer, "height"); }
    private static IntProvider provider(Object placer, String field) {
        return IntProvider.decode(FastReflection.read(placer, field));
    }
}
