package dev.wyck.wrapper;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * A handle to an NMS object.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface Wrapper {

    /**
     * Convert this handle to the real Minecraft object.
     * @return the real Minecraft object
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    Object toMinecraft();

    @AsOf("2.4.1")
    @SuppressWarnings("unchecked")
    default <T> T asHandle() {
        return (T) this.toMinecraft();
    }

    @AsOf("3.3.0")
    @SuppressWarnings("unchecked")
    default <T> T unwrap() {
        return (T) this.toMinecraft();
    }
}
