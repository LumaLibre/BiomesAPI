package dev.wyck.v26_2.registry.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.biome.entity.data.MonsterSettings;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.util.attribute.EnvironmentAttributesUtil;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.InfiniburnImpl;
import dev.wyck.level.dimension.clock.WorldClockImpl;
import dev.wyck.registry.DimensionRegistry;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.dimension.DimensionType;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;


@NullMarked
@WireFactory
@AsOf("2.4.0")
@SuppressWarnings("unchecked")
public class DimensionTypeRegistry implements DimensionRegistry {

    private final Lazy<WyckRegistry> dimensionTypeRegistry = WyckRegistry.lazy(RegistryId.DIMENSION_TYPE);

    @Override
    @AsOf("2.4.0")
    public DimensionType buildDelegate(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");

        MonsterSettings ms = dimension.monsterSettings();
        DimensionType.MonsterSettings monsterSettings = new DimensionType.MonsterSettings(
            (IntProvider) ms.monsterSpawnLightTest().toMinecraft(),
            ms.monsterSpawnBlockLightLimit()
        );

        net.minecraft.world.attribute.EnvironmentAttributeMap.Builder attributeBuilder = net.minecraft.world.attribute.EnvironmentAttributeMap.builder();
        EnvironmentAttributeMap attributes = dimension.attributes();
        EnvironmentAttributesUtil.applyTo(attributeBuilder, attributes);

        InfiniburnImpl infiniburn = (InfiniburnImpl) dimension.infiniburn();
        Optional<Holder<WorldClock>> clockHolder = dimension.defaultClock()
            .map(clock -> ((WorldClockImpl) clock).toMinecraft());

        return new DimensionType(
            dimension.hasFixedTime(),
            dimension.hasSkyLight(),
            dimension.hasCeiling(),
            dimension.hasEnderDragonFight(),
            dimension.coordinateScale(),
            dimension.minY(),
            dimension.height(),
            dimension.logicalHeight(),
            infiniburn.asHolderSet(),
            dimension.ambientLight(),
            monsterSettings,
            dimension.skybox().toNms(net.minecraft.world.level.dimension.DimensionType.Skybox.class),
            dimension.cardinalLightType().toNms(CardinalLighting.Type.class),
            attributeBuilder.build(),
            dimension.timelines().asHolderSet(),
            clockHolder
        );
    }

    @Override
    @AsOf("2.4.0")
    @SuppressWarnings("unchecked")
    public void register(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");

        Identifier location = (Identifier) dimension.resourceKey().toMinecraft();
        net.minecraft.world.level.dimension.DimensionType built = buildDelegate(dimension);

        dimensionTypeRegistry.get().whileUnfrozen(() -> {
            Registry<net.minecraft.world.level.dimension.DimensionType> registry = (Registry<net.minecraft.world.level.dimension.DimensionType>) dimensionTypeRegistry.get().toMinecraft();
            if (!registry.containsKey(location)) {
                Registry.register(registry, location, built);
            }
        });
        KeyChains.DIMENSIONS.append(dimension);
    }

    @Override
    public void modify(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");
        ResourceKey key = dimension.resourceKey();

        modify(key, dimension);
    }

    @Override
    public void modify(ResourceKey key, Dimension newData) {
        Preconditions.checkNotNull(key, "key cannot be null");
        Preconditions.checkNotNull(newData, "newData cannot be null");

        Identifier id = (Identifier) key.toMinecraft();
        DimensionType rebuilt = buildDelegate(newData);

        Registry<DimensionType> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.DIMENSION_TYPE);
        Holder.Reference<DimensionType> reference = registry.get(id).orElseThrow(
            () -> new IllegalStateException("Dimension type " + key + " is not registered"));

        DimensionType old = reference.value();
        rebindHolder(reference, rebuilt);
        repairValueMaps(registry, old, rebuilt, reference);

        if (KeyChains.DIMENSIONS.isRegistered(key)) {
            KeyChains.DIMENSIONS.replace(key, newData);
        }
    }

    private static void rebindHolder(Holder.Reference<DimensionType> reference, DimensionType value) {
        try {
            Method bindValue = Holder.Reference.class.getDeclaredMethod("bindValue", Object.class);
            bindValue.setAccessible(true);
            bindValue.invoke(reference, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to rebind dimension type holder", e);
        }
    }

    private static void repairValueMaps(Registry<DimensionType> registry, DimensionType oldValue, DimensionType newValue, Holder.Reference<DimensionType> reference) {
        int id = registry.getId(oldValue);
        if (id == -1) {
            throw new IllegalStateException("Dimension type value has no id in registry " + registry.key());
        }

        try {
            Field toIdField = MappedRegistry.class.getDeclaredField("toId");
            toIdField.setAccessible(true);
            Map<Object, Integer> toId = (Map<Object, Integer>) toIdField.get(registry);
            toId.remove(oldValue);
            toId.put(newValue, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to repair dimension type toId map", e);
        }

        try {
            Field byValueField = MappedRegistry.class.getDeclaredField("byValue");
            byValueField.setAccessible(true);
            Map<Object, Holder.Reference<DimensionType>> byValue = (Map<Object, Holder.Reference<DimensionType>>) byValueField.get(registry);
            byValue.remove(oldValue);
            byValue.put(newValue, reference);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to repair dimension type byValue map", e);
        }
    }
}