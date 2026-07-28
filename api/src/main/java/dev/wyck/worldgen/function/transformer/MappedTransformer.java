package dev.wyck.worldgen.function.transformer;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@AsOf("3.0.0")
public interface MappedTransformer extends PureTransformer {

    /**
     * The transformation to apply to the input value.
     * @return the transformation
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Transform transformation();

    /**
     * Creates a new mapped density function.
     * @param resourceKey the resource key, or null
     * @param input the input density function
     * @param transformation the transformation to apply to the input value
     * @return the mapped density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer of(@Nullable ResourceKey resourceKey, DensityFunction input, Transform transformation) {
        record Holder() {
            static final ConstructWireProvider<MappedTransformer> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.transformer.MappedTransformerImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), input, transformation);
    }

    /**
     * Creates a new mapped density function.
     * @param input the input density function
     * @param transformation the transformation to apply to the input value
     * @return the mapped density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer of(DensityFunction input, Transform transformation) {
        return of(null, input, transformation);
    }

    /**
     * Convenience method for {@link #of(DensityFunction, Transform)}.
     * @param transformation the transformation to apply to the input value
     * @param input the input density function
     * @return the mapped density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer of(Transform transformation, DensityFunction input) {
        return of(input, transformation);
    }

    /**
     * Maps the input through {@link Transform#ABS}, yielding its absolute value.
     * @param input the input density function
     * @return a mapped density function producing {@code |input|}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer abs(DensityFunction input) {
        return of(Transform.ABS, input);
    }

    /**
     * Maps the input through {@link Transform#SQUARE}, yielding its square.
     * @param input the input density function
     * @return a mapped density function producing {@code input * input}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer square(DensityFunction input) {
        return of(Transform.SQUARE, input);
    }

    /**
     * Maps the input through {@link Transform#CUBE}, yielding its cube.
     * @param input the input density function
     * @return a mapped density function producing {@code input * input * input}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer cube(DensityFunction input) {
        return of(Transform.CUBE, input);
    }

    /**
     * Maps the input through {@link Transform#HALF_NEGATIVE}, halving only its negative values.
     * @param input the input density function
     * @return a mapped density function producing {@code input} where positive and {@code input * 0.5} where negative
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer halfNegative(DensityFunction input) {
        return of(Transform.HALF_NEGATIVE, input);
    }

    /**
     * Maps the input through {@link Transform#QUARTER_NEGATIVE}, quartering only its negative values.
     * @param input the input density function
     * @return a mapped density function producing {@code input} where positive and {@code input * 0.25} where negative
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer quarterNegative(DensityFunction input) {
        return of(Transform.QUARTER_NEGATIVE, input);
    }

    /**
     * Maps the input through {@link Transform#INVERT}, negating it.
     * @param input the input density function
     * @return a mapped density function producing {@code -input}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer invert(DensityFunction input) {
        return of(Transform.INVERT, input);
    }

    /**
     * Maps the input through {@link Transform#SQUEEZE}, applying the vanilla squeeze curve.
     * @param input the input density function
     * @return a mapped density function producing the squeezed value of {@code input}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MappedTransformer squeeze(DensityFunction input) {
        return of(Transform.SQUEEZE, input);
    }

    /**
     * Single-argument value transforms applied to a density function's output.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    enum Transform {
        ABS,
        SQUARE,
        CUBE,
        HALF_NEGATIVE,
        QUARTER_NEGATIVE,
        INVERT,
        SQUEEZE
    }
}
