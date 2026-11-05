package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.ModDataGeneration;
import dev.turtywurty.turtymultiloader.neoforge.datagen.NeoForgeDataGeneration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

@Mod(Constants.MOD_ID)
public class TurtyIsSinkingNeoForge {
    public TurtyIsSinkingNeoForge(IEventBus eventBus) {
        TurtyIsSinkingCommon.init();
        eventBus.addListener(GatherDataEvent.Client.class,
            event -> NeoForgeDataGeneration.run(event, ModDataGeneration.SPEC));
        eventBus.addListener(GatherDataEvent.Server.class,
            event -> NeoForgeDataGeneration.run(event, ModDataGeneration.SPEC));
        eventBus.addListener(GatherDataRegistryEntriesEvent.class,
            event -> NeoForgeDataGeneration.run(event, ModDataGeneration.SPEC));
    }
}
