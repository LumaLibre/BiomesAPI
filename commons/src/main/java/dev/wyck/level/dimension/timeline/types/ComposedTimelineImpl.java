package dev.wyck.level.dimension.timeline.types;

import dev.wyck.util.attribute.AttributeModifiersUtil;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.clock.TimeMarker;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.AttributeTrack;
import dev.wyck.level.dimension.timeline.Keyframe;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.world.attribute.EnvironmentAttribute;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedTimelineImpl(
    @Override ResourceKey key,
    @Override WorldClock clock,
    @Override Optional<Integer> periodTicks,
    @Override List<AttributeTrack<?>> tracks,
    @Override List<TimeMarker> timeMarkers
) implements ComposedTimeline {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.TIMELINE);

    @Override
    public net.minecraft.world.timeline.Timeline toMinecraft() {
        Registry<net.minecraft.world.clock.WorldClock> clocks = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.WORLD_CLOCK);
        Identifier clockId = this.clock.key().identifier();
        Holder<net.minecraft.world.clock.WorldClock> clockHolder = clocks.get(clockId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown world clock: " + clockId));

        net.minecraft.world.timeline.Timeline.Builder builder = net.minecraft.world.timeline.Timeline.builder(clockHolder);
        this.periodTicks.ifPresent(builder::setPeriodTicks);
        for (TimeMarker timeMarker : this.timeMarkers) {
            builder.addTimeMarker(timeMarker.asHandle(), timeMarker.ticks(), timeMarker.showInCommands());
        }
        for (AttributeTrack<?> track : this.tracks) {
            applyTo(builder, track);
        }
        return builder.build();
    }

    @Override
    public ComposedTimeline register() {
        REGISTRY.get().register(this.key, this);
        return this;
    }

    private static <Value, Argument> void applyTo(net.minecraft.world.timeline.Timeline.Builder builder, AttributeTrack<?> track) {
        EnvironmentAttribute<Value> nms = track.attribute().asHandle();
        @SuppressWarnings("unchecked")
        AttributeTrack<Value> typedTrack = (AttributeTrack<Value>) track;
        var modifier = AttributeModifiersUtil.<Value, Argument>modifier(typedTrack);
        builder.addModifierTrack(nms, modifier, keyframes -> fill(keyframes, track));
    }

    private static <A> void fill(KeyframeTrack.Builder<A> keyframes, AttributeTrack<?> track) {
        keyframes.setEasing(track.easing().asHandle());
        for (Keyframe<?> keyframe : track.keyframes()) {
            keyframes.addKeyframe(keyframe.ticks(), argument(track, keyframe.value()));
        }
    }

    @SuppressWarnings("unchecked")
    private static <V, A> A argument(AttributeTrack<V> track, Object value) {
        return AttributeModifiersUtil.argument(track.attribute(), value);
    }
}
