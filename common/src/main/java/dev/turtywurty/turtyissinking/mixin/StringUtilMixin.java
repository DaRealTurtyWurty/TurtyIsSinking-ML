package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.ChatLimits;
import net.minecraft.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StringUtil.class)
public class StringUtilMixin {
    @ModifyConstant(method = "trimChatMessage", constant = @Constant(intValue = 256))
    private static int turtyissinking$increaseTrimmedChatMessageLength(int original) {
        return ChatLimits.MAX_CHAT_MESSAGE_LENGTH;
    }
}
