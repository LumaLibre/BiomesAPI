package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.Rotation;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * A feature that places a structure template chosen from a weighted list, each template optionally allowing
 * a set of rotations.
 *
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 * @since 3.0.1
 * @version 3.0.1
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.1")
public interface TemplateFeatureConfiguration extends FeatureConfiguration {

    /**
     * The weighted list of templates to choose from.
     * @return the weighted templates
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    WeightedList<TemplateEntry> templates();

    /**
     * Converts this object back to a builder.
     * @return a new builder with the same values as this object
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new template feature configuration.
     * @param templates the weighted list of templates to choose from
     * @return a new template feature configuration
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static TemplateFeatureConfiguration of(WeightedList<TemplateEntry> templates) {
        record Holder() {
            static final ConstructWireProvider<TemplateFeatureConfiguration> WIRE = ConstructWireProvider.create("dev.wyck.*?.worldgen.feature.configurations.TemplateFeatureConfigurationImpl");
        }
        return Holder.WIRE.construct(templates);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static Builder builder() {
        return new Builder();
    }

    /**
     * A single template entry: a structure template and the rotations it is allowed to be placed with.
     *
     * @param template the resource key of the structure template
     * @param rotations the rotations this template may be placed with
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    record TemplateEntry(ResourceKey template, List<Rotation> rotations) {

        public TemplateEntry {
            rotations = List.copyOf(rotations);
        }

        /**
         * Creates a template entry that allows every rotation.
         * @param template the resource key of the structure template
         * @return a new template entry
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public static TemplateEntry of(ResourceKey template) {
            return new TemplateEntry(template, List.of(Rotation.values()));
        }

        /**
         * Creates a template entry with the given rotations.
         * @param template the resource key of the structure template
         * @param rotations the rotations this template may be placed with
         * @return a new template entry
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public static TemplateEntry of(ResourceKey template, Rotation... rotations) {
            return new TemplateEntry(template, List.of(rotations));
        }
    }

    /**
     * Builder for {@link TemplateFeatureConfiguration}.
     * @since 3.0.1
     * @version 3.0.1
     * @author Jsinco
     */
    @AsOf("3.0.1")
    final class Builder {
        private final WeightedList.Builder<TemplateEntry> templates = WeightedList.builder();

        public Builder() {}

        public Builder(TemplateFeatureConfiguration configuration) {
            for (WeightedList.Weighted<TemplateEntry> entry : configuration.templates().unwrap()) {
                this.templates.add(entry.value(), entry.weight());
            }
        }

        /**
         * Adds a template entry with a weight of 1.
         * @param template the template entry to add
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder template(TemplateEntry template) {
            this.templates.add(template);
            return this;
        }

        /**
         * Adds a template entry with the given weight.
         * @param template the template entry to add
         * @param weight the weight of the template entry
         * @return this builder
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public Builder template(TemplateEntry template, int weight) {
            this.templates.add(template, weight);
            return this;
        }

        /**
         * Builds the template feature configuration.
         * @return the template feature configuration
         * @since 3.0.1
         */
        @AsOf("3.0.1")
        public TemplateFeatureConfiguration build() {
            WeightedList<TemplateEntry> built = templates.build();
            Preconditions.checkArgument(!built.isEmpty(), "templates must not be empty");
            return of(built);
        }
    }
}
