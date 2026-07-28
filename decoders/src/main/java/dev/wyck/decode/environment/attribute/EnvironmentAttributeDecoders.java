package dev.wyck.decode.environment.attribute;

import dev.wyck.decode.Decoders;
import dev.wyck.environment.Activity;
import dev.wyck.environment.BedRule;
import dev.wyck.environment.MoonPhase;
import dev.wyck.environment.TriState;
import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.particle.AmbientParticle;
import dev.wyck.environment.particle.ParticleCatalog;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@NullMarked
@ApiStatus.Internal
public final class EnvironmentAttributeDecoders extends DecoderRegistry<EnvironmentAttribute<?>, Object> {

    public EnvironmentAttributeDecoders() {
        identity("boolean");
        identity("float");
        identity("angle_degrees");
        identity("rgb_color");
        identity("argb_color");
        identity("integer");
        this.<TriState, net.minecraft.util.TriState>attribute("tri_state",
            TriState.TRANSLATOR::fromNms, value -> value.toNms(net.minecraft.util.TriState.class));
        this.<MoonPhase, net.minecraft.world.level.MoonPhase>attribute("moon_phase",
            MoonPhase.TRANSLATOR::fromNms, value -> value.toNms(net.minecraft.world.level.MoonPhase.class));
        this.attribute("activity", Activity.TRANSLATOR::fromNms, Activity::toNms);
        this.attribute("bed_rule", BedRule::decode, BedRule::toMinecraft);
        this.attribute("particle", ParticleOptions::decode, ParticleOptions::toMinecraft);
        this.<ParticleCatalog, List<?>>attribute("ambient_particles",
            particles -> new ParticleCatalog(particles.stream().map(AmbientParticle::decode).toList()),
            catalog -> catalog.particles().stream().map(AmbientParticle::toMinecraft).toList());
        this.attribute("background_music", BackgroundMusic::decode, BackgroundMusic::toMinecraft);
        this.attribute("ambient_sounds", AmbientSounds::decode, AmbientSounds::toMinecraft);
    }

    @Override
    protected ResourceKey discriminate(Object minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.ATTRIBUTE_TYPE, attribute(pair(minecraftObject)).type());
    }

    private void identity(String attributeType) {
        this.attribute(attributeType, Function.identity(), value -> value);
    }

    @SuppressWarnings("unchecked")
    private <V, U> void attribute(String attributeType, Function<U, V> reader, EnvironmentAttribute.Converter<V, U> converter) {
        register(attributeType, minecraftObject -> {
            Map.Entry<?, ?> pair = pair(minecraftObject);
            ResourceKey key = Decoders.registryKey(
                BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, attribute(pair));

            U value = (U) pair.getValue();
            return EnvironmentAttribute.of(key, converter, reader.apply(value));
        });
    }

    private static net.minecraft.world.attribute.EnvironmentAttribute<?> attribute(Map.Entry<?, ?> pair) {
        if (!(pair.getKey() instanceof net.minecraft.world.attribute.EnvironmentAttribute<?> attribute)) {
            throw new IllegalArgumentException("An environment attribute definition must be a Minecraft environment attribute, not "
                + pair.getKey().getClass().getName());
        }
        return attribute;
    }

    private static Map.Entry<?, ?> pair(Object minecraftObject) {
        if (!(minecraftObject instanceof Map.Entry<?, ?> pair)) {
            throw new IllegalArgumentException("An environment attribute decodes from its definition paired with its value, not from "
                + minecraftObject.getClass().getName());
        }
        return pair;
    }
}
