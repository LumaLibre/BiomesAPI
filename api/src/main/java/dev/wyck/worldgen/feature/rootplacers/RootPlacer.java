package dev.wyck.worldgen.feature.rootplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Controls the placement and shape of the roots for a tree and moving the trunk upwards.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Root_placer">Tree definition (Root placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface RootPlacer extends Wrapper {

    /**
     * The offset perpendicular to the trunk.
     * @return the trunk offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider trunkOffsetY();

    /**
     * The block used as the root of the tree.
     * @return the root block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider rootProvider();

    /**
     * The blocks above the root, if present.
     * @return the above root placement, or empty if not set
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Optional<AboveRootPlacement> aboveRootPlacement();

    // Friendly static accessors

    /**
     * A new builder for a {@link MangroveRootPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MangroveRootPlacer.Builder mangrove() {
        return MangroveRootPlacer.builder();
    }

    @ApiStatus.Internal
    static RootPlacer decode(Object minecraftRootPlacer) {
        record Holder() {
            static final Decoder<RootPlacer> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.tree.RootPlacerDecoders");
        }
        return Holder.DECODER.decode(minecraftRootPlacer);
    }

    /**
     * A builder for {@link RootPlacer}s.
     * @param <T> the builder type
     * @param <P> the root placer type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class RootPlacerBuilder<T, P> {
        protected IntProvider trunkOffsetY = IntProvider.constant(0);
        protected @Nullable BlockStateProvider rootProvider;
        protected @Nullable AboveRootPlacement aboveRootPlacement;

        public RootPlacerBuilder() {}

        public RootPlacerBuilder(RootPlacer rootPlacer) {
            this.trunkOffsetY = rootPlacer.trunkOffsetY();
            this.rootProvider = rootPlacer.rootProvider();
            this.aboveRootPlacement = rootPlacer.aboveRootPlacement().orElse(null);
        }

        /**
         * Sets the offset perpendicular to the trunk.
         * @param trunkOffsetY the trunk offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T trunkOffsetY(IntProvider trunkOffsetY) {
            this.trunkOffsetY = trunkOffsetY;
            return (T) this;
        }

        /**
         * Sets the block used as the root of the tree.
         * @param rootProvider the root block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T rootProvider(BlockStateProvider rootProvider) {
            this.rootProvider = rootProvider;
            return (T) this;
        }

        /**
         * Sets the blocks above the root.
         * @param aboveRootPlacement the above root placement
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T aboveRootPlacement(AboveRootPlacement aboveRootPlacement) {
            this.aboveRootPlacement = aboveRootPlacement;
            return (T) this;
        }

        /**
         * Builds the root placer.
         * @return the root placer
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final P build() {
            Preconditions.checkNotNull(rootProvider, "rootProvider must be set");
            return create();
        }

        protected abstract P create();
    }
}
