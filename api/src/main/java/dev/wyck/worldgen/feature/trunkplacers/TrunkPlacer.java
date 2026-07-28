package dev.wyck.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Determines the shape trunk of the tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Trunk_placer">Tree definition (Trunk placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TrunkPlacer extends Wrapper {

    @ApiStatus.Internal
    Decoder<TrunkPlacer> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.tree.TrunkPlacerDecoders");

    int MAX_BASE_HEIGHT = 32; // vanilla constant
    int MAX_RAND = 24; // vanilla constant

    /**
     * The base height of the trunk between 0 and 32.
     * @return the base height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int baseHeight();

    /**
     * A random value between 0 and this value is added to the height of the trunk. (max 24)
     * @return the random value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int heightRandA();

    /**
     * A random value between 0 and this value is added to the height of the trunk. (max 24)
     * @return the random value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int heightRandB();

    // Friendly statics

    /**
     * A new builder for a {@link BendingTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BendingTrunkPlacer.Builder bending() {
        return BendingTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link CherryTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CherryTrunkPlacer.Builder cherry() {
        return CherryTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link DarkOakTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DarkOakTrunkPlacer.Builder darkOak() {
        return DarkOakTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link FancyTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FancyTrunkPlacer.Builder fancy() {
        return FancyTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link ForkingTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ForkingTrunkPlacer.Builder forking() {
        return ForkingTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link GiantTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GiantTrunkPlacer.Builder giant() {
        return GiantTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link MegaJungleTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MegaJungleTrunkPlacer.Builder megaJungle() {
        return MegaJungleTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link StraightTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static StraightTrunkPlacer.Builder straight() {
        return StraightTrunkPlacer.builder();
    }

    /**
     * A new builder for a {@link UpwardsBranchingTrunkPlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static UpwardsBranchingTrunkPlacer.Builder upwardsBranching() {
        return UpwardsBranchingTrunkPlacer.builder();
    }

    @ApiStatus.Internal
    static TrunkPlacer decode(Object minecraftTrunkPlacer) {
        return DECODER.decode(minecraftTrunkPlacer);
    }

    /**
     * A builder for {@link TrunkPlacer}s.
     * @param <T> the builder type
     * @param <P> the trunk placer type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class TrunkPlacerBuilder<T, P> {
        protected int baseHeight;
        protected int heightRandA;
        protected int heightRandB;

        public TrunkPlacerBuilder() {}

        public TrunkPlacerBuilder(TrunkPlacer trunkPlacer) {
            this.baseHeight = trunkPlacer.baseHeight();
            this.heightRandA = trunkPlacer.heightRandA();
            this.heightRandB = trunkPlacer.heightRandB();
        }

        /**
         * Sets the base height of the trunk.
         * @param baseHeight the base height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T baseHeight(int baseHeight) {
            this.baseHeight = baseHeight;
            return (T) this;
        }

        /**
         * Sets the random values for the height of the trunk.
         * @param heightRandA the random value for the height of the trunk
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T heightRandA(int heightRandA) {
            this.heightRandA = heightRandA;
            return (T) this;
        }

        /**
         * Sets the random values for the height of the trunk.
         * @param heightRandB the random value for the height of the trunk
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T heightRandB(int heightRandB) {
            this.heightRandB = heightRandB;
            return (T) this;
        }

        /**
         * Builds the trunk placer.
         * @return the trunk placer
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final P build() {
            Preconditions.checkArgument(this.baseHeight >= 0 && this.baseHeight <= MAX_BASE_HEIGHT, "baseHeight must be between 0 and " + MAX_BASE_HEIGHT);
            Preconditions.checkArgument(this.heightRandA >= 0 && this.heightRandA <= MAX_RAND, "heightRandA must be between 0 and " + MAX_RAND);
            Preconditions.checkArgument(this.heightRandB >= 0 && this.heightRandB <= MAX_RAND, "heightRandB must be between 0 and " + MAX_RAND);
            return create();
        }

        protected abstract P create();
    }
}
