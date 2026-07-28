package dev.wyck.environment.attribute.modifier;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * The argument of {@link AttributeOperation#BLEND_TO_GRAY} on a color attribute.
 *
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface GrayBlend extends Wrapper {

    /**
     * The brightness of the gray color.
     * @return the brightness of the gray color
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    float brightness();

    /**
     * The factor of the gray color.
     * @return the factor of the gray color
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    float factor();

    /**
     * Creates a new gray blend.
     * @param brightness the brightness of the gray color
     * @param factor the factor of the gray color
     * @return a new gray blend
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static GrayBlend of(float brightness, float factor) {
        Preconditions.checkArgument(brightness >= 0.0f && brightness <= 1.0f, "brightness must be between 0.0 and 1.0");
        Preconditions.checkArgument(factor >= 0.0f && factor <= 1.0f, "factor must be between 0.0 and 1.0");
        record Holder() {
            static final ConstructWireProvider<GrayBlend> WIRE = ConstructWireProvider.create("dev.wyck.environment.attribute.modifier.GrayBlendImpl");
        }
        return Holder.WIRE.construct(brightness, factor);
    }

    /**
     * Builder for {@link GrayBlend}.
     * @since 3.2.0
     * @version 3.2.0
     * @author Jsinco
     */
    @AsOf("3.2.0")
    final class Builder {
        private float brightness;
        private float factor;

        public Builder() {}

        public Builder(GrayBlend grayBlend) {
            this.brightness = grayBlend.brightness();
            this.factor = grayBlend.factor();
        }

        /**
         * Sets the brightness of the gray color.
         * @param brightness the brightness of the gray color
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder brightness(float brightness) {
            this.brightness = brightness;
            return this;
        }

        /**
         * Sets the factor of the gray color.
         * @param factor the factor of the gray color
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder factor(float factor) {
            this.factor = factor;
            return this;
        }

        /**
         * Builds the gray blend.
         * @return the gray blend
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public GrayBlend build() {
            return of(brightness, factor);
        }
    }
}