package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Climbable;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "onClimbable",
            at = @At("HEAD"),
            cancellable = true
    )
    private void turtyissinking$onClimbable(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if ((Object) this instanceof Climbable climbable && climbable.turtyissinking$isClimbing()) {
            callbackInfoReturnable.setReturnValue(climbable.turtyissinking$isClimbing());
        }
    }
}
