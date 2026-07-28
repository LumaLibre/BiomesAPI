package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A feature that places sculk catalysts and sculk shriekers in
 * randomly spreading patches.
 *
 * @see <a href="https://minecraft.wiki/w/Sculk_Patch">Sculk Patch</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SculkPatchConfiguration extends FeatureConfiguration {

    /**
     * The number of charges between 1 and 32.
     * @return the charge count
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int chargeCount();

    /**
     * The initial value of each charge between 1 and 500.
     * @return the amount per charge
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int amountPerCharge();

    /**
     * The number of attempts to spread between 1 and 64.
     * @return the spread attempts
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadAttempts();

    /**
     * The number of times to generate between 0 and 8.
     * @return the growth rounds
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int growthRounds();

    /**
     * The number of times to spread between 0 and 8.
     * @return the spread rounds
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadRounds();

    /**
     * The number of extra shriekers generated.
     * @return the extra rare growths
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider extraRareGrowths();

    /**
     * The probability of generating a catalyst between 0.0 and 1.0.
     * @return the catalyst chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float catalystChance();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new sculk patch configuration.
     * @param chargeCount the charge count (1-32)
     * @param amountPerCharge the amount per charge (1-500)
     * @param spreadAttempts the spread attempts (1-64)
     * @param growthRounds the growth rounds (0-8)
     * @param spreadRounds the spread rounds (0-8)
     * @param extraRareGrowths the extra rare growths
     * @param catalystChance the catalyst chance (0.0-1.0)
     * @return a new sculk patch configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SculkPatchConfiguration of(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float catalystChance) {
        record Holder() {
            static final ConstructWireProvider<SculkPatchConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.SculkPatchConfigurationImpl");
        }
        return Holder.WIRE.construct(chargeCount, amountPerCharge, spreadAttempts, growthRounds, spreadRounds, extraRareGrowths, catalystChance);
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
     * Builder for {@link SculkPatchConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int chargeCount = 1;
        private int amountPerCharge = 1;
        private int spreadAttempts = 1;
        private int growthRounds = 0;
        private int spreadRounds = 0;
        private @Nullable IntProvider extraRareGrowths;
        private float catalystChance = 0.0F;

        public Builder() {}

        public Builder(SculkPatchConfiguration configuration) {
            this.chargeCount = configuration.chargeCount();
            this.amountPerCharge = configuration.amountPerCharge();
            this.spreadAttempts = configuration.spreadAttempts();
            this.growthRounds = configuration.growthRounds();
            this.spreadRounds = configuration.spreadRounds();
            this.extraRareGrowths = configuration.extraRareGrowths();
            this.catalystChance = configuration.catalystChance();
        }

        /**
         * Sets the charge count.
         * @param chargeCount the charge count
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder chargeCount(int chargeCount) {
            this.chargeCount = chargeCount;
            return this;
        }

        /**
         * Sets the amount per charge.
         * @param amountPerCharge the amount per charge
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder amountPerCharge(int amountPerCharge) {
            this.amountPerCharge = amountPerCharge;
            return this;
        }

        /**
         * Sets the spread attempts.
         * @param spreadAttempts the spread attempts
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadAttempts(int spreadAttempts) {
            this.spreadAttempts = spreadAttempts;
            return this;
        }

        /**
         * Sets the growth rounds.
         * @param growthRounds the growth rounds
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder growthRounds(int growthRounds) {
            this.growthRounds = growthRounds;
            return this;
        }

        /**
         * Sets the spread rounds.
         * @param spreadRounds the spread rounds
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadRounds(int spreadRounds) {
            this.spreadRounds = spreadRounds;
            return this;
        }

        /**
         * Sets the extra rare growths.
         * @param extraRareGrowths the extra rare growths
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder extraRareGrowths(IntProvider extraRareGrowths) {
            this.extraRareGrowths = extraRareGrowths;
            return this;
        }

        /**
         * Sets the catalyst chance.
         * @param catalystChance the catalyst chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder catalystChance(float catalystChance) {
            this.catalystChance = catalystChance;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public SculkPatchConfiguration build() {
            Preconditions.checkArgument(chargeCount >= 1 && chargeCount <= 32, "chargeCount must be between 1 and 32");
            Preconditions.checkArgument(amountPerCharge >= 1 && amountPerCharge <= 500, "amountPerCharge must be between 1 and 500");
            Preconditions.checkArgument(spreadAttempts >= 1 && spreadAttempts <= 64, "spreadAttempts must be between 1 and 64");
            Preconditions.checkArgument(growthRounds >= 0 && growthRounds <= 8, "growthRounds must be between 0 and 8");
            Preconditions.checkArgument(spreadRounds >= 0 && spreadRounds <= 8, "spreadRounds must be between 0 and 8");
            Preconditions.checkNotNull(extraRareGrowths, "extraRareGrowths must be set");
            Preconditions.checkArgument(catalystChance >= 0.0F && catalystChance <= 1.0F, "catalystChance must be between 0.0 and 1.0");
            return of(chargeCount, amountPerCharge, spreadAttempts, growthRounds, spreadRounds, extraRareGrowths, catalystChance);
        }
    }
}