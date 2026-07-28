package dev.wyck.test.bootstrap.decode;

import dev.wyck.environment.particle.AmbientParticle;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleType;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.environment.particle.options.BlockParticle;
import dev.wyck.environment.particle.options.ColorParticle;
import dev.wyck.environment.particle.options.DustParticle;
import dev.wyck.environment.particle.options.DustTransitionParticle;
import dev.wyck.environment.particle.options.GeyserBaseParticle;
import dev.wyck.environment.particle.options.PowerParticle;
import dev.wyck.environment.particle.options.SculkChargeParticle;
import dev.wyck.environment.particle.options.SpellParticle;
import dev.wyck.environment.particle.options.TrailParticle;
import dev.wyck.environment.particle.options.VibrationParticle;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.GeyserBaseParticleOptions;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Material;
import org.bukkit.Vibration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class ParticleDecodeTest {


    private static dev.wyck.decode.environment.particle.AmbientParticleDecoders decoders() {
        record Holder() {
            static final dev.wyck.decode.environment.particle.AmbientParticleDecoders INSTANCE =
                new dev.wyck.decode.environment.particle.AmbientParticleDecoders();
        }
        return Holder.INSTANCE;
    }

    private static AmbientParticle decode(net.minecraft.core.particles.ParticleOptions options, float probability) {
        return AmbientParticle.decode(new net.minecraft.world.attribute.AmbientParticle(options, probability));
    }

    @Test
    void everyVanillaParticleTypeHasADecoder() {
        List<ResourceKey> missing = BuiltInRegistries.PARTICLE_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !decoders().handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no ambient particle decoder is registered for: " + missing);
    }

    @Test
    void simpleParticlesCarryNoData() {
        AmbientParticle decoded = decode(net.minecraft.core.particles.ParticleTypes.ASH, 0.25f);

        assertEquals(ParticleTypes.ASH, decoded.type());
        assertEquals(0.25f, decoded.probability());
        assertNull(decoded.particleData());
    }

    @Test
    void blockParticlesDecodeToTheirMaterial() {
        AmbientParticle decoded = decode(
            new BlockParticleOption(net.minecraft.core.particles.ParticleTypes.FALLING_DUST, Blocks.STONE.defaultBlockState()), 0.5f);

        assertEquals(ParticleTypes.FALLING_DUST, decoded.type());
        assertEquals(Material.STONE, assertInstanceOf(BlockParticle.class, decoded.particleData()).type());
    }

    @Test
    void packedColoursSurviveTheGettersThatSplitThemIntoFloats() {
        AmbientParticle entityEffect = decode(
            ColorParticleOption.create(net.minecraft.core.particles.ParticleTypes.ENTITY_EFFECT, 0xFF112233), 1.0f);
        assertEquals(0xFF112233, assertInstanceOf(ColorParticle.class, entityEffect.particleData()).color());

        AmbientParticle dust = decode(new DustParticleOptions(0x00FF7F, 1.5f), 1.0f);
        DustParticle dustData = assertInstanceOf(DustParticle.class, dust.particleData());
        assertEquals(0x00FF7F, dustData.color());
        assertEquals(1.5f, dustData.scale());

        AmbientParticle transition = decode(new DustColorTransitionOptions(0x112233, 0x445566, 2.0f), 1.0f);
        DustTransitionParticle transitionData = assertInstanceOf(DustTransitionParticle.class, transition.particleData());
        assertEquals(0x112233, transitionData.fromColor());
        assertEquals(0x445566, transitionData.toColor());
        assertEquals(2.0f, transitionData.scale());

        AmbientParticle spell = decode(
            SpellParticleOption.create(net.minecraft.core.particles.ParticleTypes.EFFECT, 0x123456, 0.5f), 1.0f);
        SpellParticle spellData = assertInstanceOf(SpellParticle.class, spell.particleData());
        assertEquals(0x123456, spellData.color());
        assertEquals(0.5f, spellData.power());
    }

    @Test
    void scalarParticlesDecodeTheirFields() {
        AmbientParticle sculk = decode(new SculkChargeParticleOptions(0.75f), 1.0f);
        assertEquals(0.75f, assertInstanceOf(SculkChargeParticle.class, sculk.particleData()).roll());

        AmbientParticle breath = decode(
            PowerParticleOption.create(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH, 2.0f), 1.0f);
        assertEquals(2.0f, assertInstanceOf(PowerParticle.class, breath.particleData()).power());

        AmbientParticle geyser = decode(
            new GeyserBaseParticleOptions(net.minecraft.core.particles.ParticleTypes.GEYSER_POOF, 3, 0.25f), 1.0f);
        GeyserBaseParticle geyserData = assertInstanceOf(GeyserBaseParticle.class, geyser.particleData());
        assertEquals(3, geyserData.waterBlocks());
        assertEquals(0.25f, geyserData.burstImpulseBase());
    }

    @Test
    void positionalParticlesDecodeWithoutAWorld() {
        AmbientParticle trail = decode(new TrailParticleOption(new Vec3(1.0, 2.0, 3.0), 0x00FF00, 20), 1.0f);
        TrailParticle trailData = assertInstanceOf(TrailParticle.class, trail.particleData());
        assertEquals(2.0, trailData.target().getY());
        assertEquals(0x00FF00, trailData.color());
        assertEquals(20, trailData.duration());

        AmbientParticle vibration = decode(new VibrationParticleOption(new BlockPositionSource(new BlockPos(4, 5, 6)), 10), 1.0f);
        VibrationParticle vibrationData = assertInstanceOf(VibrationParticle.class, vibration.particleData());
        Vibration.Destination.BlockDestination destination =
            assertInstanceOf(Vibration.Destination.BlockDestination.class, vibrationData.destination());
        assertEquals(5, destination.getLocation().getBlockY());
        assertEquals(10, vibrationData.arrivalInTicks());
    }

    @Test
    void opaqueParticleHandlesJustCarryTheirObject() {
        net.minecraft.core.particles.ParticleOptions options = net.minecraft.core.particles.ParticleTypes.FLAME;

        assertSame(options, ParticleOptions.decode(options).toMinecraft());
        assertSame(net.minecraft.core.particles.ParticleTypes.FLAME,
            ParticleType.decode(net.minecraft.core.particles.ParticleTypes.FLAME).toMinecraft());
    }
}
