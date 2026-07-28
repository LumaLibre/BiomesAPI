package dev.wyck.worldgen.function.misc;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.function.DensityFunction;
import dev.wyck.worldgen.noise.NoiseRouter;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Marker functions are commonly used to cache the output of a density function.
 *
 * @see <a href="https://minecraft.wiki/w/Density_function#Marker_functions">Density function (marker functions)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface Marker extends DensityFunction, Registerable<Marker> {

    /**
     * The type of the marker.
     * @return the type of the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Type type();

    /**
     * The input density function.
     * @return the input density function
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    DensityFunction input();

    /**
     * Creates a new marker.
     * @param resourceKey the resource key, or null
     * @param type the type of the marker
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker of(@Nullable ResourceKey resourceKey, Type type, DensityFunction input) {
        record Holder() {
            static final ConstructWireProvider<Marker> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.function.misc.MarkerImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), type, input);
    }

    /**
     * Creates a new marker.
     * @param type the type of the marker
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    static Marker of(Type type, DensityFunction input) {
        return of(null, type, input);
    }

    /**
     * Interpolates at each block in one cell based on the input density function value of some cells around. The size of each cell is size_horizontal * 4 and size_vertical * 4. Used often in combination with flat_cache.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker interpolated(DensityFunction input) {
        return of(Type.INTERPOLATED, input);
    }

    /**
     * Calculate the value per 4×4 column (Value at each block in one column is the same). And it is calculated only once per column, at Y=0. Used often in combination with interpolated.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker flatCache(DensityFunction input) {
        return of(Type.FLAT_CACHE, input);
    }

    /**
     * Only computes the input density once per horizontal position.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker cache2d(DensityFunction input) {
        return of(Type.CACHE_2D, input);
    }

    /**
     * If this density function is referenced twice, it is only computed once per block position.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker cacheOnce(DensityFunction input) {
        return of(Type.CACHE_ONCE, input);
    }

    /**
     * Used by the game onto {@link NoiseRouter#finalDensity()}.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker cacheAllInCell(DensityFunction input) {
        return of(Type.CACHE_ALL_IN_CELL, input);
    }

    /**
     * Used in vanilla for smooth transition to chunks generated in old versions.
     * @param input the input density function
     * @return the marker
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Marker blendDensity(DensityFunction input) {
        return of(Type.BLEND_DENSITY, input);
    }

    /**
     * The type of the marker.
     * @since 3.0.0
     */
    enum Type {
        INTERPOLATED,
        FLAT_CACHE,
        CACHE_2D,
        CACHE_ONCE,
        CACHE_ALL_IN_CELL,
        BLEND_DENSITY // Yes, this function is actually here in the vanilla implementation.
    }
}
