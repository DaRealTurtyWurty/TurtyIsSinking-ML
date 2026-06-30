package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Climbable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends AbstractGolem implements Climbable {
    @Unique
    private boolean turtyissinking$isClimbing;

    protected IronGolemMixin(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void turtyissinking$aiStep(CallbackInfo callbackInfo) {
        if (!level().isClientSide()) {
            turtyissinking$setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public boolean turtyissinking$isClimbing() {
        return this.turtyissinking$isClimbing;
    }

    @Override
    public void turtyissinking$setClimbing(boolean value) {
        this.turtyissinking$isClimbing = value;
    }
}
