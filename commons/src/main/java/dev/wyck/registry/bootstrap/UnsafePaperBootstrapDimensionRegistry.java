package dev.wyck.registry.bootstrap;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.WireFactory;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.DimensionRegistry;
import dev.wyck.util.ThrowingRunnable;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import io.papermc.paper.registry.PaperRegistries;
import io.papermc.paper.registry.RegistryHolder;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.WritableCraftRegistry;
import io.papermc.paper.registry.entry.RegistryEntry;
import io.papermc.paper.registry.entry.RegistryEntryMeta;
import io.papermc.paper.registry.event.RegistryComposeEventImpl;
import io.papermc.paper.registry.event.RegistryEventProvider;
import io.papermc.paper.registry.event.RegistryEventTypeProviderImpl;
import io.papermc.paper.registry.event.type.RegistryEntryAddEventType;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of {@link BootstrapDimensionRegistry} that registers custom dimension types
 * through Paper's {@link WritableCraftRegistry}, the same mechanism as
 * {@link UnsafePaperBootstrapBiomeRegistry}. Unsafe, not guaranteed to work on the next Java LTS.
 *
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class UnsafePaperBootstrapDimensionRegistry implements BootstrapDimensionRegistry {

    private final List<Dimension> pending = new ArrayList<>();
    private boolean installed = false;
    private @Nullable Throwable deferred;

    @Override
    @AsOf("2.4.0")
    public BootstrapDimensionRegistry queue(Dimension dimension) {
        this.pending.add(dimension);
        return this;
    }

    @Override
    @AsOf("2.4.0")
    public BootstrapDimensionRegistry install(BootstrapContext context) {
        Preconditions.checkState(!this.installed, "already installed");
        this.installed = true;

        try {
            RegistryKey<?> apiKey = dimensionTypeRegistryKey();

            // Give dimension_type an ADDABLE Paper entry whose holder is writable.
            makeDimensionTypeAddable(apiKey);

            // Subscribe to a compose event keyed to that entry.
            LifecycleEventType.Prioritizable<?, ?> eventType = composeEventType(apiKey);
            registerComposeHandler(context, eventType);
        } catch (Throwable t) {
            throw new RuntimeException("Wyck dimension bootstrap install failed", t);
        }
        return this;
    }

    @Override
    public BootstrapDimensionRegistry deferring(ThrowingRunnable action) {
        if (this.deferred == null) {
            try {
                action.run();
            } catch (Throwable t) {
                if (this.deferred == null) {
                    this.deferred = t;
                } else {
                    this.deferred.addSuppressed(t);
                }
            }
        }
        return this;
    }

    // Patchers

    /**
     * A {@link RegistryKey} for dimension_type. Prefers a real constant if Paper ever adds one;
     * otherwise mints one through the internal {@code RegistryKeyImpl.create(String)}, which also
     * seeds Paper's known-keys set so downstream validation accepts it.
     */
    private static RegistryKey<?> dimensionTypeRegistryKey() throws Exception {
        // Initialize RegistryKey before any reflection touches RegistryKeyImpl. Initializing the
        // impl class first triggers RegistryKey.<clinit> as its superinterface, which calls back
        // into RegistryKeyImpl.create while the impl's own statics are still null -> NPE, and the
        // failed clinit poisons RegistryKey for the whole JVM (killing PaperRegistries and boot).
        // Referencing a constant forces the natural init order every normal boot relies on.
        Preconditions.checkNotNull(RegistryKey.BIOME, "RegistryKey failed to initialize");

        for (Field field : RegistryKey.class.getFields()) {
            if (field.getName().equals("DIMENSION_TYPE")) {
                return (RegistryKey<?>) field.get(null);
            }
        }

        Class<?> impl = Class.forName("io.papermc.paper.registry.RegistryKeyImpl");
        for (Method method : impl.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())
                && RegistryKey.class.isAssignableFrom(method.getReturnType())
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0] == String.class) {
                method.setAccessible(true);
                return (RegistryKey<?>) method.invoke(null, "dimension_type");
            }
        }
        // last resort: direct record constructor with an adventure Key
        var ctor = impl.getDeclaredConstructor(net.kyori.adventure.key.Key.class);
        ctor.setAccessible(true);
        return (RegistryKey<?>) ctor.newInstance(net.kyori.adventure.key.Key.key("minecraft", "dimension_type"));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void makeDimensionTypeAddable(RegistryKey<?> apiKey) throws Exception {
        RegistryEntry existing = PaperRegistries.getEntry(Registries.DIMENSION_TYPE);
        RegistryEntry donor = existing != null ? existing : PaperRegistries.getEntry(RegistryKey.BIOME);
        if (donor == null) {
            throw new IllegalStateException("No donor RegistryEntry found (dimension_type and biome both absent)");
        }
        RegistryEntryMeta.ServerSide donorMeta = (RegistryEntryMeta.ServerSide) donor.meta();

        RegistryEntryMeta.Buildable buildable = new RegistryEntryMeta.Buildable(
            Registries.DIMENSION_TYPE,
            apiKey,
            org.bukkit.Keyed.class,
            donorMeta.registryTypeMapper(),
            donorMeta.serializationUpdater(),
            (_, _) -> {
                throw new UnsupportedOperationException("dimension_type is compose-only; entryAdd unsupported");
            },
            RegistryEntryMeta.RegistryModificationApiSupport.ADDABLE
        );

        RegistryEntry inner = new RegistryEntry() {
            @Override
            public RegistryHolder createRegistryHolder(Registry r) {
                return new RegistryHolder.Memoized<>(
                    () -> new WritableCraftRegistry<>((MappedRegistry) r, buildable));
            }

            @Override
            public RegistryEntryMeta meta() {
                return buildable;
            }
        };

        @SuppressWarnings("deprecation")
        RegistryEntry patched = inner.delayed();

        overwriteEntry("BY_REGISTRY_KEY", apiKey, patched);
        overwriteEntry("BY_RESOURCE_KEY", Registries.DIMENSION_TYPE, patched);
    }


    @SuppressWarnings({"unchecked", "rawtypes", "NonExtendableApiUsage"})
    private static LifecycleEventType.Prioritizable composeEventType(RegistryKey<?> apiKey) {
        RegistryEventProvider provider = new RegistryEventProvider() {
            @Override
            public RegistryKey registryKey() {
                return apiKey;
            }

            @Override
            public RegistryEntryAddEventType entryAdd() {
                return null;
            }

            @Override
            public LifecycleEventType.Prioritizable compose() {
                return RegistryEventTypeProviderImpl.instance().registryCompose(this);
            }
        };
        return provider.compose();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerComposeHandler(BootstrapContext context, LifecycleEventType.Prioritizable eventType) {
        context.getLifecycleManager().registerEventHandler(eventType, event -> {
            if (this.deferred != null) {
                throw new RuntimeException("Deferred custom dimension type failure; aborting startup", this.deferred);
            }
            try {
                RegistryComposeEventImpl impl = (RegistryComposeEventImpl) event;

                // The API writable registry wraps the live, pre-freeze NMS WritableRegistry<DimensionType>.
                WritableRegistry<net.minecraft.world.level.dimension.DimensionType> nms = findNmsWritable(impl.registry());

                // Single source of truth for dimension type construction (also used by runtime register()).
                DimensionRegistry wyckAPIRegistry = DimensionRegistry.registry();

                for (Dimension dimension : this.pending) {
                    net.minecraft.world.level.dimension.DimensionType built =
                        (net.minecraft.world.level.dimension.DimensionType) wyckAPIRegistry.buildDelegate(dimension);
                    net.minecraft.resources.ResourceKey<net.minecraft.world.level.dimension.DimensionType> key = nmsKeyOf(dimension);
                    if (!nms.containsKey(key)) {
                        nms.register(key, built, RegistrationInfo.BUILT_IN);
                    }
                    KeyChains.DIMENSIONS.append(dimension);
                }
                this.pending.clear();
            } catch (Throwable t) {
                throw new RuntimeException("Failed to inject custom dimension types during compose", t);
            }
        });
    }

    private static net.minecraft.resources.ResourceKey<net.minecraft.world.level.dimension.DimensionType> nmsKeyOf(Dimension dimension) {
        ResourceKey rk = dimension.resourceKey();
        return net.minecraft.resources.ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(rk.namespace(), rk.path()));
    }

    /**
     * BFS the object graph of the API writable registry to find the backing NMS
     * {@link WritableRegistry}. Defensive on purpose, avoids hard-coding Paper-internal field names.
     */
    @SuppressWarnings("unchecked")
    private static WritableRegistry<net.minecraft.world.level.dimension.DimensionType> findNmsWritable(Object root) throws Exception {
        Set<Object> seen = Collections.newSetFromMap(new IdentityHashMap<>());
        Deque<Object> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Object o = queue.poll();
            if (o == null || !seen.add(o)) {
                continue;
            }

            if (o instanceof WritableRegistry<?>) {
                return (WritableRegistry<net.minecraft.world.level.dimension.DimensionType>) o;
            }

            for (Class<?> c = o.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
                for (Field field : c.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    Class<?> type = field.getType();
                    if (type.isPrimitive() || type.getName().startsWith("java.")) {
                        continue;
                    }
                    field.setAccessible(true);
                    Object v = field.get(o);
                    if (v != null) {
                        queue.add(v);
                    }
                }
            }
        }
        throw new IllegalStateException("Could not locate NMS WritableRegistry<DimensionType> from " + root.getClass());
    }

    // Unsafe operations

    @SuppressWarnings({"unchecked", "removal"})
    private static void overwriteEntry(String fieldName, Object key, RegistryEntry<?, ?> patched) throws Exception {
        Field field = PaperRegistries.class.getDeclaredField(fieldName);

        sun.misc.Unsafe unsafe = getUnsafe();
        Object staticBase = unsafe.staticFieldBase(field);
        long staticOffset = unsafe.staticFieldOffset(field);

        Map<Object, Object> current = (Map<Object, Object>) unsafe.getObject(staticBase, staticOffset);
        Map<Object, Object> copy = new IdentityHashMap<>(current);
        copy.put(key, patched);

        unsafe.putObject(staticBase, staticOffset, Collections.unmodifiableMap(copy));
    }

    private static sun.misc.Unsafe getUnsafe() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (sun.misc.Unsafe) field.get(null);
    }
}