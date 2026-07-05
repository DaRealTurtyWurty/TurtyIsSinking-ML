package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.ChatLimits;
import net.minecraft.network.chat.SignedMessageBody;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SignedMessageBody.Packed.class)
public class SignedMessageBodyPackedMixin {
    @ModifyConstant(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", constant = @Constant(intValue = 256))
    private static int turtyissinking$increaseReadMessageLength(int original) {
        return ChatLimits.MAX_CHAT_MESSAGE_LENGTH;
    }

    @ModifyConstant(method = "write", constant = @Constant(intValue = 256))
    private int turtyissinking$increaseWriteMessageLength(int original) {
        return ChatLimits.MAX_CHAT_MESSAGE_LENGTH;
    }
}
