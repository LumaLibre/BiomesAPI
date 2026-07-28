package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.util.WeightedList;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

/**
 * A block state provider of weighted entries.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#weighted_state_provider">Block state provider (weighted state provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface WeightedStateProvider extends BlockStateProvider {

    /**
     * A weighted list entry of each block state.
     * @return a weighted list of block states
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    WeightedList<BlockData> entries(); // vanilla-name: weightedList

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
     * Creates a new weighted state provider.
     * @param entries the weighted list of block states
     * @return a new weighted state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static WeightedStateProvider of(WeightedList<BlockData> entries) {
        record Holder() {
            static final ConstructWireProvider<WeightedStateProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.WeightedStateProviderImpl");
        }
        return Holder.WIRE.construct(entries);
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
     * Builder for {@link WeightedStateProvider}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private final WeightedList.Builder<BlockData> builder = WeightedList.builder();

        public Builder() {}

        public Builder(WeightedStateProvider provider) {
            this.builder.addAll(provider.entries().unwrap());
        }

        /**
         * Sets the weighted list of block states.
         * @param entries the weighted list of block states
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder entries(WeightedList<BlockData> entries) {
            this.builder.addAll(entries.unwrap());
            return this;
        }

        // Friendlies

        /**
         * Adds a weighted entry to the list.
         * @param state the block state
         * @param weight the weight of the entry
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder entry(BlockData state, int weight) {
            this.builder.add(state, weight);
            return this;
        }

        /**
         * Adds a weighted entry to the list.
         * @param material the material of the block state
         * @param weight the weight of the entry
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder entry(Material material, int weight) {
            this.builder.add(BukkitBootstrapUtil.util().createBlockData(material), weight);
            return this;
        }

        /**
         * Adds multiple weighted entries to the list.
         * @param entries the weighted entries to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder entries(Map<BlockData, Integer> entries) {
            for (Map.Entry<BlockData, Integer> entry : entries.entrySet()) {
                this.builder.add(entry.getKey(), entry.getValue());
            }
            return this;
        }

        /**
         * Builds the builder.
         * @return the constructed weighted state provider
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public WeightedStateProvider build() {
            return of(this.builder.build());
        }
    }
}
