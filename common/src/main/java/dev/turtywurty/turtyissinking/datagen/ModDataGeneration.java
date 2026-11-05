package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModTags;
import dev.turtywurty.turtymultiloader.datagen.DataGeneration;
import dev.turtywurty.turtymultiloader.datagen.DataGenerationSide;
import dev.turtywurty.turtymultiloader.datagen.DataGenerationSpec;
import dev.turtywurty.turtymultiloader.datagen.convention.ConventionTags;

public final class ModDataGeneration {
    public static final DataGenerationSpec SPEC = DataGeneration.spec(Constants.MOD_ID)
            .recipes((recipes, advancements) -> {
                new TurtyIsSinkingRecipeProvider(recipes, advancements).buildRecipes();
                new TurtyIsSinkingAdvancementProvider().run(advancements);
            })
            .itemTags((_, tags) -> tags.tag(ModTags.Items.CONTAINS_LACTOSE)
                    .addOptionalTag(ConventionTags.item("drinks/milk").key()))
            .language("en_us", TurtyIsSinkingEnglishLanguageProvider::generate)
            .models(TurtyIsSinkingModelProvider::generate)
            .provider(DataGenerationSide.CLIENT,
                    context -> new TurtyIsSinkingSoundDefinitionProvider(context.output()))
            .build();

    private ModDataGeneration() {
    }
}
