package dev.wyck.level.dimension.timeline.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.environment.attribute.modifier.AttributeOperation;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.clock.TimeMarker;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.AttributeTrack;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@NullMarked
@AsOf("3.2.0")
public interface ComposedTimeline extends Timeline, Registerable<ComposedTimeline> {

    /**
     * The world clock this timeline is based on.
     * @return the world clock this timeline is based on
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    WorldClock clock();

    /**
     * The length of one repetition of this timeline.
     * @return the length of one repetition of this timeline, or empty if the timeline should run once
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    Optional<Integer> periodTicks();

    /**
     * The attribute tracks of this timeline.
     * @return the attribute tracks of this timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    List<AttributeTrack<?>> tracks();

    /**
     * The time markers of this timeline.
     * @return the time markers of this timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    List<TimeMarker> timeMarkers();

    /**
     * Creates a new builder from this timeline.
     * @return a new builder from this timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new timeline.
     * @param key the key of the timeline
     * @param clock the world clock of the timeline
     * @param periodTicks the length of one repetition of the timeline, or null if the timeline should run once
     * @param tracks the attribute tracks of the timeline
     * @param timeMarkers the time markers of the timeline
     * @return a new timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ComposedTimeline of(ResourceKey key, WorldClock clock, @Nullable Integer periodTicks, List<AttributeTrack<?>> tracks, List<TimeMarker> timeMarkers) {
        record Holder() {
            static final ConstructWireProvider<ComposedTimeline> WIRE = WireProvider.construct("dev.wyck.*?.level.dimension.timeline.types.ComposedTimelineImpl");
        }
        return Holder.WIRE.construct(key, clock, Optional.ofNullable(periodTicks), List.copyOf(tracks), List.copyOf(timeMarkers));
    }

    /**
     * Creates a new timeline builder.
     * @return a new timeline builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static Builder builder() {
        return new Builder();
    }

    @AsOf("3.2.0")
    final class Builder {

        private @Nullable ResourceKey key;
        private WorldClock clock = WorldClock.OVERWORLD;
        private @Nullable Integer periodTicks;
        private final List<AttributeTrack<?>> tracks = new ArrayList<>();
        private final List<TimeMarker> timeMarkers = new ArrayList<>();

        Builder() {}

        Builder(ComposedTimeline timeline) {
            this.key = timeline.key();
            this.clock = timeline.clock();
            this.periodTicks = timeline.periodTicks().orElse(null);
            this.tracks.addAll(timeline.tracks());
            this.timeMarkers.addAll(timeline.timeMarkers());
        }

        @AsOf("3.2.0")
        public Builder key(ResourceKey key) {
            this.key = key;
            return this;
        }

        @AsOf("3.2.0")
        public Builder clock(WorldClock clock) {
            this.clock = clock;
            return this;
        }

        // The length of one repetition of this timeline. Leaving it unset makes the timeline run once.
        @AsOf("3.2.0")
        public Builder periodTicks(int periodTicks) {
            Preconditions.checkArgument(periodTicks > 0, "periodTicks must be positive");
            this.periodTicks = periodTicks;
            return this;
        }

        @AsOf("3.2.0")
        public Builder timeMarker(TimeMarker timeMarker) {
            this.timeMarkers.add(timeMarker);
            return this;
        }

        @AsOf("3.2.0")
        public Builder timeMarker(ResourceKey key, int ticks) {
            return timeMarker(TimeMarker.of(key, ticks));
        }

        @AsOf("3.2.0")
        public Builder timeMarker(ResourceKey key, int ticks, boolean showInCommands) {
            return timeMarker(TimeMarker.of(key, ticks, showInCommands));
        }

        @AsOf("3.2.0")
        public Builder track(AttributeTrack<?> track) {
            this.tracks.add(track);
            return this;
        }

        @AsOf("3.2.0")
        public <V> Builder track(EnvironmentAttributeSupplier<V> attribute, Consumer<AttributeTrack.Builder<V>> track) {
            return track(attribute, AttributeOperation.OVERRIDE, track);
        }

        @AsOf("3.2.0")
        @SuppressWarnings("unchecked")
        public <V> Builder track(EnvironmentAttributeSupplier<V> attribute, AttributeOperation operation, Consumer<AttributeTrack.Builder<V>> track) {
            AttributeTrack.Builder<?> builder = AttributeTrack.builder()
                .attribute(attribute)
                .operation(operation);
            track.accept((AttributeTrack.Builder<V>) builder);
            return track(builder.build());
        }

        @AsOf("3.2.0")
        public ComposedTimeline build() {
            Preconditions.checkArgument(this.key != null, "key must be set");
            return of(this.key, this.clock, this.periodTicks, this.tracks, this.timeMarkers);
        }

        @AsOf("3.2.0")
        public ComposedTimeline register() {
            return build().register();
        }
    }
}
