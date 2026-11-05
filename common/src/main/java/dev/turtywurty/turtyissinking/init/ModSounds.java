package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtymultiloader.registration.RegistrationHandle;
import dev.turtywurty.turtymultiloader.registration.RegistryService;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    public static final RegistrationHandle<SoundEvent, SoundEvent> THUNDER = RegistryService.get().register(Registries.SOUND_EVENT, Constants.id("thunder"),
            () -> SoundEvent.createVariableRangeEvent(Constants.id("thunder")));
    public static final RegistrationHandle<SoundEvent, SoundEvent> FART = RegistryService.get().register(Registries.SOUND_EVENT, Constants.id("fart"),
            () -> SoundEvent.createVariableRangeEvent(Constants.id("fart")));
    public static final RegistrationHandle<SoundEvent, SoundEvent> BABY_ZOMBIE_SIX_SEVEN = RegistryService.get().register(Registries.SOUND_EVENT, Constants.id("baby_zombie_six_seven"),
            () -> SoundEvent.createVariableRangeEvent(Constants.id("baby_zombie_six_seven")));

    private ModSounds() {
    }

    public static void load() {
    }
}
