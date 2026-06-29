package dev.turtywurty.turtyissinking.effect;

import dev.turtywurty.turtyissinking.init.ModItems;
import dev.turtywurty.turtyissinking.init.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.ThreadLocalRandom;

public class FlatulenceMobEffect extends MobEffect {
    private final RandomSource random = new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());

    public FlatulenceMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public FlatulenceMobEffect(MobEffectCategory category, int color, ParticleOptions particleOptions) {
        super(category, color, particleOptions);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return this.random.nextInt(Math.max(1, (60 * 20) / (amplification + 1))) == 0;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity mob, int amplification) {
        float volume = serverLevel.getRandom().nextFloat() + 0.25f;
        float pitch = serverLevel.getRandom().nextFloat() + 0.5f;
        serverLevel.playSound(null, mob, ModSounds.FART.get(), SoundSource.NEUTRAL, volume, pitch);

        if (amplification >= 5 && serverLevel.getRandom().nextInt(Math.max(1, 10 / (amplification - 4))) == 0) {
            Containers.dropItemStack(serverLevel, mob.getX(), mob.getY(), mob.getZ(), new ItemStack(ModItems.POOP.get()));
        }

        return true;
    }
}
