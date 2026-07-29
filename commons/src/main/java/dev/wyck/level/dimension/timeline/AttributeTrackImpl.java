package dev.wyck.level.dimension.timeline;

import dev.wyck.environment.attribute.EnvironmentAttribute;
import dev.wyck.util.attribute.AttributeModifiersUtil;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import net.minecraft.util.KeyframeTrack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("unchecked")
public record AttributeTrackImpl<V, A>(
    @Override EnvironmentAttribute<V> attribute,
    @Override AttributeOperation operation,
    @Override Easing easing,
    @Override List<Keyframe<?>> keyframes
) implements AttributeTrack<V> {

    @Override
    public net.minecraft.world.timeline.AttributeTrack<?, ?> toMinecraft() {
        var modifier = AttributeModifiersUtil.<V, A>modifier(this);
        net.minecraft.util.KeyframeTrack.Builder<A> keyframes = new KeyframeTrack.Builder<>();

        keyframes.setEasing(easing.asHandle());
        for (Keyframe<?> keyframe : this.keyframes) {
            keyframes.addKeyframe(keyframe.ticks(), AttributeModifiersUtil.argument(this.attribute, keyframe.value()));
        }
        return new net.minecraft.world.timeline.AttributeTrack<>(modifier, keyframes.build());
    }
}
