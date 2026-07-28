package dev.wyck.level.dimension.timeline;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Represents an easing function for a timeline.
 *
 * @since 3.2.0
 * @version 3.2.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.2.0")
public interface Easing extends Wrapper, EasingType {

    /**
     * The identifier of this easing.
     * @return the identifier of this easing
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    String id();

    /**
     * Gets an easing by its identifier.
     * @param id the identifier of the easing
     * @return the easing
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Easing of(String id) {
        return create(id);
    }

    /**
     * Gets an easing by its identifier.
     * @param id the identifier of the easing
     * @return the easing
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Easing of(ResourceKey id) {
        return of(id.path());
    }

    /**
     * Creates a cubic bezier easing.
     * @param x1 the x-coordinate of the first control point
     * @param y1 the y-coordinate of the first control point
     * @param x2 the x-coordinate of the second control point
     * @param y2 the y-coordinate of the second control point
     * @return a cubic bezier easing
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Easing cubicBezier(float x1, float y1, float x2, float y2) {
        return create(new float[]{x1, y1, x2, y2});
    }

    /**
     * Creates a symmetric cubic bezier easing.
     * @param x1 the x-coordinate of the control point
     * @param y1 the y-coordinate of the control point
     * @return a symmetric cubic bezier easing
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Easing symmetricCubicBezier(float x1, float y1) {
        return cubicBezier(x1, y1, 1.0F - x1, 1.0F - y1);
    }

    private static Easing create(Object source) {
        record Holder() {
            static final ConstructWireProvider<Easing> WIRE =
                WireProvider.construct("dev.wyck.level.dimension.timeline.EasingImpl");
            static final ConstructWireProvider<Easing> BEZIER_WIRE = WIRE.resolve("CubicBezier");
        }
        return switch (source) {
            case String id -> Holder.WIRE.construct((Object) id);
            case float[] points -> Holder.BEZIER_WIRE.construct(
                points[0], points[1], points[2], points[3]
            );
            default -> throw new IllegalArgumentException("Unsupported easing source: " + source);
        };
    }
}
