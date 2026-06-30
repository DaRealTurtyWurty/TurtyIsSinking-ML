package dev.turtywurty.turtyissinking.init;

import dev.turtywurty.turtyissinking.advancement.criterion.GamemodeCriterion;
import dev.turtywurty.turtyissinking.advancement.criterion.PunchBlockCriterion;
import dev.turtywurty.turtyissinking.platform.Services;
import dev.turtywurty.turtyissinking.platform.services.util.RegistryHandle;

public final class ModCriteria {
    public static final RegistryHandle<GamemodeCriterion> GAMEMODE = Services.REGISTRY.registerCriteriaTrigger("gamemode",
            GamemodeCriterion::new);

    public static final RegistryHandle<PunchBlockCriterion> PUNCH_BLOCK = Services.REGISTRY.registerCriteriaTrigger("punch_block",
            PunchBlockCriterion::new);

    public static void load() {
    }
}
