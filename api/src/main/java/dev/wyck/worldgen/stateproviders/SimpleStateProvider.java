package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

/**
 * A block state provider that always
 * returns the same block state.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#simple_state_provider">Block state provider (simple state provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface SimpleStateProvider extends BlockStateProvider {

    /**
     * The block attached to this provider.
     * @return the block state
     * @since 3.0.0
     */
    BlockData state();

    /**
     * Creates a new simple state provider.
     * @param state the block state
     * @return a new simple state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleStateProvider of(BlockData state) {
        record Holder() {
            static final ConstructWireProvider<SimpleStateProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.SimpleStateProviderImpl");
        }
        return Holder.WIRE.construct(state);
    }

    /**
     * Creates a new simple state provider.
     * @param material the material of the block
     * @return a new simple state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleStateProvider of(Material material) {
        return of(BukkitBootstrapUtil.util().createBlockData(material));
    }
}
