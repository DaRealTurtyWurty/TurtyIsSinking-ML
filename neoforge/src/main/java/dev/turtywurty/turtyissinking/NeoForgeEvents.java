package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.ModMobEffects;
import dev.turtywurty.turtyissinking.network.*;
import dev.turtywurty.turtyissinking.screen.AgeVerificationScreen;
import dev.turtywurty.turtyissinking.screen.PhoneCallScreen;
import dev.turtywurty.turtyissinking.sound.ReversedJukeboxTracker;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);

        registrar.playToClient(ClientboundOpenAgeVerificationScreenPacket.TYPE, ClientboundOpenAgeVerificationScreenPacket.CODEC);
        registrar.playToClient(ClientboundReverseJukeboxPacket.TYPE, ClientboundReverseJukeboxPacket.CODEC);

        registrar.playToServer(ServerboundAgeVerificationPacket.TYPE, ServerboundAgeVerificationPacket.CODEC, (payload, context) -> {
            Player player = context.player();
            if (player instanceof PlayerAgeVerification ageVerification) {
                ageVerification.turtyissinking$setAgeVerified(payload.verified());
            }
        });

        registrar.playToClient(ClientboundOpenPhoneScreenPacket.TYPE, ClientboundOpenPhoneScreenPacket.CODEC);
        registrar.playToServer(ServerboundNotifyAfkPacket.TYPE, ServerboundNotifyAfkPacket.CODEC, (payload, context) -> {
            context.player().addEffect(new MobEffectInstance(ModMobEffects.ADHD.get(), 5 * 20, payload.inactiveTicks() / (20 * 60)));
        });
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterClientPayloadHandlersEvent event) {
        event.register(ClientboundOpenAgeVerificationScreenPacket.TYPE,
                (_, context) -> context.enqueueWork(() -> Minecraft.getInstance().setScreenAndShow(new AgeVerificationScreen())));

        event.register(ClientboundReverseJukeboxPacket.TYPE,
                (payload, context) -> context.enqueueWork(() -> ReversedJukeboxTracker.setReversed(payload.pos(), payload.reversed())));

        event.register(ClientboundOpenPhoneScreenPacket.TYPE,
                (_, context) -> context.enqueueWork(() -> Minecraft.getInstance().setScreenAndShow(new PhoneCallScreen())));
    }
}
