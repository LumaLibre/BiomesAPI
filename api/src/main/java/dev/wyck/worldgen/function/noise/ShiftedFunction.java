package dev.wyck.worldgen.function.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A noise function that has a shift.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ShiftedFunction extends NoiseParameterFunction {

    /**
     * The kind of shift.
     * @return the kind of shift
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Kind kind();

    /**
     * Creates a new shifted function.
     * @param resourceKey the resource key, or null
     * @param noiseParameters the noise parameters
     * @param kind the kind of shift
     * @return the shifted function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction of(@Nullable ResourceKey resourceKey, NoiseParameters noiseParameters, Kind kind) {
        record Holder() {
            static final ConstructWireProvider<ShiftedFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.noise.ShiftedFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), noiseParameters, kind);
    }

    /**
     * Creates a new shifted function.
     * @param noiseParameters the noise parameters
     * @param kind the kind of shift
     * @return the shifted function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction of(NoiseParameters noiseParameters, Kind kind) {
        return of(null, noiseParameters, kind);
    }

    /**
     * Samples a noise at (x/4, y/4, z/4), then multiplies it by 4.
     * @param noiseParameters the noise parameters
     * @return the shifted function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shift(NoiseParameters noiseParameters) {
        return of(noiseParameters, Kind.SHIFT);
    }

    /**
     * Samples a noise at (x/4, 0, z/4), then multiplies it by 4.
     * @param noiseParameters the noise parameters
     * @return the shifted function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shiftA(NoiseParameters noiseParameters) {
        return of(noiseParameters, Kind.SHIFT_A);
    }

    /**
     * Samples a noise at (z/4, x/4, 0), then multiplies it by 4.
     * @param noiseParameters the noise parameters
     * @return the shifted function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ShiftedFunction shiftB(NoiseParameters noiseParameters) {
        return of(noiseParameters, Kind.SHIFT_B);
    }

    /**
     * The kind of shift.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    enum Kind {
        SHIFT,
        SHIFT_A,
        SHIFT_B
    }
}
