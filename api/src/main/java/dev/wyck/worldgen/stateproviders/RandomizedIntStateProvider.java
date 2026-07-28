package dev.wyck.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Assigns a random value to an integer block property.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#randomized_int_state_provider"">Block state provider (randomized int state provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RandomizedIntStateProvider extends BlockStateProvider {

    /**
     * Another block state provider that specifies the source of the block state.
     * @return the source block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider source();

    /**
     * The name of a block property.
     * @see <a href="https://minecraft.wiki/w/Block_properties#List_of_properties"">Block properties</a>
     * @return the name of the block property
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    String property(); // IntegerProperty in vanilla

    /**
     * The value of the block property.
     * @return the value of the block property
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider values();

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
     * Creates a new randomized int block state provider.
     * @param source the source block state provider
     * @param property the name of the block property
     * @param values the value of the block property
     * @return a new randomized int block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomizedIntStateProvider of(BlockStateProvider source, String property, IntProvider values) {
        record Holder() {
            static final ConstructWireProvider<RandomizedIntStateProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.RandomizedIntStateProviderImpl");
        }
        return Holder.WIRE.construct(source, property, values);
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
     * Builder for {@link RandomizedIntStateProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockStateProvider source;
        private @Nullable String propertyName;
        private @Nullable IntProvider values;

        public Builder() {}

        public Builder(RandomizedIntStateProvider configuration) {
            this.source = configuration.source();
            this.propertyName = configuration.property();
            this.values = configuration.values();
        }

        /**
         * Sets the source block state provider.
         * @param source the source block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder source(BlockStateProvider source) {
            this.source = source;
            return this;
        }

        /**
         * Sets the value of the block property.
         * @param propertyName the name of the block property
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder property(String propertyName) {
            this.propertyName = propertyName;
            return this;
        }

        /**
         * Sets the value of the block property.
         * @param values the value of the block property
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder values(IntProvider values) {
            this.values = values;
            return this;
        }

        /**
         * Builds the randomized int block state provider.
         * @return the randomized int block state provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public RandomizedIntStateProvider build() {
            Preconditions.checkNotNull(source, "source must not be null");
            Preconditions.checkNotNull(propertyName, "propertyName must not be null");
            Preconditions.checkNotNull(values, "values must not be null");
            return of(source, propertyName, values);
        }
    }
}
