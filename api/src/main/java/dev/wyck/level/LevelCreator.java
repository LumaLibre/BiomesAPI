package dev.wyck.level;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import dev.wyck.level.dimension.LevelStem;
import dev.wyck.level.entity.LevelSpawner;
import dev.wyck.registry.level.LevelFactory;
import dev.wyck.worldgen.chunk.ChunkGenerator;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A more in depth representation of a {@link org.bukkit.WorldCreator}.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface LevelCreator {

    /**
     * The key the world and its level stem are registered under.
     * @return the key the world and its level stem is registered under
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey resourceKey();

    /**
     * The level stem of the world.
     * @return the level stem of the world
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    LevelStem levelStem();

    /**
     * The dimension of the world.
     * @deprecated Use {@link #levelStem()} instead.
     * @return the dimension of the world
     * @since 2.4.0
     */
    @Deprecated
    @AsOf("2.4.0")
    default Dimension dimension() {
        return levelStem().dimension();
    }

    /**
     * The chunk generator to use for the world.
     * @deprecated Use {@link #levelStem()} instead.
     * @return the chunk generator to use for the world.
     * @since 2.4.0
     */
    @Deprecated
    @AsOf("2.4.0")
    default ChunkGenerator generator() {
        return levelStem().chunkGenerator();
    }

    /**
     * The seed to use for the world.
     * @return the seed to use for the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    long seed();

    /**
     * Whether to generate structures in the world.
     * @return whether to generate structures in the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean generateStructures();

    /**
     * Whether to generate a bonus chest in the world.
     * @return whether to generate a bonus chest in the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean bonusChest();

    /**
     * The environment of the world.
     * @return the environment of the world.
     * @since 2.4.0
     * @apiNote Obsolete, only here for bukkit compatibility.
     */
    @AsOf("2.4.0")
    @ApiStatus.Obsolete
    World.Environment environment();

    /**
     * The persistence of the world.
     * @deprecated Use {@link #levelStem()} and {@link LevelStem#register()} instead.
     * @return the persistence of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @SuppressWarnings("removal")
    @Deprecated(forRemoval = true, since = "3.3.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "3.4.0")
    StemPersistence persistence();

    /**
     * The spawners of the world.
     * @return the spawners of the world.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    List<LevelSpawner> spawners();

    /**
     * The name of the world.
     * @return the name of the world, as it appears in Bukkit.
     * @since 2.4.0
     */
    @AsOf("3.0.0")
    String name();

    /**
     * Used only to control client-sided behavior.
     * This enum has no representation of how a world actually behaves or is generated.
     * @return the type of the world
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    LevelType type();

    /**
     * Converts this {@link LevelCreator} to a {@link World}.
     * @return a {@link World} equivalent to this {@link LevelCreator}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @Nullable World bukkitWorld();

    /**
     * Creates a new bukkit world from this level creator.
     * @return a new {@link World} from this {@link LevelCreator}
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default World create() {
        return LevelFactory.factory().createWorld(this);
    }

    /**
     * Converts this back into a builder.
     * @return a new builder from this instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new builder seeded with the given level key.
     * @param levelKey the level key to seed the builder with
     * @return a new builder seeded with the given level key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder(ResourceKey levelKey) {
        return builder().resourceKey(levelKey);
    }

    /**
     * Builder for {@link LevelCreator}.
     * @since 2.4.0
     * @version 2.4.0
     * @author Jsinco
     */
    @AsOf("2.4.0")
    @SuppressWarnings("removal")
    final class Builder {

        private @Nullable ResourceKey levelKey = null;
        private @Nullable LevelStem levelStem = null;
        private long seed = ThreadLocalRandom.current().nextLong();
        private boolean generateStructures = true;
        private boolean bonusChest = false;
        private World.Environment environment = World.Environment.CUSTOM;
        private StemPersistence persistence = StemPersistence.TRANSIENT;
        private List<LevelSpawner> spawners = new ArrayList<>();
        private @Nullable String name = null;
        private LevelType type = LevelType.NORMAL;

        private @Nullable Dimension friendly$Dimension = null;
        private @Nullable ChunkGenerator friendly$ChunkGenerator = null;

        @AsOf("2.4.0")
        public Builder() {}

        public Builder(LevelCreator other) {
            this.levelKey = other.resourceKey();
            this.levelStem = other.levelStem();
            this.seed = other.seed();
            this.generateStructures = other.generateStructures();
            this.bonusChest = other.bonusChest();
            this.environment = other.environment();
            this.persistence = other.persistence();
            this.spawners.addAll(other.spawners());
            this.name = other.name();
            this.type = other.type();
        }

        /**
         * Sets the resource key of the level stem.
         * @param levelKey the resource key of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder resourceKey(ResourceKey levelKey) {
            this.levelKey = levelKey;
            return this;
        }

        /**
         * Sets the level stem of the world.
         * @param levelStem the level stem of the world
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder levelStem(LevelStem levelStem) {
            this.levelStem = levelStem;
            return this;
        }

        /**
         * Sets the seed of the world.
         * @param seed the seed of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder seed(long seed) {
            this.seed = seed;
            return this;
        }

        /**
         * Sets whether to generate structures in the world.
         * @param generateStructures whether to generate structures in the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder generateStructures(boolean generateStructures) {
            this.generateStructures = generateStructures;
            return this;
        }

        /**
         * Sets whether to generate a bonus chest in the world.
         * @param bonusChest whether to generate a bonus chest in the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder bonusChest(boolean bonusChest) {
            this.bonusChest = bonusChest;
            return this;
        }

        /**
         * Sets the environment of the world.
         * @param environment the environment of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder environment(World.Environment environment) {
            this.environment = environment;
            return this;
        }

        /**
         * Sets the persistence of the world.
         * @param persistence the persistence of the world
         * @return this builder
         * @since 2.4.0
         * @deprecated Use {@link #levelStem()} and {@link LevelStem#register()} instead.
         */
        @AsOf("2.4.0")
        @Deprecated(forRemoval = true, since = "3.3.0")
        @ApiStatus.ScheduledForRemoval(inVersion = "3.4.0")
        public Builder persistence(StemPersistence persistence) {
            this.persistence = persistence;
            return this;
        }

        /**
         * Sets the spawners of the world.
         * @param spawners the spawners of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder spawners(List<LevelSpawner> spawners) {
            this.spawners = spawners;
            return this;
        }

        /**
         * Sets the name of the world.
         * @param name the name of the world, or null to use the default name
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the world.
         * @param type the type of the world
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder type(LevelType type) {
            this.type = type;
            return this;
        }

        // Friendly builder methods

        /**
         * Alias of {@link #resourceKey(ResourceKey)}
         * @param levelKey the resource key of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder levelKey(ResourceKey levelKey) {
            return resourceKey(levelKey);
        }

        /**
         * Adds a spawner to the world.
         * @param spawner the spawner to add
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder spawner(LevelSpawner spawner) {
            this.spawners.add(spawner);
            return this;
        }

        /**
         * Adds spawners to the world.
         * @param spawners the spawners to add
         * @return this builder
         * @since 3.0.0
         */
        @AsOf("3.0.0")
        public Builder spawners(LevelSpawner... spawners) {
            Collections.addAll(this.spawners, spawners);
            return this;
        }

        /**
         * Sets the dimension of the world.
         * @param dimension the dimension of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder dimension(Dimension dimension) {
            this.friendly$Dimension = dimension;
            return this;
        }

        /**
         * Sets the dimension of the world.
         * @param resourceKey the resource key of the dimension
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder dimension(ResourceKey resourceKey) {
            return dimension(Dimension.reference(resourceKey));
        }

        /**
         * Sets the chunk generator of the world.
         * @param generator the chunk generator of the world
         * @return this builder
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public Builder generator(ChunkGenerator generator) {
            this.friendly$ChunkGenerator = generator;
            return this;
        }

        /**
         * Sets the level stem of the world.
         * @param levelStem the level stem of the world
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder stem(LevelStem levelStem) {
            this.levelStem = levelStem;
            return this;
        }

        /**
         * Builds the {@link LevelCreator} with the specified properties.
         * @return the constructed level creator
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public LevelCreator build() {
            Preconditions.checkNotNull(levelKey, "levelKey must be set");

            if (levelStem == null && (friendly$Dimension != null || friendly$ChunkGenerator != null)) {
                Preconditions.checkNotNull(friendly$Dimension, "dimension must be set if chunk generator is set");
                Preconditions.checkNotNull(friendly$ChunkGenerator, "chunk generator must be set if dimension is set");
                levelStem = LevelStem.of(friendly$Dimension, friendly$ChunkGenerator);
            }

            Preconditions.checkNotNull(levelStem, "levelStem must be set");
            return new LevelCreatorImpl(
                levelKey,
                levelStem,
                seed,
                generateStructures,
                bonusChest,
                environment,
                persistence,
                spawners,
                name,
                type
            );
        }

        /**
         * Creates a new bukkit world from the built level creator.
         * @return a new {@link World} from the built {@link LevelCreator}
         * @since 2.4.0
         */
        @AsOf("2.4.0")
        public World create() {
            return build().create();
        }
    }
}