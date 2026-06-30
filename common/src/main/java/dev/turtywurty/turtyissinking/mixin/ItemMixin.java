package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.init.ModTags;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import dev.turtywurty.turtyissinking.util.LactoseIntoleranceUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/Consumable;onConsume(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;",
                    shift = At.Shift.AFTER
            )
    )
    private void turtyissinking$finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        turtyissinking$handleLactoseIntolerance(itemStack, level, entity);
        turtyissinking$handleAllergies(itemStack, level, entity);
    }

    @Unique
    private void turtyissinking$handleLactoseIntolerance(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!itemStack.is(ModTags.Items.CONTAINS_LACTOSE) || !(level instanceof ServerLevel serverLevel))
            return;

        LactoseIntoleranceUtils.applyLactoseIntolerance(serverLevel, entity);
    }

    @Unique
    private void turtyissinking$handleAllergies(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player))
            return;

        if (!Services.PLATFORM.isAllergic(player, itemStack.getItem()))
            return;

        boolean hasAllergicReaction = AllergyUtils.hasAllergicReaction(level);
        if (!hasAllergicReaction)
            return;

        List<MobEffectInstance> effects = AllergyUtils.getAllergyEffects(level.getRandom());
        for (MobEffectInstance effect : effects) {
            player.addEffect(effect);
        }

        player.sendSystemMessage(AllergyUtils.ALLERGY_MESSAGE);
    }
}
