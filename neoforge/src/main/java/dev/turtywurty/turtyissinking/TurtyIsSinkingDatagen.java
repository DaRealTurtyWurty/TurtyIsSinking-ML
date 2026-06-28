package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.TurtyIsSinkingSoundDefinitionProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class TurtyIsSinkingDatagen {
    private TurtyIsSinkingDatagen() {
    }

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(TurtyIsSinkingSoundDefinitionProvider::new);
    }
}
