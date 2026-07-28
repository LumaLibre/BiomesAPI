package dev.wyck.test.bootstrap.decode;

import dev.wyck.environment.BedRule;
import dev.wyck.environment.sounds.AmbientAdditionsSettings;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.environment.sounds.Music;
import dev.wyck.environment.sounds.SoundEvent;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class SoundDecodeTest {

    @Test
    void soundEventsDecodeFromEitherAHolderOrTheEventItself() {
        SoundEvent fromHolder = SoundEvent.decode(SoundEvents.AMBIENT_CAVE);
        assertEquals(ResourceKey.minecraft("ambient.cave"), fromHolder.location());
        assertTrue(fromHolder.fixedRange().isEmpty());

        net.minecraft.sounds.SoundEvent fixed = net.minecraft.sounds.SoundEvent.createFixedRangeEvent(
            net.minecraft.resources.Identifier.withDefaultNamespace("block.anvil.land"), 12.5f);
        SoundEvent fromEvent = SoundEvent.decode(fixed);
        assertEquals(ResourceKey.minecraft("block.anvil.land"), fromEvent.location());
        assertEquals(12.5f, fromEvent.fixedRange().orElseThrow());
    }

    @Test
    void musicDecodesThroughItsSoundEvent() {
        Music decoded = Music.decode(new net.minecraft.sounds.Music(SoundEvents.AMBIENT_CAVE, 100, 200, true));

        assertEquals(ResourceKey.minecraft("ambient.cave"), decoded.sound().location());
        assertEquals(100, decoded.minDelay());
        assertEquals(200, decoded.maxDelay());
        assertTrue(decoded.replaceCurrentMusic());
    }

    @Test
    void backgroundMusicKeepsItsEmptySlotsEmpty() {
        BackgroundMusic decoded = BackgroundMusic.decode(net.minecraft.world.attribute.BackgroundMusic.OVERWORLD);

        assertTrue(decoded.defaultMusic().isPresent());
        assertTrue(decoded.creativeMusic().isPresent());
        assertTrue(decoded.underwaterMusic().isEmpty());
    }

    @Test
    void ambientSoundsDecodeTheirMoodAndAdditions() {
        net.minecraft.world.attribute.AmbientSounds minecraftSounds = new net.minecraft.world.attribute.AmbientSounds(
            Optional.of(SoundEvents.AMBIENT_CAVE),
            Optional.of(net.minecraft.world.attribute.AmbientMoodSettings.LEGACY_CAVE_SETTINGS),
            List.of(new net.minecraft.world.attribute.AmbientAdditionsSettings(Holder.direct(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS.value()), 0.0111))
        );

        AmbientSounds decoded = AmbientSounds.decode(minecraftSounds);

        assertEquals(ResourceKey.minecraft("ambient.cave"), decoded.loop().orElseThrow().location());
        assertEquals(8, decoded.mood().orElseThrow().blockSearchExtent());
        AmbientAdditionsSettings additions = decoded.additions().getFirst();
        assertEquals(0.0111, additions.tickChance());
        assertEquals(ResourceKey.minecraft("ambient.basalt_deltas.additions"), additions.soundEvent().location());
    }

    /** The two component trees only meet through json, in both directions. */
    @Test
    void bedRulesDecodeTheirErrorMessage() {
        BedRule decoded = BedRule.decode(net.minecraft.world.attribute.BedRule.CAN_SLEEP_WHEN_DARK);

        assertEquals(BedRule.Rule.WHEN_DARK, decoded.canSleep());
        assertEquals(BedRule.Rule.ALWAYS, decoded.canSetSpawn());
        assertFalse(decoded.explodes());

        Component message = decoded.errorMessage().orElseThrow();
        assertEquals("block.minecraft.bed.no_sleep", assertInstanceOf(TranslatableComponent.class, message).key());
    }
}
