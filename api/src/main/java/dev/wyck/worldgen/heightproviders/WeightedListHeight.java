package dev.wyck.worldgen.heightproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.WeightedList;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider which picks an entry from the {@link #distribution()}
 * proportionally to its weight, then samples that entry's provider.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface WeightedListHeight extends HeightProvider {

    @Override
    default VerticalAnchor minInclusive() {
        throw new UnsupportedOperationException();
    }

    @Override
    default VerticalAnchor maxInclusive() {
        throw new UnsupportedOperationException();
    }

    /**
     * The weighted pool of providers to sample from.
     * @return the weighted distribution
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    WeightedList<HeightProvider> distribution();

    /**
     * Creates a new builder with the same weighted distribution as this provider.
     * @return a new builder with the same values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new weighted list height provider.
     * @param distribution the weighted pool of providers, which must not be empty
     * @return the weighted list height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedListHeight of(WeightedList<HeightProvider> distribution) {
        Preconditions.checkNotNull(distribution, "distribution");
        Preconditions.checkArgument(!distribution.unwrap().isEmpty(), "distribution must not be empty");
        record Holder() {
            static final ConstructWireProvider<WeightedListHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.WeightedListHeightImpl");
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
     * Builder for {@link WeightedListHeight}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final WeightedList.Builder<HeightProvider> distribution = WeightedList.builder();

        public Builder() {}

        public Builder(WeightedListHeight provider) {
            this.distribution.addAll(provider.distribution().unwrap());
        }

        /**
         * Adds a provider with a weight of one.
         * @param provider the provider to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder add(HeightProvider provider) {
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
        public Builder add(HeightProvider provider, int weight) {
            this.distribution.add(provider, weight);
            return this;
        }

        @AsOf("3.0.0")
        public WeightedListHeight build() {
            return of(this.distribution.build());
        }
    }
}
