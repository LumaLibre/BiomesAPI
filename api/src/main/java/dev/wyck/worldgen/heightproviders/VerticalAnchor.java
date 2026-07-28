package dev.wyck.worldgen.heightproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the VerticalAnchor value-provider family. Sampling occurs
 * Minecraft code side, so this wrapper only carries the bounds/shape parameters.
 *
 * @see <a href="https://minecraft.wiki/w/Custom_world_generation/vertical_anchor">Vertical anchor</a>
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface VerticalAnchor extends Wrapper {

    @ApiStatus.Internal
    Decoder<VerticalAnchor> DECODER = Decoder.create("dev.wyck.decode.worldgen.heightproviders.VerticalAnchorDecoders");

    /**
     * Wraps the given value in a {@link ConstantHeight}
     * @return a constant height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default ConstantHeight constant() {
        return ConstantHeight.of(this);
    }

    /**
     * Wraps the given value in a {@link UniformHeight}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @return a uniform height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default UniformHeight uniform(VerticalAnchor maxInclusive) {
        return UniformHeight.of(this, maxInclusive);
    }

    /**
     * Wraps the given value in a {@link TrapezoidHeight}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @param plateau the length of the range in the middle that has a uniform distribution
     * @return a trapezoid height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default TrapezoidHeight trapezoid(VerticalAnchor maxInclusive, int plateau) {
        return TrapezoidHeight.of(this, maxInclusive, plateau);
    }

    /**
     * Wraps the given value in a {@link BiasedToBottomHeight}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @param inner the inner vertical anchor to use as the lower bound of the biased range
     * @return a biased-to-bottom height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default BiasedToBottomHeight biasedToBottom(VerticalAnchor maxInclusive, int inner) {
        return BiasedToBottomHeight.of(this, maxInclusive, inner);
    }

    /**
     * Alias for {@link #biasedToBottom(VerticalAnchor, int)}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @param inner the inner vertical anchor to use as the lower bound of the biased range
     * @return a biased-to-bottom height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default BiasedToBottomHeight biased(VerticalAnchor maxInclusive, int inner) {
        return biasedToBottom(maxInclusive, inner);
    }

    /**
     * Wraps the given value in a {@link VeryBiasedToBottomHeight}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @param inner the inner vertical anchor to use as the lower bound of the biased range
     * @return a very biased-to-bottom height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default VeryBiasedToBottomHeight veryBiasedToBottom(VerticalAnchor maxInclusive, int inner) {
        return VeryBiasedToBottomHeight.of(this, maxInclusive, inner);
    }

    /**
     * Alias for {@link #veryBiasedToBottom(VerticalAnchor, int)}
     * @param maxInclusive the maximum vertical anchor to use as the upper bound
     * @param inner the inner vertical anchor to use as the lower bound of the biased range
     * @return a very biased-to-bottom height provider wrapping the given value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default VeryBiasedToBottomHeight veryBiased(VerticalAnchor maxInclusive, int inner) {
        return veryBiasedToBottom(maxInclusive, inner);
    }

    /**
     * An absolute height as seen on the F3 screen.
     * @param y an absolute y level
     * @return a vertical anchor with the given absolute Y value
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Absolute absolute(int y) {
        return Absolute.of(y);
    }

    /**
     * Alias for {@link #absolute(int)}
     * @param y an absolute y level
     * @return a vertical anchor with the given absolute Y value
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Absolute y(int y) {
        return absolute(y);
    }

    /**
     * A relative height starting at the bottom of the world.
     * @param offset the offset from the bottom of the generation range
     * @return a vertical anchor with the given offset from the bottom of the generation range
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static AboveBottom aboveBottom(int offset) {
        return AboveBottom.of(offset);
    }

    /**
     * Alias for {@link #aboveBottom(int)}
     * @param offset the offset from the bottom of the generation range
     * @return a vertical anchor with the given offset from the bottom of the generation range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static AboveBottom above(int offset) {
        return aboveBottom(offset);
    }

    /**
     * A relative height starting at the top of the world. Higher values move the height down.
     * @param offset the offset from the top of the generation range
     * @return a vertical anchor with the given offset from the top of the generation range
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BelowTop belowTop(int offset) {
        return BelowTop.of(offset);
    }

    /**
     * Alias for {@link #belowTop(int)}
     * @param offset the offset from the top of the generation range
     * @return a vertical anchor with the given offset from the top of the generation range
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BelowTop below(int offset) {
        return belowTop(offset);
    }

    /**
     * Y measured from the bottom of the world.
     * @return a vertical anchor with the Y value 0
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static AboveBottom bottom() {
        return AboveBottom.BOTTOM;
    }

    /**
     * Y measured from the top of the world.
     * @return a vertical anchor with the Y value 0
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BelowTop top() {
        return BelowTop.TOP;
    }
    /**
     * Reads a Minecraft vertical anchor into a wrapper.
     * @param minecraftVerticalAnchor the vertical anchor to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static VerticalAnchor decode(Object minecraftVerticalAnchor) {
        return DECODER.decode(minecraftVerticalAnchor);
    }
}
