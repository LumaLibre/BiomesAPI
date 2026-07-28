package dev.wyck.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.data.MonsterSettings;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.environment.attribute.FriendlyColorSupplier;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.registry.DimensionRegistry;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.tags.TagKey;
import dev.wyck.tags.TagSet;
import dev.wyck.util.Either;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Wraps a minecraft dimension type.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface Dimension extends Keyed, Wrapper {

    @ApiStatus.Internal
    Decoder<Dimension> DECODER = Decoder.create("dev.wyck.decode.dimension.DimensionDecoder");

    /**
     * The key of this dimension type.
     * @return the key of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey resourceKey();

    /**
     * Whether this dimension type has a fixed time.
     * @return whether this dimension type has a fixed time.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasFixedTime();

    /**
     * If there should be light that comes from the sky.
     * @return whether this dimension type has sky light.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasSkyLight();

    /**
     * Whether this dimension type has a ceiling (e.g., the nether).
     * @return whether this dimension type has a ceiling.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasCeiling();

    /**
     * Whether this dimension type has an ender dragon fight.
     * @return whether this dimension type has an ender dragon fight.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasEnderDragonFight();

    /**
     * The coordinate scale of this dimension type.
     * @return the coordinate scale of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    double coordinateScale();

    /**
     * The minimum Y of this dimension type.
     * @return the minimum Y of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int minY();

    /**
     * The height of this dimension type.
     * @return the height of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int height();

    /**
     * The logical height of this dimension type.
     * @return the logical height of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int logicalHeight();

    /**
     * A tag of blocks that will burn infinitely in this dimension type.
     * @return the infiniburn of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Infiniburn infiniburn();

    /**
     * The ambient light of this dimension type.
     * @return the ambient light level of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    float ambientLight();

    /**
     * The monster settings of this dimension type.
     * @return the monster settings of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    MonsterSettings monsterSettings();

    /**
     * The skybox of this dimension type.
     * @return the skybox of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Skybox skybox();

    /**
     * The cardinal light type of this dimension type.
     * @return the cardinal light type of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    CardinalLightType cardinalLightType();

    /**
     * The attributes of this dimension type.
     * @return the attributes of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    EnvironmentAttributeMap attributes();

    /**
     * The timelines of this dimension type.
     * @return the timelines of this dimension type.
     * @since 3.2.0
     */
    @AsOf("3.2.0")
    TagSet<Timeline> timelines();

    /**
     * The default clock of this dimension type.
     * @return the default clock of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Optional<WorldClock> defaultClock();

    /**
     * Checks if this dimension type is similar to another.
     * @param other the other dimension type to compare to
     * @return whether this dimension type is similar to the other
     * @since 2.4.0
     */
    boolean isSimilar(Dimension other);

    /**
     * Registers this dimension type to the dimension registry.
     * @return this dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Dimension register() {
        DimensionRegistry.registry().register(this);
        return this;
    }

    /**
     * Modifies this dimension type in the dimension registry.
     * @return this dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Dimension modify() {
        DimensionRegistry.registry().modify(this);
        return this;
    }

    /**
     * Creates a new {@link Builder} with the same properties as this dimension type.
     * @return a new {@link Builder} with the same properties as this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new {@link Builder} with the given resource key.
     * @return a new {@link Builder} with the given resource key.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new {@link Builder} with the given resource key.
     * @param resourceKey the resource key to seed the builder with
     * @return a new {@link Builder} with the given resource key.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder(ResourceKey resourceKey) {
        return new Builder().resourceKey(resourceKey);
    }


    /**
     * Simply holds a reference to a dimension.
     * @param resourceKey the resource key of the dimension
     * @return a new dimension reference
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Dimension reference(ResourceKey resourceKey) {
        if (KeyChains.DIMENSIONS.isRegistered(resourceKey)) {
            return KeyChains.DIMENSIONS.getOrThrow(resourceKey);
        }
        return builder().resourceKey(resourceKey).build();
    }

    /**
     * Resolves this dimension's key in Minecraft's registry and decodes the registered value.
     * @return the decoded dimension type
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default Dimension wrap() {
        Object minecraft = WyckRegistry.of(RegistryId.DIMENSION_TYPE).retrieveOrThrow(resourceKey());
        return decode(minecraft);
    }

    /**
     * Reads a keyed Minecraft dimension-type holder.
     * @param minecraftDimension the dimension-type holder to read
     * @return the decoded dimension type
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Dimension decode(Object minecraftDimension) {
        return DECODER.decode(minecraftDimension);
    }
    
    
    /**
     * Builder for {@link Dimension}. Defaults approximate the vanilla overworld
     * so a minimally configured build is valid.
     *
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    class Builder {

        private @Nullable ResourceKey resourceKey = null;

        private boolean hasFixedTime = false;
        private boolean hasSkyLight = true;
        private boolean hasCeiling = false;
        private boolean hasEnderDragonFight = false;
        private double coordinateScale = 1.0D;
        private int minY = -64;
        private int height = 384;
        private int logicalHeight = 384;
        private Infiniburn infiniburn = Infiniburn.OVERWORLD;
        private float ambientLight = 0.0F;
        private MonsterSettings monsterSettings = MonsterSettings.overworld();
        private Skybox skybox = Skybox.OVERWORLD;
        private CardinalLightType cardinalLightType = CardinalLightType.DEFAULT;
        private EnvironmentAttributeMap attributes = EnvironmentAttributeMap.EMPTY;
        private Either<Set<Timeline>, TagKey> timelines = Either.left(new HashSet<>());
        private @Nullable WorldClock defaultClock = null;

        @AsOf("2.4.0")
        public Builder() {}

        @AsOf("2.4.0")
        public Builder(Dimension dimension) {
            this.resourceKey = dimension.resourceKey();
            this.hasFixedTime = dimension.hasFixedTime();
            this.hasSkyLight = dimension.hasSkyLight();
            this.hasCeiling = dimension.hasCeiling();
            this.coordinateScale = dimension.coordinateScale();
            this.minY = dimension.minY();
            this.height = dimension.height();
            this.logicalHeight = dimension.logicalHeight();
            this.infiniburn = dimension.infiniburn();
            this.ambientLight = dimension.ambientLight();
            this.monsterSettings = dimension.monsterSettings();
            this.skybox = dimension.skybox();
            this.cardinalLightType = dimension.cardinalLightType();
            this.attributes = dimension.attributes();
            this.timelines = dimension.timelines().value();
            this.defaultClock = dimension.defaultClock().orElse(null);
        }

        /**
         * Sets the resource key of this dimension type.
         * @param resourceKey the resource key of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets whether this dimension type has a fixed time.
         * @param hasFixedTime whether this dimension type has a fixed time.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder hasFixedTime(boolean hasFixedTime) {
            this.hasFixedTime = hasFixedTime;
            return this;
        }

        /**
         * If there should be light that comes from the sky.
         * @param hasSkyLight whether this dimension type has sky light.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder hasSkyLight(boolean hasSkyLight) {
            this.hasSkyLight = hasSkyLight;
            return this;
        }

        /**
         * Sets whether this dimension type has a ceiling (e.g., the nether).
         * @param hasCeiling whether this dimension type has a ceiling.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder hasCeiling(boolean hasCeiling) {
            this.hasCeiling = hasCeiling;
            return this;
        }

        /**
         * Sets whether this dimension type has an ender dragon fight.
         * @param hasEnderDragonFight whether this dimension type has an ender dragon fight.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder hasEnderDragonFight(boolean hasEnderDragonFight) {
            this.hasEnderDragonFight = hasEnderDragonFight;
            return this;
        }

        /**
         * Sets the coordinate scale of this dimension type.
         * @param coordinateScale the coordinate scale of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder coordinateScale(double coordinateScale) {
            this.coordinateScale = coordinateScale;
            return this;
        }

        /**
         * Sets the minimum Y of this dimension type.
         * @param minY the minimum Y of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder minY(int minY) {
            this.minY = minY;
            return this;
        }

        /**
         * Sets the height of this dimension type.
         * @param height the height of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the logical height of this dimension type.
         * @param logicalHeight the logical height of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder logicalHeight(int logicalHeight) {
            this.logicalHeight = logicalHeight;
            return this;
        }

        /**
         * Sets the infiniburn of this dimension type.
         * @param infiniburn the infiniburn of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder infiniburn(Infiniburn infiniburn) {
            this.infiniburn = infiniburn;
            return this;
        }

        /**
         * Sets the ambient light of this dimension type.
         * @param ambientLight the ambient light level of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder ambientLight(float ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the monster settings of this dimension type.
         * @param monsterSettings the monster settings of this dimension type.
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder monsterSettings(MonsterSettings monsterSettings) {
            this.monsterSettings = monsterSettings;
            return this;
        }

        /**
         * Sets the skybox of this dimension type.
         * @param skybox the skybox of this dimension type
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder skybox(Skybox skybox) {
            this.skybox = skybox;
            return this;
        }

        /**
         * Sets the cardinal light type of this dimension type.
         * @param cardinalLightType the cardinal light type of this dimension type
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder cardinalLightType(CardinalLightType cardinalLightType) {
            this.cardinalLightType = cardinalLightType;
            return this;
        }

        /**
         * Sets the attributes of this dimension type.
         * @param attributes the attributes of this dimension type
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder attributes(EnvironmentAttributeMap attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Adds an attribute to this dimension type.
         * @param supplier the attribute supplier
         * @param value the value of the attribute
         * @return this builder
         * @param <V> the type of the attribute value
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public <V> Builder attribute(EnvironmentAttributeSupplier<V> supplier, V value) {
            this.attributes = this.attributes.with(supplier, value);
            return this;
        }

        /**
         * Adds a color attribute to this dimension type.
         * @param supplier the color attribute supplier
         * @param hex the hex value of the color attribute
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder attribute(FriendlyColorSupplier supplier, String hex) {
            this.attributes = this.attributes.with(supplier, hex);
            return this;
        }

        /**
         * Sets the timelines of this dimension type.
         * @param timelines the timelines of this dimension type
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder timelines(Set<Timeline> timelines) {
            this.timelines = Either.left(timelines);
            return this;
        }

        /**
         * Sets the tagKey of timelines of this dimension type.
         * @param timelines the tagKey of timelines of this dimension type
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder timelines(TagKey timelines) {
            this.timelines = Either.right(timelines);
            return this;
        }

        /**
         * Sets the default clock of this dimension type.
         * @param defaultClock the default clock of this dimension type
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder defaultClock(@Nullable WorldClock defaultClock) {
            this.defaultClock = defaultClock;
            return this;
        }

        /**
         * Adds a timeline to this dimension type.
         * @param timeline the timeline to add
         * @return this builder
         * @since 3.2.0
         */
        @AsOf("3.2.0")
        public Builder timeline(Timeline timeline) {
            this.timelines = this.timelines.leftOrElse(new HashSet<>())
                .consumeLeft(set -> set.add(timeline));
            return this;
        }

        /**
         * Builds a new {@link Dimension} with the given properties.
         * @return a new {@link Dimension} with the given properties.
         * @throws IllegalArgumentException if the properties are invalid
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Dimension build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(height >= 16, "height has to be at least 16");
            Preconditions.checkArgument(minY + height <= 2032, "min_y + height cannot be higher than 2032");
            Preconditions.checkArgument(logicalHeight <= height, "logical_height cannot be higher than height");
            Preconditions.checkArgument(height % 16 == 0, "height has to be a multiple of 16");
            Preconditions.checkArgument(minY % 16 == 0, "min_y has to be a multiple of 16");

            return new DimensionImpl(
                resourceKey,
                hasFixedTime,
                hasSkyLight,
                hasCeiling,
                hasEnderDragonFight,
                coordinateScale,
                minY,
                height,
                logicalHeight,
                infiniburn,
                ambientLight,
                monsterSettings,
                skybox,
                cardinalLightType,
                attributes,
                TagSet.timelines(timelines),
                defaultClock
            );
        }

        @AsOf("2.4.0")
        public Dimension register() {
            return this.build().register();
        }

        @AsOf("3.0.0")
        public Dimension modify() {
            return this.build().modify();
        }
    }
}
