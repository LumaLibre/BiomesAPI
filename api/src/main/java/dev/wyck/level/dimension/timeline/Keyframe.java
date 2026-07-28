package dev.wyck.level.dimension.timeline;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a keyframe in a timeline.
 *
 * @param <V> The type of value stored in the keyframe.
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface Keyframe<V> extends Wrapper {

    /**
     * The number of ticks in this keyframe occurs at.
     * @return the number of ticks this keyframe occurs at
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    int ticks();

    /**
     * The value stored in this keyframe.
     * @return the value stored in this keyframe
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    V value();

    /**
     * Creates a new keyframe with the given ticks and value.
     * @param ticks the number of ticks this keyframe occurs at
     * @param value the value stored in this keyframe
     * @return a new keyframe with the given ticks and value
     * @param <V> the type of value stored in the keyframe
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    @SuppressWarnings("unchecked")
    static <V> Keyframe<V> of(int ticks, V value) {
        record Holder() {
            static final ConstructWireProvider<Keyframe<?>> WIRE = WireProvider.construct("dev.wyck.level.dimension.timeline.KeyframeImpl");
        }
        return (Keyframe<V>) Holder.WIRE.construct(ticks, value);
    }

    /**
     * Creates a new keyframe builder.
     * @param <V> the type of value stored in the keyframe
     * @since 3.2.0
     * @version 3.2.0
     * @author Jsinco
     */
    @AsOf("3.2.0")
    final class Builder<V> {
        private int ticks;
        private V value;

        public Builder() {}

        public Builder(Keyframe<V> keyframe) {
            this.ticks = keyframe.ticks();
            this.value = keyframe.value();
        }

        /**
         * Sets the number of ticks this keyframe occurs at.
         * @param ticks the number of ticks this keyframe occurs at
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> ticks(int ticks) {
            this.ticks = ticks;
            return this;
        }

        /**
         * Sets the value stored in this keyframe.
         * @param value the value stored in this keyframe
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> value(V value) {
            this.value = value;
            return this;
        }

        /**
         * Builds the keyframe.
         * @return the built keyframe
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Keyframe<V> build() {
            Preconditions.checkArgument(ticks >= 0, "ticks cannot be negative");
            Preconditions.checkNotNull(value, "value cannot be null");
            return of(ticks, value);
        }
    }
}
