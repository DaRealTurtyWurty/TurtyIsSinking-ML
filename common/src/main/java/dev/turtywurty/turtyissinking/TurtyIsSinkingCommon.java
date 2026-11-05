package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.*;
import dev.turtywurty.turtyissinking.network.ModNetworking;
import dev.turtywurty.turtymultiloader.registration.RegistryService;

public class TurtyIsSinkingCommon {
    public static void init() {
        ModSounds.load();
        ModMobEffects.load();
        ModItems.load();
        ModEntityTypes.load();
        ModCriteria.load();
        ModAttachments.load();
        ModNetworking.register();
        RegistryService.get().apply();
    }
}
