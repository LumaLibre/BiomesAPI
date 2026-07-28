package dev.wyck.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Determines the placement and shape of a tree's foliage.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FoliagePlacer extends Wrapper {

    /**
     * The radius of the foliage.
     * @return the radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider radius();

    /**
     * The vertical offset from the top of the trunk to the top of the foliage.
     * @return the offset
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider offset();

    // Friendly static accessors

    /**
     * A new builder for a {@link BlobFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlobFoliagePlacer.Builder blob() {
        return BlobFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link SpruceFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpruceFoliagePlacer.Builder spruce() {
        return SpruceFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link PineFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static PineFoliagePlacer.Builder pine() {
        return PineFoliagePlacer.builder();
    }

    /**
     * A new builder for an {@link AcaciaFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AcaciaFoliagePlacer.Builder acacia() {
        return AcaciaFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link BushFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BushFoliagePlacer.Builder bush() {
        return BushFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link FancyFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FancyFoliagePlacer.Builder fancy() {
        return FancyFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link MegaJungleFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MegaJungleFoliagePlacer.Builder megaJungle() {
        return MegaJungleFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link MegaPineFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MegaPineFoliagePlacer.Builder megaPine() {
        return MegaPineFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link DarkOakFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DarkOakFoliagePlacer.Builder darkOak() {
        return DarkOakFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link RandomSpreadFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomSpreadFoliagePlacer.Builder randomSpread() {
        return RandomSpreadFoliagePlacer.builder();
    }

    /**
     * A new builder for a {@link CherryFoliagePlacer}.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CherryFoliagePlacer.Builder cherry() {
        return CherryFoliagePlacer.builder();
    }

    @ApiStatus.Internal
    static FoliagePlacer decode(Object minecraftFoliagePlacer) {
        record Holder() {
            static final Decoder<FoliagePlacer> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.tree.FoliagePlacerDecoders");
        }
        return Holder.DECODER.decode(minecraftFoliagePlacer);
    }

    /**
     * A builder for {@link FoliagePlacer}s.
     * @param <T> the builder type
     * @param <P> the foliage placer type
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class FoliagePlacerBuilder<T, P> {
        protected IntProvider radius = IntProvider.constant(0);
        protected IntProvider offset = IntProvider.constant(0);

        public FoliagePlacerBuilder() {}

        public FoliagePlacerBuilder(FoliagePlacer foliagePlacer) {
            this.radius = foliagePlacer.radius();
            this.offset = foliagePlacer.offset();
        }

        /**
         * Sets the radius of the foliage.
         * @param radius the radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T radius(IntProvider radius) {
            this.radius = radius;
            return (T) this;
        }

        /**
         * Sets the vertical offset from the top of the trunk to the top of the foliage.
         * @param offset the offset
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public T offset(IntProvider offset) {
            this.offset = offset;
            return (T) this;
        }

        /**
         * Builds the foliage placer.
         * @return the foliage placer
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public final P build() {
            Preconditions.checkArgument(radius.minInclusive() >= 0 && radius.maxInclusive() <= 16, "radius must be between 0 and 16");
            Preconditions.checkArgument(offset.minInclusive() >= 0 && offset.maxInclusive() <= 16, "offset must be between 0 and 16");
            return create();
        }

        protected abstract P create();
    }
}
