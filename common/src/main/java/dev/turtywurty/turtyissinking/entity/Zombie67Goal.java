package dev.turtywurty.turtyissinking.entity;

import dev.turtywurty.turtyissinking.util.Zombie67;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.phys.Vec3;

public class Zombie67Goal extends ZombieAttackGoal {
    private static final double STARE_DISTANCE_SQR = 9.0D;
    private static final int STARE_TICKS = 100;
    private static final int APPROACH_REPATH_TICKS = 10;
    private static final int FLEE_REPATH_TICKS = 6;
    private static final double FLEE_DONE_DISTANCE_SQR = 30.0D * 30.0D;
    private static final double FLEE_SPEED_MULTIPLIER = 1.7D;

    private final double speedModifier;
    private Phase phase = Phase.APPROACHING;
    private int stareTicks;
    private int repathTicks;

    public Zombie67Goal(Zombie zombie, double speedModifier, boolean trackTarget) {
        super(zombie, speedModifier, trackTarget);
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.mob.isBaby())
            return super.canContinueToUse();

        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void start() {
        super.start();
        this.phase = Phase.APPROACHING;
        this.stareTicks = 0;
        this.repathTicks = 0;
        if (this.mob.isBaby()) {
            this.mob.setAggressive(false);
            set67ing(false);
        }
    }

    @Override
    public void stop() {
        super.stop();
        if (this.mob.isBaby()) {
            set67ing(false);
        }

        this.phase = Phase.APPROACHING;
        this.stareTicks = 0;
        this.repathTicks = 0;
    }

    @Override
    public void tick() {
        if (!this.mob.isBaby()) {
            super.tick();
            return;
        }

        LivingEntity target = this.mob.getTarget();
        if (target == null)
            return;

        this.mob.setAggressive(false);
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (this.phase == Phase.APPROACHING) {
            tickApproach(target);
        } else if (this.phase == Phase.STARING) {
            tickStare();
        } else {
            tickFlee(target);
        }
    }

    private void tickApproach(LivingEntity target) {
        if (this.mob.distanceToSqr(target) <= STARE_DISTANCE_SQR) {
            this.mob.getNavigation().stop();
            this.phase = Phase.STARING;
            this.stareTicks = STARE_TICKS;
            return;
        }

        if (--this.repathTicks <= 0) {
            this.repathTicks = adjustedTickDelay(APPROACH_REPATH_TICKS);
            this.mob.getNavigation().moveTo(target, this.speedModifier);
        }
    }

    private void tickStare() {
        set67ing(true);

        this.mob.getNavigation().stop();
        if (--this.stareTicks <= 0) {
            this.phase = Phase.FLEEING;
            this.repathTicks = 0;
            set67ing(false);
        }
    }

    private void tickFlee(LivingEntity target) {
        if (this.mob.distanceToSqr(target) >= FLEE_DONE_DISTANCE_SQR) {
            this.mob.getNavigation().stop();
            return;
        }

        if (--this.repathTicks > 0 && !this.mob.getNavigation().isDone())
            return;

        set67ing(false);
        this.repathTicks = adjustedTickDelay(FLEE_REPATH_TICKS);
        Vec3 fleePos = DefaultRandomPos.getPosAway(this.mob, 24, 7, target.position());
        if (fleePos == null) {
            fleePos = getFallbackFleePos(target);
        }

        this.mob.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, this.speedModifier * FLEE_SPEED_MULTIPLIER);
    }

    private Vec3 getFallbackFleePos(LivingEntity target) {
        Vec3 away = this.mob.position().subtract(target.position());
        if (away.lengthSqr() < 1.0E-7D) {
            away = Vec3.directionFromRotation(0.0F, this.mob.getRandom().nextFloat() * 360.0F);
        }

        return this.mob.position().add(away.normalize().scale(24.0D));
    }

    private void set67ing(boolean is67ing) {
        if (this.mob instanceof Zombie67 zombie67) {
            zombie67.turtyissinking$set67ing(is67ing);
        }
    }

    private enum Phase {
        APPROACHING,
        STARING,
        FLEEING
    }
}
