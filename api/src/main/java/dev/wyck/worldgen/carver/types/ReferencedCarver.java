package dev.wyck.worldgen.carver.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.carver.Carvers;
import dev.wyck.worldgen.carver.ConfiguredWorldCarver;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing registered carver.
 *
 * @see Carvers
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedCarver extends ConfiguredWorldCarver {

    /**
     * Creates a new reference to the given carver.
     * @param key the key of the carver
     * @return a new reference to the given carver
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedCarver of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedCarver> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.types.ReferencedCarverImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
