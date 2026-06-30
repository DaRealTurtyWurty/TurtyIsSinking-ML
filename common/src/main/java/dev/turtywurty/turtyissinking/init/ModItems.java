package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.item.PoopItem;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;

public final class ModItems {
    private ModItems() {
    }

    public static final RegistryHandle<PoopItem> POOP = Services.REGISTRY.registerItem("poop", PoopItem::new);

    public static final RegistryHandle<Item> VILLAGER_NOSE = Services.REGISTRY.registerItem("villager_nose",
            properties -> new Item(
                    properties
                            .food(new FoodProperties(4, 0.3F, true))
                            .component(
                                    DataComponents.EQUIPPABLE,
                                    Equippable.builder(EquipmentSlot.HEAD)
                                            .setSwappable(false)
                                            .setEquipSound(Holder.direct(SoundEvents.VILLAGER_AMBIENT))
                                            .build()
                            )));

    public static void load() {
    }
}