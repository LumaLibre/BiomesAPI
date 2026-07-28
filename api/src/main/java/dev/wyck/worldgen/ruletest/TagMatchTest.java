package dev.wyck.worldgen.ruletest;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jspecify.annotations.NullMarked;

/**
 * Tests if the block is in the specified block tag.
 *
 * @see <a href="https://minecraft.wiki/w/Processor_list#tag_match">Processor list (tag match)</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface TagMatchTest extends RuleTest {

    /**
     * The tag to match.
     * @return the tag
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    ResourceKey tag();

    /**
     * Creates a new tag match test.
     * @param tag the tag to match
     * @return the tag match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TagMatchTest of(ResourceKey tag) {
        record Holder() {
            static final ConstructWireProvider<TagMatchTest> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.ruletest.TagMatchTestImpl");
        }
        return Holder.WIRE.construct(tag);
    }

    /**
     * Creates a new tag match test.
     * @param tag the tag to match
     * @return the tag match test
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TagMatchTest of(Tag<Material> tag) {
        ResourceKey key = ResourceKey.of(tag.key().asString());
        return of(key);
    }
}
