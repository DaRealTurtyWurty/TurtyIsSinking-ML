package dev.turtywurty.turtyissinking.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

// TODO: Validate item was receieved whilst the player was in the correct gamemode
public class GamemodeCriterion extends SimpleCriterionTrigger<GamemodeCriterion.Conditions> {
    @Override
    public @NonNull Codec<GamemodeCriterion.Conditions> codec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, conditions -> conditions.matches(player));
    }

    public record Conditions(
            Optional<ContextAwarePredicate> playerPredicate,
            GameType gameMode) implements SimpleCriterionTrigger.SimpleInstance {
        public static Codec<GamemodeCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(Conditions::playerPredicate),
                GameType.CODEC.fieldOf("gamemode").forGetter(Conditions::gameMode)
        ).apply(instance, Conditions::new));

        @Override
        public @NonNull Optional<ContextAwarePredicate> player() {
            return playerPredicate;
        }

        public boolean matches(ServerPlayer player) {
            return player.gameMode.getGameModeForPlayer() == gameMode;
        }
    }
}
