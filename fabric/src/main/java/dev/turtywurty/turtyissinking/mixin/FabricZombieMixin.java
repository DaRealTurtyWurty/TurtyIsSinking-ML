package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Zombie67Data;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class FabricZombieMixin implements Zombie67Data {
    @Unique
    private static final EntityDataAccessor<Boolean> turtyissinking$IS_67ING = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean turtyissinking$getSynced67ing() {
        return ((Zombie) (Object) this).getEntityData().get(turtyissinking$IS_67ING);
    }

    @Override
    public void turtyissinking$setSynced67ing(boolean is67ing) {
        ((Zombie) (Object) this).getEntityData().set(turtyissinking$IS_67ING, is67ing);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void turtyissinking$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo callbackInfo) {
        builder.define(turtyissinking$IS_67ING, false);
    }
}
