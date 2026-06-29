package dev.turtywurty.turtyissinking.entity;

import dev.turtywurty.turtyissinking.init.ModEntityTypes;
import dev.turtywurty.turtyissinking.init.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class PoopProjectile extends ThrowableItemProjectile {
    public PoopProjectile(EntityType<? extends PoopProjectile> type, Level level) {
        super(type, level);
    }

    public PoopProjectile(Level level, LivingEntity mob, ItemStack itemStack) {
        super(ModEntityTypes.POOP_PROJECTILE.get(), mob, level, itemStack);
    }

    public PoopProjectile(Level level, double x, double y, double z, ItemStack itemStack) {
        super(ModEntityTypes.POOP_PROJECTILE.get(), x, y, z, level, itemStack);
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return ModItems.POOP.get();
    }

    private ParticleOptions getParticle() {
        ItemStack item = getItem();
        return item.isEmpty()
                ? ParticleTypes.ITEM_SNOWBALL
                : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = getParticle();

            for (int i = 0; i < 4; ++i) {
                level().addParticle(particle, getX(), getY(), getZ(), 0.0F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        entity.hurt(damageSources().thrown(this, getOwner()), 1.0F);
        if (entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 1));
        }
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide()) {
            level().broadcastEntityEvent(this, (byte) 3);
            discard();
        }
    }
}
