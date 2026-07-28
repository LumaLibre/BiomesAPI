package dev.wyck.worldgen.chunk;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Minecraft's debug level source.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DebugLevelSource extends ChunkGenerator {

    /**
     * Holder biome.
     * @return the holder biome.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Biome biome(); // vanilla-name: plains

    /**
     * Creates a new debug level source.
     * @param biome the biome
     * @return the debug level source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DebugLevelSource of(Biome biome) {
        record Holder() {
            static final ConstructWireProvider<DebugLevelSource> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.chunk.DebugLevelSourceImpl");
        }
        return Holder.WIRE.construct(biome);
    }
}
