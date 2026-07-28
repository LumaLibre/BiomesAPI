package dev.wyck.decode.worldgen.feature.tree;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.feature.treedecorators.AlterGroundDecorator;
import dev.wyck.worldgen.feature.treedecorators.AttachedToLeavesDecorator;
import dev.wyck.worldgen.feature.treedecorators.AttachedToLogsDecorator;
import dev.wyck.worldgen.feature.treedecorators.BeehiveDecorator;
import dev.wyck.worldgen.feature.treedecorators.CocoaDecorator;
import dev.wyck.worldgen.feature.treedecorators.CreakingHeartDecorator;
import dev.wyck.worldgen.feature.treedecorators.LeaveVineDecorator;
import dev.wyck.worldgen.feature.treedecorators.PaleMossDecorator;
import dev.wyck.worldgen.feature.treedecorators.PlaceOnGroundDecorator;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.treedecorators.TrunkVineDecorator;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class TreeDecoratorDecoders extends DecoderRegistry<TreeDecorator, net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator> {

    public TreeDecoratorDecoders() {
        register("trunk_vine", _ -> TrunkVineDecorator.of());
        register("leave_vine", decorator -> LeaveVineDecorator.of(probability(decorator)));
        register("pale_moss", decorator -> PaleMossDecorator.of(
            field(decorator, "leavesProbability"),
            field(decorator, "trunkProbability"),
            field(decorator, "groundProbability")
        ));
        register("creaking_heart", decorator -> CreakingHeartDecorator.of(probability(decorator)));
        register("cocoa", decorator -> CocoaDecorator.of(probability(decorator)));
        register("beehive", decorator -> BeehiveDecorator.of(probability(decorator)));
        register("alter_ground", decorator -> AlterGroundDecorator.of(
            BlockStateProvider.decode(
            FastReflection.read(decorator, "provider"))
        ));
        register("attached_to_leaves", decorator -> AttachedToLeavesDecorator.of(
            probability(decorator),
            FastReflection.read(decorator, "exclusionRadiusXZ"),
            FastReflection.read(decorator, "exclusionRadiusY"),
            BlockStateProvider.decode(FastReflection.read(decorator, "blockProvider")),
            FastReflection.read(decorator, "requiredEmptyBlocks"),
            directions(decorator)
        ));
        register("place_on_ground", decorator -> PlaceOnGroundDecorator.of(
            FastReflection.read(decorator, "tries"),
            FastReflection.read(decorator, "radius"),
            FastReflection.read(decorator, "height"),
            BlockStateProvider.decode(FastReflection.read(decorator, "blockStateProvider"))
        ));
        register("attached_to_logs", decorator -> AttachedToLogsDecorator.of(
            probability(decorator),
            BlockStateProvider.decode(FastReflection.read(decorator, "blockProvider")),
            directions(decorator)
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator minecraftObject) {
        TreeDecoratorType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.TREE_DECORATOR_TYPE, type);
    }

    private static float probability(Object decorator) { return field(decorator, "probability"); }
    private static float field(Object decorator, String name) { return FastReflection.read(decorator, name); }

    private static List<BlockFace> directions(Object decorator) {
        List<Direction> directions = FastReflection.read(decorator, "directions");
        return directions.stream().map(CraftBlock::notchToBlockFace).toList();
    }
}
