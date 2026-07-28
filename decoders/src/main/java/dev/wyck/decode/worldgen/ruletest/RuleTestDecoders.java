package dev.wyck.decode.worldgen.ruletest;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.ruletest.AlwaysTrueTest;
import dev.wyck.worldgen.ruletest.BlockMatchTest;
import dev.wyck.worldgen.ruletest.BlockStateMatchTest;
import dev.wyck.worldgen.ruletest.RandomBlockMatchTest;
import dev.wyck.worldgen.ruletest.RandomBlockStateMatchTest;
import dev.wyck.worldgen.ruletest.RuleTest;
import dev.wyck.worldgen.ruletest.TagMatchTest;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class RuleTestDecoders extends DecoderRegistry<RuleTest, net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest> {

    public RuleTestDecoders() {
        register("always_true", _ -> AlwaysTrueTest.INSTANCE);
        register("block_match", test -> BlockMatchTest.of(CraftBlockType.minecraftToBukkit(
            FastReflection.read(test, "block")
        )));
        register("blockstate_match", test -> BlockStateMatchTest.of(Decoders.blockData(
            FastReflection.read(test, "blockState")
        )));
        register("tag_match", test -> {
            TagKey<Block> tag = FastReflection.read(test, "tag");
            return TagMatchTest.of(Decoders.key(tag.location()));
        });
        register("random_block_match", test -> RandomBlockMatchTest.of(
            CraftBlockType.minecraftToBukkit(FastReflection.read(test, "block")),
            FastReflection.read(test, "probability")
        ));
        register("random_blockstate_match", test -> RandomBlockStateMatchTest.of(
            Decoders.blockData(FastReflection.read(test, "blockState")),
            FastReflection.read(test, "probability")
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest minecraftObject) {
        RuleTestType<?> type = FastReflection.call(minecraftObject, "getType");
        return Decoders.registryKey(BuiltInRegistries.RULE_TEST, type);
    }
}
