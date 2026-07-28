package dev.wyck.worldgen.feature.featuresize;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

/**
 * Determines how much space needs to be available for the tree to be able to place.
 * This is done by checking a square area of varying widths at each vertical position of the tree.
 * The available free height is calculated as the height until all squares are free.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Minimum_size">Tree definition (Minimum size)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FeatureSize extends Wrapper {

    @ApiStatus.Internal
    Decoder<FeatureSize> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.tree.FeatureSizeDecoders");

    /**
     * If specified and lower than the trunk height,
     * specifies the minimum free height required for the tree to generate.
     * Otherwise, the trunk height is used instead.
     * This value must be between 0 and 80.
     * @return the minimum free height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    OptionalInt minClippedHeight();

    // Friendly static accessors

    /**
     * Creates a new two-layer feature size builder.
     * @return a new two-layer feature size builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoLayersFeatureSize.Builder twoLayers() {
        return TwoLayersFeatureSize.builder();
    }

    /**
     * Creates a new three-layer feature size builder.
     * @return a new three-layer feature size builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ThreeLayersFeatureSize.Builder threeLayers() {
        return ThreeLayersFeatureSize.builder();
    }

    @ApiStatus.Internal
    static FeatureSize decode(Object minecraftFeatureSize) {
        return DECODER.decode(minecraftFeatureSize);
    }

    /**
     * Base class for feature size builders.
     * @param <T> the builder type
     * @param <P> the feature size type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class FeatureSizeBuilder<T, P> {
        protected @Nullable Integer minClippedHeight;

        public FeatureSizeBuilder() {}

        public FeatureSizeBuilder(final @Nullable Integer minClippedHeight) {
            this.minClippedHeight = minClippedHeight;
        }

        /**
         * Sets the minimum free height.
         * @param minClippedHeight the minimum free height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T minClippedHeight(final int minClippedHeight) {
            this.minClippedHeight = minClippedHeight;
            return (T) this;
        }

        /**
         * Builds the feature size.
         * @return the feature size
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final P build() {
            if (minClippedHeight != null) {
                Preconditions.checkArgument(minClippedHeight >= 0 && minClippedHeight <= 80, "if set, minClippedHeight must be between 0 and 80");
            }
            return create();
        }

        protected abstract P create();
    }
}
