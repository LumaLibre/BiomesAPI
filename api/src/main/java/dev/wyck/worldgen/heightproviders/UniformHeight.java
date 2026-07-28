package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A height provider which samples a uniformly distributed value between
 * {@link #minInclusive()} and {@link #maxInclusive()} (inclusive).
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/height_provider">Height provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface UniformHeight extends HeightProvider {

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
     * Creates a new uniform height provider.
     * @param minInclusive the vertical anchor to use as the minimum height
     * @param maxInclusive the vertical anchor to use as the maximum height
     * @return the uniform height provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformHeight of(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
        record Holder() {
            static final ConstructWireProvider<UniformHeight> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.heightproviders.UniformHeightImpl");
        }
        return Holder.WIRE.construct(minInclusive, maxInclusive);
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
     * Builder for {@link UniformHeight}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends HeightProviderBuilder<UniformHeight, Builder> {

        public Builder() {}

        public Builder(UniformHeight provider) {
            super(provider.minInclusive(), provider.maxInclusive());
        }

        @Override
        public UniformHeight build() {
            return of(minInclusive, maxInclusive);
        }
    }
}
