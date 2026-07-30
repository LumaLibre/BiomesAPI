package dev.wyck.misc.providers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.biome.Biome;
import org.bukkit.generator.WorldInfo;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A biome provider that uses a series of steps to determine the biome at a given position based on context.
 * <p>
 * Usage:
 * <pre>
 * {@code
 * StepsBiomeProvider provider = StepsBiomeProvider.steps()
 *     .step((worldInfo, x, y, z) -> {
 *         if (y > 200) return volcanoBiome;
 *         if (worldInfo.getEnvironment() == World.Environment.NETHER) return ashBiome;
 *         return null; // defer to next step
 *     }, volcanoBiome, ashBiome)
 *     .step(oceanBiome, (worldInfo, x, y, z) -> y < 40)   // convenience form, auto-registered
 *     .fallback(plainsBiome)
 *     .build();
 * }
 * </pre>
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public final class StepsBiomeProvider extends WyckBiomeProvider {

    private final List<BiomeStep> steps;
    @Nullable
    private final Biome fallback;

    @AsOf("2.3.0")
    private StepsBiomeProvider(Set<Biome> biomes, List<BiomeStep> steps, @Nullable Biome fallback) {
        super(biomes);
        this.steps = steps;
        this.fallback = fallback;
    }

    @Override
    @AsOf("2.3.0")
    public org.bukkit.block.Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        // TODO: Cache bukkit biomes maybe?
        for (BiomeStep step : steps) {
            Biome biome = step.apply(worldInfo, x, y, z);
            if (biome != null) {
                return biome.bukkitBiome();
            }
        }
        return fallback != null ? fallback.bukkitBiome() : worldInfo.vanillaBiomeProvider().getBiome(worldInfo, x, y, z);
    }

    @AsOf("2.3.0")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder class for creating instances of StepsBiomeProvider.
     * @since 2.3.0
     * @version 2.3.0
     * @author Jsinco
     */
    @AsOf("2.3.0")
    public static final class Builder {
        private final List<BiomeStep> steps = new ArrayList<>();
        private final Set<Biome> biomes = new LinkedHashSet<>();
        private @Nullable Biome fallback;

        /**
         * Full-control step. The lambda receives the position and returns a biome to place,
         * or {@code null} to defer to the next step. Declare every biome the lambda can return
         * via {@code produces} so the provider can advertise it from {@code getBiomes(...)}.
         */
        @AsOf("2.3.0")
        public Builder step(BiomeStep step, Biome... produces) {
            Preconditions.checkNotNull(step, "step cannot be null");
            Preconditions.checkNotNull(produces, "produces cannot be null");
            steps.add(step);
            Collections.addAll(biomes, produces);
            return this;
        }

        /**
         * Convenience step for simple position tests. The lambda receives the position and returns whether to place the biome.
         * The biome is automatically registered as being produced by this provider.
         * @param biome the biome to place
         * @param condition the position test
         * @return this builder
         */
        @AsOf("2.3.0")
        public Builder step(Biome biome, BiomeCondition condition) {
            Preconditions.checkNotNull(biome, "biome cannot be null");
            Preconditions.checkNotNull(condition, "condition cannot be null");
            biomes.add(biome);
            steps.add((info, x, y, z) -> condition.test(info, x, y, z) ? biome : null);
            return this;
        }

        /**
         * Fallback biome to use when no other step produces a biome.
         * @param biome the biome to use
         * @return this builder
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public Builder fallback(Biome biome) {
            Preconditions.checkNotNull(biome, "fallback biome cannot be null");
            this.fallback = biome;
            this.biomes.add(biome);
            return this;
        }

        @AsOf("2.3.0")
        public StepsBiomeProvider build() {
            return new StepsBiomeProvider(new LinkedHashSet<>(biomes), List.copyOf(steps), fallback);
        }
    }

    /**
     * A step that inspects the position and optionally produces a biome.
     * Return {@code null} to fall through to the next step.
     * Steps must be pure and/or side effect free, they're called concurrently during chunk generation.
     */
    @AsOf("2.3.0")
    @FunctionalInterface
    public interface BiomeStep {
        @Nullable Biome apply(WorldInfo worldInfo, int x, int y, int z);
    }

    /**
     * A position test for the convenience {@code step(biome, condition)} overload.
     */
    @AsOf("2.3.0")
    @FunctionalInterface
    public interface BiomeCondition {
        boolean test(WorldInfo worldInfo, int x, int y, int z);
    }
}