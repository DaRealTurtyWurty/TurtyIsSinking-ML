package dev.turtywurty.turtyissinking.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class TurtyIsSinkingRecipeProvider extends RecipeProvider.Runner {
    public TurtyIsSinkingRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
        return new Provider(provider, recipeOutput);
    }

    @Override
    public @NonNull String getName() {
        return "Turty Is Sinking Recipes";
    }

    public static class Provider extends RecipeProvider {
        public Provider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
        }

        @Override
        protected void buildRecipes() {
            netheriteSmithing(Items.DIAMOND, RecipeCategory.MISC, Items.NETHERITE_INGOT);
        }
    }
}
