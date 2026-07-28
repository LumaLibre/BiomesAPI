package dev.wyck.worldgen.surface.rule;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

/**
 * Places the given block's default state at the surface.
 *
 * @see <a href="https://minecraft.wiki/w/Surface_rule">Surface rule</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface BlockRuleSource extends RuleSource {

    /**
     * The block to place.
     * @return the block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material block();

    /**
     * Creates a new block rule source.
     * @param block the block to place
     * @return the block rule source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockRuleSource of(Material block) {
        record Holder() {
            static final ConstructWireProvider<BlockRuleSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.surface.rule.BlockRuleSourceImpl");
        }
        return Holder.WIRE.construct(block);
    }
}
