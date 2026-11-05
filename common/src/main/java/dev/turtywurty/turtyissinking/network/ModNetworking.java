package dev.turtywurty.turtyissinking.network;

import dev.turtywurty.turtyissinking.init.ModMobEffects;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import dev.turtywurty.turtymultiloader.network.NetworkService;
import dev.turtywurty.turtymultiloader.network.PayloadRegistrationOptions;
import net.minecraft.world.effect.MobEffectInstance;

public final class ModNetworking {
    private ModNetworking() {
    }

    public static void register() {
        NetworkService network = NetworkService.get();
        var options = PayloadRegistrationOptions.DEFAULT;
        network.registerPlayClientbound(ClientboundOpenAgeVerificationScreenPacket.TYPE,
                ClientboundOpenAgeVerificationScreenPacket.CODEC, options);
        network.registerPlayClientbound(ClientboundReverseJukeboxPacket.TYPE,
                ClientboundReverseJukeboxPacket.CODEC, options);
        network.registerPlayClientbound(ClientboundOpenPhoneScreenPacket.TYPE,
                ClientboundOpenPhoneScreenPacket.CODEC, options);
        network.registerPlayServerbound(ServerboundAgeVerificationPacket.TYPE,
                ServerboundAgeVerificationPacket.CODEC, options, (payload, context) ->
                        context.sender().ifPresent(player -> {
                            if (player instanceof PlayerAgeVerification verification) {
                                verification.turtyissinking$setAgeVerified(payload.verified());
                            }
                        }));
        network.registerPlayServerbound(ServerboundNotifyAfkPacket.TYPE,
                ServerboundNotifyAfkPacket.CODEC, options, (payload, context) ->
                        context.sender().ifPresent(player -> player.addEffect(new MobEffectInstance(
                                ModMobEffects.ADHD.holder(), 5 * 20,
                                Math.clamp(payload.inactiveTicks() / (20 * 60), 0, 255)))));
    }
}
