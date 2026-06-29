package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class TurtyIsSinkingSoundDefinitionProvider extends SoundDefinitionsProvider {
    public static final String THUNDER_SUBTITLE = "subtitle." + Constants.MOD_ID + ".thunder";
    public static final String FART_SUBTITLE = "subtitle." + Constants.MOD_ID + ".fart";

    public TurtyIsSinkingSoundDefinitionProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.THUNDER.get(), SoundDefinition.definition()
                .subtitle(THUNDER_SUBTITLE)
                .with(SoundDefinition.Sound.sound(
                        Constants.id("thunder"),
                        SoundDefinition.SoundType.SOUND)
                ));

        add(ModSounds.FART.get(), SoundDefinition.definition()
                .subtitle(FART_SUBTITLE)
                .with(
                        SoundDefinition.Sound.sound(
                                Constants.id("266450__ezcah__chair-fart"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("402569__teddyferguson__fart"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("610067__teddyferguson__fart"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("640803__dsisstudios__trumpet-fart"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("645339__samsterbirdies__nasty-fart"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("777448__frenkfurth__wav-fart-vegan-086"),
                                SoundDefinition.SoundType.SOUND),
                        SoundDefinition.Sound.sound(
                                Constants.id("833105__crimsonblaze__funny-fart"),
                                SoundDefinition.SoundType.SOUND)));
    }
}
