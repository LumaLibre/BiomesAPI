package dev.wyck.worldgen.climate;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps Minecraft's BiomeClimatePoint.
 *
 * @since 2.3.0
 * @version 2.3.0
 */
@NullMarked
@AsOf("2.3.0")
public interface ClimatePoint extends Wrapper {

    /**
     * The temperature axis span.
     * @return the temperature parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter temperature();

    /**
     * The humidity axis span.
     * @return the humidity parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter humidity();

    /**
     * The continentalness axis span.
     * @return the continentalness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter continentalness();

    /**
     * The erosion axis span.
     * @return the erosion parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter erosion();

    /**
     * The depth axis span.
     * @return the depth parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter depth();

    /**
     * The weirdness axis span.
     * @return the weirdness parameter
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ClimateParameter weirdness();

    /**
     * The constant fitness offset between 0.0 and 1.0.
     * @return the offset
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    float offset();

    /**
     * Creates a new builder with the same values as this point.
     * @return a new builder with the same values as this point
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a builder for a climate point. Unset axes span the full range.
     * @return a new builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a climate point with the given parameters.
     * @param temperature the temperature parameter
     * @param humidity the humidity parameter
     * @param continentalness the continentalness parameter
     * @param erosion the erosion parameter
     * @param depth the depth parameter
     * @param weirdness the weirdness parameter
     * @param offset the fitness offset
     * @return a new climate point
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimatePoint of(ClimateParameter temperature, ClimateParameter humidity, ClimateParameter continentalness, ClimateParameter erosion, ClimateParameter depth, ClimateParameter weirdness, float offset) {
        Preconditions.checkArgument(offset >= 0.0f && offset <= 1.0f, "offset must be within [0.0, 1.0]: %s", offset);
        record Holder() {
            static final ConstructWireProvider<ClimatePoint> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.climate.ClimatePointImpl");
        }
        return Holder.WIRE.construct(temperature, humidity, continentalness, erosion, depth, weirdness, offset);
    }

    /**
     * Creates a climate point pinned to a single value on every axis.
     * @param temperature the temperature value
     * @param humidity the humidity value
     * @param continentalness the continentalness value
     * @param erosion the erosion value
     * @param depth the depth value
     * @param weirdness the weirdness value
     * @param offset the fitness offset
     * @return a new climate point
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static ClimatePoint of(float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness, float offset) {
        return of(ClimateParameter.point(temperature), ClimateParameter.point(humidity), ClimateParameter.point(continentalness), ClimateParameter.point(erosion), ClimateParameter.point(depth), ClimateParameter.point(weirdness), offset);
    }

    /**
     * Creates a climate point that spans the full range on every axis.
     * @param offset the fitness offset
     * @return a new climate point
     * @since 2.4.1
     */
    @AsOf("2.4.1")
    static ClimatePoint spanAll(float offset) {
        return of(ClimateParameter.all(), ClimateParameter.all(), ClimateParameter.all(), ClimateParameter.all(), ClimateParameter.all(), ClimateParameter.all(), offset);
    }

    /**
     * Reads a Minecraft climate parameter point.
     * @param minecraftPoint the climate point to read
     * @return the decoded climate point
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static ClimatePoint decode(Object minecraftPoint) {
        record Holder() {
            static final Decoder<ClimatePoint> DECODER = Decoder.create("dev.wyck.decode.worldgen.climate.ClimatePointDecoder");
        }
        return Holder.DECODER.decode(minecraftPoint);
    }

    /**
     * Builder for a climate point. Any axis left unset spans the full [-2.0, 2.0] range, so it
     * matches anywhere on that axis.
     *
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @NullMarked
    @AsOf("2.3.0")
    final class Builder {

        private ClimateParameter temperature = ClimateParameter.zero();
        private ClimateParameter humidity = ClimateParameter.zero();
        private ClimateParameter continentalness = ClimateParameter.zero();
        private ClimateParameter erosion = ClimateParameter.zero();
        private ClimateParameter depth = ClimateParameter.zero();
        private ClimateParameter weirdness = ClimateParameter.zero();
        private float offset = 0.0f;

        public Builder() {}

        public Builder(ClimatePoint point) {
            this.temperature = point.temperature();
            this.humidity = point.humidity();
            this.continentalness = point.continentalness();
            this.erosion = point.erosion();
            this.depth = point.depth();
            this.weirdness = point.weirdness();
            this.offset = point.offset();
        }

        /**
         * Sets the temperature axis span.
         * @param temperature the temperature parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder temperature(ClimateParameter temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the humidity axis span.
         * @param humidity the humidity parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder humidity(ClimateParameter humidity) {
            this.humidity = humidity;
            return this;
        }

        /**
         * Sets the continentalness axis span.
         * @param continentalness the continentalness parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder continentalness(ClimateParameter continentalness) {
            this.continentalness = continentalness;
            return this;
        }

        /**
         * Sets the erosion axis span.
         * @param erosion the erosion parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder erosion(ClimateParameter erosion) {
            this.erosion = erosion;
            return this;
        }

        /**
         * Sets the depth axis span.
         * @param depth the depth parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder depth(ClimateParameter depth) {
            this.depth = depth;
            return this;
        }

        /**
         * Sets the weirdness axis span.
         * @param weirdness the weirdness parameter
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder weirdness(ClimateParameter weirdness) {
            this.weirdness = weirdness;
            return this;
        }

        /**
         * Sets the fitness offset between 0.0 and 1.0.
         * @param offset the fitness offset
         * @return this builder, for chaining
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder offset(float offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Builds the climate point.
         * @return the climate point
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public ClimatePoint build() {
            return of(this.temperature, this.humidity, this.continentalness, this.erosion, this.depth, this.weirdness, this.offset);
        }
    }
}
