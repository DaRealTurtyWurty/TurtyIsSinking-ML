package dev.turtywurty.turtyissinking.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Unique
    private static final Component TURTYISSINKING$NO_LITTERING_MESSAGE = Component.translatable("message.turtyissinking.no_littering");

    @Unique
    private static final int TURTYISSINKING$RISE_TICKS = 24;
    @Unique
    private static final int TURTYISSINKING$AIM_TICKS = 18;
    @Unique
    private static final int TURTYISSINKING$TOTAL_CHARGE_TICKS = TURTYISSINKING$RISE_TICKS + TURTYISSINKING$AIM_TICKS;
    @Unique
    private static final int TURTYISSINKING$ATTACK_COOLDOWN_TICKS = 100;
    @Unique
    private static final double TURTYISSINKING$TARGET_RANGE = 14.0D;
    @Unique
    private static final double TURTYISSINKING$RISE_SPEED = 0.16D;
    @Unique
    private static final double TURTYISSINKING$SHOT_SPEED = 2.2D;
    @Unique
    private static final float TURTYISSINKING$AIM_ROTATION_STEP = 24.0F;

    @Unique
    private int turtyissinking$tridentAttackTicks;
    @Unique
    private int turtyissinking$tridentAttackCooldown;
    @Unique
    private ThrownTrident turtyissinking$attackTrident;

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void turtyissinking$tick(CallbackInfo ci) {
        if (level().isClientSide())
            return;

        Player player = level().getNearestPlayer(this, TURTYISSINKING$TARGET_RANGE);
        if (!turtyissinking$isValidTarget(player)) {
            if (turtyissinking$attackTrident != null) {
                turtyissinking$tridentAttackCooldown = TURTYISSINKING$ATTACK_COOLDOWN_TICKS;
            }

            turtyissinking$resetTridentAttack(true);
            return;
        }

        if (turtyissinking$tridentAttackCooldown > 0) {
            turtyissinking$tridentAttackCooldown--;
            return;
        }

        if (turtyissinking$attackTrident == null) {
            if (!isInWater())
                return;

            turtyissinking$attackTrident = new ThrownTrident(level(), getX(), getY(), getZ(), Items.TRIDENT.getDefaultInstance());
            turtyissinking$attackTrident.setNoPhysics(true);
            turtyissinking$attackTrident.setNoGravity(true);
            turtyissinking$attackTrident.setDeltaMovement(Vec3.ZERO);
            turtyissinking$attackTrident.playSound(SoundEvents.TRIDENT_RETURN, 2.0F, 1.2F);
            level().addFreshEntity(turtyissinking$attackTrident);
        }

        if (turtyissinking$attackTrident.isRemoved()) {
            turtyissinking$resetTridentAttack(false);
            return;
        }

        Vec3 direction = player.getEyePosition().subtract(turtyissinking$attackTrident.position());
        if (direction.lengthSqr() < 1.0E-7D)
            return;

        turtyissinking$rotateTridentTowards(direction);

        if (turtyissinking$tridentAttackTicks < TURTYISSINKING$RISE_TICKS) {
            turtyissinking$attackTrident.setDeltaMovement(0.0D, TURTYISSINKING$RISE_SPEED, 0.0D);
        } else if (turtyissinking$tridentAttackTicks < TURTYISSINKING$TOTAL_CHARGE_TICKS) {
            turtyissinking$attackTrident.setDeltaMovement(Vec3.ZERO);
        } else {
            turtyissinking$shootTridentAt(direction.normalize());
            player.sendSystemMessage(TURTYISSINKING$NO_LITTERING_MESSAGE);
            turtyissinking$tridentAttackCooldown = TURTYISSINKING$ATTACK_COOLDOWN_TICKS;
            turtyissinking$resetTridentAttack(false);
            return;
        }

        turtyissinking$tridentAttackTicks++;
    }

    @Unique
    private boolean turtyissinking$isValidTarget(Player player) {
        return player != null && player.isAlive() && !player.isRemoved() && !player.isSpectator();
    }

    @Unique
    private void turtyissinking$resetTridentAttack(boolean discardAttackTrident) {
        if (discardAttackTrident && turtyissinking$attackTrident != null && !turtyissinking$attackTrident.isRemoved()) {
            turtyissinking$attackTrident.discard();
        }

        turtyissinking$tridentAttackTicks = 0;
        turtyissinking$attackTrident = null;
    }

    @Unique
    private void turtyissinking$rotateTridentTowards(Vec3 direction) {
        double horizontalDistance = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        float targetYaw = (float) (Mth.atan2(direction.z, direction.x) * Mth.RAD_TO_DEG) - 90.0F;
        float targetPitch = (float) -(Mth.atan2(direction.y, horizontalDistance) * Mth.RAD_TO_DEG);

        turtyissinking$attackTrident.setYRot(turtyissinking$approachRotation(turtyissinking$attackTrident.getYRot(), targetYaw));
        turtyissinking$attackTrident.setXRot(turtyissinking$approachRotation(turtyissinking$attackTrident.getXRot(), targetPitch));
    }

    @Unique
    private float turtyissinking$approachRotation(float current, float target) {
        float difference = Mth.wrapDegrees(target - current);
        difference = Mth.clamp(difference, -TURTYISSINKING$AIM_ROTATION_STEP, TURTYISSINKING$AIM_ROTATION_STEP);
        return current + difference;
    }

    @Unique
    private void turtyissinking$shootTridentAt(Vec3 direction) {
        turtyissinking$attackTrident.setOwner(null);
        turtyissinking$attackTrident.setNoPhysics(false);
        turtyissinking$attackTrident.setNoGravity(false);
        turtyissinking$attackTrident.setDeltaMovement(direction.scale(TURTYISSINKING$SHOT_SPEED));
        turtyissinking$attackTrident.playSound(SoundEvents.TRIDENT_THROW.value(), 1.0F, 1.0F);
    }
}
