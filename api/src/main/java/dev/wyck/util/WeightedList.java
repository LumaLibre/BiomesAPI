package dev.wyck.util;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.MinecraftUtil;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

/**
 * A simple weighted random list.
 *
 * <p>Below {@link #FLAT_THRESHOLD} total weight it uses a flat lookup array (O(1) draw, memory
 * proportional to total weight); at or above it, a linear scan (O(n) draw, O(n) memory).
 */
@NullMarked
@AsOf("2.3.0")
@MinecraftUtil
public final class WeightedList<E> {

    private static final int FLAT_THRESHOLD = 64;

    private final int totalWeight;
    private final List<Weighted<E>> items;
    private final @Nullable Selector<E> selector; // null when totalWeight == 0

    private WeightedList(List<? extends Weighted<E>> items) {
        this.items = List.copyOf(items);
        this.totalWeight = totalWeight(this.items);
        if (this.totalWeight == 0) {
            this.selector = null;
        } else if (this.totalWeight < FLAT_THRESHOLD) {
            this.selector = new Flat<>(this.items, this.totalWeight);
        } else {
            this.selector = new Compact<>(this.items);
        }
    }

    private static <E> int totalWeight(List<Weighted<E>> items) {
        long sum = 0L;
        for (Weighted<E> item : items) {
            sum += item.weight();
        }
        if (sum > Integer.MAX_VALUE) {
            throw new IllegalStateException("Sum of weights must be <= Integer.MAX_VALUE, was " + sum);
        }
        return (int) sum;
    }

    public static <E> WeightedList<E> of() {
        return new WeightedList<>(List.of());
    }

    public static <E> WeightedList<E> of(E element) {
        return new WeightedList<>(List.of(new Weighted<>(element, 1)));
    }

    @SafeVarargs
    public static <E> WeightedList<E> of(Weighted<E>... items) {
        return new WeightedList<>(List.of(items));
    }

    public static <E> WeightedList<E> of(List<Weighted<E>> items) {
        return new WeightedList<>(items);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public Optional<E> getRandom(RandomGenerator random) {
        if (this.selector == null) {
            return Optional.empty();
        }
        return Optional.of(this.selector.get(random.nextInt(this.totalWeight)));
    }

    public E getRandomOrThrow(RandomGenerator random) {
        if (this.selector == null) {
            throw new IllegalStateException("Weighted list has no elements");
        }
        return this.selector.get(random.nextInt(this.totalWeight));
    }

    public List<Weighted<E>> unwrap() {
        return this.items;
    }

    public boolean contains(E element) {
        for (Weighted<E> weighted : this.items) {
            if (weighted.value().equals(element)) {
                return true;
            }
        }
        return false;
    }

    /** A value paired with its selection weight. */
    public record Weighted<E>(E value, int weight) {
        public Weighted {
            if (weight < 0) {
                throw new IllegalArgumentException("Weight must not be negative: " + weight);
            }
        }
    }

    public static class Builder<E> {
        protected final List<Weighted<E>> result = new ArrayList<>();

        public Builder<E> add(E element) {
            return this.add(element, 1);
        }

        public Builder<E> add(E element, int weight) {
            this.result.add(new Weighted<>(element, weight));
            return this;
        }

        public Builder<E> addAll(List<Weighted<E>> elements) {
            this.result.addAll(elements);
            return this;
        }

        public WeightedList<E> build() {
            return new WeightedList<>(this.result);
        }
    }

    private interface Selector<E> {
        E get(int index);
    }

    /** Linear scan, used when total weight is at or above {@link #FLAT_THRESHOLD}. */
    private static final class Compact<E> implements Selector<E> {
        private final List<Weighted<E>> entries;

        Compact(List<Weighted<E>> entries) {
            this.entries = entries;
        }

        @Override
        public E get(int index) {
            for (Weighted<E> weighted : this.entries) {
                index -= weighted.weight();
                if (index < 0) {
                    return weighted.value();
                }
            }
            throw new IllegalStateException(index + " exceeded total weight");
        }
    }

    /** Flat lookup table: O(1) draw at the cost of an array sized to the total weight. */
    private static final class Flat<E> implements Selector<E> {
        private final Object[] entries;

        Flat(List<Weighted<E>> entries, int size) {
            this.entries = new Object[size];
            int i = 0;
            for (Weighted<E> weighted : entries) {
                int weight = weighted.weight();
                Arrays.fill(this.entries, i, i + weight, weighted.value());
                i += weight;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public E get(int index) {
            return (E) this.entries[index];
        }
    }
}