package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.NeoForgeRegistryHelper;
import dev.turtywurty.turtyissinking.init.NeoForgeAttachments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class TurtyIsSinkingNeoForge {
    public TurtyIsSinkingNeoForge(IEventBus eventBus) {
        TurtyIsSinkingCommon.init();
        eventBus.addListener(TurtyIsSinkingDatagen::onGatherClientData);
        eventBus.addListener(TurtyIsSinkingDatagen::onGatherRegistryEntries);
        NeoForgeRegistryHelper.load(eventBus);
        NeoForgeAttachments.load(eventBus);
    }
}
