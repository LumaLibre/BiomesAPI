package dev.wyck.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.GrassColorModifier;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.internal.FriendlyColorUtil;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Various biome-specific visuals as they appear in vanilla.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BiomeSpecialEffects extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<BiomeSpecialEffects> WIRE = ConstructWireProvider.create("dev.wyck.biome.BiomeSpecialEffectsImpl");

    @ApiStatus.Internal
    Decoder<BiomeSpecialEffects> DECODER = Decoder.create("dev.wyck.decode.biome.BiomeSpecialEffectsDecoder");

    BiomeSpecialEffects DEFAULT = of(0x3F75C4, null, null, null, GrassColorModifier.NONE);

    /**
     * The color of water in a biome.
     * @return the water color of the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int waterColor();

    /**
     * The color of leaves in a biome.
     * @return the foliage color override of the biome, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<Integer> foliageColorOverride();

    /**
     * The color of dry foliage such as leaf litter in a biome.
     * @return the dry foliage color override of the biome, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<Integer> dryFoliageColorOverride();

    /**
     * The color of grass in a biome.
     * @return the grass color override of the biome, if present
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<Integer> grassColorOverride();

    /**
     * The tint modifier for grass colors in a biome.
     * @return the grass color modifier of the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    GrassColorModifier grassColorModifier();

    /**
     * Converts this object back to a builder.
     * @return a new BiomeSpecialEffects builder from this instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new BiomeSpecialEffects instance.
     * @param waterColor the water color of the biome
     * @param foliageColorOverride the foliage color override of the biome, or null for none
     * @param dryFoliageColorOverride the dry foliage color override of the biome, or null for none
     * @param grassColorOverride the grass color override of the biome, or null for none
     * @param grassColorModifier the grass color modifier of the biome
     * @return a new BiomeSpecialEffects instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @NullUnmarked
    static BiomeSpecialEffects of(int waterColor, Integer foliageColorOverride, Integer dryFoliageColorOverride, Integer grassColorOverride, @NonNull GrassColorModifier grassColorModifier) {
        return WIRE.construct(waterColor, Optional.ofNullable(foliageColorOverride), Optional.ofNullable(dryFoliageColorOverride), Optional.ofNullable(grassColorOverride), grassColorModifier);
    }

    /**
     * Creates a new BiomeSpecialEffects instance.
     * @param waterColor the water color of the biome, as a hex string
     * @param foliageColorOverride the foliage color override of the biome, or null for none, as a hex string
     * @param dryFoliageColorOverride the dry foliage color override of the biome, or null for none, as a hex string
     * @param grassColorOverride the grass color override of the biome, or null for none, as a hex string
     * @param grassColorModifier the grass color modifier of the biome
     * @return a new BiomeSpecialEffects instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BiomeSpecialEffects of(String waterColor, @Nullable String foliageColorOverride, @Nullable String dryFoliageColorOverride, @Nullable String grassColorOverride, GrassColorModifier grassColorModifier) {
        return of(FriendlyColorUtil.hex(waterColor), FriendlyColorUtil.hexOrNull(foliageColorOverride), FriendlyColorUtil.hexOrNull(dryFoliageColorOverride), FriendlyColorUtil.hexOrNull(grassColorOverride), grassColorModifier);
    }

    /**
     * Creates a new BiomeSpecialEffects builder.
     * @return a new BiomeSpecialEffects builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads Minecraft biome special effects into a wrapper.
     * @param minecraftSpecialEffects the special effects to read
     * @return the wrapper for them
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BiomeSpecialEffects decode(Object minecraftSpecialEffects) {
        return DECODER.decode(minecraftSpecialEffects);
    }

    /**
     * A builder for creating BiomeSpecialEffects instances.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int waterColor = 0x3F75C4;
        private @Nullable Integer foliageColorOverride;
        private @Nullable Integer dryFoliageColorOverride;
        private @Nullable Integer grassColorOverride;
        private GrassColorModifier grassColorModifier = GrassColorModifier.NONE;

        public Builder() {}

        public Builder(BiomeSpecialEffects other) {
            this.waterColor = other.waterColor();
            this.foliageColorOverride = other.foliageColorOverride().orElse(null);
            this.dryFoliageColorOverride = other.dryFoliageColorOverride().orElse(null);
            this.grassColorOverride = other.grassColorOverride().orElse(null);
            this.grassColorModifier = other.grassColorModifier();
        }

        /**
         * Sets the water color of the biome.
         * @param waterColor the water color of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColorOverride the foliage color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageColorOverride(@Nullable Integer foliageColorOverride) {
            this.foliageColorOverride = foliageColorOverride;
            return this;
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColorOverride the dry foliage color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder dryFoliageColorOverride(@Nullable Integer dryFoliageColorOverride) {
            this.dryFoliageColorOverride = dryFoliageColorOverride;
            return this;
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColorOverride the grass color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder grassColorOverride(@Nullable Integer grassColorOverride) {
            this.grassColorOverride = grassColorOverride;
            return this;
        }

        /**
         * Sets the grass color modifier of the biome.
         * @param grassColorModifier the grass color modifier of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder grassColorModifier(GrassColorModifier grassColorModifier) {
            this.grassColorModifier = grassColorModifier;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the water color of the biome.
         * @param waterColor the water color of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder waterColor(String waterColor) {
            this.waterColor = FriendlyColorUtil.hex(waterColor);
            return this;
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColorOverride the foliage color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageColorOverride(@Nullable String foliageColorOverride) {
            this.foliageColorOverride = FriendlyColorUtil.hexOrNull(foliageColorOverride);
            return this;
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColorOverride the dry foliage color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder dryFoliageColorOverride(@Nullable String dryFoliageColorOverride) {
            this.dryFoliageColorOverride = FriendlyColorUtil.hexOrNull(dryFoliageColorOverride);
            return this;
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColorOverride the grass color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder grassColorOverride(@Nullable String grassColorOverride) {
            this.grassColorOverride = FriendlyColorUtil.hexOrNull(grassColorOverride);
            return this;
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColor the foliage color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageColor(@Nullable Integer foliageColor) {
            return foliageColorOverride(foliageColor);
        }

        /**
         * Sets the foliage color override of the biome.
         * @param foliageColor the foliage color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder foliageColor(@Nullable String foliageColor) {
            return foliageColorOverride(foliageColor);
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColor the dry foliage color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder dryFoliageColor(@Nullable Integer dryFoliageColor) {
            return dryFoliageColorOverride(dryFoliageColor);
        }

        /**
         * Sets the dry foliage color override of the biome.
         * @param dryFoliageColor the dry foliage color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder dryFoliageColor(@Nullable String dryFoliageColor) {
            return dryFoliageColorOverride(dryFoliageColor);
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColor the grass color override of the biome
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder grassColor(@Nullable Integer grassColor) {
            return grassColorOverride(grassColor);
        }

        /**
         * Sets the grass color override of the biome.
         * @param grassColor the grass color override of the biome, as a hex string
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder grassColor(@Nullable String grassColor) {
            return grassColorOverride(grassColor);
        }

        /**
         * Builds a new BiomeSpecialEffects instance.
         * @return a new BiomeSpecialEffects instance
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public BiomeSpecialEffects build() {
            return of(waterColor, foliageColorOverride, dryFoliageColorOverride, grassColorOverride, grassColorModifier);
        }
    }
}
