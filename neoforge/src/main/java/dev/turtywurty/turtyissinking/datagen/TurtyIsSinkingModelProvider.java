package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class TurtyIsSinkingModelProvider extends ModelProvider {
    public TurtyIsSinkingModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.POOP.get(), ModelTemplates.FLAT_ITEM);
    }
}
