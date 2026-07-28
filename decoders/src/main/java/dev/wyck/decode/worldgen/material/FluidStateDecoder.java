package dev.wyck.decode.worldgen.material;

import dev.wyck.worldgen.material.FluidState;
import dev.wyck.worldgen.material.FluidType;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class FluidStateDecoder implements Decodable<FluidState, net.minecraft.world.level.material.FluidState> {

    @Override
    public FluidState decode(net.minecraft.world.level.material.FluidState state) {
        if (state.isEmpty()) {
            return FluidState.empty();
        }

        FluidType fluid = FluidType.TRANSLATOR.fromNms(state.getType());
        boolean falling = state.hasProperty(FlowingFluid.FALLING) && state.getValue(FlowingFluid.FALLING);
        return state.isSource()
            ? FluidState.source(fluid, falling)
            : FluidState.flowing(fluid, state.getAmount(), falling);
    }
}
