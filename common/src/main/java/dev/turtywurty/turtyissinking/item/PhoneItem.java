package dev.turtywurty.turtyissinking.item;

import dev.turtywurty.turtyissinking.network.ClientboundOpenPhoneScreenPacket;
import dev.turtywurty.turtyissinking.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class PhoneItem extends Item {
    public PhoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (!level.isClientSide()) {
            Services.NETWORK.sendToClient(player, ClientboundOpenPhoneScreenPacket.INSTANCE);
            return InteractionResult.SUCCESS_SERVER;
        }

        return InteractionResult.SUCCESS;
    }
}
