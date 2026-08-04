package dev.wyck.worldgen.function.noise;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.synth.NoiseParameters;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class NoiseParameterFunctionImpl implements NoiseParameterFunction {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    protected final Optional<ResourceKey> resourceKey;
    protected final NoiseParameters noiseParameters;

    public NoiseParameterFunctionImpl(Optional<ResourceKey> resourceKey, NoiseParameters noiseParameters) {
        this.resourceKey = resourceKey;
        this.noiseParameters = noiseParameters;
    }

    @Override
    public Optional<ResourceKey> resourceKey() {
        return resourceKey;
    }

    @Override
    public NoiseParameters noiseParameters() {
        return noiseParameters;
    }

    @Override
    public NoiseParameterFunction register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    protected final net.minecraft.core.Holder<net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters> noiseData() {
        Optional<ResourceKey> key = this.noiseParameters.resourceKey();
        if (key.isEmpty()) {
            return net.minecraft.core.Holder.direct(this.noiseParameters.asHandle());
        }
        return BootstrapSafeMinecraftRegistries.getter(net.minecraft.core.registries.Registries.NOISE).getOrThrow(
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.NOISE, key.orElseThrow().identifier())
        );
    }

}
