package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.network.ClientboundOpenAgeVerificationScreenPacket;
import dev.turtywurty.turtyissinking.network.ClientboundOpenPhoneScreenPacket;
import dev.turtywurty.turtyissinking.network.ClientboundReverseJukeboxPacket;
import dev.turtywurty.turtyissinking.network.ServerboundAgeVerificationPacket;
import dev.turtywurty.turtyissinking.screen.AgeVerificationScreen;
import dev.turtywurty.turtyissinking.screen.PhoneCallScreen;
import dev.turtywurty.turtyissinking.sound.ReversedJukeboxTracker;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class FabricNetworking {
    private FabricNetworking() {
    }

    public static void registerPackets() {
        PayloadTypeRegistry.serverboundPlay().register(ServerboundAgeVerificationPacket.TYPE, ServerboundAgeVerificationPacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundOpenAgeVerificationScreenPacket.TYPE, ClientboundOpenAgeVerificationScreenPacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundReverseJukeboxPacket.TYPE, ClientboundReverseJukeboxPacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ClientboundOpenPhoneScreenPacket.TYPE, ClientboundOpenPhoneScreenPacket.CODEC);
    }

    public static void registerPacketReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(ServerboundAgeVerificationPacket.TYPE, (payload, context) -> {
            if (context.player() instanceof PlayerAgeVerification ageVerification) {
                ageVerification.turtyissinking$setAgeVerified(payload.verified());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(ClientboundOpenAgeVerificationScreenPacket.TYPE, (_, context) -> {
            context.client().execute(() -> context.client().setScreenAndShow(new AgeVerificationScreen()));
        });

        ClientPlayNetworking.registerGlobalReceiver(ClientboundReverseJukeboxPacket.TYPE, (payload, context) -> {
            context.client().execute(() -> ReversedJukeboxTracker.setReversed(payload.pos(), payload.reversed()));
        });

        ClientPlayNetworking.registerGlobalReceiver(ClientboundOpenPhoneScreenPacket.TYPE, (_, context) -> {
            context.client().execute(() -> context.client().setScreenAndShow(new PhoneCallScreen()));
        });
    }
}
