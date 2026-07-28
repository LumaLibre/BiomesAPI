package dev.wyck.worldgen.feature.featuresize;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

/**
 * Specifies 2 different widths based on height. At heights lower than {@link #limit()}, {@link #lowerSize()} is used,
 * otherwise {@link #upperSize()}.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Minimum_size">Tree definition (Minimum size)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TwoLayersFeatureSize extends FeatureSize {

    /**
     * The limit between the lower and upper layer, between 0 and 81 (inclusive). Defaults to 1.
     * @return the limit
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int limit();

    /**
     * The minimum width of the tree at heights under {@link #limit()}, between 0 and 16 (inclusive). Defaults to 0.
     * @return the lower size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int lowerSize();

    /**
     * The minimum width of the tree at heights greater than or equal to {@link #limit()}, between 0 and 16 (inclusive).
     * Defaults to 1.
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
     * Creates a new two-layer feature size.
     * @param minClippedHeight the minimum free height required for the tree to generate, or {@code null} to use the trunk height
     * @param limit the limit between the lower and upper layer, between 0 and 81 (inclusive)
     * @param lowerSize the minimum width of the tree at heights under the limit, between 0 and 16 (inclusive)
     * @param upperSize the minimum width of the tree at heights greater than or equal to the limit, between 0 and 16 (inclusive)
     * @return a new two-layer feature size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwoLayersFeatureSize of(@Nullable Integer minClippedHeight, int limit, int lowerSize, int upperSize) {
        OptionalInt optionalInt = minClippedHeight == null ? OptionalInt.empty() : OptionalInt.of(minClippedHeight);
        record Holder() {
            static final ConstructWireProvider<TwoLayersFeatureSize> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.featuresize.TwoLayersFeatureSizeImpl");
        }
        return Holder.WIRE.construct(optionalInt, limit, lowerSize, upperSize);
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
     * Builder for {@link TwoLayersFeatureSize}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FeatureSizeBuilder<Builder, TwoLayersFeatureSize> {
        // codec defaults
        private int limit = 1;
        private int lowerSize = 0;
        private int upperSize = 1;

        public Builder() {}

        public Builder(TwoLayersFeatureSize other) {
            Integer nullableInt = other.minClippedHeight().isPresent() ? other.minClippedHeight().getAsInt() : null;
            super(nullableInt);
            this.limit = other.limit();
            this.lowerSize = other.lowerSize();
            this.upperSize = other.upperSize();
        }

        /**
         * Sets the limit between the lower and upper layer, between 0 and 81 (inclusive).
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
         * Sets the minimum width of the tree at heights under the limit, between 0 and 16 (inclusive).
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
         * Sets the minimum width of the tree at heights greater than or equal to the limit, between 0 and 16 (inclusive).
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
        protected TwoLayersFeatureSize create() {
            Preconditions.checkArgument(limit >= 0 && limit <= 81, "limit must be between 0 and 81");
            Preconditions.checkArgument(lowerSize >= 0 && lowerSize <= 16, "lowerSize must be between 0 and 16");
            Preconditions.checkArgument(upperSize >= 0 && upperSize <= 16, "upperSize must be between 0 and 16");
            return of(minClippedHeight, limit, lowerSize, upperSize);
        }
    }
}
