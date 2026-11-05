package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.*;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

public final class TurtyIsSinkingDatagen {
    private TurtyIsSinkingDatagen() {
    }

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(TurtyIsSinkingSoundDefinitionProvider::new);
        event.createProvider(TurtyIsSinkingEnglishLanguageProvider::new);
        event.createProvider(TurtyIsSinkingItemTagsProvider::new);
        event.createProvider(TurtyIsSinkingModelProvider::new);
    }

    public static void onGatherRegistryEntries(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.ADVANCEMENT, new TurtyIsSinkingAdvancementProvider());
        event.gatherFor("minecraft").recipe(TurtyIsSinkingRecipeProvider::new);
    }
}
