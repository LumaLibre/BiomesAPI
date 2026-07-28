package dev.wyck.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Uniform int providers sample a range between the inclusive values
 * and return a single, random integer from the range.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface UniformInt extends IntProvider {

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
     * Creates a new uniform int provider.
     * @param min the minimum value
     * @param max the maximum value
     * @return the uniform int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UniformInt of(int min, int max) {
        record Holder() {
            static final ConstructWireProvider<UniformInt> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.UniformIntImpl");
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
     * Builder for {@link UniformInt}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends IntProviderBuilder<UniformInt, Builder> {

        public Builder() {}

        public Builder(UniformInt provider) {
            super(provider);
        }

        /**
         * Creates a new uniform int provider.
         * @return the uniform int provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public UniformInt build() {
            return of(minInclusive, maxInclusive);
        }
    }
}
