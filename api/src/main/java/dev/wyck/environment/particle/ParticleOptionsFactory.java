package dev.wyck.environment.particle;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Vibration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A factory for creating particle options handles.
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
@ApiStatus.Internal
public interface ParticleOptionsFactory {

    @ApiStatus.Internal
    WireProvider<ParticleOptionsFactory> WIRE = WireProvider.create("dev.wyck.*.environment.particle.ParticleOptionsFactoryImpl");

    /**
     * Creates a particle options handle for a block particle with the specified material.
     * @param type The particle type handle corresponding to a block particle type.
     * @param material The material to use for the block particle effect.
     * @return A ParticleOptionsHandle configured for the specified block particle type and material.
     */
    @AsOf("2.0.0")
    ParticleOptions block(ParticleType type, Material material);

    /**
     * Creates a particle options handle for a color particle with the specified RGB color.
     * @param type The particle type handle corresponding to a color particle type.
     * @param rgb The RGB color to use for the particle effect, represented as an integer (e.g., 0xRRGGBB).
     * @return A ParticleOptionsHandle configured for the specified color particle type and RGB color.
     */
    @AsOf("2.0.0")
    ParticleOptions color(ParticleType type, int rgb);

    /**
     * Creates a particle options handle for a dust particle with the specified RGB color and size.
     * @param rgb The RGB color to use for the particle effect, represented as an integer (e.g., 0xRRGGBB).
     * @param size The size of the dust particle effect.
     * @return A ParticleOptionsHandle configured for the specified dust particle type and RGB color and size.
     */
    @AsOf("2.0.0")
    ParticleOptions dust(int rgb, float size);

    /**
     * Creates a particle options handle for a dust transition particle with the specified RGB color and size.
     * @param fromRgb The starting RGB color for the dust transition effect, represented as an integer (e.g., 0xRRGGBB).
     * @param toRgb The ending RGB color for the dust transition effect, represented as an integer (e.g., 0xRRGGBB).
     * @param size The size of the dust transition effect.
     * @return A ParticleOptionsHandle configured for the specified dust transition particle type and RGB colors and size.
     */
    @AsOf("2.0.0")
    ParticleOptions dustTransition(int fromRgb, int toRgb, float size);

    /**
     * Creates a particle options handle for an item particle with the specified item stack.
     * @param type The particle type handle corresponding to an item particle type.
     * @param itemStack The item stack to use for the particle effect.
     * @return A ParticleOptionsHandle configured for the specified item particle type and item stack.
     */
    @AsOf("2.0.0")
    ParticleOptions item(ParticleType type, ItemStack itemStack);

    /**
     * Creates a particle options handle for a power particle with the specified power level.
     * @param type The particle type handle corresponding to a power particle type.
     * @param power The power level to use for the particle effect.
     * @return A ParticleOptionsHandle configured for the specified power particle type and power level.
     */
    @AsOf("2.0.0")
    ParticleOptions power(ParticleType type, float power);

    /**
     * Creates a particle options handle for a sculk charge particle with the specified roll.
     * @param roll The roll of the sculk charge effect.
     * @return A ParticleOptionsHandle configured for the specified sculk charge particle type and roll.
     */
    @AsOf("2.0.0")
    ParticleOptions sculkCharge(float roll);

    /**
     * Creates a particle options handle for a simple particle with the specified particle type.
     * @param type The particle type handle corresponding to a simple particle type.
     * @return A ParticleOptionsHandle configured for the specified simple particle type.
     */
    @AsOf("2.0.0")
    ParticleOptions simple(ParticleType type);

    /**
     * Creates a particle options handle for a spell particle with the specified particle type, RGB color, and power.
     * @param type The particle type handle corresponding to a spell particle type.
     * @param rgb The RGB color to use for the particle effect, represented as an integer (e.g., 0xRRGGBB).
     * @param power The power level to use for the particle effect.
     * @return A ParticleOptionsHandle configured for the specified spell particle type, RGB color, and power.
     */
    @AsOf("2.0.0")
    ParticleOptions spell(ParticleType type, int rgb, float power);

    /**
     * Creates a particle options handle for a trail particle with the specified target location, RGB color, and duration.
     * @param target The target location for the trail particle effect.
     * @param rgb The RGB color to use for the particle effect, represented as an integer (e.g., 0xRRGGBB).
     * @param duration The duration of the trail particle effect in ticks.
     * @return A ParticleOptionsHandle configured for the specified trail particle type, target location, RGB color, and duration.
     */
    @AsOf("2.0.0")
    ParticleOptions trail(Location target, int rgb, int duration);

    /**
     * Creates a particle options handle for a vibration particle with the specified destination and arrival time.
     * @param destination The destination of the vibration effect.
     * @param arrivalInTicks The arrival time of the vibration effect in ticks.
     * @return A ParticleOptionsHandle configured for the specified vibration particle type and destination and arrival time.
     */
    @AsOf("2.0.0")
    ParticleOptions vibration(Vibration.Destination destination, int arrivalInTicks);

    /**
     * Geyser particle
     * @param type The particle type handle corresponding to a geyser particle type.
     * @param waterBlocks The number of water blocks to use for the geyser effect.
     * @return A ParticleOptionsHandle configured for the geyser particle type and water blocks.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ParticleOptions geyser(ParticleType type, int waterBlocks);

    /**
     * Geyser particle base
     * @param type The particle type handle corresponding to a geyser particle type.
     * @param waterBlocks The number of water blocks to use for the geyser effect.
     * @param burstImpulseBase The base impulse for the burst effect.
     * @return A ParticleOptionsHandle configured for the geyser particle type and water blocks.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ParticleOptions geyserBase(ParticleType type, int waterBlocks, float burstImpulseBase);

    @AsOf("3.3.0")
    ParticleOptions shriek(int delay); // intnl so no javadocs, TODO: remove javadocs from the rest of the class

    /**
     * Retrieves a particle type handle by its key.
     * @param key The key of the particle type.
     * @return A ParticleTypeHandle corresponding to the particle type with the specified key.
     */
    @AsOf("2.0.0")
    ParticleType typeByKey(String key);
}