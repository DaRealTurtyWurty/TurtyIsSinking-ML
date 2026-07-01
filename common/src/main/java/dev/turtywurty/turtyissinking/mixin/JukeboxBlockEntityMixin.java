package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.network.ClientboundReverseJukeboxPacket;
import dev.turtywurty.turtyissinking.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin {
    @Inject(
            method = "setTheItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/JukeboxSongPlayer;play(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/Holder;)V"
            )
    )
    private void turtyissinking$syncReverseJukebox(ItemStack itemStack, CallbackInfo callbackInfo) {
        JukeboxBlockEntity jukebox = (JukeboxBlockEntity) (Object) this;
        Level level = jukebox.getLevel();
        if (level == null || level.isClientSide())
            return;

        Component customName = itemStack.getCustomName();
        boolean reversed = customName != null && customName.getString().equalsIgnoreCase("dinnerbone");
        BlockPos pos = jukebox.getBlockPos();
        Services.NETWORK.sendToAll(level, new ClientboundReverseJukeboxPacket(pos, reversed));
    }
}
