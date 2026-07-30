package dev.wyck.util;

import dev.wyck.annotations.AsOf;

/**
 * A runnable that has a checked exception.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
@FunctionalInterface
public interface ThrowingRunnable {
    @AsOf("2.3.0")
    void run() throws Throwable;
}