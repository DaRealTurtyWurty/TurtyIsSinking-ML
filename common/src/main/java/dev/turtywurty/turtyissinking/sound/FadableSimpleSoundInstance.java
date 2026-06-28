package dev.turtywurty.turtyissinking.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class FadableSimpleSoundInstance extends AbstractTickableSoundInstance {
    private static final int FADE_TICKS = 40;

    private final float targetVolume;
    private int fadeDirection;
    private int fade;

    public FadableSimpleSoundInstance(SoundEvent sound, SoundSource source, float volume, float pitch, RandomSource random, BlockPos pos) {
        this(sound, source, volume, pitch, random, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
    }

    public FadableSimpleSoundInstance(SoundEvent sound, SoundSource source, float volume, float pitch, RandomSource random, double x, double y, double z) {
        super(sound, source, random);
        this.targetVolume = volume;
        this.volume = 0.0F;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.looping = true;
        this.delay = 0;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.relative = true;
    }

    @Override
    public void tick() {
        if (this.fadeDirection < 0 && this.fade <= 0) {
            this.volume = 0.0F;
            stop();
            return;
        }

        this.fade = Math.clamp(this.fade + this.fadeDirection, 0, FADE_TICKS);
        updateVolume();
    }

    public void fadeOut() {
        this.fade = Math.min(this.fade, FADE_TICKS);
        this.fadeDirection = -1;
    }

    public void fadeIn() {
        this.fade = Math.max(0, this.fade);
        this.fadeDirection = 1;

        if (this.fade == 0) {
            this.fade = 1;
            updateVolume();
        }
    }

    public boolean isFadingOut() {
        return this.fadeDirection < 0;
    }

    private void updateVolume() {
        this.volume = this.targetVolume * Mth.clamp((float) this.fade / FADE_TICKS, 0.0F, 1.0F);
    }
}
