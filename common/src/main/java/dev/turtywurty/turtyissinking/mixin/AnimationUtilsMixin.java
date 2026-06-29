package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Zombie67;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.UndeadRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimationUtils.class)
public class AnimationUtilsMixin {
    @Unique
    private static final float HORIZONTAL_ARM_ROTATION = -(float) Math.PI / 2.0F;

    @Inject(
            method = "animateZombieArms",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/AnimationUtils;bobArms(Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;F)V"
            ),
            cancellable = true
    )
    private static <T extends UndeadRenderState> void turtyissinking$animateZombieArms(ModelPart leftArm, ModelPart rightArm, boolean aggressive, T state, CallbackInfo callbackInfo) {
        if (state instanceof Zombie67 sixSeven && sixSeven.turtyissinking$is67ing()) {
            float armWave = Mth.sin(state.ageInTicks * 0.75F) * 0.65F;

            leftArm.xRot = HORIZONTAL_ARM_ROTATION + armWave;
            rightArm.xRot = HORIZONTAL_ARM_ROTATION - armWave;
            leftArm.zRot = 0.0F;
            rightArm.zRot = 0.0F;
            callbackInfo.cancel();
        }
    }
}
