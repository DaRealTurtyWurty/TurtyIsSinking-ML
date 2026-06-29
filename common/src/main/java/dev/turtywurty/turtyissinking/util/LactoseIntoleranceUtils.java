package dev.turtywurty.turtyissinking.util;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public final class LactoseIntoleranceUtils {
    private LactoseIntoleranceUtils() {
    }

    public static boolean hasDeterministicChance(long worldSeed, UUID entityUuid) {
        long mixed = worldSeed;
        mixed ^= entityUuid.getMostSignificantBits();
        mixed = mix64(mixed);
        mixed ^= entityUuid.getLeastSignificantBits();
        mixed = mix64(mixed);

        // 20% chance: values 0,1,2,3,4 out of 0..24
        return Math.floorMod(mixed, 25) < 5;
    }

    private static long mix64(long value) {
        value = (value ^ (value >>> 30)) * 0xbf58476d1ce4e5b9L;
        value = (value ^ (value >>> 27)) * 0x94d049bb133111ebL;
        return value ^ (value >>> 31);
    }

    public static void applyLactoseIntolerance(ServerLevel level, LivingEntity entity) {
        long seed = level.getSeed();
        UUID entityUUID = entity.getUUID();
        Constants.LOG.info("Checking if entity {} has lactose intolerance (seed: {})", entityUUID, seed);
        if (!hasDeterministicChance(seed, entityUUID))
            return;

        int timeLasts = level.getRandom().nextInt(24_000) + 24_000; // 1-2 days in ticks
        int amplifier = 0;
        if (entity.hasEffect(ModMobEffects.LACTOSE_INTOLERANCE.get())) {
            amplifier = entity.getEffect(ModMobEffects.LACTOSE_INTOLERANCE.get()).getAmplifier() + 1;
        }

        entity.addEffect(new MobEffectInstance(ModMobEffects.LACTOSE_INTOLERANCE.get(), timeLasts, amplifier));
        Constants.LOG.info("Entity {} has lactose intolerance for {} ticks", entityUUID, timeLasts);
    }
}
