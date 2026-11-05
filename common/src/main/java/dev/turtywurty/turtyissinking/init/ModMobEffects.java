package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.effect.ADHDMobEffect;
import dev.turtywurty.turtyissinking.effect.FlatulenceMobEffect;
import dev.turtywurty.turtyissinking.effect.LactoseIntoleranceMobEffect;
import dev.turtywurty.turtymultiloader.registration.RegistrationHandle;
import dev.turtywurty.turtymultiloader.registration.RegistryService;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class ModMobEffects {
    public static final RegistrationHandle<MobEffect, MobEffect> LACTOSE_INTOLERANCE = RegistryService.get().register(
        Registries.MOB_EFFECT, Constants.id("lactose_intolerance"),
        () -> new LactoseIntoleranceMobEffect(MobEffectCategory.HARMFUL, 0xFDFFF5));
    public static final RegistrationHandle<MobEffect, MobEffect> FLATULENCE = RegistryService.get().register(
        Registries.MOB_EFFECT, Constants.id("flatulence"),
        () -> new FlatulenceMobEffect(MobEffectCategory.HARMFUL, 0x351E10));
    public static final RegistrationHandle<MobEffect, MobEffect> ADHD = RegistryService.get().register(
        Registries.MOB_EFFECT, Constants.id("adhd"),
        () -> new ADHDMobEffect(MobEffectCategory.HARMFUL, 0xFF0000));

    private ModMobEffects() {
    }

    public static void load() {
    }
}
