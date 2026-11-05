package dev.turtywurty.turtyissinking.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(
            method = "keyPress",
            at = @At("HEAD")
    )
    private void turtyissinking$keyPress(long handle, int action, KeyEvent event, CallbackInfo ci) {
        if (action != InputConstants.RELEASE) {
            ClientAfkTracker.markActive();
        }
    }

    @Inject(method = "charTyped", at = @At("HEAD"))
    private void turtyissinking$charTyped(long handle, CharacterEvent event, CallbackInfo ci) {
        ClientAfkTracker.markActive();
    }
}
