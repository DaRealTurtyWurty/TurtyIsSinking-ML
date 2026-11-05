package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.item.PhoneItem;
import dev.turtywurty.turtyissinking.item.PoopItem;
import dev.turtywurty.turtymultiloader.registration.RegistrationHandle;
import dev.turtywurty.turtymultiloader.registration.RegistryService;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;

public final class ModItems {
    public static final RegistrationHandle<Item, PoopItem> POOP = RegistryService.get().registerItem(Constants.id("poop"), () -> new PoopItem(properties("poop")));
    public static final RegistrationHandle<Item, Item> VILLAGER_NOSE = RegistryService.get().registerItem(Constants.id("villager_nose"),
            () -> new Item(
                    properties("villager_nose")
                            .food(new FoodProperties(4, 0.3F, true))
                            .component(
                                    DataComponents.EQUIPPABLE,
                                    Equippable.builder(EquipmentSlot.HEAD)
                                            .setSwappable(false)
                                            .setEquipSound(Holder.direct(SoundEvents.VILLAGER_AMBIENT))
                                            .build()
                            )));
    public static final RegistrationHandle<Item, PhoneItem> PHONE = RegistryService.get().registerItem(Constants.id("phone"), () -> new PhoneItem(properties("phone")));

    private ModItems() {
    }

    private static Item.Properties properties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Constants.id(name)));
    }

    public static void load() {
    }
}