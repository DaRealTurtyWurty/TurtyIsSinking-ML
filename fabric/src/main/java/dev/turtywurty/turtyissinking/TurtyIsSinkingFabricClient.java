package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.ServicesClient;
import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class TurtyIsSinkingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TurtyIsSinkingClient.init();

        ClientTickEvents.END_CLIENT_TICK.register(_ -> ClientAfkTracker.tick());

        ServicesClient.REGISTRY.applyEntityRendererRegistrations(EntityRenderers::register);
    }
}
