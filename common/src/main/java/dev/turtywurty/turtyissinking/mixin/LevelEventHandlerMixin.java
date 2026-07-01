package dev.turtywurty.turtyissinking.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.turtywurty.turtyissinking.sound.ReversedJukeboxTracker;
import dev.turtywurty.turtyissinking.sound.ReversedSoundInstance;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelEventHandler.class)
public class LevelEventHandlerMixin {
    @Shadow
    @Final
    private ClientLevel level;

    @WrapOperation(
            method = "playJukeboxSong",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;forJukeboxSong(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;"
            )
    )
    private SimpleSoundInstance turtyissinking$playJukeboxSong(SoundEvent sound, Vec3 pos, Operation<SimpleSoundInstance> original) {
        SimpleSoundInstance soundInstance = original.call(sound, pos);
        if (soundInstance == null)
            return null;

        if (!ReversedJukeboxTracker.consumeReversed(BlockPos.containing(pos)))
            return soundInstance;

        return new ReversedSoundInstance(soundInstance, this.level.getRandom());
    }
}
