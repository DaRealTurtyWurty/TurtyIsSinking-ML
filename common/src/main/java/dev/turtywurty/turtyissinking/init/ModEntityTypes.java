package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.entity.PoopProjectile;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntityTypes {
    private ModEntityTypes() {
    }

    public static final RegistryHandle<EntityType<PoopProjectile>> POOP_PROJECTILE =
            Services.REGISTRY.registerEntityType("poop_projectile",
                    EntityType.Builder.<PoopProjectile>of(PoopProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(20));

    public static void load() {
    }
}
