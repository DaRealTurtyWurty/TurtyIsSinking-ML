package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.init.ModMobEffects;
import dev.turtywurty.turtyissinking.init.ModEntityTypes;
import dev.turtywurty.turtyissinking.init.ModItems;
import dev.turtywurty.turtyissinking.init.ModSounds;

public class TurtyIsSinkingCommon {
    public static void init() {
        ModSounds.load();
        ModMobEffects.load();
        ModItems.load();
        ModEntityTypes.load();
    }
}