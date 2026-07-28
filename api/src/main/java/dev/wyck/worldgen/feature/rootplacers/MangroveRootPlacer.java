package dev.wyck.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Places the roots of a mangrove tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Root_placer">Tree definition (Root placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface MangroveRootPlacer extends RootPlacer {

    /**
     * The mangrove root placement parameters.
     * @return the mangrove root placement
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    MangroveRootPlacement mangroveRootPlacement();

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
     * Creates a new mangrove root placer.
     * @param trunkOffsetY the offset perpendicular to the trunk
     * @param rootProvider the block used as the root of the tree
     * @param aboveRootPlacement the blocks above the root, or {@code null} if not set
     * @param mangroveRootPlacement the mangrove root placement parameters
     * @return a new mangrove root placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MangroveRootPlacer of(IntProvider trunkOffsetY, BlockStateProvider rootProvider, @Nullable AboveRootPlacement aboveRootPlacement, MangroveRootPlacement mangroveRootPlacement) {
        record Holder() {
            static final ConstructWireProvider<MangroveRootPlacer> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.rootplacers.MangroveRootPlacerImpl");
        }
        return Holder.WIRE.construct(trunkOffsetY, rootProvider, Optional.ofNullable(aboveRootPlacement), mangroveRootPlacement);
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
     * Builder for {@link MangroveRootPlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends RootPlacerBuilder<Builder, MangroveRootPlacer> {
        private @Nullable MangroveRootPlacement mangroveRootPlacement;

        public Builder() {}

        public Builder(MangroveRootPlacer trunkPlacer) {
            super(trunkPlacer);
            this.mangroveRootPlacement = trunkPlacer.mangroveRootPlacement();
        }

        /**
         * Sets the mangrove root placement parameters.
         * @param mangroveRootPlacement the mangrove root placement
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder mangroveRootPlacement(MangroveRootPlacement mangroveRootPlacement) {
            this.mangroveRootPlacement = mangroveRootPlacement;
            return this;
        }

        @Override
        protected MangroveRootPlacer create() {
            Preconditions.checkNotNull(mangroveRootPlacement, "mangroveRootPlacement must be set");
            //noinspection ConstantConditions
            return of(trunkOffsetY, rootProvider, aboveRootPlacement, mangroveRootPlacement);
        }
    }
}
