package dev.wyck.exceptions;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * Thrown when a decoding API is called without the optional Wyck decoder module available at
 * runtime.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public class MissingDecoderException extends IllegalStateException {

    /**
     * Creates an exception for a decoder implementation that could not be loaded.
     * @param implementation the missing decoder implementation
     * @param cause the class-loading failure
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public MissingDecoderException(String implementation, Throwable cause) {
        super(
            "Wyck decoders are not available. Add dev.wyck:wyck-decoders to the runtime classpath " +
                "before using decoding APIs (missing " + implementation + ").",
            cause
        );
    }
}
