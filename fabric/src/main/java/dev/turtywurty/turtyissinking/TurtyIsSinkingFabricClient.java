package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.ServicesClient;
import dev.turtywurty.turtyissinking.util.ChatLimits;
import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import dev.turtywurty.turtyissinking.util.uwu.Uwuifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class TurtyIsSinkingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TurtyIsSinkingClient.init();

        ClientTickEvents.END_CLIENT_TICK.register(_ -> ClientAfkTracker.tick());
        ClientSendMessageEvents.MODIFY_CHAT.register(message -> ChatLimits.truncateChatMessage(Uwuifier.DEFAULT.uwuify(message)));

        ServicesClient.REGISTRY.applyEntityRendererRegistrations(EntityRenderers::register);
    }
}
