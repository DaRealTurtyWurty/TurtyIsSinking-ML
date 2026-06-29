package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.TurtyIsSinkingEnglishLanguageProvider;
import dev.turtywurty.turtyissinking.datagen.TurtyIsSinkingItemTagsProvider;
import dev.turtywurty.turtyissinking.datagen.TurtyIsSinkingModelProvider;
import dev.turtywurty.turtyissinking.datagen.TurtyIsSinkingSoundDefinitionProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class TurtyIsSinkingDatagen {
    private TurtyIsSinkingDatagen() {
    }

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(TurtyIsSinkingSoundDefinitionProvider::new);
        event.createProvider(TurtyIsSinkingEnglishLanguageProvider::new);
        event.createProvider(TurtyIsSinkingItemTagsProvider::new);
        event.createProvider(TurtyIsSinkingModelProvider::new);
    }
}
