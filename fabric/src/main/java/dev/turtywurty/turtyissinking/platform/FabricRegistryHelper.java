package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.services.IRegistryHelper;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public final class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends SoundEvent> RegistryHandle<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        Identifier id = Constants.id(name);
        T registered = Registry.register(BuiltInRegistries.SOUND_EVENT, id, soundEvent.get());
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }
}
