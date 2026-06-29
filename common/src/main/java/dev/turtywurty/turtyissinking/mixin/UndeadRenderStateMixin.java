package dev.turtywurty.turtyissinking.mixin;

import dev.turtywurty.turtyissinking.util.Zombie67;
import net.minecraft.client.renderer.entity.state.UndeadRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(UndeadRenderState.class)
public class UndeadRenderStateMixin implements Zombie67 {
    @Unique
    private boolean turtyissinking$is67ing = false;

    @Override
    public boolean turtyissinking$is67ing() {
        return this.turtyissinking$is67ing;
    }

    @Override
    public void turtyissinking$set67ing(boolean is67ing) {
        this.turtyissinking$is67ing = is67ing;
    }
}
