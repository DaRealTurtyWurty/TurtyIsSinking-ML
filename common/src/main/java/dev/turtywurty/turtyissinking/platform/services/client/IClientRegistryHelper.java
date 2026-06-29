package dev.turtywurty.turtyissinking.platform.services.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface IClientRegistryHelper {
    <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, EntityRendererProvider<T> provider);

    void applyEntityRendererRegistrations(EntityRendererRegistrar registrar);

    interface EntityRendererRegistrar {
        <T extends Entity> void register(EntityType<T> entityType, EntityRendererProvider<T> provider);
    }
}
