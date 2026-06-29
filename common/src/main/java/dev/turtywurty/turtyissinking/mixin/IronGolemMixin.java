package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Climbable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(IronGolem.class, EntityDataSerializers.BYTE);

    protected IronGolemMixin(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void turtyissinking$defineSynchedData(SynchedEntityData.Builder entityData, CallbackInfo callbackInfo) {
        entityData.define(DATA_FLAGS_ID, (byte) 0);
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
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    @Override
    public void turtyissinking$setClimbing(boolean value) {
        byte flags = this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            flags = (byte) (flags | 1);
        } else {
            flags = (byte) (flags & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, flags);
    }
}
