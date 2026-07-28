package dev.wyck.decode.dimension;

import dev.wyck.biome.entity.data.MonsterSettings;
import dev.wyck.decode.Decoders;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.CardinalLightType;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.Infiniburn;
import dev.wyck.level.dimension.Skybox;
import dev.wyck.level.dimension.clock.WorldClock;
import dev.wyck.level.dimension.timeline.Timeline;
import dev.wyck.level.dimension.timeline.types.ReferencedTimeline;
import dev.wyck.tags.TagKey;
import dev.wyck.tags.TagSet;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashSet;
import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class DimensionDecoder implements Decodable<Dimension, Object> {
    @Override
    public Dimension decode(Object minecraftObject) { // TODO: remove holder support?
        net.minecraft.world.level.dimension.DimensionType type = Decoders.value(minecraftObject);
        ResourceKey key = minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()
            ? Decoders.referenceKey(holder)
            : BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DIMENSION_TYPE).getResourceKey(type)
                .map(Decoders::key)
                .orElseThrow(() -> new IllegalArgumentException("The dimension type is not registered: " + type));
        Dimension.Builder builder = Dimension.builder(key)
            .hasFixedTime(type.hasFixedTime())
            .hasSkyLight(type.hasSkyLight())
            .hasCeiling(type.hasCeiling())
            .hasEnderDragonFight(type.hasEnderDragonFight())
            .coordinateScale(type.coordinateScale())
            .minY(type.minY())
            .height(type.height())
            .logicalHeight(type.logicalHeight())
            .infiniburn(Infiniburn.of(TagSet.decodeBlocks(type.infiniburn())))
            .ambientLight(type.ambientLight())
            .monsterSettings(new MonsterSettings(
                IntProvider.decode(type.monsterSettings().monsterSpawnLightTest()),
                type.monsterSettings().monsterSpawnBlockLightLimit()))
            .skybox(Skybox.TRANSLATOR.fromNms(type.skybox()))
            .cardinalLightType(CardinalLightType.TRANSLATOR.fromNms(type.cardinalLightType()))
            .attributes(EnvironmentAttributeMap.decode(type.attributes()));

        type.timelines().unwrapKey().ifPresentOrElse(
            tag -> builder.timelines(TagKey.timelines(Decoders.key(tag.location()))),
            () -> builder.timelines(timelines(type.timelines()))
        );
        type.defaultClock().ifPresent(clock -> builder.defaultClock(
            WorldClock.of(Decoders.referenceKey(clock))
        ));
        return builder.build();
    }

    private static Set<Timeline> timelines(HolderSet<net.minecraft.world.timeline.Timeline> holders) {
        return holders.stream()
            .map(holder -> ReferencedTimeline.of(Decoders.referenceKey(holder)))
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }
}
