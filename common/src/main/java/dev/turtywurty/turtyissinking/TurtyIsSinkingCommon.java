package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.*;

public class TurtyIsSinkingCommon {
    public static void init() {
        ModSounds.load();
        ModMobEffects.load();
        ModItems.load();
        ModEntityTypes.load();
        ModCriteria.load();
    }
}