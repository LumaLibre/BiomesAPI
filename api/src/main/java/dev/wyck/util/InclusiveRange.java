package dev.wyck.util;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.MinecraftUtil;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

/**
 * Represents a range of values with both ends inclusive.
 *
 * @param <T> the type of the range values
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@MinecraftUtil
public interface InclusiveRange<T extends Comparable<T>> extends Wrapper {

    /**
     * The minimum value in the range.
     * @return the minimum value in the range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    T minInclusive();

    /**
     * The maximum value in the range.
     * @return the maximum value in the range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    T maxInclusive();

    /**
     * Maps the range to a new type.
     * @param mapper the function to map the range values
     * @return a new range with the mapped values
     * @param <S> the type of the mapped values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default <S extends Comparable<S>> InclusiveRange<S> map(Function<? super T, ? extends S> mapper) {
        return of(mapper.apply(this.minInclusive()), mapper.apply(this.maxInclusive()));
    }

    /**
     * Checks if a value is within the range.
     * @param value the value to check
     * @return true if the value is within the range, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default boolean isValueInRange(T value) {
        return value.compareTo(this.minInclusive()) >= 0 && value.compareTo(this.maxInclusive()) <= 0;
    }

    /**
     * Checks if a range is within this range.
     * @param subRange the range to check
     * @return true if the range is within this range, false otherwise
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default boolean contains(InclusiveRange<T> subRange) {
        return subRange.minInclusive().compareTo(this.minInclusive()) >= 0 && subRange.maxInclusive().compareTo(this.maxInclusive()) <= 0;
    }

    /**
     * Creates a new instance.
     * @param minInclusive the minimum value in the range
     * @param maxInclusive the maximum value in the range
     * @return a new instance
     * @param <T> the type of the range values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <T extends Comparable<T>> InclusiveRange<T> of(T minInclusive, T maxInclusive) {
        record Holder() {
            static final ConstructWireProvider<InclusiveRange<?>> WIRE = ConstructWireProvider.construct("dev.wyck.util.InclusiveRangeImpl");
        }
        return (InclusiveRange<T>) Holder.WIRE.construct(minInclusive, maxInclusive);
    }

    /**
     * Creates a new instance with the same min and max values.
     * @param value the value to use for both min and max
     * @return a new instance
     * @param <T> the type of the range values
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <T extends Comparable<T>> InclusiveRange<T> of(T value) {
        return of(value, value);
    }
}
