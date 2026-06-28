package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.network.ClientboundOpenAgeVerificationScreenPacket;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public class AnimalMixin {
    @Inject(
            method = "setInLove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void turtyissinking$setInLove(@Nullable Player player, CallbackInfo callbackInfo) {
        if (!(player instanceof PlayerAgeVerification ageVerification) || ageVerification.turtyissinking$isAgeVerified())
            return;

        callbackInfo.cancel();

        if (ageVerification.turtyissinking$isOnCooldown()) {
            player.sendSystemMessage(PlayerAgeVerification.AGE_VERIFICATION_REQUIRED_MESSAGE);
            return;
        }

        Services.NETWORK.sendToClient(player, ClientboundOpenAgeVerificationScreenPacket.INSTANCE);
    }
}
