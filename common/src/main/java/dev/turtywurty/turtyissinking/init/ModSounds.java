package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    private ModSounds() {
    }

    public static final RegistryHandle<SoundEvent> THUNDER = Services.REGISTRY.registerSoundEvent("thunder",
            () -> SoundEvent.createVariableRangeEvent(Constants.id("thunder")));

    public static final RegistryHandle<SoundEvent> FART = Services.REGISTRY.registerSoundEvent("fart",
            () -> SoundEvent.createVariableRangeEvent(Constants.id("fart")));

    public static void load() {
    }
}
