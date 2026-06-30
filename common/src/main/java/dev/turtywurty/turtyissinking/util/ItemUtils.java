package dev.turtywurty.turtyissinking.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ItemUtils {
    private ItemUtils() {
    }

    public static void givePlayerItem(Player player, ItemStack itemStack) {
        int maxStackSize = itemStack.getMaxStackSize();
        int remaining = itemStack.getCount();

        while (remaining > 0) {
            int size = Math.min(maxStackSize, remaining);
            remaining -= size;
            ItemStack copyToDrop = itemStack.copyWithCount(size);
            boolean added = player.getInventory().add(copyToDrop);
            if (added && copyToDrop.isEmpty()) {
                ItemEntity drop = player.drop(itemStack.copy(), false);
                if (drop != null) {
                    drop.makeFakeItem();
                }

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.containerMenu.broadcastChanges();
            } else {
                ItemEntity drop = player.drop(copyToDrop, false);
                if (drop != null) {
                    drop.setNoPickUpDelay();
                    drop.setTarget(player.getUUID());
                }
            }
        }
    }
}
