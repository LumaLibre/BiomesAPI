package dev.wyck.worldgen.valueproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Samples a {@link #source()} provider and hard-clamps the result into
 * [{@link #minInclusive()}, {@link #maxInclusive()}]. Because it clamps rather
 * than rerolls, probability mass outside the bounds piles up on the bounds, so a
 * clamped uniform is <em>not</em> uniform: the endpoints become disproportionately likely.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/int_provider">int provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ClampedInt extends IntProvider {

    /**
     * The source provider whose sampled value is clamped.
     * @return the source provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider source();

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
     * Creates a new clamped int provider.
     * @param source the source provider to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     * @return the clamped int provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ClampedInt of(IntProvider source, int min, int max) {
        record Holder() {
            static final ConstructWireProvider<ClampedInt> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.valueproviders.ClampedIntImpl");
        }
        return Holder.WIRE.construct(source, min, max);
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
     * Builder for {@link ClampedInt}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends IntProviderBuilder<ClampedInt, Builder> {
        private @Nullable IntProvider source;

        public Builder() {}

        public Builder(ClampedInt provider) {
            super(provider);
            this.source = provider.source();
        }

        /**
         * Sets the source provider to clamp.
         * @param source the source provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder source(IntProvider source) {
            this.source = source;
            return this;
        }

        @Override
        public ClampedInt build() {
            Preconditions.checkNotNull(source, "source must be set");
            return of(source, minInclusive, maxInclusive);
        }
    }
}
