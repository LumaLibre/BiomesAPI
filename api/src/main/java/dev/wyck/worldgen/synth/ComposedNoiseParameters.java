package dev.wyck.worldgen.synth;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents noise parameters that are composed of a first octave and a list of amplitudes.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ComposedNoiseParameters extends NoiseParameters, Registerable<ComposedNoiseParameters> {

    /**
     * The first octave of the noise parameters.
     * @return the first octave of the noise parameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    int firstOctave();

    /**
     * The amplitudes of the noise parameters.
     * @return the amplitudes of the noise parameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    List<Double> amplitudes();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new NoiseParameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new NoiseParameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(@Nullable ResourceKey resourceKey, int firstOctave, List<Double> amplitudes) {
        record Holder() {
            static final ConstructWireProvider<ComposedNoiseParameters> WIRE = WireProvider.construct("dev.wyck.worldgen.synth.ComposedNoiseParametersImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), firstOctave, amplitudes);
    }

    /**
     * Creates a new NoiseParameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new NoiseParameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(int firstOctave, List<Double> amplitudes) {
        return of(null, firstOctave, amplitudes);
    }

    /**
     * Creates a new NoiseParameters.
     * @param firstOctave the first octave of the noise parameters
     * @param amplitudes the amplitudes of the noise parameters
     * @return a new NoiseParameters
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ComposedNoiseParameters of(int firstOctave, double... amplitudes) {
        return of(firstOctave, Arrays.stream(amplitudes).boxed().toList());
    }

    /**
     * Creates a new builder for NoiseParameters.
     * @return a new builder for NoiseParameters
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for NoiseParameters, which is a first octave plus amplitudes.
     *
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private int firstOctave;
        private List<Double> amplitudes = new ArrayList<>();

        private Builder() {}

        private Builder(ComposedNoiseParameters parameters) {
            this.resourceKey = parameters.resourceKey().orElse(null);
            this.firstOctave = parameters.firstOctave();
            this.amplitudes = new ArrayList<>(parameters.amplitudes());
        }

        /**
         * Sets the resource key of the noise parameters.
         * @param resourceKey the resource key of the noise parameters
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the first octave of the noise parameters.
         * @param firstOctave the first octave of the noise parameters
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder firstOctave(int firstOctave) {
            this.firstOctave = firstOctave;
            return this;
        }

        /**
         * Adds amplitude to the noise parameters.
         * @param amplitude the amplitude to add
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitude(double amplitude) {
            this.amplitudes.add(amplitude);
            return this;
        }

        /**
         * Sets the amplitudes of the noise parameters.
         * @param amplitudes the amplitudes of the noise parameters
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitudes(Collection<Double> amplitudes) {
            Preconditions.checkNotNull(amplitudes, "amplitudes cannot be null");
            this.amplitudes = new ArrayList<>(amplitudes);
            return this;
        }

        /**
         * Sets the amplitudes of the noise parameters.
         * @param amplitudes the amplitudes of the noise parameters
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder amplitudes(double... amplitudes) {
            this.amplitudes = new ArrayList<>(amplitudes.length);
            for (double amplitude : amplitudes) {
                this.amplitudes.add(amplitude);
            }
            return this;
        }

        /**
         * Builds the NoiseParameters.
         * @return the NoiseParameters
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public ComposedNoiseParameters build() {
            return of(resourceKey, firstOctave, amplitudes);
        }
    }
}
