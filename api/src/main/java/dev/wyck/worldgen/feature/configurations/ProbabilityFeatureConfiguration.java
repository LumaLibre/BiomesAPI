package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A configuration for a feature that has a probability of being placed.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ProbabilityFeatureConfiguration extends FeatureConfiguration {

    /**
     * Gets the probability of the feature being placed between 0.0 and 1.0.
     * @return the probability of the feature being placed
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    float probability();

    /**
     * Creates a new instance of this configuration.
     * @param probability the probability of the feature being placed between 0.0 and 1.0
     * @return a new instance of this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ProbabilityFeatureConfiguration of(float probability) {
        Preconditions.checkArgument(probability >= 0.0F && probability <= 1.0F, "probability must be between 0.0 and 1.0");
        record Holder() {
            static final ConstructWireProvider<ProbabilityFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.ProbabilityFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(probability);
    }

}