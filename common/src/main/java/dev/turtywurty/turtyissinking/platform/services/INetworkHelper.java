package dev.turtywurty.turtyissinking.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface INetworkHelper {
    void sendToServer(CustomPacketPayload packet);

    void sendToClient(Player player, CustomPacketPayload packet);

    void sendToAll(Level level, CustomPacketPayload packet);
}
