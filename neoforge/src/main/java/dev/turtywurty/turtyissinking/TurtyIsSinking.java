package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class TurtyIsSinking {
    public TurtyIsSinking(IEventBus eventBus) {
        CommonClass.init();
        eventBus.addListener(TurtyIsSinkingDatagen::onGatherClientData);
        NeoForgeRegistryHelper.load(eventBus);
    }
}