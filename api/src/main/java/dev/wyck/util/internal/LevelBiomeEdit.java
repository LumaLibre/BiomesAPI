package dev.wyck.util.internal;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.climate.ClimatePoint;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an edit to a dimension's biome distribution, either adding a biome at a climate point or replacing one biome with another.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@ApiStatus.Internal
public sealed interface LevelBiomeEdit permits LevelBiomeEdit.Add, LevelBiomeEdit.Replace {

    /**
     * The key of the dimension to edit.
     * @return the dimension key.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    ResourceKey dimension();

    /**
     * Adds a biome to a dimension.
     * @param dimension the dimension to add the biome to
     * @param biome the biome to add
     * @param point the climate point of the biome
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Add(ResourceKey dimension, ResourceKey biome, ClimatePoint point) implements LevelBiomeEdit {}

    /**
     * Replaces a biome in a dimension.
     * @param dimension the dimension to replace the biome in
     * @param target the biome to replace
     * @param replacement the biome to replace with
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Replace(ResourceKey dimension, ResourceKey target, ResourceKey replacement) implements LevelBiomeEdit {}
}