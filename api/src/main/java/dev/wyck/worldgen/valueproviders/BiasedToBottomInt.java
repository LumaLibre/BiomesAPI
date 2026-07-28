package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A provider that is similar to a {@link UniformInt},
 * but has a bias toward {@link #minInclusive()}.
 * Internally, it is <code>min + random(random(max - min + 1) + 1)</code>.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Internal
public interface BiasedToBottomInt extends IntProvider {

    /**
     * Creates a new builder from this provider.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new biased to bottom int provider.
     * @param min the minimum value
     * @param max the maximum value
     * @return the biased to bottom int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiasedToBottomInt of(int min, int max) {
        record Holder() {
            static final ConstructWireProvider<BiasedToBottomInt> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.BiasedToBottomIntImpl");
        }
        return Holder.WIRE.construct(min, max);
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
     * Builder for {@link BiasedToBottomInt}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends IntProviderBuilder<BiasedToBottomInt, Builder> {

        public Builder() {}

        public Builder(BiasedToBottomInt provider) {
            super(provider);
        }

        @Override
        public BiasedToBottomInt build() {
            return of(minInclusive, maxInclusive);
        }
    }
}
