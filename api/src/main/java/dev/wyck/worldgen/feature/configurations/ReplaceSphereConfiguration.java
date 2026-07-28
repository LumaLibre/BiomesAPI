package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A replacement sphere configuration.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ReplaceSphereConfiguration extends FeatureConfiguration {

    /**
     * The block state that is replaced within the sphere.
     * @return the target block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData targetState();

    /**
     * The block state the target is replaced with.
     * @return the replacement block state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    BlockData replaceState();

    /**
     * The radius of the sphere. Value between 0 and 12.
     * @return the radius
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    IntProvider radius();

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
     * Creates a new replace sphere configuration.
     * @param targetState the block state that is replaced within the sphere
     * @param replaceState the block state the target is replaced with
     * @param radius the radius of the sphere
     * @return a new replace sphere configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceSphereConfiguration of(BlockData targetState, BlockData replaceState, IntProvider radius) {
        record Holder() {
            static final ConstructWireProvider<ReplaceSphereConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.ReplaceSphereConfigurationImpl");
        }
        return Holder.WIRE.construct(targetState, replaceState, radius);
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
     * Builder for {@link ReplaceSphereConfiguration}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private @Nullable BlockData targetState;
        private @Nullable BlockData replaceState;
        private @Nullable IntProvider radius;

        public Builder() {}

        public Builder(ReplaceSphereConfiguration configuration) {
            this.targetState = configuration.targetState();
            this.replaceState = configuration.replaceState();
            this.radius = configuration.radius();
        }

        /**
         * Sets the block state that is replaced within the sphere.
         * @param targetState the target block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targetState(BlockData targetState) {
            this.targetState = targetState;
            return this;
        }

        /**
         * Sets the block state the target is replaced with.
         * @param replaceState the replacement block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceState(BlockData replaceState) {
            this.replaceState = replaceState;
            return this;
        }

        /**
         * Sets the radius of the sphere.
         * @param radius the radius
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder radius(IntProvider radius) {
            this.radius = radius;
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the block state that is replaced within the sphere from the given material's default block data.
         * @param material the material to derive the target block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder targetState(Material material) {
            this.targetState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Sets the replacement block state from the given material's default block data.
         * @param material the material to derive the replacement block state from
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder replaceState(Material material) {
            this.replaceState = BukkitBootstrapUtil.util().createBlockData(material);
            return this;
        }

        /**
         * Builds the configuration.
         * @return the configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public ReplaceSphereConfiguration build() {
            Preconditions.checkNotNull(targetState, "targetState must be set");
            Preconditions.checkNotNull(replaceState, "replaceState must be set");
            Preconditions.checkNotNull(radius, "radius must be set");
            Preconditions.checkArgument(radius.minInclusive() >= 0 && radius.maxInclusive() <= 12, "radius must be between 0 and 12");
            return of(targetState, replaceState, radius);
        }
    }
}