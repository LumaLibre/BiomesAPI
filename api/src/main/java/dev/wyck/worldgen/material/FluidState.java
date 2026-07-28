package dev.wyck.worldgen.material;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jetbrains.annotations.Range;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Wraps a fluid in a specific state of data.
 * There are no Bukkit or Paper API equivalents for this
 * wrapper. {@link io.papermc.paper.block.fluid.FluidData} is
 * tied to worlds.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface FluidState extends Wrapper {

    /**
     * The fluid this state belongs to.
     * @return the fluid this state belongs to
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    FluidType fluid();

    /**
     * The amount of fluid held by this state, from {@code 1} to {@code 8}.
     * @return the amount of fluid held by this state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    @Range(from = 1, to = 8)
    int amount();

    /**
     * Whether this state is a source block.
     * @return true if this state is a source block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean isSource();

    /**
     * Whether the fluid in this state is falling.
     * Falling fluids occupy the full block height regardless of {@link #amount()}.
     * @return true if the fluid in this state is falling
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    boolean falling();

    /**
     * Whether this state holds no fluid at all.
     * @return true if this state holds no fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default boolean isEmpty() {
        return this.fluid() == FluidType.EMPTY;
    }

    /**
     * The height of the fluid in this state within its own block,
     * from {@code 0.0} to {@code 1.0}, matching
     * {@code net.minecraft.world.level.material.FluidState#getOwnHeight},
     * which is defined as {@code amount / 9f}.
     *
     * @return the height of the fluid in this state within its own block
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default float ownHeight() {
        return this.isEmpty() ? 0.0F : this.amount() / 9.0F;
    }

    /**
     * Creates a source state of the given fluid.
     * @param fluid the fluid
     * @return a source state of the given fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FluidState source(FluidType fluid) {
        return source(fluid, false);
    }

    /**
     * Creates a source state of the given fluid.
     * @param fluid the fluid
     * @param falling whether the fluid is falling
     * @return a source state of the given fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FluidState source(FluidType fluid, boolean falling) {
        Preconditions.checkNotNull(fluid, "fluid cannot be null");
        return create(fluid, 8, true, falling);
    }

    /**
     * Creates a non-falling flowing state of the given fluid.
     * @param fluid the fluid
     * @param amount the amount of fluid, from 1 to 8
     * @return a flowing state of the given fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FluidState flowing(FluidType fluid, int amount) {
        return flowing(fluid, amount, false);
    }

    /**
     * Creates a flowing state of the given fluid.
     * @param fluid the fluid
     * @param amount the amount of fluid, from 1 to 8
     * @param falling whether the fluid is falling
     * @return a flowing state of the given fluid
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FluidState flowing(FluidType fluid, int amount, boolean falling) {
        Preconditions.checkNotNull(fluid, "fluid cannot be null");
        Preconditions.checkArgument(amount >= 1 && amount <= 8, "amount must be within [1, 8], got %s", amount);
        return create(fluid, amount, false, falling);
    }

    /**
     * The empty fluid state, holding no fluid.
     * @return the empty fluid state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FluidState empty() {
        return create(FluidType.EMPTY, 0, false, false);
    }

    private static FluidState create(FluidType fluid, int amount, boolean source, boolean falling) {
        record Holder() {
            static final ConstructWireProvider<FluidState> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.material.FluidStateImpl");
        }
        return Holder.WIRE.construct(fluid, amount, source, falling);
    }

    /**
     * Creates a new fluid state builder.
     * @return a new fluid state builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this fluid state into a builder.
     * @return a builder containing the same data as this fluid state
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Reads a Minecraft fluid state into a wrapper.
     * @param minecraftState the Minecraft fluid state to read
     * @return the wrapper for the fluid state
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FluidState decode(Object minecraftState) {
        record Holder() {
            static final Decoder<FluidState> DECODER = Decoder.create("dev.wyck.decode.worldgen.material.FluidStateDecoder");
        }
        return Holder.DECODER.decode(minecraftState);
    }

    /**
     * Builder for {@link FluidState}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {

        private @Nullable FluidType fluid;
        private int amount = 8;
        private boolean source = true;
        private boolean falling = false;

        private Builder() {}

        private Builder(FluidState state) {
            this.fluid = state.fluid();
            this.amount = state.amount();
            this.source = state.isSource();
            this.falling = state.falling();
        }

        /**
         * Sets the fluid of this builder.
         *
         * @param fluid the fluid
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder fluid(FluidType fluid) {
            Preconditions.checkNotNull(fluid, "fluid cannot be null");
            this.fluid = fluid;
            return this;
        }

        /**
         * Marks the built state as a source block. Source states always
         * hold an amount of {@code 8}, discarding any previously set
         * {@link #amount(int)}.
         *
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder source() {
            this.source = true;
            this.amount = 8;
            return this;
        }

        /**
         * Marks the built state as flowing with the given amount.
         *
         * @param amount the amount of fluid, from 1 to 8
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder amount(int amount) {
            Preconditions.checkArgument(amount >= 1 && amount <= 8, "amount must be within [1, 8], got %s", amount);
            this.source = false;
            this.amount = amount;
            return this;
        }

        /**
         * Sets whether the fluid in the built state is falling.
         *
         * @param falling whether the fluid is falling
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder falling(boolean falling) {
            this.falling = falling;
            return this;
        }

        /**
         * Builds the fluid state.
         *
         * @return the built fluid state
         * @throws IllegalStateException if no fluid was set
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public FluidState build() {
            Preconditions.checkState(this.fluid != null, "fluid must be set");
            if (this.fluid == FluidType.EMPTY) {
                return FluidState.empty();
            }
            return FluidState.create(this.fluid, this.amount, this.source, this.falling);
        }
    }
}
