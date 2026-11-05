package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.advancement.criterion.GamemodeCriterion;
import dev.turtywurty.turtyissinking.advancement.criterion.PunchBlockCriterion;
import dev.turtywurty.turtymultiloader.registration.RegistrationHandle;
import dev.turtywurty.turtymultiloader.registration.RegistryService;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public final class ModCriteria {
    public static final RegistrationHandle<CriterionTrigger<?>, GamemodeCriterion> GAMEMODE = RegistryService.get().register(Registries.TRIGGER_TYPE, Constants.id("gamemode"),
            GamemodeCriterion::new);

    public static final RegistrationHandle<CriterionTrigger<?>, PunchBlockCriterion> PUNCH_BLOCK = RegistryService.get().register(Registries.TRIGGER_TYPE, Constants.id("punch_block"),
            PunchBlockCriterion::new);

    public static void load() {
    }
}
