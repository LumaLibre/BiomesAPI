package dev.wyck.test.bootstrap.decode;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.surface.condition.ConditionSource;
import dev.wyck.worldgen.surface.condition.PaperOptionallyFlatBedrockConditionSource;
import dev.wyck.worldgen.surface.rule.RuleSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class SurfaceRuleDecodeTest {

    @Test
    void everySurfaceConditionAndRuleTypeIsCovered() {
        var conditionDecoders = new dev.wyck.decode.worldgen.surface.ConditionSourceDecoders();
        var ruleDecoders = new dev.wyck.decode.worldgen.surface.RuleSourceDecoders();
        var conditions = BuiltInRegistries.MATERIAL_CONDITION.keySet().stream().toList();
        var rules = BuiltInRegistries.MATERIAL_RULE.keySet().stream().toList();
        conditions.forEach(key -> assertTrue(
            conditionDecoders.handles(Decoders.key(key)), () -> "Missing condition decoder for " + key
        ));
        rules.forEach(key -> assertTrue(
            ruleDecoders.handles(Decoders.key(key)), () -> "Missing rule decoder for " + key
        ));
        assertEquals(conditions.size(), conditionDecoders.handled().size());
        assertEquals(rules.size(), ruleDecoders.handled().size());
    }

    @Test
    void everyVanillaNoiseSettingsSurfaceRuleDecodesRecursively() {
        var settings = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS);
        settings.entrySet().forEach(entry -> assertNotNull(RuleSource.decode(entry.getValue().surfaceRule()),
            () -> "Failed to decode surface rules for " + entry.getKey().identifier()));
    }

    @Test
    void paperFlatBedrockConditionPreservesEveryValue() {
        var minecraftCondition = new io.papermc.paper.world.worldgen.OptionallyFlatBedrockConditionSource(
            Identifier.parse("minecraft:bedrock_roof"),
            VerticalAnchor.belowTop(5),
            VerticalAnchor.belowTop(0),
            true
        );

        PaperOptionallyFlatBedrockConditionSource decoded = assertInstanceOf(
            PaperOptionallyFlatBedrockConditionSource.class,
            ConditionSource.decode(minecraftCondition)
        );

        assertEquals(ResourceKey.minecraft("bedrock_roof"), decoded.randomName());
        assertEquals(5, assertInstanceOf(
            dev.wyck.worldgen.heightproviders.BelowTop.class,
            decoded.trueAtAndBelow()
        ).offset());
        assertEquals(0, assertInstanceOf(
            dev.wyck.worldgen.heightproviders.BelowTop.class,
            decoded.falseAtAndAbove()
        ).offset());
        assertTrue(decoded.roof());

        var reencoded = assertInstanceOf(
            io.papermc.paper.world.worldgen.OptionallyFlatBedrockConditionSource.class,
            decoded.asHandle()
        );
        assertEquals(minecraftCondition, reencoded);
    }
}
