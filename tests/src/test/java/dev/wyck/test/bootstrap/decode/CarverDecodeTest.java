package dev.wyck.test.bootstrap.decode;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.HolderSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MinecraftBootstrap.class)
class CarverDecodeTest {

    @Test
    void everyWorldCarverTypeHasAConfigurationDecoder() {
        var decoders = new dev.wyck.decode.worldgen.carver.CarverConfigurationDecoders();
        BuiltInRegistries.CARVER.keySet().forEach(key -> assertTrue(
            decoders.handles(Decoders.key(key)), () -> "Missing carver decoder for " + key
        ));
        assertEquals(BuiltInRegistries.CARVER.size(), decoders.handled().size());
    }

    @Test
    void everyVanillaConfiguredCarverDecodesInline() {
        var registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.CONFIGURED_CARVER);
        registry.entrySet().forEach(entry -> {
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured =
                withDirectReplaceable(entry.getValue());
            ComposedCarver decoded = assertInstanceOf(ComposedCarver.class,
                ConfiguredWorldCarver.decode(Holder.direct(configured)));
            ResourceKey expectedType = Decoders.registryKey(
                BuiltInRegistries.CARVER, configured.worldCarver());
            assertEquals(expectedType, decoded.type().resourceKey());
            assertEquals(configured.config().probability, decoded.config().probability());
        });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> withDirectReplaceable(
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured
    ) {
        var original = configured.config();
        var replaceable = HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.STONE));
        net.minecraft.world.level.levelgen.carver.CarverConfiguration copy;
        if (original instanceof net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration cave) {
            copy = new net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration(
                cave.probability, cave.y, cave.yScale, cave.lavaLevel, cave.debugSettings, replaceable,
                cave.horizontalRadiusMultiplier, cave.verticalRadiusMultiplier, cave.floorLevel);
        } else {
            var canyon = (net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration) original;
            copy = new net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration(
                canyon.probability, canyon.y, canyon.yScale, canyon.lavaLevel, canyon.debugSettings, replaceable,
                canyon.verticalRotation, canyon.shape);
        }
        return new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver(configured.worldCarver(), copy);
    }
}
