package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.feature.configurations.end.EndSpike;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The pillars around the main end island that have crystals on top of them.
 *
 * @see <a href="https://minecraft.wiki/w/End_Spike">End Spike</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface EndSpikeConfiguration extends FeatureConfiguration {

    /**
     * Whether the End crystals on top of the spikes are invulnerable.
     * @return whether the crystals are invulnerable
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean crystalInvulnerable();

    /**
     * The configuration of each spike. If empty, the default random spikes are used.
     * @return an immutable list of spikes
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<EndSpike> spikes();

    /**
     * The block position the crystal beams target, or {@code null} if unset.
     * @return the crystal beam target, or {@code null}
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @Nullable BlockVector crystalBeamTarget();

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
     * Creates a new end spike configuration.
     * @param crystalInvulnerable whether the End crystals on top of the spikes are invulnerable
     * @param spikes the configuration of each spike
     * @param crystalBeamTarget the block position the crystal beams target, or {@code null}
     * @return a new end spike configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndSpikeConfiguration of(boolean crystalInvulnerable, List<EndSpike> spikes, @Nullable BlockVector crystalBeamTarget) {
        record Holder() {
            static final ConstructWireProvider<EndSpikeConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.EndSpikeConfigurationImpl");
        }
        return Holder.WIRE.construct(crystalInvulnerable, spikes, crystalBeamTarget);
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
     * Builder for {@link EndSpikeConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private boolean crystalInvulnerable = false;
        private List<EndSpike> spikes = new ArrayList<>();
        private @Nullable BlockVector crystalBeamTarget;

        public Builder() {}

        public Builder(EndSpikeConfiguration configuration) {
            this.crystalInvulnerable = configuration.crystalInvulnerable();
            this.spikes.addAll(configuration.spikes());
            this.crystalBeamTarget = configuration.crystalBeamTarget();
        }

        /**
         * Sets whether the End crystals on top of the spikes are invulnerable.
         * @param crystalInvulnerable whether the crystals are invulnerable
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder crystalInvulnerable(boolean crystalInvulnerable) {
            this.crystalInvulnerable = crystalInvulnerable;
            return this;
        }

        /**
         * Sets the configuration of each spike, replacing any existing spikes.
         * @param spikes the spikes
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spikes(List<EndSpike> spikes) {
            this.spikes = spikes;
            return this;
        }

        /**
         * Adds one or more spikes to the configuration.
         * @param spikes the spikes to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spike(EndSpike... spikes) {
            Collections.addAll(this.spikes, spikes);
            return this;
        }

        /**
         * Sets the block position the crystal beams target.
         * @param crystalBeamTarget the crystal beam target, or {@code null} to unset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder crystalBeamTarget(@Nullable BlockVector crystalBeamTarget) {
            this.crystalBeamTarget = crystalBeamTarget;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public EndSpikeConfiguration build() {
            Preconditions.checkNotNull(spikes, "spikes must be set");
            return of(crystalInvulnerable, spikes, crystalBeamTarget);
        }
    }
}