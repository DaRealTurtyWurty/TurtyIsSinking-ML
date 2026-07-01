package dev.turtywurty.turtyissinking.sound;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class ReversedSoundInstance extends SimpleSoundInstance {
    public ReversedSoundInstance(@NonNull SoundInstance delegate, @NonNull RandomSource randomSource) {
        super(delegate.getIdentifier(), delegate.getSource(), 4.0F, 1.0F, randomSource, delegate.isLooping(), delegate.getDelay(), delegate.getAttenuation(), delegate.getX(), delegate.getY(), delegate.getZ(), delegate.isRelative());
    }
}
