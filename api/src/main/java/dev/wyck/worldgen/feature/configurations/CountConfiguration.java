package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A count configuration.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface CountConfiguration extends FeatureConfiguration {

    /**
     * The count provider.
     * @return the count provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider count();

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
     * Creates a new count configuration.
     * @param count the count provider
     * @return a new count configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CountConfiguration of(IntProvider count) {
        record Holder() {
            static final ConstructWireProvider<CountConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.CountConfigurationImpl");
        }
        return Holder.WIRE.construct(count);
    }

    /**
     * Creates a new constant count configuration.
     * @param count the count
     * @return a new count configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CountConfiguration of(int count) {
        return of(IntProvider.constant(count));
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
     * Builder for {@link CountConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable IntProvider count;

        public Builder() {}

        public Builder(CountConfiguration configuration) {
            this.count = configuration.count();
        }

        /**
         * Sets the count provider.
         * @param count the count provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder count(IntProvider count) {
            this.count = count;
            return this;
        }

        // Friendly

        /**
         * Sets the count.
         * @param count the count
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder count(int count) {
            this.count = IntProvider.constant(count);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CountConfiguration build() {
            Preconditions.checkNotNull(count, "count must be set");
            Preconditions.checkArgument(count.minInclusive() >= 0 && count.maxInclusive() <= 256, "count must be between 0 and 256");
            return of(count);
        }
    }
}