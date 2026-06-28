package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerMixin implements PlayerAgeVerification {
    @Unique
    private boolean turtyissinking$ageVerified = false;
    @Unique
    private long turtyissinking$lastAgeVerification = 0;

    @Override
    public long turtyissinking$lastAgeVerification() {
        return this.turtyissinking$lastAgeVerification;
    }

    @Override
    public boolean turtyissinking$isAgeVerified() {
        return this.turtyissinking$ageVerified;
    }

    @Override
    public void turtyissinking$setAgeVerified(boolean ageVerified) {
        this.turtyissinking$ageVerified = ageVerified;
        this.turtyissinking$lastAgeVerification = System.currentTimeMillis();
    }

    @Override
    public boolean turtyissinking$isOnCooldown() {
        return System.currentTimeMillis() - this.turtyissinking$lastAgeVerification < 1000 * 60 * 5; // 5 minutes
    }
}
