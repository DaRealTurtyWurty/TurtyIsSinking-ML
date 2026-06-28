package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.screen.AgeVerificationScreen;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class TurtyIsSinkingEnglishLanguageProvider extends LanguageProvider {
    public TurtyIsSinkingEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(AgeVerificationScreen.TITLE, "Age Verification");
        add(AgeVerificationScreen.I_AM_OVER_18_BUTTON, "I am over 18");
        add(AgeVerificationScreen.I_AM_UNDER_18_BUTTON, "I am under 18");
        add(PlayerAgeVerification.AGE_VERIFICATION_REQUIRED_MESSAGE, "You must verify your age to perform this action.");
    }

    private void add(Component component, String translation) {
        if (component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), translation);
            return;
        }

        throw new IllegalArgumentException("Component is not translatable: " + component);
    }
}
