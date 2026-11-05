package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.entity.PoopProjectile;
import dev.turtywurty.turtymultiloader.registration.RegistrationHandle;
import dev.turtywurty.turtymultiloader.registration.RegistryService;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntityTypes {
    public static final RegistrationHandle<EntityType<?>, EntityType<PoopProjectile>> POOP_PROJECTILE =
            RegistryService.get().registerEntityType(Constants.id("poop_projectile"),
                    () -> EntityType.Builder.<PoopProjectile>of(PoopProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, Constants.id("poop_projectile"))));

    private ModEntityTypes() {
    }

    public static void load() {
    }
}
