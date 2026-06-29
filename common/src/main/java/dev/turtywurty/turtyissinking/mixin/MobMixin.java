package dev.turtywurty.turtyissinking.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.golem.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobMixin {
    @Inject(
            method = "createNavigation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void turtyissinking$createNavigation(CallbackInfoReturnable<PathNavigation> callbackInfoReturnable) {
        if ((Object) this instanceof IronGolem ironGolem) {
            callbackInfoReturnable.setReturnValue(new WallClimberNavigation(ironGolem, ironGolem.level()));
        }
    }
}
