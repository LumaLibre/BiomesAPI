package dev.wyck.decode.environment.particle;

import dev.wyck.decode.Decoders;
import dev.wyck.environment.particle.AmbientParticle;
import dev.wyck.environment.particle.ParticleData;
import dev.wyck.environment.particle.ParticleTypes;
import dev.wyck.environment.particle.options.BlockParticle;
import dev.wyck.environment.particle.options.ColorParticle;
import dev.wyck.environment.particle.options.DustParticle;
import dev.wyck.environment.particle.options.DustTransitionParticle;
import dev.wyck.environment.particle.options.GeyserBaseParticle;
import dev.wyck.environment.particle.options.GeyserParticle;
import dev.wyck.environment.particle.options.ItemParticle;
import dev.wyck.environment.particle.options.PowerParticle;
import dev.wyck.environment.particle.options.SculkChargeParticle;
import dev.wyck.environment.particle.options.ShriekParticle;
import dev.wyck.environment.particle.options.SpellParticle;
import dev.wyck.environment.particle.options.TrailParticle;
import dev.wyck.environment.particle.options.VibrationParticle;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.GeyserBaseParticleOptions;
import net.minecraft.core.particles.GeyserParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Vibration;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3f;
import org.jspecify.annotations.NullMarked;

import java.awt.Color;
import java.util.function.Function;

@NullMarked
@ApiStatus.Internal
public final class AmbientParticleDecoders extends DecoderRegistry<AmbientParticle, net.minecraft.world.attribute.AmbientParticle> {

    public AmbientParticleDecoders() {
        for (ParticleTypes type : ParticleTypes.values()) {
            if (type.isSimple()) {
                simple(type);
            }
        }
        complex(ParticleTypes.BLOCK, BlockParticleOption.class, AmbientParticleDecoders::block);
        complex(ParticleTypes.BLOCK_MARKER, BlockParticleOption.class, AmbientParticleDecoders::block);
        complex(ParticleTypes.BLOCK_CRUMBLE, BlockParticleOption.class, AmbientParticleDecoders::block);
        complex(ParticleTypes.DUST_PILLAR, BlockParticleOption.class, AmbientParticleDecoders::block);
        complex(ParticleTypes.FALLING_DUST, BlockParticleOption.class, AmbientParticleDecoders::block);
        complex(ParticleTypes.ENTITY_EFFECT, ColorParticleOption.class, AmbientParticleDecoders::color);
        complex(ParticleTypes.FLASH, ColorParticleOption.class, AmbientParticleDecoders::color);
        complex(ParticleTypes.TINTED_LEAVES, ColorParticleOption.class, AmbientParticleDecoders::color);
        complex(ParticleTypes.DUST, DustParticleOptions.class, opt -> new DustParticle(vec3fColor(opt.getColor()), opt.getScale()));
        complex(ParticleTypes.DUST_COLOR_TRANSITION, DustColorTransitionOptions.class, opt -> new DustTransitionParticle(vec3fColor(opt.getFromColor()), vec3fColor(opt.getToColor()), opt.getScale()));
        complex(ParticleTypes.EFFECT, SpellParticleOption.class, opt -> new SpellParticle(opt.color, opt.getPower()));
        complex(ParticleTypes.INSTANT_EFFECT, SpellParticleOption.class, opt -> new SpellParticle(opt.color, opt.getPower()));
        complex(ParticleTypes.GEYSER, GeyserParticleOptions.class, opt -> GeyserParticle.of(opt.waterBlocks()));
        complex(ParticleTypes.GEYSER_PLUME, GeyserParticleOptions.class, opt -> GeyserParticle.of(opt.waterBlocks()));
        complex(ParticleTypes.GEYSER_BASE, GeyserBaseParticleOptions.class, opt -> GeyserBaseParticle.of(opt.waterBlocks(), opt.burstImpulseBase()));
        complex(ParticleTypes.GEYSER_POOF, GeyserBaseParticleOptions.class, opt -> GeyserBaseParticle.of(opt.waterBlocks(), opt.burstImpulseBase()));
        complex(ParticleTypes.DRAGON_BREATH, PowerParticleOption.class, opt -> PowerParticle.of(opt.getPower()));
        complex(ParticleTypes.SCULK_CHARGE, SculkChargeParticleOptions.class, opt -> SculkChargeParticle.of(opt.roll()));
        complex(ParticleTypes.SHRIEK, ShriekParticleOption.class, opt -> ShriekParticle.of(opt.getDelay()));
        complex(ParticleTypes.ITEM, ItemParticleOption.class, opt -> {
            ItemStackTemplate template = opt.getItem();
            return ItemParticle.of(ItemStack.of(CraftMagicNumbers.getMaterial(template.item().value()), template.count()));
        });
        complex(ParticleTypes.TRAIL, TrailParticleOption.class, opt -> new TrailParticle(CraftLocation.toBukkit(opt.target()), opt.color(), opt.duration()));
        complex(ParticleTypes.VIBRATION, VibrationParticleOption.class, AmbientParticleDecoders::vibration);
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.attribute.AmbientParticle ambient) {
        return Decoders.registryKey(BuiltInRegistries.PARTICLE_TYPE, ambient.particle().getType());
    }

    private void simple(ParticleTypes type) {
        register(type.resourceKey().path(), nms -> AmbientParticle.of(type, nms.probability()));
    }

    private <O> void complex(ParticleTypes type, Class<O> optionsType, Function<O, ParticleData> data) {
        register(type.resourceKey().path(), ambient -> {
            return AmbientParticle.of(type, ambient.probability(), data.apply(optionsType.cast(ambient.particle())));
        });
    }

    private static ParticleData block(BlockParticleOption options) {
        return BlockParticle.of(CraftMagicNumbers.getMaterial(options.getState().getBlock()));
    }

    private static ParticleData color(ColorParticleOption options) {
        // just reconstruct color from RGBA, better than reflecting
        Color color = new Color(options.getRed(), options.getGreen(), options.getBlue(), options.getAlpha());
        return new ColorParticle(color.getRGB());
    }

    private static ParticleData vibration(VibrationParticleOption options) {
        PositionSource destination = options.getDestination();
        if (!(destination instanceof BlockPositionSource(net.minecraft.core.BlockPos pos))) {
            // Vanilla's own codec rejects entity sources, and Bukkit's destination needs a live entity.
            throw new IllegalArgumentException("Vibration particles with a " + destination.getType() + " destination cannot be read");
        }
        return VibrationParticle.of(new Vibration.Destination.BlockDestination(CraftLocation.toBukkit(pos)), options.getArrivalInTicks());
    }

    private static int vec3fColor(Vector3f vec) {
        int r = Math.round(vec.x * 255.0f);
        int g = Math.round(vec.y * 255.0f);
        int b = Math.round(vec.z * 255.0f);
        return (r << 16) | (g << 8) | b;
    }
}
