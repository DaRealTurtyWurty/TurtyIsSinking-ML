package dev.turtywurty.turtyissinking.effect;

import dev.turtywurty.turtyissinking.init.ModMobEffects;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;

public class LactoseIntoleranceMobEffect extends MobEffect {
    public LactoseIntoleranceMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public LactoseIntoleranceMobEffect(MobEffectCategory category, int color, ParticleOptions particleOptions) {
        super(category, color, particleOptions);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, LivingEntity mob, int amplification) {
        mob.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 100, amplification + 2));
        mob.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, amplification));
        mob.addEffect(new MobEffectInstance(ModMobEffects.FLATULENCE.get(), 100, amplification));
        return true;
    }
}
