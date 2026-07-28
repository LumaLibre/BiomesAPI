package dev.wyck.worldgen.feature.featuresize;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

/**
 * Specifies 3 different widths based on height. At heights lower than {@link #limit()}, the minimum width is
 * {@link #lowerSize()}; at the topmost {@link #upperLimit()} blocks of the trunk {@link #upperSize()} is used;
 * otherwise {@link #middleSize()} is used.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Minimum_size">Tree definition (Minimum size)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ThreeLayersFeatureSize extends FeatureSize {

    /**
     * The height of the lower layer, calculated from the bottom of the trunk, between 0 and 80 (inclusive).
     * Defaults to 1.
     * @return the limit
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int limit();

    /**
     * The height of the upper layer, calculated from the top of the trunk, between 0 and 80 (inclusive). Defaults to 1.
     * @return the upper limit
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int upperLimit();

    /**
     * The minimum width of the tree in the bottom layer (i.e. the bottom {@link #limit()} blocks of the trunk),
     * between 0 and 16 (inclusive). Defaults to 0.
     * @return the lower size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int lowerSize();

    /**
     * The minimum width of the tree at the middle layer (i.e., between the bottom and top layers), between 0 and 16
     * (inclusive). Defaults to 1.
     * @return the middle size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int middleSize();

    /**
     * The minimum width of the tree in the upper layer (i.e., the topmost {@link #upperLimit()} blocks of the trunk),
     * between 0 and 16 (inclusive). Defaults to 1.
     * @return the upper size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int upperSize();

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
     * Creates a new three-layer feature size.
     * @param minClippedHeight the minimum free height required for the tree to generate, or {@code null} to use the trunk height
     * @param limit the height of the lower layer, calculated from the bottom of the trunk, between 0 and 80 (inclusive)
     * @param upperLimit the height of the upper layer, calculated from the top of the trunk, between 0 and 80 (inclusive)
     * @param lowerSize the minimum width of the tree in the bottom layer, between 0 and 16 (inclusive)
     * @param middleSize the minimum width of the tree at the middle layer, between 0 and 16 (inclusive)
     * @param upperSize the minimum width of the tree in the upper layer, between 0 and 16 (inclusive)
     * @return a new three-layer feature size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ThreeLayersFeatureSize of(@Nullable Integer minClippedHeight, int limit, int upperLimit, int lowerSize, int middleSize, int upperSize) {
        OptionalInt optionalInt = minClippedHeight == null ? OptionalInt.empty() : OptionalInt.of(minClippedHeight);
        record Holder() {
            static final ConstructWireProvider<ThreeLayersFeatureSize> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.featuresize.ThreeLayersFeatureSizeImpl");
        }
        return Holder.WIRE.construct(optionalInt, limit, upperLimit, lowerSize, middleSize, upperSize);
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
     * Builder for {@link ThreeLayersFeatureSize}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FeatureSizeBuilder<Builder, ThreeLayersFeatureSize> {
        // codec defaults
        private int limit = 1;
        private int upperLimit = 1;
        private int lowerSize = 0;
        private int middleSize = 1;
        private int upperSize = 1;

        public Builder() {}

        public Builder(ThreeLayersFeatureSize other) {
            Integer nullableInt = other.minClippedHeight().isPresent() ? other.minClippedHeight().getAsInt() : null;
            super(nullableInt);
            this.limit = other.limit();
            this.upperLimit = other.upperLimit();
            this.lowerSize = other.lowerSize();
            this.middleSize = other.middleSize();
            this.upperSize = other.upperSize();
        }

        /**
         * Sets the height of the lower layer, calculated from the bottom of the trunk, between 0 and 80 (inclusive).
         * @param limit the limit
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        /**
         * Sets the height of the upper layer, calculated from the top of the trunk, between 0 and 80 (inclusive).
         * @param upperLimit the upper limit
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder upperLimit(int upperLimit) {
            this.upperLimit = upperLimit;
            return this;
        }

        /**
         * Sets the minimum width of the tree in the bottom layer, between 0 and 16 (inclusive).
         * @param lowerSize the lower size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lowerSize(int lowerSize) {
            this.lowerSize = lowerSize;
            return this;
        }

        /**
         * Sets the minimum width of the tree at the middle layer, between 0 and 16 (inclusive).
         * @param middleSize the middle size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder middleSize(int middleSize) {
            this.middleSize = middleSize;
            return this;
        }

        /**
         * Sets the minimum width of the tree in the upper layer, between 0 and 16 (inclusive).
         * @param upperSize the upper size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder upperSize(int upperSize) {
            this.upperSize = upperSize;
            return this;
        }

        @Override
        protected ThreeLayersFeatureSize create() {
            Preconditions.checkArgument(limit >= 0 && limit <= 80, "limit must be between 0 and 80");
            Preconditions.checkArgument(upperLimit >= 0 && upperLimit <= 80, "upperLimit must be between 0 and 80");
            Preconditions.checkArgument(lowerSize >= 0 && lowerSize <= 16, "lowerSize must be between 0 and 16");
            Preconditions.checkArgument(middleSize >= 0 && middleSize <= 16, "middleSize must be between 0 and 16");
            Preconditions.checkArgument(upperSize >= 0 && upperSize <= 16, "upperSize must be between 0 and 16");
            return of(minClippedHeight, limit, upperLimit, lowerSize, middleSize, upperSize);
        }
    }
}
