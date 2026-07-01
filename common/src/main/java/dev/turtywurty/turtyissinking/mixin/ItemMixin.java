package dev.turtywurty.turtyissinking.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.turtywurty.turtyissinking.init.ModTags;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import dev.turtywurty.turtyissinking.util.IPUtils;
import dev.turtywurty.turtyissinking.util.LactoseIntoleranceUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;",
                    shift = At.Shift.AFTER
            )
    )
    private void turtyissinking$finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        turtyissinking$handleLactoseIntolerance(itemStack, level, entity);
        turtyissinking$handleAllergies(itemStack, level, entity);
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/Consumable;startConsuming(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"
            ),
            cancellable = true
    )
    private void turtyissinking$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir, @Local(name = "stack") ItemStack stack) {
        String countryCode = IPUtils.getCountryCodeFromExternalIP();
        if (Objects.equals(countryCode, "IE")) {
            boolean isPotato = stack.is(Items.POTATO) || stack.is(Items.BAKED_POTATO) || stack.is(Items.POISONOUS_POTATO);
            if (!isPotato) {
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }

    @Unique
    private void turtyissinking$handleLactoseIntolerance(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!itemStack.is(ModTags.Items.CONTAINS_LACTOSE) || !(level instanceof ServerLevel serverLevel))
            return;

        LactoseIntoleranceUtils.applyLactoseIntolerance(serverLevel, entity);
    }

    @Unique
    private void turtyissinking$handleAllergies(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!(level instanceof ServerLevel) || !(entity instanceof Player player))
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
