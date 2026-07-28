package dev.wyck.worldgen.stateproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

/**
 * Randomly rotates axially rotatable blocks, such as logs, chain.
 *
 * @see <a href="https://minecraft.wiki/w/Block_state_provider#rotated_block_provider">Block state provider (rotated block provider)</a>
 * @since 3.0.0
 * @version 3.0.0
 */
@NullMarked
@AsOf("3.0.0")
public interface RotatedBlockProvider extends BlockStateProvider {

    /**
     * The block state.
     * @return the block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material state(); // vanilla-name: block

    /**
     * Creates a new rotated block provider.
     * @param state the block state
     * @return a new rotated block provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RotatedBlockProvider of(Material state) {
        record Holder() {
            static final ConstructWireProvider<RotatedBlockProvider> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.stateproviders.RotatedBlockProviderImpl");
        }
        return Holder.WIRE.construct(state);
    }

}
