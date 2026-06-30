package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.entity.Zombie67Goal;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.util.Zombie67;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster implements Zombie67 {
    @Unique
    private boolean turtyissinking$is67ing;

    protected ZombieMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean turtyissinking$is67ing() {
        return Services.PLATFORM.getZombie67ing((Zombie) (Object) this, this.turtyissinking$is67ing);
    }

    @Override
    public void turtyissinking$set67ing(boolean is67ing) {
        this.turtyissinking$is67ing = Services.PLATFORM.setZombie67ing((Zombie) (Object) this, is67ing);
    }

    @Inject(
            method = "addBehaviourGoals",
            at = @At("TAIL")
    )
    private void turtyissinking$addBehaviourGoals(CallbackInfo callbackInfo) {
        this.goalSelector.removeAllGoals(ZombieAttackGoal.class::isInstance);
        this.goalSelector.addGoal(3, new Zombie67Goal((Zombie) (Object) this, 1.0, false));
    }
}
