package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.ServicesClient;
import dev.turtywurty.turtyissinking.util.ChatLimits;
import dev.turtywurty.turtyissinking.util.ClientAfkTracker;
import dev.turtywurty.turtyissinking.util.uwu.Uwuifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        ClientAfkTracker.tick();
    }

    @SubscribeEvent
    public static void clientChat(ClientChatEvent event) {
        event.setMessage(ChatLimits.truncateChatMessage(Uwuifier.DEFAULT.uwuify(event.getMessage())));
    }
}
