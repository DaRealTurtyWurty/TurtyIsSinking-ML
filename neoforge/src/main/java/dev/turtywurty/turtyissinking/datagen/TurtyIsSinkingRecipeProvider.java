package dev.turtywurty.turtyissinking.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class TurtyIsSinkingRecipeProvider extends RecipeProvider {
    public TurtyIsSinkingRecipeProvider(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    protected void buildRecipes() {
        netheriteSmithing(Items.DIAMOND, RecipeCategory.MISC, Items.NETHERITE_INGOT);
    }
}
