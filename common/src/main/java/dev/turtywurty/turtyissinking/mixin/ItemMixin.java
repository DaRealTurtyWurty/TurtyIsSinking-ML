package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.init.ModTags;
import dev.turtywurty.turtyissinking.util.LactoseIntoleranceUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        if (!itemStack.is(ModTags.Items.CONTAINS_LACTOSE) || !(level instanceof ServerLevel serverLevel))
            return;

        LactoseIntoleranceUtils.applyLactoseIntolerance(serverLevel, entity);
    }
}
