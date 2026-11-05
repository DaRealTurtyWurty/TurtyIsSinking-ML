package dev.turtywurty.turtyissinking.datagen;

import dev.turtywurty.turtyissinking.Constants;
import dev.turtywurty.turtyissinking.advancement.criterion.GamemodeCriterion;
import dev.turtywurty.turtyissinking.advancement.criterion.PunchBlockCriterion;
import dev.turtywurty.turtyissinking.init.ModCriteria;
import dev.turtywurty.turtymultiloader.datagen.convention.ConventionTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;

public class TurtyIsSinkingAdvancementProvider extends AdvancementProvider {
    public static final Component PIRACY_ITS_A_CRIME_TITLE = Component.translatable("advancements." + Constants.MOD_ID + ".piracy_its_a_crime.title");
    public static final Component PIRACY_ITS_A_CRIME_DESCRIPTION = Component.translatable("advancements." + Constants.MOD_ID + ".piracy_its_a_crime.description");

    public static final Component PUNCH_OBSIDIAN_TITLE = Component.translatable("advancements." + Constants.MOD_ID + ".punched_obsidian.title");
    public static final Component PUNCH_OBSIDIAN_DESCRIPTION = Component.translatable("advancements." + Constants.MOD_ID + ".punched_obsidian.description");

    public TurtyIsSinkingAdvancementProvider() {
        super(List.of(Subprovider::new));
    }

    public static class Subprovider extends AdvancementSubProvider {
        public Subprovider(BootstrapContext<Advancement> output) {
            super(output);
        }

        @Override
        public void generate() {
            HolderGetter<Item> itemGetter = this.output.lookup(Registries.ITEM);
            HolderGetter<Block> blockGetter = this.output.lookup(Registries.BLOCK);

            Advancement.Builder.advancement()
                    .rootDisplay(
                            Items.MUSIC_DISC_CHIRP,
                            PIRACY_ITS_A_CRIME_TITLE,
                            PIRACY_ITS_A_CRIME_DESCRIPTION,
                            Identifier.withDefaultNamespace("gui/advancements/backgrounds/stone"),
                            AdvancementType.TASK,
                            true,
                            true,
                            true
                    )
                    .addCriterion("has_music_disc",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                                    .of(itemGetter, ConventionTags.item("music_discs").key())
                                    .build()))
                    .addCriterion("is_creative", ModCriteria.GAMEMODE.get()
                            .createCriterion(new GamemodeCriterion.Conditions(Optional.empty(), GameType.CREATIVE)))
                    .save(this.output, Constants.id("piracy_its_a_crime").toString());

            Advancement.Builder.advancement()
                    .rootDisplay(
                            Items.CRYING_OBSIDIAN,
                            PUNCH_OBSIDIAN_TITLE,
                            PUNCH_OBSIDIAN_DESCRIPTION,
                            Identifier.withDefaultNamespace("gui/advancements/backgrounds/stone"),
                            AdvancementType.TASK,
                            true,
                            true,
                            true
                    )
                    .addCriterion("punched_obsidian", ModCriteria.PUNCH_BLOCK.get()
                            .createCriterion(new PunchBlockCriterion.Conditions(Optional.empty(),
                                    BlockPredicate.Builder.block().of(blockGetter, Blocks.OBSIDIAN).build())))
                    .save(this.output, Constants.id("punched_obsidian").toString());
        }
    }
}
