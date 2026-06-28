package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class TurtyIsSinkingSoundDefinitionProvider extends SoundDefinitionsProvider {
    public TurtyIsSinkingSoundDefinitionProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.THUNDER.get(), SoundDefinition.definition()
                .subtitle("subtitle." + Constants.MOD_ID + ".thunder")
                .with(SoundDefinition.Sound.sound(
                        Constants.id("thunder"),
                        SoundDefinition.SoundType.SOUND)
                ));
    }
}
