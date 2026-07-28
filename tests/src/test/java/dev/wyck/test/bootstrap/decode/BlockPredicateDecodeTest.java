package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biomes;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class BlockPredicateDecodeTest {

    private static final Set<ResourceKey> UNWRAPPED = Set.of(ResourceKey.minecraft("unobstructed"));

    private static dev.wyck.decode.worldgen.blockpredicates.BlockPredicateDecoders decoders() {
        record Holder() {
            static final dev.wyck.decode.worldgen.blockpredicates.BlockPredicateDecoders INSTANCE =
                new dev.wyck.decode.worldgen.blockpredicates.BlockPredicateDecoders();
        }
        return Holder.INSTANCE;
    }

    @Test
    void everyRepresentableVanillaBlockPredicateTypeHasADecoder() {
        List<ResourceKey> missing = BuiltInRegistries.BLOCK_PREDICATE_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !UNWRAPPED.contains(key))
            .filter(key -> !decoders().handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no block predicate decoder is registered for: " + missing);
    }

    @Test
    void nothingListedAsUnwrappedActuallyHasADecoder() {
        List<ResourceKey> stale = UNWRAPPED.stream().filter(key -> decoders().handles(key)).toList();

        assertTrue(stale.isEmpty(), () -> "these types have a decoder and should leave the unwrapped list: " + stale);
    }

    @Test
    void nestedCombinationsDecodeThroughBlockPredicate() {
        BlockPredicate original = AllOfPredicate.of(List.of(
            AnyOfPredicate.of(List.of(
                MatchingBlocksPredicate.of(new BlockVector(2, -1, 3), List.of(Material.STONE, Material.DIRT)),
                TrueBlockPredicate.INSTANCE
            )),
            NotPredicate.of(ReplaceablePredicate.of(new BlockVector(0, 1, 0)))
        ));

        AllOfPredicate all = assertInstanceOf(AllOfPredicate.class, BlockPredicate.decode(original.asHandle()));
        AnyOfPredicate any = assertInstanceOf(AnyOfPredicate.class, all.predicates().getFirst());
        MatchingBlocksPredicate blocks = assertInstanceOf(MatchingBlocksPredicate.class, any.predicates().getFirst());

        assertEquals(new BlockVector(2, -1, 3), blocks.offset());
        assertEquals(List.of(Material.STONE, Material.DIRT), blocks.blocks());
        assertInstanceOf(TrueBlockPredicate.class, any.predicates().get(1));
        NotPredicate not = assertInstanceOf(NotPredicate.class, all.predicates().get(1));
        assertInstanceOf(ReplaceablePredicate.class, not.predicate());
    }

    @Test
    void positionalAndStatePredicatesKeepTheirFields() {
        MatchingBlockTagPredicate tag = assertInstanceOf(MatchingBlockTagPredicate.class,
            BlockPredicate.decode(MatchingBlockTagPredicate.of(
                new BlockVector(-2, 4, 1), ResourceKey.minecraft("logs")
            ).asHandle()));
        assertEquals(new BlockVector(-2, 4, 1), tag.offset());
        assertEquals(ResourceKey.minecraft("logs"), tag.tag());

        MatchingFluidsPredicate fluids = assertInstanceOf(MatchingFluidsPredicate.class,
            BlockPredicate.decode(MatchingFluidsPredicate.of(
                new BlockVector(1, 0, 0), List.of(FluidType.WATER, FluidType.LAVA)
            ).asHandle()));
        assertEquals(List.of(FluidType.WATER, FluidType.LAVA), fluids.fluids());

        HasSturdyFacePredicate sturdy = assertInstanceOf(HasSturdyFacePredicate.class,
            BlockPredicate.decode(HasSturdyFacePredicate.of(new BlockVector(0, -1, 0), BlockFace.UP).asHandle()));
        assertEquals(BlockFace.UP, sturdy.direction());

        WouldSurvivePredicate survive = assertInstanceOf(WouldSurvivePredicate.class,
            BlockPredicate.decode(WouldSurvivePredicate.of(
                new BlockVector(0, 1, 0), Material.OAK_SAPLING
            ).asHandle()));
        assertEquals(Material.OAK_SAPLING, survive.state().getMaterial());
    }

    @Test
    @SuppressWarnings("deprecation")
    void simplePredicatesDecode() {
        assertInstanceOf(SolidPredicate.class, BlockPredicate.decode(SolidPredicate.of().asHandle()));
        assertInstanceOf(InsideWorldBoundsPredicate.class,
            BlockPredicate.decode(InsideWorldBoundsPredicate.of(new BlockVector(0, 8, 0)).asHandle()));
        assertInstanceOf(TrueBlockPredicate.class,
            BlockPredicate.decode(TrueBlockPredicate.INSTANCE.asHandle()));
    }

    @Test
    void biomePredicatesStackThroughBiomeDecoding() {
        Holder.Reference<net.minecraft.world.level.biome.Biome> plains =
            BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME)
                .get(Biomes.PLAINS)
                .orElseThrow();
        var minecraft = new net.minecraft.world.level.levelgen.blockpredicates.MatchingBiomesPredicate(
            HolderSet.direct(plains)
        );

        MatchingBiomesPredicate decoded = assertInstanceOf(MatchingBiomesPredicate.class,
            BlockPredicate.decode(minecraft));

        assertEquals(List.of(ResourceKey.minecraft("plains")),
            decoded.biomes().stream().map(dev.wyck.biome.Biome::resourceKey).toList());
    }
}
