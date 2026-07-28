package dev.wyck.worldgen.feature.foliageplacers;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Places the foliage of a dark oak tree.
 *
 * @see <a href="https://minecraft.wiki/w/Tree_definition#Foliage_placer">Tree definition (Foliage placer)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface DarkOakFoliagePlacer extends FoliagePlacer {

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
     * Creates a new dark oak foliage placer.
     * @param radius the radius of the foliage
     * @param offset the vertical offset from the top of the trunk to the top of the foliage
     * @return a new dark oak foliage placer
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DarkOakFoliagePlacer of(IntProvider radius, IntProvider offset) {
        record Holder() {
            static final ConstructWireProvider<DarkOakFoliagePlacer> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.feature.foliageplacers.DarkOakFoliagePlacerImpl");
        }
        return Holder.WIRE.construct(radius, offset);
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
     * Builder for {@link DarkOakFoliagePlacer}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder extends FoliagePlacerBuilder<Builder, DarkOakFoliagePlacer> {

        public Builder() {}

        public Builder(DarkOakFoliagePlacer foliagePlacer) {
            super(foliagePlacer);
        }

        @Override
        protected DarkOakFoliagePlacer create() {
            return of(radius, offset);
        }
    }
}
