package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A feature that generates patches of surface vegetation in the crimson forest and warped forest biomes.
 *
 * @see <a href="https://minecraft.wiki/w/Nether_Forest_Vegetation">Nether Forest Vegetation</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NetherForestVegetationConfig extends FeatureConfiguration {

    /**
     * The block to use.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider stateProvider();

    /**
     * The horizonal distance to spread to.
     * The max width is <code>spread_width * 2 -1</code>.
     * Must be a positive integer.
     * @return the spread width
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadWidth();

    /**
     * The vertical distance to spread.
     * The max height is <code>spread_height * 2 -1</code>.
     * Must be a positive integer.
     * @return the spread height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadHeight();

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
     * Creates a new nether forest vegetation configuration.
     * @param stateProvider the block state provider
     * @param spreadWidth the spread width
     * @param spreadHeight the spread height
     * @return a new nether forest vegetation configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NetherForestVegetationConfig of(BlockStateProvider stateProvider, int spreadWidth, int spreadHeight) {
        record Holder() {
            static final ConstructWireProvider<NetherForestVegetationConfig> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.NetherForestVegetationConfigImpl");
        }
        return Holder.WIRE.construct(stateProvider, spreadWidth, spreadHeight);
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
     * Builder for {@link NetherForestVegetationConfig}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider stateProvider;
        private int spreadWidth = 1;
        private int spreadHeight = 1;

        public Builder() {}

        public Builder(NetherForestVegetationConfig configuration) {
            this.stateProvider = configuration.stateProvider();
            this.spreadWidth = configuration.spreadWidth();
            this.spreadHeight = configuration.spreadHeight();
        }

        /**
         * Sets the block state provider.
         * @param stateProvider the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder stateProvider(BlockStateProvider stateProvider) {
            this.stateProvider = stateProvider;
            return this;
        }

        /**
         * Sets the spread width.
         * @param spreadWidth the spread width
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadWidth(int spreadWidth) {
            this.spreadWidth = spreadWidth;
            return this;
        }

        /**
         * Sets the spread height.
         * @param spreadHeight the spread height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadHeight(int spreadHeight) {
            this.spreadHeight = spreadHeight;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NetherForestVegetationConfig build() {
            Preconditions.checkNotNull(stateProvider, "stateProvider must be set");
            Preconditions.checkArgument(spreadWidth > 0, "spreadWidth must be positive");
            Preconditions.checkArgument(spreadHeight > 0, "spreadHeight must be positive");
            return of(stateProvider, spreadWidth, spreadHeight);
        }
    }
}