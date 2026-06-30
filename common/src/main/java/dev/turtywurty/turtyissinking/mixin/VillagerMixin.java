package dev.turtywurty.turtyissinking.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.turtywurty.turtyissinking.init.ModItems;
import dev.turtywurty.turtyissinking.init.ModTags;
import dev.turtywurty.turtyissinking.util.ItemUtils;
import dev.turtywurty.turtyissinking.util.SnippableVillager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public class VillagerMixin {
    @Inject(
            method = "mobInteract",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"
            ),
            cancellable = true
    )
    private void turtyissinking$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> callbackInfo, @Local(name = "itemStack") ItemStack itemStack) {
        if (!itemStack.is(ModTags.Items.SHEAR_TOOLS)
                || !((Object) this instanceof SnippableVillager snippableVillager)
                || snippableVillager.turtyissinking$hasSnippedNose())
            return;

        snippableVillager.turtyissinking$setNoseSnipped(true);
        ItemUtils.givePlayerItem(player, new ItemStack(ModItems.VILLAGER_NOSE.get()));
        player.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
        itemStack.hurtAndBreak(1, player, hand);
        callbackInfo.setReturnValue(InteractionResult.SUCCESS_SERVER);
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void turtyissinking$addAdditionalSaveData(ValueOutput output, CallbackInfo callbackInfo) {
        if ((Object) this instanceof SnippableVillager snippableVillager) {
            output.putBoolean("NoseSnipped", snippableVillager.turtyissinking$hasSnippedNose());
        }
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void turtyissinking$readAdditionalSaveData(ValueInput input, CallbackInfo callbackInfo) {
        if ((Object) this instanceof SnippableVillager snippableVillager) {
            snippableVillager.turtyissinking$setNoseSnipped(input.getBooleanOr("NoseSnipped", false));
        }
    }
}
