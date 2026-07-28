package dev.wyck.wrapper.decode;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Collects the {@link Decodable decoders} of one wrapper family and dispatches to them by
 * type key.
 *
 * @param <W> the family this registry decodes
 * @param <M> the Minecraft type dispatched by this registry
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
@ApiStatus.Internal
public abstract class DecoderRegistry<W, M> implements Decodable<W, M> {

    private final Map<ResourceKey, Decodable<W, M>> decoders = new HashMap<>();

    protected DecoderRegistry() {

    }

    /**
     * Gets the key that identifies the concrete type of a Minecraft object.
     * @param minecraftObject the Minecraft object to discriminate
     * @return the object's decoder type key
     */
    protected abstract ResourceKey discriminate(M minecraftObject);

    /**
     * Rewrites an object into the form this registry's decoders expect.
     * @param minecraftObject the Minecraft object supplied to the registry
     * @return the normalized decoding target
     */
    protected M normalize(M minecraftObject) {
        return minecraftObject;
    }

    /**
     * Registers a decoder for a single vanilla type. Use this for types whose reading is a few field
     * reads; a type needing real logic gets its own {@link Decodable} class instead.
     */
    protected final void register(String minecraftType, Function<M, W> decoder) {
        register(ResourceKey.minecraft(minecraftType), decoder);
    }

    /**
     * Registers an inline decoder under an explicit key, including family-defined pseudo-types.
     * @param key the type key handled by the decoder
     * @param decoder the decoder function
     */
    protected final void register(ResourceKey key, Function<M, W> decoder) {
        register(new Decodable<>() {
            @Override
            public Set<ResourceKey> types() {
                return Set.of(key);
            }

            @Override
            public W decode(M minecraftObject) {
                return decoder.apply(minecraftObject);
            }
        });
    }

    protected final void register(Decodable<W, M> decoder) {
        Set<ResourceKey> types = decoder.types();
        Preconditions.checkArgument(!types.isEmpty(), "%s decoder %s claims no types", this.family(), decoder);
        for (ResourceKey type : types) {
            Decodable<W, M> previous = this.decoders.putIfAbsent(type, decoder);
            Preconditions.checkArgument(previous == null, "%s type '%s' is already claimed by %s", this.family(), type, previous);
        }
    }

    @Override
    public final W decode(M minecraftObject) {
        M target = normalize(minecraftObject);
        ResourceKey type = discriminate(target);
        Decodable<W, M> decoder = this.decoders.get(type);
        if (decoder == null) {
            throw new IllegalArgumentException("No " + this.family() + " decoder is registered for type '" + type + "'");
        }
        return decoder.decode(target);
    }

    public final ResourceKey typeOf(M minecraftObject) {
        return discriminate(normalize(minecraftObject));
    }

    public final boolean handles(ResourceKey type) {
        return this.decoders.containsKey(type);
    }

    /**
     * Gets every type key claimed by this registry.
     * @return the handled type keys
     */
    public final Set<ResourceKey> handled() {
        return Set.copyOf(this.decoders.keySet());
    }

    protected String family() {
        // space inbetween capital letters, remove decoder/decoders
        return this.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2").replaceAll("Decoder(s)?$", "").toLowerCase();
    }
}
