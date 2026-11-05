package dev.turtywurty.turtyissinking.datagen;

import com.google.gson.JsonObject;
import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtymultiloader.datagen.model.ModelGenerationContext;

public final class TurtyIsSinkingModelProvider {
    private TurtyIsSinkingModelProvider() {
    }

    public static void generate(ModelGenerationContext models) {
        flatItem(models, "poop");
        itemDefinition(models, "villager_nose");
        flatItem(models, "phone");
    }

    private static void flatItem(ModelGenerationContext models, String name) {
        var model = new JsonObject();
        model.addProperty("parent", "minecraft:item/generated");
        var textures = new JsonObject();
        textures.addProperty("layer0", Constants.id("item/" + name).toString());
        model.add("textures", textures);
        models.itemModel(Constants.id(name), model);
        itemDefinition(models, name);
    }

    private static void itemDefinition(ModelGenerationContext models, String name) {
        var model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", Constants.id("item/" + name).toString());
        var definition = new JsonObject();
        definition.add("model", model);
        models.itemDefinition(Constants.id(name), definition);
    }
}
