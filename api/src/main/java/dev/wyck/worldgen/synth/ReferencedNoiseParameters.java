package dev.wyck.worldgen.synth;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing noise parameters.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedNoiseParameters extends NoiseParameters {

    /**
     * Create a new reference to a noise parameters.
     * @param key the key of the noise parameters
     * @return the new reference
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedNoiseParameters of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedNoiseParameters> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.synth.ReferencedNoiseParametersImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
