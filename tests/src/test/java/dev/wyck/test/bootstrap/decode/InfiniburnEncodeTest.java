package dev.wyck.test.bootstrap.decode;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.wyck.level.dimension.Infiniburn;
import dev.wyck.level.dimension.InfiniburnImpl;
import dev.wyck.test.bootstrap.MinecraftBootstrap;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class InfiniburnEncodeTest {

    @Test
    void blockLookupIsTheBuiltInRegistry() {
        assertSame(BuiltInRegistries.BLOCK, BootstrapSafeMinecraftRegistries.serialization()
            .lookupOrThrow(Registries.BLOCK));
    }

    @Test
    void tagBackedInfiniburnEncodesAsTagReferenceAtBootstrap() {
        HolderSet<Block> holderSet = ((InfiniburnImpl) Infiniburn.OVERWORLD).asHolderSet();

        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization()
            .createSerializationContext(JsonOps.INSTANCE);

        DataResult<JsonElement> result = RegistryCodecs.homogeneousList(Registries.BLOCK)
            .encodeStart(ops, holderSet);

        assertTrue(result.error().isEmpty(), () -> "encode failed: " + result.error().orElseThrow().message());
        assertEquals("\"#minecraft:infiniburn_overworld\"", result.result().orElseThrow().toString());
    }
}
