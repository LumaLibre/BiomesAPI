package dev.wyck.worldgen.surface.condition;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A Paper surface condition that follows the vanilla bedrock gradient unless flat bedrock generation
 * is enabled for the world. When enabled, the two anchors collapse to a single floor or roof layer.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
@ApiStatus.Experimental
public interface OptionallyFlatBedrockConditionSource extends ConditionSource {

    @ApiStatus.Internal
    ConstructWireProvider<OptionallyFlatBedrockConditionSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.condition.OptionallyFlatBedrockConditionSourceImpl");

    /**
     * Gets the seed name used to randomize the non-flat gradient.
     * @return the random name
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    String randomName();

    /**
     * Gets the anchor at and below which the condition passes.
     * @return the lower anchor
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    VerticalAnchor trueAtAndBelow();

    /**
     * Gets the anchor at and above which the condition fails.
     * @return the upper anchor
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    VerticalAnchor falseAtAndAbove();

    /**
     * Gets whether the collapsed flat layer belongs to the dimension roof.
     * @return whether this condition creates roof bedrock
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    boolean roof();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new optionally-flat bedrock condition source.
     * @param randomName the seed used to randomize the gradient
     * @param trueAtAndBelow the anchor at and below which the condition passes
     * @param falseAtAndAbove the anchor at and above which the condition fails
     * @param roof whether the collapsed flat layer belongs to the dimension roof
     * @return the optionally-flat bedrock condition source
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static OptionallyFlatBedrockConditionSource of(String randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove, boolean roof) {
        return WIRE.construct(randomName, trueAtAndBelow, falseAtAndAbove, roof);
    }

    /**
     * Creates a new optionally-flat bedrock condition source builder.
     * @return a new builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OptionallyFlatBedrockConditionSource}.
     *
     * @since 3.3.0
     * @version 3.3.0
     * @author Jsinco
     */
    @AsOf("3.3.0")
    final class Builder {
        private @Nullable String randomName;
        private @Nullable VerticalAnchor trueAtAndBelow;
        private @Nullable VerticalAnchor falseAtAndAbove;
        private boolean roof;

        public Builder() {}

        public Builder(OptionallyFlatBedrockConditionSource source) {
            this.randomName = source.randomName();
            this.trueAtAndBelow = source.trueAtAndBelow();
            this.falseAtAndAbove = source.falseAtAndAbove();
            this.roof = source.roof();
        }

        /**
         * Sets the seed name used to randomize the non-flat gradient.
         * @param randomName the random name
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder randomName(String randomName) {
            this.randomName = randomName;
            return this;
        }

        /**
         * Sets the anchor at and below which the condition passes.
         * @param trueAtAndBelow the lower anchor
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder trueAtAndBelow(VerticalAnchor trueAtAndBelow) {
            this.trueAtAndBelow = trueAtAndBelow;
            return this;
        }

        /**
         * Sets the anchor at and above which the condition fails.
         * @param falseAtAndAbove the upper anchor
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder falseAtAndAbove(VerticalAnchor falseAtAndAbove) {
            this.falseAtAndAbove = falseAtAndAbove;
            return this;
        }

        /**
         * Sets whether the collapsed flat layer belongs to the dimension roof.
         * @param roof whether this condition creates roof bedrock
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder roof(boolean roof) {
            this.roof = roof;
            return this;
        }

        /**
         * Builds the optionally-flat bedrock condition source.
         * @return the optionally-flat bedrock condition source
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public OptionallyFlatBedrockConditionSource build() {
            Preconditions.checkNotNull(this.randomName, "randomName must be set");
            Preconditions.checkNotNull(this.trueAtAndBelow, "trueAtAndBelow must be set");
            Preconditions.checkNotNull(this.falseAtAndAbove, "falseAtAndAbove must be set");
            return of(this.randomName, this.trueAtAndBelow, this.falseAtAndAbove, this.roof);
        }
    }
}
