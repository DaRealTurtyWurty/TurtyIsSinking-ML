package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onMove", at = @At("HEAD"))
    private void turtyissinking$onMove(long handle, double xpos, double ypos, CallbackInfo ci) {
        ClientAfkTracker.markActive();
    }

    @Inject(method = "onButton", at = @At("HEAD"))
    private void turtyissinking$onButton(long handle, MouseButtonInfo rawButtonInfo, int action, CallbackInfo ci) {
        if (action != GLFW.GLFW_RELEASE) {
            ClientAfkTracker.markActive();
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"))
    private void turtyissinking$onScroll(long handle, double xoffset, double yoffset, CallbackInfo ci) {
        ClientAfkTracker.markActive();
    }
}