package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.bukkit.util.BlockVector;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Intradimensional portals generated in the End.
 *
 * @see <a href="https://minecraft.wiki/w/End_Gateway">End Gateway</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface EndGatewayConfiguration extends FeatureConfiguration {

    /**
     * The block position where the gateway should exit, or empty if the exit is searched for on use.
     * @return the exit position, or empty
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<BlockVector> exit();

    /**
     * Whether the gateway should teleport entities to the exact exit position.
     * @return whether the exit position is exact
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean exact();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new gateway configuration with a known exit position.
     * @param exit the block position where the gateway should exit
     * @param exact whether the gateway should teleport entities to the exact exit position
     * @return a new end gateway configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndGatewayConfiguration knownExit(BlockVector exit, boolean exact) {
        return create(Optional.of(exit), exact);
    }

    /**
     * Creates a new gateway configuration whose exit position is searched for when the gateway is used.
     * @return a new end gateway configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndGatewayConfiguration delayedExitSearch() {
        return create(Optional.empty(), false);
    }

    private static EndGatewayConfiguration create(Optional<BlockVector> exit, boolean exact) {
        record Holder() {
            static final ConstructWireProvider<EndGatewayConfiguration> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.EndGatewayConfigurationImpl");
        }
        return Holder.WIRE.construct(exit, exact);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link EndGatewayConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockVector exit;
        private boolean exact;

        public Builder() {}

        public Builder(EndGatewayConfiguration configuration) {
            this.exit = configuration.exit().orElse(null);
            this.exact = configuration.exact();
        }

        /**
         * Sets the block position where the gateway should exit.
         * @param exit the exit position, or {@code null} to search for the exit on use
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder exit(@Nullable BlockVector exit) {
            this.exit = exit;
            return this;
        }

        /**
         * Sets whether the gateway should teleport entities to the exact exit position.
         * @param exact whether the exit position is exact
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder exact(boolean exact) {
            this.exact = exact;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public EndGatewayConfiguration build() {
            return exit != null ? knownExit(exit, exact) : delayedExitSearch();
        }
    }
}
