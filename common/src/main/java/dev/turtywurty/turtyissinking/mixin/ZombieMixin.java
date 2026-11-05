package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.entity.Zombie67Goal;
import dev.turtywurty.turtyissinking.init.ModAttachments;
import dev.turtywurty.turtyissinking.util.Zombie67;
import dev.turtywurty.turtymultiloader.attachment.AttachmentTarget;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster implements Zombie67 {
    protected ZombieMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean turtyissinking$is67ing() {
        return AttachmentTarget.entity(this).get(ModAttachments.ZOMBIE_67ING).orElse(false);
    }

    @Override
    public void turtyissinking$set67ing(boolean is67ing) {
        if (turtyissinking$is67ing() != is67ing) {
            AttachmentTarget.entity(this).set(ModAttachments.ZOMBIE_67ING, is67ing);
        }
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
