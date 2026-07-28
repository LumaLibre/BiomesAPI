package dev.wyck.test.bootstrap.decode;

import dev.wyck.environment.Activity;
import dev.wyck.environment.BedRule;
import dev.wyck.environment.MoonPhase;
import dev.wyck.environment.TriState;
import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.attribute.NmsEnvironmentAttributes;
import dev.wyck.environment.particle.ParticleCatalog;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.modifier.ColorModifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class EnvironmentAttributeDecodeTest {

    private static dev.wyck.decode.environment.attribute.EnvironmentAttributeDecoders decoders() {
        record Holder() {
            static final dev.wyck.decode.environment.attribute.EnvironmentAttributeDecoders INSTANCE =
                new dev.wyck.decode.environment.attribute.EnvironmentAttributeDecoders();
        }
        return Holder.INSTANCE;
    }

    private static net.minecraft.world.attribute.EnvironmentAttributeMap.Builder minecraftMap() {
        return net.minecraft.world.attribute.EnvironmentAttributeMap.builder();
    }

    @Test
    void everyVanillaAttributeTypeHasADecoder() {
        List<ResourceKey> missing = BuiltInRegistries.ATTRIBUTE_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !decoders().handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no environment attribute decoder is registered for value type: " + missing);
    }

    @Test
    void valuesMinecraftStoresAsThemselvesComeBackUnchanged() {
        EnvironmentAttributeMap decoded = EnvironmentAttributeMap.decode(minecraftMap()
            .set(net.minecraft.world.attribute.EnvironmentAttributes.FOG_COLOR, 0x112233)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.SKY_LIGHT_FACTOR, 0.75f)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.CAN_START_RAID, false)
            .build());

        assertEquals(0x112233, decoded.get(EnvironmentAttributes.FOG_COLOR));
        assertEquals(0.75f, decoded.get(EnvironmentAttributes.SKY_LIGHT_FACTOR));
        assertEquals(false, decoded.get(EnvironmentAttributes.CAN_START_RAID));
    }

    @Test
    void constantValuedAttributesComeBackAsWyckConstants() {
        EnvironmentAttributeMap decoded = EnvironmentAttributeMap.decode(minecraftMap()
            .set(net.minecraft.world.attribute.EnvironmentAttributes.MOON_PHASE, net.minecraft.world.level.MoonPhase.WANING_GIBBOUS)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.EYEBLOSSOM_OPEN, net.minecraft.util.TriState.DEFAULT)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.VILLAGER_ACTIVITY, net.minecraft.world.entity.schedule.Activity.PANIC)
            .build());

        assertEquals(MoonPhase.WANING_GIBBOUS, decoded.get(EnvironmentAttributes.MOON_PHASE));
        assertEquals(TriState.DEFAULT, decoded.get(EnvironmentAttributes.EYEBLOSSOM_OPEN));
        assertEquals(Activity.PANIC, decoded.get(EnvironmentAttributes.VILLAGER_ACTIVITY));
    }

    @Test
    void wrappedValuesDecodeThroughTheirOwnWrappers() {
        net.minecraft.world.attribute.BedRule bedRule = net.minecraft.world.attribute.BedRule.EXPLODES;
        net.minecraft.world.attribute.AmbientSounds sounds = net.minecraft.world.attribute.AmbientSounds.LEGACY_CAVE_SETTINGS;

        EnvironmentAttributeMap decoded = EnvironmentAttributeMap.decode(minecraftMap()
            .set(net.minecraft.world.attribute.EnvironmentAttributes.BED_RULE, bedRule)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_SOUNDS, sounds)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.BACKGROUND_MUSIC, net.minecraft.world.attribute.BackgroundMusic.OVERWORLD)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, net.minecraft.core.particles.ParticleTypes.DRIPPING_WATER)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_PARTICLES, List.of(
                new net.minecraft.world.attribute.AmbientParticle(net.minecraft.core.particles.ParticleTypes.ASH, 0.25f)))
            .build());

        BedRule decodedBedRule = assertInstanceOf(BedRule.class, decoded.get(EnvironmentAttributes.BED_RULE));
        assertEquals(BedRule.Rule.NEVER, decodedBedRule.canSleep());
        assertTrue(decodedBedRule.explodes());

        AmbientSounds decodedSounds = assertInstanceOf(AmbientSounds.class, decoded.get(EnvironmentAttributes.AMBIENT_SOUNDS));
        assertEquals(6000, decodedSounds.mood().orElseThrow().tickDelay());

        BackgroundMusic decodedMusic = assertInstanceOf(BackgroundMusic.class, decoded.get(EnvironmentAttributes.BACKGROUND_MUSIC));
        assertTrue(decodedMusic.defaultMusic().isPresent());
        assertTrue(decodedMusic.underwaterMusic().isEmpty());

        ParticleOptions dripstone = assertInstanceOf(ParticleOptions.class, decoded.get(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE));
        assertEquals(net.minecraft.core.particles.ParticleTypes.DRIPPING_WATER, dripstone.toMinecraft());

        ParticleCatalog ambient = assertInstanceOf(ParticleCatalog.class, decoded.get(EnvironmentAttributes.AMBIENT_PARTICLES));
        assertEquals(ParticleTypes.ASH, ambient.particles().getFirst().type());
        assertEquals(0.25f, ambient.particles().getFirst().probability());
    }

    /**
     * Every decoder carries the converter that writes its value back, so a decoded map re-encodes as it
     * was. Ambient particles are left out: encoding one resolves a version-specific particle factory
     * that is not on this module's classpath.
     */
    @Test
    void aDecodedMapEncodesBackToTheSameMinecraftMap() {
        net.minecraft.world.attribute.EnvironmentAttributeMap original = minecraftMap()
            .set(net.minecraft.world.attribute.EnvironmentAttributes.SKY_COLOR, 0x8899AA)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.MOON_PHASE, net.minecraft.world.level.MoonPhase.NEW_MOON)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.BED_RULE, net.minecraft.world.attribute.BedRule.EXPLODES)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.MONSTERS_BURN, true)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.VILLAGER_ACTIVITY, net.minecraft.world.entity.schedule.Activity.REST)
            .set(net.minecraft.world.attribute.EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, net.minecraft.core.particles.ParticleTypes.DRIPPING_LAVA)
            .build();

        assertEquals(original, NmsEnvironmentAttributes.toNms(EnvironmentAttributeMap.decode(original)));
    }

    /**
     * Sound-carrying attributes are the one thing that does not round trip identically: Wyck's sound
     * wrappers encode as {@code Holder.direct}, so a registry reference decodes with the right key but
     * comes back inlined. The asymmetry is on the encode side; the decoded value itself is faithful.
     */
    @Test
    void decodedSoundsKeepTheirKeyButReEncodeAsDirectHolders() {
        net.minecraft.world.attribute.EnvironmentAttributeMap original = minecraftMap()
            .set(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_SOUNDS, net.minecraft.world.attribute.AmbientSounds.LEGACY_CAVE_SETTINGS)
            .build();

        AmbientSounds decoded = assertInstanceOf(AmbientSounds.class,
            EnvironmentAttributeMap.decode(original).get(EnvironmentAttributes.AMBIENT_SOUNDS));
        assertEquals(ResourceKey.minecraft("ambient.cave"), decoded.mood().orElseThrow().soundEvent().location());

        net.minecraft.world.attribute.AmbientSounds reEncoded =
            sounds(NmsEnvironmentAttributes.toNms(EnvironmentAttributeMap.decode(original)));
        assertEquals(sounds(original).mood().orElseThrow().soundEvent().value(), reEncoded.mood().orElseThrow().soundEvent().value());
        assertInstanceOf(net.minecraft.core.Holder.Direct.class, reEncoded.mood().orElseThrow().soundEvent());
    }

    private static net.minecraft.world.attribute.AmbientSounds sounds(net.minecraft.world.attribute.EnvironmentAttributeMap map) {
        return (net.minecraft.world.attribute.AmbientSounds)
            map.get(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_SOUNDS).argument();
    }

    /** Wyck's map holds plain values, so an entry that modifies its attribute has nowhere to land. */
    @Test
    void aModifiedAttributeIsRejected() {
        net.minecraft.world.attribute.EnvironmentAttributeMap modified = minecraftMap()
            .modify(net.minecraft.world.attribute.EnvironmentAttributes.SKY_COLOR, ColorModifier.ADD, 0x010101)
            .build();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
            () -> EnvironmentAttributeMap.decode(modified));
        assertTrue(thrown.getMessage().contains("modified rather than set"), thrown::getMessage);
    }

    /** {@code defaultValue()} used to throw for anything that was not a number, boolean or string. */
    @Test
    void defaultValuesOfWrappedAttributesRead() {
        BedRule bedRule = assertInstanceOf(BedRule.class, EnvironmentAttributes.BED_RULE.get().defaultValue());
        assertEquals(BedRule.Rule.WHEN_DARK, bedRule.canSleep());

        assertEquals(MoonPhase.FULL_MOON, EnvironmentAttributes.MOON_PHASE.get().defaultValue());
    }
}
