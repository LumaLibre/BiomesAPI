package dev.wyck.worldgen.carver.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A composed carver that uses a custom carver and its configuration.
 *
 * @param <C> The configuration type.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CustomComposedCarver<C> extends ConfiguredWorldCarver, Registerable<CustomComposedCarver<C>> {

    /**
     * The custom carver.
     * @return the custom carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    CustomCarver<C> carver();

    /**
     * The configuration of the custom carver.
     * @return the configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    C config();

    /**
     * Converts this composed carver to a builder.
     * @return a builder with the same values as this composed carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder<C> toBuilder() {
        return new Builder<>(this);
    }

    /**
     * Registers the custom carver.
     * @since 3.0.0
     */
    @Override
    @AsOf("3.0.0")
    default CustomComposedCarver<C> register() {
        this.carver().register();
        return this;
    }

    /**
     * Creates a new composed carver.
     * @param carver The custom carver.
     * @param config The configuration of the custom carver.
     * @return A new composed carver.
     * @param <C> The configuration type.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    static <C> CustomComposedCarver<C> of(CustomCarver<C> carver, C config) {
        record Holder() {
            static final ConstructWireProvider<CustomComposedCarver<?>> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.types.CustomComposedCarverImpl");
        }
        return (CustomComposedCarver<C>) Holder.WIRE.construct(carver, config);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @param <C> The configuration type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static <C> Builder<C> builder() {
        return new Builder<>();
    }

    /**
     * A builder for {@link CustomComposedCarver}.
     * @param <C> The configuration type
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder<C> {
        private @Nullable CustomCarver<C> carver;
        private @Nullable C config;

        public Builder() {}

        public Builder(CustomComposedCarver<C> carver) {
            this.carver = carver.carver();
            this.config = carver.config();
        }

        /**
         * Sets the custom carver.
         * @param carver the custom carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder<C> carver(CustomCarver<C> carver) {
            this.carver = carver;
            return this;
        }

        /**
         * Sets the configuration of the custom carver.
         * @param config the configuration of the custom carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder<C> config(C config) {
            this.config = config;
            return this;
        }

        /**
         * Builds the composed carver.
         * @return the composed carver
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CustomComposedCarver<C> build() {
            Preconditions.checkNotNull(carver, "carver must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(carver, config);
        }
    }
}
