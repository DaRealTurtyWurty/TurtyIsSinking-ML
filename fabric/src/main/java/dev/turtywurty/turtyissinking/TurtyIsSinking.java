package dev.turtywurty.turtyissinking;

import net.fabricmc.api.ModInitializer;

public class TurtyIsSinking implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        FabricNetworking.registerPackets();
        FabricNetworking.registerPacketReceivers();
    }
}
