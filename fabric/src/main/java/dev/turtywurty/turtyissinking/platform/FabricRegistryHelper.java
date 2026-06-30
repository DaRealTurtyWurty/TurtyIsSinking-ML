package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.mixin.CriteriaTriggersAccessor;
import dev.turtywurty.turtyissinking.platform.services.IRegistryHelper;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Function;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends MobEffect> RegistryHandle<Holder<T>> registerMobEffect(String name, Supplier<T> mobEffect) {
        Identifier id = Constants.id(name);
        T registered = Registry.register(BuiltInRegistries.MOB_EFFECT, id, mobEffect.get());
        Holder<T> holder = (Holder<T>) BuiltInRegistries.MOB_EFFECT.wrapAsHolder(registered);
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<T> get() {
                return holder;
            }
        };
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        ResourceKey<Item> key = IRegistryHelper.createItemKey(name);
        T registered = Registry.register(BuiltInRegistries.ITEM, key.identifier(), item.apply(new Item.Properties().setId(key)));
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return key.identifier();
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> entityBuilder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.createEntityTypeKey(name);
        EntityType<T> registered = Registry.register(BuiltInRegistries.ENTITY_TYPE, key.identifier(), entityBuilder.build(key));
        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return key.identifier();
            }

            @Override
            public EntityType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends CriterionTrigger<?>> RegistryHandle<T> registerCriteriaTrigger(String name, Supplier<T> trigger) {
        Identifier id = Constants.id(name);
        T registered = CriteriaTriggersAccessor.invokeRegister(id.toString(), trigger.get());
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
