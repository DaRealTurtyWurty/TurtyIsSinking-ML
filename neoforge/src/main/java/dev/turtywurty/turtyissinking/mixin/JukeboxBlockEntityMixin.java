package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.network.ClientboundReverseJukeboxPacket;
import dev.turtywurty.turtymultiloader.network.NetworkService;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin {
    @Inject(method = "itemChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/JukeboxSongPlayer;play(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/Holder;)V"))
    private void turtyissinking$syncReverseJukebox(CallbackInfo callbackInfo) {
        JukeboxBlockEntity jukebox = (JukeboxBlockEntity) (Object) this;
        if (!(jukebox.getLevel() instanceof ServerLevel level))
            return;

        ItemStack itemStack = jukebox.getTheItem();
        Component customName = itemStack.getCustomName();
        boolean reversed = customName != null && customName.getString().equalsIgnoreCase("dinnerbone");
        BlockPos pos = jukebox.getBlockPos();
        NetworkService.get().sendToDimension(level, new ClientboundReverseJukeboxPacket(pos, reversed));
    }
}
