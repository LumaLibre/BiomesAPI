package dev.wyck.decode.worldgen.feature.tree;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.feature.trunkplacers.BendingTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.CherryTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.DarkOakTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.FancyTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.ForkingTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.GiantTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.StraightTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.worldgen.valueproviders.UniformInt;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class TrunkPlacerDecoders extends DecoderRegistry<TrunkPlacer, net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer> {

    public TrunkPlacerDecoders() {
        register("straight_trunk_placer", placer -> StraightTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("forking_trunk_placer", placer -> ForkingTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("giant_trunk_placer", placer -> GiantTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("mega_jungle_trunk_placer", placer -> MegaJungleTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("dark_oak_trunk_placer", placer -> DarkOakTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("fancy_trunk_placer", placer -> FancyTrunkPlacer.of(base(placer), randA(placer), randB(placer)));
        register("bending_trunk_placer", placer -> BendingTrunkPlacer.of(
            base(placer), randA(placer), randB(placer), field(placer, "minHeightForLeaves"),
            IntProvider.decode(FastReflection.read(placer, "bendLength"))
        ));
        register("upwards_branching_trunk_placer", placer -> UpwardsBranchingTrunkPlacer.of(
            base(placer), randA(placer), randB(placer),
            IntProvider.decode(FastReflection.read(placer, "extraBranchSteps")),
            FastReflection.read(placer, "placeBranchPerLogProbability"),
            IntProvider.decode(FastReflection.read(placer, "extraBranchLength")),
            Decoders.materials(FastReflection.read(placer, "canGrowThrough"))
        ));
        register("cherry_trunk_placer", placer -> CherryTrunkPlacer.of(
            base(placer), randA(placer), randB(placer),
            IntProvider.decode(FastReflection.read(placer, "branchCount")),
            IntProvider.decode(FastReflection.read(placer, "branchHorizontalLength")),
            (UniformInt) IntProvider.decode(FastReflection.read(placer, "branchStartOffsetFromTop")),
            IntProvider.decode(FastReflection.read(placer, "branchEndOffsetFromTop"))
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer minecraftObject) {
        TrunkPlacerType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.TRUNK_PLACER_TYPE, type);
    }

    private static int base(Object placer) {
        return field(placer, "baseHeight");
    }

    private static int randA(Object placer) {
        return field(placer, "heightRandA");
    }

    private static int randB(Object placer) {
        return field(placer, "heightRandB");
    }

    private static int field(Object placer, String name) {
        return FastReflection.read(placer, name);
    }
}
