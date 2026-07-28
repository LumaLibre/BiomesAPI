package dev.wyck.registry;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.List;

/**
 * An interface for registering and modifying custom biomes on a Minecraft server.
 *
 * @apiNote Biomes cannot be removed from the Minecraft registry once they have been added.
 * You may {@link #modify(Biome...) modify} existing biomes to reduce memory footprint.
 *
 * @version 0.0.1
 * @since 0.0.1
 * @author Outspending
 */
@NullMarked
@AsOf("0.0.1")
public interface BiomeRegistry {

    @ApiStatus.Internal
    WireProvider<BiomeRegistry> WIRE = WireProvider.create("dev.wyck.registry.BiomeRegistryImpl");

    /**
     * This static method returns the current BiomeRegistry instance.
     * @return the current BiomeRegistry instance.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static BiomeRegistry registry() {
        return WIRE.get();
    }

    /**
     * Builds a custom biome into a minecraft biome.
     * @param biome the biome to build
     * @return the built biome
     */
    @AsOf("2.3.0")
    Object buildDelegate(Biome biome);

    /**
     * This method registers a custom biome to a Minecraft server.
     *
     * @since 0.0.1
     * @param biomes The biomes that should be registered to the server.
     */
    @AsOf("0.0.1")
    default void register(Biome... biomes) {
        register(List.of(biomes));
    }


    /**
     * Registers a collection of custom biomes to a Minecraft server.
     *
     * @param biomes The collection of Biome objects that should be registered to the server.
     * @since 2.3.1
     */
    @AsOf("2.3.1")
    void register(Collection<Biome> biomes);


    /**
     * This method modifies an existing biome on the Minecraft server.
     * Another biome must already exist with the same ResourceKey.
     * It takes a CustomBiome object as an argument.
     *
     * @throws IllegalArgumentException if the biome does not already exist in the registry.
     * @apiNote This method can only change the properties of an existing biome on the <b>server</b>.
     * Clients which have already received the biome will not see any changes until they enter the reconfiguration phase
     * (e.g., by rejoining the server.)
     * @since 0.0.8
     * @param biomes The AbstractBiomes that should internally be used to modify the existing biome.
     */
    @AsOf("0.0.8")
    default void modify(Biome... biomes) {
        modify(List.of(biomes));
    }

    /**
     * Modifies a collection of existing biomes on the Minecraft server.
     *
     * @param biomes The collection of AbstractBiomes that should internally be used to modify the existing biomes.
     * @since 2.3.1
     */
    @AsOf("2.3.1")
    void modify(Collection<Biome> biomes);


    /**
     * Gets an abstract biome from the registry.
     * @deprecated Use {@link Biome#reference(ResourceKey)} and {@link Biome#wrap()} instead.
     * @param key the key of the biome to get
     * @return the abstract biome
     * @param <T> the type of the biome
     * @since 2.3.0
     */
    @Deprecated
    @AsOf("2.3.0")
    <T extends Biome> T getBiome(ResourceKey key);

}
