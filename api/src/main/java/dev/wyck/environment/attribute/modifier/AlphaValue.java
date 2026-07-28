package dev.wyck.environment.attribute.modifier;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * The argument of {@link AttributeOperation#ALPHA_BLEND} on a float attribute.
 *
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface AlphaValue extends Wrapper {

    /**
     * The value
     * @return the value
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    float value();

    /**
     * The alpha value between 0.0 and 1.0
     * @return the alpha value
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    float alpha();

    /**
     * Converts it to a builder.
     * @return a new builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new AlphaValue
     * @param value the value
     * @param alpha the alpha value between 0.0 and 1.0
     * @return a new AlphaValue
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static AlphaValue of(float value, float alpha) {
        Preconditions.checkArgument(alpha >= 0.0f && alpha <= 1.0f, "alpha must be between 0.0 and 1.0");
        record Holder() {
            static final ConstructWireProvider<AlphaValue> WIRE = ConstructWireProvider.create("dev.wyck.environment.attribute.modifier.AlphaValueImpl");
        }
        return Holder.WIRE.construct(value, alpha);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link AlphaValue}.
     * @since 3.2.0
     * @version 3.2.0
     * @author Jsinco
     */
    @AsOf("3.2.0")
    final class Builder {
        private float value;
        private float alpha;

        public Builder() {}

        public Builder(AlphaValue alphaValue) {
            this.value = alphaValue.value();
            this.alpha = alphaValue.alpha();
        }

        /**
         * Sets the value of the AlphaValue.
         * @param value the value of the AlphaValue
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder value(float value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the alpha value of the AlphaValue.
         * @param alpha the alpha value of the AlphaValue
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        /**
         * Builds the AlphaValue.
         * @return the built AlphaValue
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public AlphaValue build() {
            return of(value, alpha);
        }
    }
}