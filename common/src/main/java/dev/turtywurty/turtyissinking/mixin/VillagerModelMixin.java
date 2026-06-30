package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.SnippableVillager;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.npc.VillagerModel;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerModel.class)
public class VillagerModelMixin {
    @Shadow
    @Final
    private ModelPart head;

    @Inject(
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/VillagerRenderState;)V",
            at = @At("TAIL")
    )
    private void turtyissinking$setupAnim(VillagerRenderState state, CallbackInfo callbackInfo) {
        if (state instanceof SnippableVillager snippableVillager) {
            this.head.getChild("nose").visible = !snippableVillager.turtyissinking$hasSnippedNose();
        }
    }
}
