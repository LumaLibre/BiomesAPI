package dev.wyck.wrapper.decode;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

/**
 * Builds a wrapper from the Minecraft object it wraps, the inverse of {@link Wrapper#toMinecraft()}.
 *
 * @param <T> the family this decoder produces
 * @param <U> the Minecraft type this decoder reads
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface Decodable<T, U> {

    /**
     * Decodes a Minecraft value into the Wyck type owned by this decoder.
     * @param minecraftObject the Minecraft object to read
     * @return the wrapper for that object
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    T decode(U minecraftObject);


    /**
     * The type keys this decoder claims within a {@link DecoderRegistry}, empty when its family has a
     * single implementation and is not dispatched.
     * @return the type keys this decoder claims
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Set<ResourceKey> types() {
        return Set.of();
    }
}
