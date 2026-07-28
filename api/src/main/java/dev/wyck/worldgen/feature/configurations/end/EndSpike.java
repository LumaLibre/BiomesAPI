package dev.wyck.worldgen.feature.configurations.end;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A single spike (obsidian pillar) of an End spike feature, optionally topped with a guarded End crystal.
 *
 * @see <a href="https://minecraft.wiki/w/End_Spike">End Spike</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface EndSpike extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<EndSpike> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.end.EndSpikeImpl");

    @ApiStatus.Internal
    Decoder<EndSpike> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.EndSpikeDecoder");

    /**
     * The X coordinate of the center of the spike.
     * @return the center X coordinate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int centerX();

    /**
     * The Z coordinate of the center of the spike.
     * @return the center Z coordinate
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int centerZ();

    /**
     * The radius of the spike.
     * @return the radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int radius();

    /**
     * The height of the spike.
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int height();

    /**
     * Whether an iron bar cage is generated around the crystal on top of the spike.
     * @return whether the crystal is guarded
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean guarded();

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
     * Creates a new end spike.
     * @param centerX the X coordinate of the center of the spike
     * @param centerZ the Z coordinate of the center of the spike
     * @param radius the radius of the spike
     * @param height the height of the spike
     * @param guarded whether an iron bar cage is generated around the crystal
     * @return a new end spike
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndSpike of(int centerX, int centerZ, int radius, int height, boolean guarded) {
        return WIRE.construct(centerX, centerZ, radius, height, guarded);
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
     * Decodes a Minecraft End spike.
     * @param minecraftObject the Minecraft End spike
     * @return the decoded spike
     * @since 3.0.0
     */
    @ApiStatus.Internal
    static EndSpike decode(Object minecraftObject) {
        return DECODER.decode(minecraftObject);
    }

    /**
     * Builder for {@link EndSpike}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int centerX = 0;
        private int centerZ = 0;
        private int radius = 0;
        private int height = 0;
        private boolean guarded = false;

        public Builder() {}

        public Builder(EndSpike spike) {
            this.centerX = spike.centerX();
            this.centerZ = spike.centerZ();
            this.radius = spike.radius();
            this.height = spike.height();
            this.guarded = spike.guarded();
        }

        /**
         * Sets the X coordinate of the center of the spike.
         * @param centerX the center X coordinate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder centerX(int centerX) {
            this.centerX = centerX;
            return this;
        }

        /**
         * Sets the Z coordinate of the center of the spike.
         * @param centerZ the center Z coordinate
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder centerZ(int centerZ) {
            this.centerZ = centerZ;
            return this;
        }

        /**
         * Sets the radius of the spike.
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
         * Sets the height of the spike.
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
         * Sets whether an iron bar cage is generated around the crystal on top of the spike.
         * @param guarded whether the crystal is guarded
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder guarded(boolean guarded) {
            this.guarded = guarded;
            return this;
        }

        /**
         * Builds the end spike.
         * @return the end spike
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public EndSpike build() {
            return of(centerX, centerZ, radius, height, guarded);
        }
    }
}
