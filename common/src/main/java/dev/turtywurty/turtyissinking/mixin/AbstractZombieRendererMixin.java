package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Zombie67;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractZombieRenderer.class)
public class AbstractZombieRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/monster/zombie/Zombie;Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;F)V",
            at = @At("TAIL")
    )
    private void turtyissinking$extractRenderState(Zombie zombie, ZombieRenderState state, float partialTick, CallbackInfo callbackInfo) {
        if (zombie instanceof Zombie67 entity67 && state instanceof Zombie67 state67) {
            state67.turtyissinking$set67ing(entity67.turtyissinking$is67ing());
        }
    }
}
