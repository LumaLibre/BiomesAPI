package dev.wyck.worldgen.function.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Samples a noise.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseFunction extends NoiseParameterFunction {

    /**
     * The xz scale of the noise function.
     * @return the xz scale
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double xzScale();

    /**
     * The y scale of the noise function.
     * @return the y scale
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double yScale();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default AbstractBuilder<?> toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new noise function.
     * @param resourceKey the resource key, or null
     * @param noiseParameters the parameters of the noise
     * @param xzScale the xz scale of the noise function
     * @param yScale the y scale of the noise function
     * @return a new noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseFunction of(@Nullable ResourceKey resourceKey, NoiseParameters noiseParameters, double xzScale, double yScale) {
        record Holder() {
            static final ConstructWireProvider<NoiseFunction> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.noise.NoiseFunctionImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), noiseParameters, xzScale, yScale);
    }

    /**
     * Creates a new noise function.
     * @param noiseParameters the parameters of the noise
     * @param xzScale the xz scale of the noise function
     * @param yScale the y scale of the noise function
     * @return a new noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseFunction of(NoiseParameters noiseParameters, double xzScale, double yScale) {
        return of(null, noiseParameters, xzScale, yScale);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * The shared base for {@link NoiseFunction} builders.
     * @param <B> the concrete builder subtype
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    abstract class AbstractBuilder<B extends AbstractBuilder<B>> {
        protected @Nullable ResourceKey resourceKey;
        protected @Nullable NoiseParameters noiseParameters;
        protected double xzScale = 1.0;
        protected double yScale = 1.0;

        protected AbstractBuilder() {}

        protected AbstractBuilder(NoiseFunction function) {
            this.resourceKey = function.resourceKey().orElse(null);
            this.noiseParameters = function.noiseParameters();
            this.xzScale = function.xzScale();
            this.yScale = function.yScale();
        }

        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }

        /**
         * Sets the resource key.
         * @param resourceKey the resource key
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return self();
        }

        /**
         * Sets the parameters of the noise.
         * @param noiseParameters the parameters of the noise
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B noiseParameters(NoiseParameters noiseParameters) {
            this.noiseParameters = noiseParameters;
            return self();
        }

        /**
         * Sets the xz scale of the noise function.
         * @param xzScale the xz scale of the noise function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B xzScale(double xzScale) {
            this.xzScale = xzScale;
            return self();
        }

        /**
         * Sets the y scale of the noise function.
         * @param yScale the y scale of the noise function
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B yScale(double yScale) {
            this.yScale = yScale;
            return self();
        }

        /**
         * Builds the noise function.
         * @return the noise function
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public abstract NoiseFunction build();
    }

    /**
     * Builder for {@link NoiseFunction}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends AbstractBuilder<Builder> {

        private Builder() {}

        private Builder(NoiseFunction function) {
            super(function);
        }

        @Override
        @AsOf("3.0.0")
        public NoiseFunction build() {
            Preconditions.checkNotNull(noiseParameters, "noiseParameters must be set");
            return of(resourceKey, noiseParameters, xzScale, yScale);
        }
    }
}
