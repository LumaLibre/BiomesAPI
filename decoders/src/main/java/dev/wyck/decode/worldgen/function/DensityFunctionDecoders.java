package dev.wyck.decode.worldgen.function;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.misc.EndIslands;
import dev.wyck.worldgen.function.misc.FindTopSurface;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.function.misc.RangeChoice;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import dev.wyck.worldgen.function.noise.NoiseFunction;
import dev.wyck.worldgen.function.noise.ShiftedFunction;
import dev.wyck.worldgen.function.noise.ShiftedNoise2dFunction;
import dev.wyck.worldgen.function.simple.BlendAlpha;
import dev.wyck.worldgen.function.simple.BlendOffset;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import dev.wyck.worldgen.function.transformer.MappedTransformer;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

@NullMarked
@ApiStatus.Internal
public final class DensityFunctionDecoders extends DecoderRegistry<DensityFunction, net.minecraft.world.level.levelgen.DensityFunction> {

    public static final ResourceKey REFERENCE = ResourceKey.wyck("reference");

    public DensityFunctionDecoders() {
        register(REFERENCE, this::reference);
        register("constant", function -> ConstantSimpleFunction.of(
            function.minValue()
        ));
        register("blend_alpha", _ -> BlendAlpha.INSTANCE);
        register("blend_offset", _ -> BlendOffset.INSTANCE);
        register("clamp", function -> {
            DensityFunctionNodes.Node node = node(function);
            return ClampedTransformer.of(
                DensityFunction.decode(node.child(0)), node.asDouble("min"), node.asDouble("max")
            );
        });
        register("noise", function -> {
            DensityFunctionNodes.Node node = node(function);
            return NoiseFunction.of(
                NoiseParameters.decode(node.noise(0)),
                node.asDouble("xz_scale"), node.asDouble("y_scale")
            );
        });
        register("shifted_noise", this::shiftedNoise);
        register("range_choice", function -> {
            DensityFunctionNodes.Node node = node(function);
            return RangeChoice.of(
                DensityFunction.decode(node.child(0)),
                node.asDouble("min_inclusive"), node.asDouble("max_exclusive"),
                DensityFunction.decode(node.child(1)), DensityFunction.decode(node.child(2))
            );
        });
        register("y_clamped_gradient", function -> {
            DensityFunctionNodes.Node node = node(function);
            return YClampedGradient.of(
                node.asInt("from_y"), node.asInt("to_y"),
                node.asDouble("from_value"), node.asDouble("to_value")
            );
        });
        register("find_top_surface", function -> {
            DensityFunctionNodes.Node node = node(function);
            return FindTopSurface.of(
                DensityFunction.decode(node.child(0)), DensityFunction.decode(node.child(1)),
                node.asInt("lower_bound"), node.asInt("cell_height")
            );
        });
        register("end_islands", _ -> EndIslands.of(0L));

        for (Marker.Type type : Marker.Type.values()) {
            register(key(type), function -> Marker.of(
                type, DensityFunction.decode(((DensityFunctions.MarkerOrMarked) function).wrapped())
            ));
        }
        for (MappedTransformer.Transform transform : MappedTransformer.Transform.values()) {
            register(key(transform), function -> MappedTransformer.of(
                DensityFunction.decode(node(function).child(0)), transform
            ));
        }
        for (TwoArgumentSimpleFunction.Operation operation : TwoArgumentSimpleFunction.Operation.values()) {
            register(key(operation), function -> {
                DensityFunctions.TwoArgumentSimpleFunction binary =
                    (DensityFunctions.TwoArgumentSimpleFunction) function;
                return TwoArgumentSimpleFunction.of(
                    operation, DensityFunction.decode(binary.argument1()),
                    DensityFunction.decode(binary.argument2())
                );
            });
        }
        for (ShiftedFunction.Kind kind : ShiftedFunction.Kind.values()) {
            register(key(kind), function -> ShiftedFunction.of(
                NoiseParameters.decode(node(function).noise(0)), kind
            ));
        }
    }

    @Override
    protected net.minecraft.world.level.levelgen.DensityFunction normalize(net.minecraft.world.level.levelgen.DensityFunction minecraftObject) {
        net.minecraft.world.level.levelgen.DensityFunction current = minecraftObject;
        while (current instanceof DensityFunctions.HolderHolder(Holder<net.minecraft.world.level.levelgen.DensityFunction> function) && !(function instanceof Holder.Reference<?>)) {
            current = function.value();
        }
        return current;
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.DensityFunction minecraftObject) {
        if (minecraftObject instanceof DensityFunctions.HolderHolder) {
            return REFERENCE;
        }
        return Decoders.registryKey(
            BuiltInRegistries.DENSITY_FUNCTION_TYPE, minecraftObject.codec().codec()
        );
    }

    private DensityFunction reference(net.minecraft.world.level.levelgen.DensityFunction minecraftObject) {
        Holder<net.minecraft.world.level.levelgen.DensityFunction> holder =
            ((DensityFunctions.HolderHolder) minecraftObject).function();
        return ReferencedDensityFunction.of(Decoders.key(
            ((Holder.Reference<net.minecraft.world.level.levelgen.DensityFunction>) holder).key().identifier()
        ));
    }

    private DensityFunction shiftedNoise(net.minecraft.world.level.levelgen.DensityFunction minecraftObject) {
        DensityFunctionNodes.Node node = node(minecraftObject);
        net.minecraft.world.level.levelgen.DensityFunction shiftY = node.child(1);
        boolean flat = node.asDouble("y_scale") == 0.0
            && shiftY.minValue() == 0.0 && shiftY.maxValue() == 0.0;
        if (!flat) {
            throw new IllegalArgumentException(
                "Cannot decode a shifted noise that shifts on Y: y_scale=" + node.asDouble("y_scale")
                    + ", shift_y=" + shiftY + ". Wyck only wraps the two-dimensional form."
            );
        }
        return ShiftedNoise2dFunction.of(
            NoiseParameters.decode(node.noise(0)),
            DensityFunction.decode(node.child(0)), DensityFunction.decode(node.child(2)),
            node.asDouble("xz_scale")
        );
    }

    private static DensityFunctionNodes.Node node(
        net.minecraft.world.level.levelgen.DensityFunction minecraftObject
    ) {
        return DensityFunctionNodes.read(minecraftObject);
    }

    private static String key(Enum<?> type) {
        return type.name().toLowerCase(Locale.ROOT);
    }
}
