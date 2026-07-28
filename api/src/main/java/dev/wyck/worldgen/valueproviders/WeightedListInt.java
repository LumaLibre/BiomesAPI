package dev.wyck.worldgen.valueproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.WeightedList;
import org.jspecify.annotations.NullMarked;

/**
 * A two-stage provider: an entry is picked from the {@link #distribution()}
 * proportionally to its weight, then that entry's provider is sampled.
 * The reported bounds aggregate the min/max across every entry.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface WeightedListInt extends IntProvider {

    /**
     * The weighted pool of providers to sample from.
     * @return the weighted distribution
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    WeightedList<IntProvider> distribution();

    @Override
    @AsOf("3.0.0")
    default int minInclusive() {
        int min = Integer.MAX_VALUE;
        for (WeightedList.Weighted<IntProvider> entry : this.distribution().unwrap()) {
            min = Math.min(min, entry.value().minInclusive());
        }
        return min;
    }

    @Override
    @AsOf("3.0.0")
    default int maxInclusive() {
        int max = Integer.MIN_VALUE;
        for (WeightedList.Weighted<IntProvider> entry : this.distribution().unwrap()) {
            max = Math.max(max, entry.value().maxInclusive());
        }
        return max;
    }

    /**
     * Creates a new weighted list int provider.
     * @param distribution the weighted pool of providers, which must not be empty
     * @return the weighted list int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedListInt of(WeightedList<IntProvider> distribution) {
        Preconditions.checkNotNull(distribution, "distribution");
        Preconditions.checkArgument(!distribution.unwrap().isEmpty(), "distribution must not be empty");
        record Holder() {
            static final ConstructWireProvider<WeightedListInt> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.WeightedListIntImpl");
        }
        return Holder.WIRE.construct(distribution);
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
     * Builder for {@link WeightedListInt}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final WeightedList.Builder<IntProvider> distribution = WeightedList.builder();

        public Builder() {}

        /**
         * Adds a provider with a weight of one.
         * @param provider the provider to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder add(IntProvider provider) {
            this.distribution.add(provider);
            return this;
        }

        /**
         * Adds a provider with the given weight.
         * @param provider the provider to add
         * @param weight the selection weight
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder add(IntProvider provider, int weight) {
            this.distribution.add(provider, weight);
            return this;
        }

        @AsOf("3.0.0")
        public WeightedListInt build() {
            return of(this.distribution.build());
        }
    }
}
