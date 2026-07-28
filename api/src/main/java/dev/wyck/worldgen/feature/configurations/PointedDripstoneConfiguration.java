package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Dripstone feature found in caves.
 *
 * @apiNote This is {@link SpeleothemConfiguration} in 26.2+.
 * @see <a href="https://minecraft.wiki/w/Dripstone_(feature)">Dripstone (feature)</a>
 * @since 3.1.0
 * @version 3.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.1.0")
public interface PointedDripstoneConfiguration extends FeatureConfiguration {

    /**
     * Probability for double-block dripstone between 0.0 and 1.0.
     * @return the probability for double-block dripstone
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    float chanceOfTallerDripstone();

    /**
     * Probability that the dripstone spreads in a horizontal direction between 0.0 and 1.0.
     * @return the probability for horizontal spread
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    float chanceOfDirectionalSpread();

    /**
     * Probability of horizontal spread by two blocks between 0.0 and 1.0.
     * @return the probability for horizontal spread by two blocks
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    float chanceOfSpreadRadius2();

    /**
     * After the spread by two blocks, the probability of spreading the third block
     * between 0.0 and 1.0.
     * @return the probability for third block spread
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    float chanceOfSpreadRadius3();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new pointed dripstone configuration.
     * @param chanceOfTallerDripstone chance of double-block dripstone between 0.0 and 1.0
     * @param chanceOfDirectionalSpread chance of horizontal spread between 0.0 and 1.0
     * @param chanceOfSpreadRadius2 chance of horizontal spread by two blocks between 0.0 and 1.0
     * @param chanceOfSpreadRadius3 after the spread by two blocks, the probability of spreading the third block between 0.0 and 1.0
     * @return a new pointed dripstone configuration
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static PointedDripstoneConfiguration of(float chanceOfTallerDripstone, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
        record Holder() {
            static final ConstructWireProvider<PointedDripstoneConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.PointedDripstoneConfigurationImpl");
        }
        return Holder.WIRE.construct(chanceOfTallerDripstone, chanceOfDirectionalSpread, chanceOfSpreadRadius2, chanceOfSpreadRadius3);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PointedDripstoneConfiguration}.
     * @since 3.1.0
     * @version 3.1.0
     * @author Jsinco
     */
    @AsOf("3.1.0")
    final class Builder {
        private float chanceOfTallerDripstone = 0.2F;
        private float chanceOfDirectionalSpread = 0.7F;
        private float chanceOfSpreadRadius2 = 0.5F;
        private float chanceOfSpreadRadius3 = 0.5F;

        public Builder() {}

        public Builder(PointedDripstoneConfiguration configuration) {
            this.chanceOfTallerDripstone = configuration.chanceOfTallerDripstone();
            this.chanceOfDirectionalSpread = configuration.chanceOfDirectionalSpread();
            this.chanceOfSpreadRadius2 = configuration.chanceOfSpreadRadius2();
            this.chanceOfSpreadRadius3 = configuration.chanceOfSpreadRadius3();
        }

        /**
         * Sets the chance of double-block dripstone.
         * @param chanceOfTallerDripstone the chance of double-block dripstone
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder chanceOfTallerDripstone(float chanceOfTallerDripstone) {
            this.chanceOfTallerDripstone = chanceOfTallerDripstone;
            return this;
        }

        /**
         * Sets the chance of horizontal spread.
         * @param chanceOfDirectionalSpread the chance of horizontal spread
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder chanceOfDirectionalSpread(float chanceOfDirectionalSpread) {
            this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
            return this;
        }

        /**
         * Sets the chance of horizontal spread by two blocks.
         * @param chanceOfSpreadRadius2 the chance of horizontal spread by two blocks
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder chanceOfSpreadRadius2(float chanceOfSpreadRadius2) {
            this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
            return this;
        }

        /**
         * Sets the chance of spreading the third block.
         * @param chanceOfSpreadRadius3 the chance of spreading the third block
         * @return this builder
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public Builder chanceOfSpreadRadius3(float chanceOfSpreadRadius3) {
            this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.1.0
         */
        @AsOf("3.1.0")
        public PointedDripstoneConfiguration build() {
            Preconditions.checkArgument(chanceOfTallerDripstone >= 0.0F && chanceOfTallerDripstone <= 1.0F, "chanceOfTallerDripstone must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfDirectionalSpread >= 0.0F && chanceOfDirectionalSpread <= 1.0F, "chanceOfDirectionalSpread must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfSpreadRadius2 >= 0.0F && chanceOfSpreadRadius2 <= 1.0F, "chanceOfSpreadRadius2 must be between 0.0 and 1.0");
            Preconditions.checkArgument(chanceOfSpreadRadius3 >= 0.0F && chanceOfSpreadRadius3 <= 1.0F, "chanceOfSpreadRadius3 must be between 0.0 and 1.0");
            return of(chanceOfTallerDripstone, chanceOfDirectionalSpread, chanceOfSpreadRadius2, chanceOfSpreadRadius3);
        }
    }
}