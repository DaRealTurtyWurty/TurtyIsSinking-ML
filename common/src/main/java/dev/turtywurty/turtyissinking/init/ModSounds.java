package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.RegistryHandle;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    private ModSounds() {
    }

    public static final RegistryHandle<SoundEvent> THUNDER = Services.REGISTRY.registerSoundEvent("thunder",
            () -> SoundEvent.createVariableRangeEvent(Constants.id("thunder")));

    public static void load() {

    }
}
