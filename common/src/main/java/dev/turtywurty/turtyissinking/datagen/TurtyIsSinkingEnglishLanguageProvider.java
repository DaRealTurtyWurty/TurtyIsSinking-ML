package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.init.ModEntityTypes;
import dev.turtywurty.turtyissinking.init.ModItems;
import dev.turtywurty.turtyissinking.init.ModMobEffects;
import dev.turtywurty.turtyissinking.screen.AgeVerificationScreen;
import dev.turtywurty.turtyissinking.screen.PhoneCallScreen;
import dev.turtywurty.turtyissinking.util.AllergyUtils;
import dev.turtywurty.turtyissinking.util.PlayerAgeVerification;
import dev.turtywurty.turtymultiloader.datagen.language.LanguageGenerationContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

public final class TurtyIsSinkingEnglishLanguageProvider {
    private TurtyIsSinkingEnglishLanguageProvider() {
    }

    public static void generate(LanguageGenerationContext translations) {
        translations.add(key(AgeVerificationScreen.TITLE), "Age Verification");
        translations.add(key(AgeVerificationScreen.I_AM_OVER_18_BUTTON), "I am over 18");
        translations.add(key(AgeVerificationScreen.I_AM_UNDER_18_BUTTON), "I am under 18");
        translations.add(key(PlayerAgeVerification.AGE_VERIFICATION_REQUIRED_MESSAGE), "You must verify your age to perform this action.");
        translations.add(ModMobEffects.LACTOSE_INTOLERANCE.get().getDescriptionId(), "Lactose Intolerance");
        translations.add(ModItems.POOP.get(), "Poop");
        translations.add(ModEntityTypes.POOP_PROJECTILE.get(), "Poop Projectile");
        translations.add(ModMobEffects.FLATULENCE.get().getDescriptionId(), "Flatulence");
        translations.add(TurtyIsSinkingSoundDefinitionProvider.THUNDER_SUBTITLE, "Thunder (by Imagine Dragons)");
        translations.add(TurtyIsSinkingSoundDefinitionProvider.FART_SUBTITLE, "Fart");
        translations.add(TurtyIsSinkingSoundDefinitionProvider.BABY_ZOMBIE_SIX_SEVEN_SUBTITLE, "Baby Zombie 67");
        translations.add(key(TurtyIsSinkingAdvancementProvider.PIRACY_ITS_A_CRIME_TITLE), "Piracy, It's a Crime");
        translations.add(key(TurtyIsSinkingAdvancementProvider.PIRACY_ITS_A_CRIME_DESCRIPTION), "Obtain a music disc in Creative Mode");
        translations.add(key(TurtyIsSinkingAdvancementProvider.PUNCH_OBSIDIAN_TITLE), "Are you happy now you sick fuck?");
        translations.add(key(TurtyIsSinkingAdvancementProvider.PUNCH_OBSIDIAN_DESCRIPTION), "Punch obsidian, therefore turning it into crying obsidian");
        translations.add(ModItems.VILLAGER_NOSE.get(), "Villager's Nose");
        translations.add(key(AllergyUtils.ALLERGY_MESSAGE), "You are having an allergic reaction!");
        translations.add("message.turtyissinking.no_littering", "No littering!");

        translations.add(key(PhoneCallScreen.TITLE), "Phone Call");
        translations.add(key(PhoneCallScreen.IS_CALLING_TEXT), "is calling...");
    }

    private static String key(Component component) {
        if (component.getContents() instanceof TranslatableContents translatableContents) {
            return translatableContents.getKey();
        }

        throw new IllegalArgumentException("Component is not translatable: " + component);
    }
}
