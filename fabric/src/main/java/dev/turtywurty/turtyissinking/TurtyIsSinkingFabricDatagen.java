package dev.turtywurty.turtyissinking;

import dev.turtywurty.turtyissinking.datagen.ModDataGeneration;
import dev.turtywurty.turtymultiloader.datagen.DataGeneration;
import dev.turtywurty.turtymultiloader.fabric.datagen.FabricDataGeneration;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public final class TurtyIsSinkingFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGeneration.run(generator, ModDataGeneration.SPEC);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        DataGeneration.addWorldRegistryBootstraps(ModDataGeneration.SPEC, builder);
    }

    @Override
    public void buildReloadableRegistry(RegistrySetBuilder builder) {
        DataGeneration.addReloadableRegistryBootstraps(ModDataGeneration.SPEC, builder);
    }
}
