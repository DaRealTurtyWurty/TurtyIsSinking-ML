package dev.turtywurty.turtyissinking.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class AllergyUtils {
    public static final Component ALLERGY_MESSAGE = Component.translatable(Constants.MOD_ID + ".allergy.message");

    public static final Map<Holder<MobEffect>, Float> ALLERGIC_REACTIONS = Map.of(
            MobEffects.NAUSEA, 0.95F,
            MobEffects.WEAKNESS, 0.85F,
            MobEffects.MINING_FATIGUE, 0.75F,
            MobEffects.HUNGER, 0.65F,
            MobEffects.WITHER, 0.20F,
            MobEffects.INSTANT_DAMAGE, 0.01F
    );
    public static final Codec<Identifier> IDENTIFIER_CODEC = Codec.STRING.comapFlatMap(
            value -> {
                Identifier id = Identifier.tryParse(value);
                return id != null ? DataResult.success(id) : DataResult.error(() -> "Invalid identifier: " + value);
            },
            Identifier::toString);

    private AllergyUtils() {
    }

    public static @NonNull Map<Identifier, Boolean> getDefaultAllergyFoods() {
        // 1 in 10 chance for each food item to be allergic
        return BuiltInRegistries.ITEM.stream()
                .filter(item -> item.components().has(DataComponents.CONSUMABLE))
                .collect(Collectors.toMap(
                        BuiltInRegistries.ITEM::getKey,
                        _ -> Math.random() < 0.1
                ));
    }

    public static @NonNull List<MobEffectInstance> getAllergyEffects(RandomSource randomSource) {
        return List.copyOf(ALLERGIC_REACTIONS.entrySet())
                .stream()
                .filter(entry -> randomSource.nextFloat() < entry.getValue())
                .map(Map.Entry::getKey)
                .map(effect -> {
                    if (effect == MobEffects.INSTANT_DAMAGE) {
                        return new MobEffectInstance(
                                effect,
                                5,
                                100
                        );
                    } else {
                        return new MobEffectInstance(
                                effect,
                                randomSource.nextInt(20 * 30, 20 * 60 * 10),
                                randomSource.nextInt(0, 5)
                        );
                    }
                })
                .toList();
    }

    public static boolean hasAllergicReaction(Level level) {
        return level.getRandom().nextFloat() < 0.95F;
    }
}
