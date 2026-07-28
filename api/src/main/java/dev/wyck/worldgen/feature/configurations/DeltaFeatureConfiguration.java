package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A one-block-deep sheet of constrained lava found among the terrain of basalt deltas biome in the Nether.
 *
 * @see <a href="https://minecraft.wiki/w/Delta">Delta</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DeltaFeatureConfiguration extends FeatureConfiguration {

    /**
     * The block to use on the inside of the delta.
     * @return the contents block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData contents();

    /**
     * The block to use for the rim of the delta.
     * @return the rim block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData rim();

    /**
     * The size of the inside of the delta. Value between 0 and 16.
     * @return the size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider size();

    /**
     * The size of the rim of the delta. Value between 0 and 16.
     * @return the rim size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider rimSize();

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
     * Creates a new delta feature configuration.
     * @param contents the block to use on the inside of the delta
     * @param rim the block to use for the rim of the delta
     * @param size the size of the inside of the delta
     * @param rimSize the size of the rim of the delta
     * @return a new delta feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DeltaFeatureConfiguration of(BlockData contents, BlockData rim, IntProvider size, IntProvider rimSize) {
        record Holder() {
            static final ConstructWireProvider<DeltaFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.DeltaFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(contents, rim, size, rimSize);
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
     * Builder for {@link DeltaFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData contents;
        private @Nullable BlockData rim;
        private @Nullable IntProvider size;
        private @Nullable IntProvider rimSize;

        public Builder() {}

        public Builder(DeltaFeatureConfiguration configuration) {
            this.contents = configuration.contents();
            this.rim = configuration.rim();
            this.size = configuration.size();
            this.rimSize = configuration.rimSize();
        }

        /**
         * Sets the block to use on the inside of the delta.
         * @param contents the contents block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder contents(BlockData contents) {
            this.contents = contents;
            return this;
        }

        /**
         * Sets the block to use for the rim of the delta.
         * @param rim the rim block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rim(BlockData rim) {
            this.rim = rim;
            return this;
        }

        /**
         * Sets the size of the inside of the delta.
         * @param size the size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder size(IntProvider size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the size of the rim of the delta.
         * @param rimSize the rim size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder rimSize(IntProvider rimSize) {
            this.rimSize = rimSize;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public DeltaFeatureConfiguration build() {
            Preconditions.checkNotNull(contents, "contents must be set");
            Preconditions.checkNotNull(rim, "rim must be set");
            Preconditions.checkNotNull(size, "size must be set");
            Preconditions.checkNotNull(rimSize, "rimSize must be set");
            Preconditions.checkArgument(size.minInclusive() >= 0 && size.maxInclusive() <= 16, "size must be between 0 and 16");
            Preconditions.checkArgument(rimSize.minInclusive() >= 0 && rimSize.maxInclusive() <= 16, "rimSize must be between 0 and 16");
            return of(contents, rim, size, rimSize);
        }
    }
}