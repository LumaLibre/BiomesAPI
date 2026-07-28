package dev.wyck.worldgen.climate;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A single climate axis span, wrapping {@code Climate.Parameter}.
 *
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public interface ClimateParameter extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<ClimateParameter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.climate.ClimateParameterImpl");

    @ApiStatus.Internal
    Decoder<ClimateParameter> DECODER = Decoder.create("dev.wyck.decode.worldgen.climate.ClimateParameterDecoder");

    float MAX_BOUNDARY = 2.0f;
    float MIN_BOUNDARY = -2.0f;

    /**
     * The minimum and maximum values of this parameter between -2.0 and 2.0.
     * @return the minimum and maximum values of this parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float min();

    /**
     * The minimum and maximum values of this parameter between -2.0 and 2.0.
     * @return the minimum and maximum values of this parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float max();

    /**
     * A span between two values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a ClimateParameter between the given values
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimateParameter span(float min, float max) {
        Preconditions.checkArgument(min <= max, "min > max: %s %s", min, max);
        Preconditions.checkArgument(min >= MIN_BOUNDARY && max <= MAX_BOUNDARY, "climate values must be within [-2.0, 2.0]: %s %s", min, max);
        return WIRE.construct(min, max);
    }

    /**
     * A zero width span pinned to one value.
     *
     * @param value the value to pin to
     * @return a ClimateParameter pinned to the given value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimateParameter point(float value) {
        return span(value, value);
    }

    /**
     * A zero width span pinned to zero.
     * @return a ClimateParameter pinned to zero
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimateParameter zero() {
        return point(0.0f);
    }

    /**
     * A span between -2.0 and 2.0.
     * @return a ClimateParameter between -2.0 and 2.0
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimateParameter all() {
        return span(MIN_BOUNDARY, MAX_BOUNDARY);
    }

    /**
     * Reads a Minecraft climate parameter.
     * @param minecraftParameter the climate parameter to read
     * @return the decoded climate parameter
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ClimateParameter decode(Object minecraftParameter) {
        return DECODER.decode(minecraftParameter);
    }
}
