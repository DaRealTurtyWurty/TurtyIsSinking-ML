package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.effect.ADHDMobEffect;
import dev.turtywurty.turtyissinking.effect.FlatulenceMobEffect;
import dev.turtywurty.turtyissinking.effect.LactoseIntoleranceMobEffect;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public final class ModMobEffects {
    private ModMobEffects() {
    }

    public static final RegistryHandle<Holder<MobEffect>> LACTOSE_INTOLERANCE = Services.REGISTRY.registerMobEffect("lactose_intolerance",
            () -> new LactoseIntoleranceMobEffect(MobEffectCategory.HARMFUL, 0xFDFFF5));

    public static final RegistryHandle<Holder<MobEffect>> FLATULENCE = Services.REGISTRY.registerMobEffect("flatulence",
            () -> new FlatulenceMobEffect(MobEffectCategory.HARMFUL, 0x351E10));

    public static final RegistryHandle<Holder<MobEffect>> ADHD = Services.REGISTRY.registerMobEffect("adhd",
            () -> new ADHDMobEffect(MobEffectCategory.HARMFUL, 0xFF0000));

    public static void load() {
    }
}
