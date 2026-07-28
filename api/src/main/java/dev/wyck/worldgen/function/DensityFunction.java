package dev.wyck.worldgen.function;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.function.misc.EndIslands;
import dev.wyck.worldgen.function.misc.FindTopSurface;
import dev.wyck.worldgen.function.misc.Marker;
import dev.wyck.worldgen.function.misc.RangeChoice;
import dev.wyck.worldgen.function.misc.ReferencedDensityFunction;
import dev.wyck.worldgen.function.misc.YClampedGradient;
import dev.wyck.worldgen.function.noise.MappedNoiseFunction;
import dev.wyck.worldgen.function.noise.NoiseFunction;
import dev.wyck.worldgen.function.noise.ShiftedFunction;
import dev.wyck.worldgen.function.noise.ShiftedNoise2dFunction;
import dev.wyck.worldgen.function.simple.BlendAlpha;
import dev.wyck.worldgen.function.simple.BlendOffset;
import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.function.simple.TwoArgumentSimpleFunction;
import dev.wyck.worldgen.function.simple.ZeroSimpleFunction;
import dev.wyck.worldgen.function.transformer.ClampedTransformer;
import dev.wyck.worldgen.function.transformer.MappedTransformer;
import dev.wyck.worldgen.synth.NoiseParameters;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

