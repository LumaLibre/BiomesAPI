package dev.wyck.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.synth.NoiseParameters;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * Randomly chooses a block state according to a noise value.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#noise_provider">Block state provider (noise provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoiseProvider extends NoiseBasedProvider {

    /**
     * List of optional block states.
     * This cannot be empty.
     * @return the list of block states
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    List<BlockData> states();

    /**
     * Converts this object back to a builder.
     * The return type is the shared base so subtypes such as
     * {@link DualNoiseProvider} can covariantly override with their own builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new noise provider.
     * @param seed the seed
     * @param parameters the parameters
     * @param scale the scale
     * @param states the list of block states
     * @return a new noise provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static NoiseProvider of(long seed, NoiseParameters parameters, float scale, List<BlockData> states) {
        record Holder() {
            static final ConstructWireProvider<NoiseProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.NoiseProviderImpl");
        }
        return Holder.WIRE.construct(seed, parameters, scale, states);
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
     * A builder for a {@link NoiseProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends AbstractNoiseProviderBuilder<Builder, NoiseProvider> {
        private List<BlockData> states = new ArrayList<>();

        public Builder() {}

        public Builder(NoiseProvider provider) {
            super(provider);
            this.states.addAll(provider.states());
        }

        /**
         * Sets the list of optional block states.
         * @param states the list of block states
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder states(List<BlockData> states) {
            this.states = new ArrayList<>(states);
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a block state to the list of optional block states.
         * @param state the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(BlockData state) {
            this.states.add(state);
            return this;
        }

        /**
         * Adds a block state to the list of optional block states.
         * @param material the material of the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder state(Material material) {
            this.states.add(BukkitBootstrapUtil.util().createBlockData(material));
            return this;
        }

        /**
         * Builds the {@link NoiseProvider} instance.
         * @return the built noise provider
         * @since 3.0.0
         */
        @Override
        @AsOf("3.0.0")
        protected NoiseProvider create() {
            Preconditions.checkArgument(!states.isEmpty(), "states must not be empty");
            //noinspection ConstantConditions
            return of(seed, parameters, scale, states);
        }
    }
}