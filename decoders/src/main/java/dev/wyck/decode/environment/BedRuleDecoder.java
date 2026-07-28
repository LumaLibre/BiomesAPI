package dev.wyck.decode.environment;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.wyck.environment.BedRule;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.wrapper.decode.Decodable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryOps;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BedRuleDecoder implements Decodable<BedRule, net.minecraft.world.attribute.BedRule> {

    @Override
    public BedRule decode(net.minecraft.world.attribute.BedRule rule) {
        return BedRule.of(
            BedRule.Rule.TRANSLATOR.fromNms(rule.canSleep()),
            BedRule.Rule.TRANSLATOR.fromNms(rule.canSetSpawn()),
            rule.explodes(),
            rule.errorMessage().map(BedRuleDecoder::component).orElse(null)
        );
    }

    // bootstrap stuff, TODO: extract
    private static Component component(net.minecraft.network.chat.Component minecraftComponent) {
        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization().createSerializationContext(JsonOps.INSTANCE);
        JsonElement json = ComponentSerialization.CODEC.encodeStart(ops, minecraftComponent)
            .getOrThrow(message -> new IllegalStateException("Component conversion failed: " + message));
        return GsonComponentSerializer.gson().deserializeFromTree(json);
    }
}
