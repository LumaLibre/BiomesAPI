package dev.wyck.worldgen.surface.condition;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Compares the current Y position with a messy transition, just like the deepslate and bedrock
 * transitions. Positions at and below {@code trueAtAndBelow} always pass, positions at and above
 * {@code falseAtAndAbove} always fail, and the Y-coordinates between the two anchors produce a gradient.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule#Surface_conditions">Surface rule (surface conditions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface VerticalGradientConditionSource extends ConditionSource {

    /**
     * Used as a seed to randomize the gradient.
     * @return the random name
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    String randomName();

    /**
     * The lower vertical anchor. Positions at and below this always pass.
     * @return the lower anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor trueAtAndBelow();

    /**
     * The upper vertical anchor. Positions at and above this always fail.
     * @return the upper anchor
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    VerticalAnchor falseAtAndAbove();

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
     * Creates a new vertical gradient condition source.
     * @param randomName the seed used to randomize the gradient
     * @param trueAtAndBelow the anchor at and below which the condition passes
     * @param falseAtAndAbove the anchor at and above which the condition fails
     * @return the vertical gradient condition source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static VerticalGradientConditionSource of(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove) {
        record Holder() {
            static final ConstructWireProvider<VerticalGradientConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.VerticalGradientConditionSourceImpl");
        }
        return Holder.WIRE.construct(randomName, trueAtAndBelow, falseAtAndAbove);
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
     * Builder for {@link VerticalGradientConditionSource}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable String randomName;
        private @Nullable VerticalAnchor trueAtAndBelow;
        private @Nullable VerticalAnchor falseAtAndAbove;

        private Builder() {}

        private Builder(VerticalGradientConditionSource source) {
            this.randomName = source.randomName();
            this.trueAtAndBelow = source.trueAtAndBelow();
            this.falseAtAndAbove = source.falseAtAndAbove();
        }

        /**
         * Sets the seed used to randomize the gradient.
         * @param randomName the random name
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder randomName(String randomName) {
            this.randomName = randomName;
            return this;
        }

        /**
         * Sets the anchor at and below which the condition passes.
         * @param trueAtAndBelow the lower anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder trueAtAndBelow(VerticalAnchor trueAtAndBelow) {
            this.trueAtAndBelow = trueAtAndBelow;
            return this;
        }

        /**
         * Sets the anchor at and above which the condition fails.
         * @param falseAtAndAbove the upper anchor
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder falseAtAndAbove(VerticalAnchor falseAtAndAbove) {
            this.falseAtAndAbove = falseAtAndAbove;
            return this;
        }

        /**
         * Builds the vertical gradient condition source.
         * @return the vertical gradient condition source
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public VerticalGradientConditionSource build() {
            Preconditions.checkNotNull(randomName, "randomName must be set");
            Preconditions.checkNotNull(trueAtAndBelow, "trueAtAndBelow must be set");
            Preconditions.checkNotNull(falseAtAndAbove, "falseAtAndAbove must be set");
            return of(randomName, trueAtAndBelow, falseAtAndAbove);
        }
    }
}
