package dev.wyck.worldgen.feature.treedecorators;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Tries {@link #tries()} positions within the specified radius and height range to find a position that is at the
 * height of the <code>MOTION_BLOCKING_NO_LEAVES</code> heightmap, is solid, and has air above.
 * Places the specified block on the block above any matching positions.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Decorator">Tree definition (Decorator)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface PlaceOnGroundDecorator extends TreeDecorator {

    /**
     * The number of positions to try. Must be a positive integer. Defaults to 128.
     * @return the number of tries
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int tries();

    /**
     * The radius within which to try positions. Must be a non-negative integer. Defaults to 2.
     * @return the radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int radius();

    /**
     * The height range within which to try positions. Must be a non-negative integer. Defaults to 1.
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int height();

    /**
     * The block of the decoration.
     * @return the block state provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockStateProvider blockStateProvider();

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
     * Creates a new place on ground decorator.
     * @param tries the number of positions to try, must be a positive integer
     * @param radius the radius within which to try positions, must be a non-negative integer
     * @param height the height range within which to try positions, must be a non-negative integer
     * @param blockStateProvider the block of the decoration
     * @return a new place on ground decorator
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PlaceOnGroundDecorator of(int tries, int radius, int height, BlockStateProvider blockStateProvider) {
        record Holder() {
            static final ConstructWireProvider<PlaceOnGroundDecorator> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.treedecorators.PlaceOnGroundDecoratorImpl");
        }
        return Holder.WIRE.construct(tries, radius, height, blockStateProvider);
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
     * Builder for {@link PlaceOnGroundDecorator}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        // codec defaults
        private int tries = 128;
        private int radius = 2;
        private int height = 1;
        private @Nullable BlockStateProvider blockStateProvider = null;

        public Builder() {}

        public Builder(PlaceOnGroundDecorator decorator) {
            this.tries = decorator.tries();
            this.radius = decorator.radius();
            this.height = decorator.height();
            this.blockStateProvider = decorator.blockStateProvider();
        }

        /**
         * Sets the number of positions to try. Must be a positive integer. Defaults to 128.
         * @param tries the number of tries
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder tries(int tries) {
            this.tries = tries;
            return this;
        }

        /**
         * Sets the radius within which to try positions. Must be a non-negative integer. Defaults to 2.
         * @param radius the radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        /**
         * Sets the height range within which to try positions. Must be a non-negative integer. Defaults to 1.
         * @param height the height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the block of the decoration.
         * @param blockStateProvider the block state provider
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder blockStateProvider(BlockStateProvider blockStateProvider) {
            this.blockStateProvider = blockStateProvider;
            return this;
        }

        /**
         * Builds the place on ground decorator.
         * @return a new place on ground decorator
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public PlaceOnGroundDecorator build() {
            Preconditions.checkArgument(tries > 0, "tries must be positive");
            Preconditions.checkArgument(radius >= 0, "radius must not be negative");
            Preconditions.checkArgument(height >= 0, "height must not be negative");
            Preconditions.checkNotNull(blockStateProvider, "blockStateProvider must be set");
            return of(tries, radius, height, blockStateProvider);
        }
    }
}
