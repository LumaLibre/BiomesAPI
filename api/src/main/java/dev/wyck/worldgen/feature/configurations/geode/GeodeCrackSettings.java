package dev.wyck.worldgen.feature.configurations.geode;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * The configuration of the crack on a geode, which forms the opening exposing its interior.
 *
 * @see <a href="https://minecraft.wiki/w/Amethyst_Geode">Amethyst Geode</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface GeodeCrackSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<GeodeCrackSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.geode.GeodeCrackSettingsImpl");

    @ApiStatus.Internal
    Decoder<GeodeCrackSettings> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.geode.GeodeCrackSettingsDecoder");

    /**
     * The probability for generating a crack. Value between 0.0 and 1.0.
     * @return the crack generation chance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double generateCrackChance();

    /**
     * The base size of the crack. Value between 0.0 and 5.0.
     * @return the base crack size
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    double baseCrackSize();

    /**
     * The offset of the crack point. Value between 0 and 10.
     * @return the crack point offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int crackPointOffset();

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
     * Creates a new geode crack settings.
     * @param generateCrackChance the probability for generating a crack
     * @param baseCrackSize the base size of the crack
     * @param crackPointOffset the offset of the crack point
     * @return a new geode crack settings
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeCrackSettings of(double generateCrackChance, double baseCrackSize, int crackPointOffset) {
        return WIRE.construct(generateCrackChance, baseCrackSize, crackPointOffset);
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

    @ApiStatus.Internal
    static GeodeCrackSettings decode(Object minecraftSettings) {
        return DECODER.decode(minecraftSettings);
    }

    /**
     * Builder for {@link GeodeCrackSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private double generateCrackChance = 1.0;
        private double baseCrackSize = 2.0;
        private int crackPointOffset = 2;

        public Builder() {}

        public Builder(GeodeCrackSettings settings) {
            this.generateCrackChance = settings.generateCrackChance();
            this.baseCrackSize = settings.baseCrackSize();
            this.crackPointOffset = settings.crackPointOffset();
        }

        /**
         * Sets the probability for generating a crack.
         * @param generateCrackChance the crack generation chance
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder generateCrackChance(double generateCrackChance) {
            this.generateCrackChance = generateCrackChance;
            return this;
        }

        /**
         * Sets the base size of the crack.
         * @param baseCrackSize the base crack size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder baseCrackSize(double baseCrackSize) {
            this.baseCrackSize = baseCrackSize;
            return this;
        }

        /**
         * Sets the offset of the crack point.
         * @param crackPointOffset the crack point offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder crackPointOffset(int crackPointOffset) {
            this.crackPointOffset = crackPointOffset;
            return this;
        }

        /**
         * Builds the settings.
         * @return the settings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public GeodeCrackSettings build() {
            Preconditions.checkArgument(generateCrackChance >= 0.0 && generateCrackChance <= 1.0, "generateCrackChance must be between 0.0 and 1.0");
            Preconditions.checkArgument(baseCrackSize >= 0.0 && baseCrackSize <= 5.0, "baseCrackSize must be between 0.0 and 5.0");
            Preconditions.checkArgument(crackPointOffset >= 0 && crackPointOffset <= 10, "crackPointOffset must be between 0 and 10");
            return of(generateCrackChance, baseCrackSize, crackPointOffset);
        }
    }
}
