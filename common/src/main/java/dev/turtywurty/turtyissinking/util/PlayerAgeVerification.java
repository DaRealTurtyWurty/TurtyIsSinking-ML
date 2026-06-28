package dev.turtywurty.turtyissinking.util;

import net.minecraft.network.chat.Component;

public interface PlayerAgeVerification {
    Component AGE_VERIFICATION_REQUIRED_MESSAGE = Component.translatable("message.turtyissinking.age_verification.required");

    long turtyissinking$lastAgeVerification();

    boolean turtyissinking$isAgeVerified();

    void turtyissinking$setAgeVerified(boolean ageVerified);

    boolean turtyissinking$isOnCooldown();
}
