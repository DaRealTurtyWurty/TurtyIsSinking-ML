package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.init.NeoForgeAttachments;
import dev.turtywurty.turtyissinking.platform.services.IPlatformHelper;
import net.minecraft.world.entity.Entity;
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
}
