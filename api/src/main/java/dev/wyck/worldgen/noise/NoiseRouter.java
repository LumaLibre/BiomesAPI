package dev.wyck.worldgen.noise;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;

/**
 * The noise router is a collection of density functions.
 * Density functions compute a value for each block position.
 * They are used for terrain generation, biome layout, aquifers, ore veins, and more.
 * A noise router is a part of a dimension's noise settings.
 *
 * @see <a href="https://minecraft.wiki/w/Noise_router">Noise router</a>
 * @since 2.4.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseRouter extends Wrapper {

    /**
     * Affects whether aquifers and open cave areas are separated; larger values make separation more likely.
     * @return the barrier density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction barrier();

    /**
     * Affects the probability of an aquifer generating liquid in a cave; larger values make liquid more likely.
     * The value is clamped to the range {@code [-1.0, 1.0]}.
     * @return the fluid level floodedness density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction fluidLevelFloodedness();

    /**
     * Affects the height of an aquifer's liquid surface at a horizontal position; smaller values favor lower heights.
     * @return the fluid level spread density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction fluidLevelSpread();

    /**
     * Affects whether an aquifer uses lava instead of water; the threshold is {@code 0.3}.
     * @return the lava density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction lava();

    /**
     * The temperature values, used for biome placement only. Like the other climate slots, this does not
     * affect terrain shape.
     * @return the temperature density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction temperature();

    /**
     * The humidity values, used for biome placement only.
     * @return the vegetation density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction vegetation();

    /**
     * The continentalness values, used for biome placement only.
     * @return the continents density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction continents();

    /**
     * The erosion values, used for biome placement and aquifer generation.
     * @return the erosion density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction erosion();

    /**
     * The depth values, used for biome placement and aquifer generation.
     * @return the depth density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction depth();

    /**
     * The weirdness values, used for biome placement only.
     * @return the ridges density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction ridges();

    /**
     * A 2D density function (sampled at {@code Y=0}) giving the Y-level of the preliminary surface,
     * generally below the actual terrain height. Used by aquifer generation and surface rules.
     * @return the preliminary surface level density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction preliminarySurfaceLevel();

    /**
     * The main density function deciding whether each position is solid or air. Where positive, the
     * default block is placed (later replaceable by surface rules); otherwise air or fluid is placed by
     * the aquifer logic.
     * @return the final density density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction finalDensity();

    /**
     * Affects ore vein type, vertical range and richness, selecting between copper and iron veins by
     * Y-level and noise value.
     * @return the vein toggle density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction veinToggle();

    /**
     * Controls which blocks belong to a vein. At or above {@code 0.0} the block is not part of a vein;
     * below {@code 0.0} it has a 30% chance to be replaced by the vein's filler or an ore block.
     * @return the vein ridged density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction veinRidged();

    /**
     * Affects which blocks in a vein become ore blocks rather than the vein's stone block.
     * @return the vein gap density function
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    DensityFunction veinGap();

    /**
     * Converts this noise router back to a builder.
     * @return a new builder with these values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new noise router from its density function slots.
     *
     * @param barrier the barrier density function
     * @param fluidLevelFloodedness the fluid level floodedness density function
     * @param fluidLevelSpread the fluid level spread density function
     * @param lava the lava density function
     * @param temperature the temperature density function
     * @param vegetation the vegetation density function
     * @param continents the continents density function
     * @param erosion the erosion density function
     * @param depth the depth density function
     * @param ridges the ridges density function
     * @param preliminarySurfaceLevel the preliminary surface level density function
     * @param finalDensity the final density density function
     * @param veinToggle the vein toggle density function
     * @param veinRidged the vein ridged density function
     * @param veinGap the vein gap density function
     * @return a new noise router
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static NoiseRouter of(
        DensityFunction barrier,
        DensityFunction fluidLevelFloodedness,
        DensityFunction fluidLevelSpread,
        DensityFunction lava,
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction preliminarySurfaceLevel,
        DensityFunction finalDensity,
        DensityFunction veinToggle,
        DensityFunction veinRidged,
        DensityFunction veinGap
    ) {
        record Holder() {
            static final ConstructWireProvider<NoiseRouter> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.NoiseRouterImpl");
        }
        return Holder.WIRE.construct(
            barrier,
            fluidLevelFloodedness,
            fluidLevelSpread,
            lava,
            temperature,
            vegetation,
            continents,
            erosion,
            depth,
            ridges,
            preliminarySurfaceLevel,
            finalDensity,
            veinToggle,
            veinRidged,
            veinGap
        );
    }

    /**
     * Reads a Minecraft noise router.
     * @param minecraftRouter the noise router to read
     * @return the decoded noise router
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static NoiseRouter decode(Object minecraftRouter) {
        record Holder() {
            static final Decoder<NoiseRouter> DECODER = Decoder.create("dev.wyck.decode.worldgen.noise.NoiseRouterDecoder");
        }
        return Holder.DECODER.decode(minecraftRouter);
    }

    /**
     * Creates a builder for a noise router.
     *
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for a {@link NoiseRouter}. Each slot defaults to {@link DensityFunction#zero()} until set.
     *
     * @since 2.4.0
     */
    @NullUnmarked
    @AsOf("2.4.0")
    final class Builder {

        private DensityFunction barrier = DensityFunction.zero();
        private DensityFunction fluidLevelFloodedness = DensityFunction.zero();
        private DensityFunction fluidLevelSpread = DensityFunction.zero();
        private DensityFunction lava = DensityFunction.zero();
        private DensityFunction temperature = DensityFunction.zero();
        private DensityFunction vegetation = DensityFunction.zero();
        private DensityFunction continents = DensityFunction.zero();
        private DensityFunction erosion = DensityFunction.zero();
        private DensityFunction depth = DensityFunction.zero();
        private DensityFunction ridges = DensityFunction.zero();
        private DensityFunction preliminarySurfaceLevel = DensityFunction.zero();
        private DensityFunction finalDensity = DensityFunction.zero();
        private DensityFunction veinToggle = DensityFunction.zero();
        private DensityFunction veinRidged = DensityFunction.zero();
        private DensityFunction veinGap = DensityFunction.zero();

        public Builder() {}

        public Builder(NoiseRouter router) {
            this.barrier = router.barrier();
            this.fluidLevelFloodedness = router.fluidLevelFloodedness();
            this.fluidLevelSpread = router.fluidLevelSpread();
            this.lava = router.lava();
            this.temperature = router.temperature();
            this.vegetation = router.vegetation();
            this.continents = router.continents();
            this.erosion = router.erosion();
            this.depth = router.depth();
            this.ridges = router.ridges();
            this.preliminarySurfaceLevel = router.preliminarySurfaceLevel();
            this.finalDensity = router.finalDensity();
            this.veinToggle = router.veinToggle();
            this.veinRidged = router.veinRidged();
            this.veinGap = router.veinGap();
        }

        /**
         * Sets the {@linkplain NoiseRouter#barrier() barrier} slot.
         * @param barrier the barrier density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder barrier(DensityFunction barrier) {
            this.barrier = barrier;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#fluidLevelFloodedness() fluid level floodedness} slot.
         * @param fluidLevelFloodedness the fluid level floodedness density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder fluidLevelFloodedness(DensityFunction fluidLevelFloodedness) {
            this.fluidLevelFloodedness = fluidLevelFloodedness;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#fluidLevelSpread() fluid level spread} slot.
         * @param fluidLevelSpread the fluid level spread density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder fluidLevelSpread(DensityFunction fluidLevelSpread) {
            this.fluidLevelSpread = fluidLevelSpread;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#lava() lava} slot.
         * @param lava the lava density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder lava(DensityFunction lava) {
            this.lava = lava;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#temperature() temperature} slot.
         * @param temperature the temperature density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder temperature(DensityFunction temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#vegetation() vegetation} slot.
         * @param vegetation the vegetation density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder vegetation(DensityFunction vegetation) {
            this.vegetation = vegetation;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#continents() continents} slot.
         * @param continents the continents density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder continents(DensityFunction continents) {
            this.continents = continents;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#erosion() erosion} slot.
         * @param erosion the erosion density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder erosion(DensityFunction erosion) {
            this.erosion = erosion;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#depth() depth} slot.
         * @param depth the depth density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder depth(DensityFunction depth) {
            this.depth = depth;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#ridges() ridges} slot.
         * @param ridges the ridges density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder ridges(DensityFunction ridges) {
            this.ridges = ridges;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#preliminarySurfaceLevel() preliminary surface level} slot.
         * @param preliminarySurfaceLevel the preliminary surface level density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder preliminarySurfaceLevel(DensityFunction preliminarySurfaceLevel) {
            this.preliminarySurfaceLevel = preliminarySurfaceLevel;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#finalDensity() final density} slot.
         * @param finalDensity the final density density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder finalDensity(DensityFunction finalDensity) {
            this.finalDensity = finalDensity;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#veinToggle() vein toggle} slot.
         * @param veinToggle the vein toggle density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder veinToggle(DensityFunction veinToggle) {
            this.veinToggle = veinToggle;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#veinRidged() vein ridged} slot.
         * @param veinRidged the vein ridged density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder veinRidged(DensityFunction veinRidged) {
            this.veinRidged = veinRidged;
            return this;
        }

        /**
         * Sets the {@linkplain NoiseRouter#veinGap() vein gap} slot.
         * @param veinGap the vein gap density function
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder veinGap(DensityFunction veinGap) {
            this.veinGap = veinGap;
            return this;
        }

        /**
         * Builds the noise router.
         * @return the noise router
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public NoiseRouter build() {
            return of(
                this.barrier,
                this.fluidLevelFloodedness,
                this.fluidLevelSpread,
                this.lava,
                this.temperature,
                this.vegetation,
                this.continents,
                this.erosion,
                this.depth,
                this.ridges,
                this.preliminarySurfaceLevel,
                this.finalDensity,
                this.veinToggle,
                this.veinRidged,
                this.veinGap
            );
        }
    }
}