/**
 * A density function. Either a reference to a registered one or an authored composition of other
 * density functions.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function">Density function</a>
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface DensityFunction extends Wrapper, Keyed {

    /**
     * The resource key of the density function, if present.
     * @return the resource key of the density function, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<ResourceKey> resourceKey();

    /**
     * Adds this density function to another density function.
     * @param other the density function to add to this one
     * @return a density function that is the sum of the two inputs
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default TwoArgumentSimpleFunction add(DensityFunction other) {
        return TwoArgumentSimpleFunction.add(this, other);
    }

    /**
     * Multiplies this density function by another density function.
     * @param other the density function to multiply this one by
     * @return a density function that is the product of the two inputs
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default TwoArgumentSimpleFunction mul(DensityFunction other) {
        return TwoArgumentSimpleFunction.mul(this, other);
    }

    /**
     * Returns the minimum of this density function and another density function.
     * @param other the density function to compare with
     * @return a density function that is the minimum of the two inputs
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default TwoArgumentSimpleFunction min(DensityFunction other) {
        return TwoArgumentSimpleFunction.min(this, other);
    }

    /**
     * Returns the maximum of this density function and another density function.
     * @param other the density function to compare with
     * @return a density function that is the maximum of the two inputs
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default TwoArgumentSimpleFunction max(DensityFunction other) {
        return TwoArgumentSimpleFunction.max(this, other);
    }

    /**
     * Calculates the absolute value of this density function.
     * @return a density function producing {@code |x|}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer abs() {
        return MappedTransformer.abs(this);
    }

    /**
     * Squares this density function.
     * @return a density function producing {@code x * x}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer square() {
        return MappedTransformer.square(this);
    }

    /**
     * Cubes this density function.
     * @return a density function producing {@code x * x * x}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer cube() {
        return MappedTransformer.cube(this);
    }

    /**
     * Halves this density function where it is negative, leaving it unchanged where positive.
     * @return a density function producing {@code x < 0 ? x / 2 : x}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer halfNegative() {
        return MappedTransformer.halfNegative(this);
    }

    /**
     * Quarters this density function where it is negative, leaving it unchanged where positive.
     * @return a density function producing {@code x < 0 ? x / 4 : x}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer quarterNegative() {
        return MappedTransformer.quarterNegative(this);
    }

    /**
     * Inverts this density function.
     * @return a density function producing {@code 1 / x}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer invert() {
        return MappedTransformer.invert(this);
    }

    /**
     * Clamps this density function between {@code -1} and {@code 1}, then transforms it using
     * {@code x / 2 - x * x * x / 24}.
     * @return a density function producing the squeezed value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default MappedTransformer squeeze() {
        return MappedTransformer.squeeze(this);
    }

    /**
     * Clamps this density function between two values.
     * @param min the lower bound
     * @param max the upper bound
     * @return a density function clamped to {@code [min, max]}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default ClampedTransformer clamp(double min, double max) {
        return ClampedTransformer.of(this, min, max);
    }

    /**
     * Interpolates this density function at each block in a cell based on the values of surrounding
     * cells. Often used in combination with {@link #flatCache()}.
     * @return an interpolating marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker interpolated() {
        return Marker.interpolated(this);
    }

    /**
     * Caches this density function per 4×4 column, computing it only once per column at {@code Y=0}.
     * Often used in combination with {@link #interpolated()}.
     * @return a flat-cache marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker flatCache() {
        return Marker.flatCache(this);
    }

    /**
     * Computes this density function only once per horizontal position.
     * @return a 2D-cache marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker cache2d() {
        return Marker.cache2d(this);
    }

    /**
     * Computes this density function only once per block position, even when it is referenced twice.
     * @return a cache-once marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker cacheOnce() {
        return Marker.cacheOnce(this);
    }

    /**
     * Caches this density function across a whole cell. Used by the game and should not be referenced
     * in data packs.
     * @return a cache-all-in-cell marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker cacheAllInCell() {
        return Marker.cacheAllInCell(this);
    }

    /**
     * Wraps this density function for smooth blending with chunks generated in old versions.
     * @return a blend-density marker over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Marker blendDensity() {
        return Marker.blendDensity(this);
    }

    /**
     * Uses this density function as the value to compare, returning {@code whenInRange} when it falls
     * within {@code [minInclusive, maxExclusive)} and {@code whenOutOfRange} otherwise. Essentially an
     * if-then-else statement.
     * @param minInclusive the lower bound of the range, inclusive
     * @param maxExclusive the upper bound of the range, exclusive
     * @param whenInRange the density function used when this value is inside the range
     * @param whenOutOfRange the density function used when this value is outside the range
     * @return a range choice density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default RangeChoice rangeChoice(double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return RangeChoice.of(this, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    /**
     * Scans a column of this density function and returns the topmost Y-level whose value is above 0,
     * or {@code lowerBound} if no such position exists within the bounds.
     * @param upperBound the Y-level to start the scan at, usually a 2D density function
     * @param lowerBound the Y-level to stop the scan at, and the value returned when no surface is found
     * @param cellHeight the resolution of the scan; e.g. {@code 4} checks only every 4th block
     * @return a find top surface density function over this density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default FindTopSurface findTopSurface(DensityFunction upperBound, int lowerBound, int cellHeight) {
        return FindTopSurface.of(this, upperBound, lowerBound, cellHeight);
    }

    /**
     * Creates a constant density function that always returns the given value.
     * @param value the value to return
     * @return a constant density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantSimpleFunction constant(double value) {
        return ConstantSimpleFunction.of(value);
    }

    /**
     * Samples a noise.
     * @param noiseParameters the noise to sample
     * @param xzScale scales the X and Z before sampling
     * @param yScale scales the Y before sampling
     * @return a noise density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseFunction noise(NoiseParameters noiseParameters, double xzScale, double yScale) {
        return NoiseFunction.of(noiseParameters, xzScale, yScale);
    }

    /**
     * Creates a new builder for a {@link NoiseFunction}.
     * @return a new noise function builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseFunction.Builder noise() {
        return NoiseFunction.builder();
    }

    /**
     * Samples a noise and linearly maps its output to the range {@code [minTarget, maxTarget]}.
     * @param noiseParameters the noise to sample
     * @param xzScale scales the X and Z before sampling
     * @param yScale scales the Y before sampling
     * @param minTarget the minimum value of the output
     * @param maxTarget the maximum value of the output
     * @return a mapped noise density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedNoiseFunction mappedNoise(NoiseParameters noiseParameters, double xzScale, double yScale, double minTarget, double maxTarget) {
        return MappedNoiseFunction.of(noiseParameters, xzScale, yScale, minTarget, maxTarget);
    }

    /**
     * Creates a new builder for a {@link MappedNoiseFunction}.
     * @return a new mapped noise function builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedNoiseFunction.Builder mappedNoise() {
        return MappedNoiseFunction.builder();
    }

    /**
     * Samples a noise at {@code (x/4, y/4, z/4)}, then multiplies it by 4.
     * @param noiseParameters the noise to sample
     * @return a shift density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shift(NoiseParameters noiseParameters) {
        return ShiftedFunction.shift(noiseParameters);
    }

    /**
     * Samples a noise at {@code (x/4, 0, z/4)}, then multiplies it by 4.
     * @param noiseParameters the noise to sample
     * @return a shift_a density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shiftA(NoiseParameters noiseParameters) {
        return ShiftedFunction.shiftA(noiseParameters);
    }

    /**
     * Samples a noise at {@code (z/4, x/4, 0)}, then multiplies it by 4.
     * @param noiseParameters the noise to sample
     * @return a shift_b density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shiftB(NoiseParameters noiseParameters) {
        return ShiftedFunction.shiftB(noiseParameters);
    }

    /**
     * Samples a noise like {@link #noise(NoiseParameters, double, double)}, but first shifts the input
     * coordinates on the X and Z axes.
     * @param shiftX offsets the position in the X direction
     * @param shiftZ offsets the position in the Z direction
     * @param xzScale scales the X and Z before sampling
     * @param noiseParameters the noise to sample
     * @return a shifted noise density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedNoise2dFunction shiftedNoise2d(DensityFunction shiftX, DensityFunction shiftZ, double xzScale, NoiseParameters noiseParameters) {
        return ShiftedNoise2dFunction.of(noiseParameters, shiftX, shiftZ, xzScale);
    }

    /**
     * Creates a new builder for a {@link ShiftedNoise2dFunction}.
     * @return a new shifted noise function builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedNoise2dFunction.Builder shiftedNoise2d() {
        return ShiftedNoise2dFunction.builder();
    }

    /**
     * Computes {@code input} and returns {@code whenInRange} when it falls within
     * {@code [minInclusive, maxExclusive)} and {@code whenOutOfRange} otherwise. Essentially an
     * if-then-else statement.
     * @param input the value to compare against the range
     * @param minInclusive the lower bound of the range, inclusive
     * @param maxExclusive the upper bound of the range, exclusive
     * @param whenInRange the density function used when the input is inside the range
     * @param whenOutOfRange the density function used when the input is outside the range
     * @return a range choice density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RangeChoice rangeChoice(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return RangeChoice.of(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    /**
     * Creates a new builder for a {@link RangeChoice}.
     * @return a new range choice builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RangeChoice.Builder rangeChoice() {
        return RangeChoice.builder();
    }

    /**
     * Clamps the Y coordinate between {@code fromY} and {@code toY} and then linearly maps it to the
     * range {@code [fromValue, toValue]}.
     * @param fromY the Y coordinate mapped to {@code fromValue}
     * @param toY the Y coordinate mapped to {@code toValue}
     * @param fromValue the value that {@code fromY} maps to
     * @param toValue the value that {@code toY} maps to
     * @return a y clamped gradient density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YClampedGradient yClampedGradient(int fromY, int toY, double fromValue, double toValue) {
        return YClampedGradient.of(fromY, toY, fromValue, toValue);
    }

    /**
     * Creates a new builder for a {@link YClampedGradient}.
     * @return a new y clamped gradient builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static YClampedGradient.Builder yClampedGradient() {
        return YClampedGradient.builder();
    }

    /**
     * Samples at the current position using the noise algorithm used for end islands. Its minimum value
     * is {@code -0.84375} and its maximum value is {@code 0.5625}.
     * @param seed the seed used to generate the end islands noise
     * @return an end islands density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndIslands endIslands(long seed) {
        return EndIslands.of(seed);
    }

    /**
     * The blend alpha density function, used in vanilla for smooth transitions to chunks generated in
     * old versions.
     * @return the blend alpha density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlendAlpha blendAlpha() {
        return BlendAlpha.INSTANCE;
    }

    /**
     * The blend offset density function, used in vanilla for smooth transitions to chunks generated in
     * old versions.
     * @return the blend offset density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlendOffset blendOffset() {
        return BlendOffset.INSTANCE;
    }

    /**
     * The density function that always returns zero.
     * @return the zero density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ZeroSimpleFunction zero() {
        return ZeroSimpleFunction.INSTANCE;
    }

    /**
     * Scans a column of {@code density} and returns the topmost Y-level whose value is above 0, or
     * {@code lowerBound} if no such position exists within the bounds.
     * @param density the density function to scan
     * @param upperBound the Y-level to start the scan at, usually a 2D density function
     * @param lowerBound the Y-level to stop the scan at, and the value returned when no surface is found
     * @param cellHeight the resolution of the scan; e.g. {@code 4} checks only every 4th block
     * @return a find top surface density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FindTopSurface findTopSurface(DensityFunction density, DensityFunction upperBound, int lowerBound, int cellHeight) {
        return FindTopSurface.of(density, upperBound, lowerBound, cellHeight);
    }

    /**
     * Creates a new builder for a {@link FindTopSurface}.
     * @return a new find top surface builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FindTopSurface.Builder findTopSurface() {
        return FindTopSurface.builder();
    }

    /**
     * Creates a reference to a density function already registered under the given key.
     * @param key the key of an entry in the density function registry
     * @return a reference to that density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedDensityFunction reference(ResourceKey key) {
        return ReferencedDensityFunction.of(key);
    }

    /**
     * Resolves this object's key in Minecraft's density-function registry and decodes the registered value.
     * @return the decoded density function
     * @throws IllegalStateException if this object has no resource key
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default DensityFunction wrap() {
        ResourceKey key = resourceKey().orElseThrow(() -> new IllegalStateException("Cannot wrap a density function without a resource key"));
        Object minecraft = WyckRegistry.of(RegistryId.DENSITY_FUNCTION).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Converts a Minecraft density function into a Wyck density function.
     * @param minecraftDensityFunction the Minecraft density function to convert
     * @return the Wyck density function
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static DensityFunction decode(Object minecraftDensityFunction) {
        record Holder() {
            static final Decoder<DensityFunction> DECODER = Decoder.create("dev.wyck.decode.worldgen.function.DensityFunctionDecoders");
        }
        return Holder.DECODER.decode(minecraftDensityFunction);
    }
}
