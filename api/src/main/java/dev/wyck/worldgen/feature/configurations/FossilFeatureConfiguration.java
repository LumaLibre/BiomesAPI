package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A fossil is a rarely-occurring skeletal feature composed of bone blocks, coal ore above Y=0, or diamond ore below Y=-8.
 *
 * @see <a href="https://minecraft.wiki/w/Fossil">Fossil</a>
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
@ApiStatus.Experimental
public interface FossilFeatureConfiguration extends FeatureConfiguration {

    /**
     * Gets the list of resource keys for fossil structures.
     * @apiNote This value will change when Wyck wraps structures.
     * @return A list of resource keys representing fossil structures.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    List<ResourceKey> fossilStructures();

    /**
     * Gets the list of resource keys for overlay structures.
     * @apiNote This value will change when Wyck wraps structures.
     * @return A list of resource keys representing overlay structures.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    List<ResourceKey> overlayStructures();

    /**
     * Gets the resource key for fossil processors.
     * @apiNote This value will change when Wyck wraps structure processors.
     * @return A resource key representing fossil processors.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ResourceKey fossilProcessors();

    /**
     * Gets the resource key for overlay processors.
     * @apiNote This value will change when Wyck wraps structure processors.
     * @return A resource key representing overlay processors.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ResourceKey overlayProcessors();

    /**
     * Gets the maximum number of empty corners allowed in the fossil feature configuration.
     * @return The maximum number of empty corners allowed.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    int maxEmptyCornersAllowed();

    /**
     * Converts this object back to a builder.
     * @return A new builder with these values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of {@link FossilFeatureConfiguration} with the specified parameters.
     * @param fossilStructures A list of resource keys for fossil structures.
     * @param overlayStructures A list of resource keys for overlay structures.
     * @param fossilProcessors A resource key for fossil processors.
     * @param overlayProcessors A resource key for overlay processors.
     * @param maxEmptyCornersAllowed The maximum number of empty corners allowed.
     * @return A new instance of {@link FossilFeatureConfiguration}.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FossilFeatureConfiguration of(List<ResourceKey> fossilStructures, List<ResourceKey> overlayStructures, ResourceKey fossilProcessors, ResourceKey overlayProcessors, int maxEmptyCornersAllowed) {
        record Holder() {
            static final ConstructWireProvider<FossilFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.FossilFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(List.copyOf(fossilStructures), List.copyOf(overlayStructures), fossilProcessors, overlayProcessors, maxEmptyCornersAllowed);
    }

    /**
     * Creates a new builder.
     * @return A new builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FossilFeatureConfiguration}.
     * @since 3.3.0
     * @version 3.3.0
     * @author Jsinco
     */
    @AsOf("3.3.0")
    final class Builder {
        private List<ResourceKey> fossilStructures = new ArrayList<>();
        private List<ResourceKey> overlayStructures = new ArrayList<>();
        private @Nullable ResourceKey fossilProcessors;
        private @Nullable ResourceKey overlayProcessors;
        private int maxEmptyCornersAllowed;

        public Builder() {}

        public Builder(FossilFeatureConfiguration config) {
            this.fossilStructures.addAll(config.fossilStructures());
            this.overlayStructures.addAll(config.overlayStructures());
            this.fossilProcessors = config.fossilProcessors();
            this.overlayProcessors = config.overlayProcessors();
            this.maxEmptyCornersAllowed = config.maxEmptyCornersAllowed();
        }

        /**
         * Sets the fossil structures for the fossil feature configuration.
         * @param fossilStructures the fossil structures
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilStructures(List<ResourceKey> fossilStructures) {
            this.fossilStructures = fossilStructures;
            return this;
        }

        /**
         * Sets the overlay structures for the fossil feature configuration.
         * @param overlayStructures the overlay structures
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayStructures(List<ResourceKey> overlayStructures) {
            this.overlayStructures = overlayStructures;
            return this;
        }

        /**
         * Sets the fossil processors for the fossil feature configuration.
         * @param fossilProcessors the fossil processors
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilProcessors(ResourceKey fossilProcessors) {
            this.fossilProcessors = fossilProcessors;
            return this;
        }

        /**
         * Sets the overlay processors for the fossil feature configuration.
         * @param overlayProcessors the overlay processors
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayProcessors(ResourceKey overlayProcessors) {
            this.overlayProcessors = overlayProcessors;
            return this;
        }

        /**
         * Sets the maximum number of empty corners allowed.
         * @param maxEmptyCornersAllowed the maximum number of empty corners allowed
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder maxEmptyCornersAllowed(int maxEmptyCornersAllowed) {
            this.maxEmptyCornersAllowed = maxEmptyCornersAllowed;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a fossil structure to the list of fossil structures.
         * @param fossilStructure the fossil structure to add
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilStructure(ResourceKey fossilStructure) {
            this.fossilStructures.add(fossilStructure);
            return this;
        }

        /**
         * Adds an overlay structure to the list of overlay structures.
         * @param overlayStructure the overlay structure to add
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayStructure(ResourceKey overlayStructure) {
            this.overlayStructures.add(overlayStructure);
            return this;
        }

        /**
         * Builds the {@link FossilFeatureConfiguration} instance.
         * @return A new instance of {@link FossilFeatureConfiguration} with the specified parameters.
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public FossilFeatureConfiguration build() {
            Preconditions.checkNotNull(fossilProcessors, "fossilProcessors must be set");
            Preconditions.checkNotNull(overlayProcessors, "overlayProcessors must be set");
            return of(fossilStructures, overlayStructures, fossilProcessors, overlayProcessors, maxEmptyCornersAllowed);
        }
    }
}
