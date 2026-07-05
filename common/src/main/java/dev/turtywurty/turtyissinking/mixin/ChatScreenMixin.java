package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.ChatLimits;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @ModifyConstant(method = "init", constant = @Constant(intValue = 256))
    private int turtyissinking$increaseInputMessageLength(int original) {
        return ChatLimits.MAX_CHAT_MESSAGE_LENGTH;
    }
}
