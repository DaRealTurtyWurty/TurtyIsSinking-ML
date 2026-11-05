package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.util.ChatLimits;
import dev.turtywurty.turtyissinking.util.uwu.Uwuifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public final class TurtyIsSinkingNeoForgeClient {
    public TurtyIsSinkingNeoForgeClient() {
        TurtyIsSinkingClient.init();
        NeoForge.EVENT_BUS.addListener(TurtyIsSinkingNeoForgeClient::clientChat);
    }

    private static void clientChat(ClientChatEvent event) {
        event.setMessage(ChatLimits.truncateChatMessage(Uwuifier.DEFAULT.uwuify(event.getMessage())));
    }
}
