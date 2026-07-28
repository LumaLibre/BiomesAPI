package dev.wyck.worldgen.placement;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.worldgen.feature.ConfiguredFeature;
import dev.wyck.worldgen.feature.custom.CustomFeature;
import dev.wyck.wrapper.ContextWrapper;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wraps Minecraft's PlacedFeature, a configured feature paired with the ordered
 * list of placement modifiers applied to it. A placed feature may either reference
 * one already present in the {@code PLACED_FEATURE} registry, or be authored from a
 * configured feature plus a list of modifiers.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface PlacedFeature extends Wrapper, Keyed permits PlacedFeature.Reference, PlacedFeature.Composed {

    @ApiStatus.Internal
    interface Factory extends ContextWrapper<PlacedFeature> {
        Object toMinecraft(PlacedFeature feature);
    }

    /**
     * Converts this placed feature to a Minecraft object.
     * @return a Minecraft object representing this placed feature
     * @since 2.3.0
     */
    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        record Holder() {
            static final WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.worldgen.placement.PlacedFeatureFactoryImpl");
        }
        return Holder.WIRE.get().toMinecraft(this);
    }

    /**
     * Converts this object to a builder.
     * @return a builder with the same values as this object
     * @since 2.3.0
     */
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * References a placed feature already registered under the given key.
     * @param key the registry key of the placed feature
     * @return a reference to the registered placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static PlacedFeature.Reference reference(ResourceKey key) {
        return new Reference(key);
    }

    /**
     * Resolves this object's key in Minecraft's placed-feature registry and decodes the registered value.
     * @return the decoded placed feature
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    @ApiStatus.Experimental
    default PlacedFeature wrap() {
        ResourceKey key = ResourceKey.of(key().namespace(), key().value());
        Object minecraft = WyckRegistry.of(RegistryId.PLACED_FEATURE).retrieveOrThrow(key);
        return decode(minecraft);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placements the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Composed of(ConfiguredFeature feature, List<PlacementModifier> placements) {
        return new Composed(feature, placements);
    }

    /**
     * Authors a placed feature from a configured feature and a list of modifiers.
     * @param feature the configured feature to place
     * @param placements the ordered placement modifiers
     * @return an authored placed feature
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Composed of(ConfiguredFeature feature, PlacementModifier... placements) {
        return new Composed(feature, List.of(placements));
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads a keyed Minecraft placed-feature holder into a reference wrapper.
     * @param minecraftPlacedFeature the placed-feature holder to read
     * @return a reference to the placed feature
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static PlacedFeature decode(Object minecraftPlacedFeature) {
        record Holder() {
            static final Decoder<PlacedFeature> DECODER = Decoder.create("dev.wyck.decode.worldgen.placement.PlacedFeatureDecoder");
        }
        return Holder.DECODER.decode(minecraftPlacedFeature);
    }

    /**
     * A reference to an already-registered placed feature.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Reference(@Override ResourceKey key) implements PlacedFeature {}

    /**
     * A placed feature authored from a configured feature and a modifier list.
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    record Composed(ConfiguredFeature feature, List<PlacementModifier> placement) implements PlacedFeature {
        @Override
        public Key key() {
            return this.feature.key();
        }
    }

    /**
     * Builder for {@link PlacedFeature}.
     * @since 2.3.0
     * @version 3.0.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    final class Builder {
        private @Nullable ConfiguredFeature feature;
        private List<PlacementModifier> placements = new ArrayList<>();

        public Builder() {}

        public Builder(PlacedFeature feature) {
            if (feature instanceof Composed(ConfiguredFeature feat, List<PlacementModifier> placement)) {
                this.feature = feat;
                this.placements = placement;
            } else {
                Reference reference = (Reference) feature;
                this.feature = ConfiguredFeature.reference(reference.key());
            }
        }

        /**
         * Sets the feature to be placed.
         * @param feature the feature to be placed
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder feature(ConfiguredFeature feature) {
            this.feature = feature;
            return this;
        }

        /**
         * Sets a list of placement modifiers to the list of modifiers.
         * @param placements the placement modifiers to set
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder modifiers(List<PlacementModifier> placements) {
            this.placements = new ArrayList<>(placements);
            return this;
        }

        // Friendly builder methods

        /**
         * Sets the feature to be placed.
         * @param customFeature the feature to be placed
         * @param config the config for the feature
         * @return this builder
         * @param <C> the config type
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public <C> Builder feature(CustomFeature<C> customFeature, C config) {
            this.feature = ConfiguredFeature.custom(customFeature, config);
            return this;
        }

        /**
         * Adds placement modifiers to the list of modifiers.
         * @param placements the placement modifiers to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder modifier(PlacementModifier... placements) {
            this.placements.addAll(Arrays.asList(placements));
            return this;
        }

        /**
         * Adds a placement modifier to the list of modifiers.
         * @param placement the placement modifier to add
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder modifier(PlacementModifier placement) {
            this.placements.add(placement);
            return this;
        }

        /**
         * Builds the placed feature.
         * @return the placed feature
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Composed build() {
            Preconditions.checkNotNull(feature, "feature must be set");
            return of(feature, placements);
        }
    }
}