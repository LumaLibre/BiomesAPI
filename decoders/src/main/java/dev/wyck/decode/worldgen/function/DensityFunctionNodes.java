package dev.wyck.decode.worldgen.function;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public final class DensityFunctionNodes {

    private static final DensityFunction PLACEHOLDER_FUNCTION = DensityFunctions.zero();
    private static final DensityFunction.NoiseHolder PLACEHOLDER_NOISE =
        new DensityFunction.NoiseHolder(Holder.direct(new NormalNoise.NoiseParameters(0, List.of(1.0))));

    private DensityFunctionNodes() {
    }

    /**
     * Splits a Minecraft density function into its child functions, the noises it samples, and its own
     * scalar fields.
     *
     * @param function the Minecraft density function to read
     * @return the node, taken apart
     */
    public static Node read(DensityFunction function) {
        List<DensityFunction> children = new ArrayList<>();
        List<DensityFunction.NoiseHolder> noises = new ArrayList<>();

        DensityFunction shallow = function.mapChildren(new DensityFunction.Visitor() {

            @Override
            public DensityFunction apply(DensityFunction child) {
                children.add(child);
                return PLACEHOLDER_FUNCTION;
            }

            @Override
            public DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder noise) {
                noises.add(noise);
                return PLACEHOLDER_NOISE;
            }
        });

        return new Node(List.copyOf(children), List.copyOf(noises), encode(shallow));
    }

    private static JsonObject encode(DensityFunction function) {
        @SuppressWarnings("unchecked")
        MapCodec<DensityFunction> codec = (MapCodec<DensityFunction>) function.codec().codec();

        DynamicOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);

        JsonElement encoded = codec.codec()
            .encodeStart(ops, function)
            .getOrThrow(error -> new IllegalStateException("Cannot read the fields of " + function + ": " + error));

        return encoded.getAsJsonObject();
    }

    /**
     * One Minecraft density function, taken apart.
     *
     * @param children the node's child functions, in the order Minecraft declares them
     * @param noises the noises the node samples, in the order Minecraft declares them
     * @param fields the node's own fields, named as its codec names them
     */
    public record Node(List<DensityFunction> children, List<DensityFunction.NoiseHolder> noises, JsonObject fields) {

        public DensityFunction child(int index) {
            return this.children.get(index);
        }

        public DensityFunction.NoiseHolder noise(int index) {
            return this.noises.get(index);
        }

        public double asDouble(String field) {
            return require(field).getAsDouble();
        }

        public int asInt(String field) {
            return require(field).getAsInt();
        }

        private JsonElement require(String field) {
            JsonElement value = this.fields.get(field);
            if (value == null) {
                throw new IllegalStateException("Density function node has no '" + field + "' field, only " + this.fields.keySet());
            }
            return value;
        }
    }
}
