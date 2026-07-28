package dev.wyck.decode.worldgen.surface;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.worldgen.surface.rule.BandlandsRuleSource;
import dev.wyck.worldgen.surface.rule.BlockRuleSource;
import dev.wyck.worldgen.surface.rule.ConditionRuleSource;
import dev.wyck.worldgen.surface.rule.RuleSource;
import dev.wyck.worldgen.surface.rule.SequenceRuleSource;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class RuleSourceDecoders extends DecoderRegistry<RuleSource, net.minecraft.world.level.levelgen.SurfaceRules.RuleSource> {

    public RuleSourceDecoders() {
        register("bandlands", _ -> BandlandsRuleSource.INSTANCE);
        register("block", source -> BlockRuleSource.of(
            Decoders.blockData(FastReflection.<BlockState>read(source, "resultState")).getMaterial()
        ));
        register("sequence", source -> SequenceRuleSource.of(sequence(source)));
        register("condition", source -> ConditionRuleSource.of(
            ConditionSource.decode(FastReflection.read(source, "ifTrue")),
            RuleSource.decode(FastReflection.read(source, "thenRun"))
        ));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.SurfaceRules.RuleSource source) {
        return Decoders.registryKey(BuiltInRegistries.MATERIAL_RULE, source.codec());
    }

    private static List<RuleSource> sequence(Object source) {
        List<net.minecraft.world.level.levelgen.SurfaceRules.RuleSource> rules =
            FastReflection.read(source, "sequence");
        return rules.stream().map(RuleSource::decode).toList();
    }
}
