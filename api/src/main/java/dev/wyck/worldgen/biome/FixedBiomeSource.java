package dev.wyck.worldgen.biome;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

/**
 * The fixed biome source, also called single biome, uses one specified biome everywhere.
 *
 * @see <a href="https://minecraft.wiki/w/Dimension_definition#fixed">Dimension definition (fixed)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FixedBiomeSource extends BiomeSource {

    /**
     * The single biome to use.
     * @return the biome
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Biome biome();

    /**
     * Creates a new fixed biome source.
     * @param biome the biome
     * @return the fixed biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FixedBiomeSource of(Biome biome) {
        record Holder() {
            static final ConstructWireProvider<FixedBiomeSource> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.biome.FixedBiomeSourceImpl");
        }
        return Holder.WIRE.construct(biome);
    }

    /**
     * Creates a new fixed biome source.
     * @param biomeKey the biome key
     * @return the fixed biome source
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FixedBiomeSource of(ResourceKey biomeKey) {
        return of(Biome.reference(biomeKey));
    }
}
