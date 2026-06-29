package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.item.PoopItem;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;

public final class ModItems {
    private ModItems() {
    }

    public static final RegistryHandle<PoopItem> POOP = Services.REGISTRY.registerItem("poop", PoopItem::new);

    public static void load() {
    }
}