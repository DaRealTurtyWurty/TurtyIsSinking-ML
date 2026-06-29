package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.ServicesClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class TurtyIsSinkingNeoForgeClient {
    private TurtyIsSinkingNeoForgeClient() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        TurtyIsSinkingClient.init();
        ServicesClient.REGISTRY.applyEntityRendererRegistrations(event::registerEntityRenderer);
    }
}
