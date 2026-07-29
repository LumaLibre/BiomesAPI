package dev.wyck.test.bootstrap.decode;

import dev.wyck.environment.Activity;
import dev.wyck.environment.BedRule;
import dev.wyck.environment.MoonPhase;
import dev.wyck.environment.TriState;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributes;
import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
import dev.wyck.util.attribute.EnvironmentAttributesUtil;
import dev.wyck.environment.particle.ParticleCatalog;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.attribute.modifier.ColorModifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        assertEquals(original, EnvironmentAttributesUtil.toNms(EnvironmentAttributeMap.decode(original)));
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
            sounds(EnvironmentAttributesUtil.toNms(EnvironmentAttributeMap.decode(original)));
        assertEquals(sounds(original).mood().orElseThrow().soundEvent().value(), reEncoded.mood().orElseThrow().soundEvent().value());
        assertInstanceOf(net.minecraft.core.Holder.Direct.class, reEncoded.mood().orElseThrow().soundEvent());
    }

    private static net.minecraft.world.attribute.AmbientSounds sounds(net.minecraft.world.attribute.EnvironmentAttributeMap map) {
        return (net.minecraft.world.attribute.AmbientSounds)
            map.get(net.minecraft.world.attribute.EnvironmentAttributes.AMBIENT_SOUNDS).argument();
    }

    @Test
    void modifiedAttributesPreserveTheirOperationAndArgument() {
        net.minecraft.world.attribute.EnvironmentAttributeMap modified = minecraftMap()
            .modify(net.minecraft.world.attribute.EnvironmentAttributes.WATER_FOG_END_DISTANCE,
                net.minecraft.world.attribute.modifier.FloatModifier.MULTIPLY, 0.85F)
            .build();

        EnvironmentAttributeMap decoded = EnvironmentAttributeMap.decode(modified);
        EnvironmentAttributeMap.Modification<Float, Float> modification =
            decoded.modification(EnvironmentAttributes.WATER_FOG_END_DISTANCE);

        assertNotNull(modification);
        assertEquals(AttributeOperation.MULTIPLY, modification.operation());
        assertEquals(0.85F, modification.argument());
        assertEquals(modified, EnvironmentAttributesUtil.toNms(decoded));
    }

    @Test
    void modifierBuilderUsesTheTimelineOperationModel() {
        EnvironmentAttributeMap attributes = EnvironmentAttributeMap.builder()
            .modify(EnvironmentAttributes.WATER_FOG_END_DISTANCE, AttributeOperation.MULTIPLY, 0.85F)
            .build();

        net.minecraft.world.attribute.EnvironmentAttributeMap encoded = EnvironmentAttributesUtil.toNms(attributes);
        var entry = encoded.get(net.minecraft.world.attribute.EnvironmentAttributes.WATER_FOG_END_DISTANCE);

        assertNotNull(entry);
        assertEquals(net.minecraft.world.attribute.modifier.FloatModifier.MULTIPLY, entry.modifier());
        assertEquals(0.85F, entry.argument());
    }

    @Test
    void everyVanillaBiomeAttributeMapDecodes() {
        BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME).entrySet().forEach(entry ->
            assertDoesNotThrow(
                () -> EnvironmentAttributeMap.decode(entry.getValue().getAttributes()),
                () -> "failed to decode the environment attributes of " + entry.getKey()
            ));
    }

    @Test
    void structuredModifierArgumentsUseTheTimelineWrappers() {
        net.minecraft.world.attribute.EnvironmentAttributeMap modified = minecraftMap()
            .modify(net.minecraft.world.attribute.EnvironmentAttributes.STAR_BRIGHTNESS,
                net.minecraft.world.attribute.modifier.FloatModifier.ALPHA_BLEND,
                new net.minecraft.world.attribute.modifier.FloatWithAlpha(0.75F, 0.25F))
            .modify(net.minecraft.world.attribute.EnvironmentAttributes.FOG_COLOR,
                ColorModifier.BLEND_TO_GRAY,
                new ColorModifier.BlendToGray(0.4F, 0.6F))
            .build();

        EnvironmentAttributeMap decoded = EnvironmentAttributeMap.decode(modified);
        EnvironmentAttributeMap.Modification<Float, AlphaValue> alpha =
            decoded.modification(EnvironmentAttributes.STAR_BRIGHTNESS);
        EnvironmentAttributeMap.Modification<Integer, GrayBlend> gray =
            decoded.modification(EnvironmentAttributes.FOG_COLOR);

        assertNotNull(alpha);
        assertEquals(AttributeOperation.ALPHA_BLEND, alpha.operation());
        assertEquals(0.75F, alpha.argument().value());
        assertEquals(0.25F, alpha.argument().alpha());

        assertNotNull(gray);
        assertEquals(AttributeOperation.BLEND_TO_GRAY, gray.operation());
        assertEquals(0.4F, gray.argument().brightness());
        assertEquals(0.6F, gray.argument().factor());
        assertEquals(modified, EnvironmentAttributesUtil.toNms(decoded));
    }

    /** {@code defaultValue()} used to throw for anything that was not a number, boolean or string. */
    @Test
    void defaultValuesOfWrappedAttributesRead() {
        BedRule bedRule = assertInstanceOf(BedRule.class, EnvironmentAttributes.BED_RULE.get().defaultValue());
        assertEquals(BedRule.Rule.WHEN_DARK, bedRule.canSleep());

        assertEquals(MoonPhase.FULL_MOON, EnvironmentAttributes.MOON_PHASE.get().defaultValue());
    }
}
