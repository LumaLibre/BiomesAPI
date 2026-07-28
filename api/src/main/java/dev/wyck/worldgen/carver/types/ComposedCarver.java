package dev.wyck.worldgen.carver.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import dev.wyck.worldgen.carver.WorldCarverType;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A composed carver that uses a world carver type and its configuration.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ComposedCarver extends ConfiguredWorldCarver, Registerable<ComposedCarver> {

    /**
     * The resource key of the carver.
     * @return the resource key of the carver
     * @since 3.0.0
     */
    Optional<ResourceKey> resourceKey();

    /**
     * The type of the carver.
     * @return the type of the carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    WorldCarverType type();

    /**
     * The configuration of the carver.
     * @return the configuration of the carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    CarverConfiguration config();

    /**
     * Converts this composed carver to a builder.
     * @return a builder with the same values as this composed carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new composed carver.
     * @param resourceKey the resource key of the carver, or null if not present
     * @param type the type of the carver
     * @param config the configuration of the carver
     * @return a new composed carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedCarver of(@Nullable ResourceKey resourceKey, WorldCarverType type, CarverConfiguration config) {
        record Holder() {
            static final ConstructWireProvider<ComposedCarver> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.types.ComposedCarverImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), type, config);
    }

    /**
     * Creates a new composed carver.
     * @param type the type of the carver
     * @param config the configuration of the carver
     * @return a new composed carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedCarver of(WorldCarverType type, CarverConfiguration config) {
        return of(null, type, config);
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

    // Friendlies

    /**
     * Creates a new builder for a cave carver.
     * @return a new builder for a cave carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder cave() {
        return new Builder(WorldCarverType.CAVE);
    }

    /**
     * Creates a new builder for a nether cave carver.
     * @return a new builder for a nether cave carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder netherCave() {
        return new Builder(WorldCarverType.NETHER_CAVE);
    }

    /**
     * Creates a new builder for a canyon carver.
     * @return a new builder for a canyon carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder canyon() {
        return new Builder(WorldCarverType.CANYON);
    }

    /**
     * Builder for {@link ComposedCarver}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private @Nullable WorldCarverType type;
        private @Nullable CarverConfiguration config;

        public Builder() {}

        public Builder(WorldCarverType type) {
            this.type = type;
        }

        public Builder(ComposedCarver carver) {
            this.resourceKey = carver.resourceKey().orElse(null);
            this.type = carver.type();
            this.config = carver.config();
        }

        /**
         * Sets the resource key of the carver.
         * @param resourceKey the resource key of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the type of the carver.
         * @param type the type of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder type(WorldCarverType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the configuration of the carver.
         * @param config the configuration of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder config(CarverConfiguration config) {
            this.config = config;
            return this;
        }

        /**
         * Builds the composed carver.
         * @return the composed carver
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ComposedCarver build() {
            Preconditions.checkNotNull(type, "type must be set");
            Preconditions.checkNotNull(config, "config must be set");
            return of(resourceKey, type, config);
        }
    }
}
