package dev.turtywurty.turtyissinking.advancement.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class PunchBlockCriterion extends SimpleCriterionTrigger<PunchBlockCriterion.Conditions> {
    @Override
    public @NonNull Codec<PunchBlockCriterion.Conditions> codec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayer player, BlockPos pos) {
        trigger(player, conditions -> conditions.matches(player, pos));
    }

    public record Conditions(
            Optional<ContextAwarePredicate> playerPredicate,
            BlockPredicate blockPredicate) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<PunchBlockCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(PunchBlockCriterion.Conditions::playerPredicate),
                BlockPredicate.CODEC.fieldOf("block").forGetter(PunchBlockCriterion.Conditions::blockPredicate)
        ).apply(instance, PunchBlockCriterion.Conditions::new));

        @Override
        public @NonNull Optional<ContextAwarePredicate> player() {
            return playerPredicate;
        }

        public boolean matches(ServerPlayer player, BlockPos pos) {
            return blockPredicate.matches(player.level(), pos);
        }
    }
}
