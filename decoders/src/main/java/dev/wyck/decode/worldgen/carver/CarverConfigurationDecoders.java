package dev.wyck.decode.worldgen.carver;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.carver.CanyonCarverConfiguration;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.CarverDebugSettings;
import dev.wyck.worldgen.carver.CaveCarverConfiguration;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class CarverConfigurationDecoders extends DecoderRegistry<CarverConfiguration, Object> {

    public CarverConfigurationDecoders() {
        register("cave", this::cave);
        register("nether_cave", this::cave);
        register("canyon", this::canyon);
    }

    @Override
    protected dev.wyck.keys.ResourceKey discriminate(Object minecraftObject) {
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured = Decoders.value(minecraftObject);
        return Decoders.registryKey(BuiltInRegistries.CARVER, configured.worldCarver());
    }

    private CarverConfiguration cave(Object minecraftObject) {
        net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration config = config(minecraftObject);
        return CaveCarverConfiguration.of(
            config.probability,
            HeightProvider.decode(config.y),
            FloatProvider.decode(config.yScale),
            VerticalAnchor.decode(config.lavaLevel),
            CarverDebugSettings.decode(config.debugSettings),
            Decoders.materials(config.replaceable),
            FloatProvider.decode(config.horizontalRadiusMultiplier),
            FloatProvider.decode(config.verticalRadiusMultiplier),
            FloatProvider.decode(config.floorLevel)
        );
    }

    private CarverConfiguration canyon(Object minecraftObject) {
        net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration config = config(minecraftObject);
        return CanyonCarverConfiguration.of(
            config.probability,
            HeightProvider.decode(config.y),
            FloatProvider.decode(config.yScale),
            VerticalAnchor.decode(config.lavaLevel),
            CarverDebugSettings.decode(config.debugSettings),
            Decoders.materials(config.replaceable),
            FloatProvider.decode(config.verticalRotation),
            CanyonCarverConfiguration.CanyonShapeConfiguration.decode(config.shape)
        );
    }

    @SuppressWarnings("unchecked")
    private static <T extends net.minecraft.world.level.levelgen.carver.CarverConfiguration> T config(Object minecraftObject) {
        return (T) ((net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>) Decoders.value(minecraftObject)).config();
    }
}
