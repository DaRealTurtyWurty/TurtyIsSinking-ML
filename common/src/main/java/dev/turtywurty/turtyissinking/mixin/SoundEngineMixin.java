package dev.turtywurty.turtyissinking.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.turtywurty.turtyissinking.sound.ReversedAudioStream;
import dev.turtywurty.turtyissinking.sound.ReversedSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.FiniteAudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @WrapOperation(
            method = "play",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/SoundBufferLibrary;getStream(Lnet/minecraft/resources/Identifier;Z)Ljava/util/concurrent/CompletableFuture;"
            )
    )
    private CompletableFuture<AudioStream> turtyissinking$reverseStreamedSound(SoundBufferLibrary soundBuffers,
                                                                               Identifier location,
                                                                               boolean looping,
                                                                               Operation<CompletableFuture<AudioStream>> original,
                                                                               SoundInstance instance) {
        CompletableFuture<AudioStream> streamFuture = original.call(soundBuffers, location, looping);
        if (!(instance instanceof ReversedSoundInstance))
            return streamFuture;

        return streamFuture.thenApply(stream -> {
            if (!(stream instanceof FiniteAudioStream finiteAudioStream))
                return stream;

            try {
                return new ReversedAudioStream(finiteAudioStream);
            } catch (IOException exception) {
                throw new CompletionException(exception);
            }
        });
    }
}
