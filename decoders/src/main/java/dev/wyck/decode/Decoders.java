package dev.wyck.decode;

import dev.wyck.keys.ResourceKey;
import dev.wyck.keys.ResourceKeyImpl;
import dev.wyck.util.WeightedList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Decoder util
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Internal
public final class Decoders {

    private Decoders() {
    }

    public static ResourceKey key(Identifier id) {
        return new ResourceKeyImpl(id);
    }

    public static ResourceKey key(net.minecraft.resources.ResourceKey<?> key) {
        return key(key.identifier());
    }

    /**
     * Reads the registry key from a holder.
     * @param holderOrValue the holder to read
     * @param family the family name used in failure messages
     * @return the holder's registry key
     */
    public static ResourceKey referenceKey(Object holderOrValue) {
        if (!(holderOrValue instanceof Holder<?> holder)) {
            throw new IllegalArgumentException("Reading requires a keyed Minecraft holder, not " + holderOrValue);
        }
        net.minecraft.resources.ResourceKey<?> key = holder.unwrapKey().orElseThrow(() ->
            new IllegalArgumentException("Inline values cannot be read until their decoders are implemented"));
        return key(key);
    }

    /**
     * Gets the key a value is registered under.
     * @param registry the Minecraft registry
     * @param value the registered value
     * @param family the family name used in failure messages
     * @return the value's registry key
     * @param <T> the registry value type
     */
    public static <T> ResourceKey registryKey(Registry<T> registry, T value) {
        Identifier id = registry.getKey(value);
        if (id == null) {
            throw new IllegalArgumentException(value + " is not in " + registry.key().identifier());
        }
        return key(id);
    }

    public static <E, W> WeightedList<W> weighted(net.minecraft.util.random.WeightedList<E> list, Function<E, W> decode) {
        List<WeightedList.Weighted<W>> entries = list.unwrap().stream()
            .map(entry -> new WeightedList.Weighted<>(decode.apply(entry.value()), entry.weight()))
            .toList();
        return WeightedList.of(entries);
    }

    public static EntityType bukkitEntityType(net.minecraft.world.entity.EntityType<?> type) {
        Identifier id = net.minecraft.world.entity.EntityType.getKey(type);
        NamespacedKey key = NamespacedKey.fromString(id.toString());
        if (key == null) {
            throw new IllegalArgumentException("Invalid entity type key '" + id + "'");
        }
        EntityType bukkit = org.bukkit.Registry.ENTITY_TYPE.get(key);
        if (bukkit == null) {
            throw new IllegalArgumentException("No Bukkit entity type is registered for '" + id + "'");
        }
        return bukkit;
    }

    public static BlockData blockData(net.minecraft.world.level.block.state.BlockState state) {
        return CraftBlockData.createData(state); // TODO: Test during bootstrap
    }

    public static Set<org.bukkit.Material> materials(HolderSet<net.minecraft.world.level.block.Block> blocks) {
        return blocks.stream()
            .map(Holder::value)
            .map(CraftBlockType::minecraftToBukkit)
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Unwraps a holder to its value.
     * @param holderOrValue a holder or direct value
     * @return the direct value
     * @param <T> the value type
     */
    public static <T> T value(Object holderOrValue) {
        @SuppressWarnings("unchecked")
        T unwrapped = holderOrValue instanceof Holder<?> holder ? (T) holder.value() : (T) holderOrValue;
        return unwrapped;
    }
}
