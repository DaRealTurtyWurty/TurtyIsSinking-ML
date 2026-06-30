package dev.turtywurty.turtyissinking.mixin;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.CriterionTrigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CriteriaTriggers.class)
public interface CriteriaTriggersAccessor {
    @Invoker
    static <T extends CriterionTrigger<?>> T invokeRegister(final String name, final T trigger) {
        throw new AssertionError("Untransformed @Invoker");
    }
}
