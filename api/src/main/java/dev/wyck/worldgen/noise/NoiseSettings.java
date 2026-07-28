package dev.wyck.worldgen.noise;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Wraps the inner {@code NoiseSettings} shape of noise generator settings.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface NoiseSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<NoiseSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.noise.NoiseSettingsImpl");

    @ApiStatus.Internal
    Decoder<NoiseSettings> DECODER = Decoder.create("dev.wyck.decode.worldgen.noise.NoiseSettingsDecoder");

    NoiseSettings OVERWORLD = of(-64, 384, 1, 2);
    NoiseSettings NETHER = of(0, 128, 1, 2);
    NoiseSettings END = of(0, 128, 2, 1);
    NoiseSettings CAVES = of(-64, 192, 1, 2);
    NoiseSettings FLOATING_ISLANDS = of(0, 256, 2, 1);

    /**
     * The lowest Y level the noise generates at.
     *
     * @return the minimum Y
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int minY();

    /**
     * The total vertical span the noise generates over.
     *
     * @return the height
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int height();

    /**
     * The horizontal noise cell size, in quarts.
     *
     * @return the horizontal size
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int sizeHorizontal();

    /**
     * The vertical noise cell size, in quarts.
     *
     * @return the vertical size
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int sizeVertical();

    /**
     * Creates a noise settings shape.
     *
     * @apiNote The values must satisfy the vanilla constraints, height a
     * multiple of 16, {@code minY + height <= 2032}, and {@code minY} a multiple of 16.
     *
     * @param minY the lowest Y level
     * @param height the total vertical span
     * @param sizeHorizontal the horizontal cell size, in quarts
     * @param sizeVertical the vertical cell size, in quarts
     * @return a new noise settings shape
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static NoiseSettings of(int minY, int height, int sizeHorizontal, int sizeVertical) {
        Preconditions.checkArgument(height % 16 == 0, "height must be a multiple of 16");
        Preconditions.checkArgument(minY + height <= 2032, "minY + height must be <= 2032");
        Preconditions.checkArgument(minY % 16 == 0, "minY must be a multiple of 16");
        return WIRE.construct(minY, height, sizeHorizontal, sizeVertical);
    }

    /**
     * Reads Minecraft noise settings.
     * @param minecraftSettings the noise settings to read
     * @return the decoded noise settings
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static NoiseSettings decode(Object minecraftSettings) {
        return DECODER.decode(minecraftSettings);
    }

    final class Builder {
        private int minY = -64;
        private int height = 384;
        private int sizeHorizontal = 1;
        private int sizeVertical = 2;

        public Builder() {}

        public Builder(NoiseSettings settings) {
            this.minY = settings.minY();
            this.height = settings.height();
            this.sizeHorizontal = settings.sizeHorizontal();
            this.sizeVertical = settings.sizeVertical();
        }

        /**
         * Sets the minimum Y level.
         * @param minY the minimum Y level
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder minY(int minY) {
            this.minY = minY;
            return this;
        }

        /**
         * Sets the height.
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
         * Sets the horizontal size.
         * @param sizeHorizontal the horizontal size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder sizeHorizontal(int sizeHorizontal) {
            this.sizeHorizontal = sizeHorizontal;
            return this;
        }

        /**
         * Sets the vertical size.
         * @param sizeVertical the vertical size
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder sizeVertical(int sizeVertical) {
            this.sizeVertical = sizeVertical;
            return this;
        }

        /**
         * Builds the noise settings shape.
         * @return a new noise settings shape
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public NoiseSettings build() {
            return of(minY, height, sizeHorizontal, sizeVertical);
        }
    }
}