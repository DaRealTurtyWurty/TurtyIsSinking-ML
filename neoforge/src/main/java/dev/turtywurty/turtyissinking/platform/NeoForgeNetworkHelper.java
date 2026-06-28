package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.platform.services.INetworkHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

public final class NeoForgeNetworkHelper implements INetworkHelper {
    @Override
    public void sendToServer(CustomPacketPayload packet) {
        ClientPacketDistributor.sendToServer(packet);
    }

    @Override
    public void sendToClient(Player player, CustomPacketPayload packet) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, packet);
        }
    }

    @Override
    public void sendToAll(Level level, CustomPacketPayload packet) {
        PacketDistributor.sendToAllPlayers(packet);
    }
}
