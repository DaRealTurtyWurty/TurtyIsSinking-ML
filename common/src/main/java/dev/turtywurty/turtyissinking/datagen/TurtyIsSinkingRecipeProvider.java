package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class TurtyIsSinkingRecipeProvider extends RecipeProvider {
    public TurtyIsSinkingRecipeProvider(
        BootstrapContext<Recipe<?>> recipes,
        BootstrapContext<Advancement> advancements
    ) {
        super(recipes, advancements);
    }

    @Override
    public void buildRecipes() {
        SmithingTransformRecipeBuilder.smithing(
            Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND),
            tag(ItemTags.NETHERITE_TOOL_MATERIALS), RecipeCategory.MISC, Items.NETHERITE_INGOT)
            .unlocks("has_netherite_ingot", has(ItemTags.NETHERITE_TOOL_MATERIALS))
            .save(this.output, ResourceKey.create(Registries.RECIPE, Constants.id("netherite_ingot_smithing")));
    }
}
