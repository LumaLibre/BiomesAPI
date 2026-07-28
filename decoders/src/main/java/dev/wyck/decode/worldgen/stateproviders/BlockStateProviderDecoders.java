package dev.wyck.decode.worldgen.stateproviders;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.InclusiveRange;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.blockpredicates.BlockPredicate;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.stateproviders.DualNoiseProvider;
import dev.wyck.worldgen.stateproviders.NoiseProvider;
import dev.wyck.worldgen.stateproviders.NoiseThresholdProvider;
import dev.wyck.worldgen.stateproviders.RandomizedIntStateProvider;
import dev.wyck.worldgen.stateproviders.RotatedBlockProvider;
import dev.wyck.worldgen.stateproviders.RuleBasedStateProvider;
import dev.wyck.worldgen.stateproviders.SimpleStateProvider;
import dev.wyck.worldgen.stateproviders.WeightedStateProvider;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class BlockStateProviderDecoders extends DecoderRegistry<BlockStateProvider, net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider> {

    public BlockStateProviderDecoders() {
        register("simple_state_provider", provider -> SimpleStateProvider.of(Decoders.blockData(
            FastReflection.read(provider, "state")
        )));
        register("weighted_state_provider", provider -> {
            WeightedList<BlockState> entries = FastReflection.read(provider, "weightedList");
            return WeightedStateProvider.of(Decoders.weighted(entries, Decoders::blockData));
        });
        register("noise_threshold_provider", provider -> NoiseThresholdProvider.of(
            seed(provider), noise(provider), scale(provider),
            FastReflection.read(provider, "threshold"),
            FastReflection.read(provider, "highChance"),
            Decoders.blockData(FastReflection.read(provider, "defaultState")),
            states(provider, "lowStates"),
            states(provider, "highStates")
        ));
        register("noise_provider", provider -> NoiseProvider.of(
            seed(provider), noise(provider), scale(provider), states(provider, "states")
        ));
        register("dual_noise_provider", provider -> {
            net.minecraft.util.InclusiveRange<Integer> variety = FastReflection.read(provider, "variety");
            return DualNoiseProvider.of(
                seed(provider), noise(provider), scale(provider),
                InclusiveRange.of(variety.minInclusive(), variety.maxInclusive()),
                NoiseParameters.decode(FastReflection.read(provider, "slowNoiseParameters")),
                FastReflection.read(provider, "slowScale"),
                states(provider, "states")
            );
        });
        register("rotated_block_provider", provider -> {
            Block block = FastReflection.read(provider, "block");
            return RotatedBlockProvider.of(CraftBlockType.minecraftToBukkit(block));
        });
        register("randomized_int_state_provider", provider -> RandomizedIntStateProvider.of(
            BlockStateProvider.decode(FastReflection.read(provider, "source")),
            FastReflection.read(provider, "propertyName"),
            IntProvider.decode(FastReflection.read(provider, "values"))
        ));
        register("rule_based_state_provider", provider -> {
            net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider fallback =
                FastReflection.read(provider, "fallback");
            List<net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider.Rule> rules =
                FastReflection.read(provider, "rules");
            return RuleBasedStateProvider.of(
                fallback == null ? null : BlockStateProvider.decode(fallback),
                rules.stream().map(rule -> RuleBasedStateProvider.rule(
                    BlockPredicate.decode(rule.ifTrue()),
                    BlockStateProvider.decode(rule.then())
                )).toList()
            );
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider minecraftObject) {
        BlockStateProviderType<?> type = FastReflection.call(minecraftObject, "type");
        return Decoders.registryKey(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, type);
    }

    private static long seed(Object provider) {
        return FastReflection.read(provider, "seed");
    }

    private static float scale(Object provider) {
        return FastReflection.read(provider, "scale");
    }

    private static NoiseParameters noise(Object provider) {
        return NoiseParameters.decode(FastReflection.read(provider, "parameters"));
    }

    private static List<org.bukkit.block.data.BlockData> states(Object provider, String field) {
        List<BlockState> states = FastReflection.read(provider, field);
        return states.stream().map(Decoders::blockData).toList();
    }
}
