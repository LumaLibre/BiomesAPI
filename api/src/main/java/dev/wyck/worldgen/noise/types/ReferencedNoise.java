package dev.wyck.worldgen.noise.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.noise.Noise;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to a noise function.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface ReferencedNoise extends Noise {

    /**
     * The resource key of the noise function.
     * @param resourceKey the resource key of the noise function
     * @return the noise function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedNoise of(ResourceKey resourceKey) {
        record Holder() {
            static final ConstructWireProvider<ReferencedNoise> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.types.ReferencedNoiseImpl");
        }
        return Holder.WIRE.construct(resourceKey);
    }
}