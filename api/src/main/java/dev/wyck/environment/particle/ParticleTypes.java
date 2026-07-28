package dev.wyck.environment.particle;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.particle.options.BlockParticle;
import dev.wyck.environment.particle.options.ColorParticle;
import dev.wyck.environment.particle.options.DustParticle;
import dev.wyck.environment.particle.options.DustTransitionParticle;
import dev.wyck.environment.particle.options.GeyserBaseParticle;
import dev.wyck.environment.particle.options.GeyserParticle;
import dev.wyck.environment.particle.options.ItemParticle;
import dev.wyck.environment.particle.options.PowerParticle;
import dev.wyck.environment.particle.options.SculkChargeParticle;
import dev.wyck.environment.particle.options.ShriekParticle;
import dev.wyck.environment.particle.options.SpellParticle;
import dev.wyck.environment.particle.options.TrailParticle;
import dev.wyck.environment.particle.options.VibrationParticle;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.wrapper.RegisteredConstantTranslator;
import dev.wyck.wrapper.WrappedConstant;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum that wraps around Minecraft's ParticleTypes, providing additional metadata and functionality.
 * Each enum constant corresponds to a specific particle type in Minecraft.
 *
 * @see AmbientParticle
 * @see ParticleCatalog
 * @see ParticleData
 * @since 2.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public enum ParticleTypes implements WrappedConstant<ParticleTypes> {

    ANGRY_VILLAGER("angry_villager"),
    BLOCK("block", BlockParticle.class),
    BLOCK_MARKER("block_marker", BlockParticle.class),
    BUBBLE("bubble"),
    SULFUR_BUBBLES("sulfur_bubbles"),
    NOXIOUS_GAS("noxious_gas"),
    NOXIOUS_GAS_CLOUD("noxious_gas_cloud"),
    GEYSER("geyser", GeyserParticle.class),
    GEYSER_BASE("geyser_base", GeyserBaseParticle.class),
    GEYSER_POOF("geyser_poof", GeyserBaseParticle.class),
    GEYSER_PLUME("geyser_plume", GeyserParticle.class),
    CLOUD("cloud"),
    COPPER_FIRE_FLAME("copper_fire_flame"),
    CRIT("crit"),
    DAMAGE_INDICATOR("damage_indicator"),
    DRAGON_BREATH("dragon_breath", PowerParticle.class),
    DRIPPING_LAVA("dripping_lava"),
    FALLING_LAVA("falling_lava"),
    LANDING_LAVA("landing_lava"),
    DRIPPING_WATER("dripping_water"),
    FALLING_WATER("falling_water"),
    DUST("dust", DustParticle.class),
    DUST_COLOR_TRANSITION("dust_color_transition", DustTransitionParticle.class),
    EFFECT("effect", SpellParticle.class),
    ELDER_GUARDIAN("elder_guardian"),
    ENCHANTED_HIT("enchanted_hit"),
    ENCHANT("enchant"),
    END_ROD("end_rod"),
    ENTITY_EFFECT("entity_effect", ColorParticle.class),
    EXPLOSION_EMITTER("explosion_emitter"),
    EXPLOSION("explosion"),
    GUST("gust"),
    SMALL_GUST("small_gust"),
    GUST_EMITTER_LARGE("gust_emitter_large"),
    GUST_EMITTER_SMALL("gust_emitter_small"),
    SONIC_BOOM("sonic_boom"),
    FALLING_DUST("falling_dust", BlockParticle.class),
    FIREWORK("firework"),
    FISHING("fishing"),
    FLAME("flame"),
    INFESTED("infested"),
    CHERRY_LEAVES("cherry_leaves"),
    PALE_OAK_LEAVES("pale_oak_leaves"),
    TINTED_LEAVES("tinted_leaves", ColorParticle.class),
    SCULK_SOUL("sculk_soul"),
    SCULK_CHARGE("sculk_charge", SculkChargeParticle.class),
    SCULK_CHARGE_POP("sculk_charge_pop"),
    SOUL_FIRE_FLAME("soul_fire_flame"),
    SOUL("soul"),
    FLASH("flash", ColorParticle.class),
    HAPPY_VILLAGER("happy_villager"),
    COMPOSTER("composter"),
    HEART("heart"),
    INSTANT_EFFECT("instant_effect", SpellParticle.class),
    ITEM("item", ItemParticle.class),
    VIBRATION("vibration", VibrationParticle.class),
    TRAIL("trail", TrailParticle.class),
    PAUSE_MOB_GROWTH("pause_mob_growth"),
    RESET_MOB_GROWTH("reset_mob_growth"),
    ITEM_SLIME("item_slime"),
    ITEM_COBWEB("item_cobweb"),
    ITEM_SNOWBALL("item_snowball"),
    LARGE_SMOKE("large_smoke"),
    LAVA("lava"),
    MYCELIUM("mycelium"),
    NOTE("note"),
    POOF("poof"),
    PORTAL("portal"),
    RAIN("rain"),
    SMOKE("smoke"),
    WHITE_SMOKE("white_smoke"),
    SNEEZE("sneeze"),
    SPIT("spit"),
    SQUID_INK("squid_ink"),
    SWEEP_ATTACK("sweep_attack"),
    TOTEM_OF_UNDYING("totem_of_undying"),
    UNDERWATER("underwater"),
    SPLASH("splash"),
    WITCH("witch"),
    BUBBLE_POP("bubble_pop"),
    CURRENT_DOWN("current_down"),
    BUBBLE_COLUMN_UP("bubble_column_up"),
    NAUTILUS("nautilus"),
    DOLPHIN("dolphin"),
    CAMPFIRE_COSY_SMOKE("campfire_cosy_smoke"),
    CAMPFIRE_SIGNAL_SMOKE("campfire_signal_smoke"),
    DRIPPING_HONEY("dripping_honey"),
    FALLING_HONEY("falling_honey"),
    LANDING_HONEY("landing_honey"),
    FALLING_NECTAR("falling_nectar"),
    FALLING_SPORE_BLOSSOM("falling_spore_blossom"),
    ASH("ash"),
    CRIMSON_SPORE("crimson_spore"),
    WARPED_SPORE("warped_spore"),
    SPORE_BLOSSOM_AIR("spore_blossom_air"),
    DRIPPING_OBSIDIAN_TEAR("dripping_obsidian_tear"),
    FALLING_OBSIDIAN_TEAR("falling_obsidian_tear"),
    LANDING_OBSIDIAN_TEAR("landing_obsidian_tear"),
    REVERSE_PORTAL("reverse_portal"),
    WHITE_ASH("white_ash"),
    SMALL_FLAME("small_flame"),
    SNOWFLAKE("snowflake"),
    DRIPPING_DRIPSTONE_LAVA("dripping_dripstone_lava"),
    FALLING_DRIPSTONE_LAVA("falling_dripstone_lava"),
    DRIPPING_DRIPSTONE_WATER("dripping_dripstone_water"),
    FALLING_DRIPSTONE_WATER("falling_dripstone_water"),
    GLOW_SQUID_INK("glow_squid_ink"),
    GLOW("glow"),
    WAX_ON("wax_on"),
    WAX_OFF("wax_off"),
    ELECTRIC_SPARK("electric_spark"),
    SCRAPE("scrape"),
    SHRIEK("shriek", ShriekParticle.class),
    EGG_CRACK("egg_crack"),
    DUST_PLUME("dust_plume"),
    TRIAL_SPAWNER_DETECTED_PLAYER("trial_spawner_detection"),
    TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS("trial_spawner_detection_ominous"),
    VAULT_CONNECTION("vault_connection"),
    DUST_PILLAR("dust_pillar", BlockParticle.class),
    OMINOUS_SPAWNING("ominous_spawning"),
    RAID_OMEN("raid_omen"),
    TRIAL_OMEN("trial_omen"),
    BLOCK_CRUMBLE("block_crumble", BlockParticle.class),
    FIREFLY("firefly"),
    SULFUR_CUBE_GOO("sulfur_cube_goo");

    public static final RegisteredConstantTranslator<ParticleTypes> TRANSLATOR = RegisteredConstantTranslator.of(RegistryId.PARTICLE_TYPE, ParticleTypes::resourceKey, ParticleTypes.values());
    private static volatile @Nullable Map<String, ParticleTypes> BY_KEY;

    private final String key;
    private final @Nullable Class<? extends ParticleData> particleDataClass;
    private volatile @Nullable ParticleType cachedHandle;

    ParticleTypes(String key) {
        this(key, null);
    }

    ParticleTypes(String key, @Nullable Class<? extends ParticleData> particleDataClass) {
        this.key = key;
        this.particleDataClass = particleDataClass;
    }

    /**
     * The resource key of this particle.
     * @return the resource key of this particle.
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    public ResourceKey resourceKey() {
        return ResourceKey.minecraft(key);
    }

    @Override
    @AsOf("3.0.0")
    public RegisteredConstantTranslator<ParticleTypes> translator() {
        return TRANSLATOR;
    }

    /**
     * Gets a version-agnostic handle to this particle type, resolved on first access
     * via the registered factory and cached.
     */
    @AsOf("1.1.0")
    public ParticleType particleType() {
        if (cachedHandle == null) {
            cachedHandle = ParticleOptionsFactory.WIRE.get().typeByKey(key);
        }
        return cachedHandle;
    }

    @AsOf("1.1.0")
    public @Nullable Class<? extends ParticleData> getParticleDataClass() {
        return particleDataClass;
    }

    @AsOf("1.1.0")
    public boolean isSimple() {
        return particleDataClass == null;
    }

    /**
     * Returns the constant whose namespaced key matches (e.g. {@code "flame"}), or null if none.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    public static @Nullable ParticleTypes byKey(String key) {
        Map<String, ParticleTypes> map = BY_KEY;
        if (map == null) {
            synchronized (ParticleTypes.class) {
                map = BY_KEY;
                if (map == null) {
                    Map<String, ParticleTypes> built = new HashMap<>();
                    for (ParticleTypes type : values()) {
                        built.put(type.key, type);
                    }
                    BY_KEY = map = built;
                }
            }
        }
        return map.get(key);
    }

}