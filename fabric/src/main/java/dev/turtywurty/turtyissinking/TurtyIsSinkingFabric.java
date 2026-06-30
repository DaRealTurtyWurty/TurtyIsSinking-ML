package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.FabricAttachments;
import net.fabricmc.api.ModInitializer;

public class TurtyIsSinkingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TurtyIsSinkingCommon.init();

        FabricNetworking.registerPackets();
        FabricNetworking.registerPacketReceivers();

        FabricAttachments.load();
    }
}
