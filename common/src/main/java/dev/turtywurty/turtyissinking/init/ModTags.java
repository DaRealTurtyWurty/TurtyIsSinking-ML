package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModTags {
    public static class Items {
        public static final TagKey<Item> CONTAINS_LACTOSE = createTag("contains_lactose");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Constants.id(name));
        }
    }
}
