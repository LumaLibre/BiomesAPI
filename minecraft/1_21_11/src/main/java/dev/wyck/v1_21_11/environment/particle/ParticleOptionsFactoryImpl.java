package dev.wyck.v1_21_11.environment.particle;

import dev.wyck.annotations.WireFactory;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.particle.ParticleOptionsFactory;
import dev.wyck.environment.particle.ParticleOptionsImpl;
import dev.wyck.environment.particle.ParticleType;
import dev.wyck.environment.particle.ParticleTypeImpl;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Vibration;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public final class ParticleOptionsFactoryImpl implements ParticleOptionsFactory {

    @Override
    public ParticleOptions block(ParticleType type, Material material) {
        net.minecraft.core.particles.ParticleType<BlockParticleOption> nmsType = (net.minecraft.core.particles.ParticleType<BlockParticleOption>) ((ParticleTypeImpl<?>) type).nms();
        BlockState blockState = CraftMagicNumbers.getBlock(material).defaultBlockState();
        return new ParticleOptionsImpl(new BlockParticleOption(nmsType, blockState));
    }

    @Override
    public ParticleOptions color(ParticleType type, int rgb) {
        net.minecraft.core.particles.ParticleType<ColorParticleOption> nmsType = (net.minecraft.core.particles.ParticleType<ColorParticleOption>) ((ParticleTypeImpl<?>) type).nms();
        return new ParticleOptionsImpl(ColorParticleOption.create(nmsType, rgb));
    }

    @Override
    public ParticleOptions dust(int rgb, float size) {
        return new ParticleOptionsImpl(new DustParticleOptions(rgb, size));
    }

    @Override
    public ParticleOptions dustTransition(int fromRgb, int toRgb, float size) {
        return new ParticleOptionsImpl(new DustColorTransitionOptions(fromRgb, toRgb, size));
    }

    @Override
    public ParticleOptions item(ParticleType type, ItemStack bukkitItemStack) {
        net.minecraft.core.particles.ParticleType<ItemParticleOption> nmsType = (net.minecraft.core.particles.ParticleType<ItemParticleOption>) ((ParticleTypeImpl<?>) type).nms();
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asCraftCopy(bukkitItemStack).handle;
        return new ParticleOptionsImpl(new ItemParticleOption(nmsType, nmsStack));
    }

    @Override
    public ParticleOptions power(ParticleType type, float power) {
        net.minecraft.core.particles.ParticleType<PowerParticleOption> nmsType = (net.minecraft.core.particles.ParticleType<PowerParticleOption>) ((ParticleTypeImpl<?>) type).nms();
        return new ParticleOptionsImpl(PowerParticleOption.create(nmsType, power));
    }

    @Override
    public ParticleOptions sculkCharge(float roll) {
        return new ParticleOptionsImpl(new SculkChargeParticleOptions(roll));
    }


    @Override
    public ParticleOptions simple(ParticleType type) {
        net.minecraft.core.particles.ParticleType<?> nms = ((ParticleTypeImpl<?>) type).nms();
        return new ParticleOptionsImpl((net.minecraft.core.particles.ParticleOptions) nms);
    }

    @Override
    public ParticleOptions spell(ParticleType type, int rgb, float power) {
        net.minecraft.core.particles.ParticleType<SpellParticleOption> nmsType = (net.minecraft.core.particles.ParticleType<SpellParticleOption>) ((ParticleTypeImpl<?>) type).nms();
        return new ParticleOptionsImpl(SpellParticleOption.create(nmsType, rgb, power));
    }

    @Override
    public ParticleOptions trail(Location target, int rgb, int duration) {
        return new ParticleOptionsImpl(new TrailParticleOption(CraftLocation.toVec3(target), rgb, duration));
    }


    @Override
    public ParticleOptions vibration(Vibration.Destination destination, int arrivalInTicks) {
        return new ParticleOptionsImpl(new VibrationParticleOption(toNmsPositionSource(destination), arrivalInTicks));
    }

    @Override
    public ParticleOptions geyser(ParticleType type, int waterBlocks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ParticleOptions geyserBase(ParticleType type, int waterBlocks, float burstImpulseBase) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ParticleOptions shriek(int delay) {
        return new ParticleOptionsImpl(new ShriekParticleOption(delay));
    }

    @Override
    public ParticleType typeByKey(String key) {
        Identifier id = Identifier.withDefaultNamespace(key);
        net.minecraft.core.particles.ParticleType<?> nms = BuiltInRegistries.PARTICLE_TYPE.getValue(id);
        if (nms == null) {
            throw new IllegalArgumentException("Unknown particle type: " + key);
        }
        return wrap(nms);
    }

    @SuppressWarnings("unchecked")
    private static <O extends net.minecraft.core.particles.ParticleOptions> ParticleTypeImpl<O> wrap(net.minecraft.core.particles.ParticleType<?> nms) {
        return new ParticleTypeImpl<>((net.minecraft.core.particles.ParticleType<O>) nms);
    }


    private static PositionSource toNmsPositionSource(Vibration.Destination dest) {
        if (dest instanceof Vibration.Destination.BlockDestination block) {
            return new BlockPositionSource(CraftLocation.toBlockPosition(block.getLocation()));
        } else if (dest instanceof Vibration.Destination.EntityDestination entityDest) {
            Entity nmsEntity = ((CraftEntity) entityDest.getEntity()).getHandle();
            return new EntityPositionSource(nmsEntity, nmsEntity.getEyeHeight());
        }
        throw new IllegalArgumentException("Unknown vibration destination " + dest);
    }
}