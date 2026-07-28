package dev.wyck.level.dimension.timeline;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.environment.attribute.modifier.AlphaValue;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.environment.attribute.modifier.GrayBlend;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.util.internal.FriendlyColorUtil;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A track that modifies an attribute.
 *
 * @param <V> the value type of the attribute
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface AttributeTrack<V> extends Wrapper {

    /**
     * The attribute this track modifies.
     * @return the attribute this track modifies
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    EnvironmentAttribute<V> attribute();

    /**
     * The operation this track performs.
     * @return the operation this track performs
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    AttributeOperation operation();

    /**
     * The easing function used for this track.
     * @return the easing function used for this track
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    Easing easing();

    /**
     * The keyframes of this track.
     * @return the keyframes of this track
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    List<Keyframe<?>> keyframes();

    /**
     * Creates a new builder for this track.
     * @return a new builder for this track
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    default Builder<V> toBuilder() {
        return new Builder<>(this);
    }

    /**
     * Creates a new attribute track.
     * @param attribute the attribute to modify
     * @param operation the operation to perform
     * @param easing the easing function to use
     * @param keyframes the keyframes of the track
     * @return a new attribute track
     * @param <V> the type of the attribute
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    @SuppressWarnings("unchecked")
    static <V> AttributeTrack<V> of(EnvironmentAttribute<V> attribute, AttributeOperation operation, Easing easing, List<Keyframe<?>> keyframes) {
        record Holder() {
            static final ConstructWireProvider<AttributeTrack<?>> WIRE = WireProvider.construct("dev.wyck.level.dimension.timeline.AttributeTrackImpl");
        }
        return (AttributeTrack<V>) Holder.WIRE.construct(attribute, operation, easing, List.copyOf(keyframes));
    }

    /**
     * Creates a new attribute track builder.
     * @return a new attribute track builder
     * @param <V> the type of the attribute
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static <V> Builder<V> builder() {
        return new Builder<>();
    }

    /**
     * Builder for {@link AttributeTrack}.
     * @param <V> the type of the attribute
     * @since 3.2.0
     * @version 3.2.0
     * @author Jsinco
     */
    @AsOf("3.2.0")
    final class Builder<V> {

        private @Nullable EnvironmentAttribute<V> attribute;
        private AttributeOperation operation = AttributeOperation.OVERRIDE;
        private Easing easing = Easing.LINEAR;
        private List<Keyframe<?>> keyframes = new ArrayList<>();

        public Builder() {}

        public Builder(AttributeTrack<V> track) {
            this.attribute = track.attribute();
            this.operation = track.operation();
            this.easing = track.easing();
            this.keyframes.addAll(track.keyframes());
        }

        /**
         * Sets the attribute of the track.
         * @param attribute the attribute of the track
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> attribute(EnvironmentAttribute<?> attribute) {
            this.attribute = (EnvironmentAttribute<V>) attribute;
            return this;
        }

        /**
         * Sets the attribute of the track.
         * @param attribute the attribute of the track
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> attribute(EnvironmentAttributeSupplier<?> attribute) {
            return attribute(attribute.get());
        }

        /**
         * Sets the operation of the track.
         * @param operation the operation of the track
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> operation(AttributeOperation operation) {
            this.operation = operation;
            return this;
        }

        /**
         * Sets the easing function of the track.
         * @param easing the easing function of the track
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> easing(Easing easing) {
            this.easing = easing;
            return this;
        }

        /**
         * Sets the keyframes of the track.
         * @param keyframes the keyframes of the track
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> keyframes(List<Keyframe<?>> keyframes) {
            this.keyframes = keyframes;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a keyframe to the track.
         * @param ticks the tick at which the keyframe should be applied
         * @param value the value of the attribute at the given tick
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> keyframe(int ticks, V value) {
            this.keyframes.add(Keyframe.of(ticks, value));
            return this;
        }

        /**
         * Adds a keyframe to a color attribute from a hex string (e.g. {@code "#FF10F0"}).
         * @param ticks the tick at which the keyframe should be applied
         * @param hex the hex string of the color at the given tick
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> keyframe(int ticks, String hex) {
            this.keyframes.add(Keyframe.of(ticks, FriendlyColorUtil.hex(hex)));
            return this;
        }

        /**
         * Adds an {@link AttributeOperation#ALPHA_BLEND} keyframe to a float attribute.
         * @param ticks the tick at which the keyframe should be applied
         * @param value the value of the attribute at the given tick
         * @param alpha the alpha value of the attribute at the given tick
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> keyframe(int ticks, float value, float alpha) {
            this.keyframes.add(Keyframe.of(ticks, AlphaValue.of(value, alpha)));
            return this;
        }

        /**
         * Adds an {@link AttributeOperation#BLEND_TO_GRAY} keyframe to a color attribute.
         * @param ticks the tick at which the keyframe should be applied
         * @param brightness the brightness of the color at the given tick
         * @param factor the factor of the color at the given tick
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder<V> grayKeyframe(int ticks, float brightness, float factor) {
            this.keyframes.add(Keyframe.of(ticks, GrayBlend.of(brightness, factor)));
            return this;
        }

        /**
         * Builds the attribute track.
         * @return the built attribute track
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public AttributeTrack<V> build() {
            Preconditions.checkNotNull(this.attribute, "attribute must be set");
            Preconditions.checkArgument(!this.keyframes.isEmpty(), "a track needs at least one keyframe");
            return of(this.attribute, this.operation, this.easing, this.keyframes);
        }
    }
}
