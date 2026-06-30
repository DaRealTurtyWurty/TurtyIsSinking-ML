package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.*;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class TurtyIsSinkingDatagen {
    private TurtyIsSinkingDatagen() {
    }

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(TurtyIsSinkingSoundDefinitionProvider::new);
        event.createProvider(TurtyIsSinkingEnglishLanguageProvider::new);
        event.createProvider(TurtyIsSinkingItemTagsProvider::new);
        event.createProvider(TurtyIsSinkingModelProvider::new);
        event.createProvider(TurtyIsSinkingRecipeProvider::new);
    }
}
