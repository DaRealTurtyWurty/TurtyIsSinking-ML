package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.init.NeoForgeAttachments;
import dev.turtywurty.turtyissinking.platform.services.IPlatformHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Map;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public boolean getZombie67ing(Entity entity, boolean fallback) {
        return entity.getData(NeoForgeAttachments.ZOMBIE_67ING.get());
    }

    @Override
    public boolean setZombie67ing(Entity entity, boolean value) {
        if (entity.getData(NeoForgeAttachments.ZOMBIE_67ING.get()) != value) {
            entity.setData(NeoForgeAttachments.ZOMBIE_67ING.get(), value);
            if (!entity.level().isClientSide()) {
                entity.syncData(NeoForgeAttachments.ZOMBIE_67ING.get());
            }
        }

        return value;
    }

    @Override
    public boolean hasNoseSnipped(Villager villager, boolean fallback) {
        return villager.getData(NeoForgeAttachments.VILLAGER_NOSE_SNIPPED.get());
    }

    @Override
    public boolean setNoseSnipped(Villager villager, boolean value) {
        if (villager.getData(NeoForgeAttachments.VILLAGER_NOSE_SNIPPED.get()) != value) {
            villager.setData(NeoForgeAttachments.VILLAGER_NOSE_SNIPPED.get(), value);
            if (!villager.level().isClientSide()) {
                villager.syncData(NeoForgeAttachments.VILLAGER_NOSE_SNIPPED.get());
            }
        }

        return value;
    }

    @Override
    public boolean isAllergic(Player player, Item item) {
        Map<Identifier, Boolean> allergicItems = player.getData(NeoForgeAttachments.ALLERGIC_ITEMS.get());
        Boolean existingValue = allergicItems.get(BuiltInRegistries.ITEM.getKey(item));
        if (existingValue != null)
            return existingValue;

        boolean isAllergic = Math.random() < 0.1;
        allergicItems.put(BuiltInRegistries.ITEM.getKey(item), isAllergic);
        player.setData(NeoForgeAttachments.ALLERGIC_ITEMS.get(), allergicItems);
        return isAllergic;
    }
}
