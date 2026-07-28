package dev.wyck.worldgen.chunk.flat;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Minecraft's FlatLayerInfo.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FlatLayerInfo extends Wrapper {

    /** The maximum height of a flat layer. */
    @AsOf("3.0.0")
    int Y_MAX = 4064;

    /**
     * The block to place.
     * @return the block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    Material block();

    /**
     * The height of the layer.
     * @return the height
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int height();

    /**
     * Creates a new builder for this object.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new FlatLayerInfo.
     * @param height the height of the layer
     * @param block the block to place
     * @return a new FlatLayerInfo
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FlatLayerInfo of(Material block, int height) {
        Preconditions.checkArgument(height >= 0 && height <= Y_MAX, "height must be between 0 and 4064");
        record Holder() {
            static final ConstructWireProvider<FlatLayerInfo> WIRE = ConstructWireProvider.construct("dev.wyck.worldgen.chunk.flat.FlatLayerInfoImpl");
        }
        return Holder.WIRE.construct(block, height);
    }

    /**
     * Reads Minecraft flat-layer information.
     * @param minecraftLayer the flat layer to read
     * @return the decoded flat layer
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FlatLayerInfo decode(Object minecraftLayer) {
        record Holder() {
            static final Decoder<FlatLayerInfo> DECODER = Decoder.create("dev.wyck.decode.worldgen.chunk.FlatLayerInfoDecoder");
        }
        return Holder.DECODER.decode(minecraftLayer);
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
     * Builder for {@link FlatLayerInfo}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable Material block;
        private int height;

        public Builder() {}

        public Builder(FlatLayerInfo configuration) {
            this.block = configuration.block();
            this.height = configuration.height();
        }

        /**
         * Sets the block.
         * @param block the block
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder block(Material block) {
            this.block = block;
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
         * Builds the FlatLayerInfo.
         * @return the FlatLayerInfo
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FlatLayerInfo build() {
            Preconditions.checkNotNull(block, "block must be set");
            return of(block, height);
        }
    }
}
