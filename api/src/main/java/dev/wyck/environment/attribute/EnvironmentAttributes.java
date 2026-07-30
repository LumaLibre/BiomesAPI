package dev.wyck.environment.attribute;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.Activity;
import dev.wyck.environment.BedRule;
import dev.wyck.environment.MoonPhase;
import dev.wyck.environment.TriState;
import dev.wyck.environment.particle.AmbientParticle;
import dev.wyck.environment.particle.ParticleCatalog;
import dev.wyck.environment.particle.ParticleOptions;
import dev.wyck.environment.sounds.AmbientSounds;
import dev.wyck.environment.sounds.BackgroundMusic;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.WrappedConstant;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A registry of wrapped environment attribute suppliers,
 * which provide access to the various attributes that can be defined for biomes.
 *
 * @since 1.1.0
 * @version 2.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public final class EnvironmentAttributes {

    /** Color of the fog when underwater (also affected by time of day, weather, and potion effects) */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier FOG_COLOR = colorSupplier("visual/fog_color");

    /** Distance in blocks from where the fog starts to have an effect. If negative, fog already exists at 0 distance. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> FOG_START_DISTANCE = supplier("visual/fog_start_distance");

    /** Distance in blocks when the fog affecting the clouds reaches maximum density.
     * (also affected by the cloud distance option) */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> FOG_END_DISTANCE = supplier("visual/fog_end_distance");

    /** Distance in blocks when the fog affecting the sky reaches maximum density. (also affected by the render distance) */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> SKY_FOG_END_DISTANCE = supplier("visual/sky_fog_end_distance");

    /** Distance in blocks when the fog affecting the clouds reaches maximum density.
     * (also affected by the cloud distance option) */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> CLOUD_FOG_END_DISTANCE = supplier("visual/cloud_fog_end_distance");

    /** Color of the fog when underwater (also affected by time of day, weather, and potion effects) */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier WATER_FOG_COLOR = colorSupplier("visual/water_fog_color");

    /** Distance in blocks from where the underwater fog starts to have an effect.
     * If negative, fog already exists at 0 distance.
     * (also affected by how long the player has been underwater) */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> WATER_FOG_START_DISTANCE = supplier("visual/water_fog_start_distance");

    /** Distance in blocks when the underwater fog reaches maximum density
     * (also affected by how long the player has been underwater) */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> WATER_FOG_END_DISTANCE = supplier("visual/water_fog_end_distance");

    /** Color of the sky
     * (also affected by time of day and weather) */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier SKY_COLOR = colorSupplier("visual/sky_color");

    /** Color and intensity of sunrise and sunset. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier SUNRISE_SUNSET_COLOR = colorSupplier("visual/sunrise_sunset_color");

    /** Opacity of the clouds.
     * If fully transparent, Happy Ghast does not regenerate health faster when at cloud height. */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier CLOUD_COLOR = colorSupplier("visual/cloud_color");

    /** Height of the clouds */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> CLOUD_HEIGHT = supplier("visual/cloud_height");

    /** Position of the sun in degrees from east to west. 0 is directly up. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> SUN_ANGLE = supplier("visual/sun_angle");

    /** Position of the moon in degrees from east to west. 0 is directly up. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> MOON_ANGLE = supplier("visual/moon_angle");

    /** Position of the sun in degrees from east to west. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> STAR_ANGLE = supplier("visual/star_angle");

    /** Phase of the moon. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<MoonPhase> MOON_PHASE = supplierWith("visual/moon_phase", it -> it.toNms("net.minecraft.world.level.MoonPhase"));

    /** Brightness of the stars. 0.5 is the default night star brightness. Only used with the overworld skybox type. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> STAR_BRIGHTNESS = supplier("visual/star_brightness");

    /** Tint of the block light.
     * Block light color starts as dark gray at low light levels,
     * becomes tinted by this attribute at medium levels, and turns white at high levels.
     * By default, it provides the yellowish tint of torches.*/
    @AsOf("2.1.0")
    public static final FriendlyColorSupplier BLOCK_LIGHT_TINT = colorSupplier("visual/block_light_tint");

    /** Color of the skylight.
     * Has no effect on blocks with a skylight of 0.
     * This value is passed to the lightmap.fsh shader as SkyLightColor */
    @AsOf("1.1.0")
    public static final FriendlyColorSupplier SKY_LIGHT_COLOR = colorSupplier("visual/sky_light_color");

    /** Visual brightness of the skylight.
     * The skylight color is multiplied with this value and passed to the lightmap.fsh shader as SkyFactor */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> SKY_LIGHT_FACTOR = supplier("visual/sky_light_factor");

    /** Used similarly to ambient light color
     * When the night vision effect is active,
     * a per-component maximum of visual/night_vision_color and visual/ambient_light_color is used as ambient color.
     * Night Vision is not tinted by default. */
    @AsOf("2.1.0")
    public static final FriendlyColorSupplier NIGHT_VISION_COLOR = colorSupplier("visual/night_vision_color");

    /** Defines both the ambient light tint and brightness
     This light is applied to the world at 0 light levels.
     Block and skylight are added on top of it. */
    @AsOf("2.1.0")
    public static final FriendlyColorSupplier AMBIENT_LIGHT_COLOR = colorSupplier("visual/ambient_light_color");

    /** The particle generated by downward pointed dripstone when no liquid is above. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<ParticleOptions> DEFAULT_DRIPSTONE_PARTICLE = supplierWith("visual/default_dripstone_particle", ParticleOptions::toMinecraft);

    /** Particles that spawn randomly around the camera where probability is the chance to spawn a particle in an empty space */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<ParticleCatalog> AMBIENT_PARTICLES = supplierWith("visual/ambient_particles", it -> it.particles().stream().map(AmbientParticle::toMinecraft).toList());

    /** Volume of the music. Music volume will fade over time to this value. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> MUSIC_VOLUME = supplier("audio/music_volume");

    /** Whether a firefly bush plays ambient sounds when not under an opaque block. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> FIREFLY_BUSH_SOUNDS = supplier("audio/firefly_bush_sounds");

    /** The internal skylight level (inversely known as the "darkening" skylight level), not the actual skylight level.
     * This affects mechanics such as monster spawning, daylight detector power, and ice melting.
     * The visual effect is controlled by visual/sky_light_factor. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> SKY_LIGHT_LEVEL = supplier("gameplay/sky_light_level");

    /** Whether a player with Raid Omen can start a raid. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> CAN_START_RAID = supplier("gameplay/can_start_raid");

    /** Whether water can be placed from a bucket, or by melting ice, or whether a wet sponge will dry out. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> WATER_EVAPORATES = supplier("gameplay/water_evaporates");

    /** Controls behavior of interacting with beds. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<BedRule> BED_RULE = supplierWith("gameplay/bed_rule", BedRule::toMinecraft);

    /** Whether a respawn anchor can be used to set spawn. If set to false, a respawn anchor explodes once used. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> RESPAWN_ANCHOR_WORKS = supplier("gameplay/respawn_anchor_works");

    /** Whether a nether portal can spawn zombified piglins. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> NETHER_PORTAL_SPAWNS_PIGLIN = supplier("gameplay/nether_portal_spawns_piglin");
    /** Whether lava should spread faster and further, and have a stronger pushing force on entities. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> FAST_LAVA = supplier("gameplay/fast_lava");

    /** Whether fire should burn out more quickly. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> INCREASED_FIRE_BURNOUT = supplier("gameplay/increased_fire_burnout");

    /** Chance for a turtle egg to progress to its next hatching state on a random tick. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> TURTLE_EGG_HATCH_CHANCE = supplier("gameplay/turtle_egg_hatch_chance");

    /** Whether piglins and hoglins zombify. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> PIGLINS_ZOMBIFY = supplier("gameplay/piglins_zombify");

    /** Whether snow golems should take damage passively. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> SNOW_GOLEM_MELTS = supplier("gameplay/snow_golem_melts");

    /** When true, a creaking heart will become active. When false, it will become inactive. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> CREAKING_ACTIVE = supplier("gameplay/creaking_active");

    /** Chance of additional slime spawn on the surface in biomes in the #allows_surface_slime_spawns tag. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> SURFACE_SLIME_SPAWN_CHANCE = supplier("gameplay/surface_slime_spawn_chance");

    /** Chance of a cat dropping a gift when the player wakes up. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Float> CAT_WAKING_UP_GIFT_CHANCE = supplier("gameplay/cat_waking_up_gift_chance");

    /** When true, bees will pathfind to beehives and not exit (except when the Hive is broken or next to fire). */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> BEES_STAY_IN_HIVE = supplier("gameplay/bees_stay_in_hive");

    /** Whether monsters burn when exposed to the sky. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> MONSTERS_BURN = supplier("gameplay/monsters_burn");

    /** Whether patrols can spawn when the global skylight is greater than 11. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Boolean> CAN_PILLAGER_PATROL_SPAWN = supplier("gameplay/can_pillager_patrol_spawn");

    /** Controls the default AI activity of villagers.
     * Core, hide, idle, meet, panic, pre_raid, raid, rest, or work.
     * Other activities are allowed causing the villager to do nothing. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Activity> VILLAGER_ACTIVITY = supplierWith("gameplay/villager_activity", WrappedConstant::toNms);

    /** Controls the default AI activity of baby villagers.
     * Core, hide, idle, meet, panic, pre_raid, raid, rest, or work.
     * Other activities are allowed causing the villager to do nothing. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<Activity> BABY_VILLAGER_ACTIVITY = supplierWith("gameplay/baby_villager_activity", WrappedConstant::toNms);

    /** The background music to play. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<BackgroundMusic> BACKGROUND_MUSIC = supplierWith("audio/background_music", BackgroundMusic::toMinecraft);

    /** Controls ambient sounds that are played around the camera. */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<AmbientSounds> AMBIENT_SOUNDS = supplierWith("audio/ambient_sounds", AmbientSounds::toMinecraft);

    /**
     * How eyeblossoms should behave.
     * <p>True: eyeblossoms will open
     * <p>False: eyeblossoms will close
     * <p>Default: eyeblossom will stay in their current state */
    @AsOf("1.1.0")
    public static final EnvironmentAttributeSupplier<TriState> EYEBLOSSOM_OPEN = supplierWith("gameplay/eyeblossom_open", it -> it.toNms("net.minecraft.util.TriState"));


    private static @Nullable Map<String, EnvironmentAttributeSupplier<?>> BY_ID;
    private static volatile @Nullable Map<String, EnvironmentAttributeSupplier<?>> BY_NAME;

    /**
     * Returns the supplier registered under the given NMS attribute id (e.g. {@code "visual/fog_color"}),
     * or null if none exists.
     */
    @AsOf("2.1.0")
    public static @Nullable EnvironmentAttributeSupplier<?> byId(String id) {
        return byIdMap().get(id);
    }

    /**
     * Returns the supplier whose constant name matches (e.g. {@code "FOG_COLOR"}), or null if none exists.
     */
    @AsOf("2.1.0")
    public static @Nullable EnvironmentAttributeSupplier<?> byName(String name) {
        Map<String, EnvironmentAttributeSupplier<?>> map = BY_NAME;
        if (map == null) {
            synchronized (EnvironmentAttributes.class) {
                map = BY_NAME;
                if (map == null) {
                    map = buildNameIndex();
                    BY_NAME = map;
                }
            }
        }
        return map.get(name);
    }

    /**
     * All registered suppliers (excluding Unmapped). Iteration order matches declaration order.
     */
    @AsOf("2.1.0")
    public static Collection<EnvironmentAttributeSupplier<?>> values() {
        return Collections.unmodifiableCollection(byIdMap().values());
    }

    /**
     * All registered ids (excluding Unmapped). Iteration order matches declaration order.
     */
    @AsOf("2.1.0")
    public static Collection<String> ids() {
        return Collections.unmodifiableCollection(byIdMap().keySet());
    }

    private static <V> EnvironmentAttributeSupplier<V> supplier(String key) {
        EnvironmentAttributeSupplier<V> supplier = EnvironmentAttribute.ofSupplier(ResourceKey.minecraft(key));
        byIdMap().put(key, supplier);
        return supplier;
    }

    private static <V, U> EnvironmentAttributeSupplier<V> supplierWith(String key, EnvironmentAttribute.Converter<V, U> converter) {
        EnvironmentAttributeSupplier<V> supplier = EnvironmentAttribute.ofSupplier(ResourceKey.minecraft(key), converter);
        byIdMap().put(key, supplier);
        return supplier;
    }

    private static FriendlyColorSupplier colorSupplier(String key) {
        FriendlyColorSupplier supplier = EnvironmentAttribute.ofFriendlyColorSupplier(ResourceKey.minecraft(key));
        byIdMap().put(key, supplier);
        return supplier;
    }

    private static Map<String, EnvironmentAttributeSupplier<?>> byIdMap() {
        Map<String, EnvironmentAttributeSupplier<?>> map = BY_ID;
        if (map == null) {
            map = new LinkedHashMap<>();
            BY_ID = map;
        }
        return map;
    }

    private static Map<String, EnvironmentAttributeSupplier<?>> buildNameIndex() {
        Map<String, EnvironmentAttributeSupplier<?>> map = new LinkedHashMap<>();
        collectStaticFields(map);
        return Collections.unmodifiableMap(map);
    }

    private static void collectStaticFields(Map<String, EnvironmentAttributeSupplier<?>> out) {
        for (Field field : EnvironmentAttributes.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!EnvironmentAttributeSupplier.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                Object value = field.get(null);
                if (value instanceof EnvironmentAttributeSupplier<?> supplier) {
                    out.put(field.getName(), supplier);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private EnvironmentAttributes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}