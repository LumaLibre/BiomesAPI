package dev.wyck.environment;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import dev.wyck.wrapper.Wrapper;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A wrapper for bed rules in a dimensional environment.
 *
 * @version 1.1.0
 * @since 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("1.1.0")
public interface BedRule extends Wrapper {

    /**
     * Gets the rule for sleeping in the bed.
     * @return the rule for sleeping in the bed
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    Rule canSleep();

    /**
     * Gets the rule for setting the spawn point using the bed.
     * @return the rule for setting the spawn point using the bed
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    Rule canSetSpawn();

    /**
     * Whether the bed explodes when used in this environment.
     * @return whether the bed explodes when used in this environment
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    boolean explodes();

    /**
     * The error message displayed when a player tries to use the bed inappropriately.
     * @return the error message displayed when a player tries to use the bed inappropriately, or null if no error message is set
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    Optional<Component> errorMessage();

    /**
     * Converts this BedRule to a builder.
     * @return a new builder for this BedRule
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new BedRule instance.
     * @param canSleep the rule for sleeping in the bed
     * @param canSetSpawn the rule for setting the spawn point using the bed
     * @param explodes whether the bed explodes when used in this environment
     * @param errorMessage the error message displayed when a player tries to use the bed inappropriately, or null if no error message is set
     * @return a new BedRule instance
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BedRule of(Rule canSleep, Rule canSetSpawn, boolean explodes, @Nullable Component errorMessage) {
        record Holder() {
            static final ConstructWireProvider<BedRule> WIRE = ConstructWireProvider.construct("dev.wyck.environment.BedRuleImpl");
        }
        return Holder.WIRE.construct(canSleep, canSetSpawn, explodes, Optional.ofNullable(errorMessage));
    }

    /**
     * Creates a new builder for bed rules.
     * @return a new builder for bed rules
     * @since 1.1.0
     */
    @AsOf("1.1.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Reads a Minecraft bed rule into a wrapper.
     * @param minecraftBedRule the bed rule to read
     * @return the wrapper for it
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static BedRule decode(Object minecraftBedRule) {
        record Holder() {
            static final Decoder<BedRule> DECODER = Decoder.create("dev.wyck.decode.environment.BedRuleDecoder");
        }
        return Holder.DECODER.decode(minecraftBedRule);
    }

    /**
     * An enum representing the rules for bed usage in an environment.
     *
     * @version 1.1.0
     * @since 1.1.0
     * @author Jsinco
     */
    @AsOf("1.1.0")
    enum Rule implements WrappedEnumerator<Rule> {
        ALWAYS("always"),
        WHEN_DARK("when_dark"),
        NEVER("never");

        public static final KeyedEnumTranslator<Rule> TRANSLATOR = KeyedEnumTranslator.byKey(Rule::getKey, Rule.values());

        private final String key;

        @AsOf("1.1.0")
        Rule(String key) {
            this.key = key;
        }

        /**
         * Gets the key for this rule.
         * @return the key for this rule
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public String getKey() {
            return key;
        }

        /**
         * Gets the translator for this enum.
         * @return the translator for this enum
         * @since 1.1.0
         */
        @Override
        @AsOf("1.1.0")
        public KeyedEnumTranslator<Rule> translator() {
            return TRANSLATOR;
        }
    }

    /**
     * A builder for bed rules. Defaults to Minecraft overworld rules.
     * @version 1.1.0
     * @since 1.1.0
     * @author Jsinco
     */
    @AsOf("1.1.0")
    final class Builder {
        private Rule canSleep = Rule.WHEN_DARK;
        private Rule canSetSpawn = Rule.ALWAYS;
        private boolean explodes = false;
        private @Nullable Component errorMessage = Component.text("You can only sleep at night or during thunderstorms");

        public Builder() {}

        public Builder(BedRule bedRule) {
            this.canSleep = bedRule.canSleep();
            this.canSetSpawn = bedRule.canSetSpawn();
            this.explodes = bedRule.explodes();
            this.errorMessage = bedRule.errorMessage().orElse(null);
        }

        /**
         * Sets the rule for sleeping in the bed.
         * @param canSleep the rule for sleeping in the bed
         * @return this builder, for chaining
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public Builder canSleep(Rule canSleep) {
            this.canSleep = canSleep;
            return this;
        }

        /**
         * Sets the rule for setting the spawn point using the bed.
         * @param canSetSpawn the rule for setting the spawn point using the bed
         * @return this builder, for chaining
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public Builder canSetSpawn(Rule canSetSpawn) {
            this.canSetSpawn = canSetSpawn;
            return this;
        }

        /**
         * Sets whether the bed explodes when used in this environment.
         * @param explodes whether the bed explodes when used in this environment
         * @return this builder, for chaining
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public Builder explodes(boolean explodes) {
            this.explodes = explodes;
            return this;
        }

        /**
         * Sets the error message displayed when a player tries to use the bed inappropriately.
         * @param errorMessage the error message displayed when a player tries to use the bed inappropriately, or null to remove the error message
         * @return this builder, for chaining
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public Builder errorMessage(@Nullable Component errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        /**
         * Builds the BedRule instance.
         * @return the BedRule instance
         * @since 1.1.0
         */
        @AsOf("1.1.0")
        public BedRule build() {
            return of(canSleep, canSetSpawn, explodes, errorMessage);
        }
    }
}