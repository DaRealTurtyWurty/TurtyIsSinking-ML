package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.init.NeoForgeAttachments;
import dev.turtywurty.turtyissinking.platform.services.IPlatformHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

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
}
