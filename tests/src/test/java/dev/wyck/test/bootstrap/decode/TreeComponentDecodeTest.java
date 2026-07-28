package dev.wyck.test.bootstrap.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.worldgen.feature.featuresize.FeatureSize;
import dev.wyck.worldgen.feature.featuresize.TwoLayersFeatureSize;
import dev.wyck.worldgen.feature.foliageplacers.BlobFoliagePlacer;
import dev.wyck.worldgen.feature.foliageplacers.FoliagePlacer;
import dev.wyck.worldgen.feature.rootplacers.AboveRootPlacement;
import dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacement;
import dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacer;
import dev.wyck.worldgen.feature.rootplacers.RootPlacer;
import dev.wyck.worldgen.feature.treedecorators.AttachedToLogsDecorator;
import dev.wyck.worldgen.feature.treedecorators.TreeDecorator;
import dev.wyck.worldgen.feature.trunkplacers.CherryTrunkPlacer;
import dev.wyck.worldgen.feature.trunkplacers.TrunkPlacer;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class TreeComponentDecodeTest {

    @Test
    void everyTreeComponentRegistryIsCovered() {
        assertCovered(BuiltInRegistries.FEATURE_SIZE_TYPE,
            new dev.wyck.decode.worldgen.feature.tree.FeatureSizeDecoders().handled());
        assertCovered(BuiltInRegistries.TRUNK_PLACER_TYPE,
            new dev.wyck.decode.worldgen.feature.tree.TrunkPlacerDecoders().handled());
        assertCovered(BuiltInRegistries.FOLIAGE_PLACER_TYPE,
            new dev.wyck.decode.worldgen.feature.tree.FoliagePlacerDecoders().handled());
        assertCovered(BuiltInRegistries.ROOT_PLACER_TYPE,
            new dev.wyck.decode.worldgen.feature.tree.RootPlacerDecoders().handled());
        assertCovered(BuiltInRegistries.TREE_DECORATOR_TYPE,
            new dev.wyck.decode.worldgen.feature.tree.TreeDecoratorDecoders().handled());
    }

    @Test
    void representativeComponentsPreserveNestedProvidersAndFields() {
        TwoLayersFeatureSize size = assertInstanceOf(TwoLayersFeatureSize.class,
            FeatureSize.decode(TwoLayersFeatureSize.of(4, 2, 1, 3).asHandle()));
        assertEquals(4, size.minClippedHeight().orElseThrow());
        assertEquals(3, size.upperSize());

        CherryTrunkPlacer trunk = assertInstanceOf(CherryTrunkPlacer.class,
            TrunkPlacer.decode(CherryTrunkPlacer.of(
                5, 2, 1, IntProvider.uniform(1, 3), IntProvider.uniform(2, 4),
                IntProvider.uniform(-5, -2), IntProvider.uniform(-1, 2)
            ).asHandle()));
        assertEquals(5, trunk.baseHeight());
        assertEquals(-5, trunk.branchStartOffsetFromTop().minInclusive());

        BlobFoliagePlacer foliage = assertInstanceOf(BlobFoliagePlacer.class,
            FoliagePlacer.decode(BlobFoliagePlacer.of(
                IntProvider.uniform(1, 3), IntProvider.constant(0), 4
            ).asHandle()));
        assertEquals(3, foliage.radius().maxInclusive());
        assertEquals(4, foliage.height());

        MangroveRootPlacer roots = assertInstanceOf(MangroveRootPlacer.class,
            RootPlacer.decode(MangroveRootPlacer.of(
                IntProvider.uniform(1, 3), BlockStateProvider.simple(Material.MANGROVE_ROOTS),
                AboveRootPlacement.of(BlockStateProvider.simple(Material.MOSS_CARPET), 0.25f),
                MangroveRootPlacement.of(
                    Set.of(Material.MUD), Set.of(Material.CLAY),
                    BlockStateProvider.simple(Material.MUDDY_MANGROVE_ROOTS), 4, 8, 0.35f
                )
            ).asHandle()));
        assertEquals(0.35f, roots.mangroveRootPlacement().randomSkewChance());
        assertTrue(roots.aboveRootPlacement().isPresent());

        AttachedToLogsDecorator decorator = assertInstanceOf(AttachedToLogsDecorator.class,
            TreeDecorator.decode(AttachedToLogsDecorator.of(
                0.4f, BlockStateProvider.simple(Material.VINE), List.of(BlockFace.NORTH, BlockFace.UP)
            ).asHandle()));
        assertEquals(List.of(BlockFace.NORTH, BlockFace.UP), decorator.directions());
        assertEquals(0.4f, decorator.probability());
    }

    private static void assertCovered(Registry<?> registry, Set<ResourceKey> handled) {
        List<ResourceKey> missing = registry.keySet().stream()
            .map(key -> ResourceKey.of(key.getNamespace(), key.getPath()))
            .filter(key -> !handled.contains(key))
            .toList();
        assertTrue(missing.isEmpty(), () -> registry.key().identifier() + " missing decoders for " + missing);
    }
}
