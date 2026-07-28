package dev.wyck.worldgen.carver;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.worldgen.feature.configurations.ProbabilityFeatureConfiguration;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Wraps Minecraft's CarverConfiguration class.
 *
 * @see <a href="https://minecraft.wiki/w/Carver_definition">Carver definition</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface CarverConfiguration extends ProbabilityFeatureConfiguration {

    /**
     * @return the y-level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    HeightProvider y();

    /**
     * @return the y-scale of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    FloatProvider yScale();

    /**
     * @return the y-level of the lava level of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    VerticalAnchor lavaLevel();

    /**
     * @return the debug settings of the carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    CarverDebugSettings debugSettings();

    /**
     * @return the materials that can be replaced by this carver
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Set<Material> replaceable();

    /**
     * Reads the configuration owned by a Minecraft configured carver.
     * @param minecraftConfiguredCarver the configured carver to read
     * @return its carver configuration
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static CarverConfiguration decode(Object minecraftConfiguredCarver) {
        record Holder() {
            static final Decoder<CarverConfiguration> DECODER = Decoder.create("dev.wyck.decode.worldgen.carver.CarverConfigurationDecoders");
        }
        return Holder.DECODER.decode(minecraftConfiguredCarver);
    }

    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class CarverConfigurationBuilder<T extends CarverConfiguration, B extends CarverConfigurationBuilder<T, B>> {
        protected float probability;
        protected @Nullable HeightProvider y;
        protected @Nullable FloatProvider yScale;
        protected @Nullable VerticalAnchor lavaLevel;
        protected @Nullable CarverDebugSettings debugSettings;
        protected Set<Material> replaceable = new HashSet<>();

        protected CarverConfigurationBuilder() {}

        protected CarverConfigurationBuilder(CarverConfiguration configuration) {
            this.probability = configuration.probability();
            this.y = configuration.y();
            this.yScale = configuration.yScale();
            this.lavaLevel = configuration.lavaLevel();
            this.debugSettings = configuration.debugSettings();
            this.replaceable.addAll(configuration.replaceable());
        }

        /**
         * Sets the probability of the carver.
         * @param probability the probability of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B probability(float probability) {
            this.probability = probability;
            return (B) this;
        }

        /**
         * Sets the y-level of the carver.
         * @param y the y-level of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B y(HeightProvider y) {
            this.y = y;
            return (B) this;
        }

        /**
         * Sets the y-scale of the carver.
         * @param yScale the y-scale of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B yScale(FloatProvider yScale) {
            this.yScale = yScale;
            return (B) this;
        }

        /**
         * Sets the lava level of the carver.
         * @param lavaLevel the lava level of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B lavaLevel(VerticalAnchor lavaLevel) {
            this.lavaLevel = lavaLevel;
            return (B) this;
        }

        /**
         * Sets the debug settings of the carver.
         * @param debugSettings the debug settings of the carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B debugSettings(CarverDebugSettings debugSettings) {
            this.debugSettings = debugSettings;
            return (B) this;
        }

        /**
         * Sets the materials that can be replaced by this carver.
         * @param replaceable the materials that can be replaced by this carver
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B replaceable(Set<Material> replaceable) {
            this.replaceable = replaceable;
            return (B) this;
        }

        // Friendly builder methods

        /**
         * Adds a material to the list of replaceable materials.
         * @param replaceable the material to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public B replaceable(Material... replaceable) {
            Collections.addAll(this.replaceable, replaceable);
            return (B) this;
        }

        /**
         * Builds the carver configuration.
         * @return the carver configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final T build() {
            Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0 and 1.0");
            Preconditions.checkNotNull(y, "y must be set");
            Preconditions.checkNotNull(yScale, "yScale must be set");
            Preconditions.checkNotNull(lavaLevel, "lavaLevel must be set");
            Preconditions.checkNotNull(debugSettings, "debugSettings must be set");
            Preconditions.checkNotNull(replaceable, "replaceable must be set");
            return create();
        }

        protected abstract T create();
    }
}
