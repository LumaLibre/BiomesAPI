package dev.wyck.environment.attribute.modifier;

import dev.wyck.annotations.AsOf;
import dev.wyck.environment.attribute.EnvironmentAttribute;
import org.jspecify.annotations.NullMarked;

/**
 * Describes an operation applied to an environment attribute.
 *
 * @param <V> the value type of the target attribute
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface AttributeModification<V> {

    /**
     * Gets the attribute this operation modifies.
     * @return the modified attribute
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    EnvironmentAttribute<V> attribute();

    /**
     * Gets the operation applied to the attribute.
     * @return the attribute operation
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    AttributeOperation operation();
}
