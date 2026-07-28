package dev.wyck.decode.worldgen.heightproviders;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.heightproviders.AboveBottom;
import dev.wyck.worldgen.heightproviders.Absolute;
import dev.wyck.worldgen.heightproviders.BelowTop;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.wrapper.decode.DecoderRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class VerticalAnchorDecoders extends DecoderRegistry<VerticalAnchor, net.minecraft.world.level.levelgen.VerticalAnchor> {

    public VerticalAnchorDecoders() {
        register("absolute", nms -> Absolute.of(((net.minecraft.world.level.levelgen.VerticalAnchor.Absolute) nms).y()));
        register("above_bottom", nms -> AboveBottom.of(((net.minecraft.world.level.levelgen.VerticalAnchor.AboveBottom) nms).offset()));
        register("below_top", nms -> BelowTop.of(((net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop) nms).offset()));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.VerticalAnchor minecraftObject) {
        // no registry
        return switch (minecraftObject) {
            case net.minecraft.world.level.levelgen.VerticalAnchor.Absolute _ -> ResourceKey.minecraft("absolute");
            case net.minecraft.world.level.levelgen.VerticalAnchor.AboveBottom _ -> ResourceKey.minecraft("above_bottom");
            case net.minecraft.world.level.levelgen.VerticalAnchor.BelowTop _ -> ResourceKey.minecraft("below_top");
            default -> throw new IllegalArgumentException(minecraftObject.getClass().getName() + " is not a Minecraft vertical anchor");
        };
    }
}
