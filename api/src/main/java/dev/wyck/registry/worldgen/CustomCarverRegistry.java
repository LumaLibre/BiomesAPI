package dev.wyck.registry.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wire contract for registering a CustomCarver into the CARVER registry.
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
@ApiStatus.Internal
public interface CustomCarverRegistry {

    @AsOf("3.0.0")
    void register(ResourceKey key, CustomCarver<?> carver);

    @AsOf("3.0.0")
    static CustomCarverRegistry registry() {
        record Holder() {
            static final WireProvider<CustomCarverRegistry> WIRE = WireProvider.create("dev.wyck.registry.worldgen.CustomCarverRegistryImpl");
        }
        return Holder.WIRE.get();
    }
}