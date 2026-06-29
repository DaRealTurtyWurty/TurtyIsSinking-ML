package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.ServicesClient;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class TurtyIsSinkingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TurtyIsSinkingClient.init();

        ServicesClient.REGISTRY.applyEntityRendererRegistrations(EntityRenderers::register);
    }
}
