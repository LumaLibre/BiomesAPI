package dev.wyck.registry.bootstrap;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.keys.KeyChains;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.registry.BiomeRegistry;
import dev.wyck.util.ThrowingRunnable;
import dev.wyck.worldgen.climate.ClimatePoint;
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
 * Implementation of {@link BootstrapBiomeRegistry} that registers custom biomes through Paper's
 * {@link WritableCraftRegistry}. Unsafe, not guaranteed to work on the next Java LTS.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
@ApiStatus.Internal
@SuppressWarnings("UnstableApiUsage")
public final class UnsafePaperBootstrapBiomeRegistry implements BootstrapBiomeRegistry {

    private final List<Biome> pending = new ArrayList<>();
    private boolean installed = false;
    private @Nullable Throwable deferred;

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry queue(Biome biome) {
        this.pending.add(biome);
        return this;
    }

    @Override
    @AsOf("2.3.0")
    public BootstrapBiomeRegistry install(BootstrapContext context) {
        Preconditions.checkState(!this.installed, "already installed");
        this.installed = true;

        try {
            // Flip biome's entry from read-only Craft -> ADDABLE Buildable whose holder is writable.
            makeBiomeAddable();

            // Subscribe to the new biome compose event
            LifecycleEventType.Prioritizable<?, ?> eventType = biomeComposeEventType();
            registerComposeHandler(context, eventType);
        } catch (Throwable t) {
            throw new RuntimeException("Wyck bootstrap install failed", t);
        }
        return this;
    }

    @Override
    public BootstrapBiomeRegistry deferring(ThrowingRunnable action) {
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void makeBiomeAddable() throws Exception {
        RegistryEntry original = PaperRegistries.getEntry(RegistryKey.BIOME);
        if (original == null) {
            throw new IllegalStateException("No biome RegistryEntry found in PaperRegistries");
        }

        // original is the stock biome entry (a .delayed() wrapper). Its meta() exposes the Craft
        // ServerSide (mapper + serialization updater) we reuse to build an equivalent Buildable.
        RegistryEntryMeta.ServerSide craftMeta = (RegistryEntryMeta.ServerSide) original.meta();

        RegistryEntryMeta.Buildable buildable = new RegistryEntryMeta.Buildable(
            craftMeta.mcKey(),
            craftMeta.apiKey(),
            craftMeta.classToPreload(),
            craftMeta.registryTypeMapper(),
            craftMeta.serializationUpdater(),
            // compose-only: never invoked, since we never register an entryAdd handler for biome
            (_, _) -> {
                throw new UnsupportedOperationException("biome is compose-only; entryAdd unsupported");
            },
            RegistryEntryMeta.RegistryModificationApiSupport.ADDABLE
        );

        // createRegistryHolder is called by RegistryHolder.Delayed.loadFrom during load, with the
        // REAL, populated NMS biome MappedRegistry. The Memoized supplier resolves lazily (inside
        // runFreezeListeners), so we wrap the genuine registry the server uses. Producing a
        // WritableCraftRegistry is what makes PaperRegistryAccess.getWritableRegistry succeed.
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

        // .delayed() is required: at org.bukkit.Registry.<clinit> the registry doesn't exist yet,
        // and only a DelayedRegistryEntry is tolerated there (it seeds an empty Delayed holder
        // instead of eagerly creating the registry). Without it: "No registry present for Biome".
        @SuppressWarnings("deprecation") // TODO: Find replacement for .delayed()
        RegistryEntry patched = inner.delayed();

        overwriteEntry("BY_REGISTRY_KEY", RegistryKey.BIOME, patched);
        overwriteEntry("BY_RESOURCE_KEY", Registries.BIOME, patched);
    }


    @SuppressWarnings({"unchecked", "rawtypes", "NonExtendableApiUsage"})
    private static LifecycleEventType.Prioritizable biomeComposeEventType() {
        RegistryEventProvider provider = new RegistryEventProvider() {
            @Override
            public RegistryKey registryKey() {
                return RegistryKey.BIOME;
            }

            @Override
            public RegistryEntryAddEventType entryAdd() {
                return null; // never used for biome (compose-only)
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
                throw new RuntimeException("Deferred custom biome failure; aborting startup", this.deferred);
            }
            try {
                RegistryComposeEventImpl impl = (RegistryComposeEventImpl) event;

                // The API writable registry wraps the live, pre-freeze NMS WritableRegistry<Biome>.
                WritableRegistry<net.minecraft.world.level.biome.Biome> nms = findNmsWritable(impl.registry());

                // Single source of truth for biome construction (also used by runtime register()).
                BiomeRegistry wyckAPIRegistry = BiomeRegistry.registry();

                for (Biome cb : this.pending) {
                    net.minecraft.world.level.biome.Biome built = (net.minecraft.world.level.biome.Biome) wyckAPIRegistry.buildDelegate(cb);
                    net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> key = nmsKeyOf(cb);
                    if (!nms.containsKey(key)) {
                        nms.register(key, built, RegistrationInfo.BUILT_IN);
                    }
                    KeyChains.BIOMES.append(cb);
                }
                this.pending.clear();
            } catch (Throwable t) {
                throw new RuntimeException("Failed to inject custom biomes during compose", t);
            }
        });
    }


    @Override
    public BootstrapBiomeRegistry addToDimension(Dimension dimension, Biome target, ClimatePoint placement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BootstrapBiomeRegistry replaceInDimension(Dimension dimension, Biome target, Biome replacement) {
        throw new UnsupportedOperationException();
    }

    private static net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> nmsKeyOf(Biome cb) {
        ResourceKey rk = cb.resourceKey();
        return net.minecraft.resources.ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(rk.namespace(), rk.path()));
    }

    /**
     * BFS the object graph of the API writable registry to find the backing NMS
     * {@link WritableRegistry}. Defensive on purpose, avoids hard-coding Paper-internal field names.
     */
    @SuppressWarnings("unchecked")
    private static WritableRegistry<net.minecraft.world.level.biome.Biome> findNmsWritable(Object root) throws Exception {
        Set<Object> seen = Collections.newSetFromMap(new IdentityHashMap<>());
        Deque<Object> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Object o = queue.poll();
            if (o == null || !seen.add(o)) {
                continue;
            }

            if (o instanceof WritableRegistry<?>) {
                return (WritableRegistry<net.minecraft.world.level.biome.Biome>) o;
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
        throw new IllegalStateException("Could not locate NMS WritableRegistry<Biome> from " + root.getClass());
    }

    // Unsafe operations

    /**
     * Overwrite a {@code private static final Map} field on {@link PaperRegistries} with a fresh
     * unmodifiable copy that also contains our biome entry. Uses {@link sun.misc.Unsafe} static field
     * base/offset, so it needs no {@code --add-opens}.
     */
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