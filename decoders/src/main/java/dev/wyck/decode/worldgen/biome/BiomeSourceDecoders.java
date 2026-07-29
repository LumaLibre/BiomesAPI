package dev.wyck.decode.worldgen.biome;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import dev.wyck.biome.Biome;
import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.decode.FastReflection;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.biome.CheckeredColumnBiomeSource;
import dev.wyck.worldgen.biome.FixedBiomeSource;
import dev.wyck.worldgen.biome.MultiNoiseBiomeSource;
import dev.wyck.worldgen.biome.MultiNoisePresetBiomeSource;
import dev.wyck.worldgen.biome.TheEndBiomeSource;
import dev.wyck.worldgen.biome.custom.CustomBiomeSourceBridge;
import dev.wyck.worldgen.climate.ClimatePoint;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.CheckerboardColumnBiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NullMarked
@ApiStatus.Internal
public final class BiomeSourceDecoders extends DecoderRegistry<BiomeSource, net.minecraft.world.level.biome.BiomeSource> {

    private static final ResourceKey CUSTOM = ResourceKey.wyck("custom");

    public BiomeSourceDecoders() {
        register(CUSTOM, source -> ((CustomBiomeSourceBridge) source).delegate());
        register("fixed", source -> FixedBiomeSource.of(reference(FastReflection.read(source, "biome"))));
        register("checkerboard", source -> CheckeredColumnBiomeSource.of(
            checkerboardBiomes(source), FastReflection.read(source, "size")
        ));
        register("multi_noise", this::multiNoise);
        register("the_end", _ -> TheEndBiomeSource.INSTANCE);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected ResourceKey discriminate(net.minecraft.world.level.biome.BiomeSource minecraftObject) {
        if (minecraftObject instanceof CustomBiomeSourceBridge) {
            return CUSTOM;
        }
        Object codec = FastReflection.call(minecraftObject, "codec");
        return Decoders.registryKey(BuiltInRegistries.BIOME_SOURCE, (com.mojang.serialization.MapCodec) codec);
    }

    private BiomeSource multiNoise(net.minecraft.world.level.biome.BiomeSource source) {
        var multiNoise = (net.minecraft.world.level.biome.MultiNoiseBiomeSource) source;
        Either<Climate.ParameterList<Holder<net.minecraft.world.level.biome.Biome>>, Holder<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList>> parameters =
            FastReflection.read(multiNoise, "parameters");
        return parameters.map(
            list -> MultiNoiseBiomeSource.of(entries(list.values())),
            preset -> MultiNoisePresetBiomeSource.of(Decoders.referenceKey(preset))
        );
    }

    private static List<MultiNoiseBiomeSource.Entry> entries(List<Pair<Climate.ParameterPoint, Holder<net.minecraft.world.level.biome.Biome>>> values) {
        return values.stream().map(pair -> new MultiNoiseBiomeSource.Entry(
            reference(pair.getSecond()), ClimatePoint.decode(pair.getFirst())
        )).toList();
    }

    private static Set<Biome> checkerboardBiomes(net.minecraft.world.level.biome.BiomeSource source) {
        var checkerboard = (CheckerboardColumnBiomeSource) source;
        HolderSet<net.minecraft.world.level.biome.Biome> holders = FastReflection.read(checkerboard, "allowedBiomes");
        return holders.stream().map(BiomeSourceDecoders::reference)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Biome reference(Holder<net.minecraft.world.level.biome.Biome> holder) {
        return Biome.reference(Decoders.referenceKey(holder));
    }
}
