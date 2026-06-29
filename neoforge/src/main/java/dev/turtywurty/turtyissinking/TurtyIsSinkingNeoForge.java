package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class TurtyIsSinkingNeoForge {
    public TurtyIsSinkingNeoForge(IEventBus eventBus) {
        TurtyIsSinkingCommon.init();
        eventBus.addListener(TurtyIsSinkingDatagen::onGatherClientData);
        NeoForgeRegistryHelper.load(eventBus);
    }
}