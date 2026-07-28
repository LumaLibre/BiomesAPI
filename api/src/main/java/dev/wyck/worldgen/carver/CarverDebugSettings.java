package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.util.BukkitBootstrapUtil;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Debug settings for carvers.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface CarverDebugSettings extends Wrapper {

    @ApiStatus.Internal
    ConstructWireProvider<CarverDebugSettings> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.carver.CarverDebugSettingsImpl");

    @ApiStatus.Internal
    Decoder<CarverDebugSettings> DECODER =
        Decoder.create("dev.wyck.decode.worldgen.carver.CarverDebugSettingsDecoder");

    /** The default debug settings, as they appear in Minecraft. */
    @AsOf("3.0.0")
    CarverDebugSettings DEFAULT = builder().build();

    /**
     * Whether to enable debug mode.
     * @return whether debug mode is enabled
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    boolean debugMode();

    /**
     * The block state to use for air.
     * @return the block state
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockData airState();

    /**
     * The block state to use for water.
     * @return the block state
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockData waterState();

    /**
     * The block state to use for lava.
     * @return the block state
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockData lavaState();

    /**
     * The block state to use for barriers.
     * @return the block state
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    BlockData barrierState();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new debug settings object.
     * @param debugMode whether to enable debug mode
     * @param airState the block state to use for air
     * @param waterState the block state to use for water
     * @param lavaState the block state to use for lava
     * @param barrierState the block state to use for barriers
     * @return a new debug settings object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static CarverDebugSettings of(boolean debugMode, BlockData airState, BlockData waterState, BlockData lavaState, BlockData barrierState) {
        return WIRE.construct(debugMode, airState, waterState, lavaState, barrierState);
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
     * Reads Minecraft carver debug settings.
     * @param minecraftSettings the settings to read
     * @return the decoded settings
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static CarverDebugSettings decode(Object minecraftSettings) {
        return DECODER.decode(minecraftSettings);
    }

    /**
     * Builder for {@link CarverDebugSettings}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private boolean debugMode = false;
        private BlockData airState = BukkitBootstrapUtil.util().createBlockData(Material.ACACIA_BUTTON);
        private BlockData waterState = BukkitBootstrapUtil.util().createBlockData(Material.CANDLE);
        private BlockData lavaState = BukkitBootstrapUtil.util().createBlockData(Material.ORANGE_STAINED_GLASS);
        private BlockData barrierState = BukkitBootstrapUtil.util().createBlockData(Material.GLASS);

        public Builder() {}

        public Builder(CarverDebugSettings settings) {
            this.debugMode = settings.debugMode();
            this.airState = settings.airState();
            this.waterState = settings.waterState();
            this.lavaState = settings.lavaState();
            this.barrierState = settings.barrierState();
        }

        /**
         * Sets whether to enable debug mode.
         * @param debugMode whether to enable debug mode
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder debugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }

        /**
         * Sets the block state to use for air.
         * @param airState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder airState(BlockData airState) {
            this.airState = airState;
            return this;
        }

        /**
         * Sets the block state to use for water.
         * @param waterState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder waterState(BlockData waterState) {
            this.waterState = waterState;
            return this;
        }

        /**
         * Sets the block state to use for lava.
         * @param lavaState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lavaState(BlockData lavaState) {
            this.lavaState = lavaState;
            return this;
        }

        /**
         * Sets the block state to use for barriers.
         * @param barrierState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder barrierState(BlockData barrierState) {
            this.barrierState = barrierState;
            return this;
        }

        // Friendly builder methods

        /**
         * Enables debug mode.
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder debugMode() {
            this.debugMode = true;
            return this;
        }

        /**
         * Sets the block state to use for air.
         * @param airState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder airState(Material airState) {
            this.airState = BukkitBootstrapUtil.util().createBlockData(airState);
            return this;
        }

        /**
         * Sets the block state to use for water.
         * @param waterState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder waterState(Material waterState) {
            this.waterState = BukkitBootstrapUtil.util().createBlockData(waterState);
            return this;
        }

        /**
         * Sets the block state to use for lava.
         * @param lavaState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder lavaState(Material lavaState) {
            this.lavaState = BukkitBootstrapUtil.util().createBlockData(lavaState);
            return this;
        }

        /**
         * Sets the block state to use for barriers.
         * @param barrierState the block state
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder barrierState(Material barrierState) {
            this.barrierState = BukkitBootstrapUtil.util().createBlockData(barrierState);
            return this;
        }

        /**
         * Builds the debug settings.
         * @return the debug settings
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public CarverDebugSettings build() {
            return of(debugMode, airState, waterState, lavaState, barrierState);
        }
    }
}
