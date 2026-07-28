package dev.wyck.worldgen.chunk;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.biome.BiomeSource;
import dev.wyck.worldgen.chunk.flat.FlatLevelGeneratorSettings;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A flat level source.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FlatLevelSource extends ChunkGenerator {

    /**
     * The settings.
     * @return the settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FlatLevelGeneratorSettings settings();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new flat level source.
     * @param biomeSource the biome source
     * @param settings the settings
     * @return a new flat level source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLevelSource of(BiomeSource biomeSource, FlatLevelGeneratorSettings settings) {
        record Holder() {
            static final ConstructWireProvider<FlatLevelSource> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.chunk.FlatLevelSourceImpl");
        }
        return Holder.WIRE.construct(biomeSource, settings);
    }

    /**
     * Creates a new flat level source with a fixed biome source.
     * @param settings the settings
     * @return a new flat level source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLevelSource of(FlatLevelGeneratorSettings settings) {
        return of(BiomeSource.fixed(settings.biome()), settings);
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
     * Builder for {@link FlatLevelSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BiomeSource biomeSource;
        private @Nullable FlatLevelGeneratorSettings settings;

        public Builder() {}

        public Builder(FlatLevelSource other) {
            this.biomeSource = other.biomeSource();
            this.settings = other.settings();
        }

        /**
         * Sets the biome source.
         * @param biomeSource the biome source
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder biomeSource(BiomeSource biomeSource) {
            this.biomeSource = biomeSource;
            return this;
        }

        /**
         * Sets the settings.
         * @param settings the settings
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder settings(FlatLevelGeneratorSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Builds the flat level source.
         * @return the flat level source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FlatLevelSource build() {
            Preconditions.checkNotNull(settings, "settings must be set");
            return biomeSource != null ? of(biomeSource, settings): of(settings);
        }
    }
}
