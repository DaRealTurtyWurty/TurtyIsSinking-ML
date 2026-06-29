package dev.turtywurty.turtyissinking;

import net.fabricmc.api.ModInitializer;

public class TurtyIsSinkingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TurtyIsSinkingCommon.init();

        FabricNetworking.registerPackets();
        FabricNetworking.registerPacketReceivers();
    }
}
