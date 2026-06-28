package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.services.IRegistryHelper;
import dev.turtywurty.turtyissinking.platform.services.RegistryHandle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    public static void load(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    @Override
    public <T extends SoundEvent> RegistryHandle<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        Identifier id = Constants.id(name);
        DeferredHolder<SoundEvent, T> registered = SOUND_EVENTS.register(id.getPath(), soundEvent);
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered.get();
            }
        };
    }
}
