package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.SnippableVillager;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class FabricVillagerMixin implements SnippableVillager {
    @Unique
    private static final EntityDataAccessor<Boolean> turtyissinking$HAS_NOSE_SNIPPED = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.BOOLEAN);

    @Override
    public boolean turtyissinking$hasSnippedNose() {
        return ((Villager) (Object) this).getEntityData().get(turtyissinking$HAS_NOSE_SNIPPED);
    }

    @Override
    public void turtyissinking$setNoseSnipped(boolean is67ing) {
        ((Villager) (Object) this).getEntityData().set(turtyissinking$HAS_NOSE_SNIPPED, is67ing);
    }

    @Inject(
            method = "defineSynchedData",
            at = @At("TAIL")
    )
    private void turtyissinking$defineSynchedData(SynchedEntityData.Builder entityData, CallbackInfo callbackInfo) {
        entityData.define(turtyissinking$HAS_NOSE_SNIPPED, false);
    }
}
