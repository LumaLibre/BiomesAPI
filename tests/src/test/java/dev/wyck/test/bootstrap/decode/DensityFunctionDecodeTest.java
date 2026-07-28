package dev.wyck.test.bootstrap.decode;

import com.google.gson.JsonElement;
import dev.wyck.keys.ResourceKey;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.function.misc.RangeChoice;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import dev.wyck.worldgen.function.noise.NoiseFunction;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import dev.wyck.worldgen.synth.ComposedNoiseParameters;
import dev.wyck.worldgen.synth.NoiseParameters;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.levelgen.DensityFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class DensityFunctionDecodeTest {

    private static final Set<ResourceKey> UNWRAPPED = Set.of(
        ResourceKey.minecraft("beardifier"),
        ResourceKey.minecraft("old_blended_noise"),
        ResourceKey.minecraft("interval_select"),
        ResourceKey.minecraft("spline")
    );

    private static dev.wyck.decode.worldgen.function.DensityFunctionDecoders decoders() {
        record Holder() {
            static final dev.wyck.decode.worldgen.function.DensityFunctionDecoders INSTANCE =
                new dev.wyck.decode.worldgen.function.DensityFunctionDecoders();
        }
        return Holder.INSTANCE;
    }

    private static JsonElement encode(net.minecraft.world.level.levelgen.DensityFunction function) {
        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);
        return DensityFunctions.DIRECT_CODEC.encodeStart(ops, function).getOrThrow(IllegalStateException::new);
    }

    /**
     * The point of dispatching on vanilla's own type keys: a density function type added upstream shows
     * up here as an unclaimed key rather than as a decode failure in a world that is already generating.
     */
    @Test
    void everyVanillaDensityFunctionTypeHasADecoder() {
        List<ResourceKey> missing = BuiltInRegistries.DENSITY_FUNCTION_TYPE.keySet().stream()
            .map(id -> ResourceKey.of(id.getNamespace(), id.getPath()))
            .filter(key -> !UNWRAPPED.contains(key))
            .filter(key -> !decoders().handles(key))
            .toList();

        assertTrue(missing.isEmpty(), () -> "no density function decoder is registered for: " + missing);
    }

    /** A type listed as unwrapped that has since grown a decoder should be taken off the list. */
    @Test
    void nothingListedAsUnwrappedActuallyHasADecoder() {
        List<ResourceKey> stale = UNWRAPPED.stream().filter(key -> decoders().handles(key)).toList();

        assertTrue(stale.isEmpty(), () -> "these types have a decoder and should leave the unwrapped list: " + stale);
    }

    @Test
    void decodingKeepsTheShapeOfAComposedFunction() {
        DensityFunction original = DensityFunction
            .noise(NoiseParameters.of(-7, List.of(1.0, 1.0, 2.0)), 0.25, 0.0)
            .clamp(-1.0, 1.0)
            .flatCache();

        DensityFunction decoded = DensityFunction.decode(original.asHandle());

        Marker marker = assertInstanceOf(Marker.class, decoded);
        assertEquals(Marker.Type.FLAT_CACHE, marker.type());

        ClampedTransformer clamp = assertInstanceOf(ClampedTransformer.class, marker.input());
        assertEquals(-1.0, clamp.min());
        assertEquals(1.0, clamp.max());

        NoiseFunction noise = assertInstanceOf(NoiseFunction.class, clamp.input());
        assertEquals(0.25, noise.xzScale());
        assertEquals(0.0, noise.yScale());

        ComposedNoiseParameters parameters = assertInstanceOf(ComposedNoiseParameters.class, noise.noiseParameters());
        assertEquals(-7, parameters.firstOctave());
        assertEquals(List.of(1.0, 1.0, 2.0), parameters.amplitudes());
    }

    @Test
    void decodingReadsTheFieldsOfFunctionsWyckCannotCastTo() {
        DensityFunction original = DensityFunction.rangeChoice(
            DensityFunction.yClampedGradient(-64, 320, 1.5, -1.5),
            -0.5,
            0.5,
            DensityFunction.constant(3.0),
            DensityFunction.constant(4.0)
        );

        RangeChoice decoded = assertInstanceOf(RangeChoice.class, DensityFunction.decode(original.asHandle()));
        assertEquals(-0.5, decoded.minInclusive());
        assertEquals(0.5, decoded.maxExclusive());
        assertEquals(3.0, assertInstanceOf(ConstantSimpleFunction.class, decoded.whenInRange()).value());
        assertEquals(4.0, assertInstanceOf(ConstantSimpleFunction.class, decoded.whenOutOfRange()).value());

        YClampedGradient gradient = assertInstanceOf(YClampedGradient.class, decoded.input());
        assertEquals(-64, gradient.fromY());
        assertEquals(320, gradient.toY());
        assertEquals(1.5, gradient.fromValue());
        assertEquals(-1.5, gradient.toValue());
    }

    /** A registered function stays a reference instead of being inlined into a copy of its contents. */
    @Test
    void decodingKeepsReferencesToRegisteredFunctions() {
        ResourceKey key = ResourceKey.minecraft("overworld/continents");
        DensityFunction decoded = DensityFunction.decode(DensityFunction.reference(key).asHandle());

        assertInstanceOf(ReferencedDensityFunction.class, decoded);
        assertEquals(key, decoded.resourceKey().orElseThrow());
    }

    /**
     * Decoding normalises rather than mirrors. Vanilla builds a mapped noise out of an add over a mul,
     * and that is what comes back, so a round trip is judged on what the function serializes to.
     */
    @Test
    void aNormalisedFunctionStillRoundTripsToTheSameSerializedForm() {
        DensityFunction original = DensityFunction.mappedNoise(
            NoiseParameters.reference(ResourceKey.minecraft("erosion")), 1.0, 0.0, -0.5, 0.5
        );

        DensityFunction decoded = DensityFunction.decode(original.asHandle());

        assertInstanceOf(TwoArgumentSimpleFunction.class, decoded);
        assertEquals(encode(original.asHandle()), encode(decoded.asHandle()));
    }

    /**
     * Every slot of the vanilla overworld router decodes, which walks far more shapes than a unit test
     * can name one by one. The assertion is on the type of the function that comes back rather than on
     * its serialized form, because Wyck inlines noise references on the way back to Minecraft and the two
     * therefore do not serialize alike.
     */
    @Test
    void theVanillaOverworldNoiseRouterDecodes() {
        var settings = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.NOISE_SETTINGS)
            .getOrThrow(net.minecraft.resources.ResourceKey.create(
                Registries.NOISE_SETTINGS, ResourceKey.minecraft("overworld").identifier()))
            .value();

        net.minecraft.world.level.levelgen.NoiseRouter router = settings.noiseRouter();

        for (net.minecraft.world.level.levelgen.DensityFunction slot : List.of(
            router.barrierNoise(), router.fluidLevelFloodednessNoise(), router.fluidLevelSpreadNoise(),
            router.lavaNoise(), router.temperature(), router.vegetation(), router.continents(),
            router.erosion(), router.depth(), router.ridges(), router.preliminarySurfaceLevel(),
            router.finalDensity(), router.veinToggle(), router.veinRidged(), router.veinGap()
        )) {
            DensityFunction decoded = DensityFunction.decode(slot);
            assertEquals(
                decoders().typeOf(slot),
                decoders().typeOf(decoded.asHandle()),
                () -> "slot " + slot + " decoded to a different type of function"
            );
        }
    }
}
