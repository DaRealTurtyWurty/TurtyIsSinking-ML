package dev.turtywurty.turtyissinking.platform.services;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryHelper {
    <T extends SoundEvent> RegistryHandle<T> registerSoundEvent(String name, Supplier<T> soundEvent);

    <T extends MobEffect> RegistryHandle<Holder<T>> registerMobEffect(String name, Supplier<T> mobEffect);

    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> entityBuilder);

    static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }

    static ResourceKey<EntityType<?>> createEntityTypeKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Constants.id(name));
    }
}