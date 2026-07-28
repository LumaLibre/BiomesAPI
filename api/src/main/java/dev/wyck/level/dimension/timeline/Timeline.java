package dev.wyck.level.dimension.timeline;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.timeline.types.ComposedTimeline;
import dev.wyck.level.dimension.timeline.types.ReferencedTimeline;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@AsOf("3.2.0")
public interface Timeline extends Wrapper, Keyed {

    Timeline OVERWORLD_DAY = reference(ResourceKey.minecraft("day"));
    Timeline MOON = reference(ResourceKey.minecraft("moon"));
    Timeline VILLAGER_SCHEDULE = reference(ResourceKey.minecraft("villager_schedule"));
    Timeline EARLY_GAME = reference(ResourceKey.minecraft("early_game"));
    Set<Timeline> OVERWORLD = Set.of(OVERWORLD_DAY, MOON, VILLAGER_SCHEDULE);

    /**
     * The registry key of this timeline.
     * @return the registry key of this timeline
     * @since 3.2.0
     */
    @Override
    @AsOf("3.2.0")
    ResourceKey key();

    /**
     * Creates a timeline reference to the given timeline.
     * @param key the key of the timeline to reference
     * @return a timeline reference to the given timeline
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ReferencedTimeline reference(ResourceKey key) {
        return ReferencedTimeline.of(key);
    }

    /**
     * Resolves this timeline's key in Minecraft's registry and returns its registry-backed wrapper.
     * @return the registered timeline wrapper
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default Timeline wrap() {
        // just check if it exists
        WyckRegistry.of(RegistryId.TIMELINE).retrieveOrThrow(key());
        return reference(key());
    }

    /**
     * Creates a new timeline builder.
     * @return a new timeline builder
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    static ComposedTimeline.Builder builder() {
        return ComposedTimeline.builder();
    }
}
