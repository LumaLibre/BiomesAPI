package dev.wyck.registry;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import org.jspecify.annotations.NullMarked;

/**
 * An interface for registering and modifying custom dimension types on a Minecraft server.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface DimensionRegistry {

    /**
     * @return the current DimensionRegistry instance.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static DimensionRegistry registry() {
        record Holder() {
            static final WireProvider<DimensionRegistry> WIRE = WireProvider.create("dev.wyck.*.registry.level.dimension.DimensionTypeRegistry");
        }
        return Holder.WIRE.get();
    }

    /**
     * Builds a {@link Dimension} into an NMS dimension type.
     * Performs no registration and touches no server state, so it is safe during bootstrap.
     *
     * @param dimension the dimension to build
     * @return the built NMS dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Object buildDelegate(Dimension dimension);

    /**
     * Registers a custom dimension type to the server's dimension_type registry.
     *
     * @param dimension the dimension to register
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void register(Dimension dimension);

    /**
     * Modifies a dimension type.
     * @param dimension the dimension to modify
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void modify(Dimension dimension);

    /**
     * Modifies a dimension type.
     * @param key the key of the dimension to modify
     * @param newData the new data for the dimension
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    void modify(ResourceKey key, Dimension newData);
}