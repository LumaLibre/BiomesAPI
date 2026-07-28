package dev.wyck.registry.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.stream.Stream;

@AsOf("2.4.0")
@ApiStatus.Experimental
public enum RegistryId {
    ACTIVITY("activity"),
    BIOME("worldgen/biome"),
    BLOCK("block"),
    CARVER("worldgen/carver"),
    CONFIGURED_CARVER("worldgen/configured_carver"),
    CONFIGURED_FEATURE("worldgen/configured_feature"),
    CLOCK_TIME_MARKER("clock_time_marker"),
    DENSITY_FUNCTION("worldgen/density_function"),
    DIMENSION_TYPE("dimension_type"),
    ENTITY_TYPE("entity_type"),
    ENVIRONMENT_ATTRIBUTE("environment_attribute"),
    FEATURE("worldgen/feature"),
    FLUID("fluid"),
    NOISE("worldgen/noise"),
    NOISE_SETTINGS("worldgen/noise_settings"),
    LEVEL_STEM("dimension"),
    PARTICLE_TYPE("particle_type"),
    PLACED_FEATURE("worldgen/placed_feature"),
    TIMELINE("timeline"),
    WORLD_CLOCK("world_clock");

    private final List<ResourceKey> keys;

    RegistryId(String... keys) {
        this.keys = Stream.of(keys).map(ResourceKey::minecraft).toList();
    }

    /**
     * The keys associated with this registry id.
     * @apiNote Internal API, this value may change at any time.
     * @return the keys associated with this registry id
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @ApiStatus.Internal
    public List<ResourceKey> keys() {
        return keys;
    }
}
