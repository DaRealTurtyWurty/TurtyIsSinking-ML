package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.advancement.criterion.GamemodeCriterion;
import dev.turtywurty.turtyissinking.advancement.criterion.PunchBlockCriterion;
import dev.turtywurty.turtyissinking.init.ModCriteria;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TurtyIsSinkingAdvancementProvider extends AdvancementProvider {
    public static final Component PIRACY_ITS_A_CRIME_TITLE = Component.translatable("advancements." + Constants.MOD_ID + ".piracy_its_a_crime.title");
    public static final Component PIRACY_ITS_A_CRIME_DESCRIPTION = Component.translatable("advancements." + Constants.MOD_ID + ".piracy_its_a_crime.description");

    public static final Component PUNCH_OBSIDIAN_TITLE = Component.translatable("advancements." + Constants.MOD_ID + ".punched_obsidian.title");
    public static final Component PUNCH_OBSIDIAN_DESCRIPTION = Component.translatable("advancements." + Constants.MOD_ID + ".punched_obsidian.description");

    public TurtyIsSinkingAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new Subprovider()));
    }

    public static class Subprovider implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NonNull Provider provider, @NonNull Consumer<AdvancementHolder> consumer) {
            HolderGetter<Item> itemGetter = provider.lookupOrThrow(Registries.ITEM);
            HolderGetter<Block> blockGetter = provider.lookupOrThrow(Registries.BLOCK);


            Advancement.Builder.advancement()
                    .display(
                            Items.MUSIC_DISC_CHIRP,
                            PIRACY_ITS_A_CRIME_TITLE,
                            PIRACY_ITS_A_CRIME_DESCRIPTION,
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            true
                    )
                    .addCriterion("has_music_disc",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                                    .of(itemGetter, Tags.Items.MUSIC_DISCS)
                                    .build()))
                    .addCriterion("is_creative", ModCriteria.GAMEMODE.get()
                            .createCriterion(new GamemodeCriterion.Conditions(Optional.empty(), GameType.CREATIVE)))
                    .save(consumer, Constants.id("piracy_its_a_crime"));

            Advancement.Builder.advancement()
                    .display(
                            Items.CRYING_OBSIDIAN,
                            PUNCH_OBSIDIAN_TITLE,
                            PUNCH_OBSIDIAN_DESCRIPTION,
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            true
                    )
                    .addCriterion("punched_obsidian", ModCriteria.PUNCH_BLOCK.get()
                            .createCriterion(new PunchBlockCriterion.Conditions(Optional.empty(),
                                    BlockPredicate.Builder.block().of(blockGetter, Blocks.OBSIDIAN).build())))
                            .save(consumer, Constants.id("punched_obsidian"));
        }
    }
}
