package dev.wyck.decode.worldgen.blockpredicates;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.blockpredicates.AllOfPredicate;
import dev.wyck.worldgen.blockpredicates.AnyOfPredicate;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.blockpredicates.HasSturdyFacePredicate;
import dev.wyck.worldgen.blockpredicates.InsideWorldBoundsPredicate;
import dev.wyck.worldgen.blockpredicates.MatchingBiomesPredicate;
import dev.wyck.worldgen.blockpredicates.MatchingBlockTagPredicate;
import dev.wyck.worldgen.blockpredicates.MatchingBlocksPredicate;
import dev.wyck.worldgen.blockpredicates.MatchingFluidsPredicate;
import dev.wyck.worldgen.blockpredicates.NotPredicate;
import dev.wyck.worldgen.blockpredicates.ReplaceablePredicate;
import dev.wyck.worldgen.blockpredicates.SolidPredicate;
import dev.wyck.worldgen.blockpredicates.TrueBlockPredicate;
import dev.wyck.worldgen.blockpredicates.WouldSurvivePredicate;
import dev.wyck.worldgen.material.FluidType;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class BlockPredicateDecoders extends DecoderRegistry<BlockPredicate, net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> {

    public BlockPredicateDecoders() {
        register("matching_blocks", predicate -> {
            HolderSet<Block> blocks = FastReflection.read(predicate, "blocks");
            return MatchingBlocksPredicate.of(offset(predicate), blocks.stream()
                .map(holder -> CraftBlockType.minecraftToBukkit(holder.value()))
                .toList());
        });
        register("matching_block_tag", predicate -> {
            TagKey<Block> tag = FastReflection.read(predicate, "tag");
            return MatchingBlockTagPredicate.of(offset(predicate), Decoders.key(tag.location()));
        });
        register("matching_fluids", predicate -> {
            HolderSet<Fluid> fluids = FastReflection.read(predicate, "fluids");
            return MatchingFluidsPredicate.of(offset(predicate), fluids.stream()
                .map(holder -> FluidType.TRANSLATOR.fromNms(holder.value()))
                .toList());
        });
        register("matching_biomes", predicate -> {
            HolderSet<Biome> biomes = FastReflection.read(predicate, "biomes");
            return MatchingBiomesPredicate.of(biomes.stream()
                .map(dev.wyck.biome.Biome::decode)
                .toList());
        });
        register("has_sturdy_face", predicate -> HasSturdyFacePredicate.of(
            offset(predicate),
            CraftBlock.notchToBlockFace(FastReflection.read(predicate, "direction"))
        ));
        register("solid", predicate -> SolidPredicate.of(offset(predicate)));
        register("replaceable", predicate -> ReplaceablePredicate.of(offset(predicate)));
        register("would_survive", predicate -> WouldSurvivePredicate.of(
            offset(predicate),
            CraftBlockData.createData(FastReflection.<BlockState>read(predicate, "state"))
        ));
        register("inside_world_bounds", predicate -> InsideWorldBoundsPredicate.of(offset(predicate)));
        register("any_of", predicate -> AnyOfPredicate.of(children(predicate)));
        register("all_of", predicate -> AllOfPredicate.of(children(predicate)));
        register("not", predicate -> NotPredicate.of(BlockPredicate.decode(FastReflection.read(predicate, "predicate"))));
        register("true", _ -> TrueBlockPredicate.INSTANCE);
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.BLOCK_PREDICATE_TYPE, minecraftObject.type());
    }

    private static BlockVector offset(Object predicate) {
        Vec3i offset = FastReflection.read(predicate, "offset");
        return new BlockVector(offset.getX(), offset.getY(), offset.getZ());
    }

    private static List<BlockPredicate> children(Object predicate) {
        List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> children =
            FastReflection.read(predicate, "predicates");
        return children.stream().map(BlockPredicate::decode).toList();
    }
}
