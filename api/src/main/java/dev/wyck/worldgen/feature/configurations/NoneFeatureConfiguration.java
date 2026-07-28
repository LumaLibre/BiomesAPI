package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A feature configuration that does nothing.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface NoneFeatureConfiguration extends FeatureConfiguration {

    /** The singleton instance of {@link NoneFeatureConfiguration}. */
    @AsOf("3.0.0")
    NoneFeatureConfiguration INSTANCE = of();

    private static NoneFeatureConfiguration of() {
        record Holder() {
            static final ConstructWireProvider<NoneFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.NoneFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct();
    }
}