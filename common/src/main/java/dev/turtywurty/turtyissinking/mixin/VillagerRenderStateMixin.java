package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.SnippableVillager;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(VillagerRenderState.class)
public class VillagerRenderStateMixin implements SnippableVillager {
    @Unique
    private boolean turtyissinking$hasSnippedNose = false;

    @Override
    public boolean turtyissinking$hasSnippedNose() {
        return this.turtyissinking$hasSnippedNose;
    }

    @Override
    public void turtyissinking$setNoseSnipped(boolean value) {
        this.turtyissinking$hasSnippedNose = value;
    }
}
