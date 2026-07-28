package dev.wyck.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

/**
 * Configuration for cave carvers.
 *
 * @see <a href="https://minecraft.wiki/w/Carver_definition#cave_and_nether_cave">Carver definition (cave and nether cave)</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface CaveCarverConfiguration extends CarverConfiguration {

    /**
     * The horizontal radius multiplier.
     * @return the horizontal radius multiplier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider horizontalRadiusMultiplier();

    /**
     * The vertical radius multiplier.
     * @return the vertical radius multiplier
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider verticalRadiusMultiplier();

    /**
     * The floor level of the cave.
     * @return the floor level of the cave
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider floorLevel();

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
     * Creates a new CaveCarverConfiguration.
     * @param probability the probability of the carver spawning
     * @param y the height provider
     * @param yScale the scale of the height provider
     * @param lavaLevel the y-level of the lava level of the carver
     * @param debugSettings the debug settings of the carver
     * @param replaceable the materials that can be replaced by this carver
     * @param horizontalRadiusMultiplier the horizontal radius multiplier
     * @param verticalRadiusMultiplier the vertical radius multiplier
     * @param floorLevel the floor level of the cave
     * @return a new CaveCarverConfiguration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CaveCarverConfiguration of(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Set<Material> replaceable,
        FloatProvider horizontalRadiusMultiplier,
        FloatProvider verticalRadiusMultiplier,
        FloatProvider floorLevel
    ) {
        record Holder() {
            static final ConstructWireProvider<CaveCarverConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.CaveCarverConfigurationImpl");
        }
        return Holder.WIRE.construct(probability, y, yScale, lavaLevel, debugSettings, replaceable, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CaveCarverConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends CarverConfigurationBuilder<CaveCarverConfiguration, Builder> {
        private @Nullable FloatProvider horizontalRadiusMultiplier;
        private @Nullable FloatProvider verticalRadiusMultiplier;
        private @Nullable FloatProvider floorLevel;

        public Builder() {}

        public Builder(CaveCarverConfiguration configuration) {
            super(configuration);
            this.horizontalRadiusMultiplier = configuration.horizontalRadiusMultiplier();
            this.verticalRadiusMultiplier = configuration.verticalRadiusMultiplier();
            this.floorLevel = configuration.floorLevel();
        }

        /**
         * Sets the horizontal radius multiplier.
         * @param horizontalRadiusMultiplier the horizontal radius multiplier
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder horizontalRadiusMultiplier(FloatProvider horizontalRadiusMultiplier) {
            this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
            return this;
        }

        /**
         * Sets the vertical radius multiplier.
         * @param verticalRadiusMultiplier the vertical radius multiplier
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder verticalRadiusMultiplier(FloatProvider verticalRadiusMultiplier) {
            this.verticalRadiusMultiplier = verticalRadiusMultiplier;
            return this;
        }

        /**
         * Sets the floor level of the cave.
         * @param floorLevel the floor level of the cave
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder floorLevel(FloatProvider floorLevel) {
            this.floorLevel = floorLevel;
            return this;
        }

        @Override
        protected CaveCarverConfiguration create() {
            Preconditions.checkNotNull(horizontalRadiusMultiplier, "horizontalRadiusMultiplier must be set");
            Preconditions.checkNotNull(verticalRadiusMultiplier, "verticalRadiusMultiplier must be set");
            Preconditions.checkNotNull(floorLevel, "floorLevel must be set");
            //noinspection ConstantConditions
            return of(probability, y, yScale, lavaLevel, debugSettings, replaceable, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel);
        }
    }
}
