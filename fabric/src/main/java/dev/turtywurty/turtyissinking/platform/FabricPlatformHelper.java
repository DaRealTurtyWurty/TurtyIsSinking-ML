package dev.turtywurty.turtyissinking.platform;

import dev.turtywurty.turtyissinking.platform.services.IPlatformHelper;
import dev.turtywurty.turtyissinking.util.SnippableVillager;
import dev.turtywurty.turtyissinking.util.Zombie67Data;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean getZombie67ing(Entity entity, boolean fallback) {
        return entity instanceof Zombie67Data zombie67Data ? zombie67Data.turtyissinking$getSynced67ing() : fallback;
    }

    @Override
    public boolean setZombie67ing(Entity entity, boolean value) {
        if (entity instanceof Zombie67Data zombie67Data) {
            zombie67Data.turtyissinking$setSynced67ing(value);
        }

        return value;
    }

    @Override
    public boolean hasNoseSnipped(Villager villager, boolean fallback) {
        return villager instanceof SnippableVillager snippableVillager ? snippableVillager.turtyissinking$hasSnippedNose() : fallback;
    }

    @Override
    public boolean setNoseSnipped(Villager villager, boolean value) {
        if (villager instanceof SnippableVillager snippableVillager) {
            snippableVillager.turtyissinking$setNoseSnipped(value);
        }

        return value;
    }
}
