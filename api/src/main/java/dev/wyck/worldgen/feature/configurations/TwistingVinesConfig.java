package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Twisting vines configuration.
 *
 * @see <a href="https://minecraft.wiki/w/Twisting_Vines_(feature)s">Twisting vines</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TwistingVinesConfig extends FeatureConfiguration {

    /**
     * How far out the vines can spread.
     * @return spread width in blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadWidth();

    /**
     * How far out the vines can spread vertically.
     * @return spread height in blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int spreadHeight();

    /**
     * The maximum height of the vines.
     * @return maximum height in blocks
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    int maxHeight();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of this configuration.
     * @param spreadWidth spread width in blocks
     * @param spreadHeight spread height in blocks
     * @param maxHeight maximum height in blocks
     * @return a new instance of this configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TwistingVinesConfig of(int spreadWidth, int spreadHeight, int maxHeight) {
        record Holder() {
            static final ConstructWireProvider<TwistingVinesConfig> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.TwistingVinesConfigImpl");
        }
        return Holder.WIRE.construct(spreadWidth, spreadHeight, maxHeight);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link TwistingVinesConfig}.
     * @since 3.0.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("3.0.0")
    final class Builder {
        private int spreadWidth = 1;
        private int spreadHeight = 1;
        private int maxHeight = 1;

        public Builder() {}

        public Builder(TwistingVinesConfig configuration) {
            this.spreadWidth = configuration.spreadWidth();
            this.spreadHeight = configuration.spreadHeight();
            this.maxHeight = configuration.maxHeight();
        }

        /**
         * Sets the spread width of the vines.
         * @param spreadWidth the spread width
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadWidth(int spreadWidth) {
            this.spreadWidth = spreadWidth;
            return this;
        }

        /**
         * Sets the spread height of the vines.
         * @param spreadHeight the spread height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spreadHeight(int spreadHeight) {
            this.spreadHeight = spreadHeight;
            return this;
        }

        /**
         * Sets the maximum height of the vines.
         * @param maxHeight the maximum height
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder maxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        /**
         * Builds the configuration.
         * @return the built configuration
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public TwistingVinesConfig build() {
            Preconditions.checkArgument(spreadWidth > 0, "spreadWidth must be positive");
            Preconditions.checkArgument(spreadHeight > 0, "spreadHeight must be positive");
            Preconditions.checkArgument(maxHeight > 0, "maxHeight must be positive");
            return of(spreadWidth, spreadHeight, maxHeight);
        }
    }
}