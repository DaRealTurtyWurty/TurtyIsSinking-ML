package dev.turtywurty.turtyissinking.init.client;

import dev.turtywurty.turtyissinking.init.ModEntityTypes;
import dev.turtywurty.turtyissinking.platform.ServicesClient;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class ModEntityRenderers {
    private ModEntityRenderers() {
    }

    public static void load() {
        ServicesClient.REGISTRY.registerEntityRenderer(ModEntityTypes.POOP_PROJECTILE.get(), ThrownItemRenderer::new);
    }
}