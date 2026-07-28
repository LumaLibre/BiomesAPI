package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Abstract column feature configuration.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ColumnFeatureConfiguration extends FeatureConfiguration {

    /**
     * The reach of the column feature.
     * @return the reach of the column feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider reach();

    /**
     * The height of the column feature.
     * @return the height of the column feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider height();

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
     * Creates a new column feature configuration.
     * @param reach the reach of the column feature
     * @param height the height of the column feature
     * @return a new column feature configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ColumnFeatureConfiguration of(IntProvider reach, IntProvider height) {
        record Holder() {
            static final ConstructWireProvider<ColumnFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.ColumnFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(reach, height);
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
     * Builder for {@link ColumnFeatureConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable IntProvider reach;
        private @Nullable IntProvider height;

        public Builder() {}

        public Builder(ColumnFeatureConfiguration configuration) {
            this.reach = configuration.reach();
            this.height = configuration.height();
        }

        /**
         * Sets the reach of the column feature.
         * @param reach the reach of the column feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder reach(IntProvider reach) {
            this.reach = reach;
            return this;
        }

        /**
         * Sets the height of the column feature.
         * @param height the height of the column feature
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(IntProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Builds the column feature configuration.
         * @return the column feature configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ColumnFeatureConfiguration build() {
            Preconditions.checkNotNull(reach, "reach must be set");
            Preconditions.checkNotNull(height, "height must be set");
            Preconditions.checkArgument(reach.minInclusive() >= 0 && reach.maxInclusive() <= 3, "reach must be between 0 and 3");
            Preconditions.checkArgument(height.minInclusive() >= 1 && height.maxInclusive() <= 10, "height must be between 1 and 10");
            return of(reach, height);
        }
    }
}