package dev.turtywurty.turtyissinking.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class TurtyIsSinkingSoundDefinitionProvider implements DataProvider {
    public static final String THUNDER_SUBTITLE = "subtitle." + Constants.MOD_ID + ".thunder";
    public static final String FART_SUBTITLE = "subtitle." + Constants.MOD_ID + ".fart";
    public static final String BABY_ZOMBIE_SIX_SEVEN_SUBTITLE = "subtitle." + Constants.MOD_ID
        + ".baby_zombie_six_seven";

    private final Path path;

    public TurtyIsSinkingSoundDefinitionProvider(PackOutput output) {
        this.path = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
            .resolve(Constants.MOD_ID).resolve("sounds.json");
    }

    private static JsonObject sound(String subtitle, String... files) {
        var definition = new JsonObject();
        definition.addProperty("subtitle", subtitle);
        var sounds = new JsonArray();
        for (String file : files) {
            sounds.add(Constants.id(file).toString());
        }
        definition.add("sounds", sounds);
        return definition;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        var sounds = new JsonObject();
        sounds.add("thunder", sound(THUNDER_SUBTITLE, "thunder"));
        sounds.add("fart", sound(FART_SUBTITLE,
            "266450__ezcah__chair-fart", "402569__teddyferguson__fart", "610067__teddyferguson__fart",
            "640803__dsisstudios__trumpet-fart", "645339__samsterbirdies__nasty-fart",
            "777448__frenkfurth__wav-fart-vegan-086", "833105__crimsonblaze__funny-fart"));
        sounds.add("baby_zombie_six_seven", sound(BABY_ZOMBIE_SIX_SEVEN_SUBTITLE, "baby_zombie_six_seven"));
        return DataProvider.saveStable(output, sounds, this.path);
    }

    @Override
    public String getName() {
        return "TurtyIsSinking sounds";
    }
}
