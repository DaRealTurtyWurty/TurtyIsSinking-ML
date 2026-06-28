package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModSounds;
import dev.turtywurty.turtyissinking.sound.FadableSimpleSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow
    private ClientLevel level;

    @Unique
    private static final float turtyissinking$THUNDER_SOUND_THRESHOLD = 0.1F;

    @Unique
    private FadableSimpleSoundInstance turtyissinking$thunderSoundInstance;

    @Inject(
            method = "handleGameEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;setThunderLevel(F)V"
            )
    )
    private void turtyissinking$handleGameEvent(ClientboundGameEventPacket packet, CallbackInfo callbackInfo) {
        Minecraft minecraft = Minecraft.getInstance();
        SoundManager soundManager = minecraft.getSoundManager();

        float thunderLevel = packet.getParam();
        if (turtyissinking$thunderSoundInstance != null && turtyissinking$thunderSoundInstance.isStopped()) {
            turtyissinking$thunderSoundInstance = null;
        }

        boolean hasThunderSound = turtyissinking$thunderSoundInstance != null;
        Constants.LOG.info("Thunder level: {}", thunderLevel);

        if (thunderLevel <= turtyissinking$THUNDER_SOUND_THRESHOLD) {
            if (hasThunderSound) {
                if (!turtyissinking$thunderSoundInstance.isFadingOut()) {
                    Constants.LOG.info("Fading out thunder sound");
                }

                turtyissinking$thunderSoundInstance.fadeOut();
            }

            return;
        }

        if (hasThunderSound) {
            turtyissinking$thunderSoundInstance.fadeIn();
            return;
        }

        if (minecraft.player != null) {
            turtyissinking$thunderSoundInstance = new FadableSimpleSoundInstance(ModSounds.THUNDER.get(), SoundSource.WEATHER, 1.0f, 1.0f, this.level.getRandom(), minecraft.player.blockPosition());
            turtyissinking$thunderSoundInstance.fadeIn();
            soundManager.play(turtyissinking$thunderSoundInstance);
            Constants.LOG.info("Fading in thunder sound");
        }
    }
}
