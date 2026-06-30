package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.SnippableVillager;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerRenderer.class)
public class VillagerRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/npc/villager/Villager;Lnet/minecraft/client/renderer/entity/state/VillagerRenderState;F)V",
            at = @At("TAIL")
    )
    private void turtyissinking$extractRenderState(Villager entity, VillagerRenderState state, float partialTicks, CallbackInfo callbackInfo) {
        if (state instanceof SnippableVillager snippableVillagerState && entity instanceof SnippableVillager snippableVillagerEntity) {
            snippableVillagerState.turtyissinking$setNoseSnipped(snippableVillagerEntity.turtyissinking$hasSnippedNose());
        }
    }
}
