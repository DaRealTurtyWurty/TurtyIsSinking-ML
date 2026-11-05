package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.util.ChatLimits;
import dev.turtywurty.turtyissinking.util.uwu.Uwuifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

public class TurtyIsSinkingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TurtyIsSinkingClient.init();

        ClientSendMessageEvents.MODIFY_CHAT
            .register(message -> ChatLimits.truncateChatMessage(Uwuifier.DEFAULT.uwuify(message)));
    }
}
