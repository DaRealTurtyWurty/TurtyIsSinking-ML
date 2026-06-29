package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.services.IRegistryHelper;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public final class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Constants.MOD_ID);

    public static void load(IEventBus bus) {
        SOUND_EVENTS.register(bus);
        MOB_EFFECTS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends MobEffect> RegistryHandle<Holder<T>> registerMobEffect(String name, Supplier<T> mobEffect) {
        Identifier id = Constants.id(name);
        DeferredHolder<MobEffect, T> registered = MOB_EFFECTS.register(id.getPath(), mobEffect);
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<T> get() {
                return (Holder<T>) registered.getDelegate();
            }
        };
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        Identifier id = Constants.id(name);
        DeferredHolder<Item, T> registered = ITEMS.registerItem(id.getPath(), item);
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

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> entityBuilder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.createEntityTypeKey(name);
        DeferredHolder<EntityType<?>, EntityType<T>> registered = ENTITIES.register(key.identifier().getPath(), () -> entityBuilder.build(key));
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return key.identifier();
            }

            @Override
            public EntityType<T> get() {
                return registered.get();
            }
        };
    }
}
