package dev.wyck.worldgen.biome.custom;

import com.google.common.base.Preconditions;
import com.mojang.serialization.MapCodec;
import dev.wyck.biome.Biome;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@NullMarked
@ApiStatus.Internal
public final class CustomBiomeSourceBridge extends net.minecraft.world.level.biome.BiomeSource {

    private final CustomBiomeSource delegate;
    private final Map<ResourceKey, Holder<net.minecraft.world.level.biome.Biome>> possibleBiomes;
    private final MapCodec<CustomBiomeSourceBridge> codec;

    public CustomBiomeSourceBridge(CustomBiomeSource delegate) {
        this.delegate = delegate;
        this.possibleBiomes = resolve(delegate.possibleBiomes());
        this.codec = MapCodec.unit(() -> this);
    }

    public CustomBiomeSource delegate() {
        return this.delegate;
    }

    @Override
    protected MapCodec<? extends net.minecraft.world.level.biome.BiomeSource> codec() {
        return this.codec;
    }

    @Override
    protected Stream<Holder<net.minecraft.world.level.biome.Biome>> collectPossibleBiomes() {
        return this.possibleBiomes.values().stream();
    }

    @Override
    public Holder<net.minecraft.world.level.biome.Biome> getNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
        Biome selected = Preconditions.checkNotNull(
            this.delegate.biome(new BiomeSourceContextImpl(quartX, quartY, quartZ, sampler)),
            "Custom biome source returned null"
        );
        ResourceKey key = selected.resourceKey();
        Holder<net.minecraft.world.level.biome.Biome> holder = this.possibleBiomes.get(key);

        Preconditions.checkNotNull(holder, "Custom biome source returned an undeclared biome '" + key + "'. Expected one of: " + this.possibleBiomes.keySet());
        return holder;
    }

    private static Map<ResourceKey, Holder<net.minecraft.world.level.biome.Biome>> resolve(Set<Biome> possibleBiomes) {
        Preconditions.checkNotNull(possibleBiomes, "possibleBiomes cannot be null");
        Preconditions.checkArgument(!possibleBiomes.isEmpty(), "possibleBiomes cannot be empty");
        Registry<net.minecraft.world.level.biome.Biome> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BIOME);
        Map<ResourceKey, Holder<net.minecraft.world.level.biome.Biome>> resolved = new LinkedHashMap<>();

        for (Biome biome : possibleBiomes) {
            Preconditions.checkNotNull(biome, "possibleBiomes cannot contain null values");
            ResourceKey key = biome.resourceKey();

            net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome> minecraftKey =
                net.minecraft.resources.ResourceKey.create(Registries.BIOME, key.identifier());
            Holder<net.minecraft.world.level.biome.Biome> holder = registry.get(minecraftKey).orElseThrow(() ->
                new IllegalStateException("No Minecraft biome is registered under '" + key + "'"));

            resolved.put(key, holder);
        }
        return Collections.unmodifiableMap(resolved);
    }
}
