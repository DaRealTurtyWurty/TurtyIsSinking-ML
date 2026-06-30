package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.init.ModCriteria;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(
            method = "setGameMode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"
            )
    )
    private void turtyissinking$setGameMode(GameType mode, CallbackInfoReturnable<Boolean> callbackInfo) {
        ModCriteria.GAMEMODE.get().trigger((ServerPlayer) (Object) this);
    }
}
