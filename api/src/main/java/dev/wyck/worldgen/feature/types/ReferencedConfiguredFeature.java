package dev.wyck.worldgen.feature.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.ConfiguredFeatures;
import org.jspecify.annotations.NullMarked;

/**
 * A reference to an existing configured feature.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature">Configured feature</a>
 * @see ConfiguredFeatures
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReferencedConfiguredFeature extends ConfiguredFeature {

    /**
     * Creates a new reference to the configured feature with the given key.
     * @param key the key of the configured feature
     * @return the reference to the configured feature
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReferencedConfiguredFeature of(ResourceKey key) {
        record Holder() {
            static final ConstructWireProvider<ReferencedConfiguredFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.types.ReferencedConfiguredFeatureImpl");
        }
        return Holder.WIRE.construct(key);
    }
}
