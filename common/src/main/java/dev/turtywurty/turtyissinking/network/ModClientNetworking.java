package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.screen.AgeVerificationScreen;
import dev.turtywurty.turtyissinking.screen.PhoneCallScreen;
import dev.turtywurty.turtyissinking.sound.ReversedJukeboxTracker;
import dev.turtywurty.turtymultiloader.network.NetworkService;
import dev.turtywurty.turtymultiloader.network.PayloadPhase;
import net.minecraft.client.Minecraft;

public final class ModClientNetworking {
    private ModClientNetworking() {
    }

    public static void register() {
        NetworkService network = NetworkService.get();
        network.registerClientHandler(PayloadPhase.PLAY, ClientboundOpenAgeVerificationScreenPacket.TYPE,
                (_, _) -> Minecraft.getInstance().setScreenAndShow(new AgeVerificationScreen()));
        network.registerClientHandler(PayloadPhase.PLAY, ClientboundOpenPhoneScreenPacket.TYPE,
                (_, _) -> Minecraft.getInstance().setScreenAndShow(new PhoneCallScreen()));
        network.registerClientHandler(PayloadPhase.PLAY, ClientboundReverseJukeboxPacket.TYPE,
                (payload, _) -> ReversedJukeboxTracker.setReversed(payload.pos(), payload.reversed()));
    }
}
