package dev.turtywurty.turtyissinking.platform.services;

import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public interface IRegistryHelper {
    <T extends SoundEvent> RegistryHandle<T> registerSoundEvent(String name, Supplier<T> soundEvent);
}
