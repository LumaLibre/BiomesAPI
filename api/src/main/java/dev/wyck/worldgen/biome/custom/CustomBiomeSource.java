package dev.wyck.worldgen.biome.custom;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.biome.BiomeSource;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Base class for an authored biome source with its own biome-selection algorithm.
 *
 * <p>Paper servers may call {@link #biome(BiomeSourceContext)} concurrently from multiple async
 * chunk loading threads.
 *
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
@ApiStatus.Experimental
public abstract class CustomBiomeSource implements BiomeSource {

    private final Set<Biome> possibleBiomes;

    /**
     * Creates a custom source whose possible biomes are supplied by an override of
     * {@link #possibleBiomes()}.
     *
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    protected CustomBiomeSource() {
        this.possibleBiomes = Set.of();
    }

    /**
     * Creates a custom source capable of returning the supplied biomes.
     * @param possibleBiomes every biome this source may return
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    protected CustomBiomeSource(Set<? extends Biome> possibleBiomes) {
        Preconditions.checkNotNull(possibleBiomes, "possibleBiomes cannot be null");
        Preconditions.checkArgument(!possibleBiomes.isEmpty(), "possibleBiomes cannot be empty");
        LinkedHashSet<Biome> copiedBiomes = new LinkedHashSet<>(possibleBiomes);
        Preconditions.checkArgument(!copiedBiomes.contains(null), "possibleBiomes cannot contain null");
        this.possibleBiomes = Collections.unmodifiableSet(copiedBiomes);
    }

    /**
     * Creates a custom source capable of returning the supplied biomes.
     * @param possibleBiomes every biome this source may return
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    protected CustomBiomeSource(Biome... possibleBiomes) {
        this(new LinkedHashSet<>(List.of(possibleBiomes)));
    }

    /**
     * Selects the biome at the requested position.
     *
     * <p>The returned biome must be one of the entries declared through the constructor or
     * {@link #possibleBiomes()}.
     * @param context the position and climate sampling surface
     * @return the biome at the requested position
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public abstract Biome biome(BiomeSourceContext context);

    /**
     * Gets every biome this source may return. Minecraft uses this set for structure placement,
     * locating biomes, and other biome-source queries. Implementations using the no-argument
     * constructor must override this method and return a non-empty set.
     *
     * <p>The returned set is read and snapshotted when {@link #toMinecraft()} is called.
     * @return the possible biomes
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    public Set<Biome> possibleBiomes() {
        return this.possibleBiomes;
    }

    /**
     * Creates the Minecraft biome source that delegates selection to this object.
     * @return the Minecraft biome source
     * @since 3.3.0
     */
    @Override
    @AsOf("3.3.0")
    public final Object toMinecraft() {
        record Holder() {
            static final ConstructWireProvider<Object> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.biome.custom.CustomBiomeSourceBridge");
        }
        return Holder.WIRE.construct(this);
    }
}
