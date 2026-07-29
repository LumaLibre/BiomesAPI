package dev.wyck.worldgen.surface.condition;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record PaperOptionallyFlatBedrockConditionSourceImpl(
    @Override ResourceKey randomName,
    @Override VerticalAnchor trueAtAndBelow,
    @Override VerticalAnchor falseAtAndAbove,
    @Override boolean roof
) implements PaperOptionallyFlatBedrockConditionSource {
    @Override
    public Object toMinecraft() {
        return new io.papermc.paper.world.worldgen.OptionallyFlatBedrockConditionSource(
            this.randomName.identifier(),
            this.trueAtAndBelow.asHandle(),
            this.falseAtAndAbove.asHandle(),
            this.roof
        );
    }
}
