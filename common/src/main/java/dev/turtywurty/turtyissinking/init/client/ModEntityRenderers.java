package dev.turtywurty.turtyissinking.init.client;

import dev.turtywurty.turtyissinking.init.ModEntityTypes;
import dev.turtywurty.turtymultiloader.client.registration.ClientRegistrations;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class ModEntityRenderers {
    private ModEntityRenderers() {
    }

    public static void load() {
        ClientRegistrations.registerEntityRenderer(ModEntityTypes.POOP_PROJECTILE, ThrownItemRenderer::new);
    }
}
